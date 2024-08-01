package com.cellbeans.hspa.trnadmissionnextkindetails;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trn_admission_next_kin_details")
public class TrnAdmissionNextKinDetailsController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnAdmissionNextKinDetailsRepository trnAdmissionNextKinDetailsRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnAdmissionNextKinDetails trnAdmissionNextKinDetails) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnAdmissionNextKinDetails.getAnkdFirstname() != null) {
            trnAdmissionNextKinDetailsRepository.save(trnAdmissionNextKinDetails);
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
        List<TrnAdmissionNextKinDetails> records;
        records = trnAdmissionNextKinDetailsRepository.findByAnkdFirstnameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{ankdId}")
    public TrnAdmissionNextKinDetails read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ankdId") Long ankdId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAdmissionNextKinDetails trnAdmissionNextKinDetails = trnAdmissionNextKinDetailsRepository.getById(ankdId);
        return trnAdmissionNextKinDetails;
    }

    @RequestMapping("update")
    public TrnAdmissionNextKinDetails update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnAdmissionNextKinDetails trnAdmissionNextKinDetails) {
        TenantContext.setCurrentTenant(tenantName);
        return trnAdmissionNextKinDetailsRepository.save(trnAdmissionNextKinDetails);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TrnAdmissionNextKinDetails> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ankdId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return trnAdmissionNextKinDetailsRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return trnAdmissionNextKinDetailsRepository.findByAnkdFirstnameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{ankdId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ankdId") Long ankdId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAdmissionNextKinDetails trnAdmissionNextKinDetails = trnAdmissionNextKinDetailsRepository.getById(ankdId);
        if (trnAdmissionNextKinDetails != null) {
            trnAdmissionNextKinDetails.setIsDeleted(true);
            trnAdmissionNextKinDetailsRepository.save(trnAdmissionNextKinDetails);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
