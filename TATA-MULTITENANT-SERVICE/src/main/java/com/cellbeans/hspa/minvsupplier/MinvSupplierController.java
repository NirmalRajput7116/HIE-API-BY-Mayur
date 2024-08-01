package com.cellbeans.hspa.minvsupplier;

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
@RequestMapping("/minv_supplier")
public class MinvSupplierController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MinvSupplierRepository minvSupplierRepository;

    @Autowired
    private MinvSupplierService minvSupplierService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MinvSupplier minvSupplier) {
        TenantContext.setCurrentTenant(tenantName);
        if (minvSupplier.getSupplierName() != null) {
            minvSupplierRepository.save(minvSupplier);
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
        List<MinvSupplier> records;
        records = minvSupplierRepository.findBySupplierNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{supplierId}")
    public MinvSupplier read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("supplierId") Long supplierId) {
        TenantContext.setCurrentTenant(tenantName);
        MinvSupplier minvSupplier = minvSupplierRepository.getById(supplierId);
        return minvSupplier;
    }

    @RequestMapping("update")
    public MinvSupplier update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MinvSupplier minvSupplier) {
        TenantContext.setCurrentTenant(tenantName);
        return minvSupplierRepository.save(minvSupplier);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MinvSupplier> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "supplierId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return minvSupplierRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return minvSupplierRepository.findBySupplierNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{supplierId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("supplierId") Long supplierId) {
        TenantContext.setCurrentTenant(tenantName);
        MinvSupplier minvSupplier = minvSupplierRepository.getById(supplierId);
        if (minvSupplier != null) {
            minvSupplier.setIsDeleted(true);
            minvSupplierRepository.save(minvSupplier);
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
        List<Tuple> items = minvSupplierService.getMinvSupplierForDropdown(page, size, globalFilter);
        return items;
    }

}
            
