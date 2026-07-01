package com.oncontrol.oncontrolbackend.imaging.application.dto;

import com.oncontrol.oncontrolbackend.imaging.domain.model.ImagingStudy;

import java.time.LocalDate;

public record ImagingStudyResponse(
        Long id,
        Long patientProfileId,
        String orthancStudyId,
        String label,
        String modality,
        String description,
        String bodyPart,
        LocalDate acquisitionDate
) {
    public static ImagingStudyResponse from(ImagingStudy s) {
        return new ImagingStudyResponse(
                s.getId(),
                s.getPatientProfileId(),
                s.getOrthancStudyId(),
                s.getLabel(),
                s.getModality(),
                s.getDescription(),
                s.getBodyPart(),
                s.getAcquisitionDate()
        );
    }
}
