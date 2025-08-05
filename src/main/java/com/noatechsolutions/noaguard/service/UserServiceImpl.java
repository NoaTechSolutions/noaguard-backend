package com.noatechsolutions.noaguard.service;

import com.noatechsolutions.noaguard.dto.UserRequest;
import com.noatechsolutions.noaguard.dto.UserResponse;
import com.noatechsolutions.noaguard.dto.UserUpdateRequest;
import com.noatechsolutions.noaguard.entity.Address;
import com.noatechsolutions.noaguard.entity.Role;
import com.noatechsolutions.noaguard.entity.User;
import com.noatechsolutions.noaguard.enums.RoleType;
import com.noatechsolutions.noaguard.mapper.AddressMapper;
import com.noatechsolutions.noaguard.repository.AddressRepository;
import com.noatechsolutions.noaguard.repository.RoleRepository;
import com.noatechsolutions.noaguard.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           AddressRepository addressRepository,
                           AddressMapper addressMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }


    @Override
    public UserResponse createUser(UserRequest request) {
        // Usuario autenticado
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new EntityNotFoundException("Current user not found"));

        // Rol solicitado
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id " + request.getRoleId()));

        // Validaciones de rol
        if (currentUser.getRole().getName() == RoleType.DAYCARE_ADMIN &&
                (role.getName() == RoleType.SUPER_ADMIN || role.getName() == RoleType.DAYCARE_ADMIN)) {
            throw new RuntimeException("DAYCARE_ADMIN cannot create SUPER_ADMIN or another DAYCARE_ADMIN");
        }

        if (currentUser.getRole().getName() != RoleType.SUPER_ADMIN &&
                currentUser.getRole().getName() != RoleType.DAYCARE_ADMIN) {
            throw new RuntimeException("You do not have permission to create users");
        }

        // Crear usuario
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setNickname(request.getNickname());
        user.setActive(request.getActive() != null ? request.getActive() : true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(currentUser.getEmail());
        user.setRole(role);

        User savedUser = userRepository.save(user);

        // 👇 Guardar dirección opcional
        if (request.getAddress() != null) {
            Address address = new Address();
            address.setStreet(request.getAddress().getStreet());
            address.setCity(request.getAddress().getCity());
            address.setState(request.getAddress().getState());
            address.setZipCode(request.getAddress().getZipCode());
            address.setCountry(request.getAddress().getCountry());
            address.setEntityId(savedUser.getId());
            address.setEntityType("USER");
            addressRepository.save(address);
        }

        return toResponse(savedUser);
    }


    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));

        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPassword() != null) user.setPassword(passwordEncoder.encode(request.getPassword()));
        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        if (request.getNickname() != null) user.setNickname(request.getNickname());
        if (request.getActive() != null) user.setActive(request.getActive());
        if (request.getRoleId() != null) {
            Role role = roleRepository.findById(request.getRoleId())
                    .orElseThrow(() -> new EntityNotFoundException("Role not found with id " + request.getRoleId()));
            user.setRole(role);
        }

        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());

        // ✅ Actualización parcial de dirección
        if (request.getAddress() != null) {
            List<Address> addresses = addressRepository.findByEntityTypeAndEntityId("USER", user.getId());
            if (!addresses.isEmpty()) {
                Address existingAddress = addresses.get(0);

                if (request.getAddress().getStreet() != null)
                    existingAddress.setStreet(request.getAddress().getStreet());
                if (request.getAddress().getCity() != null)
                    existingAddress.setCity(request.getAddress().getCity());
                if (request.getAddress().getState() != null)
                    existingAddress.setState(request.getAddress().getState());
                if (request.getAddress().getZipCode() != null)
                    existingAddress.setZipCode(request.getAddress().getZipCode());
                if (request.getAddress().getCountry() != null)
                    existingAddress.setCountry(request.getAddress().getCountry());

                addressRepository.save(existingAddress);
            } else {
                Address newAddress = new Address();
                newAddress.setStreet(request.getAddress().getStreet());
                newAddress.setCity(request.getAddress().getCity());
                newAddress.setState(request.getAddress().getState());
                newAddress.setZipCode(request.getAddress().getZipCode());
                newAddress.setCountry(request.getAddress().getCountry());
                newAddress.setEntityId(user.getId());
                newAddress.setEntityType("USER");
                addressRepository.save(newAddress);
            }
        }

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
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new EntityNotFoundException("Current user not found"));

        if (currentUser.getRole().getName() == RoleType.SUPER_ADMIN) {
            // SUPER_ADMIN → lista todos
            return userRepository.findAll()
                    .stream()
                    .map(this::toResponse)
                    .toList();
        }

        if (currentUser.getRole().getName() == RoleType.DAYCARE_ADMIN) {
            // DAYCARE_ADMIN → solo TEACHER
            return userRepository.findAllByRoleName(RoleType.TEACHER)
                    .stream()
                    .map(this::toResponse)
                    .toList();
        }

        throw new RuntimeException("You do not have permission to list users");
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

        if (user.getRole() != null) {
            response.setRoleId(user.getRole().getId());
        }

        List<Address> addresses = addressRepository.findByEntityTypeAndEntityId("USER", user.getId());
        if (!addresses.isEmpty()) {
            response.setAddress(addressMapper.toResponse(addresses.get(0)));
        }

        return response;
    }


    @Override
    public UserResponse toggleUserActive(Long id) {
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new EntityNotFoundException("Current user not found"));

        User targetUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));

        // Reglas:
        // 1. DAYCARE_ADMIN no puede cambiar el estado de un SUPER_ADMIN ni de otro DAYCARE_ADMIN
        if (currentUser.getRole().getName() == RoleType.DAYCARE_ADMIN &&
                (targetUser.getRole().getName() == RoleType.SUPER_ADMIN ||
                        targetUser.getRole().getName() == RoleType.DAYCARE_ADMIN)) {
            throw new RuntimeException("DAYCARE_ADMIN cannot change status of SUPER_ADMIN or another DAYCARE_ADMIN");
        }

        // 2. Otros roles no pueden cambiar estados
        if (currentUser.getRole().getName() != RoleType.SUPER_ADMIN &&
                currentUser.getRole().getName() != RoleType.DAYCARE_ADMIN) {
            throw new RuntimeException("You do not have permission to change user status");
        }

        // Cambiar el estado
        targetUser.setActive(!targetUser.isActive());
        targetUser.setUpdatedAt(LocalDateTime.now());
        targetUser.setUpdatedBy(currentUser.getEmail());

        User updatedUser = userRepository.save(targetUser);
        return toResponse(updatedUser);
    }

}
