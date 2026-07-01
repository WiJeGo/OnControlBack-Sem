package com.oncontrol.oncontrolbackend.imaging.domain.model;

import com.oncontrol.oncontrolbackend.shared.domain.model.AuditableModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Links an app patient (patient_profile_id) to a DICOM study stored in Orthanc
 * (orthanc_study_id). Replaces the old hardcoded imaging-studies.ts mapping so
 * the patient↔study relationship lives in the DB, independent of the (often
 * anonymized) patient identity baked into the DICOM itself.
 */
@Entity
@Table(name = "imaging_studies")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ImagingStudy extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_profile_id", nullable = false)
    private Long patientProfileId;

    @Column(name = "orthanc_study_id", nullable = false)
    private String orthancStudyId;

    @Column(name = "label")
    private String label;

    @Column(name = "modality")
    private String modality;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "body_part")
    private String bodyPart;

    @Column(name = "acquisition_date")
    private LocalDate acquisitionDate;
}
