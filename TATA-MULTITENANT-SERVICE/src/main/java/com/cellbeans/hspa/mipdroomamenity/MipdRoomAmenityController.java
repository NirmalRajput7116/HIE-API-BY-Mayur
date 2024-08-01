package com.cellbeans.hspa.mipdroomamenity;

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
@RequestMapping("/mipd_room_amenity")
public class MipdRoomAmenityController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MipdRoomAmenityRepository mipdRoomAmenityRepository;

    @Autowired
    private MipdRoomAmenityService mipdRoomAmenityService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MipdRoomAmenity mipdRoomAmenity) {
        TenantContext.setCurrentTenant(tenantName);
        if (mipdRoomAmenity.getRaName() != null) {
            mipdRoomAmenityRepository.save(mipdRoomAmenity);
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
        List<MipdRoomAmenity> records;
        records = mipdRoomAmenityRepository.findByRaNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{raId}")
    public MipdRoomAmenity read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("raId") Long raId) {
        TenantContext.setCurrentTenant(tenantName);
        MipdRoomAmenity mipdRoomAmenity = mipdRoomAmenityRepository.getById(raId);
        return mipdRoomAmenity;
    }

    @RequestMapping("update")
    public MipdRoomAmenity update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MipdRoomAmenity mipdRoomAmenity) {
        TenantContext.setCurrentTenant(tenantName);
        return mipdRoomAmenityRepository.save(mipdRoomAmenity);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MipdRoomAmenity> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "raId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mipdRoomAmenityRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mipdRoomAmenityRepository.findByRaNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{raId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("raId") Long raId) {
        TenantContext.setCurrentTenant(tenantName);
        MipdRoomAmenity mipdRoomAmenity = mipdRoomAmenityRepository.getById(raId);
        if (mipdRoomAmenity != null) {
            mipdRoomAmenity.setIsDeleted(true);
            mipdRoomAmenityRepository.save(mipdRoomAmenity);
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
        List<Tuple> items = mipdRoomAmenityService.getMipdRoomAmenityForDropdown(page, size, globalFilter);
        return items;
    }

}
            
