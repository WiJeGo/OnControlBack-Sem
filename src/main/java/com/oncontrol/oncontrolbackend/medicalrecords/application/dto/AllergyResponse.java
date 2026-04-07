package com.oncontrol.oncontrolbackend.medicalrecords.application.dto;

import com.oncontrol.oncontrolbackend.medicalrecords.domain.model.AllergyType;
import com.oncontrol.oncontrolbackend.medicalrecords.domain.model.SeverityLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllergyResponse {

    private Long id;

    private Long patientId;

    private String allergen;

    private AllergyType type;

    private SeverityLevel severity;

    private String reaction;

    private LocalDate diagnosedDate;

    private String notes;

    private Boolean isActive;
}

