package com.cellbeans.hspa.mpathsamplesuitability;

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
@RequestMapping("/mpath_sample_suitability")
public class MpathSampleSuitabilityController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MpathSampleSuitabilityRepository mpathSampleSuitabilityRepository;

    @Autowired
    private MpathSampleSuitabilityService mpathSampleSuitabilityService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathSampleSuitability mpathSampleSuitability) {
        TenantContext.setCurrentTenant(tenantName);
        if (mpathSampleSuitability.getSsName() != null) {
            mpathSampleSuitabilityRepository.save(mpathSampleSuitability);
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
        List<MpathSampleSuitability> records;
        records = mpathSampleSuitabilityRepository.findBySsNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{ssId}")
    public MpathSampleSuitability read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ssId") Long ssId) {
        TenantContext.setCurrentTenant(tenantName);
        MpathSampleSuitability mpathSampleSuitability = mpathSampleSuitabilityRepository.getById(ssId);
        return mpathSampleSuitability;
    }

    @RequestMapping("update")
    public MpathSampleSuitability update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathSampleSuitability mpathSampleSuitability) {
        TenantContext.setCurrentTenant(tenantName);
        return mpathSampleSuitabilityRepository.save(mpathSampleSuitability);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MpathSampleSuitability> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ssId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mpathSampleSuitabilityRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mpathSampleSuitabilityRepository.findBySsNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{ssId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ssId") Long ssId) {
        TenantContext.setCurrentTenant(tenantName);
        MpathSampleSuitability mpathSampleSuitability = mpathSampleSuitabilityRepository.getById(ssId);
        if (mpathSampleSuitability != null) {
            mpathSampleSuitability.setIsDeleted(true);
            mpathSampleSuitabilityRepository.save(mpathSampleSuitability);
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
        List<Tuple> items = mpathSampleSuitabilityService.getMpathSampleSuitabilityForDropdown(page, size, globalFilter);
        return items;
    }

}
            
