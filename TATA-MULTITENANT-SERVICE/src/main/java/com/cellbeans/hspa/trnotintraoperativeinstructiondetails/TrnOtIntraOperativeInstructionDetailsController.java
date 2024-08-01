package com.cellbeans.hspa.trnotintraoperativeinstructiondetails;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trn_ot_intra_operative_instruction_details")
public class TrnOtIntraOperativeInstructionDetailsController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnOtIntraOperativeInstructionDetailsRepository trnOtIntraOperativeInstructionDetailsRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOtIntraOperativeInstructionDetails trnOtIntraOperativeInstructionDetails) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnOtIntraOperativeInstructionDetails.getOioidProcedureId() != 0L && trnOtIntraOperativeInstructionDetails.getOioidIoiId().getIoiId() != 0L) {
            trnOtIntraOperativeInstructionDetailsRepository.save(trnOtIntraOperativeInstructionDetails);
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
    public Map<String, String> createbylistbyopdid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opdId") Long opdId, @RequestBody List<TrnOtIntraOperativeInstructionDetails> trnOtIntraOperativeInstructionDetails) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtIntraOperativeInstructionDetails curObj : trnOtIntraOperativeInstructionDetails) {
            if (curObj.getOioidProcedureId() != 0 && curObj.getOioidIoiId().getIoiId() != 0) {
                curObj.setOioidOpdId(opdId);
                trnOtIntraOperativeInstructionDetailsRepository.save(curObj);
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
    public Map<String, String> createbylistbyopsid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opsId") Long opsId, @RequestBody List<TrnOtIntraOperativeInstructionDetails> trnOtIntraOperativeInstructionDetails) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtIntraOperativeInstructionDetails curObj : trnOtIntraOperativeInstructionDetails) {
            if (curObj.getOioidProcedureId() != 0 && curObj.getOioidIoiId().getIoiId() != 0) {
                curObj.setOioidOpsId(opsId);
                trnOtIntraOperativeInstructionDetailsRepository.save(curObj);
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

    @RequestMapping("byid/{oioidId}")
    public TrnOtIntraOperativeInstructionDetails read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("oioidId") Long oioidId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnOtIntraOperativeInstructionDetails trnOtIntraOperativeInstructionDetails = trnOtIntraOperativeInstructionDetailsRepository.getById(oioidId);
        return trnOtIntraOperativeInstructionDetails;
    }

    @RequestMapping("byopsid/{opsId}")
    public List<TrnOtIntraOperativeInstructionDetails> byOpsId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opsId") Long opsId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtIntraOperativeInstructionDetailsRepository.findAllByOioidOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(opsId);
    }

    @RequestMapping("byopdid/{opdId}")
    public List<TrnOtIntraOperativeInstructionDetails> byOpdId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opdId") Long opdId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtIntraOperativeInstructionDetailsRepository.findAllByOioidOpdIdEqualsAndIsActiveTrueAndIsDeletedFalse(opdId);
    }

    @RequestMapping("byprocedureid/{procedureId}")
    public List<TrnOtIntraOperativeInstructionDetails> byProcedureId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("procedureId") Long procedureId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtIntraOperativeInstructionDetailsRepository.findAllByOioidProcedureIdEqualsAndIsActiveTrueAndIsDeletedFalse(procedureId);
    }

    @RequestMapping("update")
    public TrnOtIntraOperativeInstructionDetails update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOtIntraOperativeInstructionDetails trnOtIntraOperativeInstructionDetails) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtIntraOperativeInstructionDetailsRepository.save(trnOtIntraOperativeInstructionDetails);
    }

    @RequestMapping("updatebylistbyopdid")
    public Map<String, String> updatebylistbyopdid(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TrnOtIntraOperativeInstructionDetails> trnOtIntraOperativeInstructionDetails) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtIntraOperativeInstructionDetails curObj : trnOtIntraOperativeInstructionDetails) {
            if (curObj.getOioidProcedureId() != 0 && curObj.getOioidIoiId().getIoiId() != 0) {
                trnOtIntraOperativeInstructionDetailsRepository.save(curObj);
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

    @RequestMapping("delete/{oioidId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("oioidId") Long oioidId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnOtIntraOperativeInstructionDetails trnOtIntraOperativeInstructionDetails = trnOtIntraOperativeInstructionDetailsRepository.getById(oioidId);
        if (trnOtIntraOperativeInstructionDetails != null) {
            trnOtIntraOperativeInstructionDetails.setIsDeleted(true);
            trnOtIntraOperativeInstructionDetailsRepository.save(trnOtIntraOperativeInstructionDetails);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
