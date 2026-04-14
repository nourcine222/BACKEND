package com.greenbuilding.trainingbackend.web;

import com.greenbuilding.trainingbackend.dto.FormationRequest;
import com.greenbuilding.trainingbackend.dto.FormationResponse;
import com.greenbuilding.trainingbackend.service.FormationService;
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

// Handles CRUD requests for formations.
@RestController
@RequestMapping("/api/formations")
@RequiredArgsConstructor
public class FormationController {

    private final FormationService formationService;

    // Creates a new formation.
    @PostMapping
    public ResponseEntity<FormationResponse> create(@Valid @RequestBody FormationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(formationService.create(request));
    }

    // Updates an existing formation.
    @PutMapping("/{id}")
    public ResponseEntity<FormationResponse> update(@PathVariable Long id,
                                                    @Valid @RequestBody FormationRequest request) {
        return ResponseEntity.ok(formationService.update(id, request));
    }

    // Retrieves a formation by id.
    @GetMapping("/{id}")
    public ResponseEntity<FormationResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(formationService.getById(id));
    }

    // Returns every formation.
    @GetMapping
    public ResponseEntity<List<FormationResponse>> getAll() {
        return ResponseEntity.ok(formationService.getAll());
    }

    // Deletes a formation by id.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        formationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
