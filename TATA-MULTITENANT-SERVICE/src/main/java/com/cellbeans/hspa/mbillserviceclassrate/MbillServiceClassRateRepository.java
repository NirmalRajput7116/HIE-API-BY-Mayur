package com.cellbeans.hspa.mbillserviceclassrate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MbillServiceClassRateRepository extends JpaRepository<MbillServiceClassRate, Long> {

    Page<MbillServiceClassRate> findByScrClassIdClassNameContainsOrScrMbillServiceIdServiceIdEqualsAndIsActiveTrueAndIsDeletedFalse(String name, Long serviceId, Pageable page);

    Page<MbillServiceClassRate> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MbillServiceClassRate> findByScrClassIdContains(String key);

    List<MbillServiceClassRate> findByScrMbillServiceIdServiceIdAndIsActiveTrueAndIsDeletedFalse(Long serviceId);

    MbillServiceClassRate findTopByScrMbillServiceIdServiceIdEqualsAndScrClassIdClassIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long serviceId, Long classId);

}
            
