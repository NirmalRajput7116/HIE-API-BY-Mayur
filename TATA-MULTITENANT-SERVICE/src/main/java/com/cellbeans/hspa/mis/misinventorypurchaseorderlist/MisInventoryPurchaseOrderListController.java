package com.cellbeans.hspa.mis.misinventorypurchaseorderlist;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.tinvpurchasepurchaseorder.TinvPurchasePurchaseOrder;
import com.cellbeans.hspa.tinvpurchasepurchaseorderitem.TinvPurchasePurchaseOrderItem;
import com.cellbeans.hspa.tinvpurchasepurchaseorderitem.TinvPurchasePurchaseOrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/mis_inventory_purchase_order_list")
public class MisInventoryPurchaseOrderListController {

    @Autowired
    TinvPurchasePurchaseOrderItemRepository tinvPurchasePurchaseOrderItemRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping("search/{unitList}/{searchFromDate}/{searchToDate}")
    public List<TinvPurchasePurchaseOrder> getListOfItemsDependsOnSerch(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchPurchaseOrderList searchPurchaseOrderList, @PathVariable Long[] unitList, @PathVariable String searchFromDate, @PathVariable String searchToDate) {
        TenantContext.setCurrentTenant(tenantName);
        long count;
        String queryInya = "";
        String queryCountInya = "";
        String itemStore = "";
        String itemSupplier = "";
        String unitSerch = "";
        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            unitSerch += " i.ppoUnitId.unitId in (" + values + ") ";
        }
        queryInya = "Select i from TinvPurchasePurchaseOrder i left join i.ppoPqId tpq left join tpq.pqPieId tpe Where  " + unitSerch;
        queryCountInya = "Select count(i.ppoId) from TinvPurchasePurchaseOrder i left join i.ppoPqId tpq left join tpq.pqPieId tpe Where  " + unitSerch;
        if (searchPurchaseOrderList.getSearchQuotationRefNo() != null) {
            queryInya += "  AND  tpq.pqQuatationRefNo like '%" + searchPurchaseOrderList.getSearchQuotationRefNo() + "%' ";
            queryCountInya += "  AND  tpq.pqQuatationRefNo like '%" + searchPurchaseOrderList.getSearchQuotationRefNo() + "%' ";
        }
        if (searchPurchaseOrderList.getSearchPieEnquiryNo() != null) {
            queryInya += " And tpe.pieEnquiryNo like '%" + searchPurchaseOrderList.getSearchPieEnquiryNo() + "%'";
            queryCountInya += " And tpe.pieEnquiryNo like '%" + searchPurchaseOrderList.getSearchPieEnquiryNo() + "%'";
        }
        if (searchPurchaseOrderList.getSearchPoStatus() == 1) {
            queryInya += "And i.ppoIsPoApprove=" + searchPurchaseOrderList.getSearchPoStatus() + " ";
//            queryCountInya += "and i.searchPoStatus" + searchPurchaseOrderList.getSearchPoStatus() + " ";
        }
        if (searchPurchaseOrderList.getSearchPoStatus() == 0) {
            queryInya += "And i.ppoIsPoApprove=" + searchPurchaseOrderList.getSearchPoStatus() + " ";
//            queryCountInya += "and i.searchPoStatus" + searchPurchaseOrderList.getSearchPoStatus()==0 + " ";
        }
        if (searchPurchaseOrderList.getSearchFromDate() == null) {
            String sDate1 = "1998/12/31";
            searchPurchaseOrderList.setSearchFromDate(sDate1);
        }
        if (searchPurchaseOrderList.getSearchToDate() == null) {
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchPurchaseOrderList.setSearchToDate(date);
        }
        if (searchPurchaseOrderList.getSearchStoreId().getStoreId() != 0)
            itemStore = " and i.ppoStoreId.storeId=" + searchPurchaseOrderList.getSearchStoreId().getStoreId() + " ";
        if (searchPurchaseOrderList.getSearchSupplierId().getSupplierId() != 0)
            itemSupplier = "AND i.ppoSupplierId.supplierId=" + searchPurchaseOrderList.getSearchSupplierId().getSupplierId() + " ";
        queryInya += itemStore + itemSupplier + " and date(i.createdDate) between   " + " '" + searchPurchaseOrderList.getSearchFromDate() + "' AND  " + "'" + searchPurchaseOrderList.getSearchToDate() + "' " + itemStore + "  " + itemSupplier + " order by i.ppoId desc";
        queryCountInya += itemStore + itemSupplier + " and date(i.createdDate) between   " + " '" + searchPurchaseOrderList.getSearchFromDate() + "' AND  " + "'" + searchPurchaseOrderList.getSearchToDate() + "' " + itemStore + "  " + itemSupplier + "";
        // System.out.println(queryInya);
        List<TinvPurchasePurchaseOrder> ItemList = new ArrayList<>();
        List<TinvPurchasePurchaseOrder> ItemListtemp = new ArrayList<>();
        if (searchPurchaseOrderList.isPrint()) {
            count = (Long) entityManager.createQuery(queryCountInya).getSingleResult();
            ItemListtemp = entityManager.createQuery(queryInya, TinvPurchasePurchaseOrder.class).setFirstResult(searchPurchaseOrderList.getOffset()).setMaxResults(searchPurchaseOrderList.getLimit()).getResultList();
            for (TinvPurchasePurchaseOrder objPo : ItemListtemp) {
                List<TinvPurchasePurchaseOrderItem> PPOIListTemp = new ArrayList<>();
                List<TinvPurchasePurchaseOrderItem> PPOIList = new ArrayList<>();
                PPOIList = tinvPurchasePurchaseOrderItemRepository.findAllByPpotPpoIdPpoIdAndIsActiveTrueAndIsDeletedFalse(objPo.getPpoId());
                for (TinvPurchasePurchaseOrderItem objPPOI : PPOIList) {
                    TinvPurchasePurchaseOrderItem newTemp = new TinvPurchasePurchaseOrderItem();
                    newTemp.setPpotId(objPPOI.getPpotId());
                    newTemp.setPpotItemId(objPPOI.getPpotItemId());
                    newTemp.setPpotName(objPPOI.getPpotName());
                    newTemp.setPpotItemRate(objPPOI.getPpotItemRate());
                    newTemp.setPpotItemQuantity(objPPOI.getPpotItemQuantity());
                    newTemp.setPpotItemRatePerQty(objPPOI.getPpotItemRatePerQty());
                    newTemp.setPpotItemFreeQuantity(objPPOI.getPpotItemFreeQuantity());
                    newTemp.setPpotItemTotalAmount(objPPOI.getPpotItemTotalAmount());
                    newTemp.setPpotItemNetAmount(objPPOI.getPpotItemNetAmount());
                    newTemp.setPpotItemConcessionPercenatge(objPPOI.getPpotItemConcessionPercenatge());
                    newTemp.setPpotItemConcessionAmount(objPPOI.getPpotItemConcessionAmount());
                    newTemp.setPpotTaxId(objPPOI.getPpotTaxId());
                    newTemp.setPpotItemTaxValue(objPPOI.getPpotItemTaxValue());
                    newTemp.setPpotItemTaxAmount(objPPOI.getPpotItemTaxAmount());
                    newTemp.setPpotItemSpecifiction(objPPOI.getPpotItemSpecifiction());
                    newTemp.setPpotItemRemark(objPPOI.getPpotItemRemark());
                    newTemp.setPpotIdtId(objPPOI.getPpotIdtId());
                    newTemp.setIsDeleted(objPPOI.getIsDeleted());
                    newTemp.setIsActive(objPPOI.getIsActive());
                    newTemp.setCreatedBy(objPPOI.getCreatedBy());
                    newTemp.setLastModifiedBy(objPPOI.getLastModifiedBy());
                    newTemp.setCreatedDate(objPPOI.getCreatedDate());
                    newTemp.setLastModifiedDate(objPPOI.getLastModifiedDate());
                    newTemp.setPpotUnitId(objPPOI.getPpotUnitId());
                    PPOIListTemp.add(newTemp);
                }
                objPo.setTinvPurchasePurchaseOrderItems(PPOIListTemp);
                ItemList.add(objPo);
            }

        } else {
            count = (Long) entityManager.createQuery(queryCountInya).getSingleResult();
            ItemList = entityManager.createQuery(queryInya, TinvPurchasePurchaseOrder.class).setFirstResult(searchPurchaseOrderList.getOffset()).setMaxResults(searchPurchaseOrderList.getLimit()).getResultList();

        }
        if (ItemList.size() > 0)
            ItemList.get(0).setCount(count);
        return ItemList;
    }

}
