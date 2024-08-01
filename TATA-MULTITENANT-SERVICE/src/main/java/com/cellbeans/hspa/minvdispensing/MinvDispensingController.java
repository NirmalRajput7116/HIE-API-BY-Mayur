package com.cellbeans.hspa.minvdispensing;

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
@RequestMapping("/minv_dispensing")
public class MinvDispensingController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MinvDispensingRepository minvDispensingRepository;

    @Autowired
    private MinvDispensingService minvDispensingService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MinvDispensing minvDispensing) {
        TenantContext.setCurrentTenant(tenantName);
        if (minvDispensing.getDispensingName() != null) {
            minvDispensingRepository.save(minvDispensing);
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
        List<MinvDispensing> records;
        records = minvDispensingRepository.findByDispensingNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{dispensingId}")
    public MinvDispensing read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dispensingId") Long dispensingId) {
        TenantContext.setCurrentTenant(tenantName);
        MinvDispensing minvDispensing = minvDispensingRepository.getById(dispensingId);
        return minvDispensing;
    }

    @RequestMapping("update")
    public MinvDispensing update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MinvDispensing minvDispensing) {
        TenantContext.setCurrentTenant(tenantName);
        return minvDispensingRepository.save(minvDispensing);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MinvDispensing> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "dispensingId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return minvDispensingRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return minvDispensingRepository.findByDispensingNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{dispensingId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dispensingId") Long dispensingId) {
        TenantContext.setCurrentTenant(tenantName);
        MinvDispensing minvDispensing = minvDispensingRepository.getById(dispensingId);
        if (minvDispensing != null) {
            minvDispensing.setIsDeleted(true);
            minvDispensingRepository.save(minvDispensing);
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
        List<Tuple> items = minvDispensingService.getMinvDispensingForDropdown(page, size, globalFilter);
        return items;
    }

}
            
