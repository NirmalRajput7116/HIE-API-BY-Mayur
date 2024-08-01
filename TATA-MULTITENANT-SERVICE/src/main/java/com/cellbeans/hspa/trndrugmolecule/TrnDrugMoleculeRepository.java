package com.cellbeans.hspa.trndrugmolecule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrnDrugMoleculeRepository extends JpaRepository<TrnDrugMolecule, Long> {

    Page<TrnDrugMolecule> findByDmDrugIdDrugNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TrnDrugMolecule> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);
//        List<TrnDrugMolecule> findByDrugNameContains(String key);
}
            
