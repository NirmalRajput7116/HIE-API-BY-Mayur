package com.cellbeans.hspa.mbillplan;

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
@RequestMapping("/mbill_plan")
public class MbillPlanController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MbillPlanRepository mbillPlanRepository;

    @Autowired
    private MbillPlanService mbillPlanService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MbillPlan mbillPlan) {
        TenantContext.setCurrentTenant(tenantName);
        if (mbillPlan.getPlanName() != null) {
            mbillPlanRepository.save(mbillPlan);
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
        List<MbillPlan> records;
        records = mbillPlanRepository.findByPlanNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{planId}")
    public MbillPlan read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("planId") Long planId) {
        TenantContext.setCurrentTenant(tenantName);
        MbillPlan mbillPlan = mbillPlanRepository.getById(planId);
        return mbillPlan;
    }

    @RequestMapping("update")
    public MbillPlan update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MbillPlan mbillPlan) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillPlanRepository.save(mbillPlan);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MbillPlan> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "planId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mbillPlanRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mbillPlanRepository.findByPlanNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{planId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("planId") Long planId) {
        TenantContext.setCurrentTenant(tenantName);
        MbillPlan mbillPlan = mbillPlanRepository.getById(planId);
        if (mbillPlan != null) {
            mbillPlan.setIsDeleted(true);
            mbillPlanRepository.save(mbillPlan);
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
        List<Tuple> items = mbillPlanService.getMbillPlanForDropdown(page, size, globalFilter);
        return items;
    }

}
            
