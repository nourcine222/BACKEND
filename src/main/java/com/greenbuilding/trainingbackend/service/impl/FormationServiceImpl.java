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

// Implements formation CRUD rules and resolves domain, trainer, and participant relations.
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
        String titre = request.titre().trim();
        String lieu = request.lieu().trim();

        // Prevent the same title/year combination from being stored twice.
        formationRepository.findByTitreIgnoreCaseAndAnnee(titre, request.annee())
                .ifPresent(formation -> {
                    throw new IllegalArgumentException("Formation already exists for this year");
                });

        Formation saved = formationRepository.save(buildFormation(new Formation(), request, titre, lieu));
        return toResponse(saved);
    }

    @Override
    public FormationResponse update(Long id, FormationRequest request) {
        String titre = request.titre().trim();
        String lieu = request.lieu().trim();

        // Load the current formation, then validate the new title/year combination.
        Formation formation = formationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Formation not found with id " + id));

        formationRepository.findByTitreIgnoreCaseAndAnnee(titre, request.annee())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Formation already exists for this year");
                });

        return toResponse(formationRepository.save(buildFormation(formation, request, titre, lieu)));
    }

    @Override
    @Transactional(readOnly = true)
    public FormationResponse getById(Long id) {
        // Read one formation and convert it into the response DTO.
        return formationRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Formation not found with id " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FormationResponse> getAll() {
        // Return every formation as DTOs.
        return formationRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        // Delete only after confirming the formation exists.
        Formation formation = formationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Formation not found with id " + id));
        formationRepository.delete(formation);
    }

    private Formation buildFormation(Formation formation, FormationRequest request, String titre, String lieu) {
        // Resolve the domain and optional trainer before copying the rest of the request data.
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

        formation.setTitre(titre);
        formation.setAnnee(request.annee());
        formation.setDureeJours(request.dureeJours());
        formation.setBudget(request.budget());
        formation.setLieu(lieu);
        formation.setDateFormation(request.dateFormation());
        formation.setDomaine(domaine);
        formation.setFormateur(formateur);
        formation.getParticipants().clear();
        formation.getParticipants().addAll(participants);
        return formation;
    }

    private FormationResponse toResponse(Formation formation) {
        // Flatten nested entity data so the API response stays easy to consume.
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
