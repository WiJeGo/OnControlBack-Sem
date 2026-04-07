package com.oncontrol.oncontrolbackend.appointments.domain.model;

import com.oncontrol.oncontrolbackend.profiles.domain.model.Profile;
import com.oncontrol.oncontrolbackend.shared.domain.model.AuditableModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Appointment extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Profile doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Profile patient;

    @Column(name = "appointment_date", nullable = false)
    private LocalDateTime appointmentDate;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private AppointmentType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private AppointmentStatus status = AppointmentStatus.SCHEDULED;

    @Column(name = "location")
    private String location;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "preparation_instructions", columnDefinition = "TEXT")
    private String preparationInstructions;

    @Column(name = "send_reminder", nullable = false)
    @Builder.Default
    private Boolean sendReminder = true;

    @Column(name = "reminder_sent")
    private Boolean reminderSent;

    @Column(name = "cancellation_reason", columnDefinition = "TEXT")
    private String cancellationReason;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "follow_up_notes", columnDefinition = "TEXT")
    private String followUpNotes;
}
