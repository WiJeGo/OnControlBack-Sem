package com.oncontrol.oncontrolbackend.treatments.domain.repository;

import com.oncontrol.oncontrolbackend.treatments.domain.model.SessionStatus;
import com.oncontrol.oncontrolbackend.treatments.domain.model.TreatmentSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TreatmentSessionRepository extends JpaRepository<TreatmentSession, Long> {

    // Find sessions by treatment
    List<TreatmentSession> findByTreatmentIdOrderBySessionDateDesc(Long treatmentId);

    // Find sessions by treatment and status
    List<TreatmentSession> findByTreatmentIdAndStatus(Long treatmentId, SessionStatus status);

    // Find upcoming sessions for a treatment
    @Query("SELECT s FROM TreatmentSession s WHERE s.treatment.id = :treatmentId AND s.sessionDate > :now AND s.status = 'SCHEDULED' ORDER BY s.sessionDate ASC")
    List<TreatmentSession> findUpcomingSessionsByTreatmentId(@Param("treatmentId") Long treatmentId, @Param("now") LocalDateTime now);

    // Find upcoming sessions for a patient
    @Query("SELECT s FROM TreatmentSession s WHERE s.treatment.patient.id = :patientId AND s.sessionDate > :now AND s.status = 'SCHEDULED' ORDER BY s.sessionDate ASC")
    List<TreatmentSession> findUpcomingSessionsByPatientId(@Param("patientId") Long patientId, @Param("now") LocalDateTime now);

    // Count completed sessions for a treatment
    Long countByTreatmentIdAndStatus(Long treatmentId, SessionStatus status);
}

