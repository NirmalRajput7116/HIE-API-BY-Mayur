package com.cellbeans.hspa.mipdbedreleasechecklist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MipdBedReleaseChecklistRepository extends JpaRepository<MipdBedReleaseChecklist, Long> {

    Page<MipdBedReleaseChecklist> findByBrcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MipdBedReleaseChecklist> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MipdBedReleaseChecklist> findByBrcNameContains(String key);

}
            
