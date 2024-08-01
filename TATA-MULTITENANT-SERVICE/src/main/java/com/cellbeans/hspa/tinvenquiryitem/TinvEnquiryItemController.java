package com.cellbeans.hspa.tinvenquiryitem;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_enquiry_item")
public class TinvEnquiryItemController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TinvEnquiryItemRepository tinvEnquiryItemRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvEnquiryItem> tinvEnquiryItem) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvEnquiryItem.size() != 0) {
            tinvEnquiryItemRepository.saveAll(tinvEnquiryItem);
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
        List<TinvEnquiryItem> records;
        records = tinvEnquiryItemRepository.findByEiItemIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{eiId}")
    public TinvEnquiryItem read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("eiId") Long eiId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvEnquiryItem tinvEnquiryItem = tinvEnquiryItemRepository.getById(eiId);
        return tinvEnquiryItem;
    }

    @RequestMapping("update")
    public TinvEnquiryItem update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvEnquiryItem tinvEnquiryItem) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvEnquiryItemRepository.save(tinvEnquiryItem);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TinvEnquiryItem> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "eiId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tinvEnquiryItemRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tinvEnquiryItemRepository.findByEiNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{eiId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("eiId") Long eiId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvEnquiryItem tinvEnquiryItem = tinvEnquiryItemRepository.getById(eiId);
        if (tinvEnquiryItem != null) {
            tinvEnquiryItem.setIsDeleted(true);
            tinvEnquiryItemRepository.save(tinvEnquiryItem);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("items")
    public Iterable<TinvEnquiryItem> list(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvEnquiryItem tinvEnquiryItem) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvEnquiryItemRepository.findByEiPieIdPieIdAndIsActiveTrueAndIsDeletedFalse(tinvEnquiryItem.getEiPieId().getPieId());

    }

}
            
