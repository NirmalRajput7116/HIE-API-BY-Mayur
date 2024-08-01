package com.cellbeans.hspa.msttemplateprescription;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstTemplatePrescriptionRepository extends JpaRepository<MstTemplatePrescription, Long> {

    Page<MstTemplatePrescription> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstTemplatePrescription> findAllByIsActiveTrueAndIsDeletedFalse();

    List<MstTemplatePrescription> findAllByTpEtIdEtIdAndIsActiveTrueAndIsDeletedFalse(long id);

    Page<MstTemplatePrescription> findAllByTpEtIdEtIdAndIsActiveTrueAndIsDeletedFalse(long id, Pageable page);

}
            
