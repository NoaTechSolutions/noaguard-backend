package com.noatechsolutions.noaguard.mapper;

import com.noatechsolutions.noaguard.dto.ParentRequest;
import com.noatechsolutions.noaguard.dto.ParentResponse;
import com.noatechsolutions.noaguard.dto.ParentUpdateRequest;
import com.noatechsolutions.noaguard.entity.Parent;
import com.noatechsolutions.noaguard.entity.Student;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParentMapper {

    public Parent toEntity(ParentRequest request, List<Student> students) {
        if (request == null) return null;

        Parent parent = new Parent();
        parent.setFirstName(request.getFirstName());
        parent.setMiddleName(request.getMiddleName());
        parent.setLastName(request.getLastName());
        parent.setRelationshipToStudent(request.getRelationshipToStudent());
        parent.setPhone(request.getPhone());
        parent.setWorkPhone(request.getWorkPhone());
        parent.setOccupation(request.getOccupation());
        parent.setEmail(request.getEmail());
        parent.setStudents(students);

        // La dirección ahora se maneja en ParentServiceImpl con AddressRepository
        return parent;
    }

    public void updateEntity(Parent parent, ParentUpdateRequest request, List<Student> students) {
        if (parent == null || request == null) return;

        if (request.getFirstName() != null) parent.setFirstName(request.getFirstName());
        if (request.getMiddleName() != null) parent.setMiddleName(request.getMiddleName());
        if (request.getLastName() != null) parent.setLastName(request.getLastName());
        if (request.getRelationshipToStudent() != null) parent.setRelationshipToStudent(request.getRelationshipToStudent());
        if (request.getPhone() != null) parent.setPhone(request.getPhone());
        if (request.getWorkPhone() != null) parent.setWorkPhone(request.getWorkPhone());
        if (request.getOccupation() != null) parent.setOccupation(request.getOccupation());
        if (request.getEmail() != null) parent.setEmail(request.getEmail());

        if (students != null) {
            parent.setStudents(students);
        }

        // Dirección también se actualiza desde ParentServiceImpl
    }

    public ParentResponse toResponse(Parent parent) {
        if (parent == null) return null;

        ParentResponse response = new ParentResponse();
        response.setId(parent.getId());
        response.setFirstName(parent.getFirstName());
        response.setMiddleName(parent.getMiddleName());
        response.setLastName(parent.getLastName());
        response.setRelationshipToStudent(parent.getRelationshipToStudent());
        response.setPhone(parent.getPhone());
        response.setWorkPhone(parent.getWorkPhone());
        response.setOccupation(parent.getOccupation());
        response.setEmail(parent.getEmail());

        if (parent.getStudents() != null) {
            response.setStudentIds(parent.getStudents().stream()
                    .map(Student::getId)
                    .collect(Collectors.toList()));
        }

        // La dirección se inyectará en ParentServiceImpl usando AddressRepository y AddressMapper
        response.setCreatedAt(parent.getCreatedAt());
        response.setUpdatedAt(parent.getUpdatedAt());
        response.setUpdatedBy(parent.getUpdatedBy());

        return response;
    }
}
