package com.noatechsolutions.noaguard.repository;

import com.noatechsolutions.noaguard.entity.Daycare;
import com.noatechsolutions.noaguard.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DaycareRepository extends JpaRepository<Daycare, Long> {

    List<Daycare> findByAdminId(Long adminId);

}
