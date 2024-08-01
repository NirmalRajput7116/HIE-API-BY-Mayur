package com.cellbeans.hspa.memrhistorytemplate;

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
@RequestMapping("/memr_history_template")
public class MemrHistoryTemplateController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MemrHistoryTemplateRepository memrHistoryTemplateRepository;

    @Autowired
    private MemrHistoryTemplateService memrHistoryTemplateService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrHistoryTemplate memrHistoryTemplate) {
        TenantContext.setCurrentTenant(tenantName);
        if (memrHistoryTemplate.getHtName() != null) {
            if (memrHistoryTemplateRepository.findByAllOrderByHtName(memrHistoryTemplate.getHtName()) == 0) {
                memrHistoryTemplateRepository.save(memrHistoryTemplate);
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
        List<MemrHistoryTemplate> records;
        records = memrHistoryTemplateRepository.findByHtNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{htId}")
    public MemrHistoryTemplate read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("htId") Long htId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrHistoryTemplate memrHistoryTemplate = memrHistoryTemplateRepository.getById(htId);
        return memrHistoryTemplate;
    }

    @RequestMapping("update")
    public MemrHistoryTemplate update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrHistoryTemplate memrHistoryTemplate) {
        TenantContext.setCurrentTenant(tenantName);
        return memrHistoryTemplateRepository.save(memrHistoryTemplate);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MemrHistoryTemplate> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "htId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return memrHistoryTemplateRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return memrHistoryTemplateRepository.findByHtNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{htId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("htId") Long htId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrHistoryTemplate memrHistoryTemplate = memrHistoryTemplateRepository.getById(htId);
        if (memrHistoryTemplate != null) {
            memrHistoryTemplate.setIsDeleted(true);
            memrHistoryTemplateRepository.save(memrHistoryTemplate);
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
        List<Tuple> items = memrHistoryTemplateService.getMemrHistoryTemplateForDropdown(page, size, globalFilter);
        return items;
    }

}
            
