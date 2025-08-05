package com.noatechsolutions.noaguard.repository;

import com.noatechsolutions.noaguard.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {

    // Buscar todos los padres asociados a un estudiante específico
    List<Parent> findByStudentId(Long studentId);
}
