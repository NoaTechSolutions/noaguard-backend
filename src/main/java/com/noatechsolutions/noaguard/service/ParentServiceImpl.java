package com.noatechsolutions.noaguard.service;

import com.noatechsolutions.noaguard.dto.*;
import com.noatechsolutions.noaguard.entity.Address;
import com.noatechsolutions.noaguard.entity.Daycare;
import com.noatechsolutions.noaguard.entity.Parent;
import com.noatechsolutions.noaguard.entity.Student;
import com.noatechsolutions.noaguard.exception.ResourceNotFoundException;
import com.noatechsolutions.noaguard.repository.DaycareRepository;
import com.noatechsolutions.noaguard.repository.ParentRepository;
import com.noatechsolutions.noaguard.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepository;
    private final StudentRepository studentRepository;
    private final DaycareRepository daycareRepository;

    public ParentServiceImpl(ParentRepository parentRepository,
                             StudentRepository studentRepository,
                             DaycareRepository daycareRepository) {
        this.parentRepository = parentRepository;
        this.studentRepository = studentRepository;
        this.daycareRepository = daycareRepository;
    }

    @Override
    public ParentResponse createParent(ParentRequest request) {
        // Buscar entidades relacionadas por id
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + request.getStudentId()));

        Daycare daycare = daycareRepository.findById(request.getDaycareId())
                .orElseThrow(() -> new ResourceNotFoundException("Daycare not found with id " + request.getDaycareId()));

        Parent parent = new Parent();
        parent.setFirstName(request.getFirstName());
        parent.setLastName(request.getLastName());
        parent.setEmail(request.getEmail());
        parent.setPhone(request.getPhone());

        if (request.getAddress() != null) {
            Address address = new Address();
            address.setStreet(request.getAddress().getStreet());
            address.setCity(request.getAddress().getCity());
            address.setState(request.getAddress().getState());
            address.setZipCode(request.getAddress().getZipCode());
            address.setCountry(request.getAddress().getCountry());
            parent.setAddress(address);
        }

        parent.setDaycare(daycare);
        parent.setStudent(student);

        LocalDateTime now = LocalDateTime.now();
        parent.setCreatedAt(now);
        parent.setUpdatedAt(now);
        // parent.setUpdatedBy(...); // Asigna según contexto actual

        Parent savedParent = parentRepository.save(parent);

        return mapToParentResponse(savedParent);
    }

    @Override
    public ParentResponse getParentById(Long id) {
        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found with id " + id));
        return mapToParentResponse(parent);
    }

    @Override
    public List<ParentResponse> getAllParents() {
        return parentRepository.findAll().stream()
                .map(this::mapToParentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ParentResponse updateParent(Long id, ParentUpdateRequest request) {
        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found with id " + id));

        if (request.getFirstName() != null) parent.setFirstName(request.getFirstName());
        if (request.getLastName() != null) parent.setLastName(request.getLastName());
        if (request.getEmail() != null) parent.setEmail(request.getEmail());
        if (request.getPhone() != null) parent.setPhone(request.getPhone());

        if (request.getAddress() != null) {
            AddressRequest ar = request.getAddress();
            Address address = parent.getAddress() != null ? parent.getAddress() : new Address();
            if (ar.getStreet() != null) address.setStreet(ar.getStreet());
            if (ar.getCity() != null) address.setCity(ar.getCity());
            if (ar.getState() != null) address.setState(ar.getState());
            if (ar.getZipCode() != null) address.setZipCode(ar.getZipCode());
            if (ar.getCountry() != null) address.setCountry(ar.getCountry());
            parent.setAddress(address);
        }

        if (request.getStudentId() != null) {
            Student student = studentRepository.findById(request.getStudentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + request.getStudentId()));
            parent.setStudent(student);
        }

        if (request.getDaycareId() != null) {
            Daycare daycare = daycareRepository.findById(request.getDaycareId())
                    .orElseThrow(() -> new ResourceNotFoundException("Daycare not found with id " + request.getDaycareId()));
            parent.setDaycare(daycare);
        }

        parent.setUpdatedAt(LocalDateTime.now());
        // parent.setUpdatedBy(...); // asignar si tienes info

        Parent updatedParent = parentRepository.save(parent);

        return mapToParentResponse(updatedParent);
    }

    @Override
    public void deleteParent(Long id) {
        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found with id " + id));
        parentRepository.delete(parent);
    }

    private ParentResponse mapToParentResponse(Parent parent) {
        ParentResponse response = new ParentResponse();
        response.setId(parent.getId());
        response.setFirstName(parent.getFirstName());
        response.setLastName(parent.getLastName());
        response.setEmail(parent.getEmail());
        response.setPhone(parent.getPhone());

        if (parent.getAddress() != null) {
            Address a = parent.getAddress();
            AddressResponse ar = new AddressResponse(
                    a.getStreet(),
                    a.getCity(),
                    a.getState(),
                    a.getZipCode(),
                    a.getCountry()
            );
            response.setAddress(ar);
        }

        if (parent.getDaycare() != null) {
            response.setDaycareId(parent.getDaycare().getId());
        }

        if (parent.getStudent() != null) {
            response.setStudentId(parent.getStudent().getId());
        }

        response.setCreatedAt(parent.getCreatedAt());
        response.setUpdatedAt(parent.getUpdatedAt());
        response.setUpdatedBy(parent.getUpdatedBy());

        return response;
    }
}