package com.cellbeans.hspa.invtax;

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
@RequestMapping("/inv_tax")
public class InvTaxController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    InvTaxRepository invTaxRepository;

    @Autowired
    private InvTaxService invTaxService;

    /**
     * This Method is use create new entry in table name:
     * checks for {@link InvTax#} size as not null
     *
     * @param invTax : data from input form
     * @return HashMap : new entry in database with {@link InvTax}
     * respMap.put("success", "1") respMap.put("msg", "Added Successfully") upon creation of {@link InvTax} Object
     * or respMap.put("success", "0") respMap.put("msg", "Failed To Add Null Field"); upon error
     */

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvTax invTax) {
        TenantContext.setCurrentTenant(tenantName);
        if (invTax.getTaxName() != null) {
            invTaxRepository.save(invTax);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            respMap.put("taxId", invTax.toString());
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
        List<InvTax> records;
        records = invTaxRepository.findByTaxNameContains(key);
        automap.put("record", records);
        return automap;
    }

    /**
     * This Method is use get {@link InvTax} Object from database
     *
     * @param taxId: id send by request
     * @return : Object Response of {@link InvTax} if found or will return null
     */

    @RequestMapping("byid/{taxId}")
    public InvTax read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("taxId") Long taxId) {
        TenantContext.setCurrentTenant(tenantName);
        InvTax invTax = invTaxRepository.getById(taxId);
        return invTax;
    }

    /**
     * This method is use to update {@link InvTax} object and save in database
     *
     * @param invTax : object of {@link InvTax}
     * @return : newly created object of  {@link InvTax}
     */

    @RequestMapping("update")
    public InvTax update(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvTax invTax) {
        TenantContext.setCurrentTenant(tenantName);
        return invTaxRepository.save(invTax);
    }

    /**
     * This Method is use to get the @{@link Pageable} object of {@link InvTax}
     *
     * @param page    : input from request or default is 1
     * @param size    : input from request or default is 100
     * @param qString : input from request as {@link InvTax}
     * @param sort    : sorting order to {@link PageRequest}
     * @param col     : column to sort or default is {@link InvTax}
     * @return {@link Pageable} : of {@link InvTax} on the provided parameters
     */

    @GetMapping
    @RequestMapping("list")
    public Iterable<InvTax> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "taxId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return invTaxRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return invTaxRepository.findByTaxNameContainsAndIsActiveTrueAndIsDeletedFalseOrTaxCodeContainsAndIsActiveTrueAndIsDeletedFalseOrTaxValueContainsAndIsActiveTrueAndIsDeletedFalse(qString, qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    /**
     * This Method is use to set @{@link InvTax#setIsDeleted(boolean)} to true, Sudo delete in database
     *
     * @param : id from request
     * @return HashMap: respMap.put("msg", "Operation Successful") respMap.put("success", "1") upon creation
     * or respMap.put("msg", "Operation Failed") respMap.put("success", "0") upon error
     */

    @PutMapping("delete/{taxId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("taxId") Long taxId) {
        TenantContext.setCurrentTenant(tenantName);
        InvTax invTax = invTaxRepository.getById(taxId);
        if (invTax != null) {
            invTax.setIsDeleted(true);
            invTaxRepository.save(invTax);
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
        List<Tuple> items = invTaxService.getInvTaxForDropdown(page, size, globalFilter);
        return items;
    }

}

