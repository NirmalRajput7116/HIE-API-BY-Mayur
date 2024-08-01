package com.cellbeans.hspa.tinvpurchasegoodsreceivednoteitem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TinvPurchaseGoodsReceivedNoteItemRepository extends JpaRepository<TinvPurchaseGoodsReceivedNoteItem, Long> {

    Page<TinvPurchaseGoodsReceivedNoteItem> findByPgrniNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TinvPurchaseGoodsReceivedNoteItem> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TinvPurchaseGoodsReceivedNoteItem> findByPgrniPgrnIdContains(String key);

}
            
