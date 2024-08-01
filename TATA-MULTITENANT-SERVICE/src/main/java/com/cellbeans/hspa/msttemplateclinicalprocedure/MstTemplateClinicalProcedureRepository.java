package com.cellbeans.hspa.msttemplateclinicalprocedure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstTemplateClinicalProcedureRepository extends JpaRepository<MstTemplateClinicalProcedure, Long> {

    Page<MstTemplateClinicalProcedure> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstTemplateClinicalProcedure> findAllByIsActiveTrueAndIsDeletedFalse();

    List<MstTemplateClinicalProcedure> findAllByTcpEtIdEtIdAndIsActiveTrueAndIsDeletedFalse(long id);

    Page<MstTemplateClinicalProcedure> findAllByTcpEtIdEtIdAndIsActiveTrueAndIsDeletedFalse(long id, Pageable page);

}
            
