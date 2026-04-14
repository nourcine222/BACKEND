package com.greenbuilding.trainingbackend.service.impl;

import com.greenbuilding.trainingbackend.dto.ProfilRequest;
import com.greenbuilding.trainingbackend.dto.ProfilResponse;
import com.greenbuilding.trainingbackend.entity.Profil;
import com.greenbuilding.trainingbackend.exception.ResourceNotFoundException;
import com.greenbuilding.trainingbackend.repository.ProfilRepository;
import com.greenbuilding.trainingbackend.service.ProfilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Implements profile CRUD rules and DTO mapping.
@Service
@RequiredArgsConstructor
@Transactional
public class ProfilServiceImpl implements ProfilService {

    private final ProfilRepository profilRepository;

    @Override
    public ProfilResponse create(ProfilRequest request) {
        profilRepository.findByLibelleIgnoreCase(request.libelle()).ifPresent(profil -> {
            throw new IllegalArgumentException("Profil already exists");
        });

        Profil saved = profilRepository.save(
                Profil.builder()
                        .libelle(request.libelle().trim())
                        .build()
        );
        return toResponse(saved);
    }

    @Override
    public ProfilResponse update(Integer id, ProfilRequest request) {
        Profil profil = profilRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profil not found with id " + id));

        profilRepository.findByLibelleIgnoreCase(request.libelle())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Profil already exists");
                });

        profil.setLibelle(request.libelle().trim());
        return toResponse(profilRepository.save(profil));
    }

    @Override
    @Transactional(readOnly = true)
    public ProfilResponse getById(Integer id) {
        return profilRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Profil not found with id " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProfilResponse> getAll() {
        return profilRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void delete(Integer id) {
        Profil profil = profilRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profil not found with id " + id));
        profilRepository.delete(profil);
    }

    private ProfilResponse toResponse(Profil profil) {
        return new ProfilResponse(profil.getId(), profil.getLibelle());
    }
}
