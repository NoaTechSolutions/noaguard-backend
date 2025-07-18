package com.noatechsolutions.noaguard.controller;

import com.noatechsolutions.noaguard.dto.DaycareRequest;
import com.noatechsolutions.noaguard.dto.DaycareResponse;
import com.noatechsolutions.noaguard.dto.DaycareUpdateRequest;
import com.noatechsolutions.noaguard.service.DaycareService;
import jakarta.validation.Valid;
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
        DaycareResponse response = daycareService.createDaycare(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<DaycareResponse>> getAllDaycares() {
        List<DaycareResponse> daycares = daycareService.getAllDaycares();
        return ResponseEntity.ok(daycares);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DaycareResponse> getDaycareById(@PathVariable Long id) {
        DaycareResponse response = daycareService.getDaycareById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DaycareResponse> updateDaycare(@PathVariable Long id,
                                                         @Valid @RequestBody DaycareUpdateRequest request) {
        DaycareResponse response = daycareService.updateDaycare(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDaycare(@PathVariable Long id) {
        daycareService.deleteDaycare(id);
        return ResponseEntity.noContent().build();
    }
}