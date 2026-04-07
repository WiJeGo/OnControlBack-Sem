package com.oncontrol.oncontrolbackend.profiles.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Profile Model Unit Tests")
class ProfileTest {

    @Test
    void testGenerateProfileId_Doctor() {
        Profile profile = Profile.builder()
                .profileType(ProfileType.DOCTOR)
                .firstName("Williams")
                .lastName("Gongora")
                .email("doctor@test.com")
                .password("password123")
                .build();

        profile.generateProfileId();

        assertNotNull(profile.getProfileId());
        assertTrue(profile.getProfileId().startsWith("DOC-"));
    }

    @Test
    void testGenerateProfileId_Patient() {
        Profile profile = Profile.builder()
                .profileType(ProfileType.PATIENT)
                .firstName("Johan")
                .lastName("Quinonez")
                .email("patient@test.com")
                .password("password123")
                .build();

        profile.generateProfileId();

        assertNotNull(profile.getProfileId());
        assertTrue(profile.getProfileId().startsWith("PAT-"));
    }

    @Test
    void testGetFullName() {
        Profile profile = Profile.builder()
                .firstName("Williams")
                .lastName("Gongora")
                .build();

        String fullName = profile.getFullName();

        assertEquals("Williams Gongora", fullName);
    }

    @Test
    void testGenerateProfileId_AlreadyExists() {
        String existingId = "DOC-999999";
        Profile profile = Profile.builder()
                .profileId(existingId)
                .profileType(ProfileType.DOCTOR)
                .firstName("Williams")
                .lastName("Gongora")
                .email("doctor@test.com")
                .build();

        profile.generateProfileId();

        assertEquals(existingId, profile.getProfileId());
    }

}
