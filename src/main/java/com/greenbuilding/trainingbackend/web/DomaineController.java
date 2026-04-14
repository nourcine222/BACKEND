package com.greenbuilding.trainingbackend.web;

import com.greenbuilding.trainingbackend.dto.DomaineRequest;
import com.greenbuilding.trainingbackend.dto.DomaineResponse;
import com.greenbuilding.trainingbackend.service.DomaineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Handles CRUD requests for domains.
@RestController
@RequestMapping("/api/domaines")
@RequiredArgsConstructor
public class DomaineController {

    private final DomaineService domaineService;

    // Creates a new domain.
    @PostMapping
    public ResponseEntity<DomaineResponse> create(@Valid @RequestBody DomaineRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(domaineService.create(request));
    }

    // Updates an existing domain.
    @PutMapping("/{id}")
    public ResponseEntity<DomaineResponse> update(@PathVariable Integer id,
                                                  @Valid @RequestBody DomaineRequest request) {
        return ResponseEntity.ok(domaineService.update(id, request));
    }

    // Retrieves a domain by id.
    @GetMapping("/{id}")
    public ResponseEntity<DomaineResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(domaineService.getById(id));
    }

    // Returns every stored domain.
    @GetMapping
    public ResponseEntity<List<DomaineResponse>> getAll() {
        return ResponseEntity.ok(domaineService.getAll());
    }

    // Deletes a domain by id.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        domaineService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
