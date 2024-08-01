package com.cellbeans.hspa.mis.misinventoryitemindent;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.tinvstoreindent.TinvStoreIndent;
import com.cellbeans.hspa.tinvstoreindentitem.TinvStoreIndentItem;
import com.cellbeans.hspa.tinvstoreindentitem.TinvStoreIndentItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/mis_inventory_item_indent")
public class MisInventoryitemIndentController {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    TinvStoreIndentItemRepository tinvStoreIndentItemRepository;

    @RequestMapping("search/{unitList}/{searchFromDate}/{searchToDate}")
    public List<TinvStoreIndent> getListOfItemsDependsOnSerch(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchItemIndent searchItemIndent,
                                                              @PathVariable Long[] unitList, @PathVariable String searchFromDate, @PathVariable String searchToDate) {
        TenantContext.setCurrentTenant(tenantName);
        long count;
        if (searchItemIndent == null) {
            SearchItemIndent sb = new SearchItemIndent();
            searchItemIndent = sb;
        }
        String queryInya = "";
        String itemStore = "";
        String itemSupplier = "";
        String unitSerch = "";
        if (searchItemIndent.getSearchIndentNo() == null)
            searchItemIndent.setSearchIndentNo("");
        if (searchItemIndent.getSearchFromDate() == null) {
            String sDate1 = "1998/12/31";
            searchItemIndent.setSearchFromDate(sDate1);
        }
        if (searchItemIndent.getSearchToDate() == null) {
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchItemIndent.setSearchToDate(date);
        }
        if (searchItemIndent.getSearchFromStoreId().getStoreId() != 0)
            itemStore = "AND i.siFromStoreId.storeId=" + searchItemIndent.getSearchFromStoreId().getStoreId() + "";
        if (searchItemIndent.getSearchToStoreId().getStoreId() != 0)
            itemSupplier = "AND i.siToStoreId.storeId=" + searchItemIndent.getSearchToStoreId().getStoreId() + "";
        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            unitSerch += " i.storeIndentUnitId.unitId in (" + values + ") ";
        }
        String final_Date = null;
        if (searchItemIndent.getDueDate() != null) {
            final_Date = searchItemIndent.getDueDate();
        }
        if (final_Date == null) {
            queryInya = "Select i from TinvStoreIndent i Where i.siIsCancle = false And " + unitSerch + " And i.siIndentNo like '%"
                    + searchItemIndent.getSearchIndentNo() + "%'" + "  AND date(i.createdDate) between   " + " ' "
                    + searchItemIndent.getSearchFromDate() + " ' AND  " + "'" + searchItemIndent.getSearchToDate()
                    + " ' " + itemStore + "  " + itemSupplier + " order by i.siDueDate desc,i.siId asc";
        } else {
            queryInya = "Select i from TinvStoreIndent i Where i.siIsCancle = false And " + unitSerch + " And i.siIndentNo like '%"
                    + searchItemIndent.getSearchIndentNo() + "%'" + "  AND date(i.createdDate) between   " + " ' "
                    + searchItemIndent.getSearchFromDate() + " ' AND  " + "'" + searchItemIndent.getSearchToDate()
                    + " ' " + itemStore + "  " + itemSupplier + " and i.siDueDate >= '" + final_Date
                    + "' order by i.siDueDate asc";
        }
        List<TinvStoreIndent> ItemList = new ArrayList<>();
        List<TinvStoreIndent> ItemListTemp = new ArrayList<>();
        if (searchItemIndent.isPrint()) {
            if (final_Date == null) {
                count = (Long) entityManager.createQuery("Select count(i.siId) from TinvStoreIndent i Where i.siIsCancle = false And "
                        + unitSerch + " And i.siIndentNo like '%" + searchItemIndent.getSearchIndentNo() + "%'"
                        + "  AND date(i.createdDate) between   " + " ' " + searchItemIndent.getSearchFromDate()
                        + " ' AND  " + "'" + searchItemIndent.getSearchToDate() + " ' " + itemStore + "  "
                        + itemSupplier + " order by i.siDueDate desc,i.siId asc").getSingleResult();
            } else {
                count = (Long) entityManager
                        .createQuery("Select count(i.siId) from TinvStoreIndent i Where i.siIsCancle = false And " + unitSerch
                                + " And i.siIndentNo like '%" + searchItemIndent.getSearchIndentNo() + "%'"
                                + "  AND date(i.createdDate) between   " + " ' " + searchItemIndent.getSearchFromDate()
                                + " ' AND  " + "'" + searchItemIndent.getSearchToDate() + " ' " + itemStore + "  "
                                + itemSupplier + " and i.siDueDate >= '" + final_Date + "' order by i.siDueDate asc")
                        .getSingleResult();
            }
            ItemListTemp = entityManager.createQuery(queryInya, TinvStoreIndent.class)
                    .setFirstResult(searchItemIndent.getOffset()).setMaxResults(searchItemIndent.getLimit())
                    .getResultList();
            for (TinvStoreIndent TSIobj : ItemListTemp) {
                List<TinvStoreIndentItem> TSIIList = new ArrayList<>();
                List<TinvStoreIndentItem> TSIIListtemp = new ArrayList<>();
                TSIIList = tinvStoreIndentItemRepository
                        .findAllBySiiSiIdSiIdAndIsActiveTrueAndIsDeletedFalse(TSIobj.getSiId());
                for (TinvStoreIndentItem obj : TSIIList) {
                    TinvStoreIndentItem tempObj = new TinvStoreIndentItem();
                    tempObj.setSiiId((int) obj.getSiiId());
                    tempObj.setSiiItemId(obj.getSiiItemId());
                    tempObj.setSiiQuantity(obj.getSiiQuantity());
                    tempObj.setSiiMrp(obj.getSiiMrp());
                    tempObj.setSiiRate(obj.getSiiRate());
                    tempObj.setSiiTotalMrp(obj.getSiiTotalMrp());
                    tempObj.setSiiTotalAmount(obj.getSiiTotalAmount());
                    tempObj.setIsDeleted(obj.getIsDeleted());
                    tempObj.setIsActive(obj.getIsActive());
                    tempObj.setCreatedBy(obj.getCreatedBy());
                    tempObj.setLastModifiedBy(obj.getLastModifiedBy());
                    tempObj.setCreatedDate(obj.getCreatedDate());
                    tempObj.setLastModifiedDate(obj.getLastModifiedDate());
                    tempObj.setStoreIndentUnitId(obj.getStoreIndentUnitId());
                    TSIIListtemp.add(tempObj);
                }
                TSIobj.setTinvStoreIndentItems(TSIIListtemp);
                ItemList.add(TSIobj);
            }

        } else {
            count = (Long) entityManager.createQuery("Select count(i.siId) from TinvStoreIndent i Where i.siIsCancle = false And " + unitSerch
                    + " And i.siIndentNo like '%" + searchItemIndent.getSearchIndentNo() + "%'"
                    + "  AND date(i.createdDate) between   " + " ' " + searchItemIndent.getSearchFromDate() + " ' AND  "
                    + "'" + searchItemIndent.getSearchToDate() + " ' " + itemStore + "  " + itemSupplier
                    + " order by i.siId desc").getSingleResult();

			/*ItemList = entityManager.createQuery(queryInya, TinvStoreIndent.class)
					.setFirstResult(searchItemIndent.getOffset()).setMaxResults(searchItemIndent.getLimit())
					.getResultList();*/
            ItemList = entityManager.createQuery(queryInya, TinvStoreIndent.class)
                    .setFirstResult((searchItemIndent.getOffset() - 1) * searchItemIndent.getLimit()).setMaxResults(searchItemIndent.getLimit())
                    .getResultList();
        }
        if (ItemList.size() > 0)
            ItemList.get(0).setCount(count);
        return ItemList;
    }

    @RequestMapping("cancellist/{unitList}/{searchFromDate}/{searchToDate}")
    public List<TinvStoreIndent> getCancelListOfItemsDependsOnSerch(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchItemIndent searchItemIndent,
                                                                    @PathVariable Long[] unitList, @PathVariable String searchFromDate, @PathVariable String searchToDate) {
        TenantContext.setCurrentTenant(tenantName);
        long count;
        if (searchItemIndent == null) {
            SearchItemIndent sb = new SearchItemIndent();
            searchItemIndent = sb;
        }
        String queryInya = "";
        String itemStore = "";
        String itemSupplier = "";
        String unitSerch = "";
        if (searchItemIndent.getSearchIndentNo() == null)
            searchItemIndent.setSearchIndentNo("");
        if (searchItemIndent.getSearchFromDate() == null) {
            String sDate1 = "1998/12/31";
            searchItemIndent.setSearchFromDate(sDate1);
        }
        if (searchItemIndent.getSearchToDate() == null) {
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchItemIndent.setSearchToDate(date);
        }
        if (searchItemIndent.getSearchFromStoreId().getStoreId() != 0)
            itemStore = "AND i.siFromStoreId.storeId=" + searchItemIndent.getSearchFromStoreId().getStoreId() + "";
        if (searchItemIndent.getSearchToStoreId().getStoreId() != 0)
            itemSupplier = "AND i.siToStoreId.storeId=" + searchItemIndent.getSearchToStoreId().getStoreId() + "";
        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            unitSerch += " i.storeIndentUnitId.unitId in (" + values + ") ";
        }
        String final_Date = null;
        if (searchItemIndent.getDueDate() != null) {
            final_Date = searchItemIndent.getDueDate();
        }
        if (final_Date == null) {
            queryInya = "Select i from TinvStoreIndent i Where i.siIsCancle = true And " + unitSerch + " And i.siIndentNo like '%"
                    + searchItemIndent.getSearchIndentNo() + "%'" + "  AND date(i.createdDate) between   " + " ' "
                    + searchItemIndent.getSearchFromDate() + " ' AND  " + "'" + searchItemIndent.getSearchToDate()
                    + " ' " + itemStore + "  " + itemSupplier + " order by i.siDueDate desc,i.siId asc";
        } else {
            queryInya = "Select i from TinvStoreIndent i Where i.siIsCancle = true And " + unitSerch + " And i.siIndentNo like '%"
                    + searchItemIndent.getSearchIndentNo() + "%'" + "  AND date(i.createdDate) between   " + " ' "
                    + searchItemIndent.getSearchFromDate() + " ' AND  " + "'" + searchItemIndent.getSearchToDate()
                    + " ' " + itemStore + "  " + itemSupplier + " and i.siDueDate >= '" + final_Date
                    + "' order by i.siDueDate asc";
        }

        System.out.println("Requisition Cancel Report 1"+ queryInya);

        List<TinvStoreIndent> ItemList = new ArrayList<>();
        List<TinvStoreIndent> ItemListTemp = new ArrayList<>();
        if (searchItemIndent.isPrint()) {
            if (final_Date == null) {
                count = (Long) entityManager.createQuery("Select count(i.siId) from TinvStoreIndent i Where  i.siIsCancle = true And "
                        + unitSerch + " And i.siIndentNo like '%" + searchItemIndent.getSearchIndentNo() + "%'"
                        + "  AND date(i.createdDate) between   " + " ' " + searchItemIndent.getSearchFromDate()
                        + " ' AND  " + "'" + searchItemIndent.getSearchToDate() + " ' " + itemStore + "  "
                        + itemSupplier + " order by i.siDueDate desc,i.siId asc").getSingleResult();
            } else {
                count = (Long) entityManager
                        .createQuery("Select count(i.siId) from TinvStoreIndent i Where  i.siIsCancle = true And " + unitSerch
                                + " And i.siIndentNo like '%" + searchItemIndent.getSearchIndentNo() + "%'"
                                + "  AND date(i.createdDate) between   " + " ' " + searchItemIndent.getSearchFromDate()
                                + " ' AND  " + "'" + searchItemIndent.getSearchToDate() + " ' " + itemStore + "  "
                                + itemSupplier + " and i.siDueDate >= '" + final_Date + "' order by i.siDueDate asc")
                        .getSingleResult();
            }
            ItemListTemp = entityManager.createQuery(queryInya, TinvStoreIndent.class)
                    .setFirstResult(searchItemIndent.getOffset()).setMaxResults(searchItemIndent.getLimit())
                    .getResultList();
            for (TinvStoreIndent TSIobj : ItemListTemp) {
                List<TinvStoreIndentItem> TSIIList = new ArrayList<>();
                List<TinvStoreIndentItem> TSIIListtemp = new ArrayList<>();
                TSIIList = tinvStoreIndentItemRepository.findAllBySiiSiIdSiIdAndIsActiveTrueAndIsDeletedFalse(TSIobj.getSiId());
                for (TinvStoreIndentItem obj : TSIIList) {
                    TinvStoreIndentItem tempObj = new TinvStoreIndentItem();
                    tempObj.setSiiId((int) obj.getSiiId());
                    tempObj.setSiiItemId(obj.getSiiItemId());
                    tempObj.setSiiQuantity(obj.getSiiQuantity());
                    tempObj.setSiiMrp(obj.getSiiMrp());
                    tempObj.setSiiRate(obj.getSiiRate());
                    tempObj.setSiiTotalMrp(obj.getSiiTotalMrp());
                    tempObj.setSiiTotalAmount(obj.getSiiTotalAmount());
                    tempObj.setIsDeleted(obj.getIsDeleted());
                    tempObj.setIsActive(obj.getIsActive());
                    tempObj.setCreatedBy(obj.getCreatedBy());
                    tempObj.setLastModifiedBy(obj.getLastModifiedBy());
                    tempObj.setCreatedDate(obj.getCreatedDate());
                    tempObj.setLastModifiedDate(obj.getLastModifiedDate());
                    tempObj.setStoreIndentUnitId(obj.getStoreIndentUnitId());
                    TSIIListtemp.add(tempObj);
                }
                TSIobj.setTinvStoreIndentItems(TSIIListtemp);
                ItemList.add(TSIobj);
            }

        } else {
            count = (Long) entityManager.createQuery("Select count(i.siId) from TinvStoreIndent i Where  i.siIsCancle = true And " + unitSerch
                    + " And i.siIndentNo like '%" + searchItemIndent.getSearchIndentNo() + "%'"
                    + " AND date(i.createdDate) between   " + " ' " + searchItemIndent.getSearchFromDate() + " ' AND  "
                    + "'" + searchItemIndent.getSearchToDate() + " ' " + itemStore + "  " + itemSupplier
                    + " order by i.siId desc").getSingleResult();
            ItemList = entityManager.createQuery(queryInya, TinvStoreIndent.class)
                    .setFirstResult((searchItemIndent.getOffset() - 1) * searchItemIndent.getLimit()).setMaxResults(searchItemIndent.getLimit())
                    .getResultList();
        }
        if (ItemList.size() > 0)
            ItemList.get(0).setCount(count);
        return ItemList;
    }

}
