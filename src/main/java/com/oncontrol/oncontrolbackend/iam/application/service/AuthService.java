package com.oncontrol.oncontrolbackend.iam.application.service;

import com.oncontrol.oncontrolbackend.iam.application.dto.LoginRequest;
import com.oncontrol.oncontrolbackend.iam.application.dto.RegisterRequest;
import com.oncontrol.oncontrolbackend.iam.domain.model.User;
import com.oncontrol.oncontrolbackend.iam.domain.model.UserRole;
import com.oncontrol.oncontrolbackend.iam.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * AuthService handles authentication and organization registration
 * Organizations are the default user type
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Register a new organization
     */
    public User registerOrganization(RegisterRequest request) {
        log.info("Registering new organization with email: {}", request.getEmail());
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User organization = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .organizationName(request.getOrganizationName())
                .country(request.getCountry())
                .city(request.getCity())
                .phone(request.getPhone())
                .address(request.getAddress())
                .website(request.getWebsite())
                .licenseNumber(request.getLicenseNumber())
                .taxId(request.getTaxId())
                .description(request.getDescription())
                .role(UserRole.ORGANIZATION)
                .maxDoctors(request.getMaxDoctors() != null ? request.getMaxDoctors() : 10)
                .maxPatients(request.getMaxPatients() != null ? request.getMaxPatients() : 100)
                .isActive(true)
                .isEmailVerified(false)
                .build();

        organization = userRepository.save(organization);
        
        log.info("Organization registered successfully with ID: {}", organization.getId());
        return organization;
    }

    /**
     * Login for both organizations and profiles
     * Organizations login with their email
     * Profiles (doctors/patients) login with their profile email
     */
    public Object login(LoginRequest request) {
        log.info("Login attempt for email: {}", request.getEmail());
        
        // First try to find user (organization)
        var userOptional = userRepository.findByEmail(request.getEmail());
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                log.info("Organization login successful for email: {}", request.getEmail());
                return user;
            }
        }
        
        // If not found as user, it might be a profile (handled by ProfileService)
        throw new IllegalArgumentException("Invalid credentials");
    }
}
