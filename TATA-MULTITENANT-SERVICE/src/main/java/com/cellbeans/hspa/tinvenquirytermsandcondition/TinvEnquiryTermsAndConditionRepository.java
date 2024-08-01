package com.cellbeans.hspa.tinvenquirytermsandcondition;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TinvEnquiryTermsAndConditionRepository extends JpaRepository<TinvEnquiryTermsAndCondition, Long> {

    Page<TinvEnquiryTermsAndCondition> findByEtacNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TinvEnquiryTermsAndCondition> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TinvEnquiryTermsAndCondition> findByEtacPieIdContains(String key);

    List<TinvEnquiryTermsAndCondition> findAllByEtacPieIdPieIdAndIsActiveTrueAndIsDeletedFalse(Long key);

}
            
