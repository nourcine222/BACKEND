package com.greenbuilding.trainingbackend.service.impl;

import com.greenbuilding.trainingbackend.dto.ParticipantRequest;
import com.greenbuilding.trainingbackend.dto.ParticipantResponse;
import com.greenbuilding.trainingbackend.entity.Participant;
import com.greenbuilding.trainingbackend.entity.Profil;
import com.greenbuilding.trainingbackend.entity.Structure;
import com.greenbuilding.trainingbackend.exception.ResourceNotFoundException;
import com.greenbuilding.trainingbackend.repository.ParticipantRepository;
import com.greenbuilding.trainingbackend.repository.ProfilRepository;
import com.greenbuilding.trainingbackend.repository.StructureRepository;
import com.greenbuilding.trainingbackend.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Implements participant CRUD rules, including related structure and profile lookups.
@Service
@RequiredArgsConstructor
@Transactional
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;
    private final StructureRepository structureRepository;
    private final ProfilRepository profilRepository;

    @Override
    public ParticipantResponse create(ParticipantRequest request) {
        String email = normalizeEmail(request.email());

        // Optional email values still need to be unique when they are present.
        if (email != null) {
            participantRepository.findByEmailIgnoreCase(email).ifPresent(participant -> {
                throw new IllegalArgumentException("Participant already exists");
            });
        }

        Participant saved = participantRepository.save(buildParticipant(new Participant(), request, email));
        return toResponse(saved);
    }

    @Override
    public ParticipantResponse update(Integer id, ParticipantRequest request) {
        String email = normalizeEmail(request.email());

        // Load the participant first, then re-check email uniqueness if an email is provided.
        Participant participant = participantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participant not found with id " + id));

        if (email != null) {
            participantRepository.findByEmailIgnoreCase(email)
                    .filter(existing -> !existing.getId().equals(id))
                    .ifPresent(existing -> {
                        throw new IllegalArgumentException("Participant already exists");
                    });
        }

        return toResponse(participantRepository.save(buildParticipant(participant, request, email)));
    }

    @Override
    @Transactional(readOnly = true)
    public ParticipantResponse getById(Integer id) {
        // Return a single participant as a DTO.
        return participantRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Participant not found with id " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipantResponse> getAll() {
        // Return every participant as DTOs.
        return participantRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void delete(Integer id) {
        // Delete only after the participant exists.
        Participant participant = participantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participant not found with id " + id));
        participantRepository.delete(participant);
    }

    private Participant buildParticipant(Participant participant, ParticipantRequest request, String email) {
        // Resolve foreign keys and copy sanitized request data into the entity.
        Structure structure = structureRepository.findById(request.structureId())
                .orElseThrow(() -> new ResourceNotFoundException("Structure not found with id " + request.structureId()));
        Profil profil = profilRepository.findById(request.profilId())
                .orElseThrow(() -> new ResourceNotFoundException("Profil not found with id " + request.profilId()));

        participant.setNom(request.nom().trim());
        participant.setPrenom(request.prenom().trim());
        participant.setEmail(email);
        participant.setTel(request.tel() == null || request.tel().isBlank() ? null : request.tel().trim());
        participant.setStructure(structure);
        participant.setProfil(profil);
        return participant;
    }

    private String normalizeEmail(String email) {
        if (email == null || email.isBlank()) {
            return null;
        }
        return email.trim();
    }

    private ParticipantResponse toResponse(Participant participant) {
        // Flatten the entity graph into the API response payload.
        return new ParticipantResponse(
                participant.getId(),
                participant.getNom(),
                participant.getPrenom(),
                participant.getEmail(),
                participant.getTel(),
                participant.getStructure().getId(),
                participant.getStructure().getLibelle(),
                participant.getProfil().getId(),
                participant.getProfil().getLibelle()
        );
    }
}
