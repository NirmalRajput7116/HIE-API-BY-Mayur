package com.cellbeans.hspa.mstdischargesummaryform;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mst_discharge_summary_form")
public class MstDischargeSummaryFormController {
    Map<String, String> respMap = new HashMap<String, String>();
    Map<String, Object> restMap = new HashMap<String, Object>();

    @Autowired
    MstDischargeSummaryFormRepository mstDischargeSummaryFormRepository;

    @RequestMapping("create")
    public Map<String, Object> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDischargeSummaryForm mstDischargeSummaryForm) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstDischargeSummaryForm.getDsfName() != null) {
            MstDischargeSummaryForm mstDischargeSummaryFormNew = mstDischargeSummaryFormRepository.save(mstDischargeSummaryForm);
            restMap.put("success", "1");
            restMap.put("dsfId", mstDischargeSummaryFormNew.getDsfId());
            restMap.put("msg", "Added Successfully");
            return restMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return restMap;
        }
    }

    @RequestMapping("byid/{dsfId}")
    public MstDischargeSummaryForm read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dsfId") Long dsfId) {
        TenantContext.setCurrentTenant(tenantName);
        MstDischargeSummaryForm mstDischargeSummaryForm = mstDischargeSummaryFormRepository.getById(dsfId);
        return mstDischargeSummaryForm;
    }

    @RequestMapping("update")
    public MstDischargeSummaryForm update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDischargeSummaryForm mstDischargeSummaryForm) {
        TenantContext.setCurrentTenant(tenantName);
        return mstDischargeSummaryFormRepository.save(mstDischargeSummaryForm);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstDischargeSummaryForm> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "dsfId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstDischargeSummaryFormRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstDischargeSummaryFormRepository.findByDsfNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }
    }

    @PutMapping("delete/{dsfId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dsfId") Long dsfId) {
        TenantContext.setCurrentTenant(tenantName);
        MstDischargeSummaryForm mstDischargeSummaryForm = mstDischargeSummaryFormRepository.getById(dsfId);
        if (mstDischargeSummaryForm != null) {
            mstDischargeSummaryForm.setIsDeleted(true);
            mstDischargeSummaryFormRepository.save(mstDischargeSummaryForm);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
