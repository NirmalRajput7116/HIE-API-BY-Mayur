package com.cellbeans.hspa.trnotservicedetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cellbeans.hspa.TenantContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trn_ot_service_details")
public class
TrnOtServiceDetailsController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnOtServiceDetailsRepository trnOtServiceDetailsRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOtServiceDetails trnOtServiceDetails) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnOtServiceDetails.getOsrdProcedureId() != 0L && trnOtServiceDetails.getOsrdServiceId().getServiceId() != 0L) {
            trnOtServiceDetailsRepository.save(trnOtServiceDetails);
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
    public Map<String, String> createbylistbyopdid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opdId") Long opdId, @RequestBody List<TrnOtServiceDetails> trnOtServiceDetails) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtServiceDetails curObj : trnOtServiceDetails) {
            if (curObj.getOsrdProcedureId() != 0 && curObj.getOsrdServiceId().getServiceId() != 0) {
                curObj.setOsrdOpdId(opdId);
                trnOtServiceDetailsRepository.save(curObj);
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
    public Map<String, String> createbylistbyopsid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opsId") Long opsId, @RequestBody List<TrnOtServiceDetails> trnOtServiceDetails) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtServiceDetails curObj : trnOtServiceDetails) {
            if (curObj.getOsrdProcedureId() != 0 && curObj.getOsrdServiceId().getServiceId() != 0) {
                curObj.setOsrdOpsId(opsId);
                trnOtServiceDetailsRepository.save(curObj);
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

    @RequestMapping("byid/{osrdId}")
    public TrnOtServiceDetails read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("osrdId") Long osrdId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtServiceDetailsRepository.getById(osrdId);
    }

    @RequestMapping("byopsid/{opsId}")
    public List<TrnOtServiceDetails> byOpsId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opsId") Long opsId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtServiceDetailsRepository.findAllByOsrdOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(opsId);
    }

    @RequestMapping("byopdid/{opdId}")
    public List<TrnOtServiceDetails> byOpdId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opdId") Long opdId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtServiceDetailsRepository.findAllByOsrdOpdIdEqualsAndIsActiveTrueAndIsDeletedFalse(opdId);
    }

    @RequestMapping("byprocedureid/{procedureId}")
    public List<TrnOtServiceDetails> byProcedureId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("procedureId") Long procedureId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtServiceDetailsRepository.findAllByOsrdProcedureIdEqualsAndIsActiveTrueAndIsDeletedFalse(procedureId);
    }

    @RequestMapping("update")
    public TrnOtServiceDetails update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOtServiceDetails trnOtServiceDetails) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtServiceDetailsRepository.save(trnOtServiceDetails);
    }

    @RequestMapping("updatebylistbyopdid")
    public Map<String, String> updatebylistbyopdid(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TrnOtServiceDetails> trnOtServiceDetails) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtServiceDetails curObj : trnOtServiceDetails) {
            if (curObj.getOsrdProcedureId() != 0 && curObj.getOsrdServiceId().getServiceId() != 0) {
                trnOtServiceDetailsRepository.save(curObj);
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

    @RequestMapping("delete/{osrdId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("osrdId") Long osrdId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnOtServiceDetails trnOtServiceDetails = trnOtServiceDetailsRepository.getById(osrdId);
        if (trnOtServiceDetails != null) {
            trnOtServiceDetails.setIsDeleted(true);
            trnOtServiceDetailsRepository.save(trnOtServiceDetails);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }
}
