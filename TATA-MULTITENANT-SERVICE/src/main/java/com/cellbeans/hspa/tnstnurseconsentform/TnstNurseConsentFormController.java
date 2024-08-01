package com.cellbeans.hspa.tnstnurseconsentform;

import com.cellbeans.hspa.TenantContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tnst_nurse_consent_form")
public class TnstNurseConsentFormController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TnstNurseConsentFormRepository tnstNurseConsentFormRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TnstNurseConsentForm tnstNurseConsentForm) {
        TenantContext.setCurrentTenant(tenantName);
        if (tnstNurseConsentForm.getNcfcId() != null) {
            tnstNurseConsentFormRepository.save(tnstNurseConsentForm);
            respMap.put("Success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{ncfId}")
    public TnstNurseConsentForm read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ncfId") Long ncfId) {
        TenantContext.setCurrentTenant(tenantName);
        TnstNurseConsentForm tnstNurseConsentForm = tnstNurseConsentFormRepository.getById(ncfId);
        return tnstNurseConsentForm;
    }

    @RequestMapping("update")
    public TnstNurseConsentForm update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TnstNurseConsentForm tnstNurseConsentForm) {
        TenantContext.setCurrentTenant(tenantName);
        return tnstNurseConsentFormRepository.save(tnstNurseConsentForm);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TnstNurseConsentForm> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ncfcId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tnstNurseConsentFormRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return tnstNurseConsentFormRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @GetMapping
    @RequestMapping("listByAdmissionId")
    public Iterable<TnstNurseConsentForm> listByAdmissionId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ncfcId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tnstNurseConsentFormRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return tnstNurseConsentFormRepository.findByNcfAdmissionIdAdmissionIdAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @PutMapping("delete/{ncfId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ncfId") Long ncfcId) {
        TenantContext.setCurrentTenant(tenantName);
        TnstNurseConsentForm tnstNurseConsentForm = tnstNurseConsentFormRepository.getById(ncfcId);
        if (tnstNurseConsentForm != null) {
            tnstNurseConsentForm.setDeleted(true);
            tnstNurseConsentFormRepository.save(tnstNurseConsentForm);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
