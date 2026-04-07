package com.oncontrol.oncontrolbackend.dashboard.infrastructure.controller;

import com.oncontrol.oncontrolbackend.dashboard.application.dto.DoctorReportsResponse;
import com.oncontrol.oncontrolbackend.dashboard.application.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Reports", description = "Reports and analytics endpoints")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/doctor/{doctorProfileId}/overview")
    @Operation(summary = "Get doctor reports", description = "Get comprehensive reports and statistics for a doctor")
    public ResponseEntity<DoctorReportsResponse> getDoctorReports(
            @PathVariable Long doctorProfileId,
            @RequestParam(required = false, defaultValue = "6") Integer months) {
        DoctorReportsResponse reports = reportService.getDoctorReports(doctorProfileId, months);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/doctor/{doctorProfileId}/patients-by-month")
    @Operation(summary = "Get patients by month", description = "Get patient count by month for charts")
    public ResponseEntity<DoctorReportsResponse> getPatientsByMonth(
            @PathVariable Long doctorProfileId,
            @RequestParam(required = false, defaultValue = "6") Integer months) {
        DoctorReportsResponse reports = reportService.getDoctorReports(doctorProfileId, months);
        
        // Return only the patients by month data
        DoctorReportsResponse response = DoctorReportsResponse.builder()
                .patientsByMonth(reports.getPatientsByMonth())
                .build();
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/doctor/{doctorProfileId}/treatments-by-type")
    @Operation(summary = "Get treatments by type", description = "Get treatment distribution by type for charts")
    public ResponseEntity<DoctorReportsResponse> getTreatmentsByType(@PathVariable Long doctorProfileId) {
        DoctorReportsResponse reports = reportService.getDoctorReports(doctorProfileId, 6);
        
        // Return only the treatments by type data
        DoctorReportsResponse response = DoctorReportsResponse.builder()
                .treatmentsByType(reports.getTreatmentsByType())
                .build();
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/doctor/{doctorProfileId}/appointments-by-day")
    @Operation(summary = "Get appointments by day", description = "Get appointment distribution by day of week for charts")
    public ResponseEntity<DoctorReportsResponse> getAppointmentsByDay(@PathVariable Long doctorProfileId) {
        DoctorReportsResponse reports = reportService.getDoctorReports(doctorProfileId, 6);
        
        // Return only the appointments by day data
        DoctorReportsResponse response = DoctorReportsResponse.builder()
                .appointmentsByDay(reports.getAppointmentsByDay())
                .build();
        
        return ResponseEntity.ok(response);
    }
}

