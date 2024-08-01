package com.cellbeans.hspa.memrautodailyclosevisit;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/memr_auto_daily_close_visit")
public class MemrAutoDailyCloseVisitController {
    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    MemrAutoDailyCloseVisitRepository memrAutoDailyCloseVisitRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrAutoDailyCloseVisit memrAutoDailyCloseVisit) {
        TenantContext.setCurrentTenant(tenantName);
        if (memrAutoDailyCloseVisit != null) {
            memrAutoDailyCloseVisitRepository.save(memrAutoDailyCloseVisit);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{adcvId}")
    public MemrAutoDailyCloseVisit read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("adcvId") Long adcvId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrAutoDailyCloseVisit memrAutoDailyCloseVisit = memrAutoDailyCloseVisitRepository.getById(adcvId);
        return memrAutoDailyCloseVisit;
    }

    @RequestMapping("update")
    public MemrAutoDailyCloseVisit update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrAutoDailyCloseVisit memrAutoDailyCloseVisit) {
        TenantContext.setCurrentTenant(tenantName);
        return memrAutoDailyCloseVisitRepository.save(memrAutoDailyCloseVisit);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MemrAutoDailyCloseVisit> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "500") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "adcvId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return memrAutoDailyCloseVisitRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return memrAutoDailyCloseVisitRepository.findByAdcvUnitIdUnitNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{acId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("acId") Long acId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrAutoDailyCloseVisit memrAutoDailyCloseVisit = memrAutoDailyCloseVisitRepository.getById(acId);
        if (memrAutoDailyCloseVisit != null) {
            memrAutoDailyCloseVisit.setDeleted(true);
            memrAutoDailyCloseVisitRepository.save(memrAutoDailyCloseVisit);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
