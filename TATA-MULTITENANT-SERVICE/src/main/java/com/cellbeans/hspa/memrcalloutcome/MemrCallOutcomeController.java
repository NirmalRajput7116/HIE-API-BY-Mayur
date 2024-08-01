package com.cellbeans.hspa.memrcalloutcome;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/memr_calloutcome")
public class MemrCallOutcomeController {
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MemrCallOutcomeRepository memrCallOutcomeRepository;

    @Autowired
    MemrCallOutcomeService memrCallOutcomeService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrCallOutcome memrCallOutcome) {
        TenantContext.setCurrentTenant(tenantName);
        if (memrCallOutcome.getCallOutcomeName() != null) {
            memrCallOutcome.setCallOutcomeName(memrCallOutcome.getCallOutcomeName().trim());
            MemrCallOutcome memrCallOutcomeObject = memrCallOutcomeRepository.findByCallOutcomeNameEqualsAndIsActiveTrueAndIsDeletedFalse(memrCallOutcome.getCallOutcomeName());
            if (memrCallOutcomeObject == null) {
                memrCallOutcomeRepository.save(memrCallOutcome);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "2");
                respMap.put("msg", "Already Added");
                return respMap;
            }
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
        List<MemrCallOutcome> records;
        records = memrCallOutcomeRepository.findByCallOutcomeNameContainsAndIsActiveTrueAndIsDeletedFalse(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{bloodgroupId}")
    public MemrCallOutcome read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bloodgroupId") Long bloodgroupId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrCallOutcome memrCallOutcome = memrCallOutcomeRepository.getById(bloodgroupId);
        return memrCallOutcome;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrCallOutcome memrCallOutcome) {
        TenantContext.setCurrentTenant(tenantName);
        memrCallOutcome.setCallOutcomeName(memrCallOutcome.getCallOutcomeName().trim());
        MemrCallOutcome memrCallOutcomeObject = memrCallOutcomeRepository.findByCallOutcomeNameEqualsAndIsActiveTrueAndIsDeletedFalse(memrCallOutcome.getCallOutcomeName());
        if (memrCallOutcomeObject == null) {
            memrCallOutcomeRepository.save(memrCallOutcome);
            respMap.put("success", "1");
            respMap.put("msg", "updated Successfully");
            return respMap;
        } else if (memrCallOutcomeObject.getCallOutcomeId() == memrCallOutcome.getCallOutcomeId()) {
            memrCallOutcomeRepository.save(memrCallOutcome);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else {
            respMap.put("success", "2");
            respMap.put("msg", "Already Added");
            return respMap;

        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MemrCallOutcome> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "callOutcomeId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return memrCallOutcomeRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByCallOutcomeName(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return memrCallOutcomeRepository.findByCallOutcomeNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByCallOutcomeName(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @PutMapping("delete/{bloodgroupId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bloodgroupId") Long bloodgroupId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrCallOutcome memrCallOutcome = memrCallOutcomeRepository.getById(bloodgroupId);
        if (memrCallOutcome != null) {
            memrCallOutcome.setIsDeleted(true);
            memrCallOutcomeRepository.save(memrCallOutcome);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }
}
