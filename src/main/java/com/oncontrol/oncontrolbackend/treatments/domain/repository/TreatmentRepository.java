package com.oncontrol.oncontrolbackend.treatments.domain.repository;

import com.oncontrol.oncontrolbackend.medicalrecords.domain.model.TreatmentStatus;
import com.oncontrol.oncontrolbackend.treatments.domain.model.Treatment;
import com.oncontrol.oncontrolbackend.treatments.domain.model.TreatmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Long> {

    // Find treatments by doctor
    List<Treatment> findByDoctorIdAndIsActiveTrue(Long doctorId);

    // Find treatments by patient
    List<Treatment> findByPatientIdAndIsActiveTrue(Long patientId);

    // Find active treatments by doctor
    List<Treatment> findByDoctorIdAndStatusAndIsActiveTrue(Long doctorId, TreatmentStatus status);

    // Find active treatments by patient
    List<Treatment> findByPatientIdAndStatusAndIsActiveTrue(Long patientId, TreatmentStatus status);

    // Count treatments by doctor and status
    Long countByDoctorIdAndStatus(Long doctorId, TreatmentStatus status);

    // Count treatments by doctor and type
    Long countByDoctorIdAndType(Long doctorId, TreatmentType type);

    // Find current active treatment for patient
    @Query("SELECT t FROM Treatment t WHERE t.patient.id = :patientId AND t.status = 'ACTIVE' AND t.isActive = true ORDER BY t.startDate DESC")
    List<Treatment> findCurrentTreatmentsByPatientId(@Param("patientId") Long patientId);

    // Get treatment statistics
    @Query("SELECT t.status as status, COUNT(t) as count FROM Treatment t WHERE t.doctor.id = :doctorId AND t.isActive = true GROUP BY t.status")
    List<Object[]> getTreatmentStatsByDoctorId(@Param("doctorId") Long doctorId);

    @Query("SELECT t.type as type, COUNT(t) as count FROM Treatment t WHERE t.doctor.id = :doctorId AND t.isActive = true GROUP BY t.type")
    List<Object[]> getTreatmentTypeStatsByDoctorId(@Param("doctorId") Long doctorId);
}

