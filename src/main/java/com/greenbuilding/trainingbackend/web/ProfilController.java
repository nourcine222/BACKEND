package com.greenbuilding.trainingbackend.web;

import com.greenbuilding.trainingbackend.dto.ProfilRequest;
import com.greenbuilding.trainingbackend.dto.ProfilResponse;
import com.greenbuilding.trainingbackend.service.ProfilService;
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

// Handles CRUD requests for profiles.
@RestController
@RequestMapping("/api/profils")
@RequiredArgsConstructor
public class ProfilController {

    private final ProfilService profilService;

    // Creates a new profile.
    @PostMapping
    public ResponseEntity<ProfilResponse> create(@Valid @RequestBody ProfilRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(profilService.create(request));
    }

    // Updates an existing profile.
    @PutMapping("/{id}")
    public ResponseEntity<ProfilResponse> update(@PathVariable Integer id,
                                                 @Valid @RequestBody ProfilRequest request) {
        return ResponseEntity.ok(profilService.update(id, request));
    }

    // Retrieves a profile by id.
    @GetMapping("/{id}")
    public ResponseEntity<ProfilResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(profilService.getById(id));
    }

    // Returns every stored profile.
    @GetMapping
    public ResponseEntity<List<ProfilResponse>> getAll() {
        return ResponseEntity.ok(profilService.getAll());
    }

    // Deletes a profile by id.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        profilService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
