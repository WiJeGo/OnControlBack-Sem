package com.oncontrol.oncontrolbackend.dashboard.application.dto;

import com.oncontrol.oncontrolbackend.appointments.application.dto.AppointmentResponse;
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
public class PatientDashboardResponse {
    
    // Patient info
    private Long patientId;
    private String patientName;
    private String email;
    private String bloodType;
    private String cancerType;
    private String cancerStage;
    
    // Doctor info
    private Long doctorId;
    private String doctorName;
    private String doctorSpecialization;
    
    // Organization info
    private String organizationName;
    
    // Appointments statistics
    private Integer totalAppointments;
    private Integer upcomingAppointments;
    private Integer completedAppointments;
    private List<AppointmentResponse> upcomingAppointmentsList;
    private AppointmentResponse nextAppointment;
    
    // Symptoms statistics
    private Integer totalSymptoms;
    private Integer symptomsLastWeek;
    private Integer symptomsLastMonth;
    private Integer criticalSymptoms;
    private List<Symptom> recentSymptoms;
    
    // Symptom severity breakdown
    private Map<String, Integer> symptomsBySeverity;
    
    // Treatment info
    private String treatmentStatus;
    private String diagnosisDate;
}

