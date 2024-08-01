package com.cellbeans.hspa.trnotconsentsdetails;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trn_ot_consents_details")
public class TrnOtConsentsDetailsController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnOtConsentsDetailsRepository trnOtConsentsDetailsRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOtConsentsDetails trnOtConsentsDetails) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnOtConsentsDetails.getOcdProcedureId() != 0L && trnOtConsentsDetails.getOcdConsentId().getConsentId() != 0L) {
            trnOtConsentsDetailsRepository.save(trnOtConsentsDetails);
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
    public Map<String, String> createbylistbyopdid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opdId") Long opdId, @RequestBody List<TrnOtConsentsDetails> trnOtConsentsDetails) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtConsentsDetails curObj : trnOtConsentsDetails) {
            if (curObj.getOcdProcedureId() != 0 && curObj.getOcdConsentId().getConsentId() != 0) {
                curObj.setOcdOpdId(opdId);
                trnOtConsentsDetailsRepository.save(curObj);
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
    public Map<String, String> createbylistbyopsid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opsId") Long opsId, @RequestBody List<TrnOtConsentsDetails> trnOtConsentsDetails) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtConsentsDetails curObj : trnOtConsentsDetails) {
            if (curObj.getOcdProcedureId() != 0 && curObj.getOcdConsentId().getConsentId() != 0) {
                curObj.setOcdOpsId(opsId);
                trnOtConsentsDetailsRepository.save(curObj);
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
//    @RequestMapping("/autocomplete/{key}")
//    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
//        Map<String, Object> automap = new HashMap<String, Object>();
//        List<TrnOtConsentsDetails> records;
//        records = trnOtConsentsDetailsRepository.findByOcdIdContains(key);
//        automap.put("record", records);
//        return automap;
//    }

    @RequestMapping("byid/{ocdId}")
    public TrnOtConsentsDetails byid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ocdId") Long ocdId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnOtConsentsDetails trnOtConsentsDetails = trnOtConsentsDetailsRepository.getById(ocdId);
        return trnOtConsentsDetails;
    }

    @RequestMapping("byopsid/{opsId}")
    public List<TrnOtConsentsDetails> byOpsId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opsId") Long opsId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtConsentsDetailsRepository.findAllByOcdOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(opsId);
    }

    @RequestMapping("byopdid/{opdId}")
    public List<TrnOtConsentsDetails> byOpdId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opdId") Long opdId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtConsentsDetailsRepository.findAllByOcdOpdIdEqualsAndIsActiveTrueAndIsDeletedFalse(opdId);
    }

    @RequestMapping("byprocedureid/{procedureId}")
    public List<TrnOtConsentsDetails> byProcedureId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("procedureId") Long procedureId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtConsentsDetailsRepository.findAllByOcdProcedureIdEqualsAndIsActiveTrueAndIsDeletedFalse(procedureId);
    }

    @RequestMapping("update")
    public TrnOtConsentsDetails update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOtConsentsDetails trnOtConsentsDetails) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtConsentsDetailsRepository.save(trnOtConsentsDetails);
    }

    @RequestMapping("updatebylistbyopdid")
    public Map<String, String> updatebylistbyopdid(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TrnOtConsentsDetails> trnOtConsentsDetails) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtConsentsDetails curObj : trnOtConsentsDetails) {
            if (curObj.getOcdProcedureId() != 0 && curObj.getOcdConsentId().getConsentId() != 0) {
                trnOtConsentsDetailsRepository.save(curObj);
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

    @RequestMapping("delete/{ocdId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ocdId") Long ocdId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnOtConsentsDetails trnOtConsentsDetails = trnOtConsentsDetailsRepository.getById(ocdId);
        if (trnOtConsentsDetails != null) {
            trnOtConsentsDetails.setIsDeleted(true);
            trnOtConsentsDetailsRepository.save(trnOtConsentsDetails);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
