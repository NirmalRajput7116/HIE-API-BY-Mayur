package com.cellbeans.hspa.mstsection;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mst_section")
public class MstSectionController {
    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    MstSectionRepository mstSectionRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstSection mstSection) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstSection.getSectionName() != null) {
            mstSectionRepository.save(mstSection);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{sectionId}")
    public MstSection read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("sectionId") Long sectionId) {
        TenantContext.setCurrentTenant(tenantName);
        MstSection mstSection = mstSectionRepository.getById(sectionId);
        return mstSection;
    }

    @RequestMapping("update")
    public MstSection update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstSection mstSection) {
        TenantContext.setCurrentTenant(tenantName);
        return mstSectionRepository.save(mstSection);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstSection> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "500") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "ASC") String sort, @RequestParam(value = "col", required = false, defaultValue = "sectionId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstSectionRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return mstSectionRepository.findBySectionNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @PutMapping("delete/{sectionId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("sectionId") Long sectionId) {
        TenantContext.setCurrentTenant(tenantName);
        MstSection mstSection = mstSectionRepository.getById(sectionId);
        if (mstSection != null) {
            mstSection.setIsDeleted(true);
            mstSectionRepository.save(mstSection);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
