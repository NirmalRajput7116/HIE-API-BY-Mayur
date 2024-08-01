package com.cellbeans.hspa.mstconsent;

import com.cellbeans.hspa.TenantContext;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_consent")
public class MstConsentController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstConsentRepository mstConsentRepository;

    @Autowired
    private MstConsentService mstConsentService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstConsent mstConsent) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstConsent.getConsentName() != null) {
            mstConsent.setConsentName(mstConsent.getConsentName().trim());
            MstConsent mstConsentObject = mstConsentRepository.findByConsentNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstConsent.getConsentName());
            if (mstConsentObject == null) {
                mstConsentRepository.save(mstConsent);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "2");
                respMap.put("msg", "Already Added");
                return respMap;
            }
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
        List<MstConsent> records;
        records = mstConsentRepository.findByConsentNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{consentId}")
    public MstConsent read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("consentId") Long consentId) {
        TenantContext.setCurrentTenant(tenantName);
        MstConsent mstConsent = mstConsentRepository.getById(consentId);
        return mstConsent;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstConsent mstConsent) {
        TenantContext.setCurrentTenant(tenantName);
        mstConsent.setConsentName(mstConsent.getConsentName().trim());
        MstConsent mstConsentObject = mstConsentRepository.findByConsentNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstConsent.getConsentName());
        if (mstConsentObject == null) {
            mstConsentRepository.save(mstConsent);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstConsentObject.getConsentId() == mstConsent.getConsentId()) {
            mstConsentRepository.save(mstConsent);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else {
            respMap.put("success", "2");
            respMap.put("msg", "Already Added");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstConsent> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "consentId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstConsentRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByConsentNameAsc(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstConsentRepository.findByConsentNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByConsentNameAsc(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{consentId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("consentId") Long consentId) {
        TenantContext.setCurrentTenant(tenantName);
        MstConsent mstConsent = mstConsentRepository.getById(consentId);
        if (mstConsent != null) {
            mstConsent.setIsDeleted(true);
            mstConsentRepository.save(mstConsent);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = mstConsentService.getMstConsentForDropdown(page, size, globalFilter);
        return items;
    }

}
            
