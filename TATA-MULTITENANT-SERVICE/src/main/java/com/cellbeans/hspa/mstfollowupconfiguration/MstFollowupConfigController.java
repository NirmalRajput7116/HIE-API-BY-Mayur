package com.cellbeans.hspa.mstfollowupconfiguration;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mst_followup_config")
public class MstFollowupConfigController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    private MstFollowupConfigRepository mstFollowupConfigRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstFollowupConfig mstFollowupConfig) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstFollowupConfig.getFcDays() != 0) {
            mstFollowupConfigRepository.save(mstFollowupConfig);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{fcId}")
    public MstFollowupConfig read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fcId") Long fcId) {
        TenantContext.setCurrentTenant(tenantName);
        MstFollowupConfig mstFollowupConfig = mstFollowupConfigRepository.getById(fcId);
        return mstFollowupConfig;
    }

    @RequestMapping("byStaffId/{staffId}")
    public MstFollowupConfig byStaffId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("staffId") Long staffId) {
        TenantContext.setCurrentTenant(tenantName);
        MstFollowupConfig mstFollowupConfig = mstFollowupConfigRepository.findByFcStaffIdStaffIdEquals(staffId);
        return mstFollowupConfig;
    }

    @RequestMapping("update")
    public MstFollowupConfig update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstFollowupConfig mstFollowupConfig) {
        TenantContext.setCurrentTenant(tenantName);
        return mstFollowupConfigRepository.save(mstFollowupConfig);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstFollowupConfig> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "fcId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstFollowupConfigRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstFollowupConfigRepository.findByFcStaffIdStaffUserIdUserFirstnameContainsOrFcStaffIdStaffUserIdUserLastnameContainsAndIsActiveTrueAndIsDeletedFalse(qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{fcId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fcId") Long fcId) {
        TenantContext.setCurrentTenant(tenantName);
        MstFollowupConfig mstFollowupConfig = mstFollowupConfigRepository.getById(fcId);
        if (mstFollowupConfig != null) {
            mstFollowupConfig.setIsDeleted(true);
            mstFollowupConfigRepository.save(mstFollowupConfig);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
