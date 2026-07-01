package com.oncontrol.oncontrolbackend.imaging.infrastructure.client;

import com.oncontrol.oncontrolbackend.imaging.application.dto.LungSegmentationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ImagingServiceClient {

    private final RestClient restClient;

    public ImagingServiceClient(
            @Value("${app.imaging-service.url}") String imagingServiceUrl
    ) {
        this.restClient = RestClient.builder()
                .baseUrl(imagingServiceUrl)
                .build();
    }

    public LungSegmentationResponse segmentLungs(
            String orthancStudyId,
            Integer thresholdHu,
            Integer borderErosionRadius,
            Boolean force
    ) {
        return restClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/studies/{orthancStudyId}/segment-lungs")
                        .queryParam("threshold_hu", thresholdHu)
                        .queryParam("border_erosion_radius", borderErosionRadius)
                        .queryParam("force", force)
                        .build(orthancStudyId))
                .retrieve()
                .body(LungSegmentationResponse.class);
    }

    public LungSegmentationResponse getLungViewerUrl(String orthancStudyId) {
        return restClient.get()
                .uri("/studies/{orthancStudyId}/viewer-url", orthancStudyId)
                .retrieve()
                .body(LungSegmentationResponse.class);
    }
}