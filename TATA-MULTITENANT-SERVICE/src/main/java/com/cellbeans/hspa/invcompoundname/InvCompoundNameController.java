package com.cellbeans.hspa.invcompoundname;

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
@RequestMapping("/inv_compound_name")
public class InvCompoundNameController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    InvCompoundNameRepository invCompoundNameRepository;

    @Autowired
    private InvCompoundNameService invCompoundNameService;

    /**
     * This Method is use create new entry in table name:
     * checks for {@link InvCompoundName} size as not null
     *
     * @param invCompoundName : data from input form
     * @return HashMap : new entry in database with {@link InvCompoundName}
     * respMap.put("success", "1") respMap.put("msg", "Added Successfully") upon creation of {@link InvCompoundName} Object
     * or respMap.put("success", "0") respMap.put("msg", "Failed To Add Null Field"); upon error
     */

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<InvCompoundName> invCompoundName) {
        TenantContext.setCurrentTenant(tenantName);
        if (invCompoundName.size() != 0) {
            invCompoundNameRepository.saveAll(invCompoundName);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("createItem")
    public Map<String, String> createItem(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvCompoundName invCompoundName) {
        if (invCompoundName.getCompoundName() != null) {
            invCompoundNameRepository.save(invCompoundName);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    // NOt used
    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<InvCompoundName> records;
        records = invCompoundNameRepository.findByCompoundNameContains(key);
        automap.put("record", records);
        return automap;
    }

    /**
     * This Method is use get {@link InvCompoundNameController} Object from database
     *
     * @param compoundId : id send by request
     * @return : Object Response of {@link InvCompoundName} if found or will return null
     */

    @RequestMapping("byid/{compoundId}")
    public InvCompoundName read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("compoundId") Long compoundId) {
        InvCompoundName invCompoundName = invCompoundNameRepository.getById(compoundId);
        return invCompoundName;
    }

    /**
     * This method is use to update {@link InvCompoundName} object and save in database
     *
     * @param invCompoundName : object of {@link InvCompoundName}
     * @return : newly created object of  {@link InvCompoundName}
     */

    @RequestMapping("update")
    public InvCompoundName update(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvCompoundName invCompoundName) {
        TenantContext.setCurrentTenant(tenantName);
        return invCompoundNameRepository.save(invCompoundName);
    }

    /**
     * This Method is use to get the @{@link Pageable} object of {@link InvCompoundName}
     *
     * @param page    : input from request or default is 1
     * @param size    : input from request or default is 100
     * @param qString : input from request as {@link InvCompoundName}
     * @param sort    : sorting order to {@link PageRequest}
     * @param col     : column to sort or default is {@link InvCompoundName}
     * @return {@link Pageable} : of {@link InvCompoundName} on the provided parameters
     */

    @GetMapping
    @RequestMapping("list")
    public Iterable<InvCompoundName> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "compoundId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return invCompoundNameRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return invCompoundNameRepository.findByCompoundNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    /**
     * This Method is use to set @{@link InvCompoundName#setIsDeleted(boolean)} to true, Sudo delete in database
     *
     * @param compoundId: id from request
     * @return HashMap: respMap.put("msg", "Operation Successful") respMap.put("success", "1") upon creation
     * or respMap.put("msg", "Operation Failed") respMap.put("success", "0") upon error
     */

    @PutMapping("delete/{compoundId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("compoundId") Long compoundId) {
        TenantContext.setCurrentTenant(tenantName);
        InvCompoundName invCompoundName = invCompoundNameRepository.getById(compoundId);
        if (invCompoundName != null) {
            invCompoundName.setIsDeleted(true);
            invCompoundNameRepository.save(invCompoundName);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                            @RequestParam(value = "size", defaultValue = "5", required = false) Integer size,
                            @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = invCompoundNameService.getInvCompoundNameForDropdown(page, size, globalFilter);
        return items;
    }

}
            
