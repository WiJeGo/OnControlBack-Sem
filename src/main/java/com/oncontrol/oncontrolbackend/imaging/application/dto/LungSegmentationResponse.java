package com.oncontrol.oncontrolbackend.imaging.application.dto;

public record LungSegmentationResponse(
        String orthancStudyId,
        Boolean alreadyProcessed,
        String lungVolumeUrl,
        String volviewUrl,
        String message
) {
}