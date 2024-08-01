package com.cellbeans.hspa.mstrelation;

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
@RequestMapping("/mst_relation")
public class MstRelationController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstRelationRepository mstRelationRepository;

    @Autowired
    private MstRelationService mstRelationService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstRelation mstRelation) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstRelation.getRelationName() != null) {
            mstRelation.setRelationName(mstRelation.getRelationName().trim());
            MstRelation mstRelationObject = mstRelationRepository.findByRelationNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstRelation.getRelationName());
            if (mstRelationObject == null) {
                mstRelationRepository.save(mstRelation);
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
        List<MstRelation> records;
        records = mstRelationRepository.findByRelationNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{relationId}")
    public MstRelation read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("relationId") Long relationId) {
        TenantContext.setCurrentTenant(tenantName);
        MstRelation mstRelation = mstRelationRepository.getById(relationId);
        return mstRelation;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstRelation mstRelation) {
        TenantContext.setCurrentTenant(tenantName);
        mstRelation.setRelationName(mstRelation.getRelationName().trim());
        MstRelation mstRelationObject = mstRelationRepository.findByRelationNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstRelation.getRelationName());
        if (mstRelationObject == null) {
            mstRelationRepository.save(mstRelation);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstRelationObject.getRelationId() == mstRelation.getRelationId()) {
            mstRelationRepository.save(mstRelation);
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
    public Iterable<MstRelation> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "relationId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstRelationRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstRelationRepository.findByRelationNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{relationId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("relationId") Long relationId) {
        TenantContext.setCurrentTenant(tenantName);
        MstRelation mstRelation = mstRelationRepository.getById(relationId);
        if (mstRelation != null) {
            mstRelation.setIsDeleted(true);
            mstRelationRepository.save(mstRelation);
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
        List<Tuple> items = mstRelationService.getMstRelationForDropdown(page, size, globalFilter);
        return items;
    }

}
            
