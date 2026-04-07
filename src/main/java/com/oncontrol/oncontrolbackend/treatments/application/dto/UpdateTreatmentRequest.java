package com.oncontrol.oncontrolbackend.treatments.application.dto;

import com.oncontrol.oncontrolbackend.medicalrecords.domain.model.TreatmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTreatmentRequest {

    private Integer currentCycle;

    private LocalDate endDate;

    private LocalDateTime nextSession;

    private TreatmentStatus status;

    private BigDecimal effectiveness;

    private BigDecimal adherence;

    private String location;

    private List<String> medications;

    private List<String> sideEffects;

    private String notes;

    private String preparationInstructions;
}

