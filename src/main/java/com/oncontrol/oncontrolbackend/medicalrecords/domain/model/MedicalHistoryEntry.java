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

@Entity
@Table(name = "medical_history_entries")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MedicalHistoryEntry extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientProfile patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private DoctorProfile doctor;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private HistoryEntryType type;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "category")
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity")
    private SeverityLevel severity;

    @Column(name = "documents", columnDefinition = "TEXT")
    private String documents; // JSON array of document URLs

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;
}

