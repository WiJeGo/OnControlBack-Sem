package com.oncontrol.oncontrolbackend.dashboard.application.dto;

import com.oncontrol.oncontrolbackend.appointments.application.dto.AppointmentResponse;
import com.oncontrol.oncontrolbackend.profiles.application.dto.PatientProfileResponse;
import com.oncontrol.oncontrolbackend.symptoms.domain.model.Symptom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDashboardResponse {
    
    // Doctor info
    private Long doctorId;
    private String doctorName;
    private String specialization;
    private Long organizationId;
    private String organizationName;
    
    // Statistics
    private Integer totalPatients;
    private Integer activePatients;
    private Integer totalAppointments;
    private Integer upcomingAppointments;
    private Integer completedAppointments;
    private Integer totalSymptomsReported;
    private Integer criticalSymptoms;
    
    // Patients list
    private List<PatientProfileResponse> patients;
    
    // Upcoming appointments
    private List<AppointmentResponse> upcomingAppointmentsList;
    
    // Recent symptoms across all patients
    private List<Symptom> recentSymptoms;
    
    // Statistics by patient (for filtering)
    private Map<Long, PatientStatistics> patientStatistics;
    
    // Nested class for patient statistics
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PatientStatistics {
        private Long patientId;
        private String patientName;
        private Integer totalAppointments;
        private Integer upcomingAppointments;
        private Integer symptomsLastMonth;
        private Integer criticalSymptoms;
        private String lastAppointmentDate;
        private String nextAppointmentDate;
    }
}

