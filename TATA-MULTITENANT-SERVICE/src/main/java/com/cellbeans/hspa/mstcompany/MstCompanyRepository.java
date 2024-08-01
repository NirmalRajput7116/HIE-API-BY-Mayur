package com.cellbeans.hspa.mstcompany;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstCompanyRepository extends JpaRepository<MstCompany, Long> {

    Page<MstCompany> findByCompanyNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstCompany> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstCompany> findByCompanyNameContains(String key);

    List<MstCompany> findByCompanyCtIdCtIdEquals(Long key);

}
            
