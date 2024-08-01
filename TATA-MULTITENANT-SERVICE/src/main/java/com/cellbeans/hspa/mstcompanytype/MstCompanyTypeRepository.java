package com.cellbeans.hspa.mstcompanytype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstCompanyTypeRepository extends JpaRepository<MstCompanyType, Long> {

    Page<MstCompanyType> findByCtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstCompanyType> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstCompanyType> findByCtNameContains(String key);

}
            
