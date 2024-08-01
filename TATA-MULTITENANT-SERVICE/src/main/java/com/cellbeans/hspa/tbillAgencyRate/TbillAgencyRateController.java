package com.cellbeans.hspa.tbillAgencyRate;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tbill_agency_rate")
public class TbillAgencyRateController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    TbillAgencyRateRepository tbillAgencyRateRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillAgencyRate tbillAgencyRate) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("tbillAgencyRateAAAA :" + tbillAgencyRate);
        if (tbillAgencyRate != null) {
            tbillAgencyRateRepository.save(tbillAgencyRate);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TbillAgencyRate> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "atId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("hello list :" + qString);
        if (qString == null || qString.equals("")) {
            return tbillAgencyRateRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return tbillAgencyRateRepository.findByAtAgencyIdAgencyNameContainsOrAtMbillServiceIdServiceNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

    @RequestMapping("byid/{atId}")
    public TbillAgencyRate read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("atId") Long atId) {
        TenantContext.setCurrentTenant(tenantName);
        TbillAgencyRate mpathAgency = tbillAgencyRateRepository.getById(atId);
        return mpathAgency;
    }

    @PutMapping("delete/{atId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("atId") Long atId) {
        TenantContext.setCurrentTenant(tenantName);
        TbillAgencyRate tbillAgencyRate = tbillAgencyRateRepository.getById(atId);
        System.out.println("tbillAgencyRate :" + tbillAgencyRate);
        if (tbillAgencyRate != null) {
            tbillAgencyRate.setIsDeleted(true);
            tbillAgencyRateRepository.save(tbillAgencyRate);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("update")
    public TbillAgencyRate update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillAgencyRate mpathAgency) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillAgencyRateRepository.save(mpathAgency);
    }

}
            
