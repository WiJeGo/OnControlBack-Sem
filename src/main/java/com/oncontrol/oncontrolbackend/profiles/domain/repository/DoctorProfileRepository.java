package com.oncontrol.oncontrolbackend.profiles.domain.repository;

import com.oncontrol.oncontrolbackend.profiles.domain.model.DoctorProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorProfileRepository extends JpaRepository<DoctorProfile, Long> {
    
    Optional<DoctorProfile> findByProfileId(Long profileId);
    
    Optional<DoctorProfile> findByLicenseNumber(String licenseNumber);
    
    List<DoctorProfile> findBySpecializationContainingIgnoreCase(String specialization);
    
    List<DoctorProfile> findByIsAvailableTrue();
    
    @Query("SELECT dp FROM DoctorProfile dp WHERE dp.profile.firstName LIKE %:name% OR dp.profile.lastName LIKE %:name%")
    List<DoctorProfile> findByNameContaining(@Param("name") String name);

    @Query("SELECT dp FROM DoctorProfile dp WHERE dp.organization.id = :organizationId")
    List<DoctorProfile> findByOrganizationId(@Param("organizationId") Long organizationId);

    @Query("SELECT dp FROM DoctorProfile dp WHERE dp.organization.id = :organizationId AND dp.isAvailable = true")
    List<DoctorProfile> findAvailableDoctorsByOrganizationId(@Param("organizationId") Long organizationId);

    @Query("SELECT COUNT(dp) FROM DoctorProfile dp WHERE dp.organization.id = :organizationId")
    Long countDoctorsByOrganizationId(@Param("organizationId") Long organizationId);
}
