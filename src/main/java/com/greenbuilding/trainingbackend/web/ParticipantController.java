package com.greenbuilding.trainingbackend.web;

import com.greenbuilding.trainingbackend.dto.ParticipantRequest;
import com.greenbuilding.trainingbackend.dto.ParticipantResponse;
import com.greenbuilding.trainingbackend.service.ParticipantService;
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

// Handles CRUD requests for participants.
@RestController
@RequestMapping("/api/participants")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService participantService;

    // Creates a new participant.
    @PostMapping
    public ResponseEntity<ParticipantResponse> create(@Valid @RequestBody ParticipantRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(participantService.create(request));
    }

    // Updates an existing participant.
    @PutMapping("/{id}")
    public ResponseEntity<ParticipantResponse> update(@PathVariable Integer id,
                                                      @Valid @RequestBody ParticipantRequest request) {
        return ResponseEntity.ok(participantService.update(id, request));
    }

    // Retrieves a participant by id.
    @GetMapping("/{id}")
    public ResponseEntity<ParticipantResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(participantService.getById(id));
    }

    // Returns every participant.
    @GetMapping
    public ResponseEntity<List<ParticipantResponse>> getAll() {
        return ResponseEntity.ok(participantService.getAll());
    }

    // Deletes a participant by id.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        participantService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
