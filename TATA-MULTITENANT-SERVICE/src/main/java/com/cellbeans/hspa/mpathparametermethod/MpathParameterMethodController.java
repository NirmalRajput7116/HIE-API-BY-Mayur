package com.cellbeans.hspa.mpathparametermethod;

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
@RequestMapping("/mpath_parameter_method")
public class MpathParameterMethodController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MpathParameterMethodRepository mpathParameterMethodRepository;

    @Autowired
    private MpathParameterMethodService mpathParameterMethodService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathParameterMethod mpathParameterMethod) {
        TenantContext.setCurrentTenant(tenantName);
        if (mpathParameterMethod.getPmName() != null) {
            mpathParameterMethodRepository.save(mpathParameterMethod);
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
        List<MpathParameterMethod> records;
        records = mpathParameterMethodRepository.findByPmNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{PmId}")
    public MpathParameterMethod read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("PmId") Long PmId) {
        TenantContext.setCurrentTenant(tenantName);
        MpathParameterMethod mpathParameterMethod = mpathParameterMethodRepository.getById(PmId);
        return mpathParameterMethod;
    }

    @RequestMapping("update")
    public MpathParameterMethod update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathParameterMethod mpathParameterMethod) {
        TenantContext.setCurrentTenant(tenantName);
        return mpathParameterMethodRepository.save(mpathParameterMethod);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MpathParameterMethod> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "PmId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mpathParameterMethodRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mpathParameterMethodRepository.findByPmNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{PmId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("PmId") Long PmId) {
        TenantContext.setCurrentTenant(tenantName);
        MpathParameterMethod mpathParameterMethod = mpathParameterMethodRepository.getById(PmId);
        if (mpathParameterMethod != null) {
            mpathParameterMethod.setIsDeleted(true);
            mpathParameterMethodRepository.save(mpathParameterMethod);
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
        List<Tuple> items = mpathParameterMethodService.getMpathParameterMethodForDropdown(page, size, globalFilter);
        return items;
    }

}
            
