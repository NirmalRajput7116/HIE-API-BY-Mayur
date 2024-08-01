package com.cellbeans.hspa.tinvopeningbalanceitem;
//import com.cellbeans.hspa.tnstinpatient.TnstItemIssue;

import com.cellbeans.hspa.tnstinpatient.TnstItemIssue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

public interface TinvOpeningBalanceItemRepository extends JpaRepository<TinvOpeningBalanceItem, Long> {

    List<TinvOpeningBalanceItem> findAllByObiStoreIdStoreIdAndIsDeletedFalse(Long storeId);

    Page<TinvOpeningBalanceItem> findByObiItemNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    @Query(value = "SELECT tobi from TinvOpeningBalanceItem tobi where ((tobi.obiItemName  like CONCAT('%',:searchData,'%')) AND (tobi.obiItemId.isShowPharmacy=true AND tobi.obiItemQuantity >0))")
    List<TinvOpeningBalanceItem> findByItemByForEMR(@Param("searchData") String searchData);

    @Query(value = "SELECT DISTINCT tobi from TinvOpeningBalanceItem tobi where tobi.obiItemId.isShowPharmacy = true")
    List<TinvOpeningBalanceItem> findDistinctForDrugAllergies();

    //List<TinvOpeningBalanceItem> findDistinctByObiItemNameAndIsShowPharmacyTrueAndIsActiveTrueAndIsDeletedFalse(String name);
    List<TinvOpeningBalanceItem> findByObiItemNameAndObiItemIdIsShowPharmacyTrueAndIsActiveTrueAndIsDeletedFalse(String name);

    Page<TinvOpeningBalanceItem> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TinvOpeningBalanceItem> findByObiItemIdContains(String key);
    //@Query(value = "select obi from tinv_opening_balance_item obi where((obi.obi_item_name=:data OR obi.obi_item_batch_code=:data OR obi.obi_item_name=:data )AND (obi.obi_item_expiry_date > :appDate AND obi.obi_item_quantity >0)",nativeQuery = true)
	/*@Query(value = "select obi.* from tinv_opening_balance_item as obi INNER JOIN inv_item as item ON obi.obi_item_id = item.item_id where((obi.obi_item_name like %:data% OR obi.obi_item_batch_code like %:data% Or obi_item_code like %:data% AND item.item_code like %:data%) AND (obi.obi_item_expiry_date > :appDate AND obi.obi_item_quantity >0 AND item.is_show_pharmacy = True ))", nativeQuery = true)
	List<Object> findByItemNameANDaboveDate(@Param("data") String data, @Param("appDate") String appDate);*/

    @Query(value = "SELECT tobi from TinvOpeningBalanceItem tobi where openingBalanceUnitId.unitId=:unitId and ( (tobi.obiItemId.itemId like CONCAT('%',:searchData,'%')OR tobi.obiItemId.itemGenericName like CONCAT('%',:searchData,'%')OR tobi.obiItemName like CONCAT('%',:searchData,'%') OR tobi.obiItemBatchCode like CONCAT('%',:searchData,'%') OR tobi.obiItemCode like CONCAT('%',:searchData,'%') ) AND (tobi.obiItemId.isShowPharmacy=true AND tobi.obiItemQuantity >0 AND tobi.obiItemExpiryDate>:date And tobi.isActive=True And tobi.isDeleted=False))")
    List<TinvOpeningBalanceItem> findByItemByForPhatmacy(@Param("searchData") String searchData, @Param("date") @Temporal(TemporalType.DATE) Date date, @Param("unitId") long unitId, Pageable pageable);

    @Query(value = "SELECT tobi from TinvOpeningBalanceItem tobi where (tobi.obiItemId.itemId=:id AND tobi.obiItemQuantity>0 AND tobi.obiItemExpiryDate>:date AND tobi.isActive=True And tobi.isDeleted=False)")
    List<TinvOpeningBalanceItem> findAllByObiItemIdItemIdAndIsActiveTrueAndIsDeletedFalse(@Param("id") Long id, @Param("date") @Temporal(TemporalType.DATE) Date date);

    @Query(value = "SELECT tobi from TinvOpeningBalanceItem tobi where (tobi.obiPgrnId.pgrnId=:grnId AND tobi.isActive=True And tobi.isDeleted=False)")
    List<TinvOpeningBalanceItem> findAllByObiPgrnIdPgrnIdAndIsAndActiveTrueAndIsDeletedFalse(@Param("grnId") Long grnId);

    @Query(value = "SELECT tobi from TinvOpeningBalanceItem tobi where (tobi.obiItemId.itemId=:id and  tobi.openingBalanceUnitId.unitId=:unitId and tobi.obiItemQuantity>0 AND tobi.obiItemExpiryDate>:date AND tobi.isActive=True And tobi.isDeleted=False and tobi.isApproved=True )")
    List<TinvOpeningBalanceItem> findAllByObiItemIdItemIdAndOpeningBalanceUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(@Param("id") Long id, @Param("date") @Temporal(TemporalType.DATE) Date date, @Param("unitId") Long unitId);

    List<TinvOpeningBalanceItem> findAllByObiPgrnIdPgrnIdAndIsActiveTrueAndIsDeletedFalse(Long key);

    //Rohan
    @Query(value = "SELECT * from tinv_opening_balance_item tobi,mst_unit m where m.unit_id=tobi.opening_balance_unit_id and date(tobi.obi_item_expiry_date)<=:date AND  tobi.is_active = 1 and tobi.is_deleted = 0 and  tobi.opening_balance_unit_id in (:unitList)  \n#pageable\n", countQuery = "Select count(*) from tinv_opening_balance_item tobi,mst_unit m where m.unit_id=tobi.opening_balance_unit_id and date(tobi.obi_item_expiry_date)<=:date AND  tobi.is_active = 1 and tobi.is_deleted = 0 and  tobi.opening_balance_unit_id in (:unitList)", nativeQuery = true)
    Page<TinvOpeningBalanceItem> findAllByObiItemExpiryDateAndIsActiveTrueAndIsDeletedFalse(@Param("date") @Temporal(TemporalType.DATE) Date date, @Param("unitList") Long[] unitList, Pageable page);


	/*@Query(value = "SELECT tobi from TinvOpeningBalanceItem tobi where ( (tobi.obiItemId.itemId like CONCAT('%',:searchData,'%') AND  tobi.obiItemQuantity >0 And tobi.isActive=True And tobi.isDeleted=False))")
	List<TinvOpeningBalanceItem> findByItemByForId( @Param("searchData") Long searchData);
*/

    @Query(value = "SELECT tobi from TinvOpeningBalanceItem tobi where ( (tobi.obiItemId.itemId =:searchData ) AND ((tobi.obiItemId.isShowPharmacy=true AND tobi.obiItemQuantity >0 AND tobi.obiItemExpiryDate>:date And tobi.isActive=True And tobi.isDeleted=False and tobi.isApproved=True) Or (tobi.obiItemId.isShowPharmacy=false AND tobi.obiItemQuantity >0  And tobi.isActive=True And tobi.isDeleted=False and tobi.isApproved=True )))")
    List<TinvOpeningBalanceItem> findByItemByForId(@Param("searchData") Long searchData, @Param("date") @Temporal(TemporalType.DATE) Date date);

    @Query(value = "SELECT tobi from TinvOpeningBalanceItem tobi where ( (tobi.obiItemId.itemId =:searchData ) AND ((tobi.obiItemId.isShowPharmacy=true AND tobi.obiItemQuantity >0 AND tobi.obiItemExpiryDate>:date And tobi.isActive=True And tobi.isDeleted=False and tobi.isApproved=True ) Or (tobi.obiItemId.isShowPharmacy=false AND tobi.obiItemQuantity >0  And tobi.isActive=True And tobi.isDeleted=False and tobi.isApproved=True ))) and tobi.openingBalanceUnitId.unitId =:unitId order by tobi.obiItemExpiryDate asc ")
    List<TinvOpeningBalanceItem> findByItemByForIdByUnitId(@Param("searchData") Long searchData, @Param("date") @Temporal(TemporalType.DATE) Date date, @Param("unitId") Long unitId);

    @Query(value = "SELECT tobi from TinvOpeningBalanceItem tobi where ( (tobi.obiItemId.itemId =:searchData ) AND ((tobi.obiItemId.isShowPharmacy=true AND tobi.obiItemQuantity >0 AND tobi.obiItemExpiryDate>:date And tobi.isActive=True And tobi.isDeleted=False and tobi.isApproved=True ) Or (tobi.obiItemId.isShowPharmacy=false AND tobi.obiItemQuantity >0  And tobi.isActive=True And tobi.isDeleted=False and tobi.isApproved=True ))) and tobi.openingBalanceUnitId.unitId =:unitId and tobi.obiStoreId.storeId =:storeId order by tobi.obiItemExpiryDate asc ")
    List<TinvOpeningBalanceItem> findByItemByForIdByUnitIdAndStoreId(@Param("searchData") Long searchData, @Param("date") @Temporal(TemporalType.DATE) Date date, @Param("unitId") Long unitId, @Param("storeId") Long storeId);

    TinvOpeningBalanceItem findAllByObiStoreIdStoreIdAndObiItemBatchCodeAndIsActiveTrueAndIsDeletedFalse(Long id, String batchCode);

    TinvOpeningBalanceItem findAllByObiStoreIdStoreIdAndOpeningBalanceUnitIdUnitIdAndObiItemBatchCodeAndIsActiveTrueAndIsDeletedFalse(Long id, Long unitId, String batchCode);

    TinvOpeningBalanceItem findAllByObiStoreIdStoreIdAndOpeningBalanceUnitIdUnitIdAndObiItemBatchCodeAndObiobIdIsNullAndIsActiveTrueAndIsDeletedFalse(Long id, Long unitId, String batchCode);

    TinvOpeningBalanceItem findAllByObiStoreIdStoreIdAndOpeningBalanceUnitIdUnitIdAndObiItemIdItemIdAndObiItemBatchCodeAndObiobIdIsNullAndIsActiveTrueAndIsDeletedFalse(Long id, Long unitId, Long itemId, String batchCode);

    TinvOpeningBalanceItem findTopByObiStoreIdStoreIdAndOpeningBalanceUnitIdUnitIdAndObiItemBatchCodeAndObiobIdIsNullAndIsActiveTrueAndIsDeletedFalse(Long id, Long unitId, String batchCode);

    List<TinvOpeningBalanceItem> findAllByObiobIdObIdAndIsActiveTrueAndIsDeletedFalse(Long key);

    //NST Search by ItemName and Store Id
    List<TinvOpeningBalanceItem> findByObiItemNameContainsAndObiStoreIdStoreIdAndIsActiveTrueAndIsDeletedFalse(String itemName, Long storeId);

/*
	default Boolean updateStoreAndQuanatityOnReturn(List<TnstItemIssue> tnstItemIssueNew, TinvOpeningBalanceItemRepository tinvOpeningBalanceItemRepository){
		for(TnstItemIssue ob: tnstItemIssueNew){
			ob.getIiItemId().setObiItemQuantity(ob.getIiItemId().getObiItemQuantity() + ob.getReturnQty());
			tinvOpeningBalanceItemRepository.save(ob.getIiItemId());
		}
		return  true;
	}*/

    //vijay
    List<TinvOpeningBalanceItem> findAllByObiStoreIdStoreNameAndObiItemIdItemNameContainsAndIsActiveTrueAndIsDeletedFalse(String storeName, String itemName);

    TinvOpeningBalanceItem findAllByObiIdAndIsActiveTrueAndIsDeletedFalse(Long obiId);
    //NST Search by ItemName and Store Id

    default Boolean updateStoreAndQuanatityOnReturn(List<TnstItemIssue> tnstItemIssueNew, TinvOpeningBalanceItemRepository tinvOpeningBalanceItemRepository) {
        for (TnstItemIssue ob : tnstItemIssueNew) {
            ob.getIiItemId().setObiItemQuantity(ob.getIiItemId().getObiItemQuantity() + ob.getReturnQty());
            tinvOpeningBalanceItemRepository.save(ob.getIiItemId());
        }
        return true;
    }
//    @Query(value = "SELECT tobi from TinvOpeningBalanceItem tobi where tobi.openingBalanceUnitId.unitId=:unitId and tobi.obiStoreId.storeId=:storeId and ( (tobi.obiItemId.itemCode like CONCAT('%',:searchData,'%')  OR tobi.obiItemId.itemGenericId.genericName like CONCAT('%',:searchData,'%') OR tobi.obiItemId.itemName like CONCAT('%',:searchData,'%') OR tobi.obiItemBatchCode like CONCAT('%',:searchData,'%')) AND (tobi.obiItemId.isShowPharmacy=true AND tobi.obiItemQuantity >0 AND tobi.obiItemExpiryDate>:date And tobi.isActive=True And tobi.isDeleted=False and tobi.isApproved=True))")
//    List<TinvOpeningBalanceItem> findByItemByForPhatmacyByStoreId(@Param("searchData") String searchData, @Param("storeId") long storeId, @Param("date") @Temporal(TemporalType.DATE) Date date, @Param("unitId") long unitId, Pageable pageable);

    @Query(value = "SELECT tobi from TinvOpeningBalanceItem tobi where tobi.openingBalanceUnitId.unitId=:unitId and tobi.obiStoreId.storeId=:storeId and ( (tobi.obiItemId.itemCode like CONCAT('%',:searchData,'%')  OR tobi.obiItemId.itemName like CONCAT('%',:searchData,'%') OR tobi.obiItemBatchCode like CONCAT('%',:searchData,'%')) AND (tobi.obiItemId.isShowPharmacy=true AND tobi.obiItemQuantity >0 AND tobi.obiItemExpiryDate>:date And tobi.isActive=True And tobi.isDeleted=False and tobi.isApproved=True))")
    List<TinvOpeningBalanceItem> findByItemByForPhatmacyByStoreId(@Param("searchData") String searchData, @Param("storeId") long storeId, @Param("date") @Temporal(TemporalType.DATE) Date date, @Param("unitId") long unitId, Pageable pageable);

    Page<TinvOpeningBalanceItem> findAllByOpeningBalanceUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(long unitId, Pageable page);

    @Query(value = "SELECT * FROM tinv_opening_balance_item tobi WHERE tobi.is_active = 1 AND tobi.is_approved = 1 AND tobi.obi_store_id=:storeId AND tobi.obi_item_id IN (:itemIdList)", nativeQuery = true)
    List<TinvOpeningBalanceItem> findAllByObiStoreIdAndObiItemIdAndIsActiveTrueAndIsDeletedFalse(@Param("storeId") long storeId, @Param("itemIdList") String itemIdList);

    @Query(value = "SELECT tobi from TinvOpeningBalanceItem tobi where tobi.openingBalanceUnitId.unitId=:unitId and tobi.obiStoreId.storeId=:storeId and ( (tobi.obiItemId.itemCode like CONCAT('%',:searchData,'%')  OR tobi.obiItemId.itemName like CONCAT('%',:searchData,'%') OR tobi.obiItemBatchCode like CONCAT('%',:searchData,'%')) AND (tobi.obiItemQuantity >0 And tobi.isActive=True And tobi.isDeleted=False and tobi.isApproved=True))")
    List<TinvOpeningBalanceItem> findByItemByForStockConsumptionByStoreId(@Param("searchData") String searchData, @Param("storeId") long storeId, @Param("unitId") long unitId, Pageable pageable);

}

