package com.noatechsolutions.noaguard.controller;

import com.noatechsolutions.noaguard.dto.ParentRequest;
import com.noatechsolutions.noaguard.dto.ParentResponse;
import com.noatechsolutions.noaguard.dto.ParentUpdateRequest;
import com.noatechsolutions.noaguard.service.ParentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parents")
public class ParentController {

    private final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    // Crear padre
    @PostMapping
    public ResponseEntity<ParentResponse> createParent(@Valid @RequestBody ParentRequest request) {
        ParentResponse response = parentService.createParent(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Actualizar padre
    @PatchMapping("/{id}")
    public ResponseEntity<ParentResponse> updateParent(@PathVariable Long id,
                                                       @Valid @RequestBody ParentUpdateRequest request) {
        ParentResponse response = parentService.updateParent(id, request);
        return ResponseEntity.ok(response);
    }

    // Obtener padre por ID
    @GetMapping("/{id}")
    public ResponseEntity<ParentResponse> getParentById(@PathVariable Long id) {
        ParentResponse response = parentService.getParentById(id);
        return ResponseEntity.ok(response);
    }

    // Obtener todos los padres
    @GetMapping
    public ResponseEntity<List<ParentResponse>> getAllParents() {
        List<ParentResponse> parents = parentService.getAllParents();
        return ResponseEntity.ok(parents);
    }

    // Obtener padres por ID de estudiante
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ParentResponse>> getParentsByStudentId(@PathVariable Long studentId) {
        List<ParentResponse> parents = parentService.getParentsByStudentId(studentId);
        return ResponseEntity.ok(parents);
    }

    // Eliminar padre
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParent(@PathVariable Long id) {
        parentService.deleteParent(id);
        return ResponseEntity.noContent().build();
    }
}
