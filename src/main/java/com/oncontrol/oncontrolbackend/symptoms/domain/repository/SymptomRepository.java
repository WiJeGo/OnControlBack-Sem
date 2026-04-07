package com.oncontrol.oncontrolbackend.symptoms.domain.repository;

import com.oncontrol.oncontrolbackend.symptoms.domain.model.Symptom;
import com.oncontrol.oncontrolbackend.symptoms.domain.model.SymptomSeverity;
import com.oncontrol.oncontrolbackend.profiles.domain.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SymptomRepository extends JpaRepository<Symptom, Long> {
    
    List<Symptom> findByProfile(Profile profile);
    
    List<Symptom> findByProfileOrderByOccurrenceDateDesc(Profile profile);
    
    List<Symptom> findByProfileAndOccurrenceDateBetween(Profile profile, LocalDate startDate, LocalDate endDate);
    
    List<Symptom> findBySeverity(SymptomSeverity severity);
    
    List<Symptom> findByRequiresMedicalAttentionTrue();
    
    List<Symptom> findByReportedToDoctorFalse();
    
    @Query("SELECT s FROM Symptom s WHERE s.profile = :profile AND s.severity IN :severities ORDER BY s.occurrenceDate DESC")
    List<Symptom> findByProfileAndSeverityInOrderByOccurrenceDateDesc(@Param("profile") Profile profile, @Param("severities") List<SymptomSeverity> severities);
    
    @Query("SELECT COUNT(s) FROM Symptom s WHERE s.profile = :profile AND s.occurrenceDate >= :startDate")
    Long countByProfileAndOccurrenceDateAfter(@Param("profile") Profile profile, @Param("startDate") LocalDate startDate);
    
    @Query("SELECT s FROM Symptom s WHERE s.profile = :profile AND s.occurrenceDate >= :startDate ORDER BY s.occurrenceDate DESC")
    List<Symptom> findRecentByProfile(@Param("profile") Profile profile, @Param("startDate") LocalDate startDate);
    
    @Query("SELECT COUNT(s) FROM Symptom s WHERE s.profile = :profile AND s.severity = 'SEVERO' AND s.occurrenceDate >= :startDate")
    Long countSevereSymptomsByProfile(@Param("profile") Profile profile, @Param("startDate") LocalDate startDate);
}
