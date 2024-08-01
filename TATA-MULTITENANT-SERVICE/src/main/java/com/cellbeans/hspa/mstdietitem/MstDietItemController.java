package com.cellbeans.hspa.mstdietitem;

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
@RequestMapping("/mst_diet_item")
public class MstDietItemController {

    @Autowired
    MstDietItemRepository mstDietItemRepository;
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    private MstDietItemService mstDietItemService;

    @RequestMapping("/diet_item_list/{id}/{query}")
    public List<MstDietItem> diet_item_list(@RequestHeader("X-tenantId") String tenantName, @PathVariable List<Integer> id, @PathVariable String query) {
        TenantContext.setCurrentTenant(tenantName);
        return mstDietItemRepository.findByDiDietPatientTypeDpcIdInAndDiItemNameContains(id, query);
    }

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDietItem mstDietItem) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstDietItem.getDiItemName() != null) {
            mstDietItem.setDiItemName(mstDietItem.getDiItemName().trim());
            MstDietItem mstDietItemObject = mstDietItemRepository.findByDiItemNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstDietItem.getDiItemName());
            if (mstDietItemObject == null) {
                mstDietItem.setIsActive(true);
                mstDietItem.setIsDeleted(false);
                mstDietItemRepository.save(mstDietItem);
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
        List<MstDietItem> records;
        records = mstDietItemRepository.findByDiItemNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{diId}")
    public MstDietItem read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("diId") Long diId) {
        TenantContext.setCurrentTenant(tenantName);
        MstDietItem mstDietItem = mstDietItemRepository.getById(diId);
        return mstDietItem;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDietItem mstDietItem) {
        TenantContext.setCurrentTenant(tenantName);
        mstDietItem.setDiItemName(mstDietItem.getDiItemName().trim());
        MstDietItem mstDietItemObject = mstDietItemRepository.findByDiItemNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstDietItem.getDiItemName());
        if (mstDietItemObject == null) {
            mstDietItem.setIsActive(true);
            mstDietItem.setIsDeleted(false);
            mstDietItemRepository.save(mstDietItem);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstDietItemObject.getDiId() == mstDietItem.getDiId()) {
            mstDietItem.setIsActive(true);
            mstDietItem.setIsDeleted(false);
            mstDietItemRepository.save(mstDietItem);
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
    public Iterable<MstDietItem> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "diId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstDietItemRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstDietItemRepository.findByDiItemNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

    @PutMapping("delete/{diId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("diId") Long diId) {
        TenantContext.setCurrentTenant(tenantName);
        MstDietItem mstDietItem = mstDietItemRepository.getById(diId);
        if (mstDietItem != null) {
            mstDietItem.setIsDeleted(true);
            mstDietItemRepository.save(mstDietItem);
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
        List<Tuple> items = mstDietItemService.getMstDietItemForDropdown(page, size, globalFilter);
        return items;
    }

}
