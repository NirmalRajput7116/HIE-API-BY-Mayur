package com.cellbeans.hspa.mbillitem;

import com.cellbeans.hspa.TenantContext;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mbill_item")
public class MbillItemController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MbillItemRepository mbillItemRepository;

    @Autowired
    private MbillItemService mbillItemService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MbillItem mbillItem) {
        TenantContext.setCurrentTenant(tenantName);
        if (mbillItem.getItemName() != null) {
            mbillItemRepository.save(mbillItem);
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
        List<MbillItem> records;
        records = mbillItemRepository.findByItemNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{itemId}")
    public MbillItem read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("itemId") Long itemId) {
        TenantContext.setCurrentTenant(tenantName);
        MbillItem mbillItem = mbillItemRepository.getById(itemId);
        return mbillItem;
    }

    @RequestMapping("update")
    public MbillItem update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MbillItem mbillItem) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillItemRepository.save(mbillItem);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MbillItem> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "itemId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mbillItemRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mbillItemRepository.findByItemNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{itemId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("itemId") Long itemId) {
        TenantContext.setCurrentTenant(tenantName);
        MbillItem mbillItem = mbillItemRepository.getById(itemId);
        if (mbillItem != null) {
            mbillItem.setIsDeleted(true);
            mbillItemRepository.save(mbillItem);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = mbillItemService.getMbillItemForDropdown(page, size, globalFilter);
        return items;
    }

}
            
