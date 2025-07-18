package com.noatechsolutions.noaguard.service;

import com.noatechsolutions.noaguard.dto.StudentRequest;
import com.noatechsolutions.noaguard.dto.StudentResponse;
import com.noatechsolutions.noaguard.dto.StudentUpdateRequest;
import com.noatechsolutions.noaguard.entity.Daycare;
import com.noatechsolutions.noaguard.entity.Student;
import com.noatechsolutions.noaguard.entity.User;
import com.noatechsolutions.noaguard.exception.ResourceNotFoundException;
import com.noatechsolutions.noaguard.mapper.StudentMapper;
import com.noatechsolutions.noaguard.repository.DaycareRepository;
import com.noatechsolutions.noaguard.repository.StudentRepository;
import com.noatechsolutions.noaguard.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final DaycareRepository daycareRepository;
    private final UserRepository userRepository;

    public StudentServiceImpl(StudentRepository studentRepository, DaycareRepository daycareRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.daycareRepository = daycareRepository;
        this.userRepository = userRepository;
    }

    @Override
    public StudentResponse createStudent(StudentRequest request) {
        Daycare daycare = daycareRepository.findById(request.getDaycareId())
                .orElseThrow(() -> new ResourceNotFoundException("Daycare not found with id " + request.getDaycareId()));

        User teacher = null;
        if (request.getTeacherId() != null) {
            teacher = userRepository.findById(request.getTeacherId())
                    .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id " + request.getTeacherId()));
        }

        User daycareAdmin = null;
        if (request.getDaycareAdminId() != null) {
            daycareAdmin = userRepository.findById(request.getDaycareAdminId())
                    .orElseThrow(() -> new ResourceNotFoundException("Daycare admin not found with id " + request.getDaycareAdminId()));
        }

        Student student = StudentMapper.toEntity(request, daycare, teacher, daycareAdmin);
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());

        studentRepository.save(student);

        return StudentMapper.toResponse(student);
    }

    @Override
    public StudentResponse updateStudent(Long id, StudentUpdateRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));

        Daycare daycare = null;
        if (request.getDaycareId() != null) {
            daycare = daycareRepository.findById(request.getDaycareId())
                    .orElseThrow(() -> new ResourceNotFoundException("Daycare not found with id " + request.getDaycareId()));
        }

        User teacher = null;
        if (request.getTeacherId() != null) {
            teacher = userRepository.findById(request.getTeacherId())
                    .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id " + request.getTeacherId()));
        }

        User daycareAdmin = null;
        if (request.getDaycareAdminId() != null) {
            daycareAdmin = userRepository.findById(request.getDaycareAdminId())
                    .orElseThrow(() -> new ResourceNotFoundException("Daycare admin not found with id " + request.getDaycareAdminId()));
        }

        StudentMapper.updateEntity(student, request, daycare, teacher, daycareAdmin);
        student.setUpdatedAt(LocalDateTime.now());

        studentRepository.save(student);

        return StudentMapper.toResponse(student);
    }

    @Override
    public StudentResponse getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));
        return StudentMapper.toResponse(student);
    }

    @Override
    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(StudentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found with id " + id);
        }
        studentRepository.deleteById(id);
    }
}