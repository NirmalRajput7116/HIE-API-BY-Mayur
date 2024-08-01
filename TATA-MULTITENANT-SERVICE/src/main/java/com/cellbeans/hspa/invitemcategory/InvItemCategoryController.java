package com.cellbeans.hspa.invitemcategory;

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
@RequestMapping("/inv_item_category")
public class InvItemCategoryController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    InvItemCategoryRepository invItemCategoryRepository;

    @Autowired
    private InvItemCategoryService invItemCategoryService;

    /**
     * This Method is use create new entry in table name:
     * checks for {@link InvItemCategory#} size as not null
     *
     * @param invItemCategory : data from input form
     * @return HashMap : new entry in database with {@link InvItemCategory}
     * respMap.put("success", "1") respMap.put("msg", "Added Successfully") upon creation of {@link InvItemCategory} Object
     * or respMap.put("success", "0") respMap.put("msg", "Failed To Add Null Field"); upon error
     */

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvItemCategory invItemCategory) {
        TenantContext.setCurrentTenant(tenantName);
        if (invItemCategory.getIcName() != null) {
            invItemCategoryRepository.save(invItemCategory);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    // Not Used
    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<InvItemCategory> records;
        records = invItemCategoryRepository.findByIcNameContains(key);
        automap.put("record", records);
        return automap;
    }

    /**
     * This Method is use get {@link InvItemCategory} Object from database
     *
     * @param icId: id send by request
     * @return : Object Response of {@link InvItemCategory} if found or will return null
     */

    @RequestMapping("byid/{icId}")
    public InvItemCategory read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("icId") Long icId) {
        TenantContext.setCurrentTenant(tenantName);
        InvItemCategory invItemCategory = invItemCategoryRepository.getById(icId);
        return invItemCategory;
    }

    /**
     * This method is use to update {@link InvItemCategory} object and save in database
     *
     * @param invItemCategory : object of {@link InvItemCategory}
     * @return : newly created object of  {@link InvItemCategory}
     */

    @RequestMapping("update")
    public InvItemCategory update(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvItemCategory invItemCategory) {
        TenantContext.setCurrentTenant(tenantName);
        return invItemCategoryRepository.save(invItemCategory);
    }

    /**
     * This Method is use to get the @{@link Pageable} object of {@link InvItemCategory}
     *
     * @param page    : input from request or default is 1
     * @param size    : input from request or default is 100
     * @param qString : input from request as {@link InvItemCategory}
     * @param sort    : sorting order to {@link PageRequest}
     * @param col     : column to sort or default is {@link InvItemCategory}
     * @return {@link Pageable} : of {@link InvItemCategory} on the provided parameters
     */

    @GetMapping
    @RequestMapping("list")
    public Iterable<InvItemCategory> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "icId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return invItemCategoryRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByIcName(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return invItemCategoryRepository.findByIcNameContainsAndIsActiveTrueAndIsDeletedFalseOrIcMbillServiceIdServiceNameContainsAndIsActiveTrueAndIsDeletedFalseOrIcLedgerNameContainsAndIsActiveTrueAndIsDeletedFalseOrIcGroupIdIgNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByIcName(qString, qString, qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    /**
     * This Method is use to set @{@link InvItemCategory#setIsDeleted(boolean)} to true, Sudo delete in database
     *
     * @param icId: id from request
     * @return HashMap: respMap.put("msg", "Operation Successful") respMap.put("success", "1") upon creation
     * or respMap.put("msg", "Operation Failed") respMap.put("success", "0") upon error
     */

    @PutMapping("delete/{icId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("icId") Long icId) {
        TenantContext.setCurrentTenant(tenantName);
        InvItemCategory invItemCategory = invItemCategoryRepository.getById(icId);
        if (invItemCategory != null) {
            invItemCategory.setIsDeleted(true);
            invItemCategoryRepository.save(invItemCategory);
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
        List<Tuple> items = invItemCategoryService.getInvItemCategoryForDropdown(page, size, globalFilter);
        return items;
    }

}
            
