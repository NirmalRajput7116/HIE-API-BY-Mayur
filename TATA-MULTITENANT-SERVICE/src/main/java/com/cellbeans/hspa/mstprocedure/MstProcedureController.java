package com.cellbeans.hspa.mstprocedure;

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
@RequestMapping("/mst_procedure")
public class MstProcedureController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstProcedureRepository mstProcedureRepository;

    @Autowired
    private MstProcedureService mstProcedureService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstProcedure mstProcedure) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstProcedure.getProcedureName() != null) {
            mstProcedureRepository.save(mstProcedure);
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
        List<MstProcedure> records;
        records = mstProcedureRepository.findByProcedureNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{procedureId}")
    public MstProcedure read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("procedureId") Long procedureId) {
        TenantContext.setCurrentTenant(tenantName);
        MstProcedure mstProcedure = mstProcedureRepository.getById(procedureId);
        return mstProcedure;
    }

    @RequestMapping("update")
    public MstProcedure update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstProcedure mstProcedure) {
        TenantContext.setCurrentTenant(tenantName);
        return mstProcedureRepository.save(mstProcedure);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstProcedure> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "procedureId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstProcedureRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return mstProcedureRepository.findByProcedureNameContainsAndIsActiveTrueAndIsDeletedFalseOrProcedureDurationContainsAndIsActiveTrueAndIsDeletedFalseOrProcedureNoteContainsAndIsActiveTrueAndIsDeletedFalseOrProcedureAnaesthesiaTypeAtNameContainsAndIsActiveTrueAndIsDeletedFalseOrProcedureOperationTableOttNameContainsAndIsActiveTrueAndIsDeletedFalseOrAndProcedureOperationTheatreOtNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, qString, qString, qString, qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @PutMapping("delete/{procedureId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("procedureId") Long procedureId) {
        TenantContext.setCurrentTenant(tenantName);
        MstProcedure mstProcedure = mstProcedureRepository.getById(procedureId);
        if (mstProcedure != null) {
            mstProcedure.setIsDeleted(true);
            mstProcedureRepository.save(mstProcedure);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    //priyanka dont comment this method
    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter, @RequestParam(value = "specialityId", required = false) long specialityId) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = mstProcedureService.getMstProcedureForDropdown(page, size, globalFilter, specialityId);
        return items;
    }

}
            









