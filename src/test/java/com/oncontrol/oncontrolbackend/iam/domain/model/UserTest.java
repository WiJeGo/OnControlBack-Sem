package com.oncontrol.oncontrolbackend.iam.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User Model Unit Tests")
class UserTest {

    @Test
    void testGetAuthorities_Organization() {
        
        User user = User.builder()
                .email("org@test.com")
                .password("password123")
                .organizationName("Test Hospital")
                .country("PE")
                .city("New York")
                .role(UserRole.ORGANIZATION)
                .build();

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertTrue(authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ORGANIZATION")));
    }

    @Test
    void testGetUsername() {
        
        String email = "test@example.com";
        User user = User.builder()
                .email(email)
                .password("password123")
                .organizationName("Test Org")
                .country("PE")
                .city("Lima")
                .build();

        String username = user.getUsername();

        assertEquals(email, username);
    }

    @Test
    void testDefaultValues() {
       
        User user = User.builder()
                .email("test@test.com")
                .password("password")
                .organizationName("Test Org")
                .country("PE")
                .city("Lima")
                .build();

        assertEquals(UserRole.ORGANIZATION, user.getRole());
        assertTrue(user.getIsActive());
        assertFalse(user.getIsEmailVerified());
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
    }
}
