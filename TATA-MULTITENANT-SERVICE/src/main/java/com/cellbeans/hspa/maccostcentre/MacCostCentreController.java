package com.cellbeans.hspa.maccostcentre;

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
@RequestMapping("/mac_cost_centre")
public class MacCostCentreController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MacCostCentreRepository macCostCentreRepository;

    @Autowired
    private MacCostCentreService macCostCentreService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MacCostCentre macCostCentre) {
        TenantContext.setCurrentTenant(tenantName);
        if (macCostCentre.getCcName() != null) {
            macCostCentreRepository.save(macCostCentre);
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
        List<MacCostCentre> records;
        records = macCostCentreRepository.findByCcNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{ccId}")
    public MacCostCentre read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ccId") Long ccId) {
        TenantContext.setCurrentTenant(tenantName);
        MacCostCentre macCostCentre = macCostCentreRepository.getById(ccId);
        return macCostCentre;
    }

    @RequestMapping("update")
    public MacCostCentre update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MacCostCentre macCostCentre) {
        TenantContext.setCurrentTenant(tenantName);
        return macCostCentreRepository.save(macCostCentre);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MacCostCentre> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ccId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return macCostCentreRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return macCostCentreRepository.findByCcNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{ccId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ccId") Long ccId) {
        TenantContext.setCurrentTenant(tenantName);
        MacCostCentre macCostCentre = macCostCentreRepository.getById(ccId);
        if (macCostCentre != null) {
            macCostCentre.setIsDeleted(true);
            macCostCentreRepository.save(macCostCentre);
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
        List<Tuple> items = macCostCentreService.getMacCostCentreForDropdown(page, size, globalFilter);
        return items;
    }

}
            
