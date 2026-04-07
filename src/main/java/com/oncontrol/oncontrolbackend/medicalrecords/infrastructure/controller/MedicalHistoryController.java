package com.oncontrol.oncontrolbackend.medicalrecords.infrastructure.controller;

import com.oncontrol.oncontrolbackend.medicalrecords.application.dto.*;
import com.oncontrol.oncontrolbackend.medicalrecords.application.service.MedicalHistoryService;
import com.oncontrol.oncontrolbackend.medicalrecords.domain.model.HistoryEntryType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/medical-history")
@RequiredArgsConstructor
@Tag(name = "Medical History", description = "Medical history management endpoints")
public class MedicalHistoryController {

    private final MedicalHistoryService medicalHistoryService;

    @PostMapping("/patient/{patientProfileId}")
    @Operation(summary = "Add history entry", description = "Add a new medical history entry")
    public ResponseEntity<?> createHistoryEntry(
            @PathVariable Long patientProfileId,
            @RequestParam(required = false) Long doctorProfileId,
            @Valid @RequestBody CreateHistoryEntryRequest request) {
        try {
            HistoryEntryResponse response = medicalHistoryService.createHistoryEntry(
                    patientProfileId, doctorProfileId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/patient/{patientProfileId}")
    @Operation(summary = "Get medical history", description = "Get complete medical history for a patient")
    public ResponseEntity<Map<String, Object>> getPatientHistory(@PathVariable Long patientProfileId) {
        List<HistoryEntryResponse> entries = medicalHistoryService.getPatientHistory(patientProfileId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("entries", entries);
        response.put("count", entries.size());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient/{patientProfileId}/type/{type}")
    @Operation(summary = "Get history by type", description = "Get medical history entries by type")
    public ResponseEntity<Map<String, Object>> getPatientHistoryByType(
            @PathVariable Long patientProfileId,
            @PathVariable HistoryEntryType type) {
        List<HistoryEntryResponse> entries = medicalHistoryService.getPatientHistoryByType(patientProfileId, type);
        
        Map<String, Object> response = new HashMap<>();
        response.put("entries", entries);
        response.put("count", entries.size());
        response.put("type", type);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient/{patientProfileId}/date-range")
    @Operation(summary = "Get history by date range", description = "Get medical history entries by date range")
    public ResponseEntity<Map<String, Object>> getPatientHistoryByDateRange(
            @PathVariable Long patientProfileId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<HistoryEntryResponse> entries = medicalHistoryService.getPatientHistoryByDateRange(
                patientProfileId, startDate, endDate);
        
        Map<String, Object> response = new HashMap<>();
        response.put("entries", entries);
        response.put("count", entries.size());
        response.put("startDate", startDate);
        response.put("endDate", endDate);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/patient/{patientProfileId}/allergies")
    @Operation(summary = "Add allergy", description = "Add a new allergy for a patient")
    public ResponseEntity<?> createAllergy(
            @PathVariable Long patientProfileId,
            @Valid @RequestBody CreateAllergyRequest request) {
        try {
            AllergyResponse response = medicalHistoryService.createAllergy(patientProfileId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/patient/{patientProfileId}/allergies")
    @Operation(summary = "Get allergies", description = "Get all allergies for a patient")
    public ResponseEntity<Map<String, Object>> getPatientAllergies(@PathVariable Long patientProfileId) {
        List<AllergyResponse> allergies = medicalHistoryService.getPatientAllergies(patientProfileId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("allergies", allergies);
        response.put("count", allergies.size());
        
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/allergies/{allergyId}")
    @Operation(summary = "Delete allergy", description = "Delete an allergy")
    public ResponseEntity<Map<String, Object>> deleteAllergy(@PathVariable Long allergyId) {
        try {
            medicalHistoryService.deleteAllergy(allergyId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Allergy deleted successfully");
            
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}

