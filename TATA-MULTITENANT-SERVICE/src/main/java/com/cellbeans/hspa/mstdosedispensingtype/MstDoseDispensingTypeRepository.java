package com.cellbeans.hspa.mstdosedispensingtype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstDoseDispensingTypeRepository extends JpaRepository<MstDoseDispensingType, Long> {

    Page<MstDoseDispensingType> findByDdtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstDoseDispensingType> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstDoseDispensingType> findByDdtNameContains(String key);

}
            
