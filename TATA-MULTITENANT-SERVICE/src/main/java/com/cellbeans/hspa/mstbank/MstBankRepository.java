package com.cellbeans.hspa.mstbank;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstBankRepository extends JpaRepository<MstBank, Long> {

    Page<MstBank> findByBankNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstBank> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstBank> findByBankNameContains(String key);

}
            
