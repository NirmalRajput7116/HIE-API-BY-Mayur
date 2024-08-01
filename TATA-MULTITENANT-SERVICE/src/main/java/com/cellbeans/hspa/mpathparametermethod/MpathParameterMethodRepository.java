package com.cellbeans.hspa.mpathparametermethod;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MpathParameterMethodRepository extends JpaRepository<MpathParameterMethod, Long> {

    Page<MpathParameterMethod> findByPmNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MpathParameterMethod> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MpathParameterMethod> findByPmNameContains(String key);

}
            
