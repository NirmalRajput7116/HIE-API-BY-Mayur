package com.cellbeans.hspa.trnadditionaldoctor;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trn_additional_doctor")
public class TrnAdditionalDoctorController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnAdditionalDoctorRepository trnAdditionalDoctorRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnAdditionalDoctor trnAdditionalDoctor) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnAdditionalDoctor.getAdAdmissionId() != null) {
            trnAdditionalDoctorRepository.save(trnAdditionalDoctor);
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
        List<TrnAdditionalDoctor> records;
        records = trnAdditionalDoctorRepository.findByAdAdmissionIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{adId}")
    public TrnAdditionalDoctor read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("adId") Long adId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAdditionalDoctor trnAdditionalDoctor = trnAdditionalDoctorRepository.getById(adId);
        return trnAdditionalDoctor;
    }

    @RequestMapping("update")
    public TrnAdditionalDoctor update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnAdditionalDoctor trnAdditionalDoctor) {
        TenantContext.setCurrentTenant(tenantName);
        return trnAdditionalDoctorRepository.save(trnAdditionalDoctor);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TrnAdditionalDoctor> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "adId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return trnAdditionalDoctorRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return trnAdditionalDoctorRepository.findByAdAdmissionIdContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{adId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("adId") Long adId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAdditionalDoctor trnAdditionalDoctor = trnAdditionalDoctorRepository.getById(adId);
        if (trnAdditionalDoctor != null) {
            trnAdditionalDoctor.setIsDeleted(true);
            trnAdditionalDoctorRepository.save(trnAdditionalDoctor);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
