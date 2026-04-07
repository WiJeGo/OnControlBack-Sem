package com.oncontrol.oncontrolbackend.treatments.domain.model;

import com.oncontrol.oncontrolbackend.medicalrecords.domain.model.TreatmentStatus;
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
import java.time.LocalDateTime;

@Entity
@Table(name = "treatments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Treatment extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientProfile patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private DoctorProfile doctor;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TreatmentType type;

    @Column(name = "protocol", nullable = false)
    private String protocol;

    @Column(name = "current_cycle")
    @Builder.Default
    private Integer currentCycle = 1;

    @Column(name = "total_cycles", nullable = false)
    private Integer totalCycles;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "next_session")
    private LocalDateTime nextSession;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private TreatmentStatus status = TreatmentStatus.ACTIVE;

    @Column(name = "effectiveness")
    private BigDecimal effectiveness;

    @Column(name = "adherence")
    private BigDecimal adherence;

    @Column(name = "session_duration_minutes")
    private Integer sessionDurationMinutes;

    @Column(name = "location")
    private String location;

    @Column(name = "medications", columnDefinition = "TEXT")
    private String medications; // JSON array of medications

    @Column(name = "side_effects", columnDefinition = "TEXT")
    private String sideEffects; // JSON array of side effects

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "preparation_instructions", columnDefinition = "TEXT")
    private String preparationInstructions;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    // Helper method to calculate progress percentage
    public Integer getProgressPercentage() {
        if (totalCycles == null || totalCycles == 0) {
            return 0;
        }
        return (int) ((currentCycle * 100.0) / totalCycles);
    }

    // Helper method to increment cycle
    public void incrementCycle() {
        if (currentCycle < totalCycles) {
            this.currentCycle++;
        }
        if (currentCycle.equals(totalCycles)) {
            this.status = TreatmentStatus.COMPLETED;
        }
    }
}

