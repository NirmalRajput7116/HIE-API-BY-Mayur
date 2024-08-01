package com.cellbeans.hspa.invtax;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvTaxRepository extends JpaRepository<InvTax, Long> {

    Page<InvTax> findByTaxNameContainsAndIsActiveTrueAndIsDeletedFalseOrTaxCodeContainsAndIsActiveTrueAndIsDeletedFalseOrTaxValueContainsAndIsActiveTrueAndIsDeletedFalse(String name, String name1, String name2, Pageable page);

    Page<InvTax> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<InvTax> findByTaxNameContains(String key);

}
            
