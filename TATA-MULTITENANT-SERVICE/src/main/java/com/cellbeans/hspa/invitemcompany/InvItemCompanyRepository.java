package com.cellbeans.hspa.invitemcompany;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvItemCompanyRepository extends JpaRepository<InvItemCompany, Long> {

    Page<InvItemCompany> findByIcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<InvItemCompany> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<InvItemCompany> findByIcNameContains(String key);

}
            
