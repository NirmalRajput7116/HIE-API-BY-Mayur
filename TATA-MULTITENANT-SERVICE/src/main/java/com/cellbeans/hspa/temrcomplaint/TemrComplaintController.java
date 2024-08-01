package com.cellbeans.hspa.temrcomplaint;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/temr_complaint")
public class TemrComplaintController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TemrComplaintRepository temrComplaintRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrComplaint temrComplaint) {
        TenantContext.setCurrentTenant(tenantName);
        if (temrComplaint != null) {
            temrComplaintRepository.save(temrComplaint);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

/*    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        Map<String, Object> automap = new HashMap<String, Object>();
        Map<String, Object> automap = new HashMap<String, Object>();
        List<TemrComplaint> records;
        records = temrComplaintRepository.findByComplaintComplaintIdComplaintNameContains(key);
        automap.put("record", records);
        return automap;
    }*/

    @RequestMapping("byid/{complaintId}")
    public TemrComplaint read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("complaintId") Long complaintId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrComplaint temrComplaint = temrComplaintRepository.getById(complaintId);
        return temrComplaint;
    }

    @RequestMapping("update")
    public TemrComplaint update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrComplaint temrComplaint) {
        TenantContext.setCurrentTenant(tenantName);
        return temrComplaintRepository.save(temrComplaint);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TemrComplaint> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "complaintId") String col) {
        TenantContext.setCurrentTenant(tenantName);
//        if (qString == null || qString.equals("")) {
        return temrComplaintRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//        } else {
//
//      /*      return temrComplaintRepository.findByComplaintComplaintIdComplaintNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));*/
//
//            return null;
//        }
    }

    @PutMapping("delete/{complaintId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("complaintId") Long complaintId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrComplaint temrComplaint = temrComplaintRepository.getById(complaintId);
        if (temrComplaint != null) {
            temrComplaint.setDeleted(true);
            temrComplaintRepository.save(temrComplaint);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
