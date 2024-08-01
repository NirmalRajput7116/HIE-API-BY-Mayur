package com.cellbeans.hspa.tinvenquirytermsandcondition;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_enquiry_terms_and_condition")
public class TinvEnquiryTermsAndConditionController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TinvEnquiryTermsAndConditionRepository tinvEnquiryTermsAndConditionRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvEnquiryTermsAndCondition> tinvEnquiryTermsAndCondition) {
        if (tinvEnquiryTermsAndCondition.size() != 0) {
            TenantContext.setCurrentTenant(tenantName);
            tinvEnquiryTermsAndConditionRepository.saveAll(tinvEnquiryTermsAndCondition);
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
        List<TinvEnquiryTermsAndCondition> records;
        records = tinvEnquiryTermsAndConditionRepository.findByEtacPieIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{etacId}")
    public TinvEnquiryTermsAndCondition read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("etacId") Long etacId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvEnquiryTermsAndCondition tinvEnquiryTermsAndCondition = tinvEnquiryTermsAndConditionRepository.getById(etacId);
        return tinvEnquiryTermsAndCondition;
    }

    @RequestMapping("update")
    public TinvEnquiryTermsAndCondition update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvEnquiryTermsAndCondition tinvEnquiryTermsAndCondition) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvEnquiryTermsAndConditionRepository.save(tinvEnquiryTermsAndCondition);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TinvEnquiryTermsAndCondition> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "etacId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tinvEnquiryTermsAndConditionRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tinvEnquiryTermsAndConditionRepository.findByEtacNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{etacId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("etacId") Long etacId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvEnquiryTermsAndCondition tinvEnquiryTermsAndCondition = tinvEnquiryTermsAndConditionRepository.getById(etacId);
        if (tinvEnquiryTermsAndCondition != null) {
            tinvEnquiryTermsAndCondition.setIsDeleted(true);
            tinvEnquiryTermsAndConditionRepository.save(tinvEnquiryTermsAndCondition);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("getTermsByID/{etacId}")
    public List<TinvEnquiryTermsAndCondition> getAllTermsAndCondation(@RequestHeader("X-tenantId") String tenantName, @PathVariable("etacId") Long etacId) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvEnquiryTermsAndConditionRepository.findAllByEtacPieIdPieIdAndIsActiveTrueAndIsDeletedFalse(etacId);

    }

}
            
