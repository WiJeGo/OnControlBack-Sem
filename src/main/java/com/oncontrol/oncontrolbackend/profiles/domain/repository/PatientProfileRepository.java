package com.oncontrol.oncontrolbackend.profiles.domain.repository;

import com.oncontrol.oncontrolbackend.profiles.domain.model.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientProfileRepository extends JpaRepository<PatientProfile, Long> {
    
    Optional<PatientProfile> findByProfileId(Long profileId);
    
    @Query("SELECT pp FROM PatientProfile pp WHERE pp.profile.firstName LIKE %:name% OR pp.profile.lastName LIKE %:name%")
    List<PatientProfile> findByNameContaining(@Param("name") String name);

    @Query("SELECT pp FROM PatientProfile pp WHERE pp.doctorProfile.id = :doctorProfileId")
    List<PatientProfile> findByDoctorProfileId(@Param("doctorProfileId") Long doctorProfileId);

    @Query("SELECT pp FROM PatientProfile pp WHERE pp.doctorProfile.id = :doctorProfileId AND pp.profile.isActive = true")
    List<PatientProfile> findActivePatientsByDoctorProfileId(@Param("doctorProfileId") Long doctorProfileId);

    @Query("SELECT COUNT(pp) FROM PatientProfile pp WHERE pp.doctorProfile.id = :doctorProfileId")
    Long countPatientsByDoctorProfileId(@Param("doctorProfileId") Long doctorProfileId);

    @Query("SELECT pp FROM PatientProfile pp WHERE pp.doctorProfile.organization.id = :organizationId")
    List<PatientProfile> findByOrganizationId(@Param("organizationId") Long organizationId);
}
