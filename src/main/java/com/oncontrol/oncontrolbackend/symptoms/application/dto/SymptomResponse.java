package com.oncontrol.oncontrolbackend.symptoms.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SymptomResponse {
    
    private Long id;
    private String symptomName;
    private String severity;
    private LocalDate occurrenceDate;
    private LocalTime occurrenceTime;
    private Integer durationHours;
    private String notes;
    private String triggers;
    private String managementActions;
    private String impactOnDailyLife;
    private Boolean requiresMedicalAttention;
    private Boolean reportedToDoctor;
}
