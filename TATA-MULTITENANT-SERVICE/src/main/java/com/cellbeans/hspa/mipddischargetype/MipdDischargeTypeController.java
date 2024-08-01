package com.cellbeans.hspa.mipddischargetype;

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
@RequestMapping("/mipd_discharge_type")
public class MipdDischargeTypeController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MipdDischargeTypeRepository mipdDischargeTypeRepository;

    @Autowired
    private MipdDischargeTypeService mipdDischargeTypeService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MipdDischargeType mipdDischargeType) {
        TenantContext.setCurrentTenant(tenantName);
        if (mipdDischargeType.getDtName() != null) {
            mipdDischargeTypeRepository.save(mipdDischargeType);
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
        List<MipdDischargeType> records;
        records = mipdDischargeTypeRepository.findByDtNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{dtId}")
    public MipdDischargeType read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dtId") Long dtId) {
        TenantContext.setCurrentTenant(tenantName);
        MipdDischargeType mipdDischargeType = mipdDischargeTypeRepository.getById(dtId);
        return mipdDischargeType;
    }

    @RequestMapping("update")
    public MipdDischargeType update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MipdDischargeType mipdDischargeType) {
        TenantContext.setCurrentTenant(tenantName);
        return mipdDischargeTypeRepository.save(mipdDischargeType);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MipdDischargeType> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "dtId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mipdDischargeTypeRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mipdDischargeTypeRepository.findByDtNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{dtId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dtId") Long dtId) {
        TenantContext.setCurrentTenant(tenantName);
        MipdDischargeType mipdDischargeType = mipdDischargeTypeRepository.getById(dtId);
        if (mipdDischargeType != null) {
            mipdDischargeType.setIsDeleted(true);
            mipdDischargeTypeRepository.save(mipdDischargeType);
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
        List<Tuple> items = mipdDischargeTypeService.getMipdDischargeTypeForDropdown(page, size, globalFilter);
        return items;
    }

}
            
