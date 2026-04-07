package com.oncontrol.oncontrolbackend.treatments.application.dto;

import com.oncontrol.oncontrolbackend.medicalrecords.domain.model.TreatmentStatus;
import com.oncontrol.oncontrolbackend.treatments.domain.model.TreatmentType;
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
public class TreatmentResponse {

    private Long id;

    private Long patientId;

    private String patientName;

    private String patientProfileId;

    private Long doctorId;

    private String doctorName;

    private TreatmentType type;

    private String protocol;

    private Integer currentCycle;

    private Integer totalCycles;

    private Integer progressPercentage;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDateTime nextSession;

    private TreatmentStatus status;

    private BigDecimal effectiveness;

    private BigDecimal adherence;

    private Integer sessionDurationMinutes;

    private String location;

    private List<String> medications;

    private List<String> sideEffects;

    private String notes;

    private String preparationInstructions;

    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

