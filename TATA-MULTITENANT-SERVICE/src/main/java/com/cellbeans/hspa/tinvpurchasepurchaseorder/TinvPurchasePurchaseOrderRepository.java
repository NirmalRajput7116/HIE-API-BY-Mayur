package com.cellbeans.hspa.tinvpurchasepurchaseorder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TinvPurchasePurchaseOrderRepository extends JpaRepository<TinvPurchasePurchaseOrder, Long> {

    Page<TinvPurchasePurchaseOrder> findByPpoNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TinvPurchasePurchaseOrder> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TinvPurchasePurchaseOrder> findByPpoPqIdContains(String key);

    TinvPurchasePurchaseOrder findTopByOrderByPpoIdDesc();

    //By Neha
    default String makePONumber(String storeName) {
        TinvPurchasePurchaseOrder lastTinvPurchasePurchaseOrder = findTopByOrderByPpoIdDesc();
        StringBuilder poNumber = new StringBuilder(storeName + "/PO");
        try {
            if (lastTinvPurchasePurchaseOrder.getPpoNo() != null) {
                String number = lastTinvPurchasePurchaseOrder.getPpoNo().substring(lastTinvPurchasePurchaseOrder.getPpoNo().lastIndexOf("/") + 1);
                int newNumber = Integer.parseInt(number);
                poNumber.append("/" + (newNumber + 1));
            } else {
                poNumber.append("/1");
            }
        } catch (Exception e) {
            // System.out.println(e.getMessage());
            poNumber.append("/1");
        }
        return poNumber.toString();
    }

}
            
