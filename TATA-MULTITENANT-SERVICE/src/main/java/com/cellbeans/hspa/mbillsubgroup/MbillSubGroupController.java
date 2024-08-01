package com.cellbeans.hspa.mbillsubgroup;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
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
@RequestMapping("/mbill_sub_group")
public class MbillSubGroupController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MbillSubGroupRepository mbillSubGroupRepository;
    @Autowired
    CreateJSONObject createJSONObject;
    @Autowired
    private MbillSubGroupService mbillSubGroupService;

    /**
     * This Method is use create new entry in table name:
     * checks for {@link MbillSubGroup#} size as not null
     *
     * @param mbillSubGroup: data from input form
     * @return HashMap : new entry in database with {@link MbillSubGroup}
     * respMap.put("success", "1") respMap.put("msg", "Added Successfully") upon creation of {@link MbillSubGroup} Object
     * or respMap.put("success", "0") respMap.put("msg", "Failed To Add Null Field"); upon error
     */
    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MbillSubGroup mbillSubGroup) {
        TenantContext.setCurrentTenant(tenantName);
        if (mbillSubGroup.getSgName() != null) {
            if (mbillSubGroupRepository.findByAllOrderBySubGroupName(mbillSubGroup.getSgName().trim(), mbillSubGroup.getSgGroupId().getGroupId()) == 0) {
                mbillSubGroupRepository.save(mbillSubGroup);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Already Added");
            }
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MbillSubGroup> records;
        records = mbillSubGroupRepository.findBySgNameContains(key);
        automap.put("record", records);
        return automap;
    }

    /**
     * This Method is use get {@link MbillSubGroup} Object from database
     *
     * @param sgId: id send by request
     * @return : Object Response of {@link MbillSubGroup} if found or will return null
     */
    @RequestMapping("byid/{sgId}")
    public MbillSubGroup read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("sgId") Long sgId) {
        TenantContext.setCurrentTenant(tenantName);
        MbillSubGroup mbillSubGroup = mbillSubGroupRepository.getById(sgId);
        return mbillSubGroup;
    }

    /**
     * This method is use to update {@link MbillSubGroup} object and save in database
     *
     * @param mbillSubGroup: object of {@link MbillSubGroup}
     * @return : newly created object of  {@link MbillSubGroup}
     */
    @RequestMapping("update")
    public MbillSubGroup update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MbillSubGroup mbillSubGroup) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillSubGroupRepository.save(mbillSubGroup);
    }

    /**
     * This Method is use to get the @{@link Pageable} object of {@link MbillSubGroup}
     *
     * @param page    : input from request or default is 1
     * @param size    : input from request or default is 100
     * @param qString : input from request as {@link MbillSubGroup}
     * @param sort    : sorting order to {@link PageRequest}
     * @param col     : column to sort or default is {@link MbillSubGroup}
     * @return {@link Pageable} : of {@link MbillSubGroup} on the provided parameters
     */
    @GetMapping
    @RequestMapping("list")
    public Iterable<MbillSubGroup> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "sgId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mbillSubGroupRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mbillSubGroupRepository.findBySgNameContainsAndIsActiveTrueAndIsDeletedFalseOrSgGroupIdGroupNameEqualsAndIsActiveTrueAndIsDeletedFalse(qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }
    }

    /**
     * This Method is use to get the @{@link Pageable} object of {@link MbillSubGroup}
     *
     * @param groupId : column to sort or default is {@link MbillSubGroup}
     * @return {@link Pageable} : of {@link MbillSubGroup} on the provided parameters
     */
    @GetMapping("listbygroup/{groupId}")
    public List<MbillSubGroup> listByGroupId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("groupId") Long groupId) {
        TenantContext.setCurrentTenant(tenantName);
        if (groupId == null || groupId == 0) {
            return mbillSubGroupRepository.findAllByIsActiveTrueAndIsDeletedFalse();
        } else {
            return mbillSubGroupRepository.findAllBySgGroupIdGroupIdAndIsActiveTrueAndIsDeletedFalse(groupId);
        }
    }

    /**
     * This Method is use to set @{@link MbillSubGroup#setIsDeleted(boolean)} to true, Sudo delete in database
     *
     * @param sgId: id from request
     * @return HashMap: respMap.put("msg", "Operation Successful") respMap.put("success", "1") upon creation
     * or respMap.put("msg", "Operation Failed") respMap.put("success", "0") upon error
     */
    @PutMapping("delete/{sgId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("sgId") Long sgId) {
        TenantContext.setCurrentTenant(tenantName);
        MbillSubGroup mbillSubGroup = mbillSubGroupRepository.getById(sgId);
        if (mbillSubGroup != null) {
            mbillSubGroup.setIsDeleted(true);
            mbillSubGroupRepository.save(mbillSubGroup);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "groupId", required = false) String groupId, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = mbillSubGroupService.getMbillSubGroupForDropdown(page, size, groupId, globalFilter);
        return items;
    }

    @GetMapping
    @RequestMapping("alllist")
    public List<MbillSubGroup> alllist(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "sgId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillSubGroupRepository.findAllByIsActiveTrueAndIsDeletedFalse();
    }

}

