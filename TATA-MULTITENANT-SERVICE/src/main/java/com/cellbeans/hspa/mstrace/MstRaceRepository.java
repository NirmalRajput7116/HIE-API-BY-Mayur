package com.cellbeans.hspa.mstrace;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstRaceRepository extends JpaRepository<MstRace, Long> {

    Page<MstRace> findByRaceNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByRaceName(String name, Pageable page);

    Page<MstRace> findAllByIsActiveTrueAndIsDeletedFalseOrderByRaceName(Pageable page);

    List<MstRace> findByRaceNameContains(String key);

}
            
