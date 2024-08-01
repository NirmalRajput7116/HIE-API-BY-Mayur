package com.cellbeans.hspa.tinvpurchasegoodsreceivednoteitem;

import com.cellbeans.hspa.TenantContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_purchase_goods_received_note_item")
public class TinvPurchaseGoodsReceivedNoteItemController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TinvPurchaseGoodsReceivedNoteItemRepository tinvPurchaseGoodsReceivedNoteItemRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvPurchaseGoodsReceivedNoteItem> tinvPurchaseGoodsReceivedNoteItem) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvPurchaseGoodsReceivedNoteItem.size() != 0) {
            tinvPurchaseGoodsReceivedNoteItemRepository.saveAll(tinvPurchaseGoodsReceivedNoteItem);
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
        List<TinvPurchaseGoodsReceivedNoteItem> records;
        records = tinvPurchaseGoodsReceivedNoteItemRepository.findByPgrniPgrnIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{pgrniId}")
    public TinvPurchaseGoodsReceivedNoteItem read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pgrniId") Long pgrniId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPurchaseGoodsReceivedNoteItem tinvPurchaseGoodsReceivedNoteItem = tinvPurchaseGoodsReceivedNoteItemRepository.getById(pgrniId);
        return tinvPurchaseGoodsReceivedNoteItem;
    }

    @RequestMapping("update")
    public TinvPurchaseGoodsReceivedNoteItem update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvPurchaseGoodsReceivedNoteItem tinvPurchaseGoodsReceivedNoteItem) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvPurchaseGoodsReceivedNoteItemRepository.save(tinvPurchaseGoodsReceivedNoteItem);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TinvPurchaseGoodsReceivedNoteItem> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "pgrniId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tinvPurchaseGoodsReceivedNoteItemRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tinvPurchaseGoodsReceivedNoteItemRepository.findByPgrniNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{pgrniId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pgrniId") Long pgrniId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPurchaseGoodsReceivedNoteItem tinvPurchaseGoodsReceivedNoteItem = tinvPurchaseGoodsReceivedNoteItemRepository.getById(pgrniId);
        if (tinvPurchaseGoodsReceivedNoteItem != null) {
            tinvPurchaseGoodsReceivedNoteItem.setIsDeleted(true);
            tinvPurchaseGoodsReceivedNoteItemRepository.save(tinvPurchaseGoodsReceivedNoteItem);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
