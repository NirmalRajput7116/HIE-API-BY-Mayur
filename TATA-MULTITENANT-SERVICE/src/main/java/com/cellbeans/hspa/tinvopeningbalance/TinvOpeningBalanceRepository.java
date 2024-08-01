package com.cellbeans.hspa.tinvopeningbalance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TinvOpeningBalanceRepository extends JpaRepository<TinvOpeningBalance, Long> {

    Page<TinvOpeningBalance> findByObTotalAmountContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TinvOpeningBalance> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TinvOpeningBalance> findByObTotalAmountContains(String key);

}
            
