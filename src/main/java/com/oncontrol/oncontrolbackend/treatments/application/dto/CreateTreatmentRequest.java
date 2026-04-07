package com.oncontrol.oncontrolbackend.treatments.application.dto;

import com.oncontrol.oncontrolbackend.treatments.domain.model.TreatmentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTreatmentRequest {

    @NotNull(message = "Treatment type is required")
    private TreatmentType type;

    @NotBlank(message = "Protocol is required")
    private String protocol;

    @NotNull(message = "Total cycles is required")
    @Positive(message = "Total cycles must be positive")
    private Integer totalCycles;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    private Integer sessionDurationMinutes;

    private String location;

    private List<String> medications;

    private String notes;

    private String preparationInstructions;
}

