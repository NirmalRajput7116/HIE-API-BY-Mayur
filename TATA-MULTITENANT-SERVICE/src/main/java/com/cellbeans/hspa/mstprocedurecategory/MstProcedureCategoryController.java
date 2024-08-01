package com.cellbeans.hspa.mstprocedurecategory;

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
@RequestMapping("/mst_procedure_category")
public class MstProcedureCategoryController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstProcedureCategoryRepository mstProcedureCategoryRepository;

    @Autowired
    private MstProcedureCategoryService mstProcedureCategoryService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstProcedureCategory mstProcedureCategory) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstProcedureCategory.getPcName() != null) {
            mstProcedureCategoryRepository.save(mstProcedureCategory);
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
        List<MstProcedureCategory> records;
        records = mstProcedureCategoryRepository.findByPcNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{pcId}")
    public MstProcedureCategory read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pcId") Long pcId) {
        TenantContext.setCurrentTenant(tenantName);
        MstProcedureCategory mstProcedureCategory = mstProcedureCategoryRepository.getById(pcId);
        return mstProcedureCategory;
    }

    @RequestMapping("update")
    public MstProcedureCategory update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstProcedureCategory mstProcedureCategory) {
        TenantContext.setCurrentTenant(tenantName);
        return mstProcedureCategoryRepository.save(mstProcedureCategory);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstProcedureCategory> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "pcId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstProcedureCategoryRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstProcedureCategoryRepository.findByPcNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{pcId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pcId") Long pcId) {
        TenantContext.setCurrentTenant(tenantName);
        MstProcedureCategory mstProcedureCategory = mstProcedureCategoryRepository.getById(pcId);
        if (mstProcedureCategory != null) {
            mstProcedureCategory.setIsDeleted(true);
            mstProcedureCategoryRepository.save(mstProcedureCategory);
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
        List<Tuple> items = mstProcedureCategoryService.getMstProcedureCategoryForDropdown(page, size, globalFilter);
        return items;
    }

}
            
