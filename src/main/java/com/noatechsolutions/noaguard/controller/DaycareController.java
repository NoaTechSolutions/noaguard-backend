package com.noatechsolutions.noaguard.controller;

import com.noatechsolutions.noaguard.dto.DaycareRequest;
import com.noatechsolutions.noaguard.dto.DaycareResponse;
import com.noatechsolutions.noaguard.dto.DaycareUpdateRequest;
import com.noatechsolutions.noaguard.service.DaycareService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/daycares")
public class DaycareController {

    private final DaycareService daycareService;

    public DaycareController(DaycareService daycareService) {
        this.daycareService = daycareService;
    }

    @PostMapping
    public ResponseEntity<DaycareResponse> createDaycare(@Valid @RequestBody DaycareRequest request) {
        return new ResponseEntity<>(daycareService.createDaycare(request), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DaycareResponse> updateDaycare(@PathVariable Long id, @Valid @RequestBody DaycareUpdateRequest request) {
        return ResponseEntity.ok(daycareService.updateDaycare(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DaycareResponse> getDaycareById(@PathVariable Long id) {
        return ResponseEntity.ok(daycareService.getDaycareById(id));
    }

    @GetMapping
    public ResponseEntity<List<DaycareResponse>> getAllDaycares() {
        return ResponseEntity.ok(daycareService.getAllDaycares());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDaycare(@PathVariable Long id) {
        daycareService.deleteDaycare(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/toggle-active")
    public ResponseEntity<DaycareResponse> toggleDaycareActive(@PathVariable Long id) {
        return ResponseEntity.ok(daycareService.toggleDaycareActive(id));
    }
}
