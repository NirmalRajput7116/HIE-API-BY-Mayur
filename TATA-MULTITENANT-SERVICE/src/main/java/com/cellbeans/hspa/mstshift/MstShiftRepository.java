package com.cellbeans.hspa.mstshift;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstShiftRepository extends JpaRepository<MstShift, Long> {

    Page<MstShift> findByShiftNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstShift> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstShift> findByShiftNameContains(String key);

}
            
