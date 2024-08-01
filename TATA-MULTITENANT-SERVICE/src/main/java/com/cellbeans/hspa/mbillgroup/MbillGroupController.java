package com.cellbeans.hspa.mbillgroup;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mbillsubgroup.MbillSubGroup;
import com.cellbeans.hspa.mbillsubgroup.MbillSubGroupRepository;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/mbill_group")
public class MbillGroupController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    MbillGroupRepository mbillGroupRepository;

    @Autowired
    MbillSubGroupRepository mbillSubGroupRepository;

    @Autowired
    private MbillGroupService mbillGroupService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MbillGroup mbillGroup) {
        TenantContext.setCurrentTenant(tenantName);
        if (mbillGroup.getGroupName() != null) {
            if (mbillGroupRepository.findByAllOrderByGroupName(mbillGroup.getGroupName().trim(), mbillGroup.getGroupSdId().getSdId()) == 0) {
//             mbillGroup.setGroupName(mbillGroup.getGroupName().trim());
//             MbillGroup mbillGroup1 = mbillGroupRepository.findByGroupNameEqualsAndIsActiveTrueAndIsDeletedFalse(mbillGroup.getGroupName());
//             if(mbillGroup1 == null){
                mbillGroupRepository.save(mbillGroup);
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
        List<MbillGroup> records;
        records = mbillGroupRepository.findByGroupNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("/getgrouplist")
    public List<MbillGroup> groupList(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillGroupRepository.findAllByIsActiveTrueAndIsDeletedFalse();
    }

    @RequestMapping("/getsubgrouplist")
    public List<MbillSubGroup> subGroupList(@RequestHeader("X-tenantId") String tenantName, @RequestBody String groupIdList) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("groupIdList :" + groupIdList);
        StringTokenizer st = new StringTokenizer(groupIdList, "[,]");
        ArrayList<MbillSubGroup> sgList = new ArrayList<MbillSubGroup>();
        while (st.hasMoreTokens()) {
            Long groupId = Long.parseLong(st.nextToken());
            sgList.addAll(mbillSubGroupRepository.findAllBySgGroupIdGroupIdAndIsActiveTrueAndIsDeletedFalse(groupId));
        }
        return sgList;
    }

    @RequestMapping("byid/{groupId}")
    public MbillGroup read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("groupId") Long groupId) {
        TenantContext.setCurrentTenant(tenantName);
        MbillGroup mbillGroup = mbillGroupRepository.getById(groupId);
        return mbillGroup;
    }

    @RequestMapping("update")
    public MbillGroup update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MbillGroup mbillGroup) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillGroupRepository.save(mbillGroup);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MbillGroup> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "groupId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mbillGroupRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mbillGroupRepository.findByGroupNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{groupId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("groupId") Long groupId) {
        TenantContext.setCurrentTenant(tenantName);
        MbillGroup mbillGroup = mbillGroupRepository.getById(groupId);
        if (mbillGroup != null) {
            mbillGroup.setIsDeleted(true);
            mbillGroupRepository.save(mbillGroup);
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
        List<Tuple> items = mbillGroupService.getMbillGroupForDropdown(page, size, globalFilter);
        return items;
    }

}
            
