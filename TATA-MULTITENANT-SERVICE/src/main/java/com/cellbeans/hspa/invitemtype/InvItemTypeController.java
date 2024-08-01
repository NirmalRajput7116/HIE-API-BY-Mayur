package com.cellbeans.hspa.invitemtype;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/inv_item_type")
public class InvItemTypeController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    InvItemTypeRepository invItemTypeRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvItemType invItemType) {
        TenantContext.setCurrentTenant(tenantName);
        if (invItemType.getItName() != null) {
            invItemTypeRepository.save(invItemType);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{itId}")
    public InvItemType read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("itId") Long itId) {
        TenantContext.setCurrentTenant(tenantName);
        InvItemType invItemType = invItemTypeRepository.getById(itId);
        return invItemType;
    }

    @RequestMapping("update")
    public InvItemType update(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvItemType invItemType) {
        TenantContext.setCurrentTenant(tenantName);
        return invItemTypeRepository.save(invItemType);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<InvItemType> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "itId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return invItemTypeRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByItName(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return invItemTypeRepository.findByItNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByItName(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{itId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("itId") Long itId) {
        TenantContext.setCurrentTenant(tenantName);
        InvItemType invItemType = invItemTypeRepository.getById(itId);
        if (invItemType != null) {
            invItemType.setIsDeleted(true);
            invItemTypeRepository.save(invItemType);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }
//    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
//    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
//    TenantContext.setCurrentTenant(tenantName);
//        List<Tuple> items = invItemCategoryService.getInvItemCategoryForDropdown(page, size, globalFilter);
//        return items;
//    }

}




