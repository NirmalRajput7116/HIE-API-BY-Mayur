package com.cellbeans.hspa.trnotdoctordetails;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trn_ot_doctor_details")
public class TrnOtDoctorDetailsController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnOtDoctorDetailsRepository trnOtDoctorDetailsRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOtDoctorDetails trnOtDoctorDetails) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnOtDoctorDetails.getOddProcedureId() != 0L && trnOtDoctorDetails.getOddStaffId().getStaffId() != 0L) {
            trnOtDoctorDetailsRepository.save(trnOtDoctorDetails);
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
    public Map<String, String> createbylistbyopdid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opdId") Long opdId, @RequestBody List<TrnOtDoctorDetails> trnOtDoctorDetails) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtDoctorDetails curObj : trnOtDoctorDetails) {
            if (curObj.getOddProcedureId() != 0 && curObj.getOddStaffId().getStaffId() != 0) {
                curObj.setOddOpdId(opdId);
                trnOtDoctorDetailsRepository.save(curObj);
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
    public Map<String, String> createbylistbyopsid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opsId") Long opsId, @RequestBody List<TrnOtDoctorDetails> trnOtDoctorDetails) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtDoctorDetails curObj : trnOtDoctorDetails) {
            if (curObj.getOddProcedureId() != 0 && curObj.getOddStaffId().getStaffId() != 0) {
                curObj.setOddOpsId(opsId);
                trnOtDoctorDetailsRepository.save(curObj);
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
//        List<TrnOtDoctorDetails> records;
//        records = trnOtDoctorDetailsRepository.findByOdIdContains(key);
//        automap.put("record", records);
//        return automap;
//    }

    @RequestMapping("byid/{odId}")
    public TrnOtDoctorDetails read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("odId") Long odId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnOtDoctorDetails trnOtDoctorDetails = trnOtDoctorDetailsRepository.getById(odId);
        return trnOtDoctorDetails;
    }

    @RequestMapping("byopsid/{opsId}")
    public List<TrnOtDoctorDetails> byOpsId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opsId") Long opsId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtDoctorDetailsRepository.findAllByOddOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(opsId);
    }

    @RequestMapping("byopdid/{opdId}")
    public List<TrnOtDoctorDetails> byOpdId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opdId") Long opdId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtDoctorDetailsRepository.findAllByOddOpdIdEqualsAndIsActiveTrueAndIsDeletedFalse(opdId);
    }

    @RequestMapping("byprocedureid/{procedureId}")
    public List<TrnOtDoctorDetails> byProcedureId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("procedureId") Long procedureId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtDoctorDetailsRepository.findAllByOddProcedureIdEqualsAndIsActiveTrueAndIsDeletedFalse(procedureId);
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOtDoctorDetails trnOtDoctorDetails) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnOtDoctorDetails != null) {
            trnOtDoctorDetailsRepository.save(trnOtDoctorDetails);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("updatebylistbyopdid")
    public Map<String, String> updatebylistbyopdid(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TrnOtDoctorDetails> trnOtDoctorDetails) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtDoctorDetails curObj : trnOtDoctorDetails) {
            if (curObj.getOddProcedureId() != 0 && curObj.getOddStaffId().getStaffId() != 0) {
                trnOtDoctorDetailsRepository.save(curObj);
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
//    public Iterable<TrnOtDoctorDetails> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "odId") String col) {
//
//        if (qString == null || qString.equals("")) {
//            return trnOtDoctorDetailsRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//        }
//        else {
//            return trnOtDoctorDetailsRepository.findByodIdContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//        }
//    }

    @RequestMapping("delete/{odId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("odId") Long odId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnOtDoctorDetails trnOtDoctorDetails = trnOtDoctorDetailsRepository.getById(odId);
        if (trnOtDoctorDetails != null) {
            trnOtDoctorDetails.setIsDeleted(true);
            trnOtDoctorDetailsRepository.save(trnOtDoctorDetails);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
