package com.oncontrol.oncontrolbackend.imaging.application.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

/** Links an Orthanc study to an app patient. */
public record LinkImagingStudyRequest(
        @NotBlank String orthancStudyId,
        String label,
        String modality,
        String description,
        String bodyPart,
        LocalDate acquisitionDate
) {
}
