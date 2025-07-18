package com.noatechsolutions.noaguard.service;

import com.noatechsolutions.noaguard.dto.*;
import com.noatechsolutions.noaguard.entity.Address;
import com.noatechsolutions.noaguard.entity.Daycare;
import com.noatechsolutions.noaguard.exception.ResourceNotFoundException;
import com.noatechsolutions.noaguard.repository.DaycareRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DaycareServiceImpl implements DaycareService {

    private final DaycareRepository daycareRepository;

    public DaycareServiceImpl(DaycareRepository daycareRepository) {
        this.daycareRepository = daycareRepository;
    }

    @Override
    public DaycareResponse createDaycare(DaycareRequest request) {
        Daycare daycare = new Daycare();
        daycare.setName(request.getName());
        daycare.setLogoUrl(request.getLogoUrl());
        daycare.setPhone(request.getPhone());
        daycare.setEmail(request.getEmail());
        daycare.setAddress(convertToAddressEntity(request.getAddress()));
        daycare.setCreatedAt(LocalDateTime.now());
        daycare.setUpdatedAt(LocalDateTime.now());

        daycareRepository.save(daycare);

        return convertToResponse(daycare);
    }

    @Override
    public List<DaycareResponse> getAllDaycares() {
        return daycareRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DaycareResponse getDaycareById(Long id) {
        Daycare daycare = daycareRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Daycare not found with id " + id));
        return convertToResponse(daycare);
    }

    @Override
    public DaycareResponse updateDaycare(Long id, DaycareUpdateRequest request) {
        Daycare daycare = daycareRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Daycare not found with id " + id));

        if (request.getName() != null) daycare.setName(request.getName());
        if (request.getLogoUrl() != null) daycare.setLogoUrl(request.getLogoUrl());
        if (request.getPhone() != null) daycare.setPhone(request.getPhone());
        if (request.getEmail() != null) daycare.setEmail(request.getEmail());
        if (request.getAddress() != null) daycare.setAddress(convertToAddressEntity(request.getAddress()));

        daycare.setUpdatedAt(LocalDateTime.now());

        daycareRepository.save(daycare);
        return convertToResponse(daycare);
    }

    @Override
    public void deleteDaycare(Long id) {
        if (!daycareRepository.existsById(id)) {
            throw new ResourceNotFoundException("Daycare not found with id " + id);
        }
        daycareRepository.deleteById(id);
    }

    // Conversión DTO -> Entity
    private Address convertToAddressEntity(AddressRequest request) {
        if (request == null) return null;
        Address address = new Address();
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setZipCode(request.getZipCode());
        address.setCountry(request.getCountry());
        return address;
    }

    // Conversión Entity -> DTO
    private AddressResponse convertToAddressResponse(Address address) {
        if (address == null) return null;
        AddressResponse response = new AddressResponse();
        response.setStreet(address.getStreet());
        response.setCity(address.getCity());
        response.setState(address.getState());
        response.setZipCode(address.getZipCode());
        response.setCountry(address.getCountry());
        return response;
    }

    // Conversión Entity Daycare -> DTO
    private DaycareResponse convertToResponse(Daycare daycare) {
        DaycareResponse response = new DaycareResponse();
        response.setId(daycare.getId());
        response.setName(daycare.getName());
        response.setLogoUrl(daycare.getLogoUrl());
        response.setPhone(daycare.getPhone());
        response.setEmail(daycare.getEmail());
        response.setAddress(convertToAddressResponse(daycare.getAddress()));
        response.setCreatedAt(daycare.getCreatedAt());
        response.setUpdatedAt(daycare.getUpdatedAt());
        response.setUpdatedBy(daycare.getUpdatedBy());
        return response;
    }
}