package com.cellbeans.hspa.mstequipment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstEquipmentRepository extends JpaRepository<MstEquipment, Long> {

    Page<MstEquipment> findByEquipmentNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstEquipment> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstEquipment> findByEquipmentNameContains(String key);

}
            
