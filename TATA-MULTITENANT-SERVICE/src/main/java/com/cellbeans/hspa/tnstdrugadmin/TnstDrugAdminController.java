package com.cellbeans.hspa.tnstdrugadmin;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tnst_drug_admin")
public class TnstDrugAdminController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TnstDrugAdminRepository tnstDrugAdminRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TnstDrugAdmin tnstDrugAdmin) {
        TenantContext.setCurrentTenant(tenantName);
        if (tnstDrugAdmin != null) {
            tnstDrugAdminRepository.save(tnstDrugAdmin);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{daId}")
    public TnstDrugAdmin read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("daId") Long daId) {
        TenantContext.setCurrentTenant(tenantName);
        TnstDrugAdmin tnstDrugAdmin = tnstDrugAdminRepository.getById(daId);
        return tnstDrugAdmin;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TnstDrugAdmin tnstDrugAdmin) {
        TenantContext.setCurrentTenant(tenantName);
        if (tnstDrugAdmin != null) {
            tnstDrugAdminRepository.save(tnstDrugAdmin);
            respMap.put("msg", "Update Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Update Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("listByAdmissionId")
    public Iterable<TnstDrugAdmin> listByAdmissionId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) Long admissionId, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "daId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (admissionId == null || admissionId.equals("")) {
            return tnstDrugAdminRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tnstDrugAdminRepository.findByDaAdmissionIdAdmissionIdAndIsActiveTrueAndIsDeletedFalse(admissionId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{daId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("daId") Long daId) {
        TenantContext.setCurrentTenant(tenantName);
        TnstDrugAdmin tnstDrugAdmin = tnstDrugAdminRepository.getById(daId);
        if (tnstDrugAdmin != null) {
            tnstDrugAdmin.setDeleted(true);
            tnstDrugAdminRepository.save(tnstDrugAdmin);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
