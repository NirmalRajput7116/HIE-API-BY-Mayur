package com.cellbeans.hspa.temrdoctorsadvice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemrDoctorsAdviceRepository extends JpaRepository<TemrDoctorsAdvice, Long> {

    Page<TemrDoctorsAdvice> findByDcDoctorAdviceContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TemrDoctorsAdvice> findByDcContentContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TemrDoctorsAdvice> findByDcVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(Long visitid, Pageable page);

    TemrDoctorsAdvice findOneByDcVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(Long id);

    Page<TemrDoctorsAdvice> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TemrDoctorsAdvice> findByDcContentContains(String key);

    Page<TemrDoctorsAdvice> findByDcTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(Long timlineId, Pageable page);

    List<TemrDoctorsAdvice> findByDcTimelineIdTimelinePatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(Long id);

    List<TemrDoctorsAdvice> findAllByDcTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(Long id);

    //  abhijeet for OT Doctor Order
    List<TemrDoctorsAdvice> findAllByDcVisitIdVisitPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long patientid);

}
            
