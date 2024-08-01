package com.cellbeans.hspa.invitemcompany;

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
@RequestMapping("/inv_item_company")
public class InvItemCompanyController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    InvItemCompanyRepository invItemCompanyRepository;

    @Autowired
    private InvItemCompanyService invItemCompanyService;

    /**
     * This Method is use create new entry in table name:
     * checks for {@link InvItemCompany#} size as not null
     *
     * @param invItemCompany : data from input form
     * @return HashMap : new entry in database with {@link InvItemCompany}
     * respMap.put("success", "1") respMap.put("msg", "Added Successfully") upon creation of {@link InvItemCompany} Object
     * or respMap.put("success", "0") respMap.put("msg", "Failed To Add Null Field"); upon error
     */

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvItemCompany invItemCompany) {
        TenantContext.setCurrentTenant(tenantName);
        if (invItemCompany.getIcName() != null) {
            invItemCompanyRepository.save(invItemCompany);
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
        List<InvItemCompany> records;
        records = invItemCompanyRepository.findByIcNameContains(key);
        automap.put("record", records);
        return automap;
    }

    /**
     * This Method is use get {@link InvItemCompany} Object from database
     *
     * @param icId : id send by request
     * @return : Object Response of {@link InvItemCompany} if found or will return null
     */

    @RequestMapping("byid/{icId}")
    public InvItemCompany read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("icId") Long icId) {
        TenantContext.setCurrentTenant(tenantName);
        InvItemCompany invItemCompany = invItemCompanyRepository.getById(icId);
        return invItemCompany;
    }

    /**
     * This method is use to update {@link InvItemCompany} object and save in database
     *
     * @param invItemCompany : object of {@link InvItemCompany}
     * @return : newly created object of  {@link InvItemCompany}
     */

    @RequestMapping("update")
    public InvItemCompany update(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvItemCompany invItemCompany) {
        TenantContext.setCurrentTenant(tenantName);
        return invItemCompanyRepository.save(invItemCompany);
    }

    /**
     * This Method is use to get the @{@link Pageable} object of {@link InvItemCompany}
     *
     * @param page    : input from request or default is 1
     * @param size    : input from request or default is 100
     * @param qString : input from request as {@link InvItemCompany}
     * @param sort    : sorting order to {@link PageRequest}
     * @param col     : column to sort or default is {@link InvItemCompany}
     * @return {@link Pageable} : of {@link InvItemCompany} on the provided parameters
     */

    @GetMapping
    @RequestMapping("list")
    public Iterable<InvItemCompany> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "icId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return invItemCompanyRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return invItemCompanyRepository.findByIcNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    /**
     * This Method is use to set @{@link InvItemCompany#setIsDeleted(boolean)} to true, Sudo delete in database
     *
     * @param icId : id from request
     * @return HashMap: respMap.put("msg", "Operation Successful") respMap.put("success", "1") upon creation
     * or respMap.put("msg", "Operation Failed") respMap.put("success", "0") upon error
     */

    @PutMapping("delete/{icId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("icId") Long icId) {
        TenantContext.setCurrentTenant(tenantName);
        InvItemCompany invItemCompany = invItemCompanyRepository.getById(icId);
        if (invItemCompany != null) {
            invItemCompany.setIsDeleted(true);
            invItemCompanyRepository.save(invItemCompany);
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
        List<Tuple> items = invItemCompanyService.getInvItemCompanyForDropdown(page, size, globalFilter);
        return items;
    }

}
            
