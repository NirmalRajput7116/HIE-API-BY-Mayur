package com.cellbeans.hspa.invitemdispensingtype;

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
@RequestMapping("/inv_item_dispensing_type")
public class InvItemDispensingTypeController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    InvItemDispensingTypeRepository invItemDispensingTypeRepository;

    @Autowired
    private InvItemDispensingTypeService invItemDispensingTypeService;

    /**
     * This Method is use create new entry in table name:
     * checks for {@link InvItemDispensingType#} size as not null
     *
     * @param invItemDispensingType : data from input form
     * @return HashMap : new entry in database with {@link InvItemDispensingType}
     * respMap.put("success", "1") respMap.put("msg", "Added Successfully") upon creation of {@link InvItemDispensingType} Object
     * or respMap.put("success", "0") respMap.put("msg", "Failed To Add Null Field"); upon error
     */

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvItemDispensingType invItemDispensingType) {
        TenantContext.setCurrentTenant(tenantName);
        if (invItemDispensingType.getIdtName() != null) {
            invItemDispensingTypeRepository.save(invItemDispensingType);
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
        List<InvItemDispensingType> records;
        records = invItemDispensingTypeRepository.findByIdtNameContains(key);
        automap.put("record", records);
        return automap;
    }

    /**
     * This Method is use get {@link InvItemDispensingType} Object from database
     *
     * @param idtId: id send by request
     * @return : Object Response of {@link InvItemDispensingType} if found or will return null
     */

    @RequestMapping("byid/{idtId}")
    public InvItemDispensingType read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("idtId") Long idtId) {
        TenantContext.setCurrentTenant(tenantName);
        InvItemDispensingType invItemDispensingType = invItemDispensingTypeRepository.getById(idtId);
        return invItemDispensingType;
    }

    /**
     * This method is use to update {@link InvItemDispensingType} object and save in database
     *
     * @param invItemDispensingType : object of {@link InvItemDispensingType}
     * @return : newly created object of  {@link InvItemDispensingType}
     */

    @RequestMapping("update")
    public InvItemDispensingType update(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvItemDispensingType invItemDispensingType) {
        TenantContext.setCurrentTenant(tenantName);
        return invItemDispensingTypeRepository.save(invItemDispensingType);
    }

    /**
     * This Method is use to get the @{@link Pageable} object of {@link InvItemDispensingType}
     *
     * @param page    : input from request or default is 1
     * @param size    : input from request or default is 100
     * @param qString : input from request as {@link InvItemDispensingType}
     * @param sort    : sorting order to {@link PageRequest}
     * @param col     : column to sort or default is {@link InvItemDispensingType}
     * @return {@link Pageable} : of {@link InvItemDispensingType} on the provided parameters
     */

    @GetMapping
    @RequestMapping("list")
    public Iterable<InvItemDispensingType> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "idtId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return invItemDispensingTypeRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByIdtName(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return invItemDispensingTypeRepository.findByIdtNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByIdtName(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    /**
     * This Method is use to set @{@link InvItemDispensingType#setIsDeleted(boolean)} to true, Sudo delete in database
     *
     * @param idtId : id from request
     * @return HashMap: respMap.put("msg", "Operation Successful") respMap.put("success", "1") upon creation
     * or respMap.put("msg", "Operation Failed") respMap.put("success", "0") upon error
     */

    @PutMapping("delete/{idtId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("idtId") Long idtId) {
        TenantContext.setCurrentTenant(tenantName);
        InvItemDispensingType invItemDispensingType = invItemDispensingTypeRepository.getById(idtId);
        if (invItemDispensingType != null) {
            invItemDispensingType.setIsDeleted(true);
            invItemDispensingTypeRepository.save(invItemDispensingType);
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
        List<Tuple> items = invItemDispensingTypeService.getInvItemDispensingTypeForDropdown(page, size, globalFilter);
        return items;
    }

}
            
