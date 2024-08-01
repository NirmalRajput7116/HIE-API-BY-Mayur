package com.cellbeans.hspa.tinvitemsalesreturnitem;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_item_sales_return_item")
public class TinvItemSalesReturnItemController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TinvItemSalesReturnItemRepository tinvItemSalesReturnItemRepository;

    @PersistenceContext
    EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvItemSalesReturnItem> tinvItemSalesReturnItem) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvItemSalesReturnItem.size() != 0) {
            tinvItemSalesReturnItemRepository.saveAll(tinvItemSalesReturnItem);
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
        List<TinvItemSalesReturnItem> records;
        records = tinvItemSalesReturnItemRepository.findByIsriItemNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{isriId}")
    public TinvItemSalesReturnItem read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("isriId") Long isriId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvItemSalesReturnItem tinvItemSalesReturnItem = tinvItemSalesReturnItemRepository.getById(isriId);
        return tinvItemSalesReturnItem;
    }

    @RequestMapping("update")
    public TinvItemSalesReturnItem update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvItemSalesReturnItem tinvItemSalesReturnItem) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvItemSalesReturnItemRepository.save(tinvItemSalesReturnItem);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TinvItemSalesReturnItem> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "isriId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tinvItemSalesReturnItemRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tinvItemSalesReturnItemRepository.findByIsriNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{isriId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("isriId") Long isriId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvItemSalesReturnItem tinvItemSalesReturnItem = tinvItemSalesReturnItemRepository.getById(isriId);
        if (tinvItemSalesReturnItem != null) {
            tinvItemSalesReturnItem.setIsDeleted(true);
            tinvItemSalesReturnItemRepository.save(tinvItemSalesReturnItem);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("returnitems")
    public Iterable<TinvItemSalesReturnItem> list(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvItemSalesReturnItem tinvItemSalesReturnItem) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvItemSalesReturnItemRepository.findAllByIsriIsrIdIsrIdAndIsActiveTrueAndIsDeletedFalse(tinvItemSalesReturnItem.getIsriIsrId().getIsrId());

    }

    @GetMapping
    @RequestMapping("getPharmacyReturnItemsByAdmisionId")
    public List<TinvItemSalesReturnItem> getPharmacyReturnItemsByAdmisionId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "admisionId") long admisionId) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "select itri from TinvItemSalesReturnItem  itri where  itri.isriIsrId.isrPsId.pharmacyType=1 and itri.isriIsrId.isrPsId.psAdmissionId.admissionId=" + admisionId + " order by itri.createdDate ASC ";
        List<TinvItemSalesReturnItem> list = entityManager.createQuery(query, TinvItemSalesReturnItem.class).getResultList();
        return list;
    }

}
            
