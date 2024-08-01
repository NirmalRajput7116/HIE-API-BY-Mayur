package com.cellbeans.hspa.invpregnancyclass;

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
@RequestMapping("/inv_pregnancy_class")
public class InvPregnancyClassController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    InvPregnancyClassRepository invPregnancyClassRepository;

    @Autowired
    private InvPregnancyClassService invPregnancyClassService;

    /**
     * This Method is use create new entry in table name:
     * checks for {@link InvPregnancyClass#} size as not null
     *
     * @param invPregnancyClass : data from input form
     * @return HashMap : new entry in database with {@link InvPregnancyClass}
     * respMap.put("success", "1") respMap.put("msg", "Added Successfully") upon creation of {@link InvPregnancyClass} Object
     * or respMap.put("success", "0") respMap.put("msg", "Failed To Add Null Field"); upon error
     */

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvPregnancyClass invPregnancyClass) {
        TenantContext.setCurrentTenant(tenantName);
        if (invPregnancyClass.getPcName() != null) {
            invPregnancyClassRepository.save(invPregnancyClass);
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
        List<InvPregnancyClass> records;
        records = invPregnancyClassRepository.findByPcNameContains(key);
        automap.put("record", records);
        return automap;
    }

    /**
     * This Method is use get {@link InvPregnancyClass} Object from database
     *
     * @param pcId: id send by request
     * @return : Object Response of {@link InvPregnancyClass} if found or will return null
     */

    @RequestMapping("byid/{pcId}")
    public InvPregnancyClass read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pcId") Long pcId) {
        TenantContext.setCurrentTenant(tenantName);
        InvPregnancyClass invPregnancyClass = invPregnancyClassRepository.getById(pcId);
        return invPregnancyClass;
    }

    /**
     * This method is use to update {@link InvPregnancyClass} object and save in database
     *
     * @param invPregnancyClass : object of {@link InvPregnancyClass}
     * @return : newly created object of  {@link InvPregnancyClass}
     */

    @RequestMapping("update")
    public InvPregnancyClass update(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvPregnancyClass invPregnancyClass) {
        TenantContext.setCurrentTenant(tenantName);
        return invPregnancyClassRepository.save(invPregnancyClass);
    }

    /**
     * This Method is use to get the @{@link Pageable} object of {@link InvPregnancyClass}
     *
     * @param page    : input from request or default is 1
     * @param size    : input from request or default is 100
     * @param qString : input from request as {@link InvPregnancyClass}
     * @param sort    : sorting order to {@link PageRequest}
     * @param col     : column to sort or default is {@link InvPregnancyClass}
     * @return {@link Pageable} : of {@link InvPregnancyClass} on the provided parameters
     */

    @GetMapping
    @RequestMapping("list")
    public Iterable<InvPregnancyClass> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "pcId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return invPregnancyClassRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return invPregnancyClassRepository.findByPcNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    /**
     * This Method is use to set @{@link InvPregnancyClass#setIsDeleted(boolean)} to true, Sudo delete in database
     *
     * @param pcId : id from request
     * @return HashMap: respMap.put("msg", "Operation Successful") respMap.put("success", "1") upon creation
     * or respMap.put("msg", "Operation Failed") respMap.put("success", "0") upon error
     */

    @PutMapping("delete/{pcId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pcId") Long pcId) {
        TenantContext.setCurrentTenant(tenantName);
        InvPregnancyClass invPregnancyClass = invPregnancyClassRepository.getById(pcId);
        if (invPregnancyClass != null) {
            invPregnancyClass.setIsDeleted(true);
            invPregnancyClassRepository.save(invPregnancyClass);
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
        List<Tuple> items = invPregnancyClassService.getInvPregnancyClassForDropdown(page, size, globalFilter);
        return items;
    }

}
            
