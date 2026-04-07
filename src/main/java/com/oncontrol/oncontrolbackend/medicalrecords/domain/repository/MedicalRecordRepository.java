package com.oncontrol.oncontrolbackend.medicalrecords.domain.repository;

import com.oncontrol.oncontrolbackend.medicalrecords.domain.model.MedicalRecord;
import com.oncontrol.oncontrolbackend.medicalrecords.domain.model.TreatmentStatus;
import com.oncontrol.oncontrolbackend.profiles.domain.model.DoctorProfile;
import com.oncontrol.oncontrolbackend.profiles.domain.model.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    
    List<MedicalRecord> findByPatient(PatientProfile patient);
    
    List<MedicalRecord> findByDoctor(DoctorProfile doctor);
    
    List<MedicalRecord> findByPatientAndIsActiveTrue(PatientProfile patient);
    
    List<MedicalRecord> findByTreatmentStatus(TreatmentStatus status);
    
    Optional<MedicalRecord> findByPatientAndIsActiveTrueAndTreatmentStatus(PatientProfile patient, TreatmentStatus status);
    
    @Query("SELECT mr FROM MedicalRecord mr WHERE mr.patient = :patient ORDER BY mr.createdAt DESC")
    List<MedicalRecord> findByPatientOrderByCreatedAtDesc(@Param("patient") PatientProfile patient);
}
