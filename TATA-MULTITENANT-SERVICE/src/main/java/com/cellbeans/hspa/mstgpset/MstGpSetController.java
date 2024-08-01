package com.cellbeans.hspa.mstgpset;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_gp_set")
public class MstGpSetController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstGpSetRepository mstGpSetRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstGpSet mstGpSet) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstGpSet.getGpSetName() != null) {
            mstGpSetRepository.save(mstGpSet);
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
        List<MstGpSet> records;
        records = mstGpSetRepository.findByGpSetNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{gpSetId}")
    public MstGpSet read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("gpSetId") Long gpSetId) {
        TenantContext.setCurrentTenant(tenantName);
        MstGpSet mstGpSet = mstGpSetRepository.getById(gpSetId);
        return mstGpSet;
    }

    @RequestMapping("update")
    public MstGpSet update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstGpSet mstGpSet) {
        TenantContext.setCurrentTenant(tenantName);
        return mstGpSetRepository.save(mstGpSet);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstGpSet> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "gpSetId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstGpSetRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstGpSetRepository.findByGpSetNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{gpSetId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("gpSetId") Long gpSetId) {
        TenantContext.setCurrentTenant(tenantName);
        MstGpSet mstGpSet = mstGpSetRepository.getById(gpSetId);
        if (mstGpSet != null) {
            mstGpSet.setIsDeleted(true);
            mstGpSetRepository.save(mstGpSet);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

/*
    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        List<Tuple> items = mstDayService.getMstDayForDropdown(page, size, globalFilter);
        return items;
    }
*/
}
