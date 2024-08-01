package com.cellbeans.hspa.mbilltariffgroup;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mbill_tariff_group")
public class MbillTariffGroupController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MbillTariffGroupRepository mbillTariffGroupRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MbillTariffGroup mbillTariffGroup) {
        TenantContext.setCurrentTenant(tenantName);
        if (mbillTariffGroup.getTgGroupId().getGroupName() != null) {
            mbillTariffGroupRepository.save(mbillTariffGroup);
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
        List<MbillTariffGroup> records;
        records = mbillTariffGroupRepository.findByTgGroupIdGroupNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{tgId}")
    public MbillTariffGroup read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tgId") Long tgId) {
        TenantContext.setCurrentTenant(tenantName);
        MbillTariffGroup mbillTariffGroup = mbillTariffGroupRepository.getById(tgId);
        return mbillTariffGroup;
    }

    @RequestMapping("update")
    public MbillTariffGroup update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MbillTariffGroup mbillTariffGroup) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillTariffGroupRepository.save(mbillTariffGroup);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MbillTariffGroup> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "tgId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mbillTariffGroupRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mbillTariffGroupRepository.findByTgGroupIdGroupNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{tgId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tgId") Long tgId) {
        TenantContext.setCurrentTenant(tenantName);
        MbillTariffGroup mbillTariffGroup = mbillTariffGroupRepository.getById(tgId);
        if (mbillTariffGroup != null) {
            mbillTariffGroup.setIsDeleted(true);
            mbillTariffGroupRepository.save(mbillTariffGroup);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
