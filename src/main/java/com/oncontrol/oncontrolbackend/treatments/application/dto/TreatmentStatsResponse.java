package com.oncontrol.oncontrolbackend.treatments.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentStatsResponse {

    private Long active;

    private Long completed;

    private Long paused;

    private Long suspended;

    private BigDecimal averageEffectiveness;

    private BigDecimal averageAdherence;

    private Map<String, Long> byType;
}

