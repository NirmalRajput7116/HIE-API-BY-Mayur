package com.cellbeans.hspa.mstsponsorcombination;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstSponsorCombinationRepository extends JpaRepository<MstSponsorCombination, Long> {

    Page<MstSponsorCombination> findByScNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstSponsorCombination> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstSponsorCombination> findByScNameContains(String key);

}
            
