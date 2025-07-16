package com.noatechsolutions.noaguard.repository;

import com.noatechsolutions.noaguard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("""
    SELECT u FROM User u
    JOIN u.roles r
    WHERE LOWER(u.firstName) LIKE %:search%
    OR LOWER(u.lastName) LIKE %:search%
    OR LOWER(u.email) LIKE %:search%
""")
    Page<User> findBySearch(@Param("search") String search, Pageable pageable);

    @Query("""
    SELECT u FROM User u
    JOIN u.roles r
    WHERE r.name = :role
""")
    Page<User> findByRole(@Param("role") String role, Pageable pageable);

    @Query("""
    SELECT u FROM User u
    JOIN u.roles r
    WHERE r.name = :role AND (
        LOWER(u.firstName) LIKE %:search%
        OR LOWER(u.lastName) LIKE %:search%
        OR LOWER(u.email) LIKE %:search%
    )
""")
    Page<User> findBySearchAndRole(@Param("search") String search, @Param("role") String role, Pageable pageable);

}