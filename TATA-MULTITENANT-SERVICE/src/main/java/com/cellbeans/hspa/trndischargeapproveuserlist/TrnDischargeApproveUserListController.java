package com.cellbeans.hspa.trndischargeapproveuserlist;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trn_discharge_approve_user_list")
public class TrnDischargeApproveUserListController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnDischargeApproveUserListRepository trnDischargeApproveUserListRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnDischargeApproveUserList trnDischargeApproveUserList) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnDischargeApproveUserList.getDaulAdmissionId().getAdmissionId() != 0L) {
            trnDischargeApproveUserListRepository.save(trnDischargeApproveUserList);
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
        List<TrnDischargeApproveUserList> records;
        records = trnDischargeApproveUserListRepository.findByDaulIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("/dischargestafflist/{staffid}/{status}")
    public List<TrnDischargeApproveUserList> dischargestafflist(@RequestHeader("X-tenantId") String tenantName, @PathVariable("staffid") Long staffid, @PathVariable("status") String status) {
        TenantContext.setCurrentTenant(tenantName);
        return trnDischargeApproveUserListRepository.findAllByDaulStaffIdStaffIdEqualsAndDaulStatusEquals(staffid, status);
    }

    @RequestMapping("/dischargealllist/{status}")
    public List<TrnDischargeApproveUserList> dischargealllist(@RequestHeader("X-tenantId") String tenantName, @PathVariable("status") String status) {
        TenantContext.setCurrentTenant(tenantName);
        return trnDischargeApproveUserListRepository.findAllByDaulStatusEquals(status);
    }

    @RequestMapping("/dischargeadmissionllist/{admissionId}")
    public List<TrnDischargeApproveUserList> listabyadmissionid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("admissionId") Long admissionId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnDischargeApproveUserListRepository.findAllByDaulAdmissionIdAdmissionIdEqualsAndIsActiveTrueAndIsDeletedFalse(admissionId);
    }

    @RequestMapping("byid/{daulId}")
    public TrnDischargeApproveUserList read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("daulId") Long daulId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnDischargeApproveUserList trnDischargeApproveUserList = trnDischargeApproveUserListRepository.getById(daulId);
        return trnDischargeApproveUserList;
    }

    @RequestMapping("update")
    public TrnDischargeApproveUserList update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnDischargeApproveUserList trnDischargeApproveUserList) {
        TenantContext.setCurrentTenant(tenantName);
        return trnDischargeApproveUserListRepository.save(trnDischargeApproveUserList);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TrnDischargeApproveUserList> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "daulId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return trnDischargeApproveUserListRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return trnDischargeApproveUserListRepository.findBydaulIdContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{daulId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("daulId") Long daulId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnDischargeApproveUserList trnDischargeApproveUserList = trnDischargeApproveUserListRepository.getById(daulId);
        if (trnDischargeApproveUserList != null) {
            trnDischargeApproveUserList.setIsDeleted(true);
            trnDischargeApproveUserListRepository.save(trnDischargeApproveUserList);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
