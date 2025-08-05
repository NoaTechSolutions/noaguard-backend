package com.noatechsolutions.noaguard.service;

import com.noatechsolutions.noaguard.dto.StudentRequest;
import com.noatechsolutions.noaguard.dto.StudentResponse;
import com.noatechsolutions.noaguard.dto.StudentUpdateRequest;
import com.noatechsolutions.noaguard.entity.Address;
import com.noatechsolutions.noaguard.entity.Daycare;
import com.noatechsolutions.noaguard.entity.Student;
import com.noatechsolutions.noaguard.entity.User;
import com.noatechsolutions.noaguard.enums.RoleType;
import com.noatechsolutions.noaguard.exception.ResourceNotFoundException;
import com.noatechsolutions.noaguard.mapper.AddressMapper;
import com.noatechsolutions.noaguard.repository.AddressRepository;
import com.noatechsolutions.noaguard.repository.DaycareRepository;
import com.noatechsolutions.noaguard.repository.StudentRepository;
import com.noatechsolutions.noaguard.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final DaycareRepository daycareRepository;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public StudentServiceImpl(StudentRepository studentRepository,
                              UserRepository userRepository,
                              DaycareRepository daycareRepository,
                              AddressRepository addressRepository,
                              AddressMapper addressMapper) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.daycareRepository = daycareRepository;
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    public StudentResponse createStudent(StudentRequest request) {
        Student student = new Student();

        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setNickname(request.getNickname());
        student.setBirthdate(request.getBirthdate());

        Daycare daycare = daycareRepository.findById(request.getDaycareId())
                .orElseThrow(() -> new ResourceNotFoundException("Daycare not found"));
        student.setDaycare(daycare);

        if (request.getTeacherId() != null) {
            User teacher = userRepository.findById(request.getTeacherId())
                    .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
            if (teacher.getRole().getName() != RoleType.TEACHER) {
                throw new RuntimeException("Assigned user is not a TEACHER");
            }
            student.setTeacher(teacher);
        }

        if (request.getDaycareAdminId() != null) {
            User admin = userRepository.findById(request.getDaycareAdminId())
                    .orElseThrow(() -> new ResourceNotFoundException("Daycare admin not found"));
            if (admin.getRole().getName() != RoleType.DAYCARE_ADMIN) {
                throw new RuntimeException("Assigned user is not a DAYCARE_ADMIN");
            }
            student.setDaycareAdmin(admin);
        }

        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
        student.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());

        Student saved = studentRepository.save(student);

        // ✅ Guardar dirección si se envía
        if (request.getAddress() != null) {
            Address address = new Address();
            address.setStreet(request.getAddress().getStreet());
            address.setCity(request.getAddress().getCity());
            address.setState(request.getAddress().getState());
            address.setZipCode(request.getAddress().getZipCode());
            address.setCountry(request.getAddress().getCountry());
            address.setEntityId(saved.getId());
            address.setEntityType("STUDENT");
            addressRepository.save(address);
        }

        return toResponse(saved);
    }

    @Override
    public StudentResponse updateStudent(Long id, StudentUpdateRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        if (request.getFirstName() != null) student.setFirstName(request.getFirstName());
        if (request.getLastName() != null) student.setLastName(request.getLastName());
        if (request.getNickname() != null) student.setNickname(request.getNickname());
        if (request.getBirthdate() != null) student.setBirthdate(request.getBirthdate());

        if (request.getDaycareId() != null) {
            Daycare daycare = daycareRepository.findById(request.getDaycareId())
                    .orElseThrow(() -> new ResourceNotFoundException("Daycare not found"));
            student.setDaycare(daycare);
        }

        if (request.getTeacherId() != null) {
            User teacher = userRepository.findById(request.getTeacherId())
                    .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
            if (teacher.getRole().getName() != RoleType.TEACHER) {
                throw new RuntimeException("Assigned user is not a TEACHER");
            }
            student.setTeacher(teacher);
        }

        if (request.getDaycareAdminId() != null) {
            User admin = userRepository.findById(request.getDaycareAdminId())
                    .orElseThrow(() -> new ResourceNotFoundException("Daycare admin not found"));
            if (admin.getRole().getName() != RoleType.DAYCARE_ADMIN) {
                throw new RuntimeException("Assigned user is not a DAYCARE_ADMIN");
            }
            student.setDaycareAdmin(admin);
        }

        student.setUpdatedAt(LocalDateTime.now());
        student.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());

        // ✅ Actualizar o crear dirección si se envía
        if (request.getAddress() != null) {
            List<Address> addresses = addressRepository.findByEntityTypeAndEntityId("STUDENT", student.getId());
            if (!addresses.isEmpty()) {
                Address existingAddress = addresses.get(0);
                if (request.getAddress().getStreet() != null) existingAddress.setStreet(request.getAddress().getStreet());
                if (request.getAddress().getCity() != null) existingAddress.setCity(request.getAddress().getCity());
                if (request.getAddress().getState() != null) existingAddress.setState(request.getAddress().getState());
                if (request.getAddress().getZipCode() != null) existingAddress.setZipCode(request.getAddress().getZipCode());
                if (request.getAddress().getCountry() != null) existingAddress.setCountry(request.getAddress().getCountry());
                addressRepository.save(existingAddress);
            } else {
                Address newAddress = new Address();
                newAddress.setStreet(request.getAddress().getStreet());
                newAddress.setCity(request.getAddress().getCity());
                newAddress.setState(request.getAddress().getState());
                newAddress.setZipCode(request.getAddress().getZipCode());
                newAddress.setCountry(request.getAddress().getCountry());
                newAddress.setEntityId(student.getId());
                newAddress.setEntityType("STUDENT");
                addressRepository.save(newAddress);
            }
        }

        Student updated = studentRepository.save(student);
        return toResponse(updated);
    }

    @Override
    public StudentResponse getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        return toResponse(student);
    }

    @Override
    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Student not found");
        }
        studentRepository.deleteById(id);
    }

    private StudentResponse toResponse(Student student) {
        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setFirstName(student.getFirstName());
        response.setLastName(student.getLastName());
        response.setNickname(student.getNickname());
        response.setBirthdate(student.getBirthdate());
        response.setDaycareId(student.getDaycare() != null ? student.getDaycare().getId() : null);
        response.setTeacherId(student.getTeacher() != null ? student.getTeacher().getId() : null);
        response.setDaycareAdminId(student.getDaycareAdmin() != null ? student.getDaycareAdmin().getId() : null);
        response.setCreatedAt(student.getCreatedAt());
        response.setUpdatedAt(student.getUpdatedAt());

        // ✅ Cargar dirección desde tb_address
        List<Address> addresses = addressRepository.findByEntityTypeAndEntityId("STUDENT", student.getId());
        if (!addresses.isEmpty()) {
            response.setAddress(addressMapper.toResponse(addresses.get(0)));
        }

        return response;
    }
}
