package com.greenbuilding.trainingbackend.service.impl;

import com.greenbuilding.trainingbackend.dto.UserRequest;
import com.greenbuilding.trainingbackend.dto.UserResponse;
import com.greenbuilding.trainingbackend.entity.AppUser;
import com.greenbuilding.trainingbackend.entity.Role;
import com.greenbuilding.trainingbackend.exception.ResourceNotFoundException;
import com.greenbuilding.trainingbackend.repository.RoleRepository;
import com.greenbuilding.trainingbackend.repository.UserRepository;
import com.greenbuilding.trainingbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Implements user CRUD rules and converts entities into response DTOs.
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse create(UserRequest request) {
        String login = request.login().trim();

        // Prevent duplicate logins before saving the new user.
        userRepository.findByLogin(login).ifPresent(user -> {
            throw new IllegalArgumentException("User already exists");
        });

        AppUser saved = userRepository.save(buildUser(new AppUser(), request, login));
        return toResponse(saved);
    }

    @Override
    public UserResponse update(Integer id, UserRequest request) {
        String login = request.login().trim();

        // Load the existing user, then make sure the new login does not collide with another account.
        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        userRepository.findByLogin(login)
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("User already exists");
                });

        return toResponse(userRepository.save(buildUser(user, request, login)));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getById(Integer id) {
        // Read one user and translate it to the API response shape.
        return userRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAll() {
        // Return every user as a list of DTOs.
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public void delete(Integer id) {
        // Delete only after confirming the user exists.
        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        userRepository.delete(user);
    }

    private AppUser buildUser(AppUser user, UserRequest request, String login) {
        // Fetch the role and then copy validated request data into the entity.
        Role role = roleRepository.findById(request.roleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + request.roleId()));

        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(role);
        return user;
    }

    private UserResponse toResponse(AppUser user) {
        // Expose only the fields needed by the API client.
        return new UserResponse(
                user.getId(),
                user.getLogin(),
                user.getRole().getId(),
                user.getRole().getName()
        );
    }
}
