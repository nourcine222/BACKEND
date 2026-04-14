package com.greenbuilding.trainingbackend.service.impl;

import com.greenbuilding.trainingbackend.dto.FormationRequest;
import com.greenbuilding.trainingbackend.dto.FormationResponse;
import com.greenbuilding.trainingbackend.entity.Domaine;
import com.greenbuilding.trainingbackend.entity.Formateur;
import com.greenbuilding.trainingbackend.entity.Formation;
import com.greenbuilding.trainingbackend.entity.Participant;
import com.greenbuilding.trainingbackend.exception.ResourceNotFoundException;
import com.greenbuilding.trainingbackend.repository.DomaineRepository;
import com.greenbuilding.trainingbackend.repository.FormateurRepository;
import com.greenbuilding.trainingbackend.repository.FormationRepository;
import com.greenbuilding.trainingbackend.repository.ParticipantRepository;
import com.greenbuilding.trainingbackend.service.FormationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FormationServiceImpl implements FormationService {

    private final FormationRepository formationRepository;
    private final DomaineRepository domaineRepository;
    private final FormateurRepository formateurRepository;
    private final ParticipantRepository participantRepository;

    @Override
    public FormationResponse create(FormationRequest request) {
        formationRepository.findByTitreIgnoreCaseAndAnnee(request.titre(), request.annee())
                .ifPresent(formation -> {
                    throw new IllegalArgumentException("Formation already exists for this year");
                });

        Formation saved = formationRepository.save(buildFormation(new Formation(), request));
        return toResponse(saved);
    }

    @Override
    public FormationResponse update(Long id, FormationRequest request) {
        Formation formation = formationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Formation not found with id " + id));

        formationRepository.findByTitreIgnoreCaseAndAnnee(request.titre(), request.annee())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Formation already exists for this year");
                });

        return toResponse(formationRepository.save(buildFormation(formation, request)));
    }

    @Override
    @Transactional(readOnly = true)
    public FormationResponse getById(Long id) {
        return formationRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Formation not found with id " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FormationResponse> getAll() {
        return formationRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        Formation formation = formationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Formation not found with id " + id));
        formationRepository.delete(formation);
    }

    private Formation buildFormation(Formation formation, FormationRequest request) {
        Domaine domaine = domaineRepository.findById(request.domaineId())
                .orElseThrow(() -> new ResourceNotFoundException("Domaine not found with id " + request.domaineId()));

        Formateur formateur = null;
        if (request.formateurId() != null) {
            formateur = formateurRepository.findById(request.formateurId())
                    .orElseThrow(() -> new ResourceNotFoundException("Formateur not found with id " + request.formateurId()));
        }

        List<Participant> participants = new ArrayList<>();
        if (request.participantIds() != null && !request.participantIds().isEmpty()) {
            participants = participantRepository.findAllById(request.participantIds());
            if (participants.size() != request.participantIds().size()) {
                throw new ResourceNotFoundException("One or more participants were not found");
            }
        }

        formation.setTitre(request.titre().trim());
        formation.setAnnee(request.annee());
        formation.setDureeJours(request.dureeJours());
        formation.setBudget(request.budget());
        formation.setLieu(request.lieu().trim());
        formation.setDateFormation(request.dateFormation());
        formation.setDomaine(domaine);
        formation.setFormateur(formateur);
        formation.getParticipants().clear();
        formation.getParticipants().addAll(participants);
        return formation;
    }

    private FormationResponse toResponse(Formation formation) {
        Long formateurId = formation.getFormateur() != null ? formation.getFormateur().getId() : null;
        String formateurNom = formation.getFormateur() != null ? formation.getFormateur().getNom() : null;
        String formateurPrenom = formation.getFormateur() != null ? formation.getFormateur().getPrenom() : null;
        var formateurType = formation.getFormateur() != null ? formation.getFormateur().getType() : null;

        List<FormationResponse.ParticipantSummary> participantSummaries = formation.getParticipants()
                .stream()
                .map(participant -> new FormationResponse.ParticipantSummary(
                        participant.getId(),
                        participant.getNom(),
                        participant.getPrenom()
                ))
                .toList();

        return new FormationResponse(
                formation.getId(),
                formation.getTitre(),
                formation.getAnnee(),
                formation.getDureeJours(),
                formation.getBudget(),
                formation.getLieu(),
                formation.getDateFormation(),
                formation.getDomaine().getId(),
                formation.getDomaine().getLibelle(),
                formateurId,
                formateurNom,
                formateurPrenom,
                formateurType,
                participantSummaries
        );
    }
}
