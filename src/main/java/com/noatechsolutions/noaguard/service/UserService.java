package com.noatechsolutions.noaguard.service;

import com.noatechsolutions.noaguard.dto.UserRequest;
import com.noatechsolutions.noaguard.dto.UserResponse;
import com.noatechsolutions.noaguard.dto.UserUpdateRequest;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest request);

    UserResponse updateUser(Long id, UserUpdateRequest request);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    void deleteUser(Long id);
}
