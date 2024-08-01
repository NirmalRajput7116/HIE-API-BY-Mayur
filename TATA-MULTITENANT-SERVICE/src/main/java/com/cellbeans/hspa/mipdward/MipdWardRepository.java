package com.cellbeans.hspa.mipdward;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MipdWardRepository extends JpaRepository<MipdWard, Long> {

    Page<MipdWard> findByWardNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MipdWard> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MipdWard> findByWardNameContains(String key);

    List<MipdWard> findAllByIsActiveTrueAndIsDeletedFalse();

    List<MipdWard> findAllByWardUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(Long unitid);

    List<MipdWard> findAllByWardUnitIdUnitIdEqualsAndWardClassIdClassIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long unitid, Long classid);

    List<MipdWard> findByWardFloorIdFloorIdAndIsActiveTrueAndIsDeletedFalse(Long floorId);

    List<MipdWard> findByWardFloorIdFloorIdAndWardUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(Long floorId, Long unitId);

}
            
