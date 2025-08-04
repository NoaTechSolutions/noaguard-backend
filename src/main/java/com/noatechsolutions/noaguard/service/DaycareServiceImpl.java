package com.noatechsolutions.noaguard.service;

import com.noatechsolutions.noaguard.dto.*;
import com.noatechsolutions.noaguard.entity.Daycare;
import com.noatechsolutions.noaguard.entity.User;
import com.noatechsolutions.noaguard.enums.RoleType;
import com.noatechsolutions.noaguard.exception.ResourceNotFoundException;
import com.noatechsolutions.noaguard.mapper.DaycareMapper;
import com.noatechsolutions.noaguard.repository.DaycareRepository;
import com.noatechsolutions.noaguard.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DaycareServiceImpl implements DaycareService {

    private final DaycareRepository daycareRepository;
    private final DaycareMapper daycareMapper;
    private final UserRepository userRepository;

    public DaycareServiceImpl(DaycareRepository daycareRepository, DaycareMapper daycareMapper, UserRepository userRepository) {
        this.daycareRepository = daycareRepository;
        this.daycareMapper = daycareMapper;
        this.userRepository = userRepository;
    }

    @Override
    public DaycareResponse createDaycare(DaycareRequest request) {
        Daycare daycare = daycareMapper.toEntity(request);

        // Usuario autenticado
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Current user not found"));

        if (currentUser.getRole().getName() == RoleType.SUPER_ADMIN) {
            if (request.getAdminId() == null) {
                throw new RuntimeException("Admin ID is required for SUPER_ADMIN");
            }
            User adminUser = userRepository.findById(request.getAdminId())
                    .orElseThrow(() -> new ResourceNotFoundException("Admin user not found"));
            if (adminUser.getRole().getName() != RoleType.DAYCARE_ADMIN) {
                throw new RuntimeException("Assigned user must have role DAYCARE_ADMIN");
            }
            daycare.setAdmin(adminUser);
        } else if (currentUser.getRole().getName() == RoleType.DAYCARE_ADMIN) {
            daycare.setAdmin(currentUser);
        } else {
            throw new RuntimeException("You do not have permission to create a daycare");
        }

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
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Current user not found"));

        if (currentUser.getRole().getName() == RoleType.SUPER_ADMIN) {
            // SUPER_ADMIN → lista todos
            return daycareRepository.findAll()
                    .stream()
                    .map(daycareMapper::toResponse)
                    .collect(Collectors.toList());
        }

        if (currentUser.getRole().getName() == RoleType.DAYCARE_ADMIN) {
            // DAYCARE_ADMIN → solo los que administra
            return daycareRepository.findByAdminId(currentUser.getId())
                    .stream()
                    .map(daycareMapper::toResponse)
                    .collect(Collectors.toList());
        }

        throw new RuntimeException("You do not have permission to list daycares");
    }


    @Override
    public DaycareResponse updateDaycare(Long id, DaycareUpdateRequest request) {
        Daycare daycare = daycareRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Daycare not found with id " + id));

        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Current user not found"));

        if (currentUser.getRole().getName() == RoleType.SUPER_ADMIN && request.getAdminId() != null) {
            User adminUser = userRepository.findById(request.getAdminId())
                    .orElseThrow(() -> new ResourceNotFoundException("Admin user not found"));
            if (adminUser.getRole().getName() != RoleType.DAYCARE_ADMIN) {
                throw new RuntimeException("Assigned user must have role DAYCARE_ADMIN");
            }
            daycare.setAdmin(adminUser);
        } else if (currentUser.getRole().getName() != RoleType.SUPER_ADMIN &&
                !daycare.getAdmin().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You do not have permission to update this daycare");
        }

        if (request.getName() != null) daycare.setName(request.getName());
        if (request.getLogoUrl() != null) daycare.setLogoUrl(request.getLogoUrl());
        if (request.getPhone() != null) daycare.setPhone(request.getPhone());
        if (request.getEmail() != null) daycare.setEmail(request.getEmail());
        if (request.getAddress() != null) {
            if (daycare.getAddress() == null) {
                daycare.setAddress(daycareMapper.addressRequestToAddress(request.getAddress()));
            } else {
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

        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Current user not found"));

        if (currentUser.getRole().getName() != RoleType.SUPER_ADMIN &&
                !daycare.getAdmin().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You do not have permission to delete this daycare");
        }

        daycareRepository.delete(daycare);
    }
}
