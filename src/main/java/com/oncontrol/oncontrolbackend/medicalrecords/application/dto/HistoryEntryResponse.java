package com.oncontrol.oncontrolbackend.medicalrecords.application.dto;

import com.oncontrol.oncontrolbackend.medicalrecords.domain.model.HistoryEntryType;
import com.oncontrol.oncontrolbackend.medicalrecords.domain.model.SeverityLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryEntryResponse {

    private Long id;

    private Long patientId;

    private String patientName;

    private Long doctorId;

    private String doctorName;

    private String specialty;

    private HistoryEntryType type;

    private LocalDate date;

    private String title;

    private String description;

    private String category;

    private SeverityLevel severity;

    private List<String> documents;

    private Boolean isActive;

    private LocalDateTime createdAt;
}

