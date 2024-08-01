package com.cellbeans.hspa.memrreferraltype;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/memr_referraltype")
public class MemrReferralTypeController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    MemrReferralTypeRepository memrReferralTypeRepository;

    @Autowired
    MemrReferralTypeService memrReferralTypeService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrReferralType memrReferralType) {
        TenantContext.setCurrentTenant(tenantName);
        if (memrReferralType.getReferralTypeName() != null) {
            memrReferralType.setReferralTypeName(memrReferralType.getReferralTypeName().trim());
            MemrReferralType memrReferralType1 = memrReferralTypeRepository.findByReferralTypeNameEqualsAndIsActiveTrueAndIsDeletedFalse(memrReferralType.getReferralTypeName());
            if (memrReferralType1 == null) {
                memrReferralTypeRepository.save(memrReferralType);
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
        List<MemrReferralType> records;
        records = memrReferralTypeRepository.findByReferralTypeNameContainsAndIsActiveTrueAndIsDeletedFalse(key);
        automap.put("record", records);
        return automap;
    }


    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrReferralType memrReferralType) {
        TenantContext.setCurrentTenant(tenantName);
        memrReferralType.setReferralTypeName(memrReferralType.getReferralTypeName().trim());
        MemrReferralType memrReferralType1 = memrReferralTypeRepository.findByReferralTypeNameEqualsAndIsActiveTrueAndIsDeletedFalse(memrReferralType.getReferralTypeName());
        if (memrReferralType1 == null) {
            memrReferralTypeRepository.save(memrReferralType);
            respMap.put("success", "1");
            respMap.put("msg", "updated Successfully");
            return respMap;
        } else if (memrReferralType1.getReferralTypeId() == memrReferralType.getReferralTypeId()) {
            memrReferralTypeRepository.save(memrReferralType);
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
    public Iterable<MemrReferralType> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "referralTypeId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return memrReferralTypeRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByReferralTypeName(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return memrReferralTypeRepository.findByReferralTypeNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByReferralTypeName(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @RequestMapping("byid/{referralTypeId}")
    public MemrReferralType read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("referralTypeId") Long referralTypeId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrReferralType memrReferralType = memrReferralTypeRepository.getById(referralTypeId);
        return memrReferralType;
    }
    @PutMapping("delete/{refferadId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("refferadId") Long refferadId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrReferralType memrReferralType = memrReferralTypeRepository.getById(refferadId);
        if (memrReferralType != null) {
            memrReferralType.setDeleted(true);
            memrReferralType.setActive(false);
            memrReferralTypeRepository.save(memrReferralType);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
