package com.cellbeans.hspa.mstpriority;

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
@RequestMapping("/mst_priority")
public class MstPriorityController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstPriorityRepository mstPriorityRepository;
    @Autowired
    private MstPriorityService mstPriorityService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstPriority mstPriority) {
        TenantContext.setCurrentTenant(tenantName);
        List<MstPriority> records = mstPriorityRepository.findByPriorityNameAndIsActiveTrueAndIsDeletedFalse(mstPriority.getPriorityName().trim().toLowerCase());
        if (records.size() != 0) {
            respMap.put("success", "0");
            respMap.put("msg", "Already Exists");
            return respMap;
        } else if (mstPriority.getPriorityName() != null) {
            mstPriorityRepository.save(mstPriority);
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
        List<MstPriority> records;
        records = mstPriorityRepository.findByPriorityNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{priorityId}")
    public MstPriority read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("priorityId") Long priorityId) {
        TenantContext.setCurrentTenant(tenantName);
        MstPriority mstPriority = mstPriorityRepository.getById(priorityId);
        return mstPriority;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstPriority mstPriority) {
        TenantContext.setCurrentTenant(tenantName);
        mstPriority.setPriorityName(mstPriority.getPriorityName().trim());
        MstPriority mstPriorityObject = mstPriorityRepository.findByPriorityNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstPriority.getPriorityName());
        if (mstPriorityObject == null) {
            mstPriorityRepository.save(mstPriority);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstPriorityObject.getPriorityId() == mstPriority.getPriorityId()) {
            mstPriorityRepository.save(mstPriority);
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
    public Iterable<MstPriority> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "priorityId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstPriorityRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByPriorityNameAsc(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstPriorityRepository.findByPriorityNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByPriorityNameAsc(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{priorityId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("priorityId") Long priorityId) {
        TenantContext.setCurrentTenant(tenantName);
        MstPriority mstPriority = mstPriorityRepository.getById(priorityId);
        if (mstPriority != null) {
            mstPriority.setIsDeleted(true);
            mstPriorityRepository.save(mstPriority);
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
        List<Tuple> items = mstPriorityService.getMstPriorityForDropdown(page, size, globalFilter);
        return items;
    }

}
            
