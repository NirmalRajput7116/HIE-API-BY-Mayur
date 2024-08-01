package com.cellbeans.hspa.trnotproceduredetails;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.trnotchecklistdetails.TrnOtProcedureChecklist;
import com.cellbeans.hspa.trnotchecklistdetails.TrnOtProcedureChecklistRepository;
import com.cellbeans.hspa.trnotconsentsdetails.TrnOtConsentsDetails;
import com.cellbeans.hspa.trnotconsentsdetails.TrnOtConsentsDetailsRepository;
import com.cellbeans.hspa.trnotdoctordetails.TrnOtDoctorDetails;
import com.cellbeans.hspa.trnotdoctordetails.TrnOtDoctorDetailsRepository;
import com.cellbeans.hspa.trnotequipmentdetails.TrnOtEquipmentDetails;
import com.cellbeans.hspa.trnotequipmentdetails.TrnOtEquipmentDetailsRepository;
import com.cellbeans.hspa.trnotintraoperativeinstructiondetails.TrnOtIntraOperativeInstructionDetails;
import com.cellbeans.hspa.trnotintraoperativeinstructiondetails.TrnOtIntraOperativeInstructionDetailsRepository;
import com.cellbeans.hspa.trnotpostoperativeinstructiondetails.TrnOtPostOperativeInstructionDetails;
import com.cellbeans.hspa.trnotpostoperativeinstructiondetails.TrnOtPostOperativeInstructionDetailsRepository;
import com.cellbeans.hspa.trnotpreoperativeinstructiondetails.TrnOtPreOperativeInstructionDetails;
import com.cellbeans.hspa.trnotpreoperativeinstructiondetails.TrnOtPreOperativeInstructionDetailsRepository;
import com.cellbeans.hspa.trnotproceduredetail.TrnOtProcedureDetail;
import com.cellbeans.hspa.trnotproceduredetail.TrnOtProcedureDetailRepository;
import com.cellbeans.hspa.trnotservicedetails.TrnOtServiceDetails;
import com.cellbeans.hspa.trnotservicedetails.TrnOtServiceDetailsRepository;
import com.cellbeans.hspa.trnotstaffdetails.TrnOtStaffDetails;
import com.cellbeans.hspa.trnotstaffdetails.TrnOtStaffDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trn_ot_procedure_details/")
public class TrnOtProcedureDetailsController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnOtProcedureDetailsRepository trnOtProcedureDetailsRepository;
    @Autowired
    TrnOtProcedureChecklistRepository trnOtProcedureChecklistRepository;
    @Autowired
    TrnOtConsentsDetailsRepository trnOtConsentsDetailsRepository;
    @Autowired
    TrnOtDoctorDetailsRepository trnOtDoctorDetailsRepository;
    @Autowired
    TrnOtEquipmentDetailsRepository trnOtEquipmentDetailsRepository;
    @Autowired
    TrnOtIntraOperativeInstructionDetailsRepository trnOtIntraOperativeInstructionDetailsRepository;
    @Autowired
    TrnOtPostOperativeInstructionDetailsRepository trnOtPostOperativeInstructionDetailsRepository;
    @Autowired
    TrnOtPreOperativeInstructionDetailsRepository trnOtPreOperativeInstructionDetailsRepository;
    @Autowired
    TrnOtProcedureDetailRepository trnOtProcedureDetailRepository;
    @Autowired
    TrnOtServiceDetailsRepository trnOtServiceDetailsRepository;
    @Autowired
    TrnOtStaffDetailsRepository trnOtStaffDetailsRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOtProcedureDetails trnOtProcedureDetails) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnOtProcedureDetails.getOpdPatientId().getPatientId() != 0L) {
            TrnOtProcedureDetails obj = trnOtProcedureDetailsRepository.save(trnOtProcedureDetails);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            respMap.put("objId", String.valueOf(obj.getOpdId()));
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("createbyopsid")
    public Map<String, String> createByOpsId(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOtProcedureDetails trnOtProcedureDetails) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnOtProcedureDetails.getOpdPatientId().getPatientId() != 0L) {
            TrnOtProcedureDetails obj = trnOtProcedureDetailsRepository.save(trnOtProcedureDetails);
            long currOpsId = obj.getOpdOpsId();
            long currOpdId = obj.getOpdId();
            List<TrnOtProcedureChecklist> trnOtProcedureChecklist = trnOtProcedureChecklistRepository.findAllByOpcOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(currOpsId);
            for (int i = 0; i < trnOtProcedureChecklist.size(); i++) {
                TrnOtProcedureChecklist currObj = trnOtProcedureChecklist.get(i);
                currObj.setOpcOpdId(currOpdId);
                trnOtProcedureChecklistRepository.save(currObj);
            }
            List<TrnOtConsentsDetails> trnOtConsentsDetails = trnOtConsentsDetailsRepository.findAllByOcdOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(currOpsId);
            for (int i = 0; i < trnOtConsentsDetails.size(); i++) {
                TrnOtConsentsDetails currObj = trnOtConsentsDetails.get(i);
                currObj.setOcdOpdId(currOpdId);
                trnOtConsentsDetailsRepository.save(currObj);
            }
            List<TrnOtDoctorDetails> trnOtDoctorDetails = trnOtDoctorDetailsRepository.findAllByOddOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(currOpsId);
            for (int i = 0; i < trnOtDoctorDetails.size(); i++) {
                TrnOtDoctorDetails currObj = trnOtDoctorDetails.get(i);
                currObj.setOddOpdId(currOpdId);
                trnOtDoctorDetailsRepository.save(currObj);
            }
            List<TrnOtEquipmentDetails> trnOtEquipmentDetails = trnOtEquipmentDetailsRepository.findAllByOedOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(currOpsId);
            for (int i = 0; i < trnOtEquipmentDetails.size(); i++) {
                TrnOtEquipmentDetails currObj = trnOtEquipmentDetails.get(i);
                currObj.setOedOpdId(currOpdId);
                trnOtEquipmentDetailsRepository.save(currObj);
            }
            List<TrnOtIntraOperativeInstructionDetails> trnOtIntraOperativeInstructionDetails = trnOtIntraOperativeInstructionDetailsRepository.findAllByOioidOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(currOpsId);
            for (int i = 0; i < trnOtIntraOperativeInstructionDetails.size(); i++) {
                TrnOtIntraOperativeInstructionDetails currObj = trnOtIntraOperativeInstructionDetails.get(i);
                currObj.setOioidOpdId(currOpdId);
                trnOtIntraOperativeInstructionDetailsRepository.save(currObj);
            }
            List<TrnOtPostOperativeInstructionDetails> trnOtPostOperativeInstructionDetails = trnOtPostOperativeInstructionDetailsRepository.findAllByOpooidOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(currOpsId);
            for (int i = 0; i < trnOtPostOperativeInstructionDetails.size(); i++) {
                TrnOtPostOperativeInstructionDetails currObj = trnOtPostOperativeInstructionDetails.get(i);
                currObj.setOpooidOpdId(currOpdId);
                trnOtPostOperativeInstructionDetailsRepository.save(currObj);
            }
            List<TrnOtPreOperativeInstructionDetails> trnOtPreOperativeInstructionDetails = trnOtPreOperativeInstructionDetailsRepository.findAllByOpoidOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(currOpsId);
            for (int i = 0; i < trnOtPreOperativeInstructionDetails.size(); i++) {
                TrnOtPreOperativeInstructionDetails currObj = trnOtPreOperativeInstructionDetails.get(i);
                currObj.setOpoidOpdId(currOpdId);
                trnOtPreOperativeInstructionDetailsRepository.save(currObj);
            }
            List<TrnOtProcedureDetail> trnOtProcedureDetail = trnOtProcedureDetailRepository.findAllByOpOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(currOpsId);
            for (int i = 0; i < trnOtProcedureDetail.size(); i++) {
                TrnOtProcedureDetail currObj = trnOtProcedureDetail.get(i);
                currObj.setOpOpdId(currOpdId);
                trnOtProcedureDetailRepository.save(currObj);
            }
            List<TrnOtServiceDetails> trnOtServiceDetails = trnOtServiceDetailsRepository.findAllByOsrdOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(currOpsId);
            for (int i = 0; i < trnOtConsentsDetails.size(); i++) {
                TrnOtServiceDetails currObj = trnOtServiceDetails.get(i);
                currObj.setOsrdOpdId(currOpdId);
                trnOtServiceDetailsRepository.save(currObj);
            }
            List<TrnOtStaffDetails> trnOtStaffDetails = trnOtStaffDetailsRepository.findAllByOsdOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(currOpsId);
            for (int i = 0; i < trnOtStaffDetails.size(); i++) {
                TrnOtStaffDetails currObj = trnOtStaffDetails.get(i);
                currObj.setOsdOpdId(currOpdId);
                trnOtStaffDetailsRepository.save(currObj);
            }
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            respMap.put("objId", String.valueOf(obj.getOpdId()));
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{opdId}")
    public TrnOtProcedureDetails read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opdId") Long opdId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnOtProcedureDetails trnOtProcedureDetails = trnOtProcedureDetailsRepository.getById(opdId);
        return trnOtProcedureDetails;
    }

    @RequestMapping("bypatientid/{patientid}")
    public TrnOtProcedureDetails bypatientid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientid") Long patientid) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtProcedureDetailsRepository.findAllByOpdPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(patientid);
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOtProcedureDetails trnOtProcedureDetails) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnOtProcedureDetails != null) {
            trnOtProcedureDetailsRepository.save(trnOtProcedureDetails);
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
    public Iterable<TrnOtProcedureDetails> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "opdId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtProcedureDetailsRepository.findAllByOpdIsPerformedFalseAndIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @GetMapping
    @RequestMapping("getprocedureshedule")
    public List<TrnOtProcedureDetails> getprocedureshedule(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "otid", required = false) Long otid, @RequestParam(value = "proceduredate", required = false, defaultValue = "20") String proceduredate, @RequestParam(value = "ottableid", required = false) Long ottableid) {
        TenantContext.setCurrentTenant(tenantName);
//        System.out.println(otid + "==>" + proceduredate + "==>" + ottableid);
//        return trnOtProcedureDetailsRepository.findAllByOpdProcedureDateEqualsAndOpdOtIdOtIdEqualsAndOpdTableIdOttIdEqualsAndIsActiveTrueAndIsDeletedFalse(proceduredate, otid, ottableid);
        return trnOtProcedureDetailsRepository.findAll();
    }

    @PutMapping("delete/{opdId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opdId") Long opdId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnOtProcedureDetails trnOtProcedureDetails = trnOtProcedureDetailsRepository.getById(opdId);
        if (trnOtProcedureDetails != null) {
            trnOtProcedureDetails.setIsDeleted(true);
            trnOtProcedureDetailsRepository.save(trnOtProcedureDetails);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    //    @RequestMapping("updateotdchecklist")
//    public Map<String, String> updateChecklistByopdId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "opdId", required = false) Long opdId,@RequestBody List<TrnOtProcedureDetailsChecklist> trnOtProcedureDetailsChecklist) {
//        TrnOtProcedureDetails trnOtProcedureDetails = trnOtProcedureDetailsRepository.getById(opdId);
//        if(trnOtProcedureDetails!=null){
//            trnOtProcedureDetails.setOpdProcedureChecklist(trnOtProcedureDetailsChecklist);
//            trnOtProcedureDetailsRepository.save(trnOtProcedureDetails);
//            respMap.put("msg", "Operation Successful");
//            respMap.put("success", "1");
//        }else{
//            respMap.put("msg", "Operation Failed");
//            respMap.put("success", "0");
//        }
//        return respMap;
//    }
//
//    @RequestMapping("updateotdConsent")
//    public Map<String, String> updateotdConsent(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "opdId", required = false) Long opdId,@RequestBody List<TrnOtProcedureDetailsConsent> trnOtProcedureDetailsConsent) {
//        TrnOtProcedureDetails trnOtProcedureDetails = trnOtProcedureDetailsRepository.getById(opdId);
//        if(trnOtProcedureDetails!=null){
//            trnOtProcedureDetails.setOpdProcedureConsent(trnOtProcedureDetailsConsent);
//            trnOtProcedureDetailsRepository.save(trnOtProcedureDetails);
//            respMap.put("msg", "Operation Successful");
//            respMap.put("success", "1");
//        }else{
//            respMap.put("msg", "Operation Failed");
//            respMap.put("success", "0");
//        }
//        return respMap;
//    }
//
//    @RequestMapping("updateotdItem")
//    public Map<String, String> updateotdItem(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "opdId", required = false) Long opdId,@RequestBody List<TrnOtProcedureDetailsItem> trnOtProcedureDetailsItem) {
//        TrnOtProcedureDetails trnOtProcedureDetails = trnOtProcedureDetailsRepository.getById(opdId);
//        if(trnOtProcedureDetails!=null){
//            trnOtProcedureDetails.setOpdItem(trnOtProcedureDetailsItem);
//            trnOtProcedureDetailsRepository.save(trnOtProcedureDetails);
//            respMap.put("msg", "Operation Successful");
//            respMap.put("success", "1");
//        }else{
//            respMap.put("msg", "Operation Failed");
//            respMap.put("success", "0");
//        }
//        return respMap;
//    }
//
//    @RequestMapping("updateotdpreoperativeinstruction")
//    public Map<String, String> updateOtdPreOperativeInstruction(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "opdId", required = false) Long opdId,@RequestBody List<TrnOtProcedureDetailsPreOperativeInstruction> trnOtProcedureDetailsPreOperativeInstruction) {
//        TrnOtProcedureDetails trnOtProcedureDetails = trnOtProcedureDetailsRepository.getById(opdId);
//        if(trnOtProcedureDetails!=null){
//            trnOtProcedureDetails.setOpdPreOperativeInstruction(trnOtProcedureDetailsPreOperativeInstruction);
//            trnOtProcedureDetailsRepository.save(trnOtProcedureDetails);
//            respMap.put("msg", "Operation Successful");
//            respMap.put("success", "1");
//        }else{
//            respMap.put("msg", "Operation Failed");
//            respMap.put("success", "0");
//        }
//        return respMap;
//    }
//
//    @RequestMapping("updateotdintraoperativeinstruction")
//    public Map<String, String> updateOtdIntraOperativeInstruction(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "opdId", required = false) Long opdId,@RequestBody List<TrnOtProcedureDetailsIntraOperativeInstruction> trnOtProcedureDetailsIntraOperativeInstruction) {
//        TrnOtProcedureDetails trnOtProcedureDetails = trnOtProcedureDetailsRepository.getById(opdId);
//        if(trnOtProcedureDetails!=null){
//            trnOtProcedureDetails.setOpdIntraOperativeInstruction(trnOtProcedureDetailsIntraOperativeInstruction);
//            trnOtProcedureDetailsRepository.save(trnOtProcedureDetails);
//            respMap.put("msg", "Operation Successful");
//            respMap.put("success", "1");
//        }else{
//            respMap.put("msg", "Operation Failed");
//            respMap.put("success", "0");
//        }
//        return respMap;
//    }
//
//    @RequestMapping("updateotdpostoperativeinstruction")
//    public Map<String, String> updateOtdPostOperativeInstruction(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "opdId", required = false) Long opdId,@RequestBody List<TrnOtProcedureDetailsPostOperativeInstruction> trnOtProcedureDetailsPostOperativeInstruction) {
//        TrnOtProcedureDetails trnOtProcedureDetails = trnOtProcedureDetailsRepository.getById(opdId);
//        if(trnOtProcedureDetails!=null){
//            trnOtProcedureDetails.setOpdPostOperativeInstruction(trnOtProcedureDetailsPostOperativeInstruction);
//            trnOtProcedureDetailsRepository.save(trnOtProcedureDetails);
//            respMap.put("msg", "Operation Successful");
//            respMap.put("success", "1");
//        }else{
//            respMap.put("msg", "Operation Failed");
//            respMap.put("success", "0");
//        }
//        return respMap;
//    }
//
//    @RequestMapping("updateotdstaff")
//    public Map<String, String> updateOtdStaff(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "opdId", required = false) Long opdId,@RequestBody List<TrnOtProcedureDetailsStaff> trnOtProcedureDetailsStaff) {
//        TrnOtProcedureDetails trnOtProcedureDetails = trnOtProcedureDetailsRepository.getById(opdId);
//        if(trnOtProcedureDetails!=null){
//            trnOtProcedureDetails.setOpdProcedureStaff(trnOtProcedureDetailsStaff);
//            trnOtProcedureDetailsRepository.save(trnOtProcedureDetails);
//            trnOtProcedureDetailsRepository.save(trnOtProcedureDetails);
//            respMap.put("msg", "Operation Successful");
//            respMap.put("success", "1");
//        }else{
//            respMap.put("msg", "Operation Failed");
//            respMap.put("success", "0");
//        }
//        return respMap;
//    }
//
//    @RequestMapping("updateotddoctor")
//    public Map<String, String> updateOtdDoctor(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "opdId", required = false) Long opdId,@RequestBody List<TrnOtProcedureDetailsDoctor> trnOtProcedureDetailsDoctor) {
//        TrnOtProcedureDetails trnOtProcedureDetails = trnOtProcedureDetailsRepository.getById(opdId);
//        if(trnOtProcedureDetails!=null){
//            trnOtProcedureDetails.setOpdProcedureDoctor(trnOtProcedureDetailsDoctor);
//            trnOtProcedureDetailsRepository.save(trnOtProcedureDetails);
//            respMap.put("msg", "Operation Successful");
//            respMap.put("success", "1");
//        }else{
//            respMap.put("msg", "Operation Failed");
//            respMap.put("success", "0");
//        }
//        return respMap;
//    }
//
//    @RequestMapping("updateotdservice")
//    public Map<String, String> updateOtdService(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "opdId", required = false) Long opdId,@RequestBody List<TrnOtProcedureDetailsService> trnOtProcedureDetailsService) {
//        TrnOtProcedureDetails trnOtProcedureDetails = trnOtProcedureDetailsRepository.getById(opdId);
//        if(trnOtProcedureDetails!=null){
//            trnOtProcedureDetails.setOpdService(trnOtProcedureDetailsService);
//            trnOtProcedureDetailsRepository.save(trnOtProcedureDetails);
//            respMap.put("msg", "Operation Successful");
//            respMap.put("success", "1");
//        }else{
//            respMap.put("msg", "Operation Failed");
//            respMap.put("success", "0");
//        }
//        return respMap;
//    }
//
    @RequestMapping("updateotddetails/{opdId}/{othours}/{sdate}/{stime}/{edate}/{etime}")
    public Map<String, String> updateOtdDetails(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opdId") Long opdId, @PathVariable("othours") String othours, @PathVariable("sdate") String sdate, @PathVariable("stime") String stime, @PathVariable("edate") String edate, @PathVariable("etime") String etime) {
        TenantContext.setCurrentTenant(tenantName);
        TrnOtProcedureDetails trnOtProcedureDetails = trnOtProcedureDetailsRepository.getById(opdId);
        if (trnOtProcedureDetails != null) {
            trnOtProcedureDetails.setOpdStartDate(sdate);
            trnOtProcedureDetails.setOpdStartTime(stime);
            trnOtProcedureDetails.setOpdEndDate(edate);
            trnOtProcedureDetails.setOpdEndTime(etime);
            trnOtProcedureDetails.setOpdDuration(othours);
            trnOtProcedureDetailsRepository.save(trnOtProcedureDetails);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
