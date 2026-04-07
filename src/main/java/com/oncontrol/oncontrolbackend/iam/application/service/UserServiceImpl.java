package com.oncontrol.oncontrolbackend.iam.application.service;

import com.oncontrol.oncontrolbackend.iam.domain.model.User;
import com.oncontrol.oncontrolbackend.iam.domain.model.UserRole;
import com.oncontrol.oncontrolbackend.iam.domain.repository.UserRepository;
import com.oncontrol.oncontrolbackend.iam.domain.service.UserService;
import com.oncontrol.oncontrolbackend.shared.infrastructure.service.HashingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final HashingService hashingService;

    @Override
    public User createUser(String email, String password, String firstName, String lastName, 
                          String phone, java.time.LocalDate birthDate, String city, UserRole role) {
        
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User with email " + email + " already exists");
        }

        String hashedPassword = hashingService.hash(password);
        
        // NOTE: This method is deprecated with new architecture
        // Users are now organizations. This is kept for backward compatibility
        User user = User.builder()
                .email(email)
                .password(hashedPassword)
                .organizationName(firstName + " " + lastName)
                .phone(phone)
                .role(role != null ? role : UserRole.ORGANIZATION)
                .isActive(true)
                .isEmailVerified(false)
                .build();

        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        
        if (!hashingService.verify(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        
        if (!user.getIsActive()) {
            throw new IllegalArgumentException("User account is deactivated");
        }
        
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void verifyEmail(String email) {
        User user = findByEmail(email);
        user.setIsEmailVerified(true);
        userRepository.save(user);
    }

    @Override
    public void changePassword(Long userId, String newPassword) {
        User user = findById(userId);
        String hashedPassword = hashingService.hash(newPassword);
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    @Override
    public void deactivateUser(Long userId) {
        User user = findById(userId);
        user.setIsActive(false);
        userRepository.save(user);
    }

    @Override
    public void activateUser(Long userId) {
        User user = findById(userId);
        user.setIsActive(true);
        userRepository.save(user);
    }
}
