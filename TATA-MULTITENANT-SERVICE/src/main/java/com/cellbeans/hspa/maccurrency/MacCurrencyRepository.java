package com.cellbeans.hspa.maccurrency;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MacCurrencyRepository extends JpaRepository<MacCurrency, Long> {

    Page<MacCurrency> findByCurrencyNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MacCurrency> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MacCurrency> findByCurrencyNameContains(String key);

    MacCurrency findByIsCurrencyDefaultTrueAndIsActiveTrueAndIsDeletedFalse();

}
            
