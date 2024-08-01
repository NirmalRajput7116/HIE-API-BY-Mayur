package com.cellbeans.hspa.temrvisitdoctorsnote;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemrVisitDoctorsNoteRepository extends JpaRepository<TemrVisitDoctorsNote, Long> {
    Page<TemrVisitDoctorsNote> findByVaPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long patientId, Pageable page);

    Page<TemrVisitDoctorsNote> findByVaTimelineIdTimelineIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long timelineId, Pageable page);

    List<TemrVisitDoctorsNote> findByVaPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long id);

    Page<TemrVisitDoctorsNote> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<TemrVisitDoctorsNote> findByVdnIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

}

