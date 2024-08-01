package com.cellbeans.hspa.mstproceduretype;

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
@RequestMapping("/mst_procedure_type")
public class MstProcedureTypeController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstProcedureTypeRepository mstProcedureTypeRepository;

    @Autowired
    private MstProcedureTypeService mstProcedureTypeService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstProcedureType mstProcedureType) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstProcedureType.getPtName() != null) {
            mstProcedureTypeRepository.save(mstProcedureType);
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
        List<MstProcedureType> records;
        records = mstProcedureTypeRepository.findByPtNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{ptId}")
    public MstProcedureType read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ptId") Long ptId) {
        TenantContext.setCurrentTenant(tenantName);
        MstProcedureType mstProcedureType = mstProcedureTypeRepository.getById(ptId);
        return mstProcedureType;
    }

    @RequestMapping("update")
    public MstProcedureType update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstProcedureType mstProcedureType) {
        TenantContext.setCurrentTenant(tenantName);
        return mstProcedureTypeRepository.save(mstProcedureType);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstProcedureType> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ptId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstProcedureTypeRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstProcedureTypeRepository.findByPtNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{ptId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ptId") Long ptId) {
        TenantContext.setCurrentTenant(tenantName);
        MstProcedureType mstProcedureType = mstProcedureTypeRepository.getById(ptId);
        if (mstProcedureType != null) {
            mstProcedureType.setIsDeleted(true);
            mstProcedureTypeRepository.save(mstProcedureType);
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
        List<Tuple> items = mstProcedureTypeService.getMstProcedureTypeForDropdown(page, size, globalFilter);
        return items;
    }

}
            
