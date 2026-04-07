package com.oncontrol.oncontrolbackend.medicalrecords.application.service;

import com.oncontrol.oncontrolbackend.medicalrecords.application.dto.*;
import com.oncontrol.oncontrolbackend.medicalrecords.domain.model.Medication;
import com.oncontrol.oncontrolbackend.medicalrecords.domain.repository.MedicationRepository;
import com.oncontrol.oncontrolbackend.profiles.domain.model.DoctorProfile;
import com.oncontrol.oncontrolbackend.profiles.domain.model.PatientProfile;
import com.oncontrol.oncontrolbackend.profiles.domain.repository.DoctorProfileRepository;
import com.oncontrol.oncontrolbackend.profiles.domain.repository.PatientProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MedicationService {

    private final MedicationRepository medicationRepository;
    private final DoctorProfileRepository doctorProfileRepository;
    private final PatientProfileRepository patientProfileRepository;

    /**
     * Create a new medication
     */
    public MedicationResponse createMedication(Long doctorId, Long patientId, CreateMedicationRequest request) {
        log.info("Creating medication for patient {} by doctor {}", patientId, doctorId);

        DoctorProfile doctor = doctorProfileRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        PatientProfile patient = patientProfileRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        Medication medication = Medication.builder()
                .patient(patient)
                .prescribedBy(doctor)
                .medicationName(request.getMedicationName())
                .dosage(request.getDosage())
                .frequency(request.getFrequency())
                .route(request.getRoute())
                .instructions(request.getInstructions())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .nextDoseTime(request.getNextDoseTime())
                .adherencePercentage(100)
                .sideEffects(request.getSideEffects())
                .isActive(true)
                .isPrn(request.getIsPrn() != null ? request.getIsPrn() : false)
                .build();

        medication = medicationRepository.save(medication);
        log.info("Medication created successfully with ID: {}", medication.getId());

        return mapToResponse(medication);
    }

    /**
     * Get medication by ID
     */
    @Transactional(readOnly = true)
    public MedicationResponse getMedicationById(Long id) {
        Medication medication = medicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Medication not found"));
        return mapToResponse(medication);
    }

    /**
     * Get all medications for a patient
     */
    @Transactional(readOnly = true)
    public List<MedicationResponse> getMedicationsByPatient(Long patientId) {
        PatientProfile patient = patientProfileRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        
        return medicationRepository.findByPatient(patient).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get active medications for a patient
     */
    @Transactional(readOnly = true)
    public List<MedicationResponse> getActiveMedicationsByPatient(Long patientId) {
        PatientProfile patient = patientProfileRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        
        return medicationRepository.findActiveByPatientOrderByStartDateDesc(patient).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get medications prescribed by a doctor
     */
    @Transactional(readOnly = true)
    public List<MedicationResponse> getMedicationsByDoctor(Long doctorId) {
        DoctorProfile doctor = doctorProfileRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
        
        return medicationRepository.findByPrescribedBy(doctor).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Update medication
     */
    public MedicationResponse updateMedication(Long id, UpdateMedicationRequest request) {
        log.info("Updating medication {}", id);

        Medication medication = medicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Medication not found"));

        if (request.getDosage() != null) {
            medication.setDosage(request.getDosage());
        }
        if (request.getFrequency() != null) {
            medication.setFrequency(request.getFrequency());
        }
        if (request.getInstructions() != null) {
            medication.setInstructions(request.getInstructions());
        }
        if (request.getEndDate() != null) {
            medication.setEndDate(request.getEndDate());
        }
        if (request.getNextDoseTime() != null) {
            medication.setNextDoseTime(request.getNextDoseTime());
        }
        if (request.getAdherencePercentage() != null) {
            medication.setAdherencePercentage(request.getAdherencePercentage());
        }
        if (request.getSideEffects() != null) {
            medication.setSideEffects(request.getSideEffects());
        }
        if (request.getIsActive() != null) {
            medication.setIsActive(request.getIsActive());
        }

        medication = medicationRepository.save(medication);
        log.info("Medication {} updated successfully", id);

        return mapToResponse(medication);
    }

    /**
     * Discontinue medication
     */
    public MedicationResponse discontinueMedication(Long id, String reason) {
        log.info("Discontinuing medication {}", id);

        Medication medication = medicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Medication not found"));

        medication.setIsActive(false);
        medication.setEndDate(LocalDate.now());
        
        String notes = medication.getSideEffects() != null ? medication.getSideEffects() + "\n" : "";
        notes += "Discontinued: " + reason;
        medication.setSideEffects(notes);

        medication = medicationRepository.save(medication);
        return mapToResponse(medication);
    }

    /**
     * Get upcoming doses for a patient (next 7 days)
     */
    @Transactional(readOnly = true)
    public List<UpcomingDoseResponse> getUpcomingDoses(Long patientId, Integer days) {
        PatientProfile patient = patientProfileRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        List<Medication> activeMedications = medicationRepository.findActiveByPatientOrderByStartDateDesc(patient);
        List<UpcomingDoseResponse> upcomingDoses = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endDate = now.plusDays(days != null ? days : 7);

        for (Medication medication : activeMedications) {
            if (medication.getNextDoseTime() != null) {
                LocalDateTime scheduledTime = LocalDateTime.of(LocalDate.now(), medication.getNextDoseTime());
                
                if (scheduledTime.isAfter(now) && scheduledTime.isBefore(endDate)) {
                    UpcomingDoseResponse dose = UpcomingDoseResponse.builder()
                            .medicationId(medication.getId())
                            .medicationName(medication.getMedicationName())
                            .dosage(medication.getDosage())
                            .scheduledTime(scheduledTime)
                            .taken(false)
                            .instructions(medication.getInstructions())
                            .build();
                    upcomingDoses.add(dose);
                }
            }
        }

        return upcomingDoses;
    }

    /**
     * Mark dose as taken
     */
    public MedicationResponse markDoseAsTaken(Long medicationId, MarkDoseTakenRequest request) {
        log.info("Marking dose as taken for medication {}", medicationId);

        Medication medication = medicationRepository.findById(medicationId)
                .orElseThrow(() -> new IllegalArgumentException("Medication not found"));

        // Update next dose time (simple logic - can be improved)
        if (medication.getNextDoseTime() != null) {
            // Increment by a day (simplified - real logic would parse frequency)
            medication.setNextDoseTime(medication.getNextDoseTime().plusHours(24));
        }

        // Could add more sophisticated tracking here (e.g., dose history table)
        
        medication = medicationRepository.save(medication);
        return mapToResponse(medication);
    }

    // Mapping method
    private MedicationResponse mapToResponse(Medication medication) {
        String status = "ACTIVE";
        if (!medication.getIsActive()) {
            status = "DISCONTINUED";
        } else if (medication.getEndDate() != null && medication.getEndDate().isBefore(LocalDate.now())) {
            status = "COMPLETED";
        }

        return MedicationResponse.builder()
                .id(medication.getId())
                .patientId(medication.getPatient().getId())
                .patientName(medication.getPatient().getProfile().getFullName())
                .doctorId(medication.getPrescribedBy().getId()) // Changed from prescribedById
                .doctorName(medication.getPrescribedBy().getProfile().getFullName()) // Changed from prescribedByName
                .name(medication.getMedicationName()) // Changed from medicationName
                .dosage(medication.getDosage())
                .frequency(medication.getFrequency())
                .route(medication.getRoute())
                .instructions(medication.getInstructions())
                .startDate(medication.getStartDate())
                .endDate(medication.getEndDate())
                .nextDoseTime(medication.getNextDoseTime())
                .adherencePercentage(medication.getAdherencePercentage())
                .sideEffects(medication.getSideEffects())
                .isActive(medication.getIsActive())
                .isPrn(medication.getIsPrn())
                .status(status)
                .createdAt(medication.getCreatedAt())
                .updatedAt(medication.getUpdatedAt())
                .build();
    }
}

