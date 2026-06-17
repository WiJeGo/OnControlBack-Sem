package com.oncontrol.oncontrolbackend.iam.infrastructure.service;

import com.oncontrol.oncontrolbackend.iam.domain.model.User;
import com.oncontrol.oncontrolbackend.profiles.domain.model.DoctorProfile;
import com.oncontrol.oncontrolbackend.profiles.domain.model.PatientProfile;
import com.oncontrol.oncontrolbackend.profiles.domain.model.Profile;
import com.oncontrol.oncontrolbackend.profiles.domain.model.ProfileType;
import com.oncontrol.oncontrolbackend.profiles.domain.repository.DoctorProfileRepository;
import com.oncontrol.oncontrolbackend.profiles.domain.repository.PatientProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Centralizes resource-ownership (IDOR) checks.
 *
 * The authenticated principal is either a {@link User} (organization, id = User.id)
 * or a {@link Profile} (doctor/patient, id = Profile.id). The IDs used in URLs are a
 * DIFFERENT id space: doctorProfileId = DoctorProfile.id, patientProfileId = PatientProfile.id,
 * organizationId = User.id. These guards resolve the principal to its DoctorProfile/PatientProfile
 * before comparing, and allow access up the ownership chain (org -> doctor -> patient).
 */
@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final DoctorProfileRepository doctorProfileRepository;
    private final PatientProfileRepository patientProfileRepository;

    private Object currentPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal() == null) {
            throw new AccessDeniedException("Not authenticated");
        }
        return auth.getPrincipal();
    }

    /**
     * Allows only the organization itself.
     */
    public void requireOrganization(Long organizationId) {
        Object principal = currentPrincipal();
        if (principal instanceof User user && user.getId().equals(organizationId)) {
            return;
        }
        throw new AccessDeniedException("You are not allowed to access this organization's data");
    }

    /**
     * Allows the doctor themselves, or the organization that owns the doctor.
     */
    public void requireDoctor(Long doctorProfileId) {
        Object principal = currentPrincipal();
        DoctorProfile target = doctorProfileRepository.findById(doctorProfileId)
                .orElseThrow(() -> new AccessDeniedException("Doctor not found"));

        if (principal instanceof Profile profile && profile.getProfileType() == ProfileType.DOCTOR) {
            DoctorProfile own = doctorProfileRepository.findByProfileId(profile.getId()).orElse(null);
            if (own != null && own.getId().equals(target.getId())) {
                return;
            }
        } else if (principal instanceof User user) {
            if (target.getOrganization() != null && target.getOrganization().getId().equals(user.getId())) {
                return;
            }
        }
        throw new AccessDeniedException("You are not allowed to access this doctor's data");
    }

    /**
     * Allows the patient themselves, the patient's doctor, or the owning organization.
     */
    public void requirePatientAccess(Long patientProfileId) {
        Object principal = currentPrincipal();
        PatientProfile target = patientProfileRepository.findById(patientProfileId)
                .orElseThrow(() -> new AccessDeniedException("Patient not found"));

        if (principal instanceof Profile profile) {
            if (profile.getProfileType() == ProfileType.PATIENT) {
                PatientProfile own = patientProfileRepository.findByProfileId(profile.getId()).orElse(null);
                if (own != null && own.getId().equals(target.getId())) {
                    return;
                }
            } else if (profile.getProfileType() == ProfileType.DOCTOR) {
                DoctorProfile own = doctorProfileRepository.findByProfileId(profile.getId()).orElse(null);
                if (own != null && target.getDoctorProfile() != null
                        && target.getDoctorProfile().getId().equals(own.getId())) {
                    return;
                }
            }
        } else if (principal instanceof User user) {
            if (target.getDoctorProfile() != null
                    && target.getDoctorProfile().getOrganization() != null
                    && target.getDoctorProfile().getOrganization().getId().equals(user.getId())) {
                return;
            }
        }
        throw new AccessDeniedException("You are not allowed to access this patient's data");
    }
}
