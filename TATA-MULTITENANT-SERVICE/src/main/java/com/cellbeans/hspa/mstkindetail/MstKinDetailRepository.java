package com.cellbeans.hspa.mstkindetail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstKinDetailRepository extends JpaRepository<MstKinDetail, Long> {

    Page<MstKinDetail> findByKdNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstKinDetail> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstKinDetail> findByKdNameContains(String key);

}
            
