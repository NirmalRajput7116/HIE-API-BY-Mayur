package com.cellbeans.hspa.tinvissueclinicitem;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_issue_clinic_item")
public class TinvIssueClinicItemController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TinvIssueClinicItemRepository tinvIssueClinicItemRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvIssueClinicItem> tinvIssueClinicItem) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvIssueClinicItem.size() != 0) {
            tinvIssueClinicItemRepository.saveAll(tinvIssueClinicItem);
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
        List<TinvIssueClinicItem> records;
        records = tinvIssueClinicItemRepository.findByIciItemIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{iciId}")
    public TinvIssueClinicItem read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("iciId") Long iciId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvIssueClinicItem tinvIssueClinicItem = tinvIssueClinicItemRepository.getById(iciId);
        return tinvIssueClinicItem;
    }

    @RequestMapping("update")
    public TinvIssueClinicItem update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvIssueClinicItem tinvIssueClinicItem) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvIssueClinicItemRepository.save(tinvIssueClinicItem);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TinvIssueClinicItem> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "iciId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tinvIssueClinicItemRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tinvIssueClinicItemRepository.findByIciItemIdContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{iciId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("iciId") Long iciId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvIssueClinicItem tinvIssueClinicItem = tinvIssueClinicItemRepository.getById(iciId);
        if (tinvIssueClinicItem != null) {
            tinvIssueClinicItem.setIsDeleted(true);
            tinvIssueClinicItemRepository.save(tinvIssueClinicItem);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping("issuebyid/{icId}")
    public List<TinvIssueClinicItem> getIssueItemById(@RequestHeader("X-tenantId") String tenantName, @PathVariable("icId") Long icId) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvIssueClinicItemRepository.findByIciIcIdIcIdAndIsActiveTrueAndIsDeletedFalse(icId);
    }

    @GetMapping("receivebyid/{riId}")
    public List<TinvIssueClinicItem> getItemListBrReceiveId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("riId") Long riId) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvIssueClinicItemRepository.findByIciRiIdRiIdAndIsActiveTrueAndIsDeletedFalse(riId);
    }

}
            
