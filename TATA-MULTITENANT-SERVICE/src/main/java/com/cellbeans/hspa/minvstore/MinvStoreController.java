package com.cellbeans.hspa.minvstore;

import com.cellbeans.hspa.TenantContext;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/minv_store")
public class MinvStoreController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MinvStoreRepository minvStoreRepository;

    @Autowired
    private MinvStoreService minvStoreService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MinvStore minvStore) {
        TenantContext.setCurrentTenant(tenantName);
        if (minvStore.getStoreName() != null) {
            minvStoreRepository.save(minvStore);
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
        List<MinvStore> records;
        records = minvStoreRepository.findByStoreNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{storeId}")
    public MinvStore read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("storeId") Long storeId) {
        TenantContext.setCurrentTenant(tenantName);
        MinvStore minvStore = minvStoreRepository.getById(storeId);
        return minvStore;
    }

    @RequestMapping("update")
    public MinvStore update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MinvStore minvStore) {
        TenantContext.setCurrentTenant(tenantName);
        return minvStoreRepository.save(minvStore);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MinvStore> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "storeId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return minvStoreRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return minvStoreRepository.findByStoreNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{storeId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("storeId") Long storeId) {
        TenantContext.setCurrentTenant(tenantName);
        MinvStore minvStore = minvStoreRepository.getById(storeId);
        if (minvStore != null) {
            minvStore.setIsDeleted(true);
            minvStoreRepository.save(minvStore);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = minvStoreService.getMinvStoreForDropdown(page, size, globalFilter);
        return items;
    }

}
            
