package com.cellbeans.hspa.mactaxtype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MacTaxTypeRepository extends JpaRepository<MacTaxType, Long> {

    Page<MacTaxType> findByTtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MacTaxType> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MacTaxType> findByTtNameContains(String key);

}
            
