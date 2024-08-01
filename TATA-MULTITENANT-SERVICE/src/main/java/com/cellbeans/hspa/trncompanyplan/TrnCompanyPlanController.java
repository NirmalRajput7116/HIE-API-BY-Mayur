package com.cellbeans.hspa.trncompanyplan;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/trn_company_plan")
public class TrnCompanyPlanController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnCompanyPlanRepository trnCompanyPlanRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnCompanyPlan trnCompanyPlan) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnCompanyPlan.getCtCompanyId().getCompanyId() > 0) {
            trnCompanyPlanRepository.save(trnCompanyPlan);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }
//        @RequestMapping("/autocomplete/{key}")
//	public  Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
//		Map<String, Object> automap  = new HashMap<String, Object>();
//		List<TrnCompanyPlan> records;
//		records = trnCompanyPlanRepository.findByPlanNameContains(key);
//		automap.put("record", records);
//		return automap;
//	}

    @RequestMapping("byid/{cpId}")
    public TrnCompanyPlan read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("cpId") Long cpId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnCompanyPlan trnCompanyPlan = trnCompanyPlanRepository.getById(cpId);
        return trnCompanyPlan;
    }

    @RequestMapping("update")
    public TrnCompanyPlan update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnCompanyPlan trnCompanyPlan) {
        TenantContext.setCurrentTenant(tenantName);
        return trnCompanyPlanRepository.save(trnCompanyPlan);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TrnCompanyPlan> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "cpId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return trnCompanyPlanRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return null;
//            return trnCompanyPlanRepository.findByCpPlanIdPlanNameContainsOrCpCompanyIdCompanyIdAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

    @PutMapping("delete/{cpId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("cpId") Long cpId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnCompanyPlan trnCompanyPlan = trnCompanyPlanRepository.getById(cpId);
        if (trnCompanyPlan != null) {
            trnCompanyPlan.setIsDeleted(true);
            trnCompanyPlanRepository.save(trnCompanyPlan);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
