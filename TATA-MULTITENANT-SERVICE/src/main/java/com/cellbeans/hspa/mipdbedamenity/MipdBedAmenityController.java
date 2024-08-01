package com.cellbeans.hspa.mipdbedamenity;

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
@RequestMapping("/mipd_bed_amenity")
public class MipdBedAmenityController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MipdBedAmenityRepository mipdBedAmenityRepository;

    @Autowired
    private MipdBedAmenityService mipdBedAmenityService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MipdBedAmenity mipdBedAmenity) {
        TenantContext.setCurrentTenant(tenantName);
        if (mipdBedAmenity.getBaName() != null) {
            mipdBedAmenityRepository.save(mipdBedAmenity);
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
        List<MipdBedAmenity> records;
        records = mipdBedAmenityRepository.findByBaNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{baId}")
    public MipdBedAmenity read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("baId") Long baId) {
        TenantContext.setCurrentTenant(tenantName);
        MipdBedAmenity mipdBedAmenity = mipdBedAmenityRepository.getById(baId);
        return mipdBedAmenity;
    }

    @RequestMapping("update")
    public MipdBedAmenity update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MipdBedAmenity mipdBedAmenity) {
        TenantContext.setCurrentTenant(tenantName);
        return mipdBedAmenityRepository.save(mipdBedAmenity);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MipdBedAmenity> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "baId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mipdBedAmenityRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mipdBedAmenityRepository.findByBaNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{baId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("baId") Long baId) {
        TenantContext.setCurrentTenant(tenantName);
        MipdBedAmenity mipdBedAmenity = mipdBedAmenityRepository.getById(baId);
        if (mipdBedAmenity != null) {
            mipdBedAmenity.setIsDeleted(true);
            mipdBedAmenityRepository.save(mipdBedAmenity);
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
        List<Tuple> items = mipdBedAmenityService.getMipdBedAmenityForDropdown(page, size, globalFilter);
        return items;
    }

}
            
