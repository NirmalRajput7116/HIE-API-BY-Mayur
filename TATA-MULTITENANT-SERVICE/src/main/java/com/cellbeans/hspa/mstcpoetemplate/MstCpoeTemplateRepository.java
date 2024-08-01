package com.cellbeans.hspa.mstcpoetemplate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstCpoeTemplateRepository extends JpaRepository<MstCpoeTemplate, Long> {

    Page<MstCpoeTemplate> findByCtNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstCpoeTemplate> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstCpoeTemplate> findByCtNameContains(String key);

}
            
