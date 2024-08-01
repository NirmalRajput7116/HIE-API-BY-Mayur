package com.cellbeans.hspa.mpathparametertext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MpathParameterTextRepository extends JpaRepository<MpathParameterText, Long> {

    Page<MpathParameterText> findByPtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MpathParameterText> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

}
            
