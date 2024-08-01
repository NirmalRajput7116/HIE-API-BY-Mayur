package com.cellbeans.hspa.mstambulancerequisition;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_ambulancerequisition")
public class MstAmbulancerequisitionController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstAmbulancerequisitionRepository mstAmbulancerequisitionRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstAmbulancerequisition mstAmbulancerequisition) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstAmbulancerequisition.getApplicantName() != null) {
            mstAmbulancerequisitionRepository.save(mstAmbulancerequisition);
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
        List<MstAmbulancerequisition> records;
        records = mstAmbulancerequisitionRepository.findByApplicantNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{ambulanceRequisitionId}")
    public MstAmbulancerequisition read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ambulanceRequisitionId") Long ambulanceRequisitionId) {
        TenantContext.setCurrentTenant(tenantName);
        MstAmbulancerequisition mstAmbulancerequisition = mstAmbulancerequisitionRepository.getById(ambulanceRequisitionId);
        return mstAmbulancerequisition;
    }

    @RequestMapping("update")
    public MstAmbulancerequisition update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstAmbulancerequisition mstAmbulancerequisition) {
        TenantContext.setCurrentTenant(tenantName);
        return mstAmbulancerequisitionRepository.save(mstAmbulancerequisition);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstAmbulancerequisition> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ambulanceRequisitionId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstAmbulancerequisitionRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstAmbulancerequisitionRepository.findByApplicantNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{ambulanceRequisitionId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ambulanceRequisitionId") Long ambulanceRequisitionId) {
        TenantContext.setCurrentTenant(tenantName);
        MstAmbulancerequisition mstAmbulancerequisition = mstAmbulancerequisitionRepository.getById(ambulanceRequisitionId);
        if (mstAmbulancerequisition != null) {
            mstAmbulancerequisition.setIsDeleted(true);
            mstAmbulancerequisitionRepository.save(mstAmbulancerequisition);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
