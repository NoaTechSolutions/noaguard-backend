package com.noatechsolutions.noaguard.service;

import com.noatechsolutions.noaguard.dto.ParentRequest;
import com.noatechsolutions.noaguard.dto.ParentResponse;
import com.noatechsolutions.noaguard.dto.ParentUpdateRequest;
import com.noatechsolutions.noaguard.entity.Address;
import com.noatechsolutions.noaguard.entity.Parent;
import com.noatechsolutions.noaguard.entity.Student;
import com.noatechsolutions.noaguard.exception.ResourceNotFoundException;
import com.noatechsolutions.noaguard.mapper.AddressMapper;
import com.noatechsolutions.noaguard.mapper.ParentMapper;
import com.noatechsolutions.noaguard.repository.AddressRepository;
import com.noatechsolutions.noaguard.repository.ParentRepository;
import com.noatechsolutions.noaguard.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepository;
    private final StudentRepository studentRepository;
    private final ParentMapper parentMapper;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public ParentServiceImpl(
            ParentRepository parentRepository,
            StudentRepository studentRepository,
            ParentMapper parentMapper,
            AddressRepository addressRepository,
            AddressMapper addressMapper
    ) {
        this.parentRepository = parentRepository;
        this.studentRepository = studentRepository;
        this.parentMapper = parentMapper;
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    public ParentResponse createParent(ParentRequest request) {
        if (request.getStudentIds() == null || request.getStudentIds().isEmpty()) {
            throw new RuntimeException("At least one student ID is required");
        }

        List<Student> students = studentRepository.findAllById(request.getStudentIds());
        if (students.size() != request.getStudentIds().size()) {
            throw new RuntimeException("Some students not found");
        }

        Parent parent = parentMapper.toEntity(request, students);

        // Asignar daycare automáticamente desde el primer estudiante
        if (!students.isEmpty()) {
            parent.setDaycare(students.get(0).getDaycare());
        }

        parent.setCreatedAt(LocalDateTime.now());
        parent.setUpdatedAt(LocalDateTime.now());
        parent.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());

        Parent savedParent = parentRepository.save(parent);

        // Guardar dirección si existe
        if (request.getAddress() != null) {
            Address address = addressMapper.toEntity(request.getAddress());
            address.setEntityType("PARENT");
            address.setEntityId(savedParent.getId());
            addressRepository.save(address);
        }

        // 📌 Traer de nuevo el Parent con la dirección para la respuesta
        return toResponse(savedParent);
    }


    @Override
    public ParentResponse updateParent(Long id, ParentUpdateRequest request) {
        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found with id " + id));

        List<Student> students = null;
        if (request.getStudentIds() != null) {
            students = studentRepository.findAllById(request.getStudentIds());
            if (students.size() != request.getStudentIds().size()) {
                throw new RuntimeException("Some students not found");
            }

            // Actualizar daycare si cambian los estudiantes
            if (!students.isEmpty()) {
                parent.setDaycare(students.get(0).getDaycare());
            }
        }

        parentMapper.updateEntity(parent, request, students);
        parent.setUpdatedAt(LocalDateTime.now());
        parent.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());

        // Actualizar o crear dirección
        if (request.getAddress() != null) {
            List<Address> addresses = addressRepository.findByEntityTypeAndEntityId("PARENT", parent.getId());
            if (!addresses.isEmpty()) {
                Address existing = addresses.get(0);
                if (request.getAddress().getStreet() != null) existing.setStreet(request.getAddress().getStreet());
                if (request.getAddress().getCity() != null) existing.setCity(request.getAddress().getCity());
                if (request.getAddress().getState() != null) existing.setState(request.getAddress().getState());
                if (request.getAddress().getZipCode() != null) existing.setZipCode(request.getAddress().getZipCode());
                if (request.getAddress().getCountry() != null) existing.setCountry(request.getAddress().getCountry());
                addressRepository.save(existing);
            } else {
                Address newAddress = addressMapper.toEntity(request.getAddress());
                newAddress.setEntityId(parent.getId());
                newAddress.setEntityType("PARENT");
                addressRepository.save(newAddress);
            }
        }

        Parent updated = parentRepository.save(parent);
        return toResponse(updated);
    }

    @Override
    public ParentResponse getParentById(Long id) {
        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found with id " + id));
        return toResponse(parent);
    }

    @Override
    public List<ParentResponse> getAllParents() {
        return parentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ParentResponse> getParentsByStudentId(Long studentId) {
        return parentRepository.findByStudentId(studentId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteParent(Long id) {
        if (!parentRepository.existsById(id)) {
            throw new EntityNotFoundException("Parent not found with id " + id);
        }
        parentRepository.deleteById(id);
    }

    private ParentResponse toResponse(Parent parent) {
        ParentResponse response = parentMapper.toResponse(parent);
        List<Address> addresses = addressRepository.findByEntityTypeAndEntityId("PARENT", parent.getId());
        if (!addresses.isEmpty()) {
            response.setAddress(addressMapper.toResponse(addresses.get(0)));
        }
        return response;
    }
}
