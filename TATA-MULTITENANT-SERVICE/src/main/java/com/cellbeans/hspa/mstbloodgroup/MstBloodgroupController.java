package com.cellbeans.hspa.mstbloodgroup;

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
@RequestMapping("/mst_bloodgroup")
public class MstBloodgroupController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstBloodgroupRepository mstBloodgroupRepository;

    @Autowired
    private MstBloodgroupService mstBloodgroupService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstBloodgroup mstBloodgroup) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstBloodgroup.getBloodgroupName() != null) {
            mstBloodgroup.setBloodgroupName(mstBloodgroup.getBloodgroupName().trim());
            MstBloodgroup mstBloodgroupObject = mstBloodgroupRepository.findByBloodgroupNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstBloodgroup.getBloodgroupName());
            if (mstBloodgroupObject == null) {
                mstBloodgroupRepository.save(mstBloodgroup);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "2");
                respMap.put("msg", "Already Added");
                return respMap;
            }
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
        List<MstBloodgroup> records;
        records = mstBloodgroupRepository.findByBloodgroupNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{bloodgroupId}")
    public MstBloodgroup read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bloodgroupId") Long bloodgroupId) {
        TenantContext.setCurrentTenant(tenantName);
        MstBloodgroup mstBloodgroup = mstBloodgroupRepository.getById(bloodgroupId);
        return mstBloodgroup;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstBloodgroup mstBloodgroup) {
        TenantContext.setCurrentTenant(tenantName);
        /*  return mstBloodgroupRepository.save(mstBloodgroup);*/
        mstBloodgroup.setBloodgroupName(mstBloodgroup.getBloodgroupName().trim());
        MstBloodgroup mstBloodgroupObject = mstBloodgroupRepository.findByBloodgroupNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstBloodgroup.getBloodgroupName());
        if (mstBloodgroupObject == null) {
            mstBloodgroupRepository.save(mstBloodgroup);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstBloodgroupObject.getBloodgroupId() == mstBloodgroup.getBloodgroupId()) {
            mstBloodgroupRepository.save(mstBloodgroup);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else {
            respMap.put("success", "2");
            respMap.put("msg", "Already Added");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstBloodgroup> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bloodgroupId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstBloodgroupRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByBloodgroupName(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstBloodgroupRepository.findByBloodgroupNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByBloodgroupName(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{bloodgroupId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bloodgroupId") Long bloodgroupId) {
        TenantContext.setCurrentTenant(tenantName);
        MstBloodgroup mstBloodgroup = mstBloodgroupRepository.getById(bloodgroupId);
        if (mstBloodgroup != null) {
            mstBloodgroup.setIsDeleted(true);
            mstBloodgroupRepository.save(mstBloodgroup);
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
        List<Tuple> items = mstBloodgroupService.getMstBloodgroupForDropdown(page, size, globalFilter);
        return items;
    }

}
            
