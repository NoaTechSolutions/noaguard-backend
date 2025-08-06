package com.noatechsolutions.noaguard.controller;

import com.noatechsolutions.noaguard.dto.ParentRequest;
import com.noatechsolutions.noaguard.dto.ParentResponse;
import com.noatechsolutions.noaguard.dto.ParentUpdateRequest;
import com.noatechsolutions.noaguard.service.ParentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parents")
public class ParentController {

    private final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    // Crear Parent (con o sin dirección)
    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','DAYCARE_ADMIN','TEACHER')")
    public ResponseEntity<ParentResponse> createParent(@Valid @RequestBody ParentRequest request) {
        ParentResponse response = parentService.createParent(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Actualizar Parent parcialmente
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','DAYCARE_ADMIN','TEACHER')")
    public ResponseEntity<ParentResponse> updateParent(
            @PathVariable Long id,
            @Valid @RequestBody ParentUpdateRequest request) {
        ParentResponse response = parentService.updateParent(id, request);
        return ResponseEntity.ok(response);
    }

    // Obtener Parent por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','DAYCARE_ADMIN','TEACHER')")
    public ResponseEntity<ParentResponse> getParentById(@PathVariable Long id) {
        ParentResponse response = parentService.getParentById(id);
        return ResponseEntity.ok(response);
    }

    // Listar todos los Parents
    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','DAYCARE_ADMIN','TEACHER')")
    public ResponseEntity<List<ParentResponse>> getAllParents() {
        List<ParentResponse> parents = parentService.getAllParents();
        return ResponseEntity.ok(parents);
    }

    // Listar Parents por Student ID
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','DAYCARE_ADMIN','TEACHER')")
    public ResponseEntity<List<ParentResponse>> getParentsByStudentId(@PathVariable Long studentId) {
        List<ParentResponse> parents = parentService.getParentsByStudentId(studentId);
        return ResponseEntity.ok(parents);
    }

    // Eliminar Parent
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','DAYCARE_ADMIN','TEACHER')")
    public ResponseEntity<Void> deleteParent(@PathVariable Long id) {
        parentService.deleteParent(id);
        return ResponseEntity.noContent().build();
    }
}
