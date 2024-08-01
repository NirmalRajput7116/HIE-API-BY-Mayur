package com.cellbeans.hspa.mis.misinventorycurrentstockstatement;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.tinvopeningbalanceitem.TinvOpeningBalanceItem;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/mis_inventory_current_stock")
public class MisInventoryCurrentStockController {

    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping("search/{unitList}")
    public List<TinvOpeningBalanceItem> getListOfItemsDependsOnSerch(@RequestHeader("X-tenantId") String tenantName,
                                                                     @RequestBody SearchInventoryCurrentStock searchInventoryCurrentStock, @PathVariable String[] unitList) {
        TenantContext.setCurrentTenant(tenantName);
        List<TinvOpeningBalanceItem> ItemList = new ArrayList<TinvOpeningBalanceItem>();
        if (searchInventoryCurrentStock == null) {
            SearchInventoryCurrentStock sb = new SearchInventoryCurrentStock();
            searchInventoryCurrentStock = sb;
        }
        String queryInya = "";
        String forItemStore = "";
        if (searchInventoryCurrentStock.getItemName() == null)
            searchInventoryCurrentStock.setItemName("");
        if (searchInventoryCurrentStock.getItemBatchCode() == null)
            searchInventoryCurrentStock.setItemBatchCode("");
        if (searchInventoryCurrentStock.getItemStoreId().getStoreId() != 0) {
            forItemStore = "AND i.obiStoreId.storeId=" + searchInventoryCurrentStock.getItemStoreId().getStoreId() + "";
        }
        queryInya = "Select i from TinvOpeningBalanceItem i Where  i.obiItemId.itemName like '%"
                + searchInventoryCurrentStock.getItemName() + "%' AND  i.obiItemBatchCode like '%"
                + searchInventoryCurrentStock.getItemBatchCode() + "%'" + forItemStore + " ";
        if (searchInventoryCurrentStock.getStartdate() != null) {
            if (searchInventoryCurrentStock.getEnddate() != null) {
                queryInya += "AND date(i.obiItemExpiryDate) between '" + searchInventoryCurrentStock.getStartdate()
                        + "' AND  '" + searchInventoryCurrentStock.getEnddate() + "' ";

            } else {
                String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
                searchInventoryCurrentStock.setEnddate(date);
                queryInya += "AND date(i.obiItemExpiryDate) between '" + searchInventoryCurrentStock.getStartdate()
                        + "' AND  '" + searchInventoryCurrentStock.getEnddate() + "' ";
            }
            // String sDate1 = "1998/12/31";
            // searchInventoryCurrentStock.setStartdate(sDate1);
        } else if (searchInventoryCurrentStock.getItemExpUltimatum() != 0) {
            LocalDate ultimateDate = LocalDate.now().plusMonths(searchInventoryCurrentStock.getItemExpUltimatum());
            System.out.println(ultimateDate);
            // SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd");
            queryInya += " And date(i.obiItemExpiryDate) between date(NOW()) And '" + ultimateDate + "'";

        } else {
            String sDate1 = "1998/12/31";
            searchInventoryCurrentStock.setStartdate(sDate1);
            if (searchInventoryCurrentStock.getEnddate() != null) {
                queryInya += "AND date(i.obiItemExpiryDate) between '" + searchInventoryCurrentStock.getStartdate()
                        + "' AND  '" + searchInventoryCurrentStock.getEnddate() + "' ";
            }
        }
        if (searchInventoryCurrentStock.getFromQuantity() != null
                && searchInventoryCurrentStock.getToQuantity() != null) {
            queryInya += "AND  i.obiItemQuantity BETWEEN " + searchInventoryCurrentStock.getFromQuantity() + " AND "
                    + searchInventoryCurrentStock.getToQuantity();
        } else if (searchInventoryCurrentStock.getFromQuantity() != null) {
            queryInya += "AND  i.obiItemQuantity >=" + searchInventoryCurrentStock.getFromQuantity();
        } else if (searchInventoryCurrentStock.getToQuantity() != null) {
            queryInya += "AND  i.obiItemQuantity <= " + searchInventoryCurrentStock.getToQuantity();
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            queryInya += " and i.openingBalanceUnitId.unitId in (" + values + ") ";
        }
        queryInya += " order by i.obiId desc";
        System.out.println("queryInya : " + queryInya);
        String countQuery = StringUtils.replace(queryInya, "i fr", "count(i.obiId) fr");
        long count = (Long) entityManager.createQuery(countQuery).getSingleResult();
        if (searchInventoryCurrentStock.isPrint()) {
            ItemList = entityManager.createQuery(queryInya, TinvOpeningBalanceItem.class).getResultList();
        } else {
            ItemList = entityManager.createQuery(queryInya, TinvOpeningBalanceItem.class)
                    .setFirstResult(searchInventoryCurrentStock.getOffset())
                    .setMaxResults(searchInventoryCurrentStock.getLimit()).getResultList();
        }
        if (ItemList.size() > 0)
            ItemList.get(0).setCount(count);
        return ItemList;
    }

}
