package com.cellbeans.hspa.invindentflag;

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
@RequestMapping("/inv_indent_flag")
public class InvIndentFlagController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    InvIndentFlagRepository invIndentFlagRepository;

    @Autowired
    private InvIndentFlagService invIndentFlagService;

    /**
     * This Method is use create new entry in table name:
     * checks for {@link InvIndentFlag#} size as not null
     *
     * @param invIndentFlag : data from input form
     * @return HashMap : new entry in database with {@link InvIndentFlag}
     * respMap.put("success", "1") respMap.put("msg", "Added Successfully") upon creation of {@link InvIndentFlag} Object
     * or respMap.put("success", "0") respMap.put("msg", "Failed To Add Null Field"); upon error
     */

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvIndentFlag invIndentFlag) {
        TenantContext.setCurrentTenant(tenantName);
        if (invIndentFlag.getIfName() != null) {
            invIndentFlagRepository.save(invIndentFlag);
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
        List<InvIndentFlag> records;
        records = invIndentFlagRepository.findByIfNameContains(key);
        automap.put("record", records);
        return automap;
    }

    /**
     * This Method is use get {@link InvIndentFlag} Object from database
     *
     * @param ifId: id send by request
     * @return : Object Response of {@link InvIndentFlag} if found or will return null
     */

    @RequestMapping("byid/{ifId}")
    public InvIndentFlag read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ifId") Long ifId) {
        TenantContext.setCurrentTenant(tenantName);
        InvIndentFlag invIndentFlag = invIndentFlagRepository.getById(ifId);
        return invIndentFlag;
    }

    /**
     * This method is use to update {@link InvIndentFlag} object and save in database
     *
     * @param invIndentFlag: object of {@link InvIndentFlag}
     * @return : newly created object of  {@link InvIndentFlag}
     */

    @RequestMapping("update")
    public InvIndentFlag update(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvIndentFlag invIndentFlag) {
        TenantContext.setCurrentTenant(tenantName);
        return invIndentFlagRepository.save(invIndentFlag);
    }

    /**
     * This Method is use to get the @{@link Pageable} object of {@link InvIndentFlag}
     *
     * @param page    : input from request or default is 1
     * @param size    : input from request or default is 100
     * @param qString : input from request as {@link InvIndentFlag}
     * @param sort    : sorting order to {@link PageRequest}
     * @param col     : column to sort or default is {@link InvIndentFlag}
     * @return {@link Pageable} : of {@link InvIndentFlag} on the provided parameters
     */

    @GetMapping
    @RequestMapping("list")
    public Iterable<InvIndentFlag> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ifId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return invIndentFlagRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return invIndentFlagRepository.findByIfNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

    /**
     * This Method is use to set @{@link InvIndentFlag#setIsDeleted(boolean)} to true, Sudo delete in database
     *
     * @param ifId : id from request
     * @return HashMap: respMap.put("msg", "Operation Successful") respMap.put("success", "1") upon creation
     * or respMap.put("msg", "Operation Failed") respMap.put("success", "0") upon error
     */

    @PutMapping("delete/{ifId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ifId") Long ifId) {
        TenantContext.setCurrentTenant(tenantName);
        InvIndentFlag invIndentFlag = invIndentFlagRepository.getById(ifId);
        if (invIndentFlag != null) {
            invIndentFlag.setIsDeleted(true);
            invIndentFlagRepository.save(invIndentFlag);
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
        List<Tuple> items = invIndentFlagService.getInvIndentFlagForDropdown(page, size, globalFilter);
        return items;
    }

}

