package com.cellbeans.hspa.mstgpdesignation;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mstdesignation.MstGpDesignationService;
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
@RequestMapping("/mst_gp_designation")
public class MstGpDesignationController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstGpDesignationRepository mstGpDesignationRepository;

    @Autowired
    private MstGpDesignationService mstGpDesignationService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstGpDesignation mstGpDesignation) {
        TenantContext.setCurrentTenant(tenantName);
        MstGpDesignation obj = new MstGpDesignation();
        if (mstGpDesignation.getGpDesignationName() != null) {
            obj.setGpDesignationName(mstGpDesignation.getGpDesignationName());
            obj.setCreatedDate(new Date());
            mstGpDesignationRepository.save(obj);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
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
        List<MstGpDesignation> records;
        records = mstGpDesignationRepository.findByGpDesignationNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{gpDesignationId}")
    public MstGpDesignation read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("gpDesignationId") Long gpDesignationId) {
        TenantContext.setCurrentTenant(tenantName);
        MstGpDesignation mstGpDesignation = mstGpDesignationRepository.getById(gpDesignationId);
        return mstGpDesignation;
    }

    @RequestMapping("update")
    public MstGpDesignation update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstGpDesignation mstGpDesignation) {
        TenantContext.setCurrentTenant(tenantName);
        return mstGpDesignationRepository.save(mstGpDesignation);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstGpDesignation> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "gpDesignationId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstGpDesignationRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstGpDesignationRepository.findByGpDesignationNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{designationId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("designationId") Long designationId) {
        TenantContext.setCurrentTenant(tenantName);
        MstGpDesignation mstGpDesignation = mstGpDesignationRepository.getById(designationId);
        if (mstGpDesignation != null) {
            mstGpDesignation.setIsDeleted(true);
            mstGpDesignationRepository.save(mstGpDesignation);
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
        List<Tuple> items = mstGpDesignationService.getMstDesignationForDropdown(page, size, globalFilter);
        return items;
    }

    @GetMapping
    @RequestMapping("search")
    public Iterable<MstGpDesignation> search(@RequestHeader("X-tenantId") String tenantName,
                                             @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                             @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                             @RequestParam(value = "qString", required = false) String qString,
                                             @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                             @RequestParam(value = "col", required = false, defaultValue = "gpDesignationId") String col,
                                             @RequestParam(value = "colname", required = false) String colname) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("-----col name-----------------------" + colname);
        if (colname == "gpDesignationName" || colname.equals("gpDesignationName")) {
            return mstGpDesignationRepository.findByGpDesignationNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return mstGpDesignationRepository.findByGpDesignationNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }
}
            
