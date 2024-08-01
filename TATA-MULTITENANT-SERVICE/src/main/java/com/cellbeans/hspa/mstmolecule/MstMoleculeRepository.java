package com.cellbeans.hspa.mstmolecule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstMoleculeRepository extends JpaRepository<MstMolecule, Long> {

    Page<MstMolecule> findByMoleculeNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstMolecule> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstMolecule> findByMoleculeNameContains(String key);

}
            
