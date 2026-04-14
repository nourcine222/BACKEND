package com.greenbuilding.trainingbackend.service.impl;

import com.greenbuilding.trainingbackend.dto.FormateurRequest;
import com.greenbuilding.trainingbackend.dto.FormateurResponse;
import com.greenbuilding.trainingbackend.entity.Employeur;
import com.greenbuilding.trainingbackend.entity.Formateur;
import com.greenbuilding.trainingbackend.exception.ResourceNotFoundException;
import com.greenbuilding.trainingbackend.repository.EmployeurRepository;
import com.greenbuilding.trainingbackend.repository.FormateurRepository;
import com.greenbuilding.trainingbackend.service.FormateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Implements trainer CRUD rules and relation lookups.
@Service
@RequiredArgsConstructor
@Transactional
public class FormateurServiceImpl implements FormateurService {

    private final FormateurRepository formateurRepository;
    private final EmployeurRepository employeurRepository;

    @Override
    public FormateurResponse create(FormateurRequest request) {
        String email = normalizeEmail(request.email());

        if (email != null) {
            formateurRepository.findByEmailIgnoreCase(email).ifPresent(formateur -> {
                throw new IllegalArgumentException("Formateur already exists");
            });
        }

        Formateur saved = formateurRepository.save(buildFormateur(new Formateur(), request, email));
        return toResponse(saved);
    }

    @Override
    public FormateurResponse update(Long id, FormateurRequest request) {
        String email = normalizeEmail(request.email());

        Formateur formateur = formateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Formateur not found with id " + id));

        if (email != null) {
            formateurRepository.findByEmailIgnoreCase(email)
                    .filter(existing -> !existing.getId().equals(id))
                    .ifPresent(existing -> {
                        throw new IllegalArgumentException("Formateur already exists");
                    });
        }

        return toResponse(formateurRepository.save(buildFormateur(formateur, request, email)));
    }

    @Override
    @Transactional(readOnly = true)
    public FormateurResponse getById(Long id) {
        return formateurRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Formateur not found with id " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FormateurResponse> getAll() {
        return formateurRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        Formateur formateur = formateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Formateur not found with id " + id));
        formateurRepository.delete(formateur);
    }

    private Formateur buildFormateur(Formateur formateur, FormateurRequest request, String email) {
        Employeur employeur = null;
        if (request.employeurId() != null) {
            employeur = employeurRepository.findById(request.employeurId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employeur not found with id " + request.employeurId()));
        }

        formateur.setNom(request.nom().trim());
        formateur.setPrenom(request.prenom().trim());
        formateur.setEmail(email);
        formateur.setTel(request.tel() == null || request.tel().isBlank() ? null : request.tel().trim());
        formateur.setType(request.type());
        formateur.setEmployeur(employeur);
        return formateur;
    }

    private String normalizeEmail(String email) {
        if (email == null || email.isBlank()) {
            return null;
        }
        return email.trim();
    }

    private FormateurResponse toResponse(Formateur formateur) {
        Integer employeurId = formateur.getEmployeur() != null ? formateur.getEmployeur().getId() : null;
        String employeurNom = formateur.getEmployeur() != null ? formateur.getEmployeur().getNomEmployeur() : null;
        return new FormateurResponse(
                formateur.getId(),
                formateur.getNom(),
                formateur.getPrenom(),
                formateur.getEmail(),
                formateur.getTel(),
                formateur.getType(),
                employeurId,
                employeurNom
        );
    }
}
