package com.cellbeans.hspa.mipddischargetype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MipdDischargeTypeRepository extends JpaRepository<MipdDischargeType, Long> {

    Page<MipdDischargeType> findByDtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MipdDischargeType> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MipdDischargeType> findByDtNameContains(String key);

}
            
