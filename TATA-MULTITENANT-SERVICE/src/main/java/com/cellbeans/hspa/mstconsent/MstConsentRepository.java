package com.cellbeans.hspa.mstconsent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstConsentRepository extends JpaRepository<MstConsent, Long> {

    Page<MstConsent> findByConsentNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByConsentNameAsc(String name, Pageable page);

    Page<MstConsent> findAllByIsActiveTrueAndIsDeletedFalseOrderByConsentNameAsc(Pageable page);

    List<MstConsent> findByConsentNameContains(String key);

    Page<MstConsent> findAllByConsentDepartmentIdDepartmentNameAndIsActiveTrueAndIsDeletedFalse(String departmentName, Pageable page);

    MstConsent findByConsentNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
            
