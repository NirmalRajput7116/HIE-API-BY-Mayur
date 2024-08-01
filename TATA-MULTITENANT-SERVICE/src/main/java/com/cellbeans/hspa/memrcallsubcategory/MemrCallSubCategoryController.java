package com.cellbeans.hspa.memrcallsubcategory;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.memrcallcategory.MemrCallCategory;
import com.cellbeans.hspa.memrcallcategory.MemrCallCategoryRepository;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/memr_call_sub_category")
public class MemrCallSubCategoryController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MemrCallSubCategoryRepository memrCallSubCategoryRepository;

    @Autowired
    private MemrCallSubCategoryService memrCallSubCategoryService;

    @Autowired
    private MemrCallCategoryRepository memrCallCategoryRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName,@RequestBody MemrCallSubCategory memrCallSubCategory) {
        TenantContext.setCurrentTenant(tenantName);
        if (memrCallSubCategory.getCcsName() != null) {
            if(memrCallSubCategoryRepository.findByAllOrderByCallCategoryName(memrCallSubCategory.getCcsName(),memrCallSubCategory.getCcsCcId().getCallCategoryId()) == 0) {
                memrCallSubCategoryRepository.save(memrCallSubCategory);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
            }else{
                respMap.put("success", "0");
                respMap.put("msg", "Already Added");
            }
            return respMap;
        }
        else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName,@PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MemrCallSubCategory> records;
        records = memrCallSubCategoryRepository.findByCcsNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{cscId}")
    public MemrCallSubCategory read(@RequestHeader("X-tenantId") String tenantName,@PathVariable("cscId") Long cscId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrCallSubCategory memrCallSubCategory = memrCallSubCategoryRepository.getById(cscId);
        return memrCallSubCategory;
    }

    @RequestMapping("update")
    public  Map<String, String> update(@RequestHeader("X-tenantId") String tenantName,@RequestBody MemrCallSubCategory memrCallSubCategory) {
        TenantContext.setCurrentTenant(tenantName);
        if(memrCallSubCategoryRepository.findByAllOrderByCallCategoryName(memrCallSubCategory.getCcsName(),memrCallSubCategory.getCcsCcId().getCallCategoryId()) == 0) {
            memrCallSubCategoryRepository.save(memrCallSubCategory);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
        }else{
            respMap.put("success", "0");
            respMap.put("msg", "Already Added");
        }

        return respMap;
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MemrCallSubCategory> list(@RequestHeader("X-tenantId") String tenantName,@RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                              @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                              @RequestParam(value = "qString", required = false) String qString,
                                              @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                              @RequestParam(value = "col", required = false, defaultValue = "cscId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return memrCallSubCategoryRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
        else {
            return memrCallSubCategoryRepository.findByCcsNameContainsAndIsActiveTrueAndIsDeletedFalseOrCcsCcIdCallCategoryNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, qString,PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }




    @PutMapping("delete/{cscId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName,@PathVariable("cscId") Long cscId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrCallSubCategory memrCallSubCategory = memrCallSubCategoryRepository.getById(cscId);
        if (memrCallSubCategory != null) {
            memrCallSubCategory.setIsDeleted(true);
            memrCallSubCategoryRepository.save(memrCallSubCategory);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        }
        else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }


    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName,@RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = memrCallSubCategoryService.getMemrCallSubCategoryForDropdown(page, size, globalFilter);
        return items;
    }

    @RequestMapping(value = "/subcategorybycategorydropdown/{categoryId}", method = RequestMethod.GET)
    public List subcategorybycategorydropdown(@RequestHeader("X-tenantId") String tenantName,@PathVariable("categoryId") Long categoryId) {
        TenantContext.setCurrentTenant(tenantName);
        List<MemrCallSubCategory> items = memrCallSubCategoryRepository.findByCcsCcIdCallCategoryId(categoryId);
        return items;
    }

}

