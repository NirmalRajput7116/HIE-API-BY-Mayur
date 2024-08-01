package com.cellbeans.hspa.mpathsamplerejection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MpathSampleRejectionRepository extends JpaRepository<MpathSampleRejection, Long> {

    Page<MpathSampleRejection> findBySrReasonContainsAndIsActiveTrueAndIsDeletedFalseOrSrCodeContainsAndIsActiveTrueAndIsDeletedFalse(String name, String name1, Pageable page);

    Page<MpathSampleRejection> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MpathSampleRejection> findAllByIsActiveTrueAndIsDeletedFalse();

    List<MpathSampleRejection> findBySrReasonContains(String key);

}
            
