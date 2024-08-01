package com.cellbeans.hspa.tinvpurchasequotationitem;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_purchase_quotation_item")
public class TinvPurchaseQuotationItemController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TinvPurchaseQuotationItemRepository tinvPurchaseQuotationItemRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvPurchaseQuotationItem> tinvPurchaseQuotationItem) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvPurchaseQuotationItem.size() != 0) {
            tinvPurchaseQuotationItemRepository.saveAll(tinvPurchaseQuotationItem);
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
        List<TinvPurchaseQuotationItem> records;
        records = tinvPurchaseQuotationItemRepository.findByPqiPqIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{pqiId}")
    public TinvPurchaseQuotationItem read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pqiId") Long pqiId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPurchaseQuotationItem tinvPurchaseQuotationItem = tinvPurchaseQuotationItemRepository.getById(pqiId);
        return tinvPurchaseQuotationItem;
    }

    @RequestMapping("update")
    public TinvPurchaseQuotationItem update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvPurchaseQuotationItem tinvPurchaseQuotationItem) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvPurchaseQuotationItemRepository.save(tinvPurchaseQuotationItem);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TinvPurchaseQuotationItem> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "pqiId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tinvPurchaseQuotationItemRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tinvPurchaseQuotationItemRepository.findByPqiNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{pqiId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pqiId") Long pqiId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPurchaseQuotationItem tinvPurchaseQuotationItem = tinvPurchaseQuotationItemRepository.getById(pqiId);
        if (tinvPurchaseQuotationItem != null) {
            tinvPurchaseQuotationItem.setIsDeleted(true);
            tinvPurchaseQuotationItemRepository.save(tinvPurchaseQuotationItem);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("bypqid/{pqId}")
    public List<TinvPurchaseQuotationItem> getItemsById(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pqId") Long pqId) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvPurchaseQuotationItemRepository.findByPqiPqIdPqIdAndIsActiveTrueAndIsDeletedFalse(pqId);
    }

}
            
