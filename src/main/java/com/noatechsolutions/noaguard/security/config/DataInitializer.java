package com.noatechsolutions.noaguard.security.config;

import com.noatechsolutions.noaguard.entity.Role;
import com.noatechsolutions.noaguard.entity.User;
import com.noatechsolutions.noaguard.enums.RoleType;
import com.noatechsolutions.noaguard.repository.RoleRepository;
import com.noatechsolutions.noaguard.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        Role superAdminRole = roleRepository.findByName(RoleType.SUPER_ADMIN)
                .orElseThrow(() -> new RuntimeException("Role SUPER_ADMIN not found"));

        String superAdminEmail = "admin@noaguard.com";

        if (!userRepository.existsByEmail(superAdminEmail)) {
            User superAdmin = new User();
            superAdmin.setEmail(superAdminEmail);
            superAdmin.setPassword(passwordEncoder.encode("123456")); // Cambia en producción
            superAdmin.setFirstName("Super");
            superAdmin.setLastName("Admin");
            superAdmin.setActive(true);
            superAdmin.setRole(superAdminRole);
            superAdmin.setCreatedAt(LocalDateTime.now());
            superAdmin.setUpdatedAt(LocalDateTime.now());
            superAdmin.setUpdatedBy("system");

            userRepository.save(superAdmin);
            System.out.println("✅ SUPER_ADMIN created: " + superAdminEmail + " / 123456");
        }
    }
}