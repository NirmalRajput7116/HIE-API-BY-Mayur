package com.cellbeans.hspa.mstmodule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MstModuleRepository extends JpaRepository<MstModule, Long> {

    Page<MstModule> findByModuleNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstModule> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

}

