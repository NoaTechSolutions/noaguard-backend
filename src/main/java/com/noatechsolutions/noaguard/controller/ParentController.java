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

    // Crear nuevo padre
    @PostMapping
    public ResponseEntity<ParentResponse> createParent(@Valid @RequestBody ParentRequest request) {
        ParentResponse createdParent = parentService.createParent(request);
        return new ResponseEntity<>(createdParent, HttpStatus.CREATED);
    }

    // Obtener todos los padres
    @GetMapping
    public ResponseEntity<List<ParentResponse>> getAllParents() {
        List<ParentResponse> parents = parentService.getAllParents();
        return new ResponseEntity<>(parents, HttpStatus.OK);
    }

    // Obtener un padre por id
    @GetMapping("/{id}")
    public ResponseEntity<ParentResponse> getParentById(@PathVariable Long id) {
        ParentResponse parent = parentService.getParentById(id);
        return new ResponseEntity<>(parent, HttpStatus.OK);
    }

    // Actualizar parcialmente un padre
    @PatchMapping("/{id}")
    public ResponseEntity<ParentResponse> updateParent(@PathVariable Long id,
                                                       @Valid @RequestBody ParentUpdateRequest request) {
        ParentResponse updatedParent = parentService.updateParent(id, request);
        return new ResponseEntity<>(updatedParent, HttpStatus.OK);
    }

    // Eliminar un padre
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParent(@PathVariable Long id) {
        parentService.deleteParent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}