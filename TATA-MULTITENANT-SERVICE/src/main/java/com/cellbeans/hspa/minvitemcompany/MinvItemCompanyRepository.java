package com.cellbeans.hspa.minvitemcompany;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MinvItemCompanyRepository extends JpaRepository<MinvItemCompany, Long> {

    Page<MinvItemCompany> findByIcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MinvItemCompany> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MinvItemCompany> findByIcNameContains(String key);

}
            
