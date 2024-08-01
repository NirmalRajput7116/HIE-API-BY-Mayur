package com.cellbeans.hspa.memrchiefcomplaint;

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
@RequestMapping("/memr_chief_complaint")
public class MemrChiefComplaintController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MemrChiefComplaintRepository memrChiefComplaintRepository;

    @Autowired
    private MemrChiefComplaintService memrChiefComplaintService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrChiefComplaint memrChiefComplaint) {
        TenantContext.setCurrentTenant(tenantName);
        MemrChiefComplaint memrCC = memrChiefComplaintRepository.findByCcNameAndIsActiveTrueAndIsDeletedFalse(memrChiefComplaint.getCcName());
        if (memrCC != null) {
            respMap.put("success", "3");
            respMap.put("msg", "Chief Complaint - '" + memrChiefComplaint.getCcName() + "' already Present");
            return respMap;
        } else {
            if (memrChiefComplaint.getCcName() != null) {
                memrChiefComplaintRepository.save(memrChiefComplaint);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Failed To Add Null Field");
                return respMap;
            }

        }
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MemrChiefComplaint> records;
        records = memrChiefComplaintRepository.findByCcNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{ccId}")
    public MemrChiefComplaint read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ccId") Long ccId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrChiefComplaint memrChiefComplaint = memrChiefComplaintRepository.getById(ccId);
        return memrChiefComplaint;
    }

    @RequestMapping("update")
  /*  public MemrChiefComplaint update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrChiefComplaint memrChiefComplaint) {
        return memrChiefComplaintRepository.save(memrChiefComplaint);
    }*/
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrChiefComplaint memrChiefComplaint) {
        TenantContext.setCurrentTenant(tenantName);
        memrChiefComplaint.setCcName(memrChiefComplaint.getCcName().trim());
        MemrChiefComplaint memrCC = memrChiefComplaintRepository.findByCcNameAndIsActiveTrueAndIsDeletedFalse(memrChiefComplaint.getCcName());
        if (memrCC == null) {
            memrChiefComplaintRepository.save(memrChiefComplaint);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
        } else if (memrCC.getCcId() == memrChiefComplaint.getCcId()) {
            memrChiefComplaintRepository.save(memrChiefComplaint);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Chief Complaint - '" + memrChiefComplaint.getCcName() + "' already Present");
        }
        return respMap;

    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MemrChiefComplaint> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ccId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            // System.out.println("qString : "+qString);
            return memrChiefComplaintRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return memrChiefComplaintRepository.findByCcNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @GetMapping
    @RequestMapping("search")
    public Map<String, Object> search(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qString", required = false) String qString) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> searchmap = new HashMap<String, Object>();
        List<MemrChiefComplaint> records;
        records = memrChiefComplaintRepository.findByCcNameContains(qString);
        searchmap.put("items", records);
        return searchmap;

    }

    @PutMapping("delete/{ccId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ccId") Long ccId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrChiefComplaint memrChiefComplaint = memrChiefComplaintRepository.getById(ccId);
        if (memrChiefComplaint != null) {
            memrChiefComplaint.setIsDeleted(true);
            memrChiefComplaintRepository.save(memrChiefComplaint);
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
        List<Tuple> items = memrChiefComplaintService.getMemrChiefComplaintForDropdown(page, size, globalFilter);
        return items;
    }

}
            
