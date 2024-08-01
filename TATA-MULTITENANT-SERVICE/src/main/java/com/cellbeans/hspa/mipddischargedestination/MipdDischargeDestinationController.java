package com.cellbeans.hspa.mipddischargedestination;

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
@RequestMapping("/mipd_discharge_destination")
public class MipdDischargeDestinationController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MipdDischargeDestinationRepository mipdDischargeDestinationRepository;

    @Autowired
    private MipdDischargeDestinationService mipdDischargeDestinationService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MipdDischargeDestination mipdDischargeDestination) {
        TenantContext.setCurrentTenant(tenantName);
        if (mipdDischargeDestination.getDdName() != null) {
            mipdDischargeDestinationRepository.save(mipdDischargeDestination);
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
        List<MipdDischargeDestination> records;
        records = mipdDischargeDestinationRepository.findByDdNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{ddId}")
    public MipdDischargeDestination read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ddId") Long ddId) {
        TenantContext.setCurrentTenant(tenantName);
        MipdDischargeDestination mipdDischargeDestination = mipdDischargeDestinationRepository.getById(ddId);
        return mipdDischargeDestination;
    }

    @RequestMapping("update")
    public MipdDischargeDestination update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MipdDischargeDestination mipdDischargeDestination) {
        TenantContext.setCurrentTenant(tenantName);
        return mipdDischargeDestinationRepository.save(mipdDischargeDestination);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MipdDischargeDestination> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ddId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mipdDischargeDestinationRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mipdDischargeDestinationRepository.findByDdNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{ddId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ddId") Long ddId) {
        TenantContext.setCurrentTenant(tenantName);
        MipdDischargeDestination mipdDischargeDestination = mipdDischargeDestinationRepository.getById(ddId);
        if (mipdDischargeDestination != null) {
            mipdDischargeDestination.setIsDeleted(true);
            mipdDischargeDestinationRepository.save(mipdDischargeDestination);
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
        List<Tuple> items = mipdDischargeDestinationService.getMipdDischargeDestinationForDropdown(page, size, globalFilter);
        return items;
    }

}
            
