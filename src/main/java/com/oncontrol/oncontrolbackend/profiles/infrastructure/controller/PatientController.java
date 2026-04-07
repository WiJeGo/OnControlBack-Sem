package com.oncontrol.oncontrolbackend.profiles.infrastructure.controller;

import com.oncontrol.oncontrolbackend.appointments.application.service.AppointmentService;
import com.oncontrol.oncontrolbackend.symptoms.application.service.SymptomService;
import com.oncontrol.oncontrolbackend.symptoms.application.dto.SymptomResponse;
import com.oncontrol.oncontrolbackend.profiles.domain.model.Profile;
import com.oncontrol.oncontrolbackend.profiles.domain.repository.ProfileRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PatientController - Dashboard and aggregated data for patients
 */
@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Patients", description = "Patient dashboard endpoints")
public class PatientController {

    private final AppointmentService appointmentService;
    private final SymptomService symptomService;
    private final ProfileRepository profileRepository;

    @GetMapping("/{patientProfileId}/dashboard")
    @Operation(summary = "Get patient dashboard", description = "Get all patient data: appointments, symptoms, stats")
    public ResponseEntity<?> getPatientDashboard(@PathVariable Long patientProfileId) {
        try {
            Profile patientProfile = profileRepository.findById(patientProfileId)
                    .orElseThrow(() -> new RuntimeException("Patient profile not found"));

            // Get appointments
            var appointments = appointmentService.getAppointmentsByPatient(patientProfileId);
            
            // Get recent symptoms (last 30 days)
            List<SymptomResponse> recentSymptoms = symptomService.getRecentSymptoms(patientProfile, 30);
            
            // Get symptom stats
            Map<String, Object> symptomStats = symptomService.getSymptomStats(patientProfile);

            Map<String, Object> dashboard = new HashMap<>();
            dashboard.put("patient", Map.of(
                    "id", patientProfile.getId(),
                    "name", patientProfile.getFullName(),
                    "email", patientProfile.getEmail()
            ));
            dashboard.put("appointments", Map.of(
                    "total", appointments.size(),
                    "list", appointments
            ));
            dashboard.put("symptoms", Map.of(
                    "recent", recentSymptoms,
                    "stats", symptomStats
            ));

            return ResponseEntity.ok(dashboard);
        } catch (Exception e) {
            log.error("Error retrieving patient dashboard", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error retrieving dashboard: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/{patientProfileId}/summary")
    @Operation(summary = "Get patient summary", description = "Get quick summary of patient status")
    public ResponseEntity<?> getPatientSummary(@PathVariable Long patientProfileId) {
        try {
            Profile patientProfile = profileRepository.findById(patientProfileId)
                    .orElseThrow(() -> new RuntimeException("Patient profile not found"));

            var appointments = appointmentService.getAppointmentsByPatient(patientProfileId);
            var recentSymptoms = symptomService.getRecentSymptoms(patientProfile, 7);

            Map<String, Object> summary = new HashMap<>();
            summary.put("patientId", patientProfileId);
            summary.put("patientName", patientProfile.getFullName());
            summary.put("totalAppointments", appointments.size());
            summary.put("symptomsLastWeek", recentSymptoms.size());
            summary.put("lastAppointment", appointments.isEmpty() ? null : appointments.get(0));

            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            log.error("Error retrieving patient summary", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error retrieving summary: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}

