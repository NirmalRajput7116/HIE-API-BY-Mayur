package com.cellbeans.hspa.trnundermaintenancebed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import com.cellbeans.hspa.TenantContext;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/trn_undermaintenance_bed")
public class TrnUndermaintenanceBedController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    TrnUndermaintenanceBedRepository trnUndermaintenanceBedRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnUndermaintenanceBed trnUndermaintenanceBed) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnUndermaintenanceBed.getUbBedId() != null) {
            trnUndermaintenanceBedRepository.save(trnUndermaintenanceBed);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{ubId}")
    public TrnUndermaintenanceBed read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ubId") Long ubId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnUndermaintenanceBed trnUndermaintenanceBed = trnUndermaintenanceBedRepository.getById(ubId);
        return trnUndermaintenanceBed;
    }

    @RequestMapping("update")
    public TrnUndermaintenanceBed update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnUndermaintenanceBed trnUndermaintenanceBed) {
        TenantContext.setCurrentTenant(tenantName);
        return trnUndermaintenanceBedRepository.save(trnUndermaintenanceBed);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TrnUndermaintenanceBed> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "500") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "ASC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ubId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return trnUndermaintenanceBedRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

    }

    @PutMapping("delete/{ubId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ubId") Long ubId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnUndermaintenanceBed trnUndermaintenanceBed = trnUndermaintenanceBedRepository.getById(ubId);
        if (trnUndermaintenanceBed != null) {
            trnUndermaintenanceBed.setDeleted(true);
            trnUndermaintenanceBedRepository.save(trnUndermaintenanceBed);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
