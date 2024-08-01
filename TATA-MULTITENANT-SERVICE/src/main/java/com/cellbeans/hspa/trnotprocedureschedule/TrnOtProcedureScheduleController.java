package com.cellbeans.hspa.trnotprocedureschedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import com.cellbeans.hspa.TenantContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trn_ot_procedure_schedule/")
public class TrnOtProcedureScheduleController {
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnOtProcedureScheduleRepository trnOtProcedureScheduleRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOtProcedureSchedule trnOtProcedureSchedule) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnOtProcedureSchedule.getOpsPatientId().getPatientId() != 0L) {
            TrnOtProcedureSchedule obj = trnOtProcedureScheduleRepository.save(trnOtProcedureSchedule);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            respMap.put("objId", String.valueOf(obj.getOpsId()));
            return respMap;

        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{opsId}")
    public TrnOtProcedureSchedule read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opsId") Long opsId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnOtProcedureSchedule obj = trnOtProcedureScheduleRepository.getById(opsId);
        return trnOtProcedureScheduleRepository.getById(opsId);
    }

    @RequestMapping("bypatientid/{patientid}")
    public TrnOtProcedureSchedule bypatientid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientid") Long patientid) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtProcedureScheduleRepository.findAllByOpsPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(patientid);
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOtProcedureSchedule trnOtProcedureSchedule) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnOtProcedureSchedule != null) {
            trnOtProcedureScheduleRepository.save(trnOtProcedureSchedule);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("updateisschedule/{opsId}")
    public Map<String, String> updateIsSchedule(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opsId") Long opsId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnOtProcedureSchedule trnOtProcedureSchedule = trnOtProcedureScheduleRepository.getById(opsId);
        if (trnOtProcedureSchedule != null) {
            trnOtProcedureSchedule.setOpsIsSchedule(true);
            trnOtProcedureScheduleRepository.save(trnOtProcedureSchedule);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TrnOtProcedureSchedule> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "opsId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtProcedureScheduleRepository.findAllByOpsIsScheduleFalseAndIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @GetMapping
    @RequestMapping("getprocedureshedule")
    public List<TrnOtProcedureSchedule> getprocedureshedule(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "otid", required = false) Long otid, @RequestParam(value = "proceduredate", required = false, defaultValue = "20") String proceduredate, @RequestParam(value = "ottableid", required = false) Long ottableid) {
        TenantContext.setCurrentTenant(tenantName);
//        System.out.println(otid + "==>" + proceduredate + "==>" + ottableid);
//        return trnOtProcedureScheduleRepository.findAllByOpsProcedureDateEqualsAndOpsOtIdOtIdEqualsAndOpsTableIdOttIdEqualsAndIsActiveTrueAndIsDeletedFalse(proceduredate, otid, ottableid);
        return trnOtProcedureScheduleRepository.findAll();
    }

    @PutMapping("delete/{opsId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opsId") Long opsId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnOtProcedureSchedule trnOtProcedureSchedule = trnOtProcedureScheduleRepository.getById(opsId);
        if (trnOtProcedureSchedule != null) {
            trnOtProcedureSchedule.setIsDeleted(true);
            trnOtProcedureScheduleRepository.save(trnOtProcedureSchedule);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }
//    @RequestMapping("createbylist")
//    public JSONArray createbylist(@RequestHeader("X-tenantId") String tenantName, @RequestBody JSONArray jsonArray) {
//
//        System.out.println("jsona array : "  + jsonArray.toString());
//
//        return null;
//    }

}
            
