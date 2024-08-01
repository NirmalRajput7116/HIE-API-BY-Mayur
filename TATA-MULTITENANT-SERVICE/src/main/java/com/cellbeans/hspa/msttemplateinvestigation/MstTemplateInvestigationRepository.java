package com.cellbeans.hspa.msttemplateinvestigation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstTemplateInvestigationRepository extends JpaRepository<MstTemplateInvestigation, Long> {

    Page<MstTemplateInvestigation> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstTemplateInvestigation> findAllByIsActiveTrueAndIsDeletedFalse();

    List<MstTemplateInvestigation> findAllByTiEtIdEtIdAndIsActiveTrueAndIsDeletedFalse(long id);

    Page<MstTemplateInvestigation> findAllByTiEtIdEtIdAndIsActiveTrueAndIsDeletedFalse(long id, Pageable page);

}
            
