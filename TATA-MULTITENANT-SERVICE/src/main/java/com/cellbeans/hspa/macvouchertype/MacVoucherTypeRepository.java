package com.cellbeans.hspa.macvouchertype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MacVoucherTypeRepository extends JpaRepository<MacVoucherType, Long> {

    Page<MacVoucherType> findByVtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MacVoucherType> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MacVoucherType> findByVtNameContains(String key);

}
            
