package com.cellbeans.hspa.mstsmstemplate;

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
@RequestMapping("/mst_sms_template")
public class MstSmsTemplateController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstSmsTemplateRepository mstSmsTemplateRepository;

    @Autowired
    private MstSmsTemplateService mstSmsTemplateService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstSmsTemplate mstSmsTemplate) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstSmsTemplate.getStName() != null) {
            mstSmsTemplateRepository.save(mstSmsTemplate);
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
        List<MstSmsTemplate> records;
        records = mstSmsTemplateRepository.findByStNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{stId}")
    public MstSmsTemplate read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("stId") Long stId) {
        TenantContext.setCurrentTenant(tenantName);
        TenantContext.setCurrentTenant(tenantName);
        MstSmsTemplate mstSmsTemplate = mstSmsTemplateRepository.getById(stId);
        return mstSmsTemplate;
    }

    @RequestMapping("update")
    public MstSmsTemplate update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstSmsTemplate mstSmsTemplate) {
        TenantContext.setCurrentTenant(tenantName);
        return mstSmsTemplateRepository.save(mstSmsTemplate);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstSmsTemplate> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "stId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstSmsTemplateRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstSmsTemplateRepository.findByStNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{stId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("stId") Long stId) {
        TenantContext.setCurrentTenant(tenantName);
        MstSmsTemplate mstSmsTemplate = mstSmsTemplateRepository.getById(stId);
        if (mstSmsTemplate != null) {
            mstSmsTemplate.setIsDeleted(true);
            mstSmsTemplateRepository.save(mstSmsTemplate);
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
        List<Tuple> items = mstSmsTemplateService.getMstSmsTemplateForDropdown(page, size, globalFilter);
        return items;
    }

}
            
