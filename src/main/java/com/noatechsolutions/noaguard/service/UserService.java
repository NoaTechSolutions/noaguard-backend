package com.noatechsolutions.noaguard.service;

import com.noatechsolutions.noaguard.dto.UserRequest;
import com.noatechsolutions.noaguard.dto.UserResponse;
import com.noatechsolutions.noaguard.dto.UserUpdateRequest;
import com.noatechsolutions.noaguard.enums.RoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;



public interface UserService {
    UserResponse createUser(UserRequest request);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    UserResponse updateUser(Long userId, UserUpdateRequest request);

    void deleteUser(Long id);

    Page<UserResponse> getUsers(String search, RoleType role, Pageable pageable);

    UserResponse toggleUserActive(Long userId);
}
