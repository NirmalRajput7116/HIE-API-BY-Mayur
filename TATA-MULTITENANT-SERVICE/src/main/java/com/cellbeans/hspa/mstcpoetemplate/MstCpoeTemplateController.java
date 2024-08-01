package com.cellbeans.hspa.mstcpoetemplate;

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
@RequestMapping("/mst_cpoe_template")
public class MstCpoeTemplateController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstCpoeTemplateRepository mstCpoeTemplateRepository;

    @Autowired
    private MstCpoeTemplateService mstCpoeTemplateService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstCpoeTemplate mstCpoeTemplate) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstCpoeTemplate.getCtName() != null) {
            mstCpoeTemplateRepository.save(mstCpoeTemplate);
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
        List<MstCpoeTemplate> records;
        records = mstCpoeTemplateRepository.findByCtNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{ctId}")
    public MstCpoeTemplate read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ctId") Long ctId) {
        TenantContext.setCurrentTenant(tenantName);
        MstCpoeTemplate mstCpoeTemplate = mstCpoeTemplateRepository.getById(ctId);
        return mstCpoeTemplate;
    }

    @RequestMapping("update")
    public MstCpoeTemplate update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstCpoeTemplate mstCpoeTemplate) {
        TenantContext.setCurrentTenant(tenantName);
        return mstCpoeTemplateRepository.save(mstCpoeTemplate);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstCpoeTemplate> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ctId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstCpoeTemplateRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstCpoeTemplateRepository.findByCtNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{ctId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ctId") Long ctId) {
        TenantContext.setCurrentTenant(tenantName);
        MstCpoeTemplate mstCpoeTemplate = mstCpoeTemplateRepository.getById(ctId);
        if (mstCpoeTemplate != null) {
            mstCpoeTemplate.setIsDeleted(true);
            mstCpoeTemplateRepository.save(mstCpoeTemplate);
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
        List<Tuple> items = mstCpoeTemplateService.getMstCpoeTemplateForDropdown(page, size, globalFilter);
        return items;
    }

}
            
