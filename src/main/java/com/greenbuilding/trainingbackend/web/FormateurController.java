package com.greenbuilding.trainingbackend.web;

import com.greenbuilding.trainingbackend.dto.FormateurRequest;
import com.greenbuilding.trainingbackend.dto.FormateurResponse;
import com.greenbuilding.trainingbackend.service.FormateurService;
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

@RestController
@RequestMapping("/api/formateurs")
@RequiredArgsConstructor
public class FormateurController {

    private final FormateurService formateurService;

    @PostMapping
    public ResponseEntity<FormateurResponse> create(@Valid @RequestBody FormateurRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(formateurService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormateurResponse> update(@PathVariable Long id,
                                                    @Valid @RequestBody FormateurRequest request) {
        return ResponseEntity.ok(formateurService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormateurResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(formateurService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<FormateurResponse>> getAll() {
        return ResponseEntity.ok(formateurService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        formateurService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
