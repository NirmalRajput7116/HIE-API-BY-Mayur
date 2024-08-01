package com.cellbeans.hspa.mpathsamplerejection;

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
@RequestMapping("/mpath_sample_rejection")
public class MpathSampleRejectionController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MpathSampleRejectionRepository mpathSampleRejectionRepository;
    @Autowired
    private MpathSampleRejectionService mpathSampleRejectionService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathSampleRejection mpathSampleRejection) {
        TenantContext.setCurrentTenant(tenantName);
        if (mpathSampleRejection.getSrReason() != null) {
            mpathSampleRejectionRepository.save(mpathSampleRejection);
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
        List<MpathSampleRejection> records;
        records = mpathSampleRejectionRepository.findBySrReasonContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{srId}")
    public MpathSampleRejection read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("srId") Long srId) {
        TenantContext.setCurrentTenant(tenantName);
        MpathSampleRejection mpathSampleRejection = mpathSampleRejectionRepository.getById(srId);
        return mpathSampleRejection;
    }

    @RequestMapping("update")
    public MpathSampleRejection update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathSampleRejection mpathSampleRejection) {
        TenantContext.setCurrentTenant(tenantName);
        return mpathSampleRejectionRepository.save(mpathSampleRejection);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MpathSampleRejection> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "srId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("rejection qString :"+qString);
        if (qString == null || qString.equals("")) {
            return mpathSampleRejectionRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }
        if (qString.equals("dropdown")) {
            return mpathSampleRejectionRepository.findAllByIsActiveTrueAndIsDeletedFalse();
        } else {
            return mpathSampleRejectionRepository.findBySrReasonContainsAndIsActiveTrueAndIsDeletedFalseOrSrCodeContainsAndIsActiveTrueAndIsDeletedFalse(qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{srId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("srId") Long srId) {
        TenantContext.setCurrentTenant(tenantName);
        MpathSampleRejection mpathSampleRejection = mpathSampleRejectionRepository.getById(srId);
        if (mpathSampleRejection != null) {
            mpathSampleRejection.setIsDeleted(true);
            mpathSampleRejectionRepository.save(mpathSampleRejection);
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
        List<Tuple> items = mpathSampleRejectionService.getMpathSampleRejectionForDropdown(page, size, globalFilter);
        return items;
    }

}
            
