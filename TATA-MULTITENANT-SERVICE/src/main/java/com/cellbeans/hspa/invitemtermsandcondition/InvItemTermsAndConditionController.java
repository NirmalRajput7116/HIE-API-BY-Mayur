package com.cellbeans.hspa.invitemtermsandcondition;

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
@RequestMapping("/inv_item_terms_and_condition")
public class InvItemTermsAndConditionController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    InvItemTermsAndConditionRepository invItemTermsAndConditionRepository;

    @Autowired
    private InvItemTermsAndConditionService invItemTermsAndConditionService;

    /**
     * This Method is use create new entry in table name:
     * checks for {@link InvItemTermsAndCondition#} size as not null
     *
     * @param invItemTermsAndCondition : data from input form
     * @return HashMap : new entry in database with {@link InvItemTermsAndCondition}
     * respMap.put("success", "1") respMap.put("msg", "Added Successfully") upon creation of {@link InvItemTermsAndCondition} Object
     * or respMap.put("success", "0") respMap.put("msg", "Failed To Add Null Field"); upon error
     */

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvItemTermsAndCondition invItemTermsAndCondition) {
        TenantContext.setCurrentTenant(tenantName);
        if (invItemTermsAndCondition.getTacDescripation() != null) {
            invItemTermsAndConditionRepository.save(invItemTermsAndCondition);
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
        List<InvItemTermsAndCondition> records;
        records = invItemTermsAndConditionRepository.findByTacDescripationContains(key);
        automap.put("record", records);
        return automap;
    }

    /**
     * This Method is use get {@link InvItemTermsAndCondition} Object from database
     *
     * @param tacId : id send by request
     * @return : Object Response of {@link InvItemTermsAndCondition} if found or will return null
     */

    @RequestMapping("byid/{tacId}")
    public InvItemTermsAndCondition read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tacId") Long tacId) {
        TenantContext.setCurrentTenant(tenantName);
        InvItemTermsAndCondition invItemTermsAndCondition = invItemTermsAndConditionRepository.getById(tacId);
        return invItemTermsAndCondition;
    }

    /**
     * This method is use to update {@link InvItemTermsAndCondition} object and save in database
     *
     * @param invItemTermsAndCondition : object of {@link InvItemTermsAndCondition}
     * @return : newly created object of  {@link InvItemTermsAndCondition}
     */

    @RequestMapping("update")
    public InvItemTermsAndCondition update(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvItemTermsAndCondition invItemTermsAndCondition) {
        TenantContext.setCurrentTenant(tenantName);
        return invItemTermsAndConditionRepository.save(invItemTermsAndCondition);
    }

    /**
     * This Method is use to get the @{@link Pageable} object of {@link InvItemTermsAndCondition}
     *
     * @param page    : input from request or default is 1
     * @param size    : input from request or default is 100
     * @param qString : input from request as {@link InvItemTermsAndCondition}
     * @param sort    : sorting order to {@link PageRequest}
     * @param col     : column to sort or default is {@link InvItemTermsAndCondition}
     * @return {@link Pageable} : of {@link InvItemTermsAndCondition} on the provided parameters
     */

    @GetMapping
    @RequestMapping("list")
    public Iterable<InvItemTermsAndCondition> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "tacId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return invItemTermsAndConditionRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return invItemTermsAndConditionRepository.findByTacDescripationContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    /**
     * This Method is use to set @{@link InvItemTermsAndCondition#setIsDeleted(boolean)} to true, Sudo delete in database
     *
     * @param tacId: id from request
     * @return HashMap: respMap.put("msg", "Operation Successful") respMap.put("success", "1") upon creation
     * or respMap.put("msg", "Operation Failed") respMap.put("success", "0") upon error
     */

    @PutMapping("delete/{tacId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tacId") Long tacId) {
        TenantContext.setCurrentTenant(tenantName);
        InvItemTermsAndCondition invItemTermsAndCondition = invItemTermsAndConditionRepository.getById(tacId);
        if (invItemTermsAndCondition != null) {
            invItemTermsAndCondition.setIsDeleted(true);
            invItemTermsAndConditionRepository.save(invItemTermsAndCondition);
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
        List<Tuple> items = invItemTermsAndConditionService.getInvItemTermsAndConditionForDropdown(page, size, globalFilter);
        return items;
    }

}
            
