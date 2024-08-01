package com.cellbeans.hspa.mstreferringentitytype;

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
@RequestMapping("/mst_referring_entity_type")
public class MstReferringEntityTypeController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstReferringEntityTypeRepository mstReferringEntityTypeRepository;

    @Autowired
    private MstReferringEntityTypeService mstReferringEntityTypeService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstReferringEntityType mstReferringEntityType) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstReferringEntityType.getRetName() != null) {
            mstReferringEntityType.setRetName(mstReferringEntityType.getRetName().trim());
            MstReferringEntityType mstReferringEntityTypeObject = mstReferringEntityTypeRepository.findByRetNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstReferringEntityType.getRetName());
            if (mstReferringEntityTypeObject == null) {
                mstReferringEntityTypeRepository.save(mstReferringEntityType);
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
        List<MstReferringEntityType> records;
        records = mstReferringEntityTypeRepository.findByRetNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{retId}")
    public MstReferringEntityType read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("retId") Long retId) {
        TenantContext.setCurrentTenant(tenantName);
        MstReferringEntityType mstReferringEntityType = mstReferringEntityTypeRepository.getById(retId);
        return mstReferringEntityType;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstReferringEntityType mstReferringEntityType) {
        TenantContext.setCurrentTenant(tenantName);
        mstReferringEntityType.setRetName(mstReferringEntityType.getRetName().trim());
        MstReferringEntityType mstReferringEntityTypeObject = mstReferringEntityTypeRepository.findByRetNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstReferringEntityType.getRetName());
        if (mstReferringEntityTypeObject == null) {
            mstReferringEntityTypeRepository.save(mstReferringEntityType);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstReferringEntityTypeObject.getRetId() == mstReferringEntityType.getRetId()) {
            mstReferringEntityTypeRepository.save(mstReferringEntityType);
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
    public Iterable<MstReferringEntityType> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "1000") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "retId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstReferringEntityTypeRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByRetNameAsc(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstReferringEntityTypeRepository.findByRetNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByRetNameAsc(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{retId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("retId") Long retId) {
        TenantContext.setCurrentTenant(tenantName);
        MstReferringEntityType mstReferringEntityType = mstReferringEntityTypeRepository.getById(retId);
        if (mstReferringEntityType != null) {
            mstReferringEntityType.setIsDeleted(true);
            mstReferringEntityTypeRepository.save(mstReferringEntityType);
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
        List<Tuple> items = mstReferringEntityTypeService.getMstReferringEntityTypeForDropdown(page, size, globalFilter);
        return items;
    }

}
            
