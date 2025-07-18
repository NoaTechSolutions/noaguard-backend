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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;



@Service
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepository;
    private final StudentRepository studentRepository;
    private final DaycareRepository daycareRepository;

    public ParentServiceImpl(ParentRepository parentRepository, StudentRepository studentRepository, DaycareRepository daycareRepository) {
        this.parentRepository = parentRepository;
        this.studentRepository = studentRepository;
        this.daycareRepository = daycareRepository;
    }

    @Override
    public ParentResponse createParent(ParentRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + request.getStudentId()));

        Daycare daycare = daycareRepository.findById(request.getDaycareId())
                .orElseThrow(() -> new ResourceNotFoundException("Daycare not found with id " + request.getDaycareId()));

        Parent parent = new Parent();
        parent.setFirstName(request.getFirstName());
        parent.setLastName(request.getLastName());
        parent.setEmail(request.getEmail());
        parent.setPhone(request.getPhone());
        parent.setStudent(student);
        parent.setDaycare(daycare);
        parent.setCreatedAt(LocalDateTime.now());
        parent.setUpdatedAt(LocalDateTime.now());

        if (request.getAddress() != null) {
            parent.setAddress(toAddressEntity(request.getAddress()));
        }

        parentRepository.save(parent);
        return toResponse(parent);
    }

    @Override
    public ParentResponse updateParent(Long id, ParentUpdateRequest request) {
        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found with id " + id));

        if (request.getFirstName() != null) parent.setFirstName(request.getFirstName());
        if (request.getLastName() != null) parent.setLastName(request.getLastName());
        if (request.getEmail() != null) parent.setEmail(request.getEmail());
        if (request.getPhone() != null) parent.setPhone(request.getPhone());

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

        if (request.getAddress() != null) {
            parent.setAddress(toAddressEntity(request.getAddress()));
        }

        parent.setUpdatedAt(LocalDateTime.now());
        parentRepository.save(parent);

        return toResponse(parent);
    }

    @Override
    public ParentResponse getParentById(Long id) {
        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found with id " + id));
        return toResponse(parent);
    }

    @Override
    public List<ParentResponse> getAllParents() {
        return parentRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteParent(Long id) {
        if (!parentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Parent not found with id " + id);
        }
        parentRepository.deleteById(id);
    }

    // --- Métodos auxiliares ---

    private ParentResponse toResponse(Parent parent) {
        ParentResponse response = new ParentResponse();
        response.setId(parent.getId());
        response.setFirstName(parent.getFirstName());
        response.setLastName(parent.getLastName());
        response.setEmail(parent.getEmail());
        response.setPhone(parent.getPhone());

        if (parent.getAddress() != null) {
            response.setAddress(toAddressResponse(parent.getAddress()));
        }

        if (parent.getDaycare() != null) {
            response.setDaycareId(parent.getDaycare().getId());
            response.setDaycareName(parent.getDaycare().getName());
        }

        if (parent.getStudent() != null) {
            response.setStudentId(parent.getStudent().getId());
        }

        response.setCreatedAt(parent.getCreatedAt());
        response.setUpdatedAt(parent.getUpdatedAt());

        return response;
    }

    private Address toAddressEntity(AddressRequest request) {
        if (request == null) return null;
        Address address = new Address();
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setZipCode(request.getZipCode());
        address.setCountry(request.getCountry());
        return address;
    }

    private AddressResponse toAddressResponse(Address address) {
        if (address == null) return null;
        AddressResponse response = new AddressResponse();
        response.setStreet(address.getStreet());
        response.setCity(address.getCity());
        response.setState(address.getState());
        response.setZipCode(address.getZipCode());
        response.setCountry(address.getCountry());
        return response;
    }
}