package com.cellbeans.hspa.trnemergencybedhistory;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/trn_emergency_bed_history")
public class TrnEmergencyBedHistoryController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnEmergencyBedHistoryRepository trnemergencybedhistoryRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnEmergencyBedHistory trnemergencybedhistory) {
        TenantContext.setCurrentTenant(tenantName);
//        if (trnemergencybedhistory.getEbhVisitId() != null) {
        trnemergencybedhistoryRepository.save(trnemergencybedhistory);
        respMap.put("success", "1");
        respMap.put("msg", "Added Successfully");
        return respMap;
//        } else {
//            respMap.put("success", "0");
//            respMap.put("msg", "Failed To Add Null Field");
//            return respMap;
//        }
    }
//
//    @RequestMapping("/autocomplete/{key}")
//    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
//        Map<String, Object> automap = new HashMap<String, Object>();
//        List<TrnEmergencyBedAllocation> records;
//        records = trnemergencybedallocation.findByRouteNameContains(key);
//        automap.put("record", records);
//        return automap;
//    }

    @RequestMapping("byid/{ebhId}")
    public TrnEmergencyBedHistory read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ebhId") Long ebhId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnEmergencyBedHistory trnemergencybedhistory = trnemergencybedhistoryRepository.getById(ebhId);
        return trnemergencybedhistory;
    }

    @RequestMapping("update")
    public TrnEmergencyBedHistory update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnEmergencyBedHistory trnemergencybedhistory) {
        TenantContext.setCurrentTenant(tenantName);
        return trnemergencybedhistoryRepository.save(trnemergencybedhistory);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TrnEmergencyBedHistory> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ebhId") String col) {
        TenantContext.setCurrentTenant(tenantName);
//        if (qString == null || qString.equals("")) {
//            return trnemergencybedallocationRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//
//        } else {
//
//            return trnemergencybedallocationRepository.findByRouteNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//
//        }
        return trnemergencybedhistoryRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

    }

    @PutMapping("delete/{ebhId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ebhId") Long ebhId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnEmergencyBedHistory trnemergencybedhistory = trnemergencybedhistoryRepository.getById(ebhId);
        if (trnemergencybedhistory != null) {
            trnemergencybedhistory.setDeleted(true);
            trnemergencybedhistoryRepository.save(trnemergencybedhistory);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
