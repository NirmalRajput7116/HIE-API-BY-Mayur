package com.cellbeans.hspa.mstrole;

import com.cellbeans.hspa.TenantContext;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_role")
public class MstRoleController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstRoleRepository mstRoleRepository;

    @Autowired
    private MstRoleService mstRoleService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstRole mstRole) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstRole.getRoleName() != null) {
            if (mstRoleRepository.findByAllOrderByRoleName(mstRole.getRoleName()) == 0) {
                mstRoleRepository.save(mstRole);
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
        List<MstRole> records;
        records = mstRoleRepository.findByRoleNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{roleId}")
    public MstRole read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("roleId") Long roleId) {
        TenantContext.setCurrentTenant(tenantName);
        MstRole mstRole = mstRoleRepository.getById(roleId);
        return mstRole;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstRole mstRole) {
        TenantContext.setCurrentTenant(tenantName);
        //        return mstRoleRepository.save(mstRole);
        Map<String, String> automap = new HashMap<String, String>();
        if (mstRoleRepository.findByAllOrderByRoleName(mstRole.getRoleName()) == 0) {
            mstRoleRepository.save(mstRole);
            automap.put("status", "1");
            automap.put("msg", "Added Successfully ...!");
        } else {
            automap.put("status", "0");
            automap.put("msg", "Already Added ...!");
        }
        return automap;
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstRole> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "roleId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstRoleRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByRoleName(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return mstRoleRepository.findByRoleNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByRoleName(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @PutMapping("delete/{roleId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("roleId") Long roleId) {
        TenantContext.setCurrentTenant(tenantName);
        MstRole mstRole = mstRoleRepository.getById(roleId);
        if (mstRole != null) {
            mstRole.setIsDeleted(true);
            mstRoleRepository.save(mstRole);
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
        List<Tuple> items = mstRoleService.getMstRoleForDropdown(page, size, globalFilter);
        return items;
    }

}
            
