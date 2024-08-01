package com.cellbeans.hspa.mstCitizenIdProof;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstCitizenIdProofRepository extends JpaRepository<MstCitizenIdProof, Long> {
    Page<MstCitizenIdProof> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<MstCitizenIdProof> findByCipNameContainsAndIsActiveTrueAndIsDeletedFalse(String qString, Pageable page);

    List<MstCitizenIdProof> findByCipNameContainsAndIsActiveTrueAndIsDeletedFalse(String CipName);

    List<MstCitizenIdProof> findByCipNameContains(String key);

}
