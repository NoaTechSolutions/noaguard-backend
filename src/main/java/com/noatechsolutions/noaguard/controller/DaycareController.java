package com.noatechsolutions.noaguard.controller;

import com.noatechsolutions.noaguard.dto.DaycareRequest;
import com.noatechsolutions.noaguard.dto.DaycareResponse;
import com.noatechsolutions.noaguard.dto.DaycareUpdateRequest;
import com.noatechsolutions.noaguard.service.DaycareService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/daycares")
@PreAuthorize("hasAnyRole('SUPER_ADMIN', 'DAYCARE_ADMIN')")
public class DaycareController {

    private final DaycareService daycareService;

    public DaycareController(DaycareService daycareService) {
        this.daycareService = daycareService;
    }

    @PostMapping
    public DaycareResponse createDaycare(@Valid @RequestBody DaycareRequest request) {
        return daycareService.createDaycare(request);
    }

    @GetMapping("/{id}")
    public DaycareResponse getDaycareById(@PathVariable Long id) {
        return daycareService.getDaycareById(id);
    }

    @GetMapping
    public List<DaycareResponse> getAllDaycares() {
        return daycareService.getAllDaycares();
    }

    @PutMapping("/{id}")
    public DaycareResponse updateDaycare(@PathVariable Long id,
                                         @Valid @RequestBody DaycareUpdateRequest request) {
        return daycareService.updateDaycare(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteDaycare(@PathVariable Long id) {
        daycareService.deleteDaycare(id);
    }
}
