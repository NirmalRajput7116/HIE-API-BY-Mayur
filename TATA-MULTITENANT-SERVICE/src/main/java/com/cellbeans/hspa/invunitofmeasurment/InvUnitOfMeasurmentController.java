package com.cellbeans.hspa.invunitofmeasurment;

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
@RequestMapping("/inv_unit_of_measurment")
public class InvUnitOfMeasurmentController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    InvUnitOfMeasurmentRepository invUnitOfMeasurmentRepository;

    @Autowired
    private InvUnitOfMeasurmentService invUnitOfMeasurmentService;

    /**
     * This Method is use create new entry in table name:
     * checks for {@link InvUnitOfMeasurment#} size as not null
     *
     * @param invUnitOfMeasurment : data from input form
     * @return HashMap : new entry in database with {@link InvUnitOfMeasurment}
     * respMap.put("success", "1") respMap.put("msg", "Added Successfully") upon creation of {@link InvUnitOfMeasurment} Object
     * or respMap.put("success", "0") respMap.put("msg", "Failed To Add Null Field"); upon error
     */

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvUnitOfMeasurment invUnitOfMeasurment) {
        TenantContext.setCurrentTenant(tenantName);
        if (invUnitOfMeasurment.getUomName() != null) {
            invUnitOfMeasurmentRepository.save(invUnitOfMeasurment);
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
        List<InvUnitOfMeasurment> records;
        records = invUnitOfMeasurmentRepository.findByUomNameContains(key);
        automap.put("record", records);
        return automap;
    }

    /**
     * This Method is use get {@link InvUnitOfMeasurment} Object from database
     *
     * @param uomId: id send by request
     * @return : Object Response of {@link InvUnitOfMeasurment} if found or will return null
     */

    @RequestMapping("byid/{uomId}")
    public InvUnitOfMeasurment read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("uomId") Long uomId) {
        TenantContext.setCurrentTenant(tenantName);
        InvUnitOfMeasurment invUnitOfMeasurment = invUnitOfMeasurmentRepository.getById(uomId);
        return invUnitOfMeasurment;
    }

    /**
     * This method is use to update {@link InvUnitOfMeasurment} object and save in database
     *
     * @param invUnitOfMeasurment : object of {@link InvUnitOfMeasurment}
     * @return : newly created object of  {@link InvUnitOfMeasurment}
     */

    @RequestMapping("update")
    public InvUnitOfMeasurment update(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvUnitOfMeasurment invUnitOfMeasurment) {
        TenantContext.setCurrentTenant(tenantName);
        return invUnitOfMeasurmentRepository.save(invUnitOfMeasurment);
    }

    /**
     * This Method is use to get the @{@link Pageable} object of {@link InvUnitOfMeasurment}
     *
     * @param page    : input from request or default is 1
     * @param size    : input from request or default is 100
     * @param qString : input from request as {@link InvUnitOfMeasurment}
     * @param sort    : sorting order to {@link PageRequest}
     * @param col     : column to sort or default is {@link InvUnitOfMeasurment}
     * @return {@link Pageable} : of {@link InvUnitOfMeasurment} on the provided parameters
     */

    @GetMapping
    @RequestMapping("list")
    public Iterable<InvUnitOfMeasurment> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "uomId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return invUnitOfMeasurmentRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return invUnitOfMeasurmentRepository.findByUomNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    /**
     * This Method is use to set @{@link InvUnitOfMeasurment#setIsDeleted(boolean)} to true, Sudo delete in database
     *
     * @param uomId: id from request
     * @return HashMap: respMap.put("msg", "Operation Successful") respMap.put("success", "1") upon creation
     * or respMap.put("msg", "Operation Failed") respMap.put("success", "0") upon error
     */

    @PutMapping("delete/{uomId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("uomId") Long uomId) {
        TenantContext.setCurrentTenant(tenantName);
        InvUnitOfMeasurment invUnitOfMeasurment = invUnitOfMeasurmentRepository.getById(uomId);
        if (invUnitOfMeasurment != null) {
            invUnitOfMeasurment.setIsDeleted(true);
            invUnitOfMeasurmentRepository.save(invUnitOfMeasurment);
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
        List<Tuple> items = invUnitOfMeasurmentService.getInvUnitOfMeasurmentForDropdown(page, size, globalFilter);
        return items;
    }

}
            
