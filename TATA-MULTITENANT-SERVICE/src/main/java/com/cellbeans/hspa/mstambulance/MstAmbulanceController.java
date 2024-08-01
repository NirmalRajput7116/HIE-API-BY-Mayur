package com.cellbeans.hspa.mstambulance;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_ambulance")
public class MstAmbulanceController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstAmbulanceRepository mstAmbulanceRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstAmbulance mstAmbulance) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstAmbulance.getAmbulanceNumber() != null) {
            mstAmbulanceRepository.save(mstAmbulance);
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
        List<MstAmbulance> records;
        records = mstAmbulanceRepository.findByAmbulanceNumberContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{ambulanceId}")
    public MstAmbulance read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ambulanceId") Long ambulanceId) {
        TenantContext.setCurrentTenant(tenantName);
        MstAmbulance mstAmbulance = mstAmbulanceRepository.getById(ambulanceId);
        return mstAmbulance;
    }

    @RequestMapping("update")
    public MstAmbulance update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstAmbulance mstAmbulance) {
        TenantContext.setCurrentTenant(tenantName);
        return mstAmbulanceRepository.save(mstAmbulance);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstAmbulance> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ambulanceId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstAmbulanceRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstAmbulanceRepository.findByAmbulanceNumberContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{ambulanceId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ambulanceId") Long ambulanceId) {
        TenantContext.setCurrentTenant(tenantName);
        MstAmbulance mstAmbulance = mstAmbulanceRepository.getById(ambulanceId);
        if (mstAmbulance != null) {
            mstAmbulance.setIsDeleted(true);
            mstAmbulanceRepository.save(mstAmbulance);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
