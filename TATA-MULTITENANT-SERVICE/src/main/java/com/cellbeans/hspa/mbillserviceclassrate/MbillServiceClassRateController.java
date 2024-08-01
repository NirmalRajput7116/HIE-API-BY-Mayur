package com.cellbeans.hspa.mbillserviceclassrate;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mbill_service_class_rate")
public class MbillServiceClassRateController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MbillServiceClassRateRepository mbillServiceClassRateRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<MbillServiceClassRate> mbillServiceClassRate) {
        TenantContext.setCurrentTenant(tenantName);
        if (mbillServiceClassRate.get(0).getScrClassId() != null) {
            mbillServiceClassRateRepository.saveAll(mbillServiceClassRate);
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
        List<MbillServiceClassRate> records;
        records = mbillServiceClassRateRepository.findByScrClassIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{scrId}")
    public MbillServiceClassRate read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("scrId") Long scrId) {
        TenantContext.setCurrentTenant(tenantName);
        MbillServiceClassRate mbillServiceClassRate = mbillServiceClassRateRepository.getById(scrId);
        return mbillServiceClassRate;
    }

    @RequestMapping("byidServiceandclass")
    public Map<String, String> readBySerivceAndClass(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "scrServiceId") String scrServiceId, @RequestParam(value = "scrClassId") String scrClassId) {
        TenantContext.setCurrentTenant(tenantName);
        MbillServiceClassRate mbillServiceClassRate = mbillServiceClassRateRepository.findTopByScrMbillServiceIdServiceIdEqualsAndScrClassIdClassIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long.valueOf(scrServiceId), Long.valueOf(scrClassId));
        if (mbillServiceClassRate == null) {
            respMap.put("scrGross", "0");
            respMap.put("msg", "Service's Class-wise Rate not Found !");
        } else {
            respMap.put("scrGross", mbillServiceClassRate.getScrGross());
            respMap.put("msg", "Class-wies Gross Rate!");
        }
        return respMap;
    }

    @GetMapping("byidService")
    public List<MbillServiceClassRate> byServiceId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "serviceId", defaultValue = "0") Long serviceId) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillServiceClassRateRepository.findByScrMbillServiceIdServiceIdAndIsActiveTrueAndIsDeletedFalse(serviceId);
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<MbillServiceClassRate> mbillServiceClassRates) {
        TenantContext.setCurrentTenant(tenantName);
        try {
            mbillServiceClassRateRepository.saveAll(mbillServiceClassRates);
        } catch (Exception e) {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
        respMap.put("success", "1");
        respMap.put("msg", "Added Successfully");
        return respMap;
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MbillServiceClassRate> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "scrId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mbillServiceClassRateRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mbillServiceClassRateRepository.findByScrClassIdClassNameContainsOrScrMbillServiceIdServiceIdEqualsAndIsActiveTrueAndIsDeletedFalse(qString, Long.valueOf(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{scrId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("scrId") Long scrId) {
        TenantContext.setCurrentTenant(tenantName);
        MbillServiceClassRate mbillServiceClassRate = mbillServiceClassRateRepository.getById(scrId);
        if (mbillServiceClassRate != null) {
            mbillServiceClassRate.setIsDeleted(true);
            mbillServiceClassRateRepository.save(mbillServiceClassRate);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
