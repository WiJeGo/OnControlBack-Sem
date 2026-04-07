package com.oncontrol.oncontrolbackend.appointments.domain.model;

import com.oncontrol.oncontrolbackend.profiles.domain.model.Profile;
import com.oncontrol.oncontrolbackend.profiles.domain.model.ProfileType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Appointment Model Unit Tests")
class AppointmentTest {

    private Profile doctorProfile;
    private Profile patientProfile;

    @BeforeEach
    void setUp() {
        doctorProfile = Profile.builder()
                .id(1L)
                .profileType(ProfileType.DOCTOR)
                .firstName("Dr. Williams")
                .lastName("Gongora")
                .email("doctor@test.com")
                .build();

        patientProfile = Profile.builder()
                .id(2L)
                .profileType(ProfileType.PATIENT)
                .firstName("Johan")
                .lastName("Quinonez")
                .email("patient@test.com")
                .build();
    }


    @Test
    void testCancellationDetails() {
        Appointment appointment = Appointment.builder()
                .doctor(doctorProfile)
                .patient(patientProfile)
                .appointmentDate(LocalDateTime.now().plusDays(1))
                .durationMinutes(30)
                .type(AppointmentType.CONSULTA_GENERAL)
                .status(AppointmentStatus.SCHEDULED)
                .build();

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointment.setCancellationReason("Patient unavailable");
        appointment.setCancelledAt(LocalDateTime.now());

        assertEquals(AppointmentStatus.CANCELLED, appointment.getStatus());
        assertEquals("Patient unavailable", appointment.getCancellationReason());
        assertNotNull(appointment.getCancelledAt());
    }

    @Test
    void testAppointmentTypes() {
        // Test PRIMERA_CONSULTA
        Appointment consultation = Appointment.builder()
                .type(AppointmentType.PRIMERA_CONSULTA)
                .build();
        assertEquals(AppointmentType.PRIMERA_CONSULTA, consultation.getType());

        // Test CONSULTA_SEGUIMIENTO
        Appointment followUp = Appointment.builder()
                .type(AppointmentType.CONSULTA_SEGUIMIENTO)
                .build();
        assertEquals(AppointmentType.CONSULTA_SEGUIMIENTO, followUp.getType());

        // Test CONSULTA_URGENCIA
        Appointment emergency = Appointment.builder()
                .type(AppointmentType.CONSULTA_URGENCIA)
                .build();
        assertEquals(AppointmentType.CONSULTA_URGENCIA, emergency.getType());
    }
}
