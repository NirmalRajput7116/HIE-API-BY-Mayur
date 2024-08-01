package com.cellbeans.hspa.mpathparameter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MpathParameterRepository extends JpaRepository<MpathParameter, Long> {

    Page<MpathParameter> findByParameterNameContainsAndIsActiveTrueAndIsDeletedFalseOrParameterCodeContainsAndIsActiveTrueAndIsDeletedFalseOrParameterPrintNameContainsAndIsActiveTrueAndIsDeletedFalseOrParameterParameterUnitIdPuNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, String name1, String name2, String name3, Pageable page);

    Page<MpathParameter> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MpathParameter> findAllByIsActiveTrueAndIsDeletedFalse();

    List<MpathParameter> findByParameterNameContains(String key);

}
            
