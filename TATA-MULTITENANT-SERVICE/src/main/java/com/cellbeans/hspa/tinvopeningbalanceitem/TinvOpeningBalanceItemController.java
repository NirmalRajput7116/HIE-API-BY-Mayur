package com.cellbeans.hspa.tinvopeningbalanceitem;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.invitem.InvItem;
import com.cellbeans.hspa.invitem.InvItemRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/tinv_opening_balance_item")
public class TinvOpeningBalanceItemController {
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TinvOpeningBalanceItemRepository tinvOpeningBalanceItemRepository;

    @Autowired
    InvItemRepository invItemRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvOpeningBalanceItem> tinvOpeningBalanceItem) {
        TenantContext.setCurrentTenant(tenantName);
        if (true) {
            tinvOpeningBalanceItemRepository.saveAll(tinvOpeningBalanceItem);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("updateitem")
    public Map<String, String> updateItemQty(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvOpeningBalanceItem> tinvOpeningBalanceItem) {
        TenantContext.setCurrentTenant(tenantName);
        for (TinvOpeningBalanceItem objList : tinvOpeningBalanceItem) {
            Double subItemQty = 0.0;
            List<TinvOpeningBalanceItem> itemList = entityManager.createQuery("SELECT tobi FROM TinvOpeningBalanceItem tobi WHERE tobi.isActive = 1 AND tobi.isApproved = 1 AND tobi.obiStoreId.storeId = " + objList.getObiStoreId().getStoreId() + " AND tobi.obiItemId.itemId IN (" + objList.getObiItemId().getItemId() + ")").getResultList();
            if (itemList != null) {
                for (TinvOpeningBalanceItem obj : itemList) {
                    if (obj.getObiItemQuantity() - objList.getConsumeQty() >= 0) {
                        obj.setObiItemQuantity(obj.getObiItemQuantity() - objList.getConsumeQty());
                        objList.setConsumeQty(0.0);

                    } else if (obj.getObiItemQuantity() - objList.getConsumeQty() < 0 && objList.getConsumeQty() != 0) {
                        subItemQty = objList.getConsumeQty() - obj.getObiItemQuantity();
                        objList.setConsumeQty(subItemQty);
                        obj.setObiItemQuantity(0);
                    }
                    tinvOpeningBalanceItemRepository.save(obj);
                }
            }
        }
        respMap.put("success", "1");
        respMap.put("msg", "Successfully Update");
        return respMap;
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<TinvOpeningBalanceItem> records;
        records = tinvOpeningBalanceItemRepository.findByObiItemIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{obiId}")
    public TinvOpeningBalanceItem read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("obiId") Long obiId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvOpeningBalanceItem tinvOpeningBalanceItem = tinvOpeningBalanceItemRepository.getById(obiId);
        return tinvOpeningBalanceItem;
    }

    @RequestMapping("bygrnid/{grnId}")
    public List<TinvOpeningBalanceItem> listItemsForGRN(@RequestHeader("X-tenantId") String tenantName, @PathVariable("grnId") Long grnId) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvOpeningBalanceItemRepository.findAllByObiPgrnIdPgrnIdAndIsAndActiveTrueAndIsDeletedFalse(grnId);
    }

    @RequestMapping("update")
    public TinvOpeningBalanceItem update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvOpeningBalanceItem tinvOpeningBalanceItem) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvOpeningBalanceItemRepository.save(tinvOpeningBalanceItem);
    }

    @RequestMapping("updateitemlist")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvOpeningBalanceItem> tinvOpeningBalanceItem) {
        TenantContext.setCurrentTenant(tenantName);
        try {
            tinvOpeningBalanceItemRepository.saveAll(tinvOpeningBalanceItem);
            respMap.put("msg", "Added Successfully");
            return respMap;
        } catch (Exception ex) {
            respMap.put("msg", "Operation Failed");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TinvOpeningBalanceItem> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "obiItemExpiryDate") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tinvOpeningBalanceItemRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tinvOpeningBalanceItemRepository.findByObiItemNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @RequestMapping("pharmacyitemforemr")
    public List<TinvOpeningBalanceItem> listItemsForEMR(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchData", required = false, defaultValue = " ") String searchData) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvOpeningBalanceItemRepository.findByItemByForEMR(searchData);
    }

    @RequestMapping("pharmacyitemfordrugallergy")
    public List<TinvOpeningBalanceItem> listItemsForDrugAllergy(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvOpeningBalanceItemRepository.findDistinctForDrugAllergies();
    }

    @RequestMapping("pharmacyitem")
    public List<TinvOpeningBalanceItem> listItems(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchData", required = false, defaultValue = " ") String searchData, @RequestParam(value = "date", required = false, defaultValue = " ") Date date, @RequestParam(value = "unitId", required = false, defaultValue = " ") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvOpeningBalanceItemRepository.findByItemByForPhatmacy(searchData, date, unitId, PageRequest.of(0, 20));
    }
//    @RequestMapping("pharmacyitemByStoreId")
//    public List<TinvOpeningBalanceItem> listItems(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchData", required = false, defaultValue = " ") String searchData, @RequestParam(value = "date", required = false, defaultValue = " ") Date date, @RequestParam(value = "storeId", required = false, defaultValue = " ") long storeId, @RequestParam(value = "unitId", required = false, defaultValue = " ") long unitId) {
//        List<TinvOpeningBalanceItem> tinvOpeningBalanceItemlist = tinvOpeningBalanceItemRepository.findByItemByForPhatmacyByStoreId(searchData, storeId, date, unitId, PageRequest.of(0, 10));
//        String query = "select oi from TinvOpeningBalanceItem oi  join  oi.obiItemId i join i.icComponentId ic where ic.compoundName like '%" + searchData + "%' and  i.isShowPharmacy=1 and oi.obiItemQuantity>0 and oi.obiStoreId.storeId=" + storeId + " and  oi.obiItemExpiryDate>=Curdate() and oi.isActive=1 and oi.isDeleted=0 ";
//        List<TinvOpeningBalanceItem> itembycopundlist = entityManager.createQuery(query, TinvOpeningBalanceItem.class).getResultList();
//        for (TinvOpeningBalanceItem obj : itembycopundlist) {
//            if (!tinvOpeningBalanceItemlist.contains(obj)) {
//                tinvOpeningBalanceItemlist.add(obj);
//            }
//        }
//        return tinvOpeningBalanceItemlist;
//    }

    @RequestMapping("pharmacyitemByStoreId")
    public List<TinvOpeningBalanceItem> listItems(@RequestHeader("X-tenantId") String tenantName,
                                                  @RequestParam(value = "searchData", required = false, defaultValue = " ") String searchData,
                                                  @RequestParam(value = "date", required = false, defaultValue = " ") Date date,
                                                  @RequestParam(value = "storeId", required = false, defaultValue = " ") long storeId,
                                                  @RequestParam(value = "unitId", required = false, defaultValue = " ") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        List<TinvOpeningBalanceItem> tinvOpeningBalanceItemlist = tinvOpeningBalanceItemRepository
                .findByItemByForPhatmacyByStoreId(searchData, storeId, date, unitId, PageRequest.of(0, 10));
        String query = "select oi from TinvOpeningBalanceItem oi  join  oi.obiItemId i join i.icComponentId ic where ic.compoundName like '%"
                + searchData + "%' and  i.isShowPharmacy=1 and oi.obiItemQuantity>0 and oi.obiStoreId.storeId="
                + storeId + " and  oi.obiItemExpiryDate>=Curdate() and oi.isActive=1 and oi.isDeleted=0 ";
//        String query2 = "select oi from TinvOpeningBalanceItem oi  join  oi.obiItemId i where i.itemGenericId.genericName like '%"
//                + searchData + "%' and  i.isShowPharmacy=1 and oi.obiItemQuantity>0 and oi.obiStoreId.storeId="
//                + storeId + " and  oi.obiItemExpiryDate>=Curdate() and oi.isActive=1 and oi.isDeleted=0 ";
        List<TinvOpeningBalanceItem> itembycopundlist = entityManager.createQuery(query, TinvOpeningBalanceItem.class)
                .getResultList();
//        List<TinvOpeningBalanceItem> itembygenlist = entityManager.createQuery(query2, TinvOpeningBalanceItem.class)
//                .getResultList();
        for (TinvOpeningBalanceItem obj : itembycopundlist) {
            if (!tinvOpeningBalanceItemlist.contains(obj)) {
                tinvOpeningBalanceItemlist.add(obj);
            }
        }
//        for (TinvOpeningBalanceItem obj1 : itembygenlist) {
//            if (!tinvOpeningBalanceItemlist.contains(obj1)) {
//                tinvOpeningBalanceItemlist.add(obj1);
//            }
//        }
        return tinvOpeningBalanceItemlist;
    }

    @PutMapping("delete/{obiId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("obiId") Long obiId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvOpeningBalanceItem tinvOpeningBalanceItem = tinvOpeningBalanceItemRepository.getById(obiId);
        if (tinvOpeningBalanceItem != null) {
            tinvOpeningBalanceItem.setIsDeleted(true);
            tinvOpeningBalanceItemRepository.save(tinvOpeningBalanceItem);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @PutMapping("updatequantity")
    public Map<String, String> updatequantity(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvOpeningBalanceItem> tinvOpeningBalanceItem) {
        TenantContext.setCurrentTenant(tenantName);
        if (true) {
            System.out.println(tinvOpeningBalanceItem);
            tinvOpeningBalanceItemRepository.saveAll(tinvOpeningBalanceItem);
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }

    }

    @RequestMapping("drugeById")
    public List<TinvOpeningBalanceItem> getDrugeByItemId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchData", required = false, defaultValue = " ") Long searchData, @RequestParam(value = "date", required = false, defaultValue = " ") Date date) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvOpeningBalanceItemRepository.findAllByObiItemIdItemIdAndIsActiveTrueAndIsDeletedFalse(searchData, date);
    }

    @RequestMapping("drugeByIdbyUnitId")
    public List<TinvOpeningBalanceItem> getDrugeByItemId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchData", required = false, defaultValue = " ") Long searchData, @RequestParam(value = "date", required = false, defaultValue = " ") Date date, @RequestParam(value = "unitId", required = false) long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvOpeningBalanceItemRepository.findAllByObiItemIdItemIdAndOpeningBalanceUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(searchData, date, unitId);
    }

    @RequestMapping("byobipoid")
    public List<TinvOpeningBalanceItem> getListOfItemByObiPoId(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvOpeningBalanceItem tinvOpeningBalanceItem) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvOpeningBalanceItemRepository.findAllByObiPgrnIdPgrnIdAndIsActiveTrueAndIsDeletedFalse(tinvOpeningBalanceItem.getObiPgrnId().getPgrnId());
    }

    @RequestMapping("approveIteamsByGRNId/{pgrnId}")
    public Map<String, String> onApproveGRN(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pgrnId") Long pgrnId) {
        TenantContext.setCurrentTenant(tenantName);
        List<TinvOpeningBalanceItem> tinvOpeningBalanceItem = tinvOpeningBalanceItemRepository.findAllByObiPgrnIdPgrnIdAndIsActiveTrueAndIsDeletedFalse(pgrnId);
        for (int i = 0; i < tinvOpeningBalanceItem.size(); i++) {
            TinvOpeningBalanceItem currObj = tinvOpeningBalanceItem.get(i);
            currObj.setIsApproved(true);
            tinvOpeningBalanceItemRepository.save(currObj);
        }
        respMap.put("msg", "Operation Successful");
        respMap.put("success", "1");
        return respMap;
    }

    @RequestMapping("rejectIteamsByGRNId/{pgrnId}")
    public Map<String, String> onRejectGRN(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pgrnId") Long pgrnId) {
        TenantContext.setCurrentTenant(tenantName);
        List<TinvOpeningBalanceItem> tinvOpeningBalanceItem = tinvOpeningBalanceItemRepository.findAllByObiPgrnIdPgrnIdAndIsActiveTrueAndIsDeletedFalse(pgrnId);
        for (int i = 0; i < tinvOpeningBalanceItem.size(); i++) {
            TinvOpeningBalanceItem currObj = tinvOpeningBalanceItem.get(i);
            currObj.setRejected(true);
            tinvOpeningBalanceItemRepository.save(currObj);
        }
        respMap.put("msg", "Operation Successful");
        respMap.put("success", "1");
        return respMap;
    }

    @RequestMapping("listbyid")
    public List<TinvOpeningBalanceItem> getListByItemId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "itemId", required = false, defaultValue = " ") Long itemId, @RequestParam(value = "date", required = false, defaultValue = " ") Date date) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvOpeningBalanceItemRepository.findByItemByForId(itemId, date);
    }

    @RequestMapping("listbyidByUnitId")
    public List<TinvOpeningBalanceItem> getListByItemIdByUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "itemId", required = false, defaultValue = " ") Long itemId, @RequestParam(value = "date", required = false, defaultValue = " ") Date date,
                                                                @RequestParam(value = "unitId", required = false, defaultValue = " ") Long unitId, @RequestParam(value = "storeId", required = false, defaultValue = " ") Long storeId) {
        TenantContext.setCurrentTenant(tenantName);
        //        return tinvOpeningBalanceItemRepository.findByItemByForIdByUnitId(itemId, date, unitId);
        return tinvOpeningBalanceItemRepository.findByItemByForIdByUnitIdAndStoreId(itemId, date, unitId, storeId);
    }

    @RequestMapping("transferItemOfIssue")
    public Map<String, String> getItemForUpdate(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvOpeningBalanceItem> tinvOpeningBalanceItem) {
        TenantContext.setCurrentTenant(tenantName);
        try {
            for (TinvOpeningBalanceItem openingBalanceItem : tinvOpeningBalanceItem) {
                if (openingBalanceItem.getObiItemBatchCode() == null) {
                    openingBalanceItem.setObiItemBatchCode("");
                }
                TinvOpeningBalanceItem obj = new TinvOpeningBalanceItem();
//                obj = tinvOpeningBalanceItemRepository.findAllByObiStoreIdStoreIdAndOpeningBalanceUnitIdUnitIdAndObiItemBatchCodeAndObiobIdIsNullAndIsActiveTrueAndIsDeletedFalse(openingBalanceItem.getIssueStoreId().getStoreId(), openingBalanceItem.getOpeningBalanceUnitId().getUnitId(), openingBalanceItem.getObiItemBatchCode());
                obj = tinvOpeningBalanceItemRepository.findTopByObiStoreIdStoreIdAndOpeningBalanceUnitIdUnitIdAndObiItemBatchCodeAndObiobIdIsNullAndIsActiveTrueAndIsDeletedFalse(openingBalanceItem.getIssueStoreId().getStoreId(), openingBalanceItem.getOpeningBalanceUnitId().getUnitId(), openingBalanceItem.getObiItemBatchCode());
                if (obj != null) {
                    TinvOpeningBalanceItem ob = new TinvOpeningBalanceItem();
                    ob = tinvOpeningBalanceItemRepository.getById(openingBalanceItem.getObiId());
                    ob.setObiItemQuantity(ob.getObiItemQuantity() - openingBalanceItem.getTakenQty());
//                    obj.setObiItemQuantity(obj.getObiItemQuantity() + openingBalanceItem.getTakenQty());
                    obj.setIsApproved(true);
                    ob.setIsApproved(true);
                    tinvOpeningBalanceItemRepository.save(obj);
                    tinvOpeningBalanceItemRepository.save(ob);

                } else {
                    TinvOpeningBalanceItem ob = new TinvOpeningBalanceItem();
                    ob = tinvOpeningBalanceItemRepository.getById(openingBalanceItem.getObiId());
                    ob.setObiItemQuantity(ob.getObiItemQuantity() - openingBalanceItem.getTakenQty());
                    TinvOpeningBalanceItem newItemForStore = new TinvOpeningBalanceItem();
                    if (openingBalanceItem.getIssueStoreId() != null)
                        newItemForStore.setObiStoreId(openingBalanceItem.getIssueStoreId());
                    if (openingBalanceItem.getTakenQty() != 0)
                        newItemForStore.setObiItemQuantity(openingBalanceItem.getTakenQty());
                    if (openingBalanceItem.getObiItemAmount() >= 0)
                        newItemForStore.setObiItemAmount(openingBalanceItem.getObiItemAmount());
                    if (openingBalanceItem.getObiItemBatchCode() != null)
                        newItemForStore.setObiItemBatchCode(openingBalanceItem.getObiItemBatchCode());
                    if (openingBalanceItem.getObiItemCode() != null)
                        newItemForStore.setObiItemCode(openingBalanceItem.getObiItemCode());
                    if (openingBalanceItem.getObiItemCategoryId() != null)
                        newItemForStore.setObiItemCategoryId(openingBalanceItem.getObiItemCategoryId());
                    if (openingBalanceItem.getObiItemConversionFactor() != 0)
                        newItemForStore.setObiItemConversionFactor(openingBalanceItem.getObiItemConversionFactor());
                    if (openingBalanceItem.getObiItemCost() >= 0)
                        newItemForStore.setObiItemCost(openingBalanceItem.getObiItemCost());
                    if (openingBalanceItem.getObiItemExpiryDate() != null)
                        newItemForStore.setObiItemExpiryDate(openingBalanceItem.getObiItemExpiryDate());
                    if (openingBalanceItem.getObiItemDespensingIdtId() != null)
                        newItemForStore.setObiItemDespensingIdtId(openingBalanceItem.getObiItemDespensingIdtId());
                    if (openingBalanceItem.getObiItemDiscountOnSaleAmount() >= 0)
                        newItemForStore.setObiItemDiscountOnSaleAmount(openingBalanceItem.getObiItemDiscountOnSaleAmount());
                    if (openingBalanceItem.getObiItemDiscountOnSalePercentage() >= 0)
                        newItemForStore.setObiItemDiscountOnSalePercentage(openingBalanceItem.getObiItemDiscountOnSalePercentage());
                    if (openingBalanceItem.getObiItemId() != null)
                        newItemForStore.setObiItemId(openingBalanceItem.getObiItemId());
                    if (openingBalanceItem.getObiItemMrp() >= 0)
                        newItemForStore.setObiItemMrp(openingBalanceItem.getObiItemMrp());
                    if (openingBalanceItem.getObiItemPurchaseIdtId() != null)
                        newItemForStore.setObiItemPurchaseIdtId(openingBalanceItem.getObiItemPurchaseIdtId());
                    if (openingBalanceItem.getObiItemName() != null)
                        newItemForStore.setObiItemName(openingBalanceItem.getObiItemName());
                    if (openingBalanceItem.getObiItemNetAmount() >= 0)
                        newItemForStore.setObiItemNetAmount(openingBalanceItem.getObiItemNetAmount());
                    if (openingBalanceItem.getObiItemTaxAmount() >= 0)
                        newItemForStore.setObiItemTaxAmount(openingBalanceItem.getObiItemTaxAmount());
                    if (openingBalanceItem.getObiItemTaxId() != null)
                        newItemForStore.setObiItemTaxId(openingBalanceItem.getObiItemTaxId());
                    if (openingBalanceItem.getOpeningBalanceUnitId() != null)
                        newItemForStore.setOpeningBalanceUnitId(openingBalanceItem.getOpeningBalanceUnitId());
                    if (openingBalanceItem.getObiStoreId() == null) {
                        newItemForStore.setObiStoreId(null);
                    }
                    newItemForStore.setIsApproved(false);
                    tinvOpeningBalanceItemRepository.save(newItemForStore);
                    tinvOpeningBalanceItemRepository.save(ob);
                }

            }
            respMap.put("msg", "Added Successfully");
            return respMap;
        } catch (Exception ex) {
            ex.printStackTrace();
            respMap.put("msg", "Operation Failed");
            return respMap;
        }

    }

    @RequestMapping("transferItemOfIssueReturn")
    public Map<String, String> getItemForUpdateReturn(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvOpeningBalanceItem> tinvOpeningBalanceItem) {
        TenantContext.setCurrentTenant(tenantName);
        try {
            for (TinvOpeningBalanceItem openingBalanceItem : tinvOpeningBalanceItem) {
                if (openingBalanceItem.getObiItemBatchCode() == null) {
                    openingBalanceItem.setObiItemBatchCode("");
                }
                TinvOpeningBalanceItem obj = new TinvOpeningBalanceItem();
//                obj = tinvOpeningBalanceItemRepository.findAllByObiStoreIdStoreIdAndOpeningBalanceUnitIdUnitIdAndObiItemBatchCodeAndObiobIdIsNullAndIsActiveTrueAndIsDeletedFalse(openingBalanceItem.getObiStoreId().getStoreId(), openingBalanceItem.getOpeningBalanceUnitId().getUnitId(), openingBalanceItem.getObiItemBatchCode());
                obj = tinvOpeningBalanceItemRepository.findAllByObiStoreIdStoreIdAndOpeningBalanceUnitIdUnitIdAndObiItemIdItemIdAndObiItemBatchCodeAndObiobIdIsNullAndIsActiveTrueAndIsDeletedFalse(openingBalanceItem.getObiStoreId().getStoreId(), openingBalanceItem.getOpeningBalanceUnitId().getUnitId(), openingBalanceItem.getObiItemId().getItemId(), openingBalanceItem.getObiItemBatchCode());
                if (obj != null) {
                    TinvOpeningBalanceItem ob = new TinvOpeningBalanceItem();
                    ob = tinvOpeningBalanceItemRepository.getById(openingBalanceItem.getObiId());
//                    ob.setObiItemQuantity(ob.getObiItemQuantity() + openingBalanceItem.getRetunQty());
                    obj.setObiItemQuantity(obj.getObiItemQuantity() + openingBalanceItem.getRetunQty());
//                    ob.setIsApproved(true);
                    obj.setIsApproved(true);
                    tinvOpeningBalanceItemRepository.save(obj);
//                    tinvOpeningBalanceItemRepository.save(ob);
                } else {
                    TinvOpeningBalanceItem ob = new TinvOpeningBalanceItem();
                    ob = tinvOpeningBalanceItemRepository.getById(openingBalanceItem.getObiId());
//                    ob.setObiItemQuantity(ob.getObiItemQuantity() - openingBalanceItem.getTakenQty());
                    TinvOpeningBalanceItem newItemForStore = new TinvOpeningBalanceItem();
                    if (openingBalanceItem.getIssueStoreId() != null) {
//                        newItemForStore.setObiStoreId(openingBalanceItem.getIssueStoreId());
                        newItemForStore.setObiStoreId(openingBalanceItem.getObiStoreId());
                    }
                    if (openingBalanceItem.getTakenQty() != 0)
                        newItemForStore.setObiItemQuantity(openingBalanceItem.getTakenQty());
                    if (openingBalanceItem.getObiItemAmount() >= 0)
                        newItemForStore.setObiItemAmount(openingBalanceItem.getObiItemAmount());
                    if (openingBalanceItem.getObiItemBatchCode() != null)
                        newItemForStore.setObiItemBatchCode(openingBalanceItem.getObiItemBatchCode());
                    if (openingBalanceItem.getObiItemCode() != null)
                        newItemForStore.setObiItemCode(openingBalanceItem.getObiItemCode());
                    if (openingBalanceItem.getObiItemCategoryId() != null)
                        newItemForStore.setObiItemCategoryId(openingBalanceItem.getObiItemCategoryId());
                    if (openingBalanceItem.getObiItemConversionFactor() != 0)
                        newItemForStore.setObiItemConversionFactor(openingBalanceItem.getObiItemConversionFactor());
                    if (openingBalanceItem.getObiItemCost() >= 0)
                        newItemForStore.setObiItemCost(openingBalanceItem.getObiItemCost());
                    if (openingBalanceItem.getObiItemExpiryDate() != null)
                        newItemForStore.setObiItemExpiryDate(openingBalanceItem.getObiItemExpiryDate());
                    if (openingBalanceItem.getObiItemDespensingIdtId() != null)
                        newItemForStore.setObiItemDespensingIdtId(openingBalanceItem.getObiItemDespensingIdtId());
                    if (openingBalanceItem.getObiItemDiscountOnSaleAmount() >= 0)
                        newItemForStore.setObiItemDiscountOnSaleAmount(openingBalanceItem.getObiItemDiscountOnSaleAmount());
                    if (openingBalanceItem.getObiItemDiscountOnSalePercentage() >= 0)
                        newItemForStore.setObiItemDiscountOnSalePercentage(openingBalanceItem.getObiItemDiscountOnSalePercentage());
                    if (openingBalanceItem.getObiItemId() != null)
                        newItemForStore.setObiItemId(openingBalanceItem.getObiItemId());
                    if (openingBalanceItem.getObiItemMrp() >= 0)
                        newItemForStore.setObiItemMrp(openingBalanceItem.getObiItemMrp());
                    if (openingBalanceItem.getObiItemPurchaseIdtId() != null)
                        newItemForStore.setObiItemPurchaseIdtId(openingBalanceItem.getObiItemPurchaseIdtId());
                    if (openingBalanceItem.getObiItemName() != null)
                        newItemForStore.setObiItemName(openingBalanceItem.getObiItemName());
                    if (openingBalanceItem.getObiItemNetAmount() >= 0)
                        newItemForStore.setObiItemNetAmount(openingBalanceItem.getObiItemNetAmount());
                    if (openingBalanceItem.getObiItemTaxAmount() >= 0)
                        newItemForStore.setObiItemTaxAmount(openingBalanceItem.getObiItemTaxAmount());
                    if (openingBalanceItem.getObiItemTaxId() != null)
                        newItemForStore.setObiItemTaxId(openingBalanceItem.getObiItemTaxId());
                    if (openingBalanceItem.getOpeningBalanceUnitId() != null)
                        newItemForStore.setOpeningBalanceUnitId(openingBalanceItem.getOpeningBalanceUnitId());
//                    newItemForStore.setIsApproved(false);
                    newItemForStore.setIsApproved(true);
                    tinvOpeningBalanceItemRepository.save(newItemForStore);
                    tinvOpeningBalanceItemRepository.save(ob);
                }

            }
            respMap.put("msg", "Added Successfully");
            return respMap;
        } catch (Exception ex) {
            ex.printStackTrace();
            respMap.put("msg", "Operation Failed");
            return respMap;
        }

    }

    @RequestMapping("transferItemOfUpdateReturnItem")
    public Map<String, String> getItemForUpdateQtyAsTaken(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvOpeningBalanceItem> tinvOpeningBalanceItem) {
        TenantContext.setCurrentTenant(tenantName);
        try {
            for (TinvOpeningBalanceItem openingBalanceItem : tinvOpeningBalanceItem) {
                TinvOpeningBalanceItem obj = new TinvOpeningBalanceItem();
                obj = tinvOpeningBalanceItemRepository.findAllByObiStoreIdStoreIdAndObiItemBatchCodeAndIsActiveTrueAndIsDeletedFalse(openingBalanceItem.getIssueStoreId().getStoreId(), openingBalanceItem.getObiItemBatchCode());
                if (obj != null) {
                    TinvOpeningBalanceItem ob = new TinvOpeningBalanceItem();
                    ob = tinvOpeningBalanceItemRepository.getById(openingBalanceItem.getObiId());
                    ob.setObiItemQuantity(ob.getObiItemQuantity() - openingBalanceItem.getTakenQty());
                    obj.setObiItemQuantity(obj.getObiItemQuantity() + openingBalanceItem.getTakenQty());
                    tinvOpeningBalanceItemRepository.save(obj);
                    tinvOpeningBalanceItemRepository.save(ob);
                }

            }
            respMap.put("msg", "Added Successfully");
            return respMap;
        } catch (Exception ex) {
            respMap.put("msg", "Operation Failed");
            return respMap;
        }

    }

    @RequestMapping("transferItemOfIssueForNst")
    public Map<String, String> getItemForUpdateForNst(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvOpeningBalanceItem> tinvOpeningBalanceItem) {
        TenantContext.setCurrentTenant(tenantName);
        try {
            for (TinvOpeningBalanceItem openingBalanceItem : tinvOpeningBalanceItem) {
                if (tinvOpeningBalanceItem != null) {
                    TinvOpeningBalanceItem ob = new TinvOpeningBalanceItem();
                    ob = tinvOpeningBalanceItemRepository.getById(openingBalanceItem.getObiId());
                    ob.setObiItemQuantity(ob.getObiItemQuantity() - openingBalanceItem.getTakenQty());
                    tinvOpeningBalanceItemRepository.save(ob);

                } else {
                    TinvOpeningBalanceItem ob = new TinvOpeningBalanceItem();
                    ob = tinvOpeningBalanceItemRepository.getById(openingBalanceItem.getObiId());
                    ob.setObiItemQuantity(ob.getObiItemQuantity() - openingBalanceItem.getTakenQty());
                    TinvOpeningBalanceItem newItemForStore = new TinvOpeningBalanceItem();
                    if (openingBalanceItem.getIssueStoreId() != null)
                        newItemForStore.setObiStoreId(openingBalanceItem.getIssueStoreId());
                    if (openingBalanceItem.getTakenQty() != 0)
                        newItemForStore.setObiItemQuantity(openingBalanceItem.getTakenQty());
                    if (openingBalanceItem.getObiItemAmount() >= 0)
                        newItemForStore.setObiItemAmount(openingBalanceItem.getObiItemAmount());
                    if (openingBalanceItem.getObiItemBatchCode() != null)
                        newItemForStore.setObiItemBatchCode(openingBalanceItem.getObiItemBatchCode());
                    if (openingBalanceItem.getObiItemCode() != null)
                        newItemForStore.setObiItemCode(openingBalanceItem.getObiItemCode());
                    if (openingBalanceItem.getObiItemCategoryId() != null)
                        newItemForStore.setObiItemCategoryId(openingBalanceItem.getObiItemCategoryId());
                    if (openingBalanceItem.getObiItemConversionFactor() != 0)
                        newItemForStore.setObiItemConversionFactor(openingBalanceItem.getObiItemConversionFactor());
                    if (openingBalanceItem.getObiItemCost() >= 0)
                        newItemForStore.setObiItemCost(openingBalanceItem.getObiItemCost());
                    if (openingBalanceItem.getObiItemExpiryDate() != null)
                        newItemForStore.setObiItemExpiryDate(openingBalanceItem.getObiItemExpiryDate());
                    if (openingBalanceItem.getObiItemDespensingIdtId() != null)
                        newItemForStore.setObiItemDespensingIdtId(openingBalanceItem.getObiItemDespensingIdtId());
                    if (openingBalanceItem.getObiItemDiscountOnSaleAmount() >= 0)
                        newItemForStore.setObiItemDiscountOnSaleAmount(openingBalanceItem.getObiItemDiscountOnSaleAmount());
                    if (openingBalanceItem.getObiItemDiscountOnSalePercentage() >= 0)
                        newItemForStore.setObiItemDiscountOnSalePercentage(openingBalanceItem.getObiItemDiscountOnSalePercentage());
                    if (openingBalanceItem.getObiItemId() != null)
                        newItemForStore.setObiItemId(openingBalanceItem.getObiItemId());
                    if (openingBalanceItem.getObiItemMrp() >= 0)
                        newItemForStore.setObiItemMrp(openingBalanceItem.getObiItemMrp());
                    if (openingBalanceItem.getObiItemPurchaseIdtId() != null)
                        newItemForStore.setObiItemPurchaseIdtId(openingBalanceItem.getObiItemPurchaseIdtId());
                    if (openingBalanceItem.getObiItemName() != null)
                        newItemForStore.setObiItemName(openingBalanceItem.getObiItemName());
                    if (openingBalanceItem.getObiItemNetAmount() >= 0)
                        newItemForStore.setObiItemNetAmount(openingBalanceItem.getObiItemNetAmount());
                    if (openingBalanceItem.getObiItemTaxAmount() >= 0)
                        newItemForStore.setObiItemTaxAmount(openingBalanceItem.getObiItemTaxAmount());
                    if (openingBalanceItem.getObiItemTaxId() != null)
                        newItemForStore.setObiItemTaxId(openingBalanceItem.getObiItemTaxId());
                    if (openingBalanceItem.getOpeningBalanceUnitId() != null)
                        newItemForStore.getOpeningBalanceUnitId().setUnitId((int) openingBalanceItem.getOpeningBalanceUnitId().getUnitId());
                    tinvOpeningBalanceItemRepository.save(newItemForStore);
                    tinvOpeningBalanceItemRepository.save(ob);
                }
            }
            respMap.put("msg", "Added Successfully");
            respMap.put("success", "1");
            return respMap;
        } catch (Exception ex) {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
            return respMap;
        }

    }

    @GetMapping("byObiId/{id}")
    public List<TinvOpeningBalanceItem> getListOfItemById(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvOpeningBalanceItemRepository.findAllByObiobIdObIdAndIsActiveTrueAndIsDeletedFalse(id);
    }

    @GetMapping("bytotalitembyId/{id}")
    public String getCountByObiId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        String Query = "select count(p.obi_id) from tinv_opening_balance_item p where p.obiob_id=" + id;
        return entityManager.createNativeQuery(Query).getSingleResult().toString();
    }

    @RequestMapping("search")
    public List<TinvOpeningBalanceItem> getListOfItemsDependsOnSerch(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchOprningBalanceItem searchOprningBalanceItem) {
        TenantContext.setCurrentTenant(tenantName);
        if (searchOprningBalanceItem == null) {
            SearchOprningBalanceItem sb = new SearchOprningBalanceItem();
            searchOprningBalanceItem = sb;
        }
        String queryInya = "";
        String forItemCategories = "";
        String forItemGroup = "";
        String forItemStore = "";
        String forItemGenericName = "";
        String forItemStorage = "";
        String forItemDespensingName = "";
        String forItemBachCode = "";
        String forExpiryDate = "";
        String forNearByExpiryDate = "";
        String forHighRisk = "";
        String forNarcotics = "";
        if (searchOprningBalanceItem.getItemName() == null)
            searchOprningBalanceItem.setItemName("");
        if (searchOprningBalanceItem.getItemBatchCode() == null)
            searchOprningBalanceItem.setItemBatchCode("");
        if (searchOprningBalanceItem.getItemBrandName() == null)
            searchOprningBalanceItem.setItemBrandName("");
        if (searchOprningBalanceItem.getItemCode() == null)
            searchOprningBalanceItem.setItemCode("");
        if (searchOprningBalanceItem.getItemExpiryDate() == null || searchOprningBalanceItem.getItemExpiryDate().equals("")) {
            searchOprningBalanceItem.setItemExpiryDate("");
        } else {
            forExpiryDate = " AND DATE_FORMAT(i.obiItemExpiryDate,'%Y-%m-%d') = '" + searchOprningBalanceItem.getItemExpiryDate() + "' ";
        }
        if (searchOprningBalanceItem.getItemCategoryId().getIcId() != 0)
            forItemCategories = " AND i.obiItemId.itemIcId.icId=" + searchOprningBalanceItem.getItemCategoryId().getIcId() + " ";
        if (searchOprningBalanceItem.getItemGroupId().getIgId() != 0)
            forItemGroup = "AND i.obiItemId.itemIgId.igId=" + searchOprningBalanceItem.getItemGroupId().getIgId() + "";
        if (searchOprningBalanceItem.getItemStoreId().getStoreId() != 0)
            forItemStore = "AND i.obiStoreId.storeId=" + searchOprningBalanceItem.getItemStoreId().getStoreId() + "";
        if (searchOprningBalanceItem.getItemGenericId().getGenericId() != 0)
            forItemGenericName = "AND i.obiItemId.itemGenericId.genericId=" + searchOprningBalanceItem.getItemGenericId().getGenericId() + "";
        if (searchOprningBalanceItem.getItemStorageId().getIstId() != 0)
            forItemStorage = "AND i.obiItemId.itemIstId.istId=" + searchOprningBalanceItem.getItemStorageId().getIstId() + "";
        if (searchOprningBalanceItem.getItemDespensingId().getIdtId() != 0)
            forItemDespensingName = "AND i.obiItemId.itemIdtId.idtId=" + searchOprningBalanceItem.getItemDespensingId().getIdtId() + "";
        if (searchOprningBalanceItem.getItemBatchCode() != "") {
            forItemBachCode = "AND  i.obiItemBatchCode like '%" + searchOprningBalanceItem.getItemBatchCode() + "%'";
        }
        if (searchOprningBalanceItem.getForNearExpiry() == 1) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            Date curr = new Date();
//            Calendar c = Calendar.getInstance();
//            Date newDate = null;
//            try{
//                //Setting the date to the given date
//                c.setTime(sdf.parse(curr.toString()));
//                c.add(Calendar.DAY_OF_MONTH, 4);
//                //Date after adding the days to the given date
//                String new1 = sdf.format(c.getTime());
//                 newDate = sdf.parse(new1);
//            }catch(ParseException e){
//                e.printStackTrace();
//            }
            java.util.Date newDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(newDate);
            cal.add(Calendar.MONTH, 3);
            String new1 = sdf.format(cal.getTime());
//
            System.out.println("newDate : " + newDate);
//            forNearByExpiryDate =  " AND DATE_FORMAT(i.obiItemExpiryDate,'%Y-%m-%d') = '" + newDate + "' ";
//            forNearByExpiryDate =  " AND IF(DATE(tinvopenin0_.obi_item_expiry_date) > CURDATE(), NOT((DATE(tinvopenin0_.obi_item_expiry_date)>= DATE(NOW() + INTERVAL 4 MONTH))), '') ";
//            forNearByExpiryDate =  " AND IF(DATE(i.obiItemExpiryDate) > CURDATE(), NOT((DATE(i.obiItemExpiryDate)>= DATE(NOW() + 4 MONTH))), '') ";
//            forNearByExpiryDate =  "AND IF(DATE(i.obiItemExpiryDate) > CURDATE(), NOT(DATE(i.obiItemExpiryDate) >= '" + new1 + "'),'') ";
            forNearByExpiryDate = "AND DATE(i.obiItemExpiryDate) > CURDATE() AND NOT(DATE(i.obiItemExpiryDate) >= '" + new1 + "') ";
//            forNearByExpiryDate =  "AND CASE WHEN (DATE(i.obiItemExpiryDate) > CURDATE()) THEN (DATE(i.obiItemExpiryDate) < ('" + new1 +"')) ELSE '' END ";
        }
        if (searchOprningBalanceItem.getForNearExpiry() == 2) {
            forNearByExpiryDate = " AND DATE(i.obiItemExpiryDate) IS NULL ";
        }
        if (searchOprningBalanceItem.getForHighRisk() != 0) {
            forHighRisk = " AND i.obiItemId.itemHighRisk = " + searchOprningBalanceItem.getForHighRisk();
        }
        if (searchOprningBalanceItem.getForNarcotics() != 0) {
            forNarcotics = " AND i.obiItemId.itemNarcotics = " + searchOprningBalanceItem.getForNarcotics();
        }
        queryInya = "Select i from TinvOpeningBalanceItem i Where i.openingBalanceUnitId.unitId=" + searchOprningBalanceItem.getUnitId() + " And i.obiItemId.itemName like '%" + searchOprningBalanceItem.getItemName() + "%' And  i.obiItemId.itemBrandName like '%" + searchOprningBalanceItem.getItemBrandName() + "%' AND  i.obiItemId.itemCode like '%" + searchOprningBalanceItem.getItemCode() + "%' " + forExpiryDate + " " + forItemBachCode + " " + forItemCategories + "  " + forItemGroup + "  " + forItemStore + " " + forItemGenericName + " " + forItemStorage + " " + forItemDespensingName + forNearByExpiryDate + " " + forHighRisk + " " + forNarcotics + " and i.isActive=1 and i.isDeleted=0 and i.isApproved=1 and i.obiItemQuantity > 0 order by i.obiId desc  ";
        System.out.println(queryInya);
        long count = (Long) entityManager.createQuery("Select count(i.obiId) from TinvOpeningBalanceItem i Where i.openingBalanceUnitId.unitId=" + searchOprningBalanceItem.getUnitId() + " And i.obiItemId.itemName like '%" + searchOprningBalanceItem.getItemName() + "%' And  i.obiItemId.itemBrandName like '%" + searchOprningBalanceItem.getItemBrandName() + "%' AND  i.obiItemId.itemCode like '%" + searchOprningBalanceItem.getItemCode() + "%' " + forExpiryDate + " " + forItemBachCode + " " + forItemCategories + "  " + forItemGroup + "  " + forItemStore + " " + forItemGenericName + " " + forItemStorage + " " + forItemDespensingName + " " + forNearByExpiryDate + " " + forHighRisk + " " + forNarcotics + "  and i.isActive=1 and i.isDeleted=0 and i.isApproved=1 and i.obiItemQuantity > 0 order by i.obiId desc  ").getSingleResult();
        //System.out.println(queryInya);
        List<TinvOpeningBalanceItem> ItemList = entityManager.createQuery(queryInya, TinvOpeningBalanceItem.class).setFirstResult(((searchOprningBalanceItem.getOffset() - 1) * searchOprningBalanceItem.getLimit())).setMaxResults(searchOprningBalanceItem.getLimit()).getResultList();
      /*  List<InvItem> ObjectArray=new ArrayList<>();
        for (InvItem obj : ItemList) {
            obj.setCount(count);
            ObjectArray.add(obj);
        }*/
        if (ItemList.size() > 0)
            ItemList.get(0).setCount(count);
        return ItemList;
    }

    @RequestMapping("expiry")
    public List<TinvOpeningBalanceItem> getListOfItemsDependsOnSerchForExpiry(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchOprningBalanceItem searchOprningBalanceItem) {
        TenantContext.setCurrentTenant(tenantName);
        if (searchOprningBalanceItem == null) {
            SearchOprningBalanceItem sb = new SearchOprningBalanceItem();
            searchOprningBalanceItem = sb;
        }
        String queryInya = "";
        String forItemCategories = "";
        String forItemGroup = "";
        String forItemStore = "";
        if (searchOprningBalanceItem.getItemName() == null)
            searchOprningBalanceItem.setItemName("");
        if (searchOprningBalanceItem.getItemBatchCode() == null)
            searchOprningBalanceItem.setItemBatchCode("");
        if (searchOprningBalanceItem.getItemBrandName() == null)
            searchOprningBalanceItem.setItemBrandName("");
        if (searchOprningBalanceItem.getItemCode() == null)
            searchOprningBalanceItem.setItemCode("");
        if (searchOprningBalanceItem.getItemExpiryFromDate() == null) {
            String sDate1 = "1998/12/31";
            searchOprningBalanceItem.setItemExpiryFromDate(sDate1);
        }
        if (searchOprningBalanceItem.getItemExpiryToDate() == null) {
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchOprningBalanceItem.setItemExpiryToDate(date);
        }
        if (searchOprningBalanceItem.getItemCategoryId().getIcId() != 0)
            forItemCategories = " AND i.obiItemId.itemIcId.icId=" + searchOprningBalanceItem.getItemCategoryId().getIcId() + " ";
        if (searchOprningBalanceItem.getItemGroupId().getIgId() != 0)
            forItemGroup = "AND i.obiItemId.itemIgId.igId=" + searchOprningBalanceItem.getItemGroupId().getIgId() + "";

     /*   if (searchOprningBalanceItem.getItemStoreId().getStoreId() != 0)
            forItemStore = "AND i.obiStoreId.storeId=" + searchOprningBalanceItem.getItemStoreId().getStoreId() + "";*/
        queryInya = "Select i from TinvOpeningBalanceItem i Where i.openingBalanceUnitId.unitId=" + searchOprningBalanceItem.getUnitId() + " And i.obiItemId.itemName like '%" + searchOprningBalanceItem.getItemName() + "%' And  i.obiItemId.itemBrandName like '%" + searchOprningBalanceItem.getItemBrandName() + "%' AND  i.obiItemId.itemCode like '%" + searchOprningBalanceItem.getItemCode() + "%' AND  i.obiItemBatchCode like '%" + searchOprningBalanceItem.getItemBatchCode() + "%'AND  i.obiItemQuantity>0 AND date(i.obiItemExpiryDate) between   " + " ' " + searchOprningBalanceItem.getItemExpiryFromDate() + " ' AND  " + "'" + searchOprningBalanceItem.getItemExpiryToDate() + "'" + forItemCategories + " " + forItemGroup + "    " + forItemStore + "  " + " and  i.isApproved=1 order by i.obiId desc ";
        System.out.println(queryInya);
        // long count = (Long)entityManager.createQuery("Select i from TinvOpeningBalanceItem i Where i.obiItemId.itemName like '%" + searchOprningBalanceItem.getItemName() + "%' And  i.obiItemId.itemBrandName like '%" + searchOprningBalanceItem.getItemBrandName() + "%' AND  i.obiItemId.itemCode like '%" + searchOprningBalanceItem.getItemCode() + "%' AND  i.obiItemBatchCode like '%" + searchOprningBalanceItem.getItemBatchCode()+ "%' AND date(i.obiItemExpiryDate) between   " + " ' " + searchOprningBalanceItem.getItemExpiryFromDate() + " ' AND  " + "'" + searchOprningBalanceItem.getItemExpiryToDate() +    "+ forItemCategories +  " + forItemGroup +    "  +forItemStore+ " +  "  order by i.obiId desc  ").getSingleResult();
        List<TinvOpeningBalanceItem> ItemList = entityManager.createQuery(queryInya, TinvOpeningBalanceItem.class).getResultList();
      /*  List<InvItem> ObjectArray=new ArrayList<>();
        for (InvItem obj : ItemList) {
            obj.setCount(count);
            ObjectArray.add(obj);
        }*/
      /*  if(ItemList.size()>0)
            ItemList.get(0).setCount(count);*/
        return ItemList;
    }

    @RequestMapping("returnItem")
    public Map<String, String> returnItemDepends(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvOpeningBalanceItem> searchOprningBalanceItemList) {
        TenantContext.setCurrentTenant(tenantName);
        for (TinvOpeningBalanceItem searchOprningBalanceItem : searchOprningBalanceItemList) {
            TinvOpeningBalanceItem tinvOpeningBalanceItem = tinvOpeningBalanceItemRepository.getById(searchOprningBalanceItem.getObiId());
            if (tinvOpeningBalanceItem != null) {
                tinvOpeningBalanceItem.setObiItemQuantity(searchOprningBalanceItem.getObiItemQuantity());
                tinvOpeningBalanceItemRepository.save(tinvOpeningBalanceItem);

            }

        }
        respMap.put("msg", "Added Successfully");
        respMap.put("success", "1");
        return respMap;
    }

    @RequestMapping("adjustItem")
    public Map<String, String> UodateItem(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvOpeningBalanceItem> tinvOpeningBalanceItem) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvOpeningBalanceItem.size() > 0) {
            for (TinvOpeningBalanceItem ob : tinvOpeningBalanceItem) {
                TinvOpeningBalanceItem oldTinvOpeningBalanceItem = tinvOpeningBalanceItemRepository.getById(ob.getObiId());
                oldTinvOpeningBalanceItem.setObiItemQuantity(ob.getObiItemQuantity());
                oldTinvOpeningBalanceItem.setObiItemExpiryDate(ob.getObiItemExpiryDate());
                oldTinvOpeningBalanceItem.setObiItemBatchCode(ob.getObiItemBatchCode());
                oldTinvOpeningBalanceItem.setObiItemMrp(ob.getObiItemMrp());
                tinvOpeningBalanceItemRepository.save(oldTinvOpeningBalanceItem);

            }
            respMap.put("msg", "Added Successfully");
            respMap.put("success", "1");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }

    }

    @RequestMapping("scrapeitem")
    public Map<String, String> ScrapeItem(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvOpeningBalanceItem> tinvOpeningBalanceItem) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvOpeningBalanceItem.size() > 0) {
            for (TinvOpeningBalanceItem ob : tinvOpeningBalanceItem) {
                TinvOpeningBalanceItem oldTinvOpeningBalanceItem = tinvOpeningBalanceItemRepository.getById(ob.getObiId());
                oldTinvOpeningBalanceItem.setObiItemQuantity(ob.getObiItemQuantity());
                tinvOpeningBalanceItemRepository.save(oldTinvOpeningBalanceItem);
            }
            respMap.put("msg", "Added Successfully");
            respMap.put("success", "1");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }

    }

    @GetMapping
    @RequestMapping("getitemsbystorename")
    public Iterable<TinvOpeningBalanceItem> getItemsByStorename(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "storeName", required = false) String storeName, @RequestParam(value = "itemName", required = false) String itemName) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("item list1232 :storeName" + storeName + " gString :" + itemName);
        System.out.println("item list :" + itemName + ":" + tinvOpeningBalanceItemRepository.findAllByObiStoreIdStoreNameAndObiItemIdItemNameContainsAndIsActiveTrueAndIsDeletedFalse(storeName, itemName));
        return tinvOpeningBalanceItemRepository.findAllByObiStoreIdStoreNameAndObiItemIdItemNameContainsAndIsActiveTrueAndIsDeletedFalse(storeName, itemName);
    }

    //    public Map<String,String> reduceItemQty(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "obiId", required = false) Long obiId,
//                                                                @RequestParam(value = "obiItemQuantity", requ`ired = false) int obiItemQuantity)
    @RequestMapping(value = "/reduceitemqty", method = RequestMethod.POST)
    public Map<String, String> reduceItemQty(@RequestHeader("X-tenantId") String tenantName, @RequestBody JSONObject obj) {
        TenantContext.setCurrentTenant(tenantName);
        String obiIdStr = String.valueOf(obj.get("obiId"));
        String obiItemQuantityStr = String.valueOf(obj.get("obiItemQuantity"));
        System.out.println("reduceitemqty :obiId" + obiIdStr + " obiItemQuantity :" + obiItemQuantityStr);
        System.out.println("reduceitemqty :" + obj.get("obiItemQuantity") + ":" + tinvOpeningBalanceItemRepository.findAllByObiIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(obiIdStr)));
        TinvOpeningBalanceItem tinvOpeningBalanceItem = tinvOpeningBalanceItemRepository.findAllByObiIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(obiIdStr));
        tinvOpeningBalanceItem.setObiItemQuantity(Integer.parseInt(obiItemQuantityStr));
        tinvOpeningBalanceItem.setObiId(Integer.parseInt(obiIdStr));
        tinvOpeningBalanceItemRepository.save(tinvOpeningBalanceItem);
        respMap.put("msg", "Quantity Reduce Successfully");
        respMap.put("success", "1");
        return respMap;
    }

    @GetMapping
    @RequestMapping("getAvailItemQty")
    public String getAvailItemQty(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "itemId") long itemId, @RequestParam(value = "unitId") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "select ifnull(sum(obi.obi_item_quantity),0) as availQty from tinv_opening_balance_item obi where obi.obi_item_id=" + itemId + " and obi.opening_balance_unit_id=" + unitId;
        BigDecimal sum = (BigDecimal) entityManager.createNativeQuery(query).getSingleResult();
        return sum.toString();
    }

    @RequestMapping("transferItemOfReturnIssueQty")
    public Map<String, String> transferItemOfReturnIssue(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvOpeningBalanceItem> tinvOpeningBalanceItem) {
        TenantContext.setCurrentTenant(tenantName);
        try {
            for (TinvOpeningBalanceItem openingBalanceItem : tinvOpeningBalanceItem) {
                if (openingBalanceItem.getObiItemBatchCode() == null) {
                    openingBalanceItem.setObiItemBatchCode("");
                }
                TinvOpeningBalanceItem ob = new TinvOpeningBalanceItem();
                ob = tinvOpeningBalanceItemRepository.findAllByObiStoreIdStoreIdAndOpeningBalanceUnitIdUnitIdAndObiItemBatchCodeAndIsActiveTrueAndIsDeletedFalse(openingBalanceItem.getIssueStoreId().getStoreId(), openingBalanceItem.getChangeUnitId(), openingBalanceItem.getObiItemBatchCode());
                if (ob != null) {
                    TinvOpeningBalanceItem obj = new TinvOpeningBalanceItem();
                    obj = tinvOpeningBalanceItemRepository.findAllByObiStoreIdStoreIdAndOpeningBalanceUnitIdUnitIdAndObiItemBatchCodeAndObiobIdIsNullAndIsActiveTrueAndIsDeletedFalse(openingBalanceItem.getObiStoreId().getStoreId(), openingBalanceItem.getOpeningBalanceUnitId().getUnitId(), openingBalanceItem.getObiItemBatchCode());
                    obj.setObiItemQuantity(obj.getObiItemQuantity() - openingBalanceItem.getRetunQty());
                    ob.setObiItemQuantity(ob.getObiItemQuantity() + openingBalanceItem.getRetunQty());
                    ob.setIsApproved(true);
                    obj.setIsApproved(true);
                    tinvOpeningBalanceItemRepository.save(obj);
                    tinvOpeningBalanceItemRepository.save(ob);

                } else {
                    TinvOpeningBalanceItem obj = new TinvOpeningBalanceItem();
                    obj = tinvOpeningBalanceItemRepository.findAllByObiStoreIdStoreIdAndOpeningBalanceUnitIdUnitIdAndObiItemBatchCodeAndObiobIdIsNullAndIsActiveTrueAndIsDeletedFalse(openingBalanceItem.getObiStoreId().getStoreId(), openingBalanceItem.getOpeningBalanceUnitId().getUnitId(), openingBalanceItem.getObiItemBatchCode());
                    obj.setObiItemQuantity(obj.getObiItemQuantity() - openingBalanceItem.getRetunQty());
                    TinvOpeningBalanceItem newItemForStore = new TinvOpeningBalanceItem();
                    if (openingBalanceItem.getIssueStoreId() != null)
                        newItemForStore.setObiStoreId(openingBalanceItem.getIssueStoreId());
                    if (openingBalanceItem.getRetunQty() != 0)
                        newItemForStore.setObiItemQuantity(openingBalanceItem.getRetunQty());
                    if (openingBalanceItem.getObiItemAmount() >= 0)
                        newItemForStore.setObiItemAmount(openingBalanceItem.getObiItemAmount());
                    if (openingBalanceItem.getObiItemBatchCode() != null)
                        newItemForStore.setObiItemBatchCode(openingBalanceItem.getObiItemBatchCode());
                    if (openingBalanceItem.getObiItemCode() != null)
                        newItemForStore.setObiItemCode(openingBalanceItem.getObiItemCode());
                    if (openingBalanceItem.getObiItemCategoryId() != null)
                        newItemForStore.setObiItemCategoryId(openingBalanceItem.getObiItemCategoryId());
                    if (openingBalanceItem.getObiItemConversionFactor() != 0)
                        newItemForStore.setObiItemConversionFactor(openingBalanceItem.getObiItemConversionFactor());
                    if (openingBalanceItem.getObiItemCost() >= 0)
                        newItemForStore.setObiItemCost(openingBalanceItem.getObiItemCost());
                    if (openingBalanceItem.getObiItemExpiryDate() != null)
                        newItemForStore.setObiItemExpiryDate(openingBalanceItem.getObiItemExpiryDate());
                    if (openingBalanceItem.getObiItemDespensingIdtId() != null)
                        newItemForStore.setObiItemDespensingIdtId(openingBalanceItem.getObiItemDespensingIdtId());
                    if (openingBalanceItem.getObiItemDiscountOnSaleAmount() >= 0)
                        newItemForStore.setObiItemDiscountOnSaleAmount(openingBalanceItem.getObiItemDiscountOnSaleAmount());
                    if (openingBalanceItem.getObiItemDiscountOnSalePercentage() >= 0)
                        newItemForStore.setObiItemDiscountOnSalePercentage(openingBalanceItem.getObiItemDiscountOnSalePercentage());
                    if (openingBalanceItem.getObiItemId() != null)
                        newItemForStore.setObiItemId(openingBalanceItem.getObiItemId());
                    if (openingBalanceItem.getObiItemMrp() >= 0)
                        newItemForStore.setObiItemMrp(openingBalanceItem.getObiItemMrp());
                    if (openingBalanceItem.getObiItemPurchaseIdtId() != null)
                        newItemForStore.setObiItemPurchaseIdtId(openingBalanceItem.getObiItemPurchaseIdtId());
                    if (openingBalanceItem.getObiItemName() != null)
                        newItemForStore.setObiItemName(openingBalanceItem.getObiItemName());
                    if (openingBalanceItem.getObiItemNetAmount() >= 0)
                        newItemForStore.setObiItemNetAmount(openingBalanceItem.getObiItemNetAmount());
                    if (openingBalanceItem.getObiItemTaxAmount() >= 0)
                        newItemForStore.setObiItemTaxAmount(openingBalanceItem.getObiItemTaxAmount());
                    if (openingBalanceItem.getObiItemTaxId() != null)
                        newItemForStore.setObiItemTaxId(openingBalanceItem.getObiItemTaxId());
                    if (openingBalanceItem.getChangeUnitId() != null)
                        newItemForStore.setOpeningBalanceUnitId(openingBalanceItem.getIssueStoreId().getStoreUnitId());
                    newItemForStore.setIsApproved(false);
                    tinvOpeningBalanceItemRepository.save(newItemForStore);
                    tinvOpeningBalanceItemRepository.save(ob);
                }

            }
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } catch (Exception ex) {
            respMap.put("fail", "0");
            respMap.put("msg", "Operation Failed");
            return respMap;
        }

    }

    @RequestMapping("returnNursingItem")
    public Map<String, String> returnNursingItem(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvOpeningBalanceItem> searchOprningBalanceItemList) {
        TenantContext.setCurrentTenant(tenantName);
        for (TinvOpeningBalanceItem searchOprningBalanceItem : searchOprningBalanceItemList) {
            TinvOpeningBalanceItem tinvOpeningBalanceItem = tinvOpeningBalanceItemRepository.getById(searchOprningBalanceItem.getObiId());
            if (tinvOpeningBalanceItem != null) {
                tinvOpeningBalanceItem.setObiItemQuantity(tinvOpeningBalanceItem.getObiItemQuantity() + searchOprningBalanceItem.getRetunQty());
                tinvOpeningBalanceItemRepository.save(tinvOpeningBalanceItem);
            }
        }
        respMap.put("msg", "Added Successfully");
        respMap.put("success", "1");
        return respMap;
    }

    @GetMapping
    @RequestMapping("listSortByExpDate")
    public Iterable<TinvOpeningBalanceItem> listSortByExpDate(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "obiItemExpiryDate") String col, @RequestParam(value = "unitId", required = false) long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvOpeningBalanceItemRepository.findAllByOpeningBalanceUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @GetMapping
    @RequestMapping("getSetRanges")
    public List<ItemRanges> getSetRanges(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "select p from ItemRanges p ";
        return entityManager.createQuery(query, ItemRanges.class).getResultList();
    }

    @Transactional
    @RequestMapping("createItemRanges")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody ItemRanges itemRanges) {
        TenantContext.setCurrentTenant(tenantName);
        if (itemRanges.getItemMin() != 0) {
            if (itemRanges.getMmcId() == 0) {
                entityManager.persist(itemRanges);
            } else {
                entityManager.merge(itemRanges);
            }
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @Transactional
    @RequestMapping("deletAllRanges/{id}")
    public Map<String, String> deletAllRanges(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        if (id != null) {
            String query = "delete from ItemRanges";
            entityManager.createQuery(query).executeUpdate();
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }

    }

    @RequestMapping("getListofGrnByItemId")
    public List<TinvOpeningBalanceItem> getListofGrnByItemId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "itemId") Long itemId, @RequestParam(value = "unitId", required = false, defaultValue = " ") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "select p from TinvOpeningBalanceItem p where p.obiPgrnId.pgrnId is not null and p.obiItemId.itemId=" + itemId + " and p.openingBalanceUnitId.unitId=" + unitId;
        return entityManager.createQuery(query, TinvOpeningBalanceItem.class).getResultList();
    }

    @RequestMapping("getItemListBYStoreId/{storeId}")
    public List<TinvOpeningBalanceItem> getListofGrnByItemId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("storeId") Long storeId) {
        TenantContext.setCurrentTenant(tenantName);
        if (storeId != null) {
            return tinvOpeningBalanceItemRepository.findAllByObiStoreIdStoreIdAndIsDeletedFalse(storeId);
        }
        return null;
    }

    @RequestMapping("getItemAvailabityBy/{storeId}")
    public List<TinvOpeningBalanceItem> getItemAvailabityBy(@RequestHeader("X-tenantId") String tenantName, @PathVariable Long storeId, @RequestBody String ItemList) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("---object------");
        List<Map<String, TinvOpeningBalanceItem>> list = new ArrayList<Map<String, TinvOpeningBalanceItem>>();
        JSONArray array = new JSONArray(ItemList);
        Map<Long, TinvOpeningBalanceItem> mapObj = new HashMap<Long, TinvOpeningBalanceItem>();
        TinvOpeningBalanceItem invOpeningBalanceItem = new TinvOpeningBalanceItem();
        String itemIdList = Long.toString(array.getJSONObject(0).getLong("itemId"));
        for (int i = 1; i < array.length(); i++) {
            itemIdList += "," + array.getJSONObject(i).getLong("itemId");
        }
        List<TinvOpeningBalanceItem> TmpObjList = entityManager.createQuery("SELECT tobi FROM TinvOpeningBalanceItem tobi WHERE tobi.isActive = 1 AND tobi.isApproved = 1 AND tobi.obiStoreId.storeId =" + storeId + " AND tobi.obiItemId.itemId IN (" + itemIdList + ")").getResultList();
        for (TinvOpeningBalanceItem tmpObjList : TmpObjList) {
            Long key = tmpObjList.getObiItemId().getItemId();
            if ((mapObj != null) && (mapObj.containsKey(key))) {
                if (mapObj.containsKey(key)) {
                    TinvOpeningBalanceItem mapValueByKey = mapObj.get(key);
                    tmpObjList.setObiItemQuantity(mapValueByKey.getObiItemQuantity() + tmpObjList.getObiItemQuantity());
                    mapObj.put(key, tmpObjList);
                }
            } else {
                mapObj.put(key, tmpObjList);
            }
        }
        for (int i = 0; i < array.length(); i++) {
            if (!mapObj.containsKey(array.getJSONObject(i).getLong("itemId"))) {
                TinvOpeningBalanceItem openingBalanceItem = new TinvOpeningBalanceItem();
                InvItem invItem = new InvItem();
                invItem.setItemQuantity("0");
                invItem.setItemId(array.getJSONObject(i).getLong("itemId"));
                openingBalanceItem.setObiItemId(invItem);
                mapObj.put(array.getJSONObject(i).getLong("itemId"), openingBalanceItem);
            }
        }
        List<TinvOpeningBalanceItem> newTinvOpeningBalanceItem = new ArrayList<TinvOpeningBalanceItem>(mapObj.values());
        return newTinvOpeningBalanceItem;
    }

    @RequestMapping("stockConsumptionitemByStoreId")
    public List<TinvOpeningBalanceItem> stockConsumptionitemByStoreId(@RequestHeader("X-tenantId") String tenantName,
                                                                      @RequestParam(value = "searchData", required = false, defaultValue = " ") String searchData,
                                                                      @RequestParam(value = "storeId", required = false, defaultValue = " ") long storeId,
                                                                      @RequestParam(value = "unitId", required = false, defaultValue = " ") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        List<TinvOpeningBalanceItem> tinvOpeningBalanceItemlist = tinvOpeningBalanceItemRepository
                .findByItemByForStockConsumptionByStoreId(searchData, storeId, unitId, PageRequest.of(0, 50));
        return tinvOpeningBalanceItemlist;
    }

}




            
