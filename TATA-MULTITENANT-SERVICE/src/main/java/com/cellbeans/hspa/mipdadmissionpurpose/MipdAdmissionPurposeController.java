package com.cellbeans.hspa.mipdadmissionpurpose;

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
@RequestMapping("/mipd_admission_purpose")
public class MipdAdmissionPurposeController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MipdAdmissionPurposeRepository mipdAdmissionPurposeRepository;

    @Autowired
    private MipdAdmissionPurposeService mipdAdmissionPurposeService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MipdAdmissionPurpose mipdAdmissionPurpose) {
        TenantContext.setCurrentTenant(tenantName);
        if (mipdAdmissionPurpose.getApName() != null) {
            mipdAdmissionPurposeRepository.save(mipdAdmissionPurpose);
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
        List<MipdAdmissionPurpose> records;
        records = mipdAdmissionPurposeRepository.findByApNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{apId}")
    public MipdAdmissionPurpose read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("apId") Long apId) {
        TenantContext.setCurrentTenant(tenantName);
        MipdAdmissionPurpose mipdAdmissionPurpose = mipdAdmissionPurposeRepository.getById(apId);
        return mipdAdmissionPurpose;
    }

    @RequestMapping("update")
    public MipdAdmissionPurpose update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MipdAdmissionPurpose mipdAdmissionPurpose) {
        TenantContext.setCurrentTenant(tenantName);
        return mipdAdmissionPurposeRepository.save(mipdAdmissionPurpose);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MipdAdmissionPurpose> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "apId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mipdAdmissionPurposeRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mipdAdmissionPurposeRepository.findByApNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{apId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("apId") Long apId) {
        TenantContext.setCurrentTenant(tenantName);
        MipdAdmissionPurpose mipdAdmissionPurpose = mipdAdmissionPurposeRepository.getById(apId);
        if (mipdAdmissionPurpose != null) {
            mipdAdmissionPurpose.setIsDeleted(true);
            mipdAdmissionPurposeRepository.save(mipdAdmissionPurpose);
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
        List<Tuple> items = mipdAdmissionPurposeService.getMipdAdmissionPurposeForDropdown(page, size, globalFilter);
        return items;
    }

}
            
