package com.cellbeans.hspa.msttab;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_tab")
public class MstTabController {
    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    MstTabRepository mstTabRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstTab mstTab) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstTab.getTabName() != null) {
            mstTabRepository.save(mstTab);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{tabId}")
    public MstTab read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tabId") Long tabId) {
        TenantContext.setCurrentTenant(tenantName);
        MstTab mstTab = mstTabRepository.getById(tabId);
        return mstTab;
    }

    @RequestMapping("update")
    public MstTab update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstTab mstTab) {
        TenantContext.setCurrentTenant(tenantName);
        return mstTabRepository.save(mstTab);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstTab> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "500") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "ASC") String sort, @RequestParam(value = "col", required = false, defaultValue = "tabId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstTabRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return mstTabRepository.findByTabNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @PutMapping("delete/{tabId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tabId") Long tabId) {
        TenantContext.setCurrentTenant(tenantName);
        MstTab mstTab = mstTabRepository.getById(tabId);
        if (mstTab != null) {
            mstTab.setIsDeleted(true);
            mstTabRepository.save(mstTab);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("gettabbysectionid/{sectionId}")
    public List<MstTab> getSectionbyTabid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("sectionId") Long sectionId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstTabRepository.findByTabSectionIdSectionIdEqualsAndIsActiveTrueAndIsDeletedFalse(sectionId);
    }

}
