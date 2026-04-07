package com.oncontrol.oncontrolbackend.medicalrecords.domain.model;

import com.oncontrol.oncontrolbackend.profiles.domain.model.PatientProfile;
import com.oncontrol.oncontrolbackend.shared.domain.model.AuditableModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "allergies")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Allergy extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientProfile patient;

    @Column(name = "allergen", nullable = false)
    private String allergen;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private AllergyType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = false)
    private SeverityLevel severity;

    @Column(name = "reaction", columnDefinition = "TEXT")
    private String reaction;

    @Column(name = "diagnosed_date")
    private LocalDate diagnosedDate;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;
}

