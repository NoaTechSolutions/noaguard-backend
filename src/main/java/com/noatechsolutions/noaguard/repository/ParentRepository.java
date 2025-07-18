package com.noatechsolutions.noaguard.repository;

import com.noatechsolutions.noaguard.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParentRepository extends JpaRepository<Parent, Long> {
    List<Parent> findByDaycareId(Long daycareId);
}