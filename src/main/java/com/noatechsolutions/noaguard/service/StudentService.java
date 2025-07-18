package com.noatechsolutions.noaguard.service;

import com.noatechsolutions.noaguard.dto.StudentRequest;
import com.noatechsolutions.noaguard.dto.StudentResponse;
import com.noatechsolutions.noaguard.dto.StudentUpdateRequest;

import java.util.List;

public interface StudentService {
    StudentResponse createStudent(StudentRequest request);
    StudentResponse getStudentById(Long id);
    List<StudentResponse> getAllStudents();
    StudentResponse updateStudent(Long id, StudentUpdateRequest request);
    void deleteStudent(Long id);
}
