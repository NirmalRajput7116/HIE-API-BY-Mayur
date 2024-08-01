package com.cellbeans.hspa.mactax;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MacTaxRepository extends JpaRepository<MacTax, Long> {

    Page<MacTax> findByTaxNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MacTax> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MacTax> findByTaxNameContains(String key);

}
            
