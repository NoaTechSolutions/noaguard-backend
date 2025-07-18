package com.noatechsolutions.noaguard.service;

import com.noatechsolutions.noaguard.dto.*;
import com.noatechsolutions.noaguard.entity.Address;
import com.noatechsolutions.noaguard.entity.Daycare;
import com.noatechsolutions.noaguard.exception.ResourceNotFoundException;
import com.noatechsolutions.noaguard.mapper.DaycareMapper;
import com.noatechsolutions.noaguard.repository.DaycareRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DaycareServiceImpl implements DaycareService {

    private final DaycareRepository daycareRepository;
    private final DaycareMapper daycareMapper;

    public DaycareServiceImpl(DaycareRepository daycareRepository, DaycareMapper daycareMapper) {
        this.daycareRepository = daycareRepository;
        this.daycareMapper = daycareMapper;
    }

    @Override
    public DaycareResponse createDaycare(DaycareRequest request) {
        Daycare daycare = daycareMapper.toEntity(request);
        daycare.setCreatedAt(LocalDateTime.now());
        daycare.setUpdatedAt(LocalDateTime.now());
        Daycare saved = daycareRepository.save(daycare);
        return daycareMapper.toResponse(saved);
    }

    @Override
    public DaycareResponse getDaycareById(Long id) {
        Daycare daycare = daycareRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Daycare not found with id " + id));
        return daycareMapper.toResponse(daycare);
    }

    @Override
    public List<DaycareResponse> getAllDaycares() {
        return daycareRepository.findAll()
                .stream()
                .map(daycareMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DaycareResponse updateDaycare(Long id, DaycareUpdateRequest request) {
        Daycare daycare = daycareRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Daycare not found with id " + id));

        if (request.getName() != null) daycare.setName(request.getName());
        if (request.getLogoUrl() != null) daycare.setLogoUrl(request.getLogoUrl());
        if (request.getPhone() != null) daycare.setPhone(request.getPhone());
        if (request.getEmail() != null) daycare.setEmail(request.getEmail());
        if (request.getAddress() != null) {
            if (daycare.getAddress() == null) {
                daycare.setAddress(daycareMapper.addressRequestToAddress(request.getAddress()));
            } else {
                // actualizar campo por campo
                var ar = request.getAddress();
                var addr = daycare.getAddress();
                if (ar.getStreet() != null) addr.setStreet(ar.getStreet());
                if (ar.getCity() != null) addr.setCity(ar.getCity());
                if (ar.getState() != null) addr.setState(ar.getState());
                if (ar.getZipCode() != null) addr.setZipCode(ar.getZipCode());
                if (ar.getCountry() != null) addr.setCountry(ar.getCountry());
            }
        }
        daycare.setUpdatedAt(LocalDateTime.now());
        Daycare updated = daycareRepository.save(daycare);
        return daycareMapper.toResponse(updated);
    }

    @Override
    public void deleteDaycare(Long id) {
        Daycare daycare = daycareRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Daycare not found with id " + id));
        daycareRepository.delete(daycare);
    }
}