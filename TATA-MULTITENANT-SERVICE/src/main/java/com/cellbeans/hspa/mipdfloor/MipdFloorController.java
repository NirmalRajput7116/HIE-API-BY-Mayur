package com.cellbeans.hspa.mipdfloor;

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
@RequestMapping("/mipd_floor")
public class MipdFloorController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MipdFloorRepository mipdFloorRepository;

    @Autowired
    private MipdFloorService mipdFloorService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MipdFloor mipdFloor) {
        TenantContext.setCurrentTenant(tenantName);
        if (mipdFloor.getFloorName() != null) {
            mipdFloorRepository.save(mipdFloor);
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
        List<MipdFloor> records;
        records = mipdFloorRepository.findByFloorNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{floorId}")
    public MipdFloor read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("floorId") Long floorId) {
        TenantContext.setCurrentTenant(tenantName);
        MipdFloor mipdFloor = mipdFloorRepository.getById(floorId);
        return mipdFloor;
    }

    @RequestMapping("update")
    public MipdFloor update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MipdFloor mipdFloor) {
        TenantContext.setCurrentTenant(tenantName);
        return mipdFloorRepository.save(mipdFloor);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MipdFloor> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "floorId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mipdFloorRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mipdFloorRepository.findByFloorNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{floorId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("floorId") Long floorId) {
        TenantContext.setCurrentTenant(tenantName);
        MipdFloor mipdFloor = mipdFloorRepository.getById(floorId);
        if (mipdFloor != null) {
            mipdFloor.setIsDeleted(true);
            mipdFloorRepository.save(mipdFloor);
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
        List<Tuple> items = mipdFloorService.getMipdFloorForDropdown(page, size, globalFilter);
        return items;
    }

}
            