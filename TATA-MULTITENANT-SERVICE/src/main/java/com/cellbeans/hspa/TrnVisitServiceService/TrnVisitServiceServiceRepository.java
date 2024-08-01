package com.cellbeans.hspa.TrnVisitServiceService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrnVisitServiceServiceRepository extends JpaRepository<TrnVisitServiceService, Long> {
    Page<TrnVisitServiceService> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<TrnVisitServiceService> findByServiceIdServiceNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);
//    List<TrnVisitServiceService> findByVsIdEquals(Long key);
}
