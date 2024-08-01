package com.cellbeans.hspa.tinvpurchaseitemenquiry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TinvPurchaseItemEnquiryRepository extends JpaRepository<TinvPurchaseItemEnquiry, Long> {

    Page<TinvPurchaseItemEnquiry> findByPicNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TinvPurchaseItemEnquiry> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TinvPurchaseItemEnquiry> findByPieEnquiryNoContains(String key);

    List<TinvPurchaseItemEnquiry> findByPieEnquiryNoContainsOrPieStoreIdStoreNameContainsAndIsActiveTrueAndIsDeletedFalse(String pieEqNumber, String storeName);

    //List<TinvPurchaseItemEnquiry> findByPieEnquiryNoContainsOrPieStoreIdStoreNameContainsAndPieUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalseAnd(String pieEqNumber, String storeName, long unitId);
    @Query(value = "select u from TinvPurchaseItemEnquiry u where (u.pieEnquiryNo like %:data% or u.pieStoreId.storeName like %:data%) and u.pieUnitId.unitId =:unitId and u.pieIsApproved =1 and u.pieIsRejected =0 and u.isActive =1 and u.isDeleted = 0", nativeQuery = false)
    List<TinvPurchaseItemEnquiry> findByPieEnquiryNoContainsOrPieStoreIdStoreNameContainsAndPieUnitIdUnitId(@Param("data") String data, @Param("unitId") long unitId);

    TinvPurchaseItemEnquiry findTopByOrderByPieIdDesc();

    default String makeEQNumber(String storeName) {
        TinvPurchaseItemEnquiry lastPurchaseItemEnquiry = findTopByOrderByPieIdDesc();
        StringBuilder eqNumber = new StringBuilder("EQ/");
        try {
            if (lastPurchaseItemEnquiry.getPieEnquiryNo() != null) {
                String number = lastPurchaseItemEnquiry.getPieEnquiryNo().substring(lastPurchaseItemEnquiry.getPieEnquiryNo().lastIndexOf("/") + 1);
                int newNumber = Integer.parseInt(number);
                eqNumber.append(storeName).append("/" + (newNumber + 1));
            } else {
                eqNumber.append(storeName).append("/1");
            }
        } catch (Exception e) {
            // System.out.println(e.getMessage());
            eqNumber.append(storeName).append("/1");
        }
        return eqNumber.toString();
    }

    //By Neha
    default String makeEQNumberNew(String storeName) {
        TinvPurchaseItemEnquiry lastPurchaseItemEnquiry = findTopByOrderByPieIdDesc();
        StringBuilder eqNumber = new StringBuilder(storeName + "/EQ");
        try {
            if (lastPurchaseItemEnquiry.getPieEnquiryNo() != null) {
                String number = lastPurchaseItemEnquiry.getPieEnquiryNo().substring(lastPurchaseItemEnquiry.getPieEnquiryNo().lastIndexOf("/") + 1);
                int newNumber = Integer.parseInt(number);
                eqNumber.append("/" + (newNumber + 1));
            } else {
                eqNumber.append("/1");
            }
        } catch (Exception e) {
            // System.out.println(e.getMessage());
            eqNumber.append("/1");
        }
        return eqNumber.toString();
    }

}
            
