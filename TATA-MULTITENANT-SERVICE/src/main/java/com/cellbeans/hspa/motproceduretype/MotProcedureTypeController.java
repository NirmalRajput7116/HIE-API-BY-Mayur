package com.cellbeans.hspa.motproceduretype;

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
@RequestMapping("/mot_procedure_type")
public class MotProcedureTypeController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MotProcedureTypeRepository motProcedureTypeRepository;

    @Autowired
    private MotProcedureTypeService motProcedureTypeService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MotProcedureType motProcedureType) {
        TenantContext.setCurrentTenant(tenantName);
        if (motProcedureType.getPtName() != null) {
            motProcedureTypeRepository.save(motProcedureType);
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
        List<MotProcedureType> records;
        records = motProcedureTypeRepository.findByPtNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{ptId}")
    public MotProcedureType read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ptId") Long ptId) {
        TenantContext.setCurrentTenant(tenantName);
        MotProcedureType motProcedureType = motProcedureTypeRepository.getById(ptId);
        return motProcedureType;
    }

    @RequestMapping("update")
    public MotProcedureType update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MotProcedureType motProcedureType) {
        TenantContext.setCurrentTenant(tenantName);
        return motProcedureTypeRepository.save(motProcedureType);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MotProcedureType> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ptId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return motProcedureTypeRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return motProcedureTypeRepository.findByPtNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{ptId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ptId") Long ptId) {
        TenantContext.setCurrentTenant(tenantName);
        MotProcedureType motProcedureType = motProcedureTypeRepository.getById(ptId);
        if (motProcedureType != null) {
            motProcedureType.setIsDeleted(true);
            motProcedureTypeRepository.save(motProcedureType);
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
        List<Tuple> items = motProcedureTypeService.getMotProcedureTypeForDropdown(page, size, globalFilter);
        return items;
    }

}
            
