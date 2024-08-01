package com.cellbeans.hspa.trnotpostoperativeinstructiondetails;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trn_ot_post_operative_instruction_details")
public class TrnOtPostOperativeInstructionDetailsController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnOtPostOperativeInstructionDetailsRepository trnOtPostOperativeInstructionDetailsRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOtPostOperativeInstructionDetails trnOtPostOperativeInstructionDetails) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnOtPostOperativeInstructionDetails.getOpooidProcedureId() != 0L && trnOtPostOperativeInstructionDetails.getOpooidPooiId().getPooiId() != 0L) {
            trnOtPostOperativeInstructionDetailsRepository.save(trnOtPostOperativeInstructionDetails);
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
    public Map<String, String> createbylistbyopdid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opdId") Long opdId, @RequestBody List<TrnOtPostOperativeInstructionDetails> trnOtPostOperativeInstructionDetails) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtPostOperativeInstructionDetails curObj : trnOtPostOperativeInstructionDetails) {
            if (curObj.getOpooidProcedureId() != 0 && curObj.getOpooidPooiId().getPooiId() != 0) {
                curObj.setOpooidOpdId(opdId);
                trnOtPostOperativeInstructionDetailsRepository.save(curObj);
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
    public Map<String, String> createbylistbyopsid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opsId") Long opsId, @RequestBody List<TrnOtPostOperativeInstructionDetails> trnOtPostOperativeInstructionDetails) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtPostOperativeInstructionDetails curObj : trnOtPostOperativeInstructionDetails) {
            if (curObj.getOpooidProcedureId() != 0 && curObj.getOpooidPooiId().getPooiId() != 0) {
                curObj.setOpooidOpsId(opsId);
                trnOtPostOperativeInstructionDetailsRepository.save(curObj);
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

    @RequestMapping("byid/{opooidId}")
    public TrnOtPostOperativeInstructionDetails read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opooid") Long opooid) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtPostOperativeInstructionDetailsRepository.getById(opooid);
    }

    @RequestMapping("byopsid/{opsId}")
    public List<TrnOtPostOperativeInstructionDetails> byOpsId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opsId") Long opsId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtPostOperativeInstructionDetailsRepository.findAllByOpooidOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(opsId);
    }

    @RequestMapping("byopdid/{opdId}")
    public List<TrnOtPostOperativeInstructionDetails> byOpdId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opdId") Long opdId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtPostOperativeInstructionDetailsRepository.findAllByOpooidOpdIdEqualsAndIsActiveTrueAndIsDeletedFalse(opdId);
    }

    @RequestMapping("byprocedureid/{procedureId}")
    public List<TrnOtPostOperativeInstructionDetails> byProcedureId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("procedureId") Long procedureId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtPostOperativeInstructionDetailsRepository.findAllByOpooidProcedureIdEqualsAndIsActiveTrueAndIsDeletedFalse(procedureId);
    }

    @RequestMapping("update")
    public TrnOtPostOperativeInstructionDetails update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOtPostOperativeInstructionDetails trnOtPostOperativeInstructionDetails) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtPostOperativeInstructionDetailsRepository.save(trnOtPostOperativeInstructionDetails);
    }

    @RequestMapping("updatebylistbyopdid")
    public Map<String, String> updatebylistbyopdid(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TrnOtPostOperativeInstructionDetails> trnOtPostOperativeInstructionDetails) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtPostOperativeInstructionDetails curObj : trnOtPostOperativeInstructionDetails) {
            if (curObj.getOpooidProcedureId() != 0 && curObj.getOpooidPooiId().getPooiId() != 0) {
                trnOtPostOperativeInstructionDetailsRepository.save(curObj);
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

    @RequestMapping("delete/{opooidId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opooidId") Long opooidId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnOtPostOperativeInstructionDetails trnOtPostOperativeInstructionDetails = trnOtPostOperativeInstructionDetailsRepository.getById(opooidId);
        if (trnOtPostOperativeInstructionDetails != null) {
            trnOtPostOperativeInstructionDetails.setIsDeleted(true);
            trnOtPostOperativeInstructionDetailsRepository.save(trnOtPostOperativeInstructionDetails);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
