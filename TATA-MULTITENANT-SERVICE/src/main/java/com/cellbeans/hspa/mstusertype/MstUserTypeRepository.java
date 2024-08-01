package com.cellbeans.hspa.mstusertype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstUserTypeRepository extends JpaRepository<MstUserType, Long> {

    Page<MstUserType> findByUtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstUserType> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstUserType> findByUtNameContains(String key);

}
            
