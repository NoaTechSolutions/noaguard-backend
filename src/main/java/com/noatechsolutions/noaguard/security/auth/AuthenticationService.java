package com.noatechsolutions.noaguard.security.auth;

import com.noatechsolutions.noaguard.entity.Role;
import com.noatechsolutions.noaguard.entity.User;
import com.noatechsolutions.noaguard.enums.RoleType;
import com.noatechsolutions.noaguard.exception.ResourceNotFoundException;
import com.noatechsolutions.noaguard.repository.RoleRepository;
import com.noatechsolutions.noaguard.repository.UserRepository;
import com.noatechsolutions.noaguard.security.jwt.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationService(UserRepository userRepository,
                                 RoleRepository roleRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        List<Role> roles;

        if (request.getRoles() == null || request.getRoles().isEmpty()) {
            // Asignar rol por defecto
            Role defaultRole = roleRepository.findByName(RoleType.DAYCARE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Role DAYCARE_ADMIN not found"));
            roles = List.of(defaultRole);
        } else {
            roles = request.getRoles().stream()
                    .map(roleString -> {
                        // Convertir el string a RoleType enum
                        RoleType roleEnum;
                        try {
                            roleEnum = RoleType.valueOf(roleString);
                        } catch (IllegalArgumentException e) {
                            throw new RuntimeException("Invalid role: " + roleString);
                        }

                        // Buscar el Role en la base de datos
                        return roleRepository.findByName(roleEnum)
                                .orElseThrow(() -> new RuntimeException("Role not found: " + roleString));
                    })
                    .collect(Collectors.toList());
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setNickname(request.getNickname());
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setRoles(roles);

        userRepository.save(user);

        String token = jwtService.generateToken(
                user.getEmail(),
                Map.of("roles",
                        user.getRoles().stream()
                                .map(role -> role.getName().name())
                                .collect(Collectors.toList())
                )
        );

        return new RegisterResponse(token);
    }
}