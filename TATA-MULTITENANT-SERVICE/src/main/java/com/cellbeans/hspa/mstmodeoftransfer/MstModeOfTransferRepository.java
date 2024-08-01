package com.cellbeans.hspa.mstmodeoftransfer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstModeOfTransferRepository extends JpaRepository<MstModeOfTransfer, Long> {

    Page<MstModeOfTransfer> findByMotNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstModeOfTransfer> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstModeOfTransfer> findByMotNameContains(String key);
}
