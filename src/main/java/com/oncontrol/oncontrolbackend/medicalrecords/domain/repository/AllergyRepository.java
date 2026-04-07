package com.oncontrol.oncontrolbackend.medicalrecords.domain.repository;

import com.oncontrol.oncontrolbackend.medicalrecords.domain.model.Allergy;
import com.oncontrol.oncontrolbackend.profiles.domain.model.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllergyRepository extends JpaRepository<Allergy, Long> {

    List<Allergy> findByPatientAndIsActiveTrueOrderByDiagnosedDateDesc(PatientProfile patient);

    Long countByPatientAndIsActiveTrue(PatientProfile patient);
}

