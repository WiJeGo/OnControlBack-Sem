package com.oncontrol.oncontrolbackend.medicalrecords.domain.repository;

import com.oncontrol.oncontrolbackend.medicalrecords.domain.model.HistoryEntryType;
import com.oncontrol.oncontrolbackend.medicalrecords.domain.model.MedicalHistoryEntry;
import com.oncontrol.oncontrolbackend.profiles.domain.model.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistoryEntry, Long> {

    List<MedicalHistoryEntry> findByPatientAndIsActiveTrueOrderByDateDesc(PatientProfile patient);

    List<MedicalHistoryEntry> findByPatientAndTypeAndIsActiveTrueOrderByDateDesc(PatientProfile patient, HistoryEntryType type);

    @Query("SELECT h FROM MedicalHistoryEntry h WHERE h.patient = :patient AND h.date BETWEEN :startDate AND :endDate AND h.isActive = true ORDER BY h.date DESC")
    List<MedicalHistoryEntry> findByPatientAndDateRange(
            @Param("patient") PatientProfile patient,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    Long countByPatientAndType(PatientProfile patient, HistoryEntryType type);
}

