package com.cellbeans.hspa.tbillplanuncovereddrug;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tbill_plan_uncovered_drug")
public class TbillPlanUncoveredDrugController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TbillPlanUncoveredDrugRepository tbillPlanUncoveredDrugRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillPlanUncoveredDrug tbillPlanUncoveredDrug) {
        TenantContext.setCurrentTenant(tenantName);
        if (tbillPlanUncoveredDrug.getPudPlanId().getPlanId() > 0) {
            tbillPlanUncoveredDrugRepository.save(tbillPlanUncoveredDrug);
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
        List<TbillPlanUncoveredDrug> records;
        records = tbillPlanUncoveredDrugRepository.findByPudDrugIdDrugNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{pudId}")
    public TbillPlanUncoveredDrug read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pudId") Long pudId) {
        TenantContext.setCurrentTenant(tenantName);
        TbillPlanUncoveredDrug tbillPlanUncoveredDrug = tbillPlanUncoveredDrugRepository.getById(pudId);
        return tbillPlanUncoveredDrug;
    }

    @RequestMapping("update")
    public TbillPlanUncoveredDrug update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillPlanUncoveredDrug tbillPlanUncoveredDrug) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillPlanUncoveredDrugRepository.save(tbillPlanUncoveredDrug);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TbillPlanUncoveredDrug> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "pudId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tbillPlanUncoveredDrugRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return null;
//            return tbillPlanUncoveredDrugRepository.findByPudDrugIdDrugNameContainsOrPudPlanIdPlanIdContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

    @PutMapping("delete/{pudId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pudId") Long pudId) {
        TenantContext.setCurrentTenant(tenantName);
        TbillPlanUncoveredDrug tbillPlanUncoveredDrug = tbillPlanUncoveredDrugRepository.getById(pudId);
        if (tbillPlanUncoveredDrug != null) {
            tbillPlanUncoveredDrug.setIsDeleted(true);
            tbillPlanUncoveredDrugRepository.save(tbillPlanUncoveredDrug);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
