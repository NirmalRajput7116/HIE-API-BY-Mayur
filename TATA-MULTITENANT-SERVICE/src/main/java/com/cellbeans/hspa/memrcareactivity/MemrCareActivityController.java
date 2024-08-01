package com.cellbeans.hspa.memrcareactivity;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/memr_careactivity")
public class MemrCareActivityController {
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MemrCareActivityRepository memrCareActivityRepository;

    @Autowired
    MemrCareActivityService memrCareActivityService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrCareActivity memrCareActivity) {
        TenantContext.setCurrentTenant(tenantName);
        if (memrCareActivity.getCareActivityName() != null) {
            memrCareActivity.setCareActivityName(memrCareActivity.getCareActivityName().trim());
            MemrCareActivity memrCareActivityObject = memrCareActivityRepository.findByCareActivityNameEqualsAndIsActiveTrueAndIsDeletedFalse(memrCareActivity.getCareActivityName());
            if (memrCareActivityObject == null) {
                memrCareActivityRepository.save(memrCareActivity);
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
        List<MemrCareActivity> records;
        records = memrCareActivityRepository.findByCareActivityNameContainsAndIsActiveTrueAndIsDeletedFalse(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{bloodgroupId}")
    public MemrCareActivity read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bloodgroupId") Long bloodgroupId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrCareActivity memrCareActivity = memrCareActivityRepository.getById(bloodgroupId);
        return memrCareActivity;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrCareActivity memrCareActivity) {
        TenantContext.setCurrentTenant(tenantName);
        memrCareActivity.setCareActivityName(memrCareActivity.getCareActivityName().trim());
        MemrCareActivity memrCareActivityObject = memrCareActivityRepository.findByCareActivityNameEqualsAndIsActiveTrueAndIsDeletedFalse(memrCareActivity.getCareActivityName());
        if (memrCareActivityObject == null) {
            memrCareActivityRepository.save(memrCareActivity);
            respMap.put("success", "1");
            respMap.put("msg", "updated Successfully");
            return respMap;
        } else if (memrCareActivityObject.getCareActivityId() == memrCareActivity.getCareActivityId()) {
            memrCareActivityRepository.save(memrCareActivity);
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
    public Iterable<MemrCareActivity> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "careActivityId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return memrCareActivityRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByCareActivityName(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return memrCareActivityRepository.findByCareActivityNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByCareActivityName(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @PutMapping("delete/{bloodgroupId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bloodgroupId") Long bloodgroupId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrCareActivity memrCareActivity = memrCareActivityRepository.getById(bloodgroupId);
        if (memrCareActivity != null) {
            memrCareActivity.setIsDeleted(true);
            memrCareActivityRepository.save(memrCareActivity);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }
}
