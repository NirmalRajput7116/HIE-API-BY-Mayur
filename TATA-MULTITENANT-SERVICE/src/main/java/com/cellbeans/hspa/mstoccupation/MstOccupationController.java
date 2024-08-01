package com.cellbeans.hspa.mstoccupation;

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
@RequestMapping("/mst_occupation")
public class MstOccupationController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstOccupationRepository mstOccupationRepository;

    @Autowired
    private MstOccupationService mstOccupationService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstOccupation mstOccupation) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstOccupation.getOccupationName() != null) {
            mstOccupation.setOccupationName(mstOccupation.getOccupationName().trim());
            MstOccupation mstOccupationObject = mstOccupationRepository.findByOccupationNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstOccupation.getOccupationName());
            if (mstOccupationObject == null) {
                mstOccupationRepository.save(mstOccupation);
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
        List<MstOccupation> records;
        records = mstOccupationRepository.findByOccupationNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{occupationId}")
    public MstOccupation read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("occupationId") Long occupationId) {
        TenantContext.setCurrentTenant(tenantName);
        MstOccupation mstOccupation = mstOccupationRepository.getById(occupationId);
        return mstOccupation;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstOccupation mstOccupation) {
        TenantContext.setCurrentTenant(tenantName);
        mstOccupation.setOccupationName(mstOccupation.getOccupationName().trim());
        MstOccupation mstOccupationObject = mstOccupationRepository.findByOccupationNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstOccupation.getOccupationName());
        if (mstOccupationObject == null) {
            mstOccupationRepository.save(mstOccupation);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstOccupationObject.getOccupationId() == mstOccupation.getOccupationId()) {
            mstOccupationRepository.save(mstOccupation);
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
    public Iterable<MstOccupation> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "occupationId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstOccupationRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByOccupationNameAsc(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstOccupationRepository.findByOccupationNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByOccupationNameAsc(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @GetMapping
    @RequestMapping("dropdownList")
    public Iterable<MstOccupation> list(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        return mstOccupationRepository.findAllByIsActiveTrueAndIsDeletedFalse();
    }

    @PutMapping("delete/{occupationId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("occupationId") Long occupationId) {
        TenantContext.setCurrentTenant(tenantName);
        MstOccupation mstOccupation = mstOccupationRepository.getById(occupationId);
        if (mstOccupation != null) {
            mstOccupation.setIsDeleted(true);
            mstOccupationRepository.save(mstOccupation);
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
        List<Tuple> items = mstOccupationService.getMstOccupationForDropdown(page, size, globalFilter);
        return items;
    }

}
            
