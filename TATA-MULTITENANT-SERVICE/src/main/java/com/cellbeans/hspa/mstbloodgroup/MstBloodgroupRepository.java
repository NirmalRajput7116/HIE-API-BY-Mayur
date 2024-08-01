package com.cellbeans.hspa.mstbloodgroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstBloodgroupRepository extends JpaRepository<MstBloodgroup, Long> {

    Page<MstBloodgroup> findByBloodgroupNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByBloodgroupName(String name, Pageable page);

    Page<MstBloodgroup> findAllByIsActiveTrueAndIsDeletedFalseOrderByBloodgroupName(Pageable page);

    List<MstBloodgroup> findByBloodgroupNameContains(String key);

    MstBloodgroup findByBloodgroupNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
            
