package com.oncontrol.oncontrolbackend.imaging.domain.repository;

import com.oncontrol.oncontrolbackend.imaging.domain.model.ImagingStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImagingStudyRepository extends JpaRepository<ImagingStudy, Long> {

    List<ImagingStudy> findByPatientProfileIdOrderByAcquisitionDateDesc(Long patientProfileId);

    boolean existsByPatientProfileIdAndOrthancStudyId(Long patientProfileId, String orthancStudyId);

    Optional<ImagingStudy> findByPatientProfileIdAndOrthancStudyId(Long patientProfileId, String orthancStudyId);
}
