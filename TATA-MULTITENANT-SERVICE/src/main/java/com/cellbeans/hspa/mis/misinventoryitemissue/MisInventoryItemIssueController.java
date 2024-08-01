package com.cellbeans.hspa.mis.misinventoryitemissue;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.tinvissueclinic.TinvIssueClinic;
import com.cellbeans.hspa.tinvissueclinicitem.TinvIssueClinicItem;
import com.cellbeans.hspa.tinvissueclinicitem.TinvIssueClinicItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/mis_inventory_item_issue")
public class MisInventoryItemIssueController {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    TinvIssueClinicItemRepository tinvIssueClinicItemRepository;

    @RequestMapping("search/{unitList}/{searchFromDate}/{searchToDate}")
    public List<TinvIssueClinic> getListOfItemsDependsOnSerch(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchItemIssue searchItemIssue, @PathVariable Long[] unitList, @PathVariable String searchFromDate, @PathVariable String searchToDate) {
        TenantContext.setCurrentTenant(tenantName);
        long count;
        if (searchItemIssue == null) {
            SearchItemIssue sb = new SearchItemIssue();
            searchItemIssue = sb;
        }
        String queryInya = "";
        String itemStore = "";
        String itemSupplier = "";
        String unitSerch = "";
        if (searchItemIssue.getSearchIndentNo() == null)
            searchItemIssue.setSearchIndentNo("");
        if (searchItemIssue.getSearchIssueNo() == null)
            searchItemIssue.setSearchIssueNo("");
        if (searchItemIssue.getSearchFromDate() == null) {
            String sDate1 = "1998/12/31";
            searchItemIssue.setSearchFromDate(sDate1);
        }
        if (searchItemIssue.getSearchToDate() == null) {
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchItemIssue.setSearchToDate(date);
        }
        if (searchItemIssue.getSearchFromStoreId().getStoreId() != 0)
            itemStore = "AND i.icFromStoreId.storeId=" + searchItemIssue.getSearchFromStoreId().getStoreId() + "";
        if (searchItemIssue.getSearchToStoreId().getStoreId() != 0)
            itemSupplier = "AND i.icToStoreId.storeId=" + searchItemIssue.getSearchToStoreId().getStoreId() + "";
        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            unitSerch += " i.issueClinicUnitId.unitId in (" + values + ") ";
        }
        queryInya = "Select i from TinvIssueClinic i Where " + unitSerch + " And i.icId like '%" + searchItemIssue.getSearchIndentNo() + "%'" + " and  i.icNo like '%" + searchItemIssue.getSearchIssueNo() + "%'" + "  AND date(i.createdDate) between   " + " ' " + searchItemIssue.getSearchFromDate() + " ' AND  " + "'" + searchItemIssue.getSearchToDate() + " ' " + itemStore + "  " + itemSupplier + " order by i.icId desc";
         System.out.println("Item Issue Report "+ queryInya);
        List<TinvIssueClinic> ItemList = new ArrayList<>();
        List<TinvIssueClinic> ItemListtemp = new ArrayList<>();
        if (searchItemIssue.isPrint()) {
            count = (Long) entityManager.createQuery("Select count(i.icId) from TinvIssueClinic i Where " + unitSerch + " And i.icId like '%" + searchItemIssue.getSearchIndentNo() + "%'" + " and  i.icNo like '%" + searchItemIssue.getSearchIssueNo() + "%'" + "  AND date(i.createdDate) between   " + " ' " + searchItemIssue.getSearchFromDate() + " ' AND  " + "'" + searchItemIssue.getSearchToDate() + " ' " + itemStore + "  " + itemSupplier + " order by i.icId desc").getSingleResult();
            ItemListtemp = entityManager.createQuery(queryInya, TinvIssueClinic.class).setFirstResult(searchItemIssue.getOffset()).setMaxResults(searchItemIssue.getLimit()).getResultList();
            for (TinvIssueClinic tempObj : ItemListtemp) {
                List<TinvIssueClinicItem> tempIssueClinicList = new ArrayList<>();
                List<TinvIssueClinicItem> IssueClinicItemList = new ArrayList<>();
                tempIssueClinicList = tinvIssueClinicItemRepository.findByIciIcIdIcIdAndIsActiveTrueAndIsDeletedFalse(tempObj.getIcId());
                for (TinvIssueClinicItem tempIssueClinicObj : tempIssueClinicList) {
                    TinvIssueClinicItem obj = new TinvIssueClinicItem();
                    obj.setIciId((int) tempIssueClinicObj.getIciId());
                    obj.setIciItemId(tempIssueClinicObj.getIciItemId());
                    obj.setIciRiId(tempIssueClinicObj.getIciRiId());
                    obj.setIciItemMrp(tempIssueClinicObj.getIciItemMrp());
                    obj.setIciItemRate(tempIssueClinicObj.getIciItemRate());
                    obj.setIciTotalAmount(tempIssueClinicObj.getIciTotalAmount());
                    obj.setIciItemExpiryDate(tempIssueClinicObj.getIciItemExpiryDate());
                    obj.setIciItemBatchCode(tempIssueClinicObj.getIciItemBatchCode());
                    obj.setIciIssueQuantity(tempIssueClinicObj.getIciIssueQuantity());
                    obj.setIciIndentQuantity(tempIssueClinicObj.getIciIndentQuantity());
                    obj.setIciReceiveQuantity(tempIssueClinicObj.getIciReceiveQuantity());
                    obj.setIciReturnQuantity(tempIssueClinicObj.getIciReturnQuantity());
                    obj.setIciReturnQuantityRemark(tempIssueClinicObj.getIciReturnQuantityRemark());
                    obj.setIsDeleted(tempIssueClinicObj.getIsDeleted());
                    obj.setIsActive(tempIssueClinicObj.getIsActive());
                    obj.setCreatedBy(tempIssueClinicObj.getCreatedBy());
                    obj.setLastModifiedBy(tempIssueClinicObj.getLastModifiedBy());
                    obj.setCreatedDate(tempIssueClinicObj.getCreatedDate());
                    obj.setLastModifiedDate(tempIssueClinicObj.getLastModifiedDate());
                    obj.setIssueClinicUnitId(tempIssueClinicObj.getIssueClinicUnitId());
                    IssueClinicItemList.add(obj);
                }
                tempObj.setTinvIssueClinicItems(IssueClinicItemList);
                ItemList.add(tempObj);
            }

        } else {
            count = (Long) entityManager.createQuery("Select count(i.icId) from TinvIssueClinic i Where " + unitSerch + " And i.icIndentNo like '%" + searchItemIssue.getSearchIndentNo() + "%'" + " and  i.icNo like '%" + searchItemIssue.getSearchIssueNo() + "%'" + "  AND date(i.createdDate) between   " + " ' " + searchItemIssue.getSearchFromDate() + " ' AND  " + "'" + searchItemIssue.getSearchToDate() + " ' " + itemStore + "  " + itemSupplier + " order by i.icId desc").getSingleResult();
            ItemList = entityManager.createQuery(queryInya, TinvIssueClinic.class).setFirstResult(searchItemIssue.getOffset()).setMaxResults(searchItemIssue.getLimit()).getResultList();
        }
        if (ItemList.size() > 0)
            ItemList.get(0).setCount(count);
        return ItemList;
    }

}


