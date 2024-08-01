package com.cellbeans.hspa.tinvitemstockadjustment;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_item_stock_adjustment")
public class TinvItemStockAdjustmentController {
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TinvItemStockAdjustmentRepository tinvItemStockAdjustmentRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvItemStockAdjustment> tinvItemStockAdjustment) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvItemStockAdjustment.size() != 0) {
            tinvItemStockAdjustmentRepository.saveAll(tinvItemStockAdjustment);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{isaId}")
    public TinvItemStockAdjustment read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("isaId") Long isaId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvItemStockAdjustment tinvItemStockAdjustment = tinvItemStockAdjustmentRepository.getById(isaId);
        return tinvItemStockAdjustment;
    }

    @RequestMapping("update")
    public TinvItemStockAdjustment update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvItemStockAdjustment tinvItemStockAdjustment) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvItemStockAdjustmentRepository.save(tinvItemStockAdjustment);
    }

    @GetMapping
    @RequestMapping("list")
   /* public Iterable<TinvItemStockAdjustment> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                  @RequestParam(value = "size", required = false, defaultValue = "20") String size,
                                                  @RequestParam(value = "qString", required = false) String qString,
                                                  @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                  @RequestParam(value = "col", required = false, defaultValue = "isaId") String col) {

        if (qString == null || qString.equals("")) {
            return tinvItemStockAdjustmentRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {

            return tinvItemStockAdjustmentRepository.findByAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }*/
    @PutMapping("delete/{isaId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("isaId") Long isaId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvItemStockAdjustment tinvItemStockAdjustment = tinvItemStockAdjustmentRepository.getById(isaId);
        if (tinvItemStockAdjustment != null) {
            tinvItemStockAdjustment.setDeleted(true);
            tinvItemStockAdjustmentRepository.save(tinvItemStockAdjustment);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("search")
    public List<TinvItemStockAdjustment> getListOfItemsDependsOnSerch(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchAdjustItemList searchAdjustItemList) {
        TenantContext.setCurrentTenant(tenantName);
        if (searchAdjustItemList == null) {
            SearchAdjustItemList sb = new SearchAdjustItemList();
            searchAdjustItemList = sb;
        }
        String queryInya = "";
        String forItemCategories = "";
        String forItemGroup = "";
        String forItemStore = "";
        String forItemGenericName = "";
        String forItemStorage = "";
        String forItemDespensingName = "";
        if (searchAdjustItemList.getItemName() == null)
            searchAdjustItemList.setItemName("");
        if (searchAdjustItemList.getItemBatchCode() == null)
            searchAdjustItemList.setItemBatchCode("");
        if (searchAdjustItemList.getItemBrandName() == null)
            searchAdjustItemList.setItemBrandName("");
        if (searchAdjustItemList.getItemCode() == null)
            searchAdjustItemList.setItemCode("");
        if (searchAdjustItemList.getItemExpiryDate() == null)
            searchAdjustItemList.setItemExpiryDate("");
        if (searchAdjustItemList.getItemCategoryId().getIcId() != 0)
            forItemCategories = " AND i.isaObiId.obiItemId.itemIcId.icId=" + searchAdjustItemList.getItemCategoryId().getIcId() + " ";
        if (searchAdjustItemList.getItemGroupId().getIgId() != 0)
            forItemGroup = "AND i.isaObiId.obiItemId.itemIgId.igId=" + searchAdjustItemList.getItemGroupId().getIgId() + "";
        if (searchAdjustItemList.getItemStoreId().getStoreId() != 0)
            forItemStore = "AND i.isaObiId.obiStoreId.storeId=" + searchAdjustItemList.getItemStoreId().getStoreId() + "";
        if (searchAdjustItemList.getItemGenericId().getGenericId() != 0)
            forItemGenericName = "AND i.isaObiId.obiItemId.itemGenericId.genericId=" + searchAdjustItemList.getItemGenericId().getGenericId() + "";
        if (searchAdjustItemList.getItemStorageId().getIstId() != 0)
            forItemStorage = "AND i.isaObiId.obiItemId.itemIstId.istId=" + searchAdjustItemList.getItemStorageId().getIstId() + "";
        if (searchAdjustItemList.getItemDespensingId().getIdtId() != 0)
            forItemDespensingName = "AND i.isaObiId.obiItemId.itemSaleUomId.idtId=" + searchAdjustItemList.getItemDespensingId().getIdtId() + "";
        queryInya = "Select i from TinvItemStockAdjustment i Where i.isaUnitId.unitId=" + searchAdjustItemList.getUnitId() + " And i.isaObiId.obiItemId.itemName like '%" + searchAdjustItemList.getItemName() + "%' And  i.isaObiId.obiItemId.itemBrandName like '%" + searchAdjustItemList.getItemBrandName() + "%' AND  i.isaObiId.obiItemId.itemCode like '%" + searchAdjustItemList.getItemCode() + "%' AND  i.isaItemBatchCodeNew like '%" + searchAdjustItemList.getItemBatchCode() + "%'" + forItemCategories + "  " + forItemGroup + "  " + forItemStore + " " + forItemGenericName + " " + forItemStorage + " " + forItemDespensingName + "  order by i.isaId desc  ";
        // System.out.println(queryInya);
        long count = (Long) entityManager.createQuery("Select count(i.isaId) from TinvItemStockAdjustment i Where i.isaUnitId.unitId=" + searchAdjustItemList.getUnitId() + " And i.isaObiId.obiItemId.itemName like '%" + searchAdjustItemList.getItemName() + "%' And  i.isaObiId.obiItemId.itemBrandName like '%" + searchAdjustItemList.getItemBrandName() + "%' AND  i.isaObiId.obiItemId.itemCode like '%" + searchAdjustItemList.getItemCode() + "%' AND  i.isaItemBatchCodeNew like '%" + searchAdjustItemList.getItemBatchCode() + "%'" + forItemCategories + "  " + forItemGroup + "  " + forItemStore + " " + forItemGenericName + " " + forItemStorage + " " + forItemDespensingName + "  order by i.isaId desc  ").getSingleResult();
        List<TinvItemStockAdjustment> ItemList = entityManager.createQuery(queryInya, TinvItemStockAdjustment.class).setFirstResult(((searchAdjustItemList.getOffset() - 1) * searchAdjustItemList.getLimit())).setMaxResults(searchAdjustItemList.getLimit()).getResultList();
      /*  List<InvItem> ObjectArray=new ArrayList<>();
        for (InvItem obj : ItemList) {
            obj.setCount(count);
            ObjectArray.add(obj);
        }*/
        if (ItemList.size() > 0)
            ItemList.get(0).setCount(count);
        return ItemList;
    }

}
            
