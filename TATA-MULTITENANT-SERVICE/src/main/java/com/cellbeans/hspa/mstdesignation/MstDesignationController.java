package com.cellbeans.hspa.mstdesignation;

import com.cellbeans.hspa.TenantContext;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_designation")
public class MstDesignationController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstDesignationRepository mstDesignationRepository;

    @Autowired
    private MstDesignationService mstDesignationService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDesignation mstDesignation) {
        TenantContext.setCurrentTenant(tenantName);
        MstDesignation obj = new MstDesignation();
        if (mstDesignation.getDesignationName() != null) {
            mstDesignation.setDesignationName(mstDesignation.getDesignationName().trim());
            MstDesignation mDesigObj = mstDesignationRepository.findByDesignationNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstDesignation.getDesignationName());
            if (mDesigObj == null) {
                obj.setDesignationName(mstDesignation.getDesignationName());
                obj.setCreatedDate(new Date());
                obj.setDesignationUnitId(mstDesignation.getDesignationUnitId());
                mstDesignationRepository.save(obj);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Already Added");
                return respMap;
            }
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
//        if (mstDesignation.getDesignationName() != null) {
//            mstDesignationRepository.save(mstDesignation);
//            respMap.put("success", "1");
//            respMap.put("msg", "Added Successfully");
//            return respMap;
//        }
//        else {
//            respMap.put("success", "0");
//            respMap.put("msg", "Failed To Add Null Field");
//            return respMap;
//        }
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MstDesignation> records;
        records = mstDesignationRepository.findByDesignationNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{designationId}")
    public MstDesignation read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("designationId") Long designationId) {
        TenantContext.setCurrentTenant(tenantName);
        MstDesignation mstDesignation = mstDesignationRepository.getById(designationId);
        return mstDesignation;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDesignation mstDesignation) {
        TenantContext.setCurrentTenant(tenantName);
        //        return mstDesignationRepository.save(mstDesignation);
        MstDesignation obj = new MstDesignation();
        if (mstDesignation.getDesignationName() != null) {
            mstDesignation.setDesignationName(mstDesignation.getDesignationName().trim());
            MstDesignation mDesigObj = mstDesignationRepository.findByDesignationNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstDesignation.getDesignationName());
            if (mDesigObj == null) {
                obj.setDesignationName(mstDesignation.getDesignationName());
                obj.setCreatedDate(new Date());
                obj.setDesignationUnitId(mstDesignation.getDesignationUnitId());
                mstDesignationRepository.save(obj);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Already Added");
                return respMap;
            }
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstDesignation> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "designationId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstDesignationRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByDesignationNameAsc(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstDesignationRepository.findByDesignationNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByDesignationNameAsc(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{designationId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("designationId") Long designationId) {
        TenantContext.setCurrentTenant(tenantName);
        MstDesignation mstDesignation = mstDesignationRepository.getById(designationId);
        if (mstDesignation != null) {
            mstDesignation.setIsDeleted(true);
            mstDesignationRepository.save(mstDesignation);
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
        List<Tuple> items = mstDesignationService.getMstDesignationForDropdown(page, size, globalFilter);
        return items;
    }

}
            
