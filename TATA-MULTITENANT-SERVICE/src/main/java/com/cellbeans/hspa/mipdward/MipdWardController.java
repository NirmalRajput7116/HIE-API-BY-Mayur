package com.cellbeans.hspa.mipdward;

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
@RequestMapping("/mipd_ward")
public class MipdWardController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MipdWardRepository mipdWardRepository;

    @Autowired
    private MipdWardService mipdWardService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MipdWard mipdWard) {
        TenantContext.setCurrentTenant(tenantName);
        if (mipdWard.getWardName() != null) {
            mipdWardRepository.save(mipdWard);
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
        List<MipdWard> records;
        records = mipdWardRepository.findByWardNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{wardId}")
    public MipdWard read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("wardId") Long wardId) {
        TenantContext.setCurrentTenant(tenantName);
        MipdWard mipdWard = mipdWardRepository.getById(wardId);
        return mipdWard;
    }

    @RequestMapping("update")
    public MipdWard update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MipdWard mipdWard) {
        TenantContext.setCurrentTenant(tenantName);
        return mipdWardRepository.save(mipdWard);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MipdWard> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "wardId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mipdWardRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mipdWardRepository.findByWardNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{wardId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("wardId") Long wardId) {
        TenantContext.setCurrentTenant(tenantName);
        MipdWard mipdWard = mipdWardRepository.getById(wardId);
        if (mipdWard != null) {
            mipdWard.setIsDeleted(true);
            mipdWardRepository.save(mipdWard);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("byunitid/{unitId}")
    public List<MipdWard> byunitid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return mipdWardRepository.findAllByWardUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(unitId);

    }

    @RequestMapping("byclassidunitid/{classId}/{unitId}")
    public List<MipdWard> byclassidunitid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("classId") Long classId, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println(classId + "==>" + unitId);
        return mipdWardRepository.findAllByWardUnitIdUnitIdEqualsAndWardClassIdClassIdEqualsAndIsActiveTrueAndIsDeletedFalse(unitId, classId);

    }

    @RequestMapping("byfloorId/{floorId}")
    public List<MipdWard> findWardByFloorId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("floorId") Long floorId) {
        TenantContext.setCurrentTenant(tenantName);
        return mipdWardRepository.findByWardFloorIdFloorIdAndIsActiveTrueAndIsDeletedFalse(floorId);
    }

    @RequestMapping("getWardByFloorIdAndUnitIdd/{floorId}/{unitId}")
    public List<MipdWard> getWardByFloorIdAndUnitIdd(@RequestHeader("X-tenantId") String tenantName, @PathVariable("floorId") Long floorId, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return mipdWardRepository.findByWardFloorIdFloorIdAndWardUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(floorId, unitId);
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = mipdWardService.getMipdWardForDropdown(page, size, globalFilter);
        return items;
    }

}
            
