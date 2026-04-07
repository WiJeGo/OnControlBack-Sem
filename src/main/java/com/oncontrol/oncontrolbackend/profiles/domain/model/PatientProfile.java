package com.oncontrol.oncontrolbackend.profiles.domain.model;

import com.oncontrol.oncontrolbackend.shared.domain.model.AuditableModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * PatientProfile entity contains patient-specific attributes
 * A patient is created by a Doctor and belongs to that doctor
 */
@Entity
@Table(name = "patient_profiles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PatientProfile extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_profile_id", nullable = false)
    private DoctorProfile doctorProfile; // The doctor that created this patient

    @Column(name = "blood_type")
    private String bloodType;

    @Column(name = "allergies", columnDefinition = "TEXT")
    private String allergies;

    @Column(name = "emergency_contact_name")
    private String emergencyContactName;

    @Column(name = "emergency_contact_phone")
    private String emergencyContactPhone;

    @Column(name = "emergency_contact_relationship")
    private String emergencyContactRelationship;

    @Column(name = "medical_history", columnDefinition = "TEXT")
    private String medicalHistory;

    @Column(name = "current_medications", columnDefinition = "TEXT")
    private String currentMedications;

    @Column(name = "insurance_provider")
    private String insuranceProvider;

    @Column(name = "insurance_number")
    private String insuranceNumber;

    // Oncology-specific fields
    @Column(name = "cancer_type")
    private String cancerType;

    @Column(name = "cancer_stage")
    private String cancerStage;

    @Column(name = "diagnosis_date")
    private LocalDate diagnosisDate;

    @Column(name = "treatment_status")
    private String treatmentStatus;

    @Column(name = "last_treatment_date")
    private LocalDate lastTreatmentDate;
}
