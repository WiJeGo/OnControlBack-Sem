package com.oncontrol.oncontrolbackend.treatments.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSessionRequest {

    @NotNull(message = "Session date is required")
    private LocalDateTime sessionDate;

    @NotNull(message = "Cycle number is required")
    @Positive(message = "Cycle number must be positive")
    private Integer cycleNumber;

    private List<String> medicationsAdministered;

    private List<String> sideEffects;

    private Map<String, Object> vitalSigns;

    private String notes;
}

