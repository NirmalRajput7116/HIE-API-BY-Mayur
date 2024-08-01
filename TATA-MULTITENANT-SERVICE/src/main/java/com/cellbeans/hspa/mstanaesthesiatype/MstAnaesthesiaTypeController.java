package com.cellbeans.hspa.mstanaesthesiatype;

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
@RequestMapping("/mst_anaesthesia_type")
public class MstAnaesthesiaTypeController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstAnaesthesiaTypeRepository mstAnaesthesiaTypeRepository;

    @Autowired
    private MstAnaesthesiaTypeService mstAnaesthesiaTypeService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstAnaesthesiaType mstAnaesthesiaType) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstAnaesthesiaType.getAtName() != null) {
            mstAnaesthesiaTypeRepository.save(mstAnaesthesiaType);
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
        List<MstAnaesthesiaType> records;
        records = mstAnaesthesiaTypeRepository.findByAtNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{atId}")
    public MstAnaesthesiaType read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("atId") Long atId) {
        TenantContext.setCurrentTenant(tenantName);
        MstAnaesthesiaType mstAnaesthesiaType = mstAnaesthesiaTypeRepository.getById(atId);
        return mstAnaesthesiaType;
    }

    @RequestMapping("update")
    public MstAnaesthesiaType update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstAnaesthesiaType mstAnaesthesiaType) {
        TenantContext.setCurrentTenant(tenantName);
        return mstAnaesthesiaTypeRepository.save(mstAnaesthesiaType);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstAnaesthesiaType> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "atId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstAnaesthesiaTypeRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstAnaesthesiaTypeRepository.findByAtNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{atId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("atId") Long atId) {
        TenantContext.setCurrentTenant(tenantName);
        MstAnaesthesiaType mstAnaesthesiaType = mstAnaesthesiaTypeRepository.getById(atId);
        if (mstAnaesthesiaType != null) {
            mstAnaesthesiaType.setIsDeleted(true);
            mstAnaesthesiaTypeRepository.save(mstAnaesthesiaType);
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
        List<Tuple> items = mstAnaesthesiaTypeService.getMstAnaesthesiaTypeForDropdown(page, size, globalFilter);
        return items;
    }

}
            
