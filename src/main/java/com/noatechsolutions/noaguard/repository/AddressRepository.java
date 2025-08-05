package com.noatechsolutions.noaguard.repository;

import com.noatechsolutions.noaguard.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByEntityTypeAndEntityId(String entityType, Long entityId);
}