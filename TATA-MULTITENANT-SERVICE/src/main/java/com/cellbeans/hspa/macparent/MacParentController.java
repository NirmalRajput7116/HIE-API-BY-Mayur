package com.cellbeans.hspa.macparent;

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
@RequestMapping("/mac_parent")
public class MacParentController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MacParentRepository macParentRepository;

    @Autowired
    private MacParentService macParentService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MacParent macParent) {
        TenantContext.setCurrentTenant(tenantName);
        if (macParent.getParentName() != null) {
            macParentRepository.save(macParent);
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
        List<MacParent> records;
        records = macParentRepository.findByParentNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{parentId}")
    public MacParent read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("parentId") Long parentId) {
        TenantContext.setCurrentTenant(tenantName);
        MacParent macParent = macParentRepository.getById(parentId);
        return macParent;
    }

    @RequestMapping("update")
    public MacParent update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MacParent macParent) {
        TenantContext.setCurrentTenant(tenantName);
        return macParentRepository.save(macParent);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MacParent> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "parentId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return macParentRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return macParentRepository.findByParentNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{parentId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("parentId") Long parentId) {
        TenantContext.setCurrentTenant(tenantName);
        MacParent macParent = macParentRepository.getById(parentId);
        if (macParent != null) {
            macParent.setIsDeleted(true);
            macParentRepository.save(macParent);
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
        List<Tuple> items = macParentService.getMacParentForDropdown(page, size, globalFilter);
        return items;
    }

}
            
