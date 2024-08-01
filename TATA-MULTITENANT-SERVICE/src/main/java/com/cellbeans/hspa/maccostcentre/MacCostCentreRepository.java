package com.cellbeans.hspa.maccostcentre;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MacCostCentreRepository extends JpaRepository<MacCostCentre, Long> {

    Page<MacCostCentre> findByCcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MacCostCentre> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MacCostCentre> findByCcNameContains(String key);

}
            
