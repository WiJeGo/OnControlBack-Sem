package com.oncontrol.oncontrolbackend.imaging.interfaces.rest;

import com.oncontrol.oncontrolbackend.iam.infrastructure.service.AuthorizationService;
import com.oncontrol.oncontrolbackend.imaging.application.dto.ImagingStudyResponse;
import com.oncontrol.oncontrolbackend.imaging.application.dto.LinkImagingStudyRequest;
import com.oncontrol.oncontrolbackend.imaging.application.dto.LungSegmentationResponse;
import com.oncontrol.oncontrolbackend.imaging.domain.model.ImagingStudy;
import com.oncontrol.oncontrolbackend.imaging.domain.repository.ImagingStudyRepository;
import com.oncontrol.oncontrolbackend.imaging.infrastructure.client.ImagingServiceClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/imaging")
@RequiredArgsConstructor
public class ImagingController {

    private final ImagingServiceClient imagingServiceClient;
    private final ImagingStudyRepository imagingStudyRepository;
    private final AuthorizationService authorizationService;

    // --- Segmentation (proxy to the FastAPI microservice) ---

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

    // --- Patient ↔ study linkage (replaces the hardcoded frontend mapping) ---

    @GetMapping("/patients/{patientProfileId}/studies")
    public List<ImagingStudyResponse> getPatientStudies(@PathVariable Long patientProfileId) {
        authorizationService.requirePatientAccess(patientProfileId);
        return imagingStudyRepository
                .findByPatientProfileIdOrderByAcquisitionDateDesc(patientProfileId)
                .stream()
                .map(ImagingStudyResponse::from)
                .toList();
    }

    @PostMapping("/patients/{patientProfileId}/studies")
    public ResponseEntity<ImagingStudyResponse> linkPatientStudy(
            @PathVariable Long patientProfileId,
            @Valid @RequestBody LinkImagingStudyRequest request
    ) {
        authorizationService.requirePatientAccess(patientProfileId);

        if (imagingStudyRepository.existsByPatientProfileIdAndOrthancStudyId(
                patientProfileId, request.orthancStudyId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        ImagingStudy saved = imagingStudyRepository.save(ImagingStudy.builder()
                .patientProfileId(patientProfileId)
                .orthancStudyId(request.orthancStudyId())
                .label(request.label())
                .modality(request.modality())
                .description(request.description())
                .bodyPart(request.bodyPart())
                .acquisitionDate(request.acquisitionDate())
                .build());

        return ResponseEntity.status(HttpStatus.CREATED).body(ImagingStudyResponse.from(saved));
    }
}
