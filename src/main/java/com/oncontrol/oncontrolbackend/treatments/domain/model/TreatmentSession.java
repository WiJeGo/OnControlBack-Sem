package com.oncontrol.oncontrolbackend.treatments.domain.model;

import com.oncontrol.oncontrolbackend.shared.domain.model.AuditableModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "treatment_sessions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TreatmentSession extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "treatment_id", nullable = false)
    private Treatment treatment;

    @Column(name = "session_number", nullable = false)
    private Integer sessionNumber;

    @Column(name = "cycle_number", nullable = false)
    private Integer cycleNumber;

    @Column(name = "session_date", nullable = false)
    private LocalDateTime sessionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private SessionStatus status = SessionStatus.SCHEDULED;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(name = "location")
    private String location;

    @Column(name = "medications_administered", columnDefinition = "TEXT")
    private String medicationsAdministered; // JSON array

    @Column(name = "side_effects", columnDefinition = "TEXT")
    private String sideEffects; // JSON array

    @Column(name = "vital_signs", columnDefinition = "TEXT")
    private String vitalSigns; // JSON object

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @Column(name = "cancellation_reason")
    private String cancellationReason;
}

