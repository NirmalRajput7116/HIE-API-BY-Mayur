package com.cellbeans.hspa.tinvscrapesaleitem;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_scrape_sale_item")
public class TinvScrapeSaleItemController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TinvScrapeSaleItemRepository tinvScrapeSaleItemRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvScrapeSaleItem> tinvScrapeSaleItem) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvScrapeSaleItem.size() != 0) {
            tinvScrapeSaleItemRepository.saveAll(tinvScrapeSaleItem);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{ssiId}")
    public TinvScrapeSaleItem read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ssiId") Long ssiId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvScrapeSaleItem tinvScrapeSaleItem = tinvScrapeSaleItemRepository.getById(ssiId);
        return tinvScrapeSaleItem;
    }

    @RequestMapping("update")
    public TinvScrapeSaleItem update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvScrapeSaleItem tinvScrapeSaleItem) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvScrapeSaleItemRepository.save(tinvScrapeSaleItem);
    }

    @PutMapping("delete/{ssiId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ssiId") Long ssiId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvScrapeSaleItem tinvScrapeSaleItem = tinvScrapeSaleItemRepository.getById(ssiId);
        if (tinvScrapeSaleItem != null) {
            tinvScrapeSaleItem.setIsDeleted(true);
            tinvScrapeSaleItemRepository.save(tinvScrapeSaleItem);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("scrapeItem/{id}")
    public List<TinvScrapeSaleItem> update(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") long id) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvScrapeSaleItemRepository.findBySsiSsIdSsIdAndIsActiveTrueAndIsDeletedFalse(id);
    }

}
            
