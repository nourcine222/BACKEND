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

@RestController
@RequestMapping("/api/domaines")
@RequiredArgsConstructor
public class DomaineController {

    private final DomaineService domaineService;

    @PostMapping
    public ResponseEntity<DomaineResponse> create(@Valid @RequestBody DomaineRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(domaineService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DomaineResponse> update(@PathVariable Integer id,
                                                   @Valid @RequestBody DomaineRequest request) {
        return ResponseEntity.ok(domaineService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DomaineResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(domaineService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<DomaineResponse>> getAll() {
        return ResponseEntity.ok(domaineService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        domaineService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
