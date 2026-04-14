package com.greenbuilding.trainingbackend.web;

import com.greenbuilding.trainingbackend.dto.EmployeurRequest;
import com.greenbuilding.trainingbackend.dto.EmployeurResponse;
import com.greenbuilding.trainingbackend.service.EmployeurService;
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
@RequestMapping("/api/employeurs")
@RequiredArgsConstructor
public class EmployeurController {

    private final EmployeurService employeurService;

    @PostMapping
    public ResponseEntity<EmployeurResponse> create(@Valid @RequestBody EmployeurRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeurService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeurResponse> update(@PathVariable Integer id,
                                                    @Valid @RequestBody EmployeurRequest request) {
        return ResponseEntity.ok(employeurService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeurResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(employeurService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeurResponse>> getAll() {
        return ResponseEntity.ok(employeurService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        employeurService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
