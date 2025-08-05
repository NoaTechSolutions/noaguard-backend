package com.noatechsolutions.noaguard.controller;

import com.noatechsolutions.noaguard.dto.AddressRequest;
import com.noatechsolutions.noaguard.dto.AddressResponse;
import com.noatechsolutions.noaguard.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(@Valid @RequestBody AddressRequest request) {
        return ResponseEntity.ok(addressService.createAddress(request));
    }

    @GetMapping("/{entityType}/{entityId}")
    public ResponseEntity<List<AddressResponse>> getAddressesByEntity(
            @PathVariable String entityType,
            @PathVariable Long entityId) {
        return ResponseEntity.ok(addressService.getAddressesByEntity(entityType, entityId));
    }
}
