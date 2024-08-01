package com.cellbeans.hspa.tbillplanitem;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tbill_plan_item")
public class TbillPlanItemController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TbillPlanItemRepository tbillPlanItemRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillPlanItem tbillPlanItem) {
        TenantContext.setCurrentTenant(tenantName);
        if (tbillPlanItem.getPiItemId().getItemName() != null) {
            tbillPlanItemRepository.save(tbillPlanItem);
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
        List<TbillPlanItem> records;
        records = tbillPlanItemRepository.findByPiItemIdItemNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{piId}")
    public TbillPlanItem read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("piId") Long piId) {
        TenantContext.setCurrentTenant(tenantName);
        TbillPlanItem tbillPlanItem = tbillPlanItemRepository.getById(piId);
        return tbillPlanItem;
    }

    @RequestMapping("update")
    public TbillPlanItem update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillPlanItem tbillPlanItem) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillPlanItemRepository.save(tbillPlanItem);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TbillPlanItem> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "piId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tbillPlanItemRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return null;
//            return tbillPlanItemRepository.findByPiItemIdItemNameContainsOrPiPlanIdPlanIdContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

    @PutMapping("delete/{piId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("piId") Long piId) {
        TenantContext.setCurrentTenant(tenantName);
        TbillPlanItem tbillPlanItem = tbillPlanItemRepository.getById(piId);
        if (tbillPlanItem != null) {
            tbillPlanItem.setIsDeleted(true);
            tbillPlanItemRepository.save(tbillPlanItem);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
