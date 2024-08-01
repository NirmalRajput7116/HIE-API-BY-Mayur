package com.cellbeans.hspa.mstemploymentstatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstEmploymentStatusRepository extends JpaRepository<MstEmploymentStatus, Long> {

    Page<MstEmploymentStatus> findByEsNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByEsName(String name, Pageable page);

    Page<MstEmploymentStatus> findAllByIsActiveTrueAndIsDeletedFalseOrderByEsName(Pageable page);

    List<MstEmploymentStatus> findByEsNameContains(String key);

}
            
