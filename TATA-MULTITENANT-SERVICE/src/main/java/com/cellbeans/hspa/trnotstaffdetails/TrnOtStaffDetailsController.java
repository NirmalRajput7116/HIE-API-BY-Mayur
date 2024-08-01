package com.cellbeans.hspa.trnotstaffdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cellbeans.hspa.TenantContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trn_ot_staff_details")
public class TrnOtStaffDetailsController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnOtStaffDetailsRepository trnOtStaffDetailsRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOtStaffDetails trnOtStaffDetails) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnOtStaffDetails.getOsdStaffId().getStaffId() != 0L) {
            trnOtStaffDetailsRepository.save(trnOtStaffDetails);
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
    public Map<String, String> createbylistbyopdid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opdId") Long opdId, @RequestBody List<TrnOtStaffDetails> trnOtStaffDetails) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtStaffDetails curObj : trnOtStaffDetails) {
            if (curObj.getOsdProcedureId() != 0 && curObj.getOsdStaffId().getStaffId() != 0) {
                curObj.setOsdOpdId(opdId);
                trnOtStaffDetailsRepository.save(curObj);
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
    public Map<String, String> createbylistbyopsid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opsId") Long opsId, @RequestBody List<TrnOtStaffDetails> trnOtStaffDetails) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtStaffDetails curObj : trnOtStaffDetails) {
            if (curObj.getOsdProcedureId() != 0 && curObj.getOsdStaffId().getStaffId() != 0) {
                curObj.setOsdOpsId(opsId);
                trnOtStaffDetailsRepository.save(curObj);
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
//        List<TrnOtStaffDetails> records;
//        records = trnOtStaffDetailsRepository.findByOsdIdContains(key);
//        automap.put("record", records);
//        return automap;
//    }

    @RequestMapping("byid/{osdId}")
    public TrnOtStaffDetails read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("osdId") Long osdId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnOtStaffDetails trnOtStaffDetails = trnOtStaffDetailsRepository.getById(osdId);
        return trnOtStaffDetails;
    }

    @RequestMapping("byopsid/{opsId}")
    public List<TrnOtStaffDetails> byOpsId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opsId") Long opsId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtStaffDetailsRepository.findAllByOsdOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(opsId);
    }

    @RequestMapping("byopdid/{opdId}")
    public List<TrnOtStaffDetails> byOpdId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opdId") Long opdId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtStaffDetailsRepository.findAllByOsdOpdIdEqualsAndIsActiveTrueAndIsDeletedFalse(opdId);
    }

    @RequestMapping("byprocedureid/{procedureId}")
    public List<TrnOtStaffDetails> byProcedureId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("procedureId") Long procedureId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtStaffDetailsRepository.findAllByOsdProcedureIdEqualsAndIsActiveTrueAndIsDeletedFalse(procedureId);
    }
//    @RequestMapping("update")
//    public TrnOtStaffDetails update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOtStaffDetails trnOtStaffDetails) {
//        return trnOtStaffDetailsRepository.save(trnOtStaffDetails);
//    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOtStaffDetails trnOtStaffDetails) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnOtStaffDetails != null) {
            trnOtStaffDetailsRepository.save(trnOtStaffDetails);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("updatebylistbyopdid")
    public Map<String, String> updatebylistbyopdid(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TrnOtStaffDetails> trnOtStaffDetails) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtStaffDetails curObj : trnOtStaffDetails) {
            if (curObj.getOsdProcedureId() != 0 && curObj.getOsdStaffId().getStaffId() != 0) {
                trnOtStaffDetailsRepository.save(curObj);
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
//    @GetMapping
//    @RequestMapping("list")
//    public Iterable<TrnOtStaffDetails> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "osdId") String col) {
//
//        if (qString == null || qString.equals("")) {
//            return trnOtStaffDetailsRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//
//        }
//        else {
//
//            return trnOtStaffDetailsRepository.findByosdIdContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//
//        }
//
//    }

    @PutMapping("delete/{osdId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("osdId") Long osdId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnOtStaffDetails trnOtStaffDetails = trnOtStaffDetailsRepository.getById(osdId);
        if (trnOtStaffDetails != null) {
            trnOtStaffDetails.setIsDeleted(true);
            trnOtStaffDetailsRepository.save(trnOtStaffDetails);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
