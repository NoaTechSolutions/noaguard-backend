package com.noatechsolutions.noaguard.service;

import com.noatechsolutions.noaguard.dto.ParentRequest;
import com.noatechsolutions.noaguard.dto.ParentResponse;
import com.noatechsolutions.noaguard.dto.ParentUpdateRequest;
import com.noatechsolutions.noaguard.entity.Parent;
import com.noatechsolutions.noaguard.entity.Student;
import com.noatechsolutions.noaguard.exception.ResourceNotFoundException;
import com.noatechsolutions.noaguard.mapper.ParentMapper;
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

    public ParentServiceImpl(ParentRepository parentRepository,
                             StudentRepository studentRepository,
                             ParentMapper parentMapper) {
        this.parentRepository = parentRepository;
        this.studentRepository = studentRepository;
        this.parentMapper = parentMapper;
    }

    @Override
    public ParentResponse createParent(ParentRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + request.getStudentId()));

        Parent parent = parentMapper.toEntity(request, student);
        parent.setCreatedAt(LocalDateTime.now());
        parent.setUpdatedAt(LocalDateTime.now());
        parent.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());

        Parent saved = parentRepository.save(parent);
        return parentMapper.toResponse(saved);
    }

    @Override
    public ParentResponse updateParent(Long id, ParentUpdateRequest request) {
        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found with id " + id));

        if (request.getFirstName() != null) parent.setFirstName(request.getFirstName());
        if (request.getLastName() != null) parent.setLastName(request.getLastName());
        if (request.getPhone() != null) parent.setPhone(request.getPhone());
        if (request.getEmail() != null) parent.setEmail(request.getEmail());

        if (request.getStudentId() != null) {
            Student student = studentRepository.findById(request.getStudentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + request.getStudentId()));
            parent.setStudent(student);
        }

        parent.setUpdatedAt(LocalDateTime.now());
        parent.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());

        Parent updated = parentRepository.save(parent);
        return parentMapper.toResponse(updated);
    }

    @Override
    public ParentResponse getParentById(Long id) {
        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found with id " + id));
        return parentMapper.toResponse(parent);
    }

    @Override
    public List<ParentResponse> getAllParents() {
        return parentRepository.findAll()
                .stream()
                .map(parentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ParentResponse> getParentsByStudentId(Long studentId) {
        return parentRepository.findByStudentId(studentId)
                .stream()
                .map(parentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteParent(Long id) {
        if (!parentRepository.existsById(id)) {
            throw new EntityNotFoundException("Parent not found with id " + id);
        }
        parentRepository.deleteById(id);
    }
}
