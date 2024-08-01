package com.cellbeans.hspa.temritemprescription;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/temr_item_prescription")
public class TemrItemPrescriptionController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TemrItemPrescriptionRepository temrItemPrescriptionRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TemrItemPrescription> temrItemPrescription) {
        TenantContext.setCurrentTenant(tenantName);
        temrItemPrescriptionRepository.saveAll(temrItemPrescription);
        respMap.put("success", "1");
        respMap.put("msg", "Added Successfully");
        return respMap;
    }

    @RequestMapping("createlist")
    public Map<String, String> createlist(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TemrItemPrescription> temrItemPrescriptionList) {
        TenantContext.setCurrentTenant(tenantName);
        temrItemPrescriptionRepository.saveAll(temrItemPrescriptionList);
        respMap.put("success", "1");
        respMap.put("msg", "Added Successfully");
        return respMap;
    }
//
//    @RequestMapping("/autocomplete/{key}")
//    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
//        Map<String, Object> automap = new HashMap<String, Object>();
//        List<TemrItemPrescription> records;
//        records = temrItemPrescriptionRepository.findByPrescriptionNameContains(key);
//        automap.put("record", records);
//        return automap;
//    }

    @RequestMapping("byid/{id}")
    public TemrItemPrescription read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        TemrItemPrescription temrItemPrescription = temrItemPrescriptionRepository.getById(id);
        return temrItemPrescription;
    }
/*
    @GetMapping
    @RequestMapping("byvisitid/{visitId}")
    public Iterable<TemrItemPrescription>  byvisitid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("visitId") Long visitId) {
        return temrItemPrescriptionRepository.findAllByVpVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(visitId);

    }*/

    @RequestMapping("update")
    public TemrItemPrescription update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrItemPrescription temrItemPrescription) {
        TenantContext.setCurrentTenant(tenantName);
        return temrItemPrescriptionRepository.save(temrItemPrescription);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TemrItemPrescription> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "vpId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return temrItemPrescriptionRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

    }

    @PutMapping("delete/{id}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        TemrItemPrescription temrItemPrescription = temrItemPrescriptionRepository.getById(id);
        if (temrItemPrescription != null) {
            temrItemPrescription.setDeleted(true);
            temrItemPrescriptionRepository.save(temrItemPrescription);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
