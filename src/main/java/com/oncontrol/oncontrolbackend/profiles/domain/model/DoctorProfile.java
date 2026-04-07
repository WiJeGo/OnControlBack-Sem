package com.oncontrol.oncontrolbackend.profiles.domain.model;

import com.oncontrol.oncontrolbackend.iam.domain.model.User;
import com.oncontrol.oncontrolbackend.shared.domain.model.AuditableModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DoctorProfile entity contains doctor-specific attributes
 * A doctor is created by an Organization (User) and belongs to that organization
 */
@Entity
@Table(name = "doctor_profiles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DoctorProfile extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private User organization; // The organization that created this doctor

    @Column(name = "specialization", nullable = false)
    private String specialization;

    @Column(name = "license_number", nullable = false, unique = true)
    private String licenseNumber;

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    @Column(name = "hospital_affiliation")
    private String hospitalAffiliation;

    @Column(name = "consultation_fee")
    private BigDecimal consultationFee;

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @Column(name = "is_available", nullable = false)
    @Builder.Default
    private Boolean isAvailable = true;

    @Column(name = "rating")
    @Builder.Default
    private Double rating = 0.0;

    @Column(name = "total_reviews")
    @Builder.Default
    private Integer totalReviews = 0;
}
