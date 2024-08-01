package com.cellbeans.hspa.trnadmissionemergencycontact;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trn_admission_emergency_contact")
public class TrnAdmissionEmergencyContactController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnAdmissionEmergencyContactRepository trnAdmissionEmergencyContactRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnAdmissionEmergencyContact trnAdmissionEmergencyContact) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnAdmissionEmergencyContact.getAecFirstname() != null) {
            trnAdmissionEmergencyContactRepository.save(trnAdmissionEmergencyContact);
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
        List<TrnAdmissionEmergencyContact> records;
        records = trnAdmissionEmergencyContactRepository.findByAecFirstnameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{aecId}")
    public TrnAdmissionEmergencyContact read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("aecId") Long aecId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAdmissionEmergencyContact trnAdmissionEmergencyContact = trnAdmissionEmergencyContactRepository.getById(aecId);
        return trnAdmissionEmergencyContact;
    }

    @RequestMapping("update")
    public TrnAdmissionEmergencyContact update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnAdmissionEmergencyContact trnAdmissionEmergencyContact) {
        TenantContext.setCurrentTenant(tenantName);
        return trnAdmissionEmergencyContactRepository.save(trnAdmissionEmergencyContact);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TrnAdmissionEmergencyContact> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "aecId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return trnAdmissionEmergencyContactRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return trnAdmissionEmergencyContactRepository.findByAecFirstnameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{aecId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("aecId") Long aecId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAdmissionEmergencyContact trnAdmissionEmergencyContact = trnAdmissionEmergencyContactRepository.getById(aecId);
        if (trnAdmissionEmergencyContact != null) {
            trnAdmissionEmergencyContact.setIsDeleted(true);
            trnAdmissionEmergencyContactRepository.save(trnAdmissionEmergencyContact);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
