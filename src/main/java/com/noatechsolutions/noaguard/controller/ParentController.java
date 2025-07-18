package com.noatechsolutions.noaguard.controller;

import com.noatechsolutions.noaguard.dto.ParentRequest;
import com.noatechsolutions.noaguard.dto.ParentResponse;
import com.noatechsolutions.noaguard.dto.ParentUpdateRequest;
import com.noatechsolutions.noaguard.service.ParentService;
import jakarta.validation.Valid;
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

    @PostMapping
    public ResponseEntity<ParentResponse> createParent(@Valid @RequestBody ParentRequest request) {
        ParentResponse response = parentService.createParent(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ParentResponse>> getAllParents() {
        List<ParentResponse> parents = parentService.getAllParents();
        return ResponseEntity.ok(parents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParentResponse> getParentById(@PathVariable Long id) {
        ParentResponse response = parentService.getParentById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParentResponse> updateParent(@PathVariable Long id,
                                                       @Valid @RequestBody ParentUpdateRequest request) {
        ParentResponse response = parentService.updateParent(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParent(@PathVariable Long id) {
        parentService.deleteParent(id);
        return ResponseEntity.noContent().build();
    }
}