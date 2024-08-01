package com.cellbeans.hspa.trncompanydrug;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/trn_company_drug")
public class TrnCompanyDrugController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnCompanyDrugRepository trnCompanyDrugRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnCompanyDrug trnCompanyDrug) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnCompanyDrug.getCdCompanyId().getCompanyId() > 0) {
            trnCompanyDrugRepository.save(trnCompanyDrug);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }
    /*
     * @RequestMapping("/autocomplete/{key}") public Map<String, Object>
     * listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) { Map<String, Object> automap =
     * new HashMap<String, Object>(); List<TrnCompanyDrug> records; records =
     * trnCompanyDrugRepository.findByPlanNameContains(key);
     * automap.put("record", records); return automap; }
     */

    @RequestMapping("byid/{cdId}")
    public TrnCompanyDrug read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("cdId") Long cdId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnCompanyDrug trnCompanyDrug = trnCompanyDrugRepository.getById(cdId);
        return trnCompanyDrug;
    }

    @RequestMapping("update")
    public TrnCompanyDrug update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnCompanyDrug trnCompanyDrug) {
        TenantContext.setCurrentTenant(tenantName);
        return trnCompanyDrugRepository.save(trnCompanyDrug);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TrnCompanyDrug> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "cdId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return trnCompanyDrugRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return null;
//            return trnCompanyDrugRepository.findByCdDrugIdDrugNameContainsOrCdCompanyIdCompanyIdAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

    @PutMapping("delete/{cdId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("cdId") Long cdId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnCompanyDrug trnCompanyDrug = trnCompanyDrugRepository.getById(cdId);
        if (trnCompanyDrug != null) {
            trnCompanyDrug.setIsDeleted(true);
            trnCompanyDrugRepository.save(trnCompanyDrug);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
