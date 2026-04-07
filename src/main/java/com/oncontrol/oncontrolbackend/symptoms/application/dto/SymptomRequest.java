package com.oncontrol.oncontrolbackend.symptoms.application.dto;

import com.oncontrol.oncontrolbackend.symptoms.domain.model.SymptomSeverity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
public class SymptomRequest {
    
    @NotBlank(message = "Symptom name is required")
    private String symptomName;
    
    @NotNull(message = "Severity is required")
    private SymptomSeverity severity;
    
    @NotNull(message = "Occurrence date is required")
    @PastOrPresent(message = "Occurrence date cannot be in the future")
    private LocalDate occurrenceDate;
    
    @NotNull(message = "Occurrence time is required")
    private LocalTime occurrenceTime;
    
    private Integer durationHours;
    
    private String notes;
    
    private String triggers;
    
    private String managementActions;
    
    private String impactOnDailyLife;
    
    @Builder.Default
    private Boolean requiresMedicalAttention = false;
}
