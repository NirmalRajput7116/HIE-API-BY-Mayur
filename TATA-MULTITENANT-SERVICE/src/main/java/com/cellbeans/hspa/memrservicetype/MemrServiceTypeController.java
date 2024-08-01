package com.cellbeans.hspa.memrservicetype;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/memr_servicetype")
public class MemrServiceTypeController {
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MemrServiceTypeRepository memrServiceTypeRepository;

    @Autowired
    MemrServiceTypeService memrServiceTypeService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrServiceType memrServiceType) {
        TenantContext.setCurrentTenant(tenantName);
        if (memrServiceType.getServiceTypeName() != null) {
            memrServiceType.setServiceTypeName(memrServiceType.getServiceTypeName().trim());
            MemrServiceType memrServiceTypeObject = memrServiceTypeRepository.findByServiceTypeNameEqualsAndIsActiveTrueAndIsDeletedFalse(memrServiceType.getServiceTypeName());
            if (memrServiceTypeObject == null) {
                memrServiceTypeRepository.save(memrServiceType);
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
        List<MemrServiceType> records;
        records = memrServiceTypeRepository.findByServiceTypeNameContainsAndIsActiveTrueAndIsDeletedFalse(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{bloodgroupId}")
    public MemrServiceType read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bloodgroupId") Long bloodgroupId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrServiceType memrServiceType = memrServiceTypeRepository.getById(bloodgroupId);
        return memrServiceType;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrServiceType memrServiceType) {
        TenantContext.setCurrentTenant(tenantName);
        memrServiceType.setServiceTypeName(memrServiceType.getServiceTypeName().trim());
        MemrServiceType memrServiceTypeObject = memrServiceTypeRepository.findByServiceTypeNameEqualsAndIsActiveTrueAndIsDeletedFalse(memrServiceType.getServiceTypeName());
        if (memrServiceTypeObject == null) {
            memrServiceTypeRepository.save(memrServiceType);
            respMap.put("success", "1");
            respMap.put("msg", "updated Successfully");
            return respMap;
        } else if (memrServiceTypeObject.getServiceTypeId() == memrServiceType.getServiceTypeId()) {
            memrServiceTypeRepository.save(memrServiceType);
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
    public Iterable<MemrServiceType> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "serviceTypeId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return memrServiceTypeRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByServiceTypeName(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return memrServiceTypeRepository.findByServiceTypeNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByServiceTypeName(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @PutMapping("delete/{bloodgroupId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bloodgroupId") Long bloodgroupId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrServiceType memrServiceType = memrServiceTypeRepository.getById(bloodgroupId);
        if (memrServiceType != null) {
            memrServiceType.setIsDeleted(true);
            memrServiceTypeRepository.save(memrServiceType);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }
}
