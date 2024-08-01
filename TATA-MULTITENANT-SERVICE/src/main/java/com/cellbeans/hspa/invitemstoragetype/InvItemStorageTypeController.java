package com.cellbeans.hspa.invitemstoragetype;

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
@RequestMapping("/inv_item_storage_type")
public class InvItemStorageTypeController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    InvItemStorageTypeRepository invItemStorageTypeRepository;

    @Autowired
    private InvItemStorageTypeService invItemStorageTypeService;

    /**
     * This Method is use create new entry in table name:
     * checks for {@link InvItemStorageType#} size as not null
     *
     * @param invItemStorageType : data from input form
     * @return HashMap : new entry in database with {@link InvItemStorageType}
     * respMap.put("success", "1") respMap.put("msg", "Added Successfully") upon creation of {@link InvItemStorageType} Object
     * or respMap.put("success", "0") respMap.put("msg", "Failed To Add Null Field"); upon error
     */

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvItemStorageType invItemStorageType) {
        TenantContext.setCurrentTenant(tenantName);
        if (invItemStorageType.getIstName() != null) {
            invItemStorageTypeRepository.save(invItemStorageType);
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
        List<InvItemStorageType> records;
        records = invItemStorageTypeRepository.findByIstNameContains(key);
        automap.put("record", records);
        return automap;
    }

    /**
     * This Method is use get {@link InvItemStorageType} Object from database
     *
     * @param istId: id send by request
     * @return : Object Response of {@link InvItemStorageType} if found or will return null
     */

    @RequestMapping("byid/{istId}")
    public InvItemStorageType read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("istId") Long istId) {
        TenantContext.setCurrentTenant(tenantName);
        InvItemStorageType invItemStorageType = invItemStorageTypeRepository.getById(istId);
        return invItemStorageType;
    }

    /**
     * This method is use to update {@link InvItemStorageType} object and save in database
     *
     * @param invItemStorageType : object of {@link InvItemStorageType}
     * @return : newly created object of  {@link InvItemStorageType}
     */

    @RequestMapping("update")
    public InvItemStorageType update(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvItemStorageType invItemStorageType) {
        TenantContext.setCurrentTenant(tenantName);
        return invItemStorageTypeRepository.save(invItemStorageType);
    }

    /**
     * This Method is use to get the @{@link Pageable} object of {@link InvItemStorageType}
     *
     * @param page    : input from request or default is 1
     * @param size    : input from request or default is 100
     * @param qString : input from request as {@link InvItemStorageType}
     * @param sort    : sorting order to {@link PageRequest}
     * @param col     : column to sort or default is {@link InvItemStorageType}
     * @return {@link Pageable} : of {@link InvItemStorageType} on the provided parameters
     */

    @GetMapping
    @RequestMapping("list")
    public Iterable<InvItemStorageType> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "istId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return invItemStorageTypeRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return invItemStorageTypeRepository.findByIstNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    /**
     * This Method is use to set @{@link InvItemStorageType#setIsDeleted(boolean)} to true, Sudo delete in database
     *
     * @param istId : id from request
     * @return HashMap: respMap.put("msg", "Operation Successful") respMap.put("success", "1") upon creation
     * or respMap.put("msg", "Operation Failed") respMap.put("success", "0") upon error
     */

    @PutMapping("delete/{istId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("istId") Long istId) {
        TenantContext.setCurrentTenant(tenantName);
        InvItemStorageType invItemStorageType = invItemStorageTypeRepository.getById(istId);
        if (invItemStorageType != null) {
            invItemStorageType.setIsDeleted(true);
            invItemStorageTypeRepository.save(invItemStorageType);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List<Tuple> getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = invItemStorageTypeService.getInvItemStorageTypeForDropdown(page, size, globalFilter);
        return items;
    }

}
            
