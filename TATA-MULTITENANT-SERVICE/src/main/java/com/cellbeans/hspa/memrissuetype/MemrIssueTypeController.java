package com.cellbeans.hspa.memrissuetype;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/memr_issuetype")
public class MemrIssueTypeController {
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MemrIssueTypeRepository memrIssueTypeRepository;

    @Autowired
    MemrIssueTypeService memrIssueTypeService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrIssueType memrIssueType) {
        TenantContext.setCurrentTenant(tenantName);
        if (memrIssueType.getIssueTypeName() != null) {
            memrIssueType.setIssueTypeName(memrIssueType.getIssueTypeName().trim());
            MemrIssueType memrIssueTypeObject = memrIssueTypeRepository.findByIssueTypeNameEqualsAndIsActiveTrueAndIsDeletedFalse(memrIssueType.getIssueTypeName());
            if (memrIssueTypeObject == null) {
                memrIssueTypeRepository.save(memrIssueType);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "2");
                respMap.put("msg", "Already Added");
                return respMap;
            }
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
        List<MemrIssueType> records;
        records = memrIssueTypeRepository.findByIssueTypeNameContainsAndIsActiveTrueAndIsDeletedFalse(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{bloodgroupId}")
    public MemrIssueType read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bloodgroupId") Long bloodgroupId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrIssueType memrIssueType = memrIssueTypeRepository.getById(bloodgroupId);
        return memrIssueType;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrIssueType memrIssueType) {
        TenantContext.setCurrentTenant(tenantName);
        memrIssueType.setIssueTypeName(memrIssueType.getIssueTypeName().trim());
        MemrIssueType memrIssueTypeObject = memrIssueTypeRepository.findByIssueTypeNameEqualsAndIsActiveTrueAndIsDeletedFalse(memrIssueType.getIssueTypeName());
        if (memrIssueTypeObject == null) {
            memrIssueTypeRepository.save(memrIssueType);
            respMap.put("success", "1");
            respMap.put("msg", "updated Successfully");
            return respMap;
        } else if (memrIssueTypeObject.getIssueTypeId() == memrIssueType.getIssueTypeId()) {
            memrIssueTypeRepository.save(memrIssueType);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else {
            respMap.put("success", "2");
            respMap.put("msg", "Already Added");
            return respMap;

        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MemrIssueType> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "issueTypeId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return memrIssueTypeRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByIssueTypeName(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return memrIssueTypeRepository.findByIssueTypeNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByIssueTypeName(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @PutMapping("delete/{bloodgroupId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bloodgroupId") Long bloodgroupId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrIssueType memrIssueType = memrIssueTypeRepository.getById(bloodgroupId);
        if (memrIssueType != null) {
            memrIssueType.setIsDeleted(true);
            memrIssueTypeRepository.save(memrIssueType);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }
}
