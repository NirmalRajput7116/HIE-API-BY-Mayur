package com.cellbeans.hspa.mipdautocharge;

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
@RequestMapping("/mipd_auto_charge")
public class MipdAutoChargeController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MipdAutoChargeRepository mipdAutoChargeRepository;

    @Autowired
    private MipdAutoChargeService mipdAutoChargeService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MipdAutoCharge mipdAutoCharge) {
        TenantContext.setCurrentTenant(tenantName);
        if (mipdAutoCharge.getAtName() != null) {
            mipdAutoChargeRepository.save(mipdAutoCharge);
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
        List<MipdAutoCharge> records;
        records = mipdAutoChargeRepository.findByAtNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{atId}")
    public MipdAutoCharge read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("atId") Long atId) {
        TenantContext.setCurrentTenant(tenantName);
        MipdAutoCharge mipdAutoCharge = mipdAutoChargeRepository.getById(atId);
        return mipdAutoCharge;
    }

    @RequestMapping("update")
    public MipdAutoCharge update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MipdAutoCharge mipdAutoCharge) {
        TenantContext.setCurrentTenant(tenantName);
        return mipdAutoChargeRepository.save(mipdAutoCharge);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MipdAutoCharge> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "atId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mipdAutoChargeRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mipdAutoChargeRepository.findByAtNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{atId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("atId") Long atId) {
        TenantContext.setCurrentTenant(tenantName);
        MipdAutoCharge mipdAutoCharge = mipdAutoChargeRepository.getById(atId);
        if (mipdAutoCharge != null) {
            mipdAutoCharge.setIsDeleted(true);
            mipdAutoChargeRepository.save(mipdAutoCharge);
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
        List<Tuple> items = mipdAutoChargeService.getMipdAutoChargeForDropdown(page, size, globalFilter);
        return items;
    }

}
            
