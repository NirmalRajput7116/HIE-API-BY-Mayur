package com.cellbeans.hspa.temrreferraloutsource;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/temr_referral_out_source")
public class TemrReferralOutSourceController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TemrReferralOutSourceRepository temrReferralOutSourceRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrReferralOutSource temrReferralOutSource) {
        TenantContext.setCurrentTenant(tenantName);
        if (temrReferralOutSource.getRosVisitId().getVisitId() > 0) {
            temrReferralOutSourceRepository.save(temrReferralOutSource);
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
     * new HashMap<String, Object>(); List<TemrReferralOutSource> records;
     * records = temrReferralOutSourceRepository.findByRetNameContains(key);
     * automap.put("record", records); return automap; }
     */

    @RequestMapping("byid/{rosId}")
    public TemrReferralOutSource read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("rosId") Long rosId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrReferralOutSource temrReferralOutSource = temrReferralOutSourceRepository.getById(rosId);
        return temrReferralOutSource;
    }

    @RequestMapping("update")
    public TemrReferralOutSource update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrReferralOutSource temrReferralOutSource) {
        TenantContext.setCurrentTenant(tenantName);
        return temrReferralOutSourceRepository.save(temrReferralOutSource);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TemrReferralOutSource> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "rosId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return temrReferralOutSourceRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return temrReferralOutSourceRepository.findByRosReIdReNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{rosId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("rosId") Long rosId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrReferralOutSource temrReferralOutSource = temrReferralOutSourceRepository.getById(rosId);
        if (temrReferralOutSource != null) {
            temrReferralOutSource.setIsDeleted(true);
            temrReferralOutSourceRepository.save(temrReferralOutSource);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
