package com.noatechsolutions.noaguard.mapper;

import com.noatechsolutions.noaguard.dto.ParentRequest;
import com.noatechsolutions.noaguard.dto.ParentResponse;
import com.noatechsolutions.noaguard.entity.Parent;
import com.noatechsolutions.noaguard.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class ParentMapper {

    public Parent toEntity(ParentRequest request, Student student) {
        Parent parent = new Parent();
        parent.setFirstName(request.getFirstName());
        parent.setLastName(request.getLastName());
        parent.setEmail(request.getEmail());
        parent.setPhone(request.getPhone());
        parent.setStudent(student);
        return parent;
    }

    public ParentResponse toResponse(Parent parent) {
        ParentResponse response = new ParentResponse();
        response.setId(parent.getId());
        response.setFirstName(parent.getFirstName());
        response.setLastName(parent.getLastName());
        response.setEmail(parent.getEmail());
        response.setPhone(parent.getPhone());
        response.setStudentId(parent.getStudent() != null ? parent.getStudent().getId() : null);
        response.setCreatedAt(parent.getCreatedAt());
        response.setUpdatedAt(parent.getUpdatedAt());
        response.setUpdatedBy(parent.getUpdatedBy()); // ✅ nuevo
        return response;
    }
}
