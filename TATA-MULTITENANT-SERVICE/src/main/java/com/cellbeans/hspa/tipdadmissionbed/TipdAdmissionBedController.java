package com.cellbeans.hspa.tipdadmissionbed;

import com.cellbeans.hspa.TenantContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tipd_admission_bed")
public class TipdAdmissionBedController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TipdAdmissionBedRepository tipdAdmissionBedRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TipdAdmissionBed tipdAdmissionBed) {
        TenantContext.setCurrentTenant(tenantName);
        if (tipdAdmissionBed.getAbAdmissionId() != null) {
            tipdAdmissionBedRepository.save(tipdAdmissionBed);
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
        List<TipdAdmissionBed> records;
        records = tipdAdmissionBedRepository.findByAbAdmissionIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{abId}")
    public TipdAdmissionBed read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("abId") Long abId) {
        TenantContext.setCurrentTenant(tenantName);
        TipdAdmissionBed tipdAdmissionBed = tipdAdmissionBedRepository.getById(abId);
        return tipdAdmissionBed;
    }

    @RequestMapping("update")
    public TipdAdmissionBed update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TipdAdmissionBed tipdAdmissionBed) {
        TenantContext.setCurrentTenant(tenantName);
        return tipdAdmissionBedRepository.save(tipdAdmissionBed);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TipdAdmissionBed> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "abId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tipdAdmissionBedRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tipdAdmissionBedRepository.findByAbAdmissionIdContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{abId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("abId") Long abId) {
        TenantContext.setCurrentTenant(tenantName);
        TipdAdmissionBed tipdAdmissionBed = tipdAdmissionBedRepository.getById(abId);
        if (tipdAdmissionBed != null) {
            tipdAdmissionBed.setIsDeleted(true);
            tipdAdmissionBedRepository.save(tipdAdmissionBed);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
