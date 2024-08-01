package com.cellbeans.hspa.trnotpreoperativeinstructiondetails;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trn_ot_pre_operative_instruction_details")
public class TrnOtPreOperativeInstructionDetailsController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnOtPreOperativeInstructionDetailsRepository trnOtPreOperativeInstructionDetailsRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOtPreOperativeInstructionDetails trnOtPreOperativeInstructionDetails) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnOtPreOperativeInstructionDetails.getOpoidProcedureId() != 0L && trnOtPreOperativeInstructionDetails.getOpoidPoiId().getPoiId() != 0L) {
            trnOtPreOperativeInstructionDetailsRepository.save(trnOtPreOperativeInstructionDetails);
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
    public Map<String, String> createbylistbyopdid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opdId") Long opdId, @RequestBody List<TrnOtPreOperativeInstructionDetails> trnOtPreOperativeInstructionDetails) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtPreOperativeInstructionDetails curObj : trnOtPreOperativeInstructionDetails) {
            if (curObj.getOpoidProcedureId() != 0 && curObj.getOpoidPoiId().getPoiId() != 0) {
                curObj.setOpoidOpdId(opdId);
                trnOtPreOperativeInstructionDetailsRepository.save(curObj);
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
    public Map<String, String> createbylistbyopsid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opsId") Long opsId, @RequestBody List<TrnOtPreOperativeInstructionDetails> trnOtPreOperativeInstructionDetails) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtPreOperativeInstructionDetails curObj : trnOtPreOperativeInstructionDetails) {
            if (curObj.getOpoidProcedureId() != 0 && curObj.getOpoidPoiId().getPoiId() != 0) {
                curObj.setOpoidOpsId(opsId);
                trnOtPreOperativeInstructionDetailsRepository.save(curObj);
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

    @RequestMapping("byid/{opoidId}")
    public TrnOtPreOperativeInstructionDetails read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opoidId") Long opoidId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtPreOperativeInstructionDetailsRepository.getById(opoidId);
    }

    @RequestMapping("byopsid/{opsId}")
    public List<TrnOtPreOperativeInstructionDetails> byOpsId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opsId") Long opsId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtPreOperativeInstructionDetailsRepository.findAllByOpoidOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(opsId);
    }

    @RequestMapping("byopdid/{opdId}")
    public List<TrnOtPreOperativeInstructionDetails> byOpdId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opdId") Long opdId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtPreOperativeInstructionDetailsRepository.findAllByOpoidOpdIdEqualsAndIsActiveTrueAndIsDeletedFalse(opdId);
    }

    @RequestMapping("byprocedureid/{procedureId}")
    public List<TrnOtPreOperativeInstructionDetails> byProcedureId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("procedureId") Long procedureId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtPreOperativeInstructionDetailsRepository.findAllByOpoidProcedureIdEqualsAndIsActiveTrueAndIsDeletedFalse(procedureId);
    }

    @RequestMapping("update")
    public TrnOtPreOperativeInstructionDetails update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOtPreOperativeInstructionDetails trnOtPreOperativeInstructionDetails) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtPreOperativeInstructionDetailsRepository.save(trnOtPreOperativeInstructionDetails);
    }

    @RequestMapping("updatebylistbyopdid")
    public Map<String, String> updatebylistbyopdid(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TrnOtPreOperativeInstructionDetails> trnOtPreOperativeInstructionDetails) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtPreOperativeInstructionDetails curObj : trnOtPreOperativeInstructionDetails) {
            if (curObj.getOpoidProcedureId() != 0 && curObj.getOpoidPoiId().getPoiId() != 0) {
                trnOtPreOperativeInstructionDetailsRepository.save(curObj);
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

    @RequestMapping("delete/{opoidId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opoidId") Long opoidId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnOtPreOperativeInstructionDetails trnOtPreOperativeInstructionDetails = trnOtPreOperativeInstructionDetailsRepository.getById(opoidId);
        if (trnOtPreOperativeInstructionDetails != null) {
            trnOtPreOperativeInstructionDetails.setIsDeleted(true);
            trnOtPreOperativeInstructionDetailsRepository.save(trnOtPreOperativeInstructionDetails);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
