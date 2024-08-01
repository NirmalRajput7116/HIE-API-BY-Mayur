package com.cellbeans.hspa.tinvpurchasegoodsreceivednote;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TinvPurchaseGoodsReceivedNoteRepository extends JpaRepository<TinvPurchaseGoodsReceivedNote, Long> {

    Page<TinvPurchaseGoodsReceivedNote> findByPgrnNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TinvPurchaseGoodsReceivedNote> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TinvPurchaseGoodsReceivedNote> findByPgrnGrnNoContains(String key);

}
            
