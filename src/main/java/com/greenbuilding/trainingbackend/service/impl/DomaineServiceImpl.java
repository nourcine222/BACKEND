package com.greenbuilding.trainingbackend.service.impl;

import com.greenbuilding.trainingbackend.dto.DomaineRequest;
import com.greenbuilding.trainingbackend.dto.DomaineResponse;
import com.greenbuilding.trainingbackend.entity.Domaine;
import com.greenbuilding.trainingbackend.exception.ResourceNotFoundException;
import com.greenbuilding.trainingbackend.repository.DomaineRepository;
import com.greenbuilding.trainingbackend.service.DomaineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DomaineServiceImpl implements DomaineService {

    private final DomaineRepository domaineRepository;

    @Override
    public DomaineResponse create(DomaineRequest request) {
        domaineRepository.findByLibelleIgnoreCase(request.libelle()).ifPresent(domaine -> {
            throw new IllegalArgumentException("Domaine already exists");
        });

        Domaine saved = domaineRepository.save(
                Domaine.builder()
                        .libelle(request.libelle().trim())
                        .build()
        );
        return toResponse(saved);
    }

    @Override
    public DomaineResponse update(Integer id, DomaineRequest request) {
        Domaine domaine = domaineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Domaine not found with id " + id));

        domaineRepository.findByLibelleIgnoreCase(request.libelle())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Domaine already exists");
                });

        domaine.setLibelle(request.libelle().trim());
        return toResponse(domaineRepository.save(domaine));
    }

    @Override
    @Transactional(readOnly = true)
    public DomaineResponse getById(Integer id) {
        return domaineRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Domaine not found with id " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DomaineResponse> getAll() {
        return domaineRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void delete(Integer id) {
        Domaine domaine = domaineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Domaine not found with id " + id));
        domaineRepository.delete(domaine);
    }

    private DomaineResponse toResponse(Domaine domaine) {
        return new DomaineResponse(domaine.getId(), domaine.getLibelle());
    }
}
