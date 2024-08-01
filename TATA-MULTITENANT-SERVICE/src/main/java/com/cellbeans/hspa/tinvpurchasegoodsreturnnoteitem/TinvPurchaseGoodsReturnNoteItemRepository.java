package com.cellbeans.hspa.tinvpurchasegoodsreturnnoteitem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TinvPurchaseGoodsReturnNoteItemRepository extends JpaRepository<TinvPurchaseGoodsReturnNoteItem, Long> {


    /*	Page<TinvPurchaseGoodsReturnNoteItem> findByPgrniNameContainsAndIsActiveTrueAndIsDeletedFalse(String name,Pageable page); */
                    

	/*Page<TinvPurchaseGoodsReturnNoteItem> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);
            
        List<TinvPurchaseGoodsReturnNoteItem> findByPgrniItemIdContains(String key);*/

    List<TinvPurchaseGoodsReturnNoteItem> findAllByPgrniPgrnIdPgrnIdAndIsActiveTrueAndIsDeletedFalse(long id);

}
            
