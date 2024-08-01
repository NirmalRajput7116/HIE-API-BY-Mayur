package com.cellbeans.hspa.memrdosefrequency;

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
@RequestMapping("/memr_dose_frequency")
public class MemrDoseFrequencyController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MemrDoseFrequencyRepository memrDoseFrequencyRepository;

    @Autowired
    private MemrDoseFrequencyService memrDoseFrequencyService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrDoseFrequency memrDoseFrequency) {
        TenantContext.setCurrentTenant(tenantName);
        if (memrDoseFrequency.getDfName() != null) {
            memrDoseFrequencyRepository.save(memrDoseFrequency);
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
        List<MemrDoseFrequency> records;
        records = memrDoseFrequencyRepository.findByDfNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{dfId}")
    public MemrDoseFrequency read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dfId") Long dfId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrDoseFrequency memrDoseFrequency = memrDoseFrequencyRepository.getById(dfId);
        return memrDoseFrequency;
    }

    @RequestMapping("update")
    public MemrDoseFrequency update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrDoseFrequency memrDoseFrequency) {
        TenantContext.setCurrentTenant(tenantName);
        return memrDoseFrequencyRepository.save(memrDoseFrequency);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MemrDoseFrequency> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "dfId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return memrDoseFrequencyRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return memrDoseFrequencyRepository.findByDfNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{dfId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dfId") Long dfId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrDoseFrequency memrDoseFrequency = memrDoseFrequencyRepository.getById(dfId);
        if (memrDoseFrequency != null) {
            memrDoseFrequency.setIsDeleted(true);
            memrDoseFrequencyRepository.save(memrDoseFrequency);
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
        List<Tuple> items = memrDoseFrequencyService.getMemrDoseFrequencyForDropdown(page, size, globalFilter);
        return items;
    }

}
            
