package com.oncontrol.oncontrolbackend.treatments.application.dto;

import com.oncontrol.oncontrolbackend.treatments.domain.model.SessionStatus;
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
public class TreatmentSessionResponse {

    private Long id;

    private Long treatmentId;

    private Integer sessionNumber;

    private Integer cycleNumber;

    private LocalDateTime sessionDate;

    private SessionStatus status;

    private Integer durationMinutes;

    private String location;

    private List<String> medicationsAdministered;

    private List<String> sideEffects;

    private Map<String, Object> vitalSigns;

    private String notes;

    private LocalDateTime completedAt;

    private LocalDateTime cancelledAt;

    private String cancellationReason;

    private LocalDateTime createdAt;
}

