package com.cellbeans.hspa.mstmistype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstMisTypeRepository extends JpaRepository<MstMisType, Long> {

    Page<MstMisType> findByMtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstMisType> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstMisType> findByMtNameContains(String key);

}
            
