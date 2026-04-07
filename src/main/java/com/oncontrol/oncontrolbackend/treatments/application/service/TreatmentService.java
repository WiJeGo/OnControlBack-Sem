package com.oncontrol.oncontrolbackend.treatments.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oncontrol.oncontrolbackend.medicalrecords.domain.model.TreatmentStatus;
import com.oncontrol.oncontrolbackend.profiles.domain.model.DoctorProfile;
import com.oncontrol.oncontrolbackend.profiles.domain.model.PatientProfile;
import com.oncontrol.oncontrolbackend.profiles.domain.repository.DoctorProfileRepository;
import com.oncontrol.oncontrolbackend.profiles.domain.repository.PatientProfileRepository;
import com.oncontrol.oncontrolbackend.treatments.application.dto.*;
import com.oncontrol.oncontrolbackend.treatments.domain.model.SessionStatus;
import com.oncontrol.oncontrolbackend.treatments.domain.model.Treatment;
import com.oncontrol.oncontrolbackend.treatments.domain.model.TreatmentSession;
import com.oncontrol.oncontrolbackend.treatments.domain.model.TreatmentType;
import com.oncontrol.oncontrolbackend.treatments.domain.repository.TreatmentRepository;
import com.oncontrol.oncontrolbackend.treatments.domain.repository.TreatmentSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TreatmentService {

    private final TreatmentRepository treatmentRepository;
    private final TreatmentSessionRepository sessionRepository;
    private final DoctorProfileRepository doctorProfileRepository;
    private final PatientProfileRepository patientProfileRepository;
    private final ObjectMapper objectMapper;

    /**
     * Create a new treatment
     */
    public TreatmentResponse createTreatment(Long doctorId, Long patientId, CreateTreatmentRequest request) {
        log.info("Creating treatment for patient {} by doctor {}", patientId, doctorId);

        DoctorProfile doctor = doctorProfileRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        PatientProfile patient = patientProfileRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        Treatment treatment = Treatment.builder()
                .doctor(doctor)
                .patient(patient)
                .type(request.getType())
                .protocol(request.getProtocol())
                .currentCycle(1)
                .totalCycles(request.getTotalCycles())
                .startDate(request.getStartDate())
                .status(TreatmentStatus.ACTIVE)
                .sessionDurationMinutes(request.getSessionDurationMinutes())
                .location(request.getLocation())
                .medications(serializeList(request.getMedications()))
                .notes(request.getNotes())
                .preparationInstructions(request.getPreparationInstructions())
                .isActive(true)
                .build();

        treatment = treatmentRepository.save(treatment);
        log.info("Treatment created successfully with ID: {}", treatment.getId());

        return mapToResponse(treatment);
    }

    /**
     * Get treatment by ID
     */
    @Transactional(readOnly = true)
    public TreatmentResponse getTreatmentById(Long id) {
        Treatment treatment = treatmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Treatment not found"));
        return mapToResponse(treatment);
    }

    /**
     * Get all treatments by doctor
     */
    @Transactional(readOnly = true)
    public List<TreatmentResponse> getTreatmentsByDoctor(Long doctorId) {
        return treatmentRepository.findByDoctorIdAndIsActiveTrue(doctorId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get all treatments by patient
     */
    @Transactional(readOnly = true)
    public List<TreatmentResponse> getTreatmentsByPatient(Long patientId) {
        return treatmentRepository.findByPatientIdAndIsActiveTrue(patientId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get current active treatment for patient
     */
    @Transactional(readOnly = true)
    public TreatmentResponse getCurrentTreatmentByPatient(Long patientId) {
        List<Treatment> treatments = treatmentRepository.findCurrentTreatmentsByPatientId(patientId);
        if (treatments.isEmpty()) {
            throw new IllegalArgumentException("No active treatment found for patient");
        }
        return mapToResponse(treatments.get(0));
    }

    /**
     * Update treatment
     */
    public TreatmentResponse updateTreatment(Long id, UpdateTreatmentRequest request) {
        log.info("Updating treatment {}", id);

        Treatment treatment = treatmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Treatment not found"));

        if (request.getCurrentCycle() != null) {
            treatment.setCurrentCycle(request.getCurrentCycle());
        }
        if (request.getEndDate() != null) {
            treatment.setEndDate(request.getEndDate());
        }
        if (request.getNextSession() != null) {
            treatment.setNextSession(request.getNextSession());
        }
        if (request.getStatus() != null) {
            treatment.setStatus(request.getStatus());
        }
        if (request.getEffectiveness() != null) {
            treatment.setEffectiveness(request.getEffectiveness());
        }
        if (request.getAdherence() != null) {
            treatment.setAdherence(request.getAdherence());
        }
        if (request.getLocation() != null) {
            treatment.setLocation(request.getLocation());
        }
        if (request.getMedications() != null) {
            treatment.setMedications(serializeList(request.getMedications()));
        }
        if (request.getSideEffects() != null) {
            treatment.setSideEffects(serializeList(request.getSideEffects()));
        }
        if (request.getNotes() != null) {
            treatment.setNotes(request.getNotes());
        }
        if (request.getPreparationInstructions() != null) {
            treatment.setPreparationInstructions(request.getPreparationInstructions());
        }

        treatment = treatmentRepository.save(treatment);
        log.info("Treatment {} updated successfully", id);

        return mapToResponse(treatment);
    }

    /**
     * Update treatment status
     */
    public TreatmentResponse updateTreatmentStatus(Long id, UpdateTreatmentStatusRequest request) {
        log.info("Updating treatment {} status to {}", id, request.getStatus());

        Treatment treatment = treatmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Treatment not found"));

        treatment.setStatus(request.getStatus());

        if (request.getReason() != null) {
            String notes = treatment.getNotes() != null ? treatment.getNotes() + "\n" : "";
            notes += "Status changed to " + request.getStatus() + ": " + request.getReason();
            treatment.setNotes(notes);
        }

        treatment = treatmentRepository.save(treatment);
        return mapToResponse(treatment);
    }

    /**
     * Register a treatment session
     */
    public TreatmentSessionResponse registerSession(Long treatmentId, CreateSessionRequest request) {
        log.info("Registering session for treatment {}", treatmentId);

        Treatment treatment = treatmentRepository.findById(treatmentId)
                .orElseThrow(() -> new IllegalArgumentException("Treatment not found"));

        // Calculate session number
        Long completedSessions = sessionRepository.countByTreatmentIdAndStatus(treatmentId, SessionStatus.COMPLETED);
        int sessionNumber = completedSessions.intValue() + 1;

        TreatmentSession session = TreatmentSession.builder()
                .treatment(treatment)
                .sessionNumber(sessionNumber)
                .cycleNumber(request.getCycleNumber())
                .sessionDate(request.getSessionDate())
                .status(SessionStatus.COMPLETED)
                .durationMinutes(treatment.getSessionDurationMinutes())
                .location(treatment.getLocation())
                .medicationsAdministered(serializeList(request.getMedicationsAdministered()))
                .sideEffects(serializeList(request.getSideEffects()))
                .vitalSigns(serializeMap(request.getVitalSigns()))
                .notes(request.getNotes())
                .completedAt(LocalDateTime.now())
                .build();

        session = sessionRepository.save(session);

        // Update treatment cycle if needed
        if (request.getCycleNumber() > treatment.getCurrentCycle()) {
            treatment.setCurrentCycle(request.getCycleNumber());
            if (treatment.getCurrentCycle().equals(treatment.getTotalCycles())) {
                treatment.setStatus(TreatmentStatus.COMPLETED);
            }
            treatmentRepository.save(treatment);
        }

        // Update side effects in treatment
        if (request.getSideEffects() != null && !request.getSideEffects().isEmpty()) {
            List<String> existingSideEffects = deserializeList(treatment.getSideEffects());
            Set<String> allSideEffects = new HashSet<>(existingSideEffects);
            allSideEffects.addAll(request.getSideEffects());
            treatment.setSideEffects(serializeList(new ArrayList<>(allSideEffects)));
            treatmentRepository.save(treatment);
        }

        log.info("Session registered successfully with ID: {}", session.getId());
        return mapToSessionResponse(session);
    }

    /**
     * Get sessions for a treatment
     */
    @Transactional(readOnly = true)
    public List<TreatmentSessionResponse> getSessionsByTreatment(Long treatmentId) {
        return sessionRepository.findByTreatmentIdOrderBySessionDateDesc(treatmentId).stream()
                .map(this::mapToSessionResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get upcoming sessions for a patient
     */
    @Transactional(readOnly = true)
    public List<TreatmentSessionResponse> getUpcomingSessionsByPatient(Long patientId) {
        return sessionRepository.findUpcomingSessionsByPatientId(patientId, LocalDateTime.now()).stream()
                .map(this::mapToSessionResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get treatment statistics for doctor
     */
    @Transactional(readOnly = true)
    public TreatmentStatsResponse getTreatmentStats(Long doctorId) {
        Long active = treatmentRepository.countByDoctorIdAndStatus(doctorId, TreatmentStatus.ACTIVE);
        Long completed = treatmentRepository.countByDoctorIdAndStatus(doctorId, TreatmentStatus.COMPLETED);
        Long paused = treatmentRepository.countByDoctorIdAndStatus(doctorId, TreatmentStatus.SUSPENDED);
        Long suspended = treatmentRepository.countByDoctorIdAndStatus(doctorId, TreatmentStatus.SUSPENDED);

        // Calculate averages
        List<Treatment> allTreatments = treatmentRepository.findByDoctorIdAndIsActiveTrue(doctorId);
        BigDecimal avgEffectiveness = allTreatments.stream()
                .map(Treatment::getEffectiveness)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(Math.max(allTreatments.size(), 1)), 2, RoundingMode.HALF_UP);

        BigDecimal avgAdherence = allTreatments.stream()
                .map(Treatment::getAdherence)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(Math.max(allTreatments.size(), 1)), 2, RoundingMode.HALF_UP);

        // Count by type
        Map<String, Long> byType = new HashMap<>();
        for (TreatmentType type : TreatmentType.values()) {
            Long count = treatmentRepository.countByDoctorIdAndType(doctorId, type);
            if (count > 0) {
                byType.put(type.name(), count);
            }
        }

        return TreatmentStatsResponse.builder()
                .active(active)
                .completed(completed)
                .paused(paused)
                .suspended(suspended)
                .averageEffectiveness(avgEffectiveness)
                .averageAdherence(avgAdherence)
                .byType(byType)
                .build();
    }

    // Mapping methods
    private TreatmentResponse mapToResponse(Treatment treatment) {
        return TreatmentResponse.builder()
                .id(treatment.getId())
                .patientId(treatment.getPatient().getId())
                .patientName(treatment.getPatient().getProfile().getFullName())
                .patientProfileId(treatment.getPatient().getProfile().getProfileId())
                .doctorId(treatment.getDoctor().getId())
                .doctorName(treatment.getDoctor().getProfile().getFullName())
                .type(treatment.getType())
                .protocol(treatment.getProtocol())
                .currentCycle(treatment.getCurrentCycle())
                .totalCycles(treatment.getTotalCycles())
                .progressPercentage(treatment.getProgressPercentage())
                .startDate(treatment.getStartDate())
                .endDate(treatment.getEndDate())
                .nextSession(treatment.getNextSession())
                .status(treatment.getStatus())
                .effectiveness(treatment.getEffectiveness())
                .adherence(treatment.getAdherence())
                .sessionDurationMinutes(treatment.getSessionDurationMinutes())
                .location(treatment.getLocation())
                .medications(deserializeList(treatment.getMedications()))
                .sideEffects(deserializeList(treatment.getSideEffects()))
                .notes(treatment.getNotes())
                .preparationInstructions(treatment.getPreparationInstructions())
                .isActive(treatment.getIsActive())
                .createdAt(treatment.getCreatedAt())
                .updatedAt(treatment.getUpdatedAt())
                .build();
    }

    private TreatmentSessionResponse mapToSessionResponse(TreatmentSession session) {
        return TreatmentSessionResponse.builder()
                .id(session.getId())
                .treatmentId(session.getTreatment().getId())
                .sessionNumber(session.getSessionNumber())
                .cycleNumber(session.getCycleNumber())
                .sessionDate(session.getSessionDate())
                .status(session.getStatus())
                .durationMinutes(session.getDurationMinutes())
                .location(session.getLocation())
                .medicationsAdministered(deserializeList(session.getMedicationsAdministered()))
                .sideEffects(deserializeList(session.getSideEffects()))
                .vitalSigns(deserializeMap(session.getVitalSigns()))
                .notes(session.getNotes())
                .completedAt(session.getCompletedAt())
                .cancelledAt(session.getCancelledAt())
                .cancellationReason(session.getCancellationReason())
                .createdAt(session.getCreatedAt())
                .build();
    }

    // JSON serialization helpers
    private String serializeList(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "[]";
        }
        try {
            return objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            log.error("Error serializing list", e);
            return "[]";
        }
    }

    private List<String> deserializeList(String json) {
        if (json == null || json.isEmpty()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            log.error("Error deserializing list", e);
            return new ArrayList<>();
        }
    }

    private String serializeMap(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return "{}";
        }
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            log.error("Error serializing map", e);
            return "{}";
        }
    }

    private Map<String, Object> deserializeMap(String json) {
        if (json == null || json.isEmpty()) {
            return new HashMap<>();
        }
        try {
            return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            log.error("Error deserializing map", e);
            return new HashMap<>();
        }
    }
}

