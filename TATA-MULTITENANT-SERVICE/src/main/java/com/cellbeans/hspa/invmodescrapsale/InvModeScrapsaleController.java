package com.cellbeans.hspa.invmodescrapsale;

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

@RestController
@RequestMapping("/inv_mode_scrapsale")
public class InvModeScrapsaleController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    InvModeScrapsaleRepository invModeScrapsaleRepository;

    @Autowired
    private InvModeScrapsaleService invModeScrapsaleService;

    /**
     * This Method is use create new entry in table name:
     * checks for {@link InvModeScrapsale#} size as not null
     *
     * @param invModeScrapsale : data from input form
     * @return HashMap : new entry in database with {@link InvModeScrapsale}
     * respMap.put("success", "1") respMap.put("msg", "Added Successfully") upon creation of {@link InvModeScrapsale} Object
     * or respMap.put("success", "0") respMap.put("msg", "Failed To Add Null Field"); upon error
     */

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvModeScrapsale invModeScrapsale) {
        TenantContext.setCurrentTenant(tenantName);
        if (invModeScrapsale.getMcName() != null) {
            invModeScrapsaleRepository.save(invModeScrapsale);
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
        List<InvModeScrapsale> records;
        records = invModeScrapsaleRepository.findByMcNameContains(key);
        automap.put("record", records);
        return automap;
    }

    /**
     * This Method is use get {@link InvModeScrapsale} Object from database
     *
     * @param mcId : id send by request
     * @return : Object Response of {@link InvModeScrapsale} if found or will return null
     */

    @RequestMapping("byid/{mcId}")
    public InvModeScrapsale read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("mcId") Long mcId) {
        TenantContext.setCurrentTenant(tenantName);
        InvModeScrapsale invModeScrapsale = invModeScrapsaleRepository.getById(mcId);
        return invModeScrapsale;
    }

    /**
     * This method is use to update {@link InvModeScrapsale} object and save in database
     *
     * @param invModeScrapsale : object of {@link InvModeScrapsale}
     * @return : newly created object of  {@link InvModeScrapsale}
     */

    @RequestMapping("update")
    public InvModeScrapsale update(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvModeScrapsale invModeScrapsale) {
        TenantContext.setCurrentTenant(tenantName);
        return invModeScrapsaleRepository.save(invModeScrapsale);
    }

    /**
     * This Method is use to get the @{@link Pageable} object of {@link InvModeScrapsale}
     *
     * @param page    : input from request or default is 1
     * @param size    : input from request or default is 100
     * @param qString : input from request as {@link InvModeScrapsale}
     * @param sort    : sorting order to {@link PageRequest}
     * @param col     : column to sort or default is {@link InvModeScrapsale}
     * @return {@link Pageable} : of {@link InvModeScrapsale} on the provided parameters
     */

    @GetMapping
    @RequestMapping("list")
    public Iterable<InvModeScrapsale> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "mcId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return invModeScrapsaleRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return invModeScrapsaleRepository.findByMcNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    /**
     * This Method is use to set @{@link InvModeScrapsale#setIsDeleted(boolean)} to true, Sudo delete in database
     *
     * @param mcId: id from request
     * @return HashMap: respMap.put("msg", "Operation Successful") respMap.put("success", "1") upon creation
     * or respMap.put("msg", "Operation Failed") respMap.put("success", "0") upon error
     */

    @PutMapping("delete/{mcId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("mcId") Long mcId) {
        TenantContext.setCurrentTenant(tenantName);
        InvModeScrapsale invModeScrapsale = invModeScrapsaleRepository.getById(mcId);
        if (invModeScrapsale != null) {
            invModeScrapsale.setIsDeleted(true);
            invModeScrapsaleRepository.save(invModeScrapsale);
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
        List<Tuple> items = invModeScrapsaleService.getInvModeScrapsaleForDropdown(page, size, globalFilter);
        return items;
    }

}
            