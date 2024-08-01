package com.cellbeans.hspa.mstbankbranch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstBankBranchRepository extends JpaRepository<MstBankBranch, Long> {

    Page<MstBankBranch> findByBbNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstBankBranch> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstBankBranch> findByBbNameContains(String key);

}
            
