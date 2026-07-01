package com.oncontrol.oncontrolbackend.imaging.infrastructure.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

/**
 * Talks to Orthanc (PACS) to upload DICOM. Orthanc auto-detects a single
 * instance or a ZIP archive and returns one entry per imported instance,
 * each carrying the parent study id — which is what we link to the patient.
 */
@Component
public class OrthancClient {

    private final RestClient restClient;

    public OrthancClient(@Value("${app.orthanc.url}") String orthancUrl) {
        this.restClient = RestClient.builder().baseUrl(orthancUrl).build();
    }

    /** Uploads raw DICOM/ZIP bytes and returns the resulting Orthanc study id. */
    public String uploadStudy(byte[] data) {
        List<Map<String, Object>> imported = restClient.post()
                .uri("/instances")
                .body(data)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        if (imported == null || imported.isEmpty()) {
            throw new IllegalStateException("Orthanc no importó ninguna instancia DICOM.");
        }

        Object parentStudy = imported.get(0).get("ParentStudy");
        if (parentStudy == null) {
            throw new IllegalStateException("Orthanc no devolvió el estudio padre (ParentStudy).");
        }
        return parentStudy.toString();
    }
}
