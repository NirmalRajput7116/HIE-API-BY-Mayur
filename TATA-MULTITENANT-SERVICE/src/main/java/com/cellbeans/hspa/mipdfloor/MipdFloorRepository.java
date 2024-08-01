package com.cellbeans.hspa.mipdfloor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MipdFloorRepository extends JpaRepository<MipdFloor, Long> {

    Page<MipdFloor> findByFloorNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MipdFloor> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MipdFloor> findByFloorNameContains(String key);

}
            
