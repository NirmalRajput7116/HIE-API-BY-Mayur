package com.cellbeans.hspa.mstset;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_set")
public class MstSetController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    MstSetRepository mstSetRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstSet mstSet) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstSet.getSetName() != null) {
            mstSetRepository.save(mstSet);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{setId}")
    public MstSet read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("setId") Long setId) {
        TenantContext.setCurrentTenant(tenantName);
        MstSet mstSet = mstSetRepository.getById(setId);
        return mstSet;
    }

    @RequestMapping("update")
    public MstSet update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstSet mstSet) {
        TenantContext.setCurrentTenant(tenantName);
        return mstSetRepository.save(mstSet);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstSet> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "500") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "ASC") String sort, @RequestParam(value = "col", required = false, defaultValue = "setId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstSetRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstSetRepository.findBySetNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }
    }

    @PutMapping("delete/{setId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("setId") Long setId) {
        TenantContext.setCurrentTenant(tenantName);
        MstSet mstSet = mstSetRepository.getById(setId);
        if (mstSet != null) {
            mstSet.setIsDeleted(true);
            mstSetRepository.save(mstSet);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("getsetlistbytabid/{tabId}")
    public List<MstSet> getSetListByTabid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tabId") Long tabId) {
        TenantContext.setCurrentTenant(tenantName);
        List<MstSet> mstSetList = mstSetRepository.findBySetTabIdTabIdEqualsAndIsActiveTrueAndIsDeletedFalse(tabId);
        return mstSetList;
    }
}
