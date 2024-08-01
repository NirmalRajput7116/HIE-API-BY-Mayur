package com.cellbeans.hspa.tbillplanuncoveredservice;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tbill_plan_uncovered_service")
public class TbillPlanUncoveredServiceController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TbillPlanUncoveredServiceRepository tbillPlanUncoveredServiceRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillPlanUncoveredService tbillPlanUncoveredService) {
        TenantContext.setCurrentTenant(tenantName);
        if (tbillPlanUncoveredService.getPusPlanId().getPlanId() > 0) {
            tbillPlanUncoveredServiceRepository.save(tbillPlanUncoveredService);
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
        List<TbillPlanUncoveredService> records;
        records = tbillPlanUncoveredServiceRepository.findByPusServiceIdServiceNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{pusId}")
    public TbillPlanUncoveredService read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pusId") Long pusId) {
        TenantContext.setCurrentTenant(tenantName);
        TbillPlanUncoveredService tbillPlanUncoveredService = tbillPlanUncoveredServiceRepository.getById(pusId);
        return tbillPlanUncoveredService;
    }

    @RequestMapping("update")
    public TbillPlanUncoveredService update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillPlanUncoveredService tbillPlanUncoveredService) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillPlanUncoveredServiceRepository.save(tbillPlanUncoveredService);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TbillPlanUncoveredService> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "pusId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tbillPlanUncoveredServiceRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return null;
//            return tbillPlanUncoveredServiceRepository.findByPusServiceIdServiceNameOrPusPlanIdPlanIdContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

    @PutMapping("delete/{pusId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pusId") Long pusId) {
        TenantContext.setCurrentTenant(tenantName);
        TbillPlanUncoveredService tbillPlanUncoveredService = tbillPlanUncoveredServiceRepository.getById(pusId);
        if (tbillPlanUncoveredService != null) {
            tbillPlanUncoveredService.setIsDeleted(true);
            tbillPlanUncoveredServiceRepository.save(tbillPlanUncoveredService);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
