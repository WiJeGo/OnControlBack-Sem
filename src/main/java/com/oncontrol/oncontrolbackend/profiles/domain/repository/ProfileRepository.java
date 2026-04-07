package com.oncontrol.oncontrolbackend.profiles.domain.repository;

import com.oncontrol.oncontrolbackend.profiles.domain.model.Profile;
import com.oncontrol.oncontrolbackend.profiles.domain.model.ProfileType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByEmail(String email);

    Optional<Profile> findByProfileId(String profileId);

    boolean existsByEmail(String email);

    List<Profile> findByProfileType(ProfileType profileType);

    List<Profile> findByUserId(Long userId);

    @Query("SELECT p FROM Profile p WHERE p.profileType = :profileType AND p.isActive = true")
    List<Profile> findActiveProfilesByType(@Param("profileType") ProfileType profileType);

    @Query("SELECT p FROM Profile p WHERE p.profileType = :profileType AND " +
           "(LOWER(p.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
           "LOWER(p.lastName) LIKE LOWER(CONCAT('%', :name, '%')))")
    List<Profile> findByTypeAndNameContaining(@Param("profileType") ProfileType profileType, @Param("name") String name);

    @Query("SELECT p FROM Profile p WHERE p.profileType = 'PATIENT' AND p.isActive = true")
    List<Profile> findAllActivePatients();

    @Query("SELECT p FROM Profile p WHERE p.profileType = 'DOCTOR' AND p.isActive = true")
    List<Profile> findAllActiveDoctors();

    @Query("SELECT COUNT(p) FROM Profile p WHERE p.profileType = :profileType AND p.isActive = true")
    Long countActiveProfilesByType(@Param("profileType") ProfileType profileType);

    @Query("SELECT p FROM Profile p WHERE p.profileType = 'PATIENT' AND " +
           "(LOWER(p.firstName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.lastName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.email) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.profileId) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Profile> searchPatients(@Param("query") String query);

    @Query("SELECT p FROM Profile p WHERE p.profileType = 'PATIENT' AND p.isActive = :isActive")
    List<Profile> findPatientsByActiveStatus(@Param("isActive") Boolean isActive);

    Page<Profile> findByProfileType(ProfileType profileType, Pageable pageable);

    // Organization-based queries
    @Query("SELECT p FROM Profile p WHERE p.user.id = :organizationId AND p.profileType = :profileType")
    List<Profile> findByOrganizationIdAndType(@Param("organizationId") Long organizationId, @Param("profileType") ProfileType profileType);

    @Query("SELECT p FROM Profile p WHERE p.user.id = :organizationId AND p.profileType = 'DOCTOR' AND p.isActive = true")
    List<Profile> findActiveDoctorsByOrganizationId(@Param("organizationId") Long organizationId);

    @Query("SELECT COUNT(p) FROM Profile p WHERE p.user.id = :organizationId AND p.profileType = :profileType AND p.isActive = true")
    Long countActiveProfilesByOrganizationIdAndType(@Param("organizationId") Long organizationId, @Param("profileType") ProfileType profileType);

    @Query("SELECT p FROM Profile p WHERE p.user.id = :organizationId AND p.profileType = 'DOCTOR' AND " +
           "(LOWER(p.firstName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.lastName) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Profile> searchDoctorsByOrganizationId(@Param("organizationId") Long organizationId, @Param("query") String query);
}
