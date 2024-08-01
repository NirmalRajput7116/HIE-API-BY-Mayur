package com.cellbeans.hspa.invitem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvItemRepository extends JpaRepository<InvItem, Long> {

    Page<InvItem> findByItemNameContainsAndIsActiveTrueAndIsDeletedFalseOrItemBrandNameContainsAndIsActiveTrueAndIsDeletedFalseOrItemCodeContainsAndIsActiveTrueAndIsDeletedFalseOrItemIgIdIgNameContainsAndIsActiveTrueAndIsDeletedFalseOrItemIcIdIcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, String name1, String name3, String name2, String name4, Pageable page);

    @Query(value = "select distinct item from InvItem item where isShowPharmacy = true")
    List<InvItem> findDistinctByItemNameAndIsShowPharmacyTrue();

    Page<InvItem> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<InvItem> findAllByIsActiveTrueAndIsDeletedFalse();

    List<InvItem> findByItemNameContains(String key);

    List<InvItem> findByItemNameContainsAndItemStoreIdStoreIdAndIsActiveTrueAndIsDeletedFalse(String name, Long itemStoreId);

    List<InvItem> findByItemNameContainsOrItemCodeContainsOrItemBrandNameContainsAndIsActiveTrueAndIsDeletedFalse(String key, String key1, String key2);
    //Search Tables
//    @Query(value = "select distinct item from inv_item item where is_show_pharmacy = true and is_active = true and is_deleted = false and item_name like '%":itemname"' ",nativeQuery = true)
//    List<InvItem> findByItemName( @Param("itemname") String itemname);

    List<InvItem> findByItemNameContainsAndIsShowPharmacyTrueAndIsActiveTrueAndIsDeletedFalse(String data, Pageable page);

    List<InvItem> findByIsShowPharmacyTrueAndIsActiveTrueAndIsDeletedFalse(Pageable page);
    //Page<InvItem> findAllByItemUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(Pageable page, long unitId);
    //Page<InvItem> findByItemNameContainsAndItemUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalseOrItemBrandNameContainsAndItemUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalseOrItemCodeContainsAndItemUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalseOrItemIgIdIgNameContainsAndItemUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalseOrItemIcIdIcNameContainsAndItemUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(String name, String name1, String name3, String name2, String name4, long unitId, Pageable page);

    // vijay P.  igName
    List<InvItem> findAllByItemIgIdIgNameAndItemNameContainsAndIsActiveTrueAndIsDeletedFalse(String itemCategory, String itemName);

    //emr
//    List<InvItem> findByItemNameContainsOrItemGenericNameContainsAndIsShowPharmacyTrueAndIsActiveTrueAndIsDeletedFalseOrderByItemNameAsc(String data, String dataq, Pageable page);

    //emr Nayan
    @Query(value = "SELECT * FROM inv_item ii WHERE ii.item_name LIKE %:data% AND ii.is_active = TRUE AND ii.is_deleted = FALSE " , nativeQuery = true)
    List<InvItem> findByItemNameContainsOrItemGenericNameContainsAndIsShowPharmacyTrueAndIsActiveTrueAndIsDeletedFalseOrderByItemNameAsc(@Param("data") String data);
}
            
