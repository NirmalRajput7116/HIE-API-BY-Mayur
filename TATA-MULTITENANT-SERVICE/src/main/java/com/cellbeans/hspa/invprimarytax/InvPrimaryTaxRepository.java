package com.cellbeans.hspa.invprimarytax;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvPrimaryTaxRepository extends JpaRepository<InvPrimaryTax, Long> {

    Page<InvPrimaryTax> findByPtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, String name1, String name2, Pageable page);

    Page<InvPrimaryTax> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<InvPrimaryTax> findByPtNameContains(String key);

}
            
