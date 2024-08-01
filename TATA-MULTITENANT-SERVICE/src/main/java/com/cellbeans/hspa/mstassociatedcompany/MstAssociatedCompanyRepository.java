package com.cellbeans.hspa.mstassociatedcompany;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstAssociatedCompanyRepository extends JpaRepository<MstAssociatedCompany, Long> {

    Page<MstAssociatedCompany> findByAcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstAssociatedCompany> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstAssociatedCompany> findByAcNameContains(String key);

}
            
