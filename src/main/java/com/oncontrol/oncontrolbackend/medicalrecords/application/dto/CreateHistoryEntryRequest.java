package com.oncontrol.oncontrolbackend.medicalrecords.application.dto;

import com.oncontrol.oncontrolbackend.medicalrecords.domain.model.HistoryEntryType;
import com.oncontrol.oncontrolbackend.medicalrecords.domain.model.SeverityLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateHistoryEntryRequest {

    @NotNull(message = "Type is required")
    private HistoryEntryType type;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private String category;

    private SeverityLevel severity;

    private List<String> documents;
}

