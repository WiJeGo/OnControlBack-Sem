package com.oncontrol.oncontrolbackend.medicalrecords.application.dto;

import com.oncontrol.oncontrolbackend.medicalrecords.domain.model.AllergyType;
import com.oncontrol.oncontrolbackend.medicalrecords.domain.model.SeverityLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAllergyRequest {

    @NotBlank(message = "Allergen is required")
    private String allergen;

    @NotNull(message = "Type is required")
    private AllergyType type;

    @NotNull(message = "Severity is required")
    private SeverityLevel severity;

    private String reaction;

    private LocalDate diagnosedDate;

    private String notes;
}

