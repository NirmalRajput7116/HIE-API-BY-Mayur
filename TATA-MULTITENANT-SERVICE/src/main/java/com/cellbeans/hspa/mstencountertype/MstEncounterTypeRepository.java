package com.cellbeans.hspa.mstencountertype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstEncounterTypeRepository extends JpaRepository<MstEncounterType, Long> {

    Page<MstEncounterType> findByEtNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByEtNameAsc(String name, Pageable page);

    Page<MstEncounterType> findAllByIsActiveTrueAndIsDeletedFalseOrderByEtNameAsc(Pageable page);

    List<MstEncounterType> findByEtNameContains(String key);

    MstEncounterType findByEtNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
            
