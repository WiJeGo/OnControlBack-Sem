package com.oncontrol.oncontrolbackend.symptoms.infrastructure.controller;

import com.oncontrol.oncontrolbackend.symptoms.application.dto.SymptomRequest;
import com.oncontrol.oncontrolbackend.symptoms.application.dto.SymptomResponse;
import com.oncontrol.oncontrolbackend.symptoms.application.service.SymptomService;
import com.oncontrol.oncontrolbackend.profiles.domain.model.Profile;
import com.oncontrol.oncontrolbackend.profiles.domain.repository.ProfileRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SymptomController - Patients report symptoms, doctors view them
 */
@RestController
@RequestMapping("/api/symptoms")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Symptoms", description = "Symptom tracking endpoints")
public class SymptomController {

    private final SymptomService symptomService;
    private final ProfileRepository profileRepository;

    @PostMapping("/patient/{patientProfileId}")
    @Operation(summary = "Report symptom", description = "Patient reports a symptom")
    public ResponseEntity<?> reportSymptom(
            @PathVariable Long patientProfileId,
            @Valid @RequestBody SymptomRequest request) {
        log.info("🔵 POST /api/symptoms/patient/{} - Request received", patientProfileId);
        log.info("🔵 Request body: {}", request);
        
        try {
            Profile patientProfile = profileRepository.findById(patientProfileId)
                    .orElseThrow(() -> new RuntimeException("Patient profile not found"));
            
            log.info("✅ Patient profile found: {}", patientProfile.getFullName());

            SymptomResponse symptom = symptomService.reportSymptom(request, patientProfile);
            log.info("✅ Symptom created successfully: {}", symptom.getId());
            
            Map<String, Object> response = new HashMap<>();
            response.put("symptom", symptom);
            response.put("message", "Symptom reported successfully");
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("❌ Error reporting symptom: {}", e.getMessage(), e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error reporting symptom: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/patient/{patientProfileId}")
    @Operation(summary = "Get patient symptoms", description = "Get all symptoms for a patient")
    public ResponseEntity<?> getPatientSymptoms(
            @PathVariable Long patientProfileId,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        try {
            Profile patientProfile = profileRepository.findById(patientProfileId)
                    .orElseThrow(() -> new RuntimeException("Patient profile not found"));

            List<SymptomResponse> symptoms = symptomService.getPatientSymptoms(patientProfile, startDate, endDate);
            
            Map<String, Object> response = new HashMap<>();
            response.put("symptoms", symptoms);
            response.put("count", symptoms.size());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error retrieving symptoms", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error retrieving symptoms: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/patient/{patientProfileId}/recent")
    @Operation(summary = "Get recent symptoms", description = "Get recent symptoms for a patient")
    public ResponseEntity<?> getRecentSymptoms(
            @PathVariable Long patientProfileId,
            @RequestParam(defaultValue = "7") int days) {
        try {
            Profile patientProfile = profileRepository.findById(patientProfileId)
                    .orElseThrow(() -> new RuntimeException("Patient profile not found"));

            List<SymptomResponse> symptoms = symptomService.getRecentSymptoms(patientProfile, days);
            
            Map<String, Object> response = new HashMap<>();
            response.put("symptoms", symptoms);
            response.put("count", symptoms.size());
            response.put("days", days);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error retrieving recent symptoms", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error retrieving symptoms: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/patient/{patientProfileId}/stats")
    @Operation(summary = "Get symptom statistics", description = "Get statistics about patient symptoms")
    public ResponseEntity<?> getSymptomStats(@PathVariable Long patientProfileId) {
        try {
            Profile patientProfile = profileRepository.findById(patientProfileId)
                    .orElseThrow(() -> new RuntimeException("Patient profile not found"));

            Map<String, Object> stats = symptomService.getSymptomStats(patientProfile);
            
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            log.error("Error retrieving symptom stats", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error retrieving stats: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
