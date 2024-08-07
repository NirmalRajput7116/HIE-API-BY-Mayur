package com.cellbeans.hspa.mstdocumentcategory;

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
@RequestMapping("/mst_document_category")
public class MstDocumentCategoryController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstDocumentCategoryRepository mstDocumentCategoryRepository;

    @Autowired
    private MstDocumentCategoryService mstDocumentCategoryService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDocumentCategory mstDocumentCategory) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstDocumentCategory.getDcName() != null) {
            mstDocumentCategory.setDcName(mstDocumentCategory.getDcName().trim());
            MstDocumentCategory mstDocumentCategoryObject = mstDocumentCategoryRepository.findByDcNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstDocumentCategory.getDcName());
            if (mstDocumentCategoryObject == null) {
                mstDocumentCategoryRepository.save(mstDocumentCategory);
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
        List<MstDocumentCategory> records;
        records = mstDocumentCategoryRepository.findByDcNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{dcId}")
    public MstDocumentCategory read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dcId") Long dcId) {
        TenantContext.setCurrentTenant(tenantName);
        MstDocumentCategory mstDocumentCategory = mstDocumentCategoryRepository.getById(dcId);
        return mstDocumentCategory;
    }

    @RequestMapping("update")
    public MstDocumentCategory update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDocumentCategory mstDocumentCategory) {
        TenantContext.setCurrentTenant(tenantName);
        return mstDocumentCategoryRepository.save(mstDocumentCategory);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstDocumentCategory> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "dcId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstDocumentCategoryRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstDocumentCategoryRepository.findByDcNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{dcId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dcId") Long dcId) {
        TenantContext.setCurrentTenant(tenantName);
        MstDocumentCategory mstDocumentCategory = mstDocumentCategoryRepository.getById(dcId);
        if (mstDocumentCategory != null) {
            mstDocumentCategory.setIsDeleted(true);
            mstDocumentCategoryRepository.save(mstDocumentCategory);
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
        List<Tuple> items = mstDocumentCategoryService.getMstDocumentCategoryForDropdown(page, size, globalFilter);
        return items;
    }

}
            
