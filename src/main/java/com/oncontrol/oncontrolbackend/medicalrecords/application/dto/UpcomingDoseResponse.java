package com.oncontrol.oncontrolbackend.medicalrecords.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpcomingDoseResponse {

    private Long medicationId;

    private String medicationName;

    private String dosage;

    private LocalDateTime scheduledTime;

    private Boolean taken;

    private String instructions;
}

