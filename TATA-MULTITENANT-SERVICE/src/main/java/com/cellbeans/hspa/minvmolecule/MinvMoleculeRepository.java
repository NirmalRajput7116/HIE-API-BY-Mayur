package com.cellbeans.hspa.minvmolecule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MinvMoleculeRepository extends JpaRepository<MinvMolecule, Long> {

    Page<MinvMolecule> findByMoleculeNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MinvMolecule> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MinvMolecule> findByMoleculeNameContains(String key);

}
            
