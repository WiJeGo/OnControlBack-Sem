package com.oncontrol.oncontrolbackend.dashboard.application.dto;

import com.oncontrol.oncontrolbackend.profiles.application.dto.DoctorProfileResponse;
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
public class OrganizationDashboardResponse {
    
    // Organization info
    private Long organizationId;
    private String organizationName;
    private String country;
    private String city;
    
    // Statistics
    private Integer totalDoctors;
    private Integer activeDoctors;
    private Integer totalPatients;
    private Integer activePatients;
    private Integer totalAppointments;
    private Integer upcomingAppointments;
    
    // Doctors list
    private List<DoctorProfileResponse> doctors;
    
    // Statistics by doctor (for filtering)
    private Map<Long, DoctorStatistics> doctorStatistics;
    
    // Nested class for doctor statistics
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DoctorStatistics {
        private Long doctorId;
        private String doctorName;
        private Integer totalPatients;
        private Integer totalAppointments;
        private Integer upcomingAppointments;
        private Double averageRating;
    }
}

