package com.cellbeans.hspa.mipddischargetemplate;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mipd_discharge_template")
public class MipdDischargeTemplateController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MipdDischargeTemplateRepository mipdDischargeTemplateRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MipdDischargeTemplate mipdDischargeTemplate) {
        TenantContext.setCurrentTenant(tenantName);
        if (mipdDischargeTemplate.getDtName() != null) {
            mipdDischargeTemplateRepository.save(mipdDischargeTemplate);
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
        List<MipdDischargeTemplate> records;
        records = mipdDischargeTemplateRepository.findByDtNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("/templatelist")
    public Map<String, Object> templatelist(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MipdDischargeTemplate> records;
        records = mipdDischargeTemplateRepository.findDistinctDtName();
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{dtId}")
    public MipdDischargeTemplate read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dtId") Long dtId) {
        TenantContext.setCurrentTenant(tenantName);
        MipdDischargeTemplate mipdDischargeTemplate = mipdDischargeTemplateRepository.getById(dtId);
        return mipdDischargeTemplate;
    }

    @RequestMapping("byname/{dtId}")
    public List<MipdDischargeTemplate> readname(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dtId") String dtId) {
        TenantContext.setCurrentTenant(tenantName);
        return mipdDischargeTemplateRepository.findByDtNameEqualsAndIsActiveTrueAndIsDeletedFalse(dtId);
    }

    @RequestMapping("update")
    public MipdDischargeTemplate update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MipdDischargeTemplate mipdDischargeTemplate) {
        TenantContext.setCurrentTenant(tenantName);
        return mipdDischargeTemplateRepository.save(mipdDischargeTemplate);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MipdDischargeTemplate> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "dtId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mipdDischargeTemplateRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mipdDischargeTemplateRepository.findByDtNameContainsAndIsActiveTrueAndIsDeletedFalseOrDtTemplateContainsAndIsActiveTrueAndIsDeletedFalse(qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{dtId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dtId") Long dtId) {
        TenantContext.setCurrentTenant(tenantName);
        MipdDischargeTemplate mipdDischargeTemplate = mipdDischargeTemplateRepository.getById(dtId);
        if (mipdDischargeTemplate != null) {
            mipdDischargeTemplate.setIsDeleted(true);
            mipdDischargeTemplateRepository.save(mipdDischargeTemplate);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
