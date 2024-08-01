package com.cellbeans.hspa.tinvitemstore;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_item_store")
public class TinvItemStoreController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TinvItemStoreRepository tinvItemStoreRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvItemStore> tinvItemStore) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvItemStore != null) {
            tinvItemStoreRepository.saveAll(tinvItemStore);
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
        List<TinvItemStore> records;
        records = tinvItemStoreRepository.findByIsItemIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{isId}")
    public TinvItemStore read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("isId") Long isId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvItemStore tinvItemStore = tinvItemStoreRepository.getById(isId);
        return tinvItemStore;
    }

    @RequestMapping("update")
    public TinvItemStore update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvItemStore tinvItemStore) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvItemStoreRepository.save(tinvItemStore);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TinvItemStore> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "isId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tinvItemStoreRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tinvItemStoreRepository.findByIsNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{isId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("isId") Long isId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvItemStore tinvItemStore = tinvItemStoreRepository.getById(isId);
        if (tinvItemStore != null) {
            tinvItemStore.setIsDeleted(true);
            tinvItemStoreRepository.save(tinvItemStore);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
