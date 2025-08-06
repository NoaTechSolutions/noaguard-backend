package com.noatechsolutions.noaguard.repository;

import com.noatechsolutions.noaguard.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParentRepository extends JpaRepository<Parent, Long> {

    // 🔍 Buscar padres asociados a un Student específico
    @Query("SELECT p FROM Parent p JOIN p.students s WHERE s.id = :studentId")
    List<Parent> findByStudentId(@Param("studentId") Long studentId);

    List<Parent> findAllByDaycareId(Long daycareId);
}
