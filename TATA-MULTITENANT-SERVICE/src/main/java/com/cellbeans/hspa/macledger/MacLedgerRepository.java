package com.cellbeans.hspa.macledger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MacLedgerRepository extends JpaRepository<MacLedger, Long> {

    Page<MacLedger> findByLedgerNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MacLedger> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MacLedger> findByLedgerNameContains(String key);

}
            
