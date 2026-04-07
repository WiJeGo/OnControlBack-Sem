package com.oncontrol.oncontrolbackend.appointments.application.service;

import com.oncontrol.oncontrolbackend.appointments.application.dto.CreateAppointmentRequest;
import com.oncontrol.oncontrolbackend.appointments.application.dto.AppointmentResponse;
import com.oncontrol.oncontrolbackend.appointments.domain.model.Appointment;
import com.oncontrol.oncontrolbackend.appointments.domain.model.AppointmentStatus;
import com.oncontrol.oncontrolbackend.appointments.domain.repository.AppointmentRepository;
import com.oncontrol.oncontrolbackend.profiles.domain.model.Profile;
import com.oncontrol.oncontrolbackend.profiles.domain.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ProfileRepository profileRepository;

    /**
     * Create appointment (can be called by doctor or patient)
     */
    public AppointmentResponse createAppointment(Long doctorProfileId, Long patientProfileId, CreateAppointmentRequest request) {
        log.info("Creating appointment between doctor {} and patient {}", doctorProfileId, patientProfileId);

        Profile doctorProfile = profileRepository.findById(doctorProfileId)
                .orElseThrow(() -> new RuntimeException("Doctor profile not found"));

        Profile patientProfile = profileRepository.findById(patientProfileId)
                .orElseThrow(() -> new RuntimeException("Patient profile not found"));

        Appointment appointment = Appointment.builder()
                .doctor(doctorProfile)
                .patient(patientProfile)
                .appointmentDate(request.getAppointmentDate())
                .durationMinutes(request.getDurationMinutes() != null ? request.getDurationMinutes() : 30)
                .type(request.getType())
                .status(AppointmentStatus.SCHEDULED)
                .location(request.getLocation())
                .notes(request.getNotes())
                .preparationInstructions(request.getPreparationInstructions())
                .sendReminder(request.getSendReminder() != null ? request.getSendReminder() : true)
                .build();

        appointment = appointmentRepository.save(appointment);

        log.info("Appointment created with ID: {}", appointment.getId());
        return mapToAppointmentResponse(appointment);
    }

    /**
     * Get all appointments for a doctor
     */
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByDoctor(Long doctorProfileId) {
        return appointmentRepository.findByDoctorId(doctorProfileId).stream()
                .map(this::mapToAppointmentResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get all appointments for a patient
     */
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByPatient(Long patientProfileId) {
        return appointmentRepository.findByPatientId(patientProfileId).stream()
                .map(this::mapToAppointmentResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get appointment by ID
     */
    @Transactional(readOnly = true)
    public AppointmentResponse getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        return mapToAppointmentResponse(appointment);
    }

    /**
     * Update appointment status
     */
    public AppointmentResponse updateAppointmentStatus(Long id, AppointmentStatus status, String reason) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus(status);
        
        if (status == AppointmentStatus.CANCELLED && reason != null) {
            appointment.setCancellationReason(reason);
            appointment.setCancelledAt(java.time.LocalDateTime.now());
        } else if (status == AppointmentStatus.COMPLETED) {
            appointment.setCompletedAt(java.time.LocalDateTime.now());
        }

        appointment = appointmentRepository.save(appointment);
        return mapToAppointmentResponse(appointment);
    }

    /**
     * Add follow-up notes to completed appointment
     */
    public AppointmentResponse addFollowUpNotes(Long id, String notes) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setFollowUpNotes(notes);
        appointment = appointmentRepository.save(appointment);
        return mapToAppointmentResponse(appointment);
    }

    /**
     * Reschedule an appointment to a new date
     */
    public AppointmentResponse rescheduleAppointment(Long id, java.time.LocalDateTime newAppointmentDate, String reason) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        log.info("Rescheduling appointment {} from {} to {}", id, appointment.getAppointmentDate(), newAppointmentDate);

        appointment.setAppointmentDate(newAppointmentDate);
        
        if (reason != null && !reason.isEmpty()) {
            String currentNotes = appointment.getNotes();
            String rescheduleNote = "Reprogramada: " + reason;
            appointment.setNotes(currentNotes != null ? currentNotes + "\n" + rescheduleNote : rescheduleNote);
        }

        appointment = appointmentRepository.save(appointment);
        return mapToAppointmentResponse(appointment);
    }

    private AppointmentResponse mapToAppointmentResponse(Appointment appointment) {
        Profile doctor = appointment.getDoctor();
        Profile patient = appointment.getPatient();

        return AppointmentResponse.builder()
                .id(appointment.getId())
                .doctorId(doctor.getId())
                .doctorName(doctor.getFullName())
                .patientId(patient.getId())
                .patientName(patient.getFullName())
                .appointmentDate(appointment.getAppointmentDate())
                .durationMinutes(appointment.getDurationMinutes())
                .type(appointment.getType())
                .status(appointment.getStatus())
                .location(appointment.getLocation())
                .notes(appointment.getNotes())
                .preparationInstructions(appointment.getPreparationInstructions())
                .followUpNotes(appointment.getFollowUpNotes())
                .cancellationReason(appointment.getCancellationReason())
                .build();
    }
}

