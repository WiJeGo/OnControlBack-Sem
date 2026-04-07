package com.oncontrol.oncontrolbackend.appointments.application.service;

import com.oncontrol.oncontrolbackend.appointments.application.dto.AppointmentResponse;
import com.oncontrol.oncontrolbackend.appointments.application.dto.CreateAppointmentRequest;
import com.oncontrol.oncontrolbackend.appointments.domain.model.Appointment;
import com.oncontrol.oncontrolbackend.appointments.domain.model.AppointmentStatus;
import com.oncontrol.oncontrolbackend.appointments.domain.model.AppointmentType;
import com.oncontrol.oncontrolbackend.appointments.domain.repository.AppointmentRepository;
import com.oncontrol.oncontrolbackend.profiles.domain.model.Profile;
import com.oncontrol.oncontrolbackend.profiles.domain.model.ProfileType;
import com.oncontrol.oncontrolbackend.profiles.domain.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AppointmentService Unit Tests")
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    private Profile doctorProfile;
    private Profile patientProfile;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        doctorProfile = Profile.builder()
                .id(1L)
                .profileId("DOC-123")
                .profileType(ProfileType.DOCTOR)
                .firstName("Williams")
                .lastName("Gongora")
                .email("doctor@test.com")
                .build();

        patientProfile = Profile.builder()
                .id(2L)
                .profileId("PAT-456")
                .profileType(ProfileType.PATIENT)
                .firstName("Johan")
                .lastName("Quinonez")
                .email("patient@test.com")
                .build();

        appointment = Appointment.builder()
                .id(1L)
                .doctor(doctorProfile)
                .patient(patientProfile)
                .appointmentDate(LocalDateTime.now().plusDays(1))
                .durationMinutes(30)
                .type(AppointmentType.PRIMERA_CONSULTA)
                .status(AppointmentStatus.SCHEDULED)
                .build();
    }

    @Test
    void testCreateAppointment_Success() {
        // Arrange
        CreateAppointmentRequest request = CreateAppointmentRequest.builder()
                .appointmentDate(LocalDateTime.now().plusDays(1))
                .durationMinutes(30)
                .type(AppointmentType.PRIMERA_CONSULTA)
                .location("Room 420")
                .notes("Regular checkup")
                .build();

        when(profileRepository.findById(1L)).thenReturn(Optional.of(doctorProfile));
        when(profileRepository.findById(2L)).thenReturn(Optional.of(patientProfile));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        AppointmentResponse response = appointmentService.createAppointment(1L, 2L, request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Williams Gongora", response.getDoctorName());
        assertEquals("Johan Quinonez", response.getPatientName());
        assertEquals(AppointmentStatus.SCHEDULED, response.getStatus());
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    void testCreateAppointment_DoctorNotFound() {
        CreateAppointmentRequest request = CreateAppointmentRequest.builder()
                .appointmentDate(LocalDateTime.now().plusDays(1))
                .type(AppointmentType.CONSULTA_GENERAL)
                .build();

        when(profileRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                appointmentService.createAppointment(1L, 2L, request)
        );
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }

    @Test
    void testGetAppointmentsByDoctor_Success() {
        List<Appointment> appointments = Collections.singletonList(appointment);
        when(appointmentRepository.findByDoctorId(1L)).thenReturn(appointments);

        List<AppointmentResponse> responses = appointmentService.getAppointmentsByDoctor(1L);

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("Williams Gongora", responses.getFirst().getDoctorName());
        verify(appointmentRepository, times(1)).findByDoctorId(1L);
    }

    @Test
    void testUpdateAppointmentStatus_ToCancelled() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        AppointmentResponse response = appointmentService.updateAppointmentStatus(
                1L,
                AppointmentStatus.CANCELLED,
                "Patient requested cancellation"
        );

        assertNotNull(response);
        assertEquals(AppointmentStatus.CANCELLED, appointment.getStatus());
        assertEquals("Patient requested cancellation", appointment.getCancellationReason());
        assertNotNull(appointment.getCancelledAt());
        verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    void testAddFollowUpNotes_Success() {
        String followUpNotes = "Patient should return in 2 weeks for follow-up";
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        AppointmentResponse response = appointmentService.addFollowUpNotes(1L, followUpNotes);

        assertNotNull(response);
        assertEquals(followUpNotes, appointment.getFollowUpNotes());
        verify(appointmentRepository, times(1)).save(appointment);
    }
}
