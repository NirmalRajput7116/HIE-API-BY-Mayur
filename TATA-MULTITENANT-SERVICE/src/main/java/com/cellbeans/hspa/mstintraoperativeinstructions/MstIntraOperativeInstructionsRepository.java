package com.cellbeans.hspa.mstintraoperativeinstructions;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstIntraOperativeInstructionsRepository extends JpaRepository<MstIntraOperativeInstructions, Long> {

    Page<MstIntraOperativeInstructions> findByIoiNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstIntraOperativeInstructions> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstIntraOperativeInstructions> findByIoiNameContains(String key);

}
            
