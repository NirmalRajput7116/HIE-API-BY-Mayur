package com.cellbeans.hspa.invitemgroup;

import com.cellbeans.hspa.TenantContext;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Inya Gaikwad
 */

@RestController
@RequestMapping("/inv_item_group")
public class InvItemGroupController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    InvItemGroupRepository invItemGroupRepository;

    @Autowired
    private InvItemGroupService invItemGroupService;

    /**
     * This Method is use create new entry in table name:
     * checks for {@link InvItemGroup#} size as not null
     *
     * @param invItemGroup : data from input form
     * @return HashMap : new entry in database with {@link InvItemGroup}
     * respMap.put("success", "1") respMap.put("msg", "Added Successfully") upon creation of {@link InvItemGroup} Object
     * or respMap.put("success", "0") respMap.put("msg", "Failed To Add Null Field"); upon error
     */

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvItemGroup invItemGroup) {
        TenantContext.setCurrentTenant(tenantName);
        if (invItemGroup.getIgName() != null) {
            invItemGroupRepository.save(invItemGroup);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    //Not Used
    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<InvItemGroup> records;
        records = invItemGroupRepository.findByIgNameContains(key);
        automap.put("record", records);
        return automap;
    }

    /**
     * This Method is use get {@link InvItemGroup} Object from database
     *
     * @param igId: id send by request
     * @return : Object Response of {@link InvItemGroup} if found or will return null
     */

    @RequestMapping("byid/{igId}")
    public InvItemGroup read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("igId") Long igId) {
        TenantContext.setCurrentTenant(tenantName);
        InvItemGroup invItemGroup = invItemGroupRepository.getById(igId);
        return invItemGroup;
    }

    /**
     * This method is use to update {@link InvItemGroup} object and save in database
     *
     * @param invItemGroup: object of {@link InvItemGroup}
     * @return : newly created object of  {@link InvItemGroup}
     */

    @RequestMapping("update")
    public InvItemGroup update(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvItemGroup invItemGroup) {
        TenantContext.setCurrentTenant(tenantName);
        return invItemGroupRepository.save(invItemGroup);
    }

    /**
     * This Method is use to get the @{@link Pageable} object of {@link InvItemGroup}
     *
     * @param page    : input from request or default is 1
     * @param size    : input from request or default is 100
     * @param qString : input from request as {@link InvItemGroup}
     * @param sort    : sorting order to {@link PageRequest}
     * @param col     : column to sort or default is {@link InvItemGroup}
     * @return {@link Pageable} : of {@link InvItemGroup} on the provided parameters
     */

    @GetMapping
    @RequestMapping("list")
    public Iterable<InvItemGroup> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "igId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return invItemGroupRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByIgName(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return invItemGroupRepository.findByIgNameContainsAndIsActiveTrueAndIsDeletedFalseOrIgLedgerNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByIgName(qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    /**
     * This Method is use to set @{@link InvItemGroup#setIsDeleted(boolean)} to true, Sudo delete in database
     *
     * @param igId : id from request
     * @return HashMap: respMap.put("msg", "Operation Successful") respMap.put("success", "1") upon creation
     * or respMap.put("msg", "Operation Failed") respMap.put("success", "0") upon error
     */

    @PutMapping("delete/{igId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("igId") Long igId) {
        TenantContext.setCurrentTenant(tenantName);
        InvItemGroup invItemGroup = invItemGroupRepository.getById(igId);
        if (invItemGroup != null) {
            invItemGroup.setIsDeleted(true);
            invItemGroupRepository.save(invItemGroup);
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
        List<Tuple> items = invItemGroupService.getInvItemGroupForDropdown(page, size, globalFilter);
        return items;
    }

}

