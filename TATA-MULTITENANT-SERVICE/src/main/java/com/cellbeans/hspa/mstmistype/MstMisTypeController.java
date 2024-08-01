package com.cellbeans.hspa.mstmistype;

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
@RequestMapping("/mst_mis_type")
public class MstMisTypeController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstMisTypeRepository mstMisTypeRepository;

    @Autowired
    private MstMisTypeService mstMisTypeService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstMisType mstMisType) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstMisType.getMtName() != null) {
            mstMisTypeRepository.save(mstMisType);
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
        List<MstMisType> records;
        records = mstMisTypeRepository.findByMtNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{mtId}")
    public MstMisType read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("mtId") Long mtId) {
        TenantContext.setCurrentTenant(tenantName);
        MstMisType mstMisType = mstMisTypeRepository.getById(mtId);
        return mstMisType;
    }

    @RequestMapping("update")
    public MstMisType update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstMisType mstMisType) {
        TenantContext.setCurrentTenant(tenantName);
        return mstMisTypeRepository.save(mstMisType);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstMisType> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "mtId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstMisTypeRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstMisTypeRepository.findByMtNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{mtId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("mtId") Long mtId) {
        TenantContext.setCurrentTenant(tenantName);
        MstMisType mstMisType = mstMisTypeRepository.getById(mtId);
        if (mstMisType != null) {
            mstMisType.setIsDeleted(true);
            mstMisTypeRepository.save(mstMisType);
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
        List<Tuple> items = mstMisTypeService.getMstMisTypeForDropdown(page, size, globalFilter);
        return items;
    }

}
            
