package com.oncontrol.oncontrolbackend.medicalrecords.infrastructure.controller;

import com.oncontrol.oncontrolbackend.medicalrecords.application.dto.*;
import com.oncontrol.oncontrolbackend.medicalrecords.application.service.MedicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/medications")
@RequiredArgsConstructor
@Tag(name = "Medications", description = "Medication management endpoints")
public class MedicationController {

    private final MedicationService medicationService;

    @PostMapping("/doctor/{doctorProfileId}/patient/{patientProfileId}")
    @Operation(summary = "Prescribe medication", description = "Create a new medication prescription")
    public ResponseEntity<?> createMedication(
            @PathVariable Long doctorProfileId,
            @PathVariable Long patientProfileId,
            @Valid @RequestBody CreateMedicationRequest request) {
        try {
            MedicationResponse response = medicationService.createMedication(doctorProfileId, patientProfileId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/{medicationId}")
    @Operation(summary = "Get medication", description = "Get medication by ID")
    public ResponseEntity<?> getMedication(@PathVariable Long medicationId) {
        try {
            MedicationResponse response = medicationService.getMedicationById(medicationId);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/patient/{patientProfileId}")
    @Operation(summary = "Get patient medications", description = "Get all medications for a patient")
    public ResponseEntity<Map<String, Object>> getPatientMedications(@PathVariable Long patientProfileId) {
        List<MedicationResponse> medications = medicationService.getMedicationsByPatient(patientProfileId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("medications", medications);
        response.put("count", medications.size());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient/{patientProfileId}/active")
    @Operation(summary = "Get active medications", description = "Get active medications for a patient")
    public ResponseEntity<Map<String, Object>> getActiveMedications(@PathVariable Long patientProfileId) {
        List<MedicationResponse> medications = medicationService.getActiveMedicationsByPatient(patientProfileId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("medications", medications);
        response.put("count", medications.size());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/doctor/{doctorProfileId}")
    @Operation(summary = "Get doctor medications", description = "Get medications prescribed by a doctor")
    public ResponseEntity<Map<String, Object>> getDoctorMedications(@PathVariable Long doctorProfileId) {
        List<MedicationResponse> medications = medicationService.getMedicationsByDoctor(doctorProfileId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("medications", medications);
        response.put("count", medications.size());
        
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{medicationId}")
    @Operation(summary = "Update medication", description = "Update medication details")
    public ResponseEntity<?> updateMedication(
            @PathVariable Long medicationId,
            @Valid @RequestBody UpdateMedicationRequest request) {
        try {
            MedicationResponse response = medicationService.updateMedication(medicationId, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @DeleteMapping("/{medicationId}")
    @Operation(summary = "Discontinue medication", description = "Discontinue a medication")
    public ResponseEntity<?> discontinueMedication(
            @PathVariable Long medicationId,
            @RequestParam(required = false, defaultValue = "Doctor's decision") String reason) {
        try {
            MedicationResponse response = medicationService.discontinueMedication(medicationId, reason);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/patient/{patientProfileId}/upcoming-doses")
    @Operation(summary = "Get upcoming doses", description = "Get upcoming medication doses for a patient")
    public ResponseEntity<Map<String, Object>> getUpcomingDoses(
            @PathVariable Long patientProfileId,
            @RequestParam(required = false, defaultValue = "7") Integer days) {
        List<UpcomingDoseResponse> doses = medicationService.getUpcomingDoses(patientProfileId, days);
        
        Map<String, Object> response = new HashMap<>();
        response.put("doses", doses);
        response.put("count", doses.size());
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{medicationId}/mark-taken")
    @Operation(summary = "Mark dose as taken", description = "Mark a medication dose as taken")
    public ResponseEntity<?> markDoseAsTaken(
            @PathVariable Long medicationId,
            @Valid @RequestBody MarkDoseTakenRequest request) {
        try {
            MedicationResponse response = medicationService.markDoseAsTaken(medicationId, request);
            
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("medication", response);
            result.put("message", "Dose marked as taken successfully");
            
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}

