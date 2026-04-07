package com.oncontrol.oncontrolbackend.treatments.infrastructure.controller;

import com.oncontrol.oncontrolbackend.treatments.application.dto.*;
import com.oncontrol.oncontrolbackend.treatments.application.service.TreatmentService;
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
@RequestMapping("/api/treatments")
@RequiredArgsConstructor
@Tag(name = "Treatments", description = "Treatment management endpoints")
public class TreatmentController {

    private final TreatmentService treatmentService;

    @PostMapping("/doctor/{doctorProfileId}/patient/{patientProfileId}")
    @Operation(summary = "Create treatment", description = "Create a new treatment for a patient")
    public ResponseEntity<?> createTreatment(
            @PathVariable Long doctorProfileId,
            @PathVariable Long patientProfileId,
            @Valid @RequestBody CreateTreatmentRequest request) {
        try {
            TreatmentResponse response = treatmentService.createTreatment(doctorProfileId, patientProfileId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/{treatmentId}")
    @Operation(summary = "Get treatment", description = "Get treatment by ID")
    public ResponseEntity<?> getTreatment(@PathVariable Long treatmentId) {
        try {
            TreatmentResponse response = treatmentService.getTreatmentById(treatmentId);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/doctor/{doctorProfileId}")
    @Operation(summary = "Get doctor treatments", description = "Get all treatments for a doctor")
    public ResponseEntity<Map<String, Object>> getDoctorTreatments(@PathVariable Long doctorProfileId) {
        List<TreatmentResponse> treatments = treatmentService.getTreatmentsByDoctor(doctorProfileId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("treatments", treatments);
        response.put("count", treatments.size());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient/{patientProfileId}")
    @Operation(summary = "Get patient treatments", description = "Get all treatments for a patient")
    public ResponseEntity<Map<String, Object>> getPatientTreatments(@PathVariable Long patientProfileId) {
        List<TreatmentResponse> treatments = treatmentService.getTreatmentsByPatient(patientProfileId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("treatments", treatments);
        response.put("count", treatments.size());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient/{patientProfileId}/current")
    @Operation(summary = "Get current treatment", description = "Get current active treatment for a patient")
    public ResponseEntity<?> getCurrentTreatment(@PathVariable Long patientProfileId) {
        try {
            TreatmentResponse response = treatmentService.getCurrentTreatmentByPatient(patientProfileId);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PutMapping("/{treatmentId}")
    @Operation(summary = "Update treatment", description = "Update treatment details")
    public ResponseEntity<?> updateTreatment(
            @PathVariable Long treatmentId,
            @Valid @RequestBody UpdateTreatmentRequest request) {
        try {
            TreatmentResponse response = treatmentService.updateTreatment(treatmentId, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PatchMapping("/{treatmentId}/status")
    @Operation(summary = "Update treatment status", description = "Update treatment status (ACTIVE, PAUSED, COMPLETED, SUSPENDED)")
    public ResponseEntity<?> updateTreatmentStatus(
            @PathVariable Long treatmentId,
            @Valid @RequestBody UpdateTreatmentStatusRequest request) {
        try {
            TreatmentResponse response = treatmentService.updateTreatmentStatus(treatmentId, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/{treatmentId}/sessions")
    @Operation(summary = "Register session", description = "Register a treatment session")
    public ResponseEntity<?> registerSession(
            @PathVariable Long treatmentId,
            @Valid @RequestBody CreateSessionRequest request) {
        try {
            TreatmentSessionResponse response = treatmentService.registerSession(treatmentId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/{treatmentId}/sessions")
    @Operation(summary = "Get treatment sessions", description = "Get all sessions for a treatment")
    public ResponseEntity<Map<String, Object>> getTreatmentSessions(@PathVariable Long treatmentId) {
        List<TreatmentSessionResponse> sessions = treatmentService.getSessionsByTreatment(treatmentId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("sessions", sessions);
        response.put("count", sessions.size());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient/{patientProfileId}/sessions/upcoming")
    @Operation(summary = "Get upcoming sessions", description = "Get upcoming sessions for a patient")
    public ResponseEntity<Map<String, Object>> getUpcomingSessions(@PathVariable Long patientProfileId) {
        List<TreatmentSessionResponse> sessions = treatmentService.getUpcomingSessionsByPatient(patientProfileId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("sessions", sessions);
        response.put("count", sessions.size());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/doctor/{doctorProfileId}/stats")
    @Operation(summary = "Get treatment statistics", description = "Get treatment statistics for a doctor")
    public ResponseEntity<TreatmentStatsResponse> getTreatmentStats(@PathVariable Long doctorProfileId) {
        TreatmentStatsResponse stats = treatmentService.getTreatmentStats(doctorProfileId);
        return ResponseEntity.ok(stats);
    }
}

