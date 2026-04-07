package com.oncontrol.oncontrolbackend.medicalrecords.domain.repository;

import com.oncontrol.oncontrolbackend.medicalrecords.domain.model.Medication;
import com.oncontrol.oncontrolbackend.profiles.domain.model.DoctorProfile;
import com.oncontrol.oncontrolbackend.profiles.domain.model.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {
    
    List<Medication> findByPatient(PatientProfile patient);
    
    List<Medication> findByPatientAndIsActiveTrue(PatientProfile patient);
    
    List<Medication> findByPrescribedBy(DoctorProfile doctor);
    
    @Query("SELECT m FROM Medication m WHERE m.patient = :patient AND m.isActive = true ORDER BY m.startDate DESC")
    List<Medication> findActiveByPatientOrderByStartDateDesc(@Param("patient") PatientProfile patient);
}
