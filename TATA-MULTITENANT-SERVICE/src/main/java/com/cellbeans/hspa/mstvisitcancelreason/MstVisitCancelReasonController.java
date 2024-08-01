package com.cellbeans.hspa.mstvisitcancelreason;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_visitcancelreason")
public class MstVisitCancelReasonController {
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstVisitCancelReasonRepository mstVisitCancelReasonRepository;

    @Autowired
    MstVisitCancelReasonService mstVisitCancelReasonService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstVisitCancelReason mstVisitCancelReason) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstVisitCancelReason.getVisitCancelreasonName() != null) {
            mstVisitCancelReason.setVisitcancelreasonName(mstVisitCancelReason.getVisitCancelreasonName().trim());
            MstVisitCancelReason mstVisitCancelReasonObject = mstVisitCancelReasonRepository.findByVisitcancelreasonNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstVisitCancelReason.getVisitCancelreasonName());
            if (mstVisitCancelReasonObject == null) {
                mstVisitCancelReasonRepository.save(mstVisitCancelReason);
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
        List<MstVisitCancelReason> records;
        records = mstVisitCancelReasonRepository.findByVisitcancelreasonNameContainsAndIsActiveTrueAndIsDeletedFalse(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{bloodgroupId}")
    public MstVisitCancelReason read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bloodgroupId") Long bloodgroupId) {
        TenantContext.setCurrentTenant(tenantName);
        MstVisitCancelReason mstVisitCancelReason = mstVisitCancelReasonRepository.getById(bloodgroupId);
        return mstVisitCancelReason;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstVisitCancelReason mstVisitCancelReason) {
        TenantContext.setCurrentTenant(tenantName);
        /*  return mstVisitCancelReasonRepository.save(mstVisitCancelReason);*/
        mstVisitCancelReason.setVisitcancelreasonName(mstVisitCancelReason.getVisitCancelreasonName().trim());
        MstVisitCancelReason mstVisitCancelReasonObject = mstVisitCancelReasonRepository.findByVisitcancelreasonNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstVisitCancelReason.getVisitCancelreasonName());
        if (mstVisitCancelReasonObject == null) {
            mstVisitCancelReasonRepository.save(mstVisitCancelReason);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstVisitCancelReasonObject.getVisitCancelreasonId() == mstVisitCancelReason.getVisitCancelreasonId()) {
            mstVisitCancelReasonRepository.save(mstVisitCancelReason);
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
    public Iterable<MstVisitCancelReason> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "visitcancelreasonId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstVisitCancelReasonRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByVisitcancelreasonName(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstVisitCancelReasonRepository.findByVisitcancelreasonNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByVisitcancelreasonName(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

    @PutMapping("delete/{bloodgroupId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bloodgroupId") Long bloodgroupId) {
        TenantContext.setCurrentTenant(tenantName);
        MstVisitCancelReason mstVisitCancelReason = mstVisitCancelReasonRepository.getById(bloodgroupId);
        if (mstVisitCancelReason != null) {
            mstVisitCancelReason.setIsDeleted(true);
            mstVisitCancelReasonRepository.save(mstVisitCancelReason);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
