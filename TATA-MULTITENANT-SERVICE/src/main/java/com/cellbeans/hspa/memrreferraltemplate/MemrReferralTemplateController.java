package com.cellbeans.hspa.memrreferraltemplate;

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
@RequestMapping("/memr_referral_template")
public class MemrReferralTemplateController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MemrReferralTemplateRepository memrReferralTemplateRepository;

    @Autowired
    private MemrReferralTemplateService memrReferralTemplateService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrReferralTemplate memrReferralTemplate) {
        TenantContext.setCurrentTenant(tenantName);
        if (memrReferralTemplate.getRtName() != null) {
            if (memrReferralTemplateRepository.findByAllOrderByClusterName(memrReferralTemplate.getRtName()) == 0) {
                memrReferralTemplateRepository.save(memrReferralTemplate);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Already Added");
            }
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
        List<MemrReferralTemplate> records;
        records = memrReferralTemplateRepository.findByRtNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{rtId}")
    public MemrReferralTemplate read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("rtId") Long rtId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrReferralTemplate memrReferralTemplate = memrReferralTemplateRepository.getById(rtId);
        return memrReferralTemplate;
    }

    @RequestMapping("update")
    public MemrReferralTemplate update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrReferralTemplate memrReferralTemplate) {
        TenantContext.setCurrentTenant(tenantName);
        return memrReferralTemplateRepository.save(memrReferralTemplate);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MemrReferralTemplate> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "rtId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return memrReferralTemplateRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return memrReferralTemplateRepository.findByRtNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{rtId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("rtId") Long rtId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrReferralTemplate memrReferralTemplate = memrReferralTemplateRepository.getById(rtId);
        if (memrReferralTemplate != null) {
            memrReferralTemplate.setIsDeleted(true);
            memrReferralTemplateRepository.save(memrReferralTemplate);
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
        List<Tuple> items = memrReferralTemplateService.getMemrReferralTemplateForDropdown(page, size, globalFilter);
        return items;
    }

}
            
