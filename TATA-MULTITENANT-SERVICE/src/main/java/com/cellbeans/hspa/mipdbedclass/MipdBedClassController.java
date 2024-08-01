package com.cellbeans.hspa.mipdbedclass;

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
@RequestMapping("/mipd_bed_class")
public class MipdBedClassController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MipdBedClassRepository mipdBedClassRepository;

    @Autowired
    private MipdBedClassService mipdBedClassService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MipdBedClass mipdBedClass) {
        TenantContext.setCurrentTenant(tenantName);
        if (mipdBedClass.getBcName() != null) {
            mipdBedClassRepository.save(mipdBedClass);
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
        List<MipdBedClass> records;
        records = mipdBedClassRepository.findByBcNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{bcId}")
    public MipdBedClass read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bcId") Long bcId) {
        TenantContext.setCurrentTenant(tenantName);
        MipdBedClass mipdBedClass = mipdBedClassRepository.getById(bcId);
        return mipdBedClass;
    }

    @RequestMapping("update")
    public MipdBedClass update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MipdBedClass mipdBedClass) {
        TenantContext.setCurrentTenant(tenantName);
        return mipdBedClassRepository.save(mipdBedClass);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MipdBedClass> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bcId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mipdBedClassRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mipdBedClassRepository.findByBcNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{bcId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bcId") Long bcId) {
        TenantContext.setCurrentTenant(tenantName);
        MipdBedClass mipdBedClass = mipdBedClassRepository.getById(bcId);
        if (mipdBedClass != null) {
            mipdBedClass.setIsDeleted(true);
            mipdBedClassRepository.save(mipdBedClass);
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
        List<Tuple> items = mipdBedClassService.getMipdBedClassForDropdown(page, size, globalFilter);
        return items;
    }

}
            
