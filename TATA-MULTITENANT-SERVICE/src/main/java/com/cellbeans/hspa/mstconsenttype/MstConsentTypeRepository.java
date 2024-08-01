package com.cellbeans.hspa.mstconsenttype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstConsentTypeRepository extends JpaRepository<MstConsentType, Long> {

    Page<MstConsentType> findByCtNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByCtNameAsc(String name, Pageable page);

    Page<MstConsentType> findAllByIsActiveTrueAndIsDeletedFalseOrderByCtNameAsc(Pageable page);

    List<MstConsentType> findByCtNameContains(String key);

    MstConsentType findByCtNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
            
