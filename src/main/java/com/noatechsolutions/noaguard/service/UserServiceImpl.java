package com.noatechsolutions.noaguard.service;

import com.noatechsolutions.noaguard.dto.UserRequest;
import com.noatechsolutions.noaguard.dto.UserResponse;
import com.noatechsolutions.noaguard.dto.UserUpdateRequest;
import com.noatechsolutions.noaguard.entity.Role;
import com.noatechsolutions.noaguard.entity.User;
import com.noatechsolutions.noaguard.exception.ResourceNotFoundException;
import com.noatechsolutions.noaguard.repository.RoleRepository;
import com.noatechsolutions.noaguard.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // Asumimos que ya está encriptada si aplica
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setNickname(request.getNickname());
        user.setActive(request.getActive() != null ? request.getActive() : true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy("system"); // Puedes reemplazar por el usuario autenticado si aplica

        if (request.getRoleIds() != null && !request.getRoleIds().isEmpty()) {
            List<Role> roles = roleRepository.findAllById(request.getRoleIds());
            user.setRoles(roles);
        }

        User savedUser = userRepository.save(user);
        return toResponse(savedUser);
    }

    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));

        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPassword() != null) user.setPassword(request.getPassword());
        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        if (request.getNickname() != null) user.setNickname(request.getNickname());
        if (request.getActive() != null) user.setActive(request.getActive());
        if (request.getRoleIds() != null) {
            List<Role> roles = roleRepository.findAllById(request.getRoleIds());
            user.setRoles(roles);
        }

        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy("system"); // Puedes reemplazar por quien lo modificó

        User updatedUser = userRepository.save(user);
        return toResponse(updatedUser);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
        return toResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id " + id);
        }
        userRepository.deleteById(id);
    }

    private UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setNickname(user.getNickname());
        response.setActive(user.isActive());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());

        // Solo los IDs de roles
        List<Long> roleIds = user.getRoles().stream()
                .map(Role::getId)
                .collect(Collectors.toList());
        response.setRoleIds(roleIds);

        return response;
    }
}