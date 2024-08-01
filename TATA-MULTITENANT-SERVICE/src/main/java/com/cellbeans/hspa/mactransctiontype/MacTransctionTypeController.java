package com.cellbeans.hspa.mactransctiontype;

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
@RequestMapping("/mac_transction_type")
public class MacTransctionTypeController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MacTransctionTypeRepository macTransctionTypeRepository;

    @Autowired
    private MacTransctionTypeService macTransctionTypeService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MacTransctionType macTransctionType) {
        TenantContext.setCurrentTenant(tenantName);
        if (macTransctionType.getTtName() != null) {
            macTransctionTypeRepository.save(macTransctionType);
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
        List<MacTransctionType> records;
        records = macTransctionTypeRepository.findByTtNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{ttId}")
    public MacTransctionType read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ttId") Long ttId) {
        TenantContext.setCurrentTenant(tenantName);
        MacTransctionType macTransctionType = macTransctionTypeRepository.getById(ttId);
        return macTransctionType;
    }

    @RequestMapping("update")
    public MacTransctionType update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MacTransctionType macTransctionType) {
        TenantContext.setCurrentTenant(tenantName);
        return macTransctionTypeRepository.save(macTransctionType);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MacTransctionType> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ttId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return macTransctionTypeRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return macTransctionTypeRepository.findByTtNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{ttId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ttId") Long ttId) {
        TenantContext.setCurrentTenant(tenantName);
        MacTransctionType macTransctionType = macTransctionTypeRepository.getById(ttId);
        if (macTransctionType != null) {
            macTransctionType.setIsDeleted(true);
            macTransctionTypeRepository.save(macTransctionType);
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
        List<Tuple> items = macTransctionTypeService.getMacTransctionTypeDropdown(page, size, globalFilter);
        return items;
    }

}
            
