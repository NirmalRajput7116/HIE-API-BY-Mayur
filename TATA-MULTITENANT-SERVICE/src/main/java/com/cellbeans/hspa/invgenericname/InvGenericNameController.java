package com.cellbeans.hspa.invgenericname;

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
@RequestMapping("/inv_generic_name")
public class InvGenericNameController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    InvGenericNameRepository invGenericNameRepository;

    @Autowired
    private InvGenericNameService invGenericNameService;

    /**
     * This Method is use create new entry in table name:
     * checks for {@link InvGenericName#} size as not null
     *
     * @param invGenericName: data from input form
     * @return HashMap : new entry in database with {@link InvGenericName}
     * respMap.put("success", "1") respMap.put("msg", "Added Successfully") upon creation of {@link InvGenericName} Object
     * or respMap.put("success", "0") respMap.put("msg", "Failed To Add Null Field"); upon error
     */

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvGenericName invGenericName) {
        TenantContext.setCurrentTenant(tenantName);
        if (invGenericName.getGenericName() != null) {
            invGenericNameRepository.save(invGenericName);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    //Not used
    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<InvGenericName> records;
        records = invGenericNameRepository.findByGenericNameContains(key);
        automap.put("record", records);
        return automap;
    }

    /**
     * This Method is use get {@link InvGenericName} Object from database
     *
     * @param genericId: id send by request
     * @return : Object Response of {@link InvGenericName} if found or will return null
     */

    @RequestMapping("byid/{genericId}")
    public InvGenericName read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("genericId") Long genericId) {
        TenantContext.setCurrentTenant(tenantName);
        InvGenericName invGenericName = invGenericNameRepository.getById(genericId);
        return invGenericName;
    }

    /**
     * This method is use to update {@link InvGenericName} object and save in database
     *
     * @param invGenericName: object of {@link InvGenericName}
     * @return : newly created object of  {@link InvGenericName}
     */

    @RequestMapping("update")
    public InvGenericName update(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvGenericName invGenericName) {
        TenantContext.setCurrentTenant(tenantName);
        return invGenericNameRepository.save(invGenericName);
    }

    /**
     * This Method is use to get the @{@link Pageable} object of {@link InvGenericName}
     *
     * @param page    : input from request or default is 1
     * @param size    : input from request or default is 100
     * @param qString : input from request as {@link InvGenericName}
     * @param sort    : sorting order to {@link PageRequest}
     * @param col     : column to sort or default is {@link InvGenericName}
     * @return {@link Pageable} : of {@link InvGenericName} on the provided parameters
     */

    @GetMapping
    @RequestMapping("list")
    public Iterable<InvGenericName> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "genericId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return invGenericNameRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return invGenericNameRepository.findByGenericNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    /**
     * This Method is use to set @{@link InvGenericName#setIsDeleted(boolean)} to true, Sudo delete in database
     *
     * @param genericId: id from request
     * @return HashMap: respMap.put("msg", "Operation Successful") respMap.put("success", "1") upon creation
     * or respMap.put("msg", "Operation Failed") respMap.put("success", "0") upon error
     */

    @PutMapping("delete/{genericId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("genericId") Long genericId) {
        TenantContext.setCurrentTenant(tenantName);
        InvGenericName invGenericName = invGenericNameRepository.getById(genericId);
        if (invGenericName != null) {
            invGenericName.setIsDeleted(true);
            invGenericNameRepository.save(invGenericName);
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
        List<Tuple> items = invGenericNameService.getInvGenericNameForDropdown(page, size, globalFilter);
        return items;
    }

}
            
