package com.noatechsolutions.noaguard.repository;

import com.noatechsolutions.noaguard.entity.Role;
import com.noatechsolutions.noaguard.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType name);
}