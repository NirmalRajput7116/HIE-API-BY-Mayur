package com.cellbeans.hspa.trnotequipmentdetails;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trn_ot_equipment_details")
public class TrnOtEquipmentDetailsController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnOtEquipmentDetailsRepository trnOtEquipmentDetailsRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOtEquipmentDetails trnOtEquipmentDetails) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnOtEquipmentDetails.getOedProcedureId() != 0L && trnOtEquipmentDetails.getOedInvItemId().getItemId() != 0L) {
            trnOtEquipmentDetailsRepository.save(trnOtEquipmentDetails);
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
    public Map<String, String> createbylistbyopdid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opdId") Long opdId, @RequestBody List<TrnOtEquipmentDetails> trnOtEquipmentDetails) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtEquipmentDetails curObj : trnOtEquipmentDetails) {
            if (curObj.getOedProcedureId() != 0 && curObj.getOedInvItemId().getItemId() != 0) {
                curObj.setOedOpdId(opdId);
                trnOtEquipmentDetailsRepository.save(curObj);
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
    public Map<String, String> createbylistbyopsid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opsId") Long opsId, @RequestBody List<TrnOtEquipmentDetails> trnOtEquipmentDetails) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtEquipmentDetails curObj : trnOtEquipmentDetails) {
            if (curObj.getOedProcedureId() != 0 && curObj.getOedInvItemId().getItemId() != 0) {
                curObj.setOedOpsId(opsId);
                trnOtEquipmentDetailsRepository.save(curObj);
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
//        List<TrnOtEquipmentDetails> records;
//        records = trnOtEquipmentDetailsRepository.findByOedIdContains(key);
//        automap.put("record", records);
//        return automap;
//    }

    @RequestMapping("byid/{oedId}")
    public TrnOtEquipmentDetails read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("oedId") Long oedId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnOtEquipmentDetails trnOtEquipmentDetails = trnOtEquipmentDetailsRepository.getById(oedId);
        return trnOtEquipmentDetails;
    }

    @RequestMapping("byopsid/{opsId}")
    public List<TrnOtEquipmentDetails> byOpsId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opsId") Long opsId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtEquipmentDetailsRepository.findAllByOedOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(opsId);
    }

    @RequestMapping("byopdid/{opdId}")
    public List<TrnOtEquipmentDetails> byOpdId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opdId") Long opdId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtEquipmentDetailsRepository.findAllByOedOpdIdEqualsAndIsActiveTrueAndIsDeletedFalse(opdId);
    }

    @RequestMapping("byprocedureid/{procedureId}")
    public List<TrnOtEquipmentDetails> byProcedureId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("procedureId") Long procedureId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtEquipmentDetailsRepository.findAllByOedProcedureIdEqualsAndIsActiveTrueAndIsDeletedFalse(procedureId);
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOtEquipmentDetails trnOtEquipmentDetails) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnOtEquipmentDetails != null) {
            trnOtEquipmentDetailsRepository.save(trnOtEquipmentDetails);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("updatebylistbyopdid")
    public Map<String, String> updatebylistbyopdid(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TrnOtEquipmentDetails> trnOtEquipmentDetails) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtEquipmentDetails curObj : trnOtEquipmentDetails) {
            if (curObj.getOedProcedureId() != 0 && curObj.getOedInvItemId().getItemId() != 0) {
                trnOtEquipmentDetailsRepository.save(curObj);
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
//    public Iterable<TrnOtEquipmentDetails> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "oedId") String col) {
//
//        if (qString == null || qString.equals("")) {
//            return trnOtEquipmentDetailsRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//
//        }
//        else {
//            return trnOtEquipmentDetailsRepository.findByoedIdContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//        }
//
//    }

    @RequestMapping("delete/{oedId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("oedId") Long oedId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnOtEquipmentDetails trnOtEquipmentDetails = trnOtEquipmentDetailsRepository.getById(oedId);
        if (trnOtEquipmentDetails != null) {
            trnOtEquipmentDetails.setIsDeleted(true);
            trnOtEquipmentDetailsRepository.save(trnOtEquipmentDetails);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
