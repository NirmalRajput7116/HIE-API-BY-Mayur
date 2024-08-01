package com.cellbeans.hspa.trnpayout;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrnPayoutRepository extends JpaRepository<TrnPayout, Long> {

    Page<TrnPayout> findByPayoutServiceTypeContainsAndIsActiveTrueAndIsDeletedFalse(String serviceType, Pageable page);

    Page<TrnPayout> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

}