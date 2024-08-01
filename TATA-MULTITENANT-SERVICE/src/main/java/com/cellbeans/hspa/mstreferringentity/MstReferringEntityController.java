package com.cellbeans.hspa.mstreferringentity;

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
@RequestMapping("/mst_referring_entity")
public class MstReferringEntityController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstReferringEntityRepository mstReferringEntityRepository;

    @Autowired
    private MstReferringEntityService mstReferringEntityService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstReferringEntity mstReferringEntity) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstReferringEntity.getReName() != null) {
            mstReferringEntity.setReName(mstReferringEntity.getReName().trim());
            MstReferringEntity mstReferringEntityObject = mstReferringEntityRepository.findByReNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstReferringEntity.getReName());
            if (mstReferringEntityObject == null) {
                mstReferringEntityRepository.save(mstReferringEntity);
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

    /*@RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MstReferringEntity> records;
        records = mstReferringEntityRepository.findByReNameContains(key);
        automap.put("record", records);
        return automap;                                                  // by Gayatri
    }*/

    @RequestMapping("autocomplete")
    public List<MstReferringEntity> getReferringEntityByAutoSearchID(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "entitytype", required = false) Long entitytype, @RequestParam(value = "qString", required = false) String qString) {
        TenantContext.setCurrentTenant(tenantName);
        return mstReferringEntityRepository.findByReRetIdRetIdEqualsAndReNameContainingAndIsActiveTrueAndIsDeletedFalse(entitytype, qString, PageRequest.of(0, 10));
    }

    @RequestMapping("byid/{reId}")
    public MstReferringEntity read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("reId") Long reId) {
        TenantContext.setCurrentTenant(tenantName);
        MstReferringEntity mstReferringEntity = mstReferringEntityRepository.getById(reId);
        return mstReferringEntity;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstReferringEntity mstReferringEntity) {
        TenantContext.setCurrentTenant(tenantName);
        mstReferringEntity.setReName(mstReferringEntity.getReName().trim());
        MstReferringEntity mstReferringEntityObject = mstReferringEntityRepository.findByReNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstReferringEntity.getReName());
        if (mstReferringEntityObject == null) {
            mstReferringEntityRepository.save(mstReferringEntity);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstReferringEntityObject.getReId() == mstReferringEntity.getReId()) {
            mstReferringEntityRepository.save(mstReferringEntity);
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
    @RequestMapping("byretype")
    public Iterable<MstReferringEntity> byretype(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "retype") Long retype) {
        TenantContext.setCurrentTenant(tenantName);
        return mstReferringEntityRepository.findByReRetIdRetIdAndIsDeletedOrderByReName(retype, false);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstReferringEntity> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "reId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstReferringEntityRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByReNameAsc(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return mstReferringEntityRepository.findByReNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByReNameAsc(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

    @PutMapping("delete/{reId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("reId") Long reId) {
        TenantContext.setCurrentTenant(tenantName);
        MstReferringEntity mstReferringEntity = mstReferringEntityRepository.getById(reId);
        if (mstReferringEntity != null) {
            mstReferringEntity.setIsDeleted(true);
            mstReferringEntityRepository.save(mstReferringEntity);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "entityType", defaultValue = "0", required = false) long entityType, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = mstReferringEntityService.getMstReferringEntityForDropdown(page, size, entityType, globalFilter);
        return items;
    }

}
            
