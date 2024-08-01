package com.cellbeans.hspa.mbilltarrifgroup;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mbill_tarrif_group")
public class MbillTarrifGroupController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MbillTarrifGroupRepository mbillTarrifGroupRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MbillTarrifGroup mbillTarrifGroup) {
        TenantContext.setCurrentTenant(tenantName);
        if (mbillTarrifGroup.getTgGroupId().getGroupName() != null) {
            mbillTarrifGroupRepository.save(mbillTarrifGroup);
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
        List<MbillTarrifGroup> records;
        records = mbillTarrifGroupRepository.findByTgGroupIdGroupNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{tgId}")
    public MbillTarrifGroup read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tgId") Long tgId) {
        TenantContext.setCurrentTenant(tenantName);
        MbillTarrifGroup mbillTarrifGroup = mbillTarrifGroupRepository.getById(tgId);
        return mbillTarrifGroup;
    }

    @RequestMapping("update")
    public MbillTarrifGroup update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MbillTarrifGroup mbillTarrifGroup) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillTarrifGroupRepository.save(mbillTarrifGroup);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MbillTarrifGroup> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "tgId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mbillTarrifGroupRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mbillTarrifGroupRepository.findByTgGroupIdGroupNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{tgId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tgId") Long tgId) {
        TenantContext.setCurrentTenant(tenantName);
        MbillTarrifGroup mbillTarrifGroup = mbillTarrifGroupRepository.getById(tgId);
        if (mbillTarrifGroup != null) {
            mbillTarrifGroup.setIsDeleted(true);
            mbillTarrifGroupRepository.save(mbillTarrifGroup);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
