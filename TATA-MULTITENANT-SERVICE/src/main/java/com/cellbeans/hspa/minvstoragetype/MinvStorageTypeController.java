package com.cellbeans.hspa.minvstoragetype;

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
@RequestMapping("/minv_storage_type")
public class MinvStorageTypeController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MinvStorageTypeRepository minvStorageTypeRepository;

    @Autowired
    private MinvStorageTypeService minvStorageTypeService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MinvStorageType minvStorageType) {
        TenantContext.setCurrentTenant(tenantName);
        if (minvStorageType.getStName() != null) {
            minvStorageTypeRepository.save(minvStorageType);
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
        List<MinvStorageType> records;
        records = minvStorageTypeRepository.findByStNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{stId}")
    public MinvStorageType read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("stId") Long stId) {
        TenantContext.setCurrentTenant(tenantName);
        MinvStorageType minvStorageType = minvStorageTypeRepository.getById(stId);
        return minvStorageType;
    }

    @RequestMapping("update")
    public MinvStorageType update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MinvStorageType minvStorageType) {
        TenantContext.setCurrentTenant(tenantName);
        return minvStorageTypeRepository.save(minvStorageType);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MinvStorageType> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "stId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return minvStorageTypeRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return minvStorageTypeRepository.findByStNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{stId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("stId") Long stId) {
        TenantContext.setCurrentTenant(tenantName);
        MinvStorageType minvStorageType = minvStorageTypeRepository.getById(stId);
        if (minvStorageType != null) {
            minvStorageType.setIsDeleted(true);
            minvStorageTypeRepository.save(minvStorageType);
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
        List<Tuple> items = minvStorageTypeService.getMinvStorageTypeForDropdown(page, size, globalFilter);
        return items;
    }

}
            
