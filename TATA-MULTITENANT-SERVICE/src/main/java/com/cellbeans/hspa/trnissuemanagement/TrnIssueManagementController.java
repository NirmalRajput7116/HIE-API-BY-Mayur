package com.cellbeans.hspa.trnissuemanagement;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/trn_issue_management")
public class TrnIssueManagementController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    TrnIssueManagementRepository trnIssueManagementRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnIssueManagement trnIssueManagement) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println(" issueDesc-----------------------------------------" + trnIssueManagement.getIssueDate());
        if (trnIssueManagement != null) {
            trnIssueManagementRepository.save(trnIssueManagement);
            respMap.put("success", "1");
            respMap.put("msg", "Record added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed to Add Null Field");
            return respMap;
        }
    }

    //
    @RequestMapping("byid/{tikitId}")
    public TrnIssueManagement read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tikitId") Long tikitId) {
        TenantContext.setCurrentTenant(tenantName);
        if (tikitId != null) {
            TrnIssueManagement trnIssueManagement = trnIssueManagementRepository.getById(tikitId);
            return trnIssueManagement;
        } else {
            return null;
        }
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnIssueManagement trnIssueManagement) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("trnIssueManagement :" + trnIssueManagement);
        if (trnIssueManagement != null) {
            trnIssueManagement.setModifiedDate(new Date());
            trnIssueManagementRepository.save(trnIssueManagement);
            respMap.put("success", "1");
            respMap.put("msg", "Record updated Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed to updated Null Field");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TrnIssueManagement> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "10") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "tikitId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return trnIssueManagementRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return trnIssueManagementRepository.findAllByIssueTitleContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{tikitId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tikitId") Long tikitId) {
        TenantContext.setCurrentTenant(tenantName);
        if (tikitId != null) {
            TrnIssueManagement trnIssueManagement = trnIssueManagementRepository.getById(tikitId);
            trnIssueManagement.setDeleted(true);
            trnIssueManagementRepository.save(trnIssueManagement);
            respMap.put("success", "1");
            respMap.put("msg", "Record deleted Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed to deleted record");
            return respMap;
        }
    }
//    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
//    public List getDropdown(
//            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
//            @RequestParam(value = "size", defaultValue = "5", required = false) Integer size,
//            @RequestParam(value = "globalFilter", required = false) String globalFilter) {
//        List<Tuple> items = actionMstService.getMstActionForDropdown(page, size, globalFilter);
//        return items;
//    }

}
