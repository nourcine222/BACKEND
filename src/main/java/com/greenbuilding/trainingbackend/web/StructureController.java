package com.greenbuilding.trainingbackend.web;

import com.greenbuilding.trainingbackend.dto.StructureRequest;
import com.greenbuilding.trainingbackend.dto.StructureResponse;
import com.greenbuilding.trainingbackend.service.StructureService;
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

// Handles CRUD requests for structures.
@RestController
@RequestMapping("/api/structures")
@RequiredArgsConstructor
public class StructureController {

    private final StructureService structureService;

    // Creates a new structure.
    @PostMapping
    public ResponseEntity<StructureResponse> create(@Valid @RequestBody StructureRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(structureService.create(request));
    }

    // Updates an existing structure.
    @PutMapping("/{id}")
    public ResponseEntity<StructureResponse> update(@PathVariable Integer id,
                                                    @Valid @RequestBody StructureRequest request) {
        return ResponseEntity.ok(structureService.update(id, request));
    }

    // Retrieves a structure by id.
    @GetMapping("/{id}")
    public ResponseEntity<StructureResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(structureService.getById(id));
    }

    // Returns every structure in the database.
    @GetMapping
    public ResponseEntity<List<StructureResponse>> getAll() {
        return ResponseEntity.ok(structureService.getAll());
    }

    // Deletes a structure by id.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        structureService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
