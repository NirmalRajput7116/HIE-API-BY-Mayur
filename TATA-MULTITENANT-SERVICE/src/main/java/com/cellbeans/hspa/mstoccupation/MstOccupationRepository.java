package com.cellbeans.hspa.mstoccupation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstOccupationRepository extends JpaRepository<MstOccupation, Long> {

    Page<MstOccupation> findByOccupationNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByOccupationNameAsc(String name, Pageable page);

    Page<MstOccupation> findAllByIsActiveTrueAndIsDeletedFalseOrderByOccupationNameAsc(Pageable page);

    List<MstOccupation> findByOccupationNameContains(String key);

    List<MstOccupation> findAllByIsActiveTrueAndIsDeletedFalse();

    MstOccupation findByOccupationNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);
}
            
