package com.cellbeans.hspa.tinvpurchasegoodsreturnnote;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TinvPurchaseGoodsReturnNoteRepository extends JpaRepository<TinvPurchaseGoodsReturnNote, Long> {

    Page<TinvPurchaseGoodsReturnNote> findByPgrnIdContainsAndIsActiveTrueAndIsDeletedFalse(Long name, Pageable page);

    Page<TinvPurchaseGoodsReturnNote> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TinvPurchaseGoodsReturnNote> findByPgrnGoodsReturnNoContains(String key);

}
            
