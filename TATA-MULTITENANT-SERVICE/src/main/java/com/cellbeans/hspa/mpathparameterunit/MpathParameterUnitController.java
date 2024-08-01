package com.cellbeans.hspa.mpathparameterunit;

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
@RequestMapping("/mpath_parameter_unit")
public class MpathParameterUnitController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MpathParameterUnitRepository mpathParameterUnitRepository;

    @Autowired
    private MpathParameterUnitService mpathParameterUnitService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathParameterUnit mpathParameterUnit) {
        TenantContext.setCurrentTenant(tenantName);
        if (mpathParameterUnit.getPuName() != null) {
            mpathParameterUnitRepository.save(mpathParameterUnit);
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
        List<MpathParameterUnit> records;
        records = mpathParameterUnitRepository.findByPuNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{puId}")
    public MpathParameterUnit read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("puId") Long puId) {
        TenantContext.setCurrentTenant(tenantName);
        MpathParameterUnit mpathParameterUnit = mpathParameterUnitRepository.getById(puId);
        return mpathParameterUnit;
    }

    @RequestMapping("update")
    public MpathParameterUnit update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathParameterUnit mpathParameterUnit) {
        TenantContext.setCurrentTenant(tenantName);
        return mpathParameterUnitRepository.save(mpathParameterUnit);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MpathParameterUnit> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "puId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mpathParameterUnitRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mpathParameterUnitRepository.findByPuNameContainsAndIsActiveTrueAndIsDeletedFalseOrPuCodeContainsAndIsActiveTrueAndIsDeletedFalse(qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{puId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("puId") Long puId) {
        TenantContext.setCurrentTenant(tenantName);
        MpathParameterUnit mpathParameterUnit = mpathParameterUnitRepository.getById(puId);
        if (mpathParameterUnit != null) {
            mpathParameterUnit.setIsDeleted(true);
            mpathParameterUnitRepository.save(mpathParameterUnit);
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
        List<Tuple> items = mpathParameterUnitService.getMpathParameterUnitForDropdown(page, size, globalFilter);
        return items;
    }

}
            
