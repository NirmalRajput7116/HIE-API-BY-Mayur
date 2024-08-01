package com.cellbeans.hspa.temrreferralinhouse;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/temr_referral_in_house")
public class TemrReferralInHouseController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TemrReferralInHouseRepository temrReferralInHouseRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrReferralInHouse temrReferralInHouse) {
        TenantContext.setCurrentTenant(tenantName);
        if (temrReferralInHouse.getRihVisitId().getVisitId() > 0) {
            temrReferralInHouseRepository.save(temrReferralInHouse);
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
     * new HashMap<String, Object>(); List<TemrReferralInHouse> records; records
     * = temrReferralInHouseRepository.findByDepartmentNameContains(key);
     * automap.put("record", records); return automap; }
     */

    @RequestMapping("byid/{rihId}")
    public TemrReferralInHouse read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("rihId") Long rihId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrReferralInHouse temrReferralInHouse = temrReferralInHouseRepository.getById(rihId);
        return temrReferralInHouse;
    }

    @RequestMapping("update")
    public TemrReferralInHouse update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrReferralInHouse temrReferralInHouse) {
        TenantContext.setCurrentTenant(tenantName);
        return temrReferralInHouseRepository.save(temrReferralInHouse);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TemrReferralInHouse> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "rihId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return temrReferralInHouseRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return temrReferralInHouseRepository.findByRihDepartmentIdDepartmentNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{rihId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("rihId") Long rihId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrReferralInHouse temrReferralInHouse = temrReferralInHouseRepository.getById(rihId);
        if (temrReferralInHouse != null) {
            temrReferralInHouse.setIsDeleted(true);
            temrReferralInHouseRepository.save(temrReferralInHouse);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
