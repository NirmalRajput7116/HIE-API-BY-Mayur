package com.cellbeans.hspa.msttitle;

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
@RequestMapping("/mst_title")
public class MstTitleController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstTitleRepository mstTitleRepository;
    @Autowired
    private MstTitleService mstTitleService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstTitle mstTitle) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstTitle.getTitleName() != null) {
            mstTitle.setTitleName(mstTitle.getTitleName().trim());
            MstTitle mstTitleObject = mstTitleRepository.findByTitleNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstTitle.getTitleName());
            if (mstTitleObject == null) {
                mstTitleRepository.save(mstTitle);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "2");
                respMap.put("msg", "Duplicate Title Name");
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
        List<MstTitle> records;
        records = mstTitleRepository.findByTitleNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{titleId}")
    public MstTitle read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("titleId") Long titleId) {
        TenantContext.setCurrentTenant(tenantName);
        MstTitle mstTitle = mstTitleRepository.getById(titleId);
        return mstTitle;
    }

    @RequestMapping("update")
   /* public MstTitle update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstTitle mstTitle) {
        return mstTitleRepository.save(mstTitle);
    }*/
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstTitle mstTitle) {
        TenantContext.setCurrentTenant(tenantName);
        mstTitle.setTitleName(mstTitle.getTitleName().trim());
        MstTitle mstTitleObject = mstTitleRepository.findByTitleNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstTitle.getTitleName());
        if (mstTitleObject == null) {
            mstTitleRepository.save(mstTitle);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstTitleObject.getTitleId() == mstTitle.getTitleId()) {
            mstTitleRepository.save(mstTitle);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else {
            respMap.put("success", "2");
            respMap.put("msg", "Duplicate Title Found");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstTitle> list(@RequestHeader("X-tenantId") String tenantName,
                                   @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                   @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                   @RequestParam(value = "qString", required = false) String qString,
                                   @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                   @RequestParam(value = "col", required = false, defaultValue = "titleId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstTitleRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByTitleNameAsc(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstTitleRepository.findByTitleNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByTitleNameAsc(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{titleId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("titleId") Long titleId) {
        TenantContext.setCurrentTenant(tenantName);
        MstTitle mstTitle = mstTitleRepository.getById(titleId);
        if (mstTitle != null) {
            mstTitle.setIsDeleted(true);
            mstTitleRepository.save(mstTitle);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName,
                            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                            @RequestParam(value = "size", defaultValue = "5", required = false) Integer size,
                            @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = mstTitleService.getMstTitleForDropdown(page, size, globalFilter);
        return items;
    }

}
            
