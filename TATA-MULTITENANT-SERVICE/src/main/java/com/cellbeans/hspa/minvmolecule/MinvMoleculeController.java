package com.cellbeans.hspa.minvmolecule;

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
@RequestMapping("/minv_molecule")
public class MinvMoleculeController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MinvMoleculeRepository minvMoleculeRepository;

    @Autowired
    private MinvMoleculeService minvMoleculeService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MinvMolecule minvMolecule) {
        TenantContext.setCurrentTenant(tenantName);
        if (minvMolecule.getMoleculeName() != null) {
            minvMoleculeRepository.save(minvMolecule);
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
        List<MinvMolecule> records;
        records = minvMoleculeRepository.findByMoleculeNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{moleculeId}")
    public MinvMolecule read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("moleculeId") Long moleculeId) {
        TenantContext.setCurrentTenant(tenantName);
        MinvMolecule minvMolecule = minvMoleculeRepository.getById(moleculeId);
        return minvMolecule;
    }

    @RequestMapping("update")
    public MinvMolecule update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MinvMolecule minvMolecule) {
        TenantContext.setCurrentTenant(tenantName);
        return minvMoleculeRepository.save(minvMolecule);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MinvMolecule> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "moleculeId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return minvMoleculeRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return minvMoleculeRepository.findByMoleculeNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{moleculeId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("moleculeId") Long moleculeId) {
        TenantContext.setCurrentTenant(tenantName);
        MinvMolecule minvMolecule = minvMoleculeRepository.getById(moleculeId);
        if (minvMolecule != null) {
            minvMolecule.setIsDeleted(true);
            minvMoleculeRepository.save(minvMolecule);
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
        List<Tuple> items = minvMoleculeService.getMinvMoleculeForDropdown(page, size, globalFilter);
        return items;
    }

}
            