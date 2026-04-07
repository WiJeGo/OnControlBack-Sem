package com.oncontrol.oncontrolbackend.medicalrecords.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarkDoseTakenRequest {

    @NotNull(message = "Taken time is required")
    private LocalDateTime takenAt;

    private String notes;
}

