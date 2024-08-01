package com.cellbeans.hspa.mradtemplate;

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
@RequestMapping("/mrad_template")
public class MradTemplateController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MradTemplateRepository mradTemplateRepository;

    @Autowired
    private MradTemplateService mradTemplateService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MradTemplate mradTemplate) {
        TenantContext.setCurrentTenant(tenantName);
        if (mradTemplate.getTemplateName() != null) {
            mradTemplateRepository.save(mradTemplate);
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
        List<MradTemplate> records;
        records = mradTemplateRepository.findByTemplateNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{templateId}")
    public MradTemplate read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("templateId") Long templateId) {
        TenantContext.setCurrentTenant(tenantName);
        MradTemplate mradTemplate = mradTemplateRepository.getById(templateId);
        return mradTemplate;
    }

    @RequestMapping("update")
    public MradTemplate update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MradTemplate mradTemplate) {
        TenantContext.setCurrentTenant(tenantName);
        return mradTemplateRepository.save(mradTemplate);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MradTemplate> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "templateId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mradTemplateRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mradTemplateRepository.findByTemplateNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{templateId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("templateId") Long templateId) {
        TenantContext.setCurrentTenant(tenantName);
        MradTemplate mradTemplate = mradTemplateRepository.getById(templateId);
        if (mradTemplate != null) {
            mradTemplate.setIsDeleted(true);
            mradTemplateRepository.save(mradTemplate);
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
        List<Tuple> items = mradTemplateService.getMradTemplateForDropdown(page, size, globalFilter);
        return items;
    }

}
            
