package com.oncontrol.oncontrolbackend.iam.domain.service;

import com.oncontrol.oncontrolbackend.iam.domain.model.User;
import com.oncontrol.oncontrolbackend.iam.domain.model.UserRole;

public interface UserService {
    
    User createUser(String email, String password, String firstName, String lastName, 
                   String phone, java.time.LocalDate birthDate, String city, UserRole role);
    
    User authenticateUser(String email, String password);
    
    User findByEmail(String email);
    
    User findById(Long id);
    
    boolean existsByEmail(String email);
    
    void verifyEmail(String email);
    
    void changePassword(Long userId, String newPassword);
    
    void deactivateUser(Long userId);
    
    void activateUser(Long userId);
}
