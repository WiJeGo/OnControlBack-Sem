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

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "medical_records")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MedicalRecord extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientProfile patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private DoctorProfile doctor;

    @Column(name = "diagnosis", nullable = false)
    private String diagnosis;

    @Column(name = "stage")
    private String stage;

    @Column(name = "treatment_plan", columnDefinition = "TEXT")
    private String treatmentPlan;

    @Column(name = "treatment_start_date")
    private LocalDate treatmentStartDate;

    @Column(name = "treatment_end_date")
    private LocalDate treatmentEndDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "treatment_status", nullable = false)
    @Builder.Default
    private TreatmentStatus treatmentStatus = TreatmentStatus.ACTIVE;

    @Column(name = "progress_percentage")
    @Builder.Default
    private Integer progressPercentage = 0;

    @Column(name = "total_sessions")
    private Integer totalSessions;

    @Column(name = "completed_sessions")
    @Builder.Default
    private Integer completedSessions = 0;

    @Column(name = "next_session_date")
    private LocalDate nextSessionDate;

    @Column(name = "treatment_type")
    private String treatmentType;

    @Column(name = "response_rate")
    private String responseRate;

    @Column(name = "side_effects_level")
    private String sideEffectsLevel;

    @Column(name = "quality_of_life_score")
    private BigDecimal qualityOfLifeScore;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;
}
