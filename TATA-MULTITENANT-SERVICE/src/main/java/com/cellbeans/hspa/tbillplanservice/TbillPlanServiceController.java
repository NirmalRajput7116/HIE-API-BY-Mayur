package com.cellbeans.hspa.tbillplanservice;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tbill_plan_service")
public class TbillPlanServiceController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TbillPlanServiceRepository tbillPlanServiceRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillPlanService tbillPlanService) {
        TenantContext.setCurrentTenant(tenantName);
        if (tbillPlanService.getPsPlanId().getPlanId() > 0) {
            tbillPlanServiceRepository.save(tbillPlanService);
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
        List<TbillPlanService> records;
        records = tbillPlanServiceRepository.findByPsServiceIdServiceNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{psId}")
    public TbillPlanService read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("psId") Long psId) {
        TenantContext.setCurrentTenant(tenantName);
        TbillPlanService tbillPlanService = tbillPlanServiceRepository.getById(psId);
        return tbillPlanService;
    }

    @RequestMapping("update")
    public TbillPlanService update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillPlanService tbillPlanService) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillPlanServiceRepository.save(tbillPlanService);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TbillPlanService> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "psId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tbillPlanServiceRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return null;
//            return tbillPlanServiceRepository.findByPsServiceIdServiceNameContainsOrPsPlanIdPlanIdContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

    @PutMapping("delete/{psId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("psId") Long psId) {
        TenantContext.setCurrentTenant(tenantName);
        TbillPlanService tbillPlanService = tbillPlanServiceRepository.getById(psId);
        if (tbillPlanService != null) {
            tbillPlanService.setIsDeleted(true);
            tbillPlanServiceRepository.save(tbillPlanService);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
