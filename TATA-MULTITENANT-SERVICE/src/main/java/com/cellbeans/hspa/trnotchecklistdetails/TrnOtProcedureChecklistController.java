package com.cellbeans.hspa.trnotchecklistdetails;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trn_procedure_checklist")
public class TrnOtProcedureChecklistController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnOtProcedureChecklistRepository trnOtProcedureChecklistRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOtProcedureChecklist trnotprocedurechecklist) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnotprocedurechecklist.getOpcProcedureId() != 0 && trnotprocedurechecklist.getOpcChecklistId().getPcId() != 0) {
            trnOtProcedureChecklistRepository.save(trnotprocedurechecklist);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("createbylistbyopdid/{opdId}")
    public Map<String, String> createbylistbyopdid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opdId") Long opdId, @RequestBody List<TrnOtProcedureChecklist> trnotprocedurechecklist) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtProcedureChecklist curObj : trnotprocedurechecklist) {
            if (curObj.getOpcProcedureId() != 0 && curObj.getOpcChecklistId().getPcId() != 0) {
                curObj.setOpcOpdId(opdId);
                trnOtProcedureChecklistRepository.save(curObj);
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Failed To Add Null Field");
                return respMap;
            }
        }
        respMap.put("success", "1");
        respMap.put("msg", "Added Successfully");
        return respMap;
    }

    @RequestMapping("createbylistbyopsid/{opsId}")
    public Map<String, String> createbylistbyopsid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opsId") Long opsId, @RequestBody List<TrnOtProcedureChecklist> trnotprocedurechecklist) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtProcedureChecklist curObj : trnotprocedurechecklist) {
            if (curObj.getOpcProcedureId() != 0 && curObj.getOpcChecklistId().getPcId() != 0) {
                curObj.setOpcOpsId(opsId);
                trnOtProcedureChecklistRepository.save(curObj);
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Failed To Add Null Field");
                return respMap;
            }
        }
        respMap.put("success", "1");
        respMap.put("msg", "Added Successfully");
        return respMap;
    }

    @RequestMapping("byopsid/{opsId}")
    public List<TrnOtProcedureChecklist> byOpsId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opsId") Long opsId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtProcedureChecklistRepository.findAllByOpcOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(opsId);
    }

    @RequestMapping("byopdid/{opdId}")
    public List<TrnOtProcedureChecklist> byOpdId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opdId") Long opdId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtProcedureChecklistRepository.findAllByOpcOpdIdEqualsAndIsActiveTrueAndIsDeletedFalse(opdId);
    }

    @RequestMapping("byprocedureid/{procedureId}")
    public List<TrnOtProcedureChecklist> byProcedureId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("procedureId") Long procedureId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtProcedureChecklistRepository.findAllByOpcProcedureIdEqualsAndIsActiveTrueAndIsDeletedFalse(procedureId);
    }

    @RequestMapping("byid/{opcId}")
    public TrnOtProcedureChecklist byid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opcId") Long opcId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnOtProcedureChecklist trnOtProcedureChecklist = trnOtProcedureChecklistRepository.getById(opcId);
        return trnOtProcedureChecklist;
    }

    @RequestMapping("update")
    public TrnOtProcedureChecklist update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOtProcedureChecklist trnOtProcedureChecklist) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtProcedureChecklistRepository.save(trnOtProcedureChecklist);
    }

    @RequestMapping("updatebylistbyopdid")
    public Map<String, String> updatebylistbyopdid(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TrnOtProcedureChecklist> trnotprocedurechecklist) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtProcedureChecklist curObj : trnotprocedurechecklist) {
            if (curObj.getOpcProcedureId() != 0 && curObj.getOpcChecklistId().getPcId() != 0) {
                trnOtProcedureChecklistRepository.save(curObj);
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Failed To Update Null Field");
                return respMap;
            }
        }
        respMap.put("success", "1");
        respMap.put("msg", "Updated Successfully");
        return respMap;
    }

    @RequestMapping("delete/{opcId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opcId") Long opcId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnOtProcedureChecklist trnOtProcedureChecklist = trnOtProcedureChecklistRepository.getById(opcId);
        if (trnOtProcedureChecklist != null) {
            trnOtProcedureChecklist.setIsDeleted(true);
            trnOtProcedureChecklistRepository.save(trnOtProcedureChecklist);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
