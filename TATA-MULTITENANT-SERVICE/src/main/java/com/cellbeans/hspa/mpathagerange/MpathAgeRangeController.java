package com.cellbeans.hspa.mpathagerange;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mpathparameter.MpathParameterRepository;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mpath_age_range")
public class MpathAgeRangeController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MpathAgeRangeRepository mpathAgeRangeRepository;
    MpathParameterRepository mpathParameterRepository;

    @Autowired
    private MpathAgeRangeService mpathAgeRangeService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathAgeRange mpathAgeRange) {
        TenantContext.setCurrentTenant(tenantName);
        if (mpathAgeRange.getArAgeFrom() != 0) {
            mpathAgeRangeRepository.save(mpathAgeRange);
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
        List<MpathAgeRange> records;
        records = mpathAgeRangeRepository.findByArAgeFromContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{arId}")
    public MpathAgeRange read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("arId") Long arId) {
        TenantContext.setCurrentTenant(tenantName);
        MpathAgeRange mpathAgeRange = mpathAgeRangeRepository.getById(arId);
        return mpathAgeRange;
    }

    @RequestMapping("agerangedetail/{age}/{genderId}/{parameterId}")
    public MpathAgeRange getAgeRangeDefaultvalue(@RequestHeader("X-tenantId") String tenantName, @PathVariable("age") int age, @PathVariable("genderId") int genderId, @PathVariable("parameterId") int parameterId) {
        TenantContext.setCurrentTenant(tenantName);
        MpathAgeRange mpathAgeRange = new MpathAgeRange(); //mpathAgeRangeRepository.findMpathAgeRangeQuery(age, genderId, parameterId);
        return mpathAgeRange;
    }

    @RequestMapping("update")
    public MpathAgeRange update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathAgeRange mpathAgeRange) {
        TenantContext.setCurrentTenant(tenantName);
        return mpathAgeRangeRepository.save(mpathAgeRange);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MpathAgeRange> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "parameterId", required = false) long parameterId, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "arId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if ((qString == null || qString.equals("")) && (parameterId == 0)) {
            return mpathAgeRangeRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }
        if ((qString == null || qString.equals("")) && (parameterId != 0)) {
            return mpathAgeRangeRepository.findByArParameterIdParameterIdLikeAndIsActiveTrueAndIsDeletedFalse(parameterId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return mpathAgeRangeRepository.findByArAgeFromContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{arId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("arId") Long arId) {
        TenantContext.setCurrentTenant(tenantName);
        MpathAgeRange mpathAgeRange = mpathAgeRangeRepository.getById(arId);
        if (mpathAgeRange != null) {
            mpathAgeRange.setIsDeleted(true);
            mpathAgeRangeRepository.save(mpathAgeRange);
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
        List<Tuple> items = mpathAgeRangeService.getMpathAgeRangeForDropdown(page, size, globalFilter);
        return items;
    }

}
            
