package com.noatechsolutions.noaguard.repository;

import com.noatechsolutions.noaguard.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Aquí puedes agregar consultas personalizadas si las necesitas
}