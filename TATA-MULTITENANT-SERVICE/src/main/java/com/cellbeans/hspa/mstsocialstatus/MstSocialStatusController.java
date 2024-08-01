package com.cellbeans.hspa.mstsocialstatus;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_social_status")
public class MstSocialStatusController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstSocialStatusRepository mstSocialStatusRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstSocialStatus mstSocialStatus) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstSocialStatus.getSocialstatusName() != null) {
            mstSocialStatusRepository.save(mstSocialStatus);
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
        List<MstSocialStatus> records;
        records = mstSocialStatusRepository.findBySocialstatusNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{socialstatusId}")
    public MstSocialStatus read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("socialstatusId") Long socialstatusId) {
        TenantContext.setCurrentTenant(tenantName);
        MstSocialStatus mstSocialStatus = mstSocialStatusRepository.getById(socialstatusId);
        return mstSocialStatus;
    }

    @RequestMapping("update")
    public MstSocialStatus update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstSocialStatus mstSocialStatus) {
        TenantContext.setCurrentTenant(tenantName);
        return mstSocialStatusRepository.save(mstSocialStatus);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstSocialStatus> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "socialstatusId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstSocialStatusRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstSocialStatusRepository.findBySocialstatusNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{socialstatusId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("socialstatusId") Long socialstatusId) {
        TenantContext.setCurrentTenant(tenantName);
        MstSocialStatus mstSocialStatus = mstSocialStatusRepository.getById(socialstatusId);
        if (mstSocialStatus != null) {
            mstSocialStatus.setIsDeleted(true);
            mstSocialStatusRepository.save(mstSocialStatus);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
