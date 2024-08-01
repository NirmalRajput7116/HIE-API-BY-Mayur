package com.cellbeans.hspa.mstcenterwaiver;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstCenterWaiverRepository extends JpaRepository<MstCenterWaiver, Long> {

    Page<MstCenterWaiver> findByCwNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstCenterWaiver> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstCenterWaiver> findByCwNameContains(String key);

}
            
