package com.cellbeans.hspa.mpathcontainer;

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
@RequestMapping("/mpath_container")
public class MpathContainerController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MpathContainerRepository mpathContainerRepository;

    @Autowired
    private MpathContainerService mpathContainerService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathContainer mpathContainer) {
        TenantContext.setCurrentTenant(tenantName);
        if (mpathContainer.getContainerName() != null) {
            mpathContainerRepository.save(mpathContainer);
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
        List<MpathContainer> records;
        records = mpathContainerRepository.findByContainerNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{containerId}")
    public MpathContainer read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("containerId") Long containerId) {
        TenantContext.setCurrentTenant(tenantName);
        MpathContainer mpathContainer = mpathContainerRepository.getById(containerId);
        return mpathContainer;
    }

    @RequestMapping("update")
    public MpathContainer update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathContainer mpathContainer) {
        TenantContext.setCurrentTenant(tenantName);
        return mpathContainerRepository.save(mpathContainer);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MpathContainer> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "containerId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mpathContainerRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mpathContainerRepository.findByContainerNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{containerId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("containerId") Long containerId) {
        TenantContext.setCurrentTenant(tenantName);
        MpathContainer mpathContainer = mpathContainerRepository.getById(containerId);
        if (mpathContainer != null) {
            mpathContainer.setIsDeleted(true);
            mpathContainerRepository.save(mpathContainer);
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
        List<Tuple> items = mpathContainerService.getMpathContainerForDropdown(page, size, globalFilter);
        return items;
    }

}
            
