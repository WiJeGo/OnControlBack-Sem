package com.oncontrol.oncontrolbackend.imaging.interfaces.rest;

import com.oncontrol.oncontrolbackend.imaging.application.dto.LungSegmentationResponse;
import com.oncontrol.oncontrolbackend.imaging.infrastructure.client.ImagingServiceClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/imaging")
public class ImagingController {

    private final ImagingServiceClient imagingServiceClient;

    public ImagingController(ImagingServiceClient imagingServiceClient) {
        this.imagingServiceClient = imagingServiceClient;
    }

    @PostMapping("/studies/{orthancStudyId}/segment-lungs")
    public LungSegmentationResponse segmentLungs(
            @PathVariable String orthancStudyId,
            @RequestParam(defaultValue = "-400") Integer thresholdHu,
            @RequestParam(defaultValue = "1") Integer borderErosionRadius,
            @RequestParam(defaultValue = "false") Boolean force
    ) {
        return imagingServiceClient.segmentLungs(
                orthancStudyId,
                thresholdHu,
                borderErosionRadius,
                force
        );
    }

    @GetMapping("/studies/{orthancStudyId}/lung-viewer-url")
    public LungSegmentationResponse getLungViewerUrl(
            @PathVariable String orthancStudyId
    ) {
        return imagingServiceClient.getLungViewerUrl(orthancStudyId);
    }
}