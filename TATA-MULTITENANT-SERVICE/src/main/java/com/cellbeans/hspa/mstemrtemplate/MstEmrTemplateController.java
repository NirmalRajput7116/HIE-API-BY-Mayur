package com.cellbeans.hspa.mstemrtemplate;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mst_emr_template")
public class MstEmrTemplateController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstEmrTemplateRepository mstEmrTemplateRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstEmrTemplate mstEmrTemplate) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstEmrTemplate.getEtName() != null) {
            MstEmrTemplate mstEmrTemplateCreated = new MstEmrTemplate();
            mstEmrTemplateCreated = mstEmrTemplateRepository.save(mstEmrTemplate);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            respMap.put("id", String.valueOf(mstEmrTemplateCreated.getEtId()));
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{etId}")
    public MstEmrTemplate read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("etId") Long etId) {
        TenantContext.setCurrentTenant(tenantName);
        MstEmrTemplate mstEmrTemplate = mstEmrTemplateRepository.getById(etId);
        return mstEmrTemplate;
    }

    @RequestMapping("update")
    public MstEmrTemplate update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstEmrTemplate mstEmrTemplate) {
        TenantContext.setCurrentTenant(tenantName);
        return mstEmrTemplateRepository.save(mstEmrTemplate);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstEmrTemplate> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "etId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstEmrTemplateRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstEmrTemplateRepository.findAllByEtNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{unitId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        MstEmrTemplate mstEmrTemplate = mstEmrTemplateRepository.getById(unitId);
        if (mstEmrTemplate != null) {
            mstEmrTemplate.setDeleted(true);
            mstEmrTemplateRepository.save(mstEmrTemplate);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("getAllRecordByLoginStaff")
    public Iterable<MstEmrTemplate> getAllRecordByLoginStaff(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                             @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                                             @RequestParam(value = "qString", required = false) String qString,
                                                             @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                             @RequestParam(value = "col", required = false, defaultValue = "etId") String col,
                                                             @RequestParam(value = "staffId", required = false) long staffId) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstEmrTemplateRepository.findAllByEtStaffIdStaffIdEqualsAndIsActiveTrueAndIsDeletedFalse(staffId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstEmrTemplateRepository.findAllByEtNameContainsAndEtStaffIdStaffIdEqualsAndIsActiveTrueAndIsDeletedFalse(qString, staffId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }
}
            
