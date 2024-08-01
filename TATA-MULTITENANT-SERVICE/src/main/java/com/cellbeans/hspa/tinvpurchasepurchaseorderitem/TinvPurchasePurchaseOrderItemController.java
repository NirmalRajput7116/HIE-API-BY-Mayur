package com.cellbeans.hspa.tinvpurchasepurchaseorderitem;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_purchase_purchase_order_item")
public class TinvPurchasePurchaseOrderItemController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TinvPurchasePurchaseOrderItemRepository tinvPurchasePurchaseOrderItemRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvPurchasePurchaseOrderItem> tinvPurchasePurchaseOrderItem) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvPurchasePurchaseOrderItem.size() != 0) {
            tinvPurchasePurchaseOrderItemRepository.saveAll(tinvPurchasePurchaseOrderItem);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<TinvPurchasePurchaseOrderItem> records;
        records = tinvPurchasePurchaseOrderItemRepository.findByPpotItemIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{ppotId}")
    public TinvPurchasePurchaseOrderItem read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ppotId") Long ppotId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPurchasePurchaseOrderItem tinvPurchasePurchaseOrderItem = tinvPurchasePurchaseOrderItemRepository.getById(ppotId);
        return tinvPurchasePurchaseOrderItem;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvPurchasePurchaseOrderItem> tinvPurchasePurchaseOrderItem) {
        TenantContext.setCurrentTenant(tenantName);
        try {
            tinvPurchasePurchaseOrderItemRepository.saveAll(tinvPurchasePurchaseOrderItem);
            respMap.put("msg", "Added Successfully");
            return respMap;
        } catch (Exception ex) {
            respMap.put("msg", "Operation Failed");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TinvPurchasePurchaseOrderItem> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ppotId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tinvPurchasePurchaseOrderItemRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tinvPurchasePurchaseOrderItemRepository.findByPpotNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{ppotId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ppotId") Long ppotId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPurchasePurchaseOrderItem tinvPurchasePurchaseOrderItem = tinvPurchasePurchaseOrderItemRepository.getById(ppotId);
        if (tinvPurchasePurchaseOrderItem != null) {
            tinvPurchasePurchaseOrderItem.setIsDeleted(true);
            tinvPurchasePurchaseOrderItemRepository.save(tinvPurchasePurchaseOrderItem);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("itembypopid/{ppoId}")
    public List<TinvPurchasePurchaseOrderItem> getItemListById(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ppoId") Long ppoId) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvPurchasePurchaseOrderItemRepository.findAllByPpotPpoIdPpoIdAndIsActiveTrueAndIsDeletedFalse(ppoId);
    }

    @RequestMapping("rejectIteamsByPOId/{ppoId}")
    public Map<String, String> onRejectGRN(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ppoId") Long ppoId) {
        TenantContext.setCurrentTenant(tenantName);
        List<TinvPurchasePurchaseOrderItem> tinvOpeningBalanceItem = tinvPurchasePurchaseOrderItemRepository.findAllByPpotPpoIdPpoIdAndIsActiveTrueAndIsDeletedFalse(ppoId);
        for (int i = 0; i < tinvOpeningBalanceItem.size(); i++) {
            TinvPurchasePurchaseOrderItem currObj = tinvOpeningBalanceItem.get(i);
            currObj.setRejected(true);
            tinvPurchasePurchaseOrderItemRepository.save(currObj);
        }
        respMap.put("msg", "Operation Successful");
        respMap.put("success", "1");
        return respMap;
    }

}
            
