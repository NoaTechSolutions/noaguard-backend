package com.noatechsolutions.noaguard.service;

import com.noatechsolutions.noaguard.dto.DaycareRequest;
import com.noatechsolutions.noaguard.dto.DaycareResponse;
import com.noatechsolutions.noaguard.dto.DaycareUpdateRequest;
import com.noatechsolutions.noaguard.entity.Address;
import com.noatechsolutions.noaguard.entity.Daycare;
import com.noatechsolutions.noaguard.entity.User;
import com.noatechsolutions.noaguard.enums.RoleType;
import com.noatechsolutions.noaguard.exception.ResourceNotFoundException;
import com.noatechsolutions.noaguard.mapper.AddressMapper;
import com.noatechsolutions.noaguard.repository.AddressRepository;
import com.noatechsolutions.noaguard.repository.DaycareRepository;
import com.noatechsolutions.noaguard.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DaycareServiceImpl implements DaycareService {

    private final DaycareRepository daycareRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public DaycareServiceImpl(DaycareRepository daycareRepository, UserRepository userRepository,AddressRepository addressRepository,
                              AddressMapper addressMapper) {
        this.daycareRepository = daycareRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    public DaycareResponse createDaycare(DaycareRequest request) {
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Current user not found"));

        if (daycareRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("A daycare with this email already exists");
        }

        Daycare daycare = new Daycare();
        daycare.setName(request.getName());
        daycare.setLogoUrl(request.getLogoUrl());
        daycare.setPhone(request.getPhone());
        daycare.setEmail(request.getEmail());
        daycare.setCreatedAt(LocalDateTime.now());
        daycare.setUpdatedAt(LocalDateTime.now());
        daycare.setUpdatedBy(currentEmail);

        // Asignación de admin según el rol
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

        // Guardar Daycare primero para tener ID
        Daycare saved = daycareRepository.save(daycare);

        // Guardar dirección si se envía
        if (request.getAddress() != null) {
            Address address = new Address();
            address.setStreet(request.getAddress().getStreet());
            address.setCity(request.getAddress().getCity());
            address.setState(request.getAddress().getState());
            address.setZipCode(request.getAddress().getZipCode());
            address.setCountry(request.getAddress().getCountry());
            address.setEntityId(saved.getId());
            address.setEntityType("DAYCARE");
            addressRepository.save(address);
        }

        return toResponse(saved);
    }


    @Override
    public DaycareResponse updateDaycare(Long id, DaycareUpdateRequest request) {
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Current user not found"));

        Daycare daycare = daycareRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Daycare not found"));

        if (request.getName() != null) daycare.setName(request.getName());
        if (request.getLogoUrl() != null) daycare.setLogoUrl(request.getLogoUrl());
        if (request.getPhone() != null) daycare.setPhone(request.getPhone());
        if (request.getEmail() != null) daycare.setEmail(request.getEmail());

        // ✅ Mantener validación de cambio de admin solo por SUPER_ADMIN
        if (request.getAdminId() != null) {
            if (currentUser.getRole().getName() != RoleType.SUPER_ADMIN) {
                throw new RuntimeException("Only SUPER_ADMIN can change the admin of a daycare");
            }
            User adminUser = userRepository.findById(request.getAdminId())
                    .orElseThrow(() -> new ResourceNotFoundException("Admin user not found"));
            if (adminUser.getRole().getName() != RoleType.DAYCARE_ADMIN) {
                throw new RuntimeException("Assigned user must have role DAYCARE_ADMIN");
            }
            daycare.setAdmin(adminUser);
        }

        // ✅ Actualización parcial de dirección
        if (request.getAddress() != null) {
            List<Address> addresses = addressRepository.findByEntityTypeAndEntityId("DAYCARE", daycare.getId());
            if (!addresses.isEmpty()) {
                Address existingAddress = addresses.get(0);

                if (request.getAddress().getStreet() != null)
                    existingAddress.setStreet(request.getAddress().getStreet());
                if (request.getAddress().getCity() != null)
                    existingAddress.setCity(request.getAddress().getCity());
                if (request.getAddress().getState() != null)
                    existingAddress.setState(request.getAddress().getState());
                if (request.getAddress().getZipCode() != null)
                    existingAddress.setZipCode(request.getAddress().getZipCode());
                if (request.getAddress().getCountry() != null)
                    existingAddress.setCountry(request.getAddress().getCountry());

                addressRepository.save(existingAddress);
            } else {
                Address newAddress = new Address();
                newAddress.setStreet(request.getAddress().getStreet());
                newAddress.setCity(request.getAddress().getCity());
                newAddress.setState(request.getAddress().getState());
                newAddress.setZipCode(request.getAddress().getZipCode());
                newAddress.setCountry(request.getAddress().getCountry());
                newAddress.setEntityId(daycare.getId());
                newAddress.setEntityType("DAYCARE");
                addressRepository.save(newAddress);
            }
        }

        daycare.setUpdatedAt(LocalDateTime.now());
        daycare.setUpdatedBy(currentEmail);

        Daycare updated = daycareRepository.save(daycare);
        return toResponse(updated);
    }


    @Override
    public DaycareResponse getDaycareById(Long id) {
        Daycare daycare = daycareRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Daycare not found"));
        return toResponse(daycare);
    }

    @Override
    public List<DaycareResponse> getAllDaycares() {
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Current user not found"));

        if (currentUser.getRole().getName() == RoleType.SUPER_ADMIN) {
            return daycareRepository.findAll()
                    .stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
        }

        if (currentUser.getRole().getName() == RoleType.DAYCARE_ADMIN) {
            return daycareRepository.findByAdminId(currentUser.getId())
                    .stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
        }

        throw new RuntimeException("You do not have permission to list daycares");
    }

    @Override
    public void deleteDaycare(Long id) {
        if (!daycareRepository.existsById(id)) {
            throw new ResourceNotFoundException("Daycare not found");
        }
        daycareRepository.deleteById(id);
    }

    private DaycareResponse toResponse(Daycare daycare) {
        DaycareResponse response = new DaycareResponse();
        response.setId(daycare.getId());
        response.setName(daycare.getName());
        response.setLogoUrl(daycare.getLogoUrl());
        response.setPhone(daycare.getPhone());
        response.setEmail(daycare.getEmail());

        if (daycare.getAdmin() != null) {
            response.setAdminId(daycare.getAdmin().getId());
        }

        response.setCreatedAt(daycare.getCreatedAt());
        response.setUpdatedAt(daycare.getUpdatedAt());
        response.setUpdatedBy(daycare.getUpdatedBy());

        // ✅ Obtener la dirección desde la tabla tb_address
        List<Address> addresses = addressRepository.findByEntityTypeAndEntityId("DAYCARE", daycare.getId());
        if (!addresses.isEmpty()) {
            response.setAddress(addressMapper.toResponse(addresses.get(0)));
        }

        return response;
    }

}
