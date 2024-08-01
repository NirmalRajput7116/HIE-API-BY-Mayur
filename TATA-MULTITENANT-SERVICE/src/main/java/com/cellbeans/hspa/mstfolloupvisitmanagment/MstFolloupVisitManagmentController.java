package com.cellbeans.hspa.mstfolloupvisitmanagment;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mst_followupvisit_management")
public class MstFolloupVisitManagmentController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    MstFolloupVisitManagmentRepository mstFolloupVisitManagmentRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstFolloupVisitManagment mstFolloupVisitManagment) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstFolloupVisitManagment.getFollowupVistDuration() != null) {
            Long Id = mstFolloupVisitManagment.getFollowupServiceId().getServiceId();
            MstFolloupVisitManagment mVisit = mstFolloupVisitManagmentRepository.findByFollowupServiceIdServiceIdEqualsAndIsActiveTrueAndIsDeletedFalse(Id);
            System.out.println(mVisit);
            if (mVisit == null) {
                mstFolloupVisitManagmentRepository.save(mstFolloupVisitManagment);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
            } else {
                if (mVisit.getFollowupServiceId().getServiceId() == mstFolloupVisitManagment.getFollowupServiceId().getServiceId()) {
                    respMap.put("success", "2");
                    respMap.put("msg", "Already Added!");
                } else {
                    mstFolloupVisitManagmentRepository.save(mstFolloupVisitManagment);
                    respMap.put("success", "1");
                    respMap.put("msg", "Added Successfully");
                }
            }
        } else {
            respMap.put("success", "2");
            respMap.put("msg", "Failed");
        }
        return respMap;
    }

    @RequestMapping("byid/{id}")
    public MstFolloupVisitManagment read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long Id) {
        TenantContext.setCurrentTenant(tenantName);
        MstFolloupVisitManagment mstFolloupVisitManagment = mstFolloupVisitManagmentRepository.getById(Id);
        return mstFolloupVisitManagment;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstFolloupVisitManagment mstFolloupVisitManagment) {
        TenantContext.setCurrentTenant(tenantName);
        MstFolloupVisitManagment mVisit = mstFolloupVisitManagmentRepository.getById(mstFolloupVisitManagment.getFollowupVisitManagementId());
        Long id = mVisit.getFollowupServiceId().getServiceId();
//        if(mstFolloupVisitManagment.getFollowupVisitManagementId() == mVisit.getFollowupVisitManagementId() && mstFolloupVisitManagment.getFollowupServiceId().getServiceId() == id){
        if (mstFolloupVisitManagment.getFollowupVisitManagementId() == mVisit.getFollowupVisitManagementId()) {
            mstFolloupVisitManagmentRepository.save(mstFolloupVisitManagment);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
        } else {
            respMap.put("Failed", "0");
            respMap.put("msg", "Failed");
        }
        return respMap;
    }

    @PutMapping("delete/{id}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        MstFolloupVisitManagment mstFolloupVisitManagment = mstFolloupVisitManagmentRepository.getById(id);
        if (mstFolloupVisitManagment != null) {
            mstFolloupVisitManagment.setIsDeleted(true);
            mstFolloupVisitManagment.setIsActive(false);
            mstFolloupVisitManagmentRepository.save(mstFolloupVisitManagment);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstFolloupVisitManagment> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "followupVisitManagementId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstFolloupVisitManagmentRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstFolloupVisitManagmentRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

}
