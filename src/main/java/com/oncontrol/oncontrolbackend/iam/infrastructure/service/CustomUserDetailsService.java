package com.oncontrol.oncontrolbackend.iam.infrastructure.service;

import com.oncontrol.oncontrolbackend.iam.domain.model.User;
import com.oncontrol.oncontrolbackend.iam.domain.service.UserService;
import com.oncontrol.oncontrolbackend.profiles.domain.model.Profile;
import com.oncontrol.oncontrolbackend.profiles.domain.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Try to find in User table (organizations) first
        try {
            User user = userService.findByEmail(email);
            return user;
        } catch (IllegalArgumentException e) {
            // If not found in User table, try Profile table (doctors/patients)
            Profile profile = profileRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User or Profile not found with email: " + email));
            return profile;
        }
    }
}
