package com.noatechsolutions.noaguard.mapper;

import com.noatechsolutions.noaguard.dto.StudentRequest;
import com.noatechsolutions.noaguard.dto.StudentResponse;
import com.noatechsolutions.noaguard.dto.StudentUpdateRequest;
import com.noatechsolutions.noaguard.entity.Daycare;
import com.noatechsolutions.noaguard.entity.Student;
import com.noatechsolutions.noaguard.entity.User;
import org.springframework.stereotype.Component;

public class StudentMapper {

    public static Student toEntity(StudentRequest request, Daycare daycare, User teacher, User daycareAdmin) {
        if (request == null) return null;

        Student student = new Student();
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setNickname(request.getNickname());
        student.setBirthdate(request.getBirthdate());
        student.setAddress(AddressMapper.toEntity(request.getAddress()));
        student.setDaycare(daycare);
        student.setTeacher(teacher);
        student.setDaycareAdmin(daycareAdmin);
        return student;
    }

    public static void updateEntity(Student student, StudentUpdateRequest request, Daycare daycare, User teacher, User daycareAdmin) {
        if (request == null || student == null) return;

        if (request.getFirstName() != null) student.setFirstName(request.getFirstName());
        if (request.getLastName() != null) student.setLastName(request.getLastName());
        if (request.getNickname() != null) student.setNickname(request.getNickname());
        if (request.getBirthdate() != null) student.setBirthdate(request.getBirthdate());
        if (request.getAddress() != null) student.setAddress(AddressMapper.toEntity(request.getAddress()));
        if (daycare != null) student.setDaycare(daycare);
        if (teacher != null) student.setTeacher(teacher);
        if (daycareAdmin != null) student.setDaycareAdmin(daycareAdmin);
    }

    public static StudentResponse toResponse(Student student) {
        if (student == null) return null;

        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setFirstName(student.getFirstName());
        response.setLastName(student.getLastName());
        response.setNickname(student.getNickname());
        response.setBirthdate(student.getBirthdate());
        response.setAddress(AddressMapper.toResponse(student.getAddress()));

        response.setDaycareId(student.getDaycare() != null ? student.getDaycare().getId() : null);
        response.setTeacherId(student.getTeacher() != null ? student.getTeacher().getId() : null);
        response.setDaycareAdminId(student.getDaycareAdmin() != null ? student.getDaycareAdmin().getId() : null);

        response.setCreatedAt(student.getCreatedAt());
        response.setUpdatedAt(student.getUpdatedAt());
        response.setUpdatedBy(student.getUpdatedBy());

        return response;
    }
}