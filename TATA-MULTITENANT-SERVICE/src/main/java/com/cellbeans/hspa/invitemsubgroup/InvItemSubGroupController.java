package com.cellbeans.hspa.invitemsubgroup;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inv_item_sub_group")
public class InvItemSubGroupController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    InvItemSubgroupRepository invItemSubgroupRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvItemSubGroup invItemSubGroup) {
        TenantContext.setCurrentTenant(tenantName);
        if (invItemSubGroup.getIsgName() != null) {
            invItemSubgroupRepository.save(invItemSubGroup);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{igsId}")
    public InvItemSubGroup read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("igsId") Long igsId) {
        TenantContext.setCurrentTenant(tenantName);
        InvItemSubGroup invItemSubGroup = invItemSubgroupRepository.getById(igsId);
        return invItemSubGroup;
    }

    @RequestMapping("update")
    public InvItemSubGroup update(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvItemSubGroup invItemSubGroup) {
        TenantContext.setCurrentTenant(tenantName);
        return invItemSubgroupRepository.save(invItemSubGroup);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<InvItemSubGroup> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "isgId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return invItemSubgroupRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByIsgName(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return invItemSubgroupRepository.findByIsgNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByIsgName(qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{igsId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("igsId") Long igsId) {
        TenantContext.setCurrentTenant(tenantName);
        InvItemSubGroup invItemSubGroup = invItemSubgroupRepository.getById(igsId);
        if (invItemSubGroup != null) {
            invItemSubGroup.setIsDeleted(true);
            invItemSubgroupRepository.save(invItemSubGroup);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("groupId/{igId}")
    public List<InvItemSubGroup> subGroupbyID(@RequestHeader("X-tenantId") String tenantName, @PathVariable("igId") Long igId) {
        TenantContext.setCurrentTenant(tenantName);
        return invItemSubgroupRepository.findAllByIsgGIdIgIdEqualsAndIsActiveTrueAndIsDeletedFalse(igId);

    }
//    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
//    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
//    TenantContext.setCurrentTenant(tenantName);
//        List<Tuple> items = invItemGroupService.getInvItemGroupForDropdown(page, size, globalFilter);
//        return items;
//    }

}

