package com.cellbeans.hspa.mbilladvanceagainst;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MbillAdvanceAgainstRepository extends JpaRepository<MbillAdvanceAgainst, Long> {

    Page<MbillAdvanceAgainst> findByAaNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MbillAdvanceAgainst> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MbillAdvanceAgainst> findByAaNameContains(String key);

}
            
