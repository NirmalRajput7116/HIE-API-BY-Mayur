package com.cellbeans.hspa.invitemsubtype;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inv_item_sub_type")
public class InvItemSubTypeController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    InvItemSubTypeRepository invItemSubTypeRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvItemSubType invItemSubType) {
        TenantContext.setCurrentTenant(tenantName);
        if (invItemSubType.getIstName() != null) {
            invItemSubTypeRepository.save(invItemSubType);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{istId}")
    public InvItemSubType read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("istId") Long istId) {
        TenantContext.setCurrentTenant(tenantName);
        InvItemSubType invItemSubType = invItemSubTypeRepository.getById(istId);
        return invItemSubType;
    }

    @RequestMapping("update")
    public InvItemSubType update(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvItemSubType invItemSubType) {
        TenantContext.setCurrentTenant(tenantName);
        return invItemSubTypeRepository.save(invItemSubType);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<InvItemSubType> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "istId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return invItemSubTypeRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByIstName(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return invItemSubTypeRepository.findByIstNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByIstName(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{istId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("istId") Long istId) {
        TenantContext.setCurrentTenant(tenantName);
        InvItemSubType invItemSubType = invItemSubTypeRepository.getById(istId);
        if (invItemSubType != null) {
            invItemSubType.setIsDeleted(true);
            invItemSubTypeRepository.save(invItemSubType);
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
//        List<Tuple> items = invItemCategoryService.getInvItemCategoryForDropdown(page, size, globalFilter);
//        return items;
//    }
    @RequestMapping("typeId/{itId}")
    public List<InvItemSubType> subTypebyID(@RequestHeader("X-tenantId") String tenantName, @PathVariable("itId") Long itId) {
        TenantContext.setCurrentTenant(tenantName);
        return invItemSubTypeRepository.findAllByIstTIdItIdEqualsAndIsActiveTrueAndIsDeletedFalse(itId);

    }

}



