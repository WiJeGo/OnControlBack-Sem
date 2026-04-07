package com.oncontrol.oncontrolbackend.treatments.application.dto;

import com.oncontrol.oncontrolbackend.medicalrecords.domain.model.TreatmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTreatmentStatusRequest {

    @NotNull(message = "Status is required")
    private TreatmentStatus status;

    private String reason;
}

