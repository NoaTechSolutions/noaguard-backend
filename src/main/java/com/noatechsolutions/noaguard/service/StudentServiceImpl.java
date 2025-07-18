package com.noatechsolutions.noaguard.service;

import com.noatechsolutions.noaguard.dto.AddressResponse;
import com.noatechsolutions.noaguard.dto.StudentRequest;
import com.noatechsolutions.noaguard.dto.StudentResponse;
import com.noatechsolutions.noaguard.dto.StudentUpdateRequest;
import com.noatechsolutions.noaguard.entity.Address;
import com.noatechsolutions.noaguard.entity.Daycare;
import com.noatechsolutions.noaguard.entity.Student;
import com.noatechsolutions.noaguard.entity.User;
import com.noatechsolutions.noaguard.exception.ResourceNotFoundException;
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
                .orElseThrow(() -> new ResourceNotFoundException("Daycare not found"));

        User teacher = null;
        if (request.getTeacherId() != null) {
            teacher = userRepository.findById(request.getTeacherId())
                    .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
        }

        Student student = new Student();
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setNickname(request.getNickname());
        student.setBirthdate(request.getBirthdate());
        student.setAddress(new Address(request.getAddress()));
        student.setDaycare(daycare);
        student.setTeacher(teacher);
        student.setCreatedAt(LocalDateTime.now());

        student = studentRepository.save(student);

        return mapToResponse(student);
    }

    @Override
    public StudentResponse getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        return mapToResponse(student);
    }

    @Override
    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public StudentResponse updateStudent(Long id, StudentUpdateRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        if (request.getFirstName() != null) student.setFirstName(request.getFirstName());
        if (request.getLastName() != null) student.setLastName(request.getLastName());
        if (request.getNickname() != null) student.setNickname(request.getNickname());
        if (request.getBirthdate() != null) student.setBirthdate(request.getBirthdate());

        if (request.getAddress() != null) {
            student.setAddress(new Address(request.getAddress()));
        }

        if (request.getDaycareId() != null) {
            Daycare daycare = daycareRepository.findById(request.getDaycareId())
                    .orElseThrow(() -> new ResourceNotFoundException("Daycare not found"));
            student.setDaycare(daycare);
        }

        if (request.getTeacherId() != null) {
            User teacher = userRepository.findById(request.getTeacherId())
                    .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
            student.setTeacher(teacher);
        }

        student.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(studentRepository.save(student));
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        studentRepository.delete(student);
    }

    private StudentResponse mapToResponse(Student s) {
        StudentResponse res = new StudentResponse();
        res.setId(s.getId());
        res.setFirstName(s.getFirstName());
        res.setLastName(s.getLastName());
        res.setNickname(s.getNickname());
        res.setBirthdate(s.getBirthdate());
        res.setAddress(new AddressResponse(s.getAddress()));
        res.setDaycareId(s.getDaycare().getId());
        res.setTeacherId(s.getTeacher() != null ? s.getTeacher().getId() : null);
        return res;
    }
}