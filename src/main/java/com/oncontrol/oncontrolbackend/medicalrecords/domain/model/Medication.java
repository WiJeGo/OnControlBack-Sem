package com.oncontrol.oncontrolbackend.medicalrecords.domain.model;

import com.oncontrol.oncontrolbackend.profiles.domain.model.DoctorProfile;
import com.oncontrol.oncontrolbackend.profiles.domain.model.PatientProfile;
import com.oncontrol.oncontrolbackend.shared.domain.model.AuditableModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "medications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Medication extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientProfile patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescribed_by", nullable = false)
    private DoctorProfile prescribedBy;

    @Column(name = "medication_name", nullable = false)
    private String medicationName;

    @Column(name = "dosage", nullable = false)
    private String dosage;

    @Column(name = "frequency", nullable = false)
    private String frequency;

    @Column(name = "route")
    private String route; // ORAL, INTRAVENOUS, INTRAMUSCULAR, SUBCUTANEOUS, TOPICAL

    @Column(name = "instructions", columnDefinition = "TEXT")
    private String instructions;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "next_dose_time")
    private LocalTime nextDoseTime;

    @Column(name = "adherence_percentage")
    @Builder.Default
    private Integer adherencePercentage = 100;

    @Column(name = "side_effects", columnDefinition = "TEXT")
    private String sideEffects;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "is_prn")
    @Builder.Default
    private Boolean isPrn = false; // "Pro re nata" - as needed medication
}
