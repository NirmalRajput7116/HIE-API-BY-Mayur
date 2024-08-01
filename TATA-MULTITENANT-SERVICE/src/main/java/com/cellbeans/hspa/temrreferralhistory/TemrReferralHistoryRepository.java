package com.cellbeans.hspa.temrreferralhistory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemrReferralHistoryRepository extends JpaRepository<TemrReferralHistory, Long> {

    Page<TemrReferralHistory> findByRhVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(Long visitId, Pageable page);

    Page<TemrReferralHistory> findByRhVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalseAndRhIsExTrue(Long visitId, Pageable page);

    Page<TemrReferralHistory> findByRhVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalseAndRhIsExFalse(Long visitId, Pageable page);

    Page<TemrReferralHistory> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TemrReferralHistory> findByRhVisitIdContains(String key);

    List<TemrReferralHistory> findByRhPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long pid);

}
            
