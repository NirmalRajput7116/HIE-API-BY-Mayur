package com.cellbeans.hspa.invcompositionname;

import com.cellbeans.hspa.TenantContext;
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
@RequestMapping("/inv_composition_name")
public class InvCompositionNameController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    InvCompositionNameRepository invCompositionNameRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<InvCompositionName> invCompositionName) {
        TenantContext.setCurrentTenant(tenantName);
        if (invCompositionName.size() != 0) {
            invCompositionNameRepository.saveAll(invCompositionName);
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
    public Map<String, String> createItem(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvCompositionName invCompositionName) {
        TenantContext.setCurrentTenant(tenantName);
        if (invCompositionName.getCompositionName() != null) {
            invCompositionNameRepository.save(invCompositionName);
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
        List<InvCompositionName> records;
        records = invCompositionNameRepository.findByCompositionNameContains(key);
        automap.put("record", records);
        return automap;
    }

    /**
     * This Method is use get {@link InvCompositionNameController} Object from database
     *
     * @param CompositionId : id send by request
     * @return : Object Response of {@link InvCompositionName} if found or will return null
     */

    @RequestMapping("byid/{CompositionId}")
    public InvCompositionName read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("CompositionId") Long CompositionId) {
        TenantContext.setCurrentTenant(tenantName);
        InvCompositionName invCompositionName = invCompositionNameRepository.getById(CompositionId);
        return invCompositionName;
    }

    /**
     * This method is use to update {@link InvCompositionName} object and save in database
     *
     * @param invCompositionName : object of {@link InvCompositionName}
     * @return : newly created object of  {@link InvCompositionName}
     */

    @RequestMapping("update")
    public InvCompositionName update(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvCompositionName invCompositionName) {
        TenantContext.setCurrentTenant(tenantName);
        return invCompositionNameRepository.save(invCompositionName);
    }

    /**
     * This Method is use to get the @{@link Pageable} object of {@link InvCompositionName}
     *
     * @param page    : input from request or default is 1
     * @param size    : input from request or default is 100
     * @param qString : input from request as {@link InvCompositionName}
     * @param sort    : sorting order to {@link PageRequest}
     * @param col     : column to sort or default is {@link InvCompositionName}
     * @return {@link Pageable} : of {@link InvCompositionName} on the provided parameters
     */

    @GetMapping
    @RequestMapping("list")
    public Iterable<InvCompositionName> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "CompositionId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return invCompositionNameRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return invCompositionNameRepository.findByCompositionNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    /**
     * This Method is use to set @{@link InvCompositionName#setIsDeleted(boolean)} to true, Sudo delete in database
     *
     * @param CompositionId: id from request
     * @return HashMap: respMap.put("msg", "Operation Successful") respMap.put("success", "1") upon creation
     * or respMap.put("msg", "Operation Failed") respMap.put("success", "0") upon error
     */

    @PutMapping("delete/{CompositionId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("CompositionId") Long CompositionId) {
        TenantContext.setCurrentTenant(tenantName);
        InvCompositionName invCompositionName = invCompositionNameRepository.getById(CompositionId);
        if (invCompositionName != null) {
            invCompositionName.setIsDeleted(true);
            invCompositionNameRepository.save(invCompositionName);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
