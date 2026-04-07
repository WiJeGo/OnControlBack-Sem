package com.oncontrol.oncontrolbackend.appointments.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oncontrol.oncontrolbackend.appointments.application.dto.CreateAppointmentRequest;
import com.oncontrol.oncontrolbackend.appointments.domain.model.AppointmentStatus;
import com.oncontrol.oncontrolbackend.appointments.domain.model.AppointmentType;
import com.oncontrol.oncontrolbackend.iam.domain.model.User;
import com.oncontrol.oncontrolbackend.iam.domain.model.UserRole;
import com.oncontrol.oncontrolbackend.iam.domain.repository.UserRepository;
import com.oncontrol.oncontrolbackend.profiles.domain.model.DoctorProfile;
import com.oncontrol.oncontrolbackend.profiles.domain.model.PatientProfile;
import com.oncontrol.oncontrolbackend.profiles.domain.model.Profile;
import com.oncontrol.oncontrolbackend.profiles.domain.model.ProfileType;
import com.oncontrol.oncontrolbackend.profiles.domain.repository.DoctorProfileRepository;
import com.oncontrol.oncontrolbackend.profiles.domain.repository.PatientProfileRepository;
import com.oncontrol.oncontrolbackend.profiles.domain.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test de integración para el módulo de Appointments
 * Prueba el flujo completo desde el controlador hasta la base de datos
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AppointmentIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private DoctorProfileRepository doctorProfileRepository;

    @Autowired
    private PatientProfileRepository patientProfileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Profile doctorProfile;
    private Profile patientProfile;

    @BeforeEach
    void setUp() {
        // Crear organización
        User organization = User.builder()
                .organizationName("Test Organization")
                .email("org@test.com")
                .password(passwordEncoder.encode("password123"))
                .country("Perú")
                .city("Lima")
                .role(UserRole.ORGANIZATION)
                .isActive(true)
                .build();
        organization = userRepository.save(organization);

        // Crear perfil de doctor
        doctorProfile = Profile.builder()
                .user(organization)
                .profileType(ProfileType.DOCTOR)
                .email("doctor@test.com")
                .password(passwordEncoder.encode("password123"))
                .firstName("Dr. Juan")
                .lastName("Pérez")
                .phone("987654321")
                .birthDate(LocalDate.of(1980, 1, 15))
                .city("Lima")
                .address("Av. Test 123")
                .isActive(true)
                .build();
        doctorProfile = profileRepository.save(doctorProfile);

        // Crear perfil específico de doctor
        DoctorProfile doctorSpecificProfile = DoctorProfile.builder()
                .profile(doctorProfile)
                .organization(organization)
                .specialization("Oncología")
                .licenseNumber("MED-12345")
                .yearsOfExperience(15)
                .hospitalAffiliation("Hospital Nacional")
                .consultationFee(new BigDecimal("150.00"))
                .bio("Especialista en oncología con 15 años de experiencia")
                .isAvailable(true)
                .rating(4.5)
                .totalReviews(50)
                .build();
        doctorProfileRepository.save(doctorSpecificProfile);

        // Crear perfil de paciente
        patientProfile = Profile.builder()
                .user(organization)
                .profileType(ProfileType.PATIENT)
                .email("patient@test.com")
                .password(passwordEncoder.encode("password123"))
                .firstName("María")
                .lastName("González")
                .phone("912345678")
                .birthDate(LocalDate.of(1990, 5, 20))
                .city("Lima")
                .address("Jr. Test 456")
                .isActive(true)
                .build();
        patientProfile = profileRepository.save(patientProfile);

        // Crear perfil específico de paciente
        PatientProfile patientSpecificProfile = PatientProfile.builder()
                .profile(patientProfile)
                .doctorProfile(doctorSpecificProfile)
                .bloodType("O+")
                .emergencyContactName("Juan González")
                .emergencyContactPhone("987654321")
                .insuranceProvider("Seguro Test")
                .insuranceNumber("INS-12345")
                .build();
        patientProfileRepository.save(patientSpecificProfile);
    }

    @Test
    @DisplayName("Debe crear una cita exitosamente")
    void testCreateAppointment_Success() throws Exception {
        // Preparar datos
        CreateAppointmentRequest request = CreateAppointmentRequest.builder()
                .appointmentDate(LocalDateTime.now().plusDays(7))
                .durationMinutes(30)
                .type(AppointmentType.PRIMERA_CONSULTA)
                .location("Consultorio 101")
                .notes("Primera consulta de control")
                .preparationInstructions("Traer exámenes previos")
                .sendReminder(true)
                .build();

        // Ejecutar y verificar
        mockMvc.perform(post("/api/appointments/doctor/{doctorId}/patient/{patientId}",
                        doctorProfile.getId(), patientProfile.getId())
                        .with(user(doctorProfile))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Appointment created successfully"))
                .andExpect(jsonPath("$.appointment.id").exists())
                .andExpect(jsonPath("$.appointment.type").value("PRIMERA_CONSULTA"))
                .andExpect(jsonPath("$.appointment.status").value("SCHEDULED"))
                .andExpect(jsonPath("$.appointment.location").value("Consultorio 101"))
                .andExpect(jsonPath("$.appointment.durationMinutes").value(30));
    }

    @Test
    @DisplayName("Debe obtener todas las citas de un doctor")
    void testGetDoctorAppointments_Success() throws Exception {
        // Crear citas de prueba
        createTestAppointment(LocalDateTime.now().plusDays(1), AppointmentType.PRIMERA_CONSULTA);
        createTestAppointment(LocalDateTime.now().plusDays(2), AppointmentType.CONSULTA_SEGUIMIENTO);

        // Ejecutar y verificar
        mockMvc.perform(get("/api/appointments/doctor/{doctorId}", doctorProfile.getId())
                        .with(user(doctorProfile)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appointments").isArray())
                .andExpect(jsonPath("$.appointments", hasSize(2)))
                .andExpect(jsonPath("$.count").value(2));
    }

    @Test
    @DisplayName("Debe obtener todas las citas de un paciente")
    void testGetPatientAppointments_Success() throws Exception {
        // Crear citas de prueba
        createTestAppointment(LocalDateTime.now().plusDays(1), AppointmentType.PRIMERA_CONSULTA);
        createTestAppointment(LocalDateTime.now().plusDays(3), AppointmentType.REVISION_TRATAMIENTO);

        // Ejecutar y verificar
        mockMvc.perform(get("/api/appointments/patient/{patientId}", patientProfile.getId())
                        .with(user(patientProfile)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appointments").isArray())
                .andExpect(jsonPath("$.appointments", hasSize(2)))
                .andExpect(jsonPath("$.count").value(2));
    }

    @Test
    @DisplayName("Debe obtener una cita por su ID")
    void testGetAppointmentById_Success() throws Exception {
        // Crear cita de prueba
        Long appointmentId = createTestAppointment(
                LocalDateTime.now().plusDays(5), 
                AppointmentType.CONSULTA_SEGUIMIENTO
        );

        // Ejecutar y verificar
        mockMvc.perform(get("/api/appointments/{id}", appointmentId)
                        .with(user(doctorProfile)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(appointmentId))
                .andExpect(jsonPath("$.type").value("CONSULTA_SEGUIMIENTO"))
                .andExpect(jsonPath("$.status").value("SCHEDULED"));
    }

    @Test
    @DisplayName("Debe actualizar el estado de una cita")
    void testUpdateAppointmentStatus_Success() throws Exception {
        // Crear cita de prueba
        Long appointmentId = createTestAppointment(
                LocalDateTime.now().plusDays(1), 
                AppointmentType.PRIMERA_CONSULTA
        );

        // Ejecutar y verificar
        mockMvc.perform(patch("/api/appointments/{id}/status", appointmentId)
                        .with(user(doctorProfile))
                        .param("status", AppointmentStatus.CONFIRMED.name()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Appointment status updated successfully"))
                .andExpect(jsonPath("$.appointment.id").value(appointmentId))
                .andExpect(jsonPath("$.appointment.status").value("CONFIRMED"));
    }

    @Test
    @DisplayName("Debe cancelar una cita con razón")
    void testCancelAppointment_WithReason() throws Exception {
        // Crear cita de prueba
        Long appointmentId = createTestAppointment(
                LocalDateTime.now().plusDays(2), 
                AppointmentType.PRIMERA_CONSULTA
        );

        // Ejecutar y verificar
        mockMvc.perform(patch("/api/appointments/{id}/status", appointmentId)
                        .with(user(doctorProfile))
                        .param("status", AppointmentStatus.CANCELLED.name())
                        .param("reason", "El paciente solicitó reprogramar"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Appointment status updated successfully"))
                .andExpect(jsonPath("$.appointment.status").value("CANCELLED"));
    }

    @Test
    @DisplayName("Debe agregar notas de seguimiento a una cita completada")
    void testAddFollowUpNotes_Success() throws Exception {
        // Crear y completar cita de prueba
        Long appointmentId = createTestAppointment(
                LocalDateTime.now().minusDays(1), 
                AppointmentType.PRIMERA_CONSULTA
        );

        // Completar la cita primero
        mockMvc.perform(patch("/api/appointments/{id}/status", appointmentId)
                        .with(user(doctorProfile))
                        .param("status", AppointmentStatus.COMPLETED.name()))
                .andExpect(status().isOk());

        // Agregar notas de seguimiento
        String notesJson = objectMapper.writeValueAsString(
                java.util.Map.of("notes", "El paciente presenta mejoría significativa")
        );

        mockMvc.perform(patch("/api/appointments/{id}/follow-up", appointmentId)
                        .with(user(doctorProfile))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(notesJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Follow-up notes added successfully"))
                .andExpect(jsonPath("$.appointment.id").value(appointmentId));
    }

    @Test
    @DisplayName("Debe fallar al crear una cita sin fecha")
    void testCreateAppointment_MissingDate() throws Exception {
        // Preparar datos inválidos (sin fecha)
        CreateAppointmentRequest request = CreateAppointmentRequest.builder()
                .durationMinutes(30)
                .type(AppointmentType.PRIMERA_CONSULTA)
                .location("Consultorio 101")
                .build();

        // Ejecutar y verificar que falla la validación
        mockMvc.perform(post("/api/appointments/doctor/{doctorId}/patient/{patientId}",
                        doctorProfile.getId(), patientProfile.getId())
                        .with(user(doctorProfile))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Debe fallar al obtener una cita con ID inexistente")
    void testGetAppointmentById_NotFound() throws Exception {
        Long nonExistentId = 99999L;

        mockMvc.perform(get("/api/appointments/{id}", nonExistentId)
                        .with(user(doctorProfile)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    /**
     * Método auxiliar para crear una cita de prueba
     */
    private Long createTestAppointment(LocalDateTime appointmentDate, AppointmentType type) throws Exception {
        CreateAppointmentRequest request = CreateAppointmentRequest.builder()
                .appointmentDate(appointmentDate)
                .durationMinutes(30)
                .type(type)
                .location("Consultorio Test")
                .notes("Cita de prueba")
                .sendReminder(true)
                .build();

        String response = mockMvc.perform(post("/api/appointments/doctor/{doctorId}/patient/{patientId}",
                        doctorProfile.getId(), patientProfile.getId())
                        .with(user(doctorProfile))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var jsonResponse = objectMapper.readTree(response);
        return jsonResponse.get("appointment").get("id").asLong();
    }
}
