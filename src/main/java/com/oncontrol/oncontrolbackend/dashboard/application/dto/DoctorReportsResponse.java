package com.oncontrol.oncontrolbackend.dashboard.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorReportsResponse {

    private PatientStatistics patients;
    private TreatmentStatistics treatments;
    private AppointmentStatistics appointments;
    private List<MonthlyData> patientsByMonth;
    private List<TypeDistribution> treatmentsByType;
    private List<DayDistribution> appointmentsByDay;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PatientStatistics {
        private Long total;
        private Long active;
        private Long followUp;
        private Long newConsultations;
        private Integer monthlyGrowth;
        private BigDecimal averageSatisfaction;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TreatmentStatistics {
        private Long active;
        private Long completed;
        private Long paused;
        private Long suspended;
        private BigDecimal averageEffectiveness;
        private BigDecimal averageAdherence;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AppointmentStatistics {
        private Long totalMonth;
        private Long completed;
        private Long cancelled;
        private Long rescheduled;
        private Integer averageDuration;
        private Integer averageOccupancy;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MonthlyData {
        private String month;
        private String monthName;
        private Long count;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TypeDistribution {
        private String type;
        private Long count;
        private Integer percentage;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DayDistribution {
        private String day;
        private String dayName;
        private Long count;
    }
}

