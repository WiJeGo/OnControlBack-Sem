package com.oncontrol.oncontrolbackend.symptoms.domain.model;

import com.oncontrol.oncontrolbackend.profiles.domain.model.Profile;
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
@Table(name = "symptoms")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Symptom extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @Column(name = "symptom_name", nullable = false)
    private String symptomName;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = false)
    private SymptomSeverity severity;

    @Column(name = "occurrence_date", nullable = false)
    private LocalDate occurrenceDate;

    @Column(name = "occurrence_time", nullable = false)
    private LocalTime occurrenceTime;

    @Column(name = "duration_hours")
    private Integer durationHours;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "triggers", columnDefinition = "TEXT")
    private String triggers;

    @Column(name = "management_actions", columnDefinition = "TEXT")
    private String managementActions;

    @Column(name = "impact_on_daily_life")
    private String impactOnDailyLife;

    @Column(name = "requires_medical_attention", nullable = false)
    @Builder.Default
    private Boolean requiresMedicalAttention = false;

    @Column(name = "reported_to_doctor")
    @Builder.Default
    private Boolean reportedToDoctor = false;

    @Column(name = "doctor_response", columnDefinition = "TEXT")
    private String doctorResponse;
}
