package com.cellbeans.hspa.trnotproceduredetail;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.invitem.InvItem;
import com.cellbeans.hspa.mbillservice.MbillService;
import com.cellbeans.hspa.mstconsent.MstConsent;
import com.cellbeans.hspa.mstintraoperativeinstructions.MstIntraOperativeInstructions;
import com.cellbeans.hspa.mstpostoperativeinstructions.MstPostOperativeInstructions;
import com.cellbeans.hspa.mstpreoperativeinstructions.MstPreOperativeInstructions;
import com.cellbeans.hspa.mstprocedure.MstProcedure;
import com.cellbeans.hspa.mstprocedure.MstProcedureRepository;
import com.cellbeans.hspa.mstprocedurechecklist.MstProcedureChecklist;
import com.cellbeans.hspa.mststaff.MstStaff;
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
import com.cellbeans.hspa.trnotservicedetails.TrnOtServiceDetails;
import com.cellbeans.hspa.trnotservicedetails.TrnOtServiceDetailsRepository;
import com.cellbeans.hspa.trnotstaffdetails.TrnOtStaffDetails;
import com.cellbeans.hspa.trnotstaffdetails.TrnOtStaffDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trn_ot_procedure_detail")
public class TrnOtProcedureDetailController {
    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    TrnOtProcedureDetailRepository trnOtProcedureDetailRepository;

    @Autowired
    MstProcedureRepository mstProcedureRepository;

    @Autowired
    TrnOtProcedureChecklistRepository trnOtProcedureChecklistRepository;
    @Autowired
    TrnOtConsentsDetailsRepository trnOtConsentsDetailsRepository;
    @Autowired
    TrnOtDoctorDetailsRepository trnOtDoctorDetailsRepository;
    @Autowired
    TrnOtStaffDetailsRepository trnOtStaffDetailsRepository;
    @Autowired
    TrnOtEquipmentDetailsRepository trnOtEquipmentDetailsRepository;
    @Autowired
    TrnOtIntraOperativeInstructionDetailsRepository trnOtIntraOperativeInstructionDetailsRepository;
    @Autowired
    TrnOtPostOperativeInstructionDetailsRepository trnOtPostOperativeInstructionDetailsRepository;
    @Autowired
    TrnOtPreOperativeInstructionDetailsRepository trnOtPreOperativeInstructionDetailsRepository;
    @Autowired
    TrnOtServiceDetailsRepository trnOtServiceDetailsRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOtProcedureDetail trnotproceduredetail) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnotproceduredetail.getOpProcedureId() != 0L) {
            trnOtProcedureDetailRepository.save(trnotproceduredetail);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("createbyopsid")
    public Map<String, String> createbyopsid(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOtProcedureDetail trnotproceduredetail) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnotproceduredetail.getOpProcedureId() != 0L) {
            long curOpsId = trnotproceduredetail.getOpOpsId();
            long curOpdId = trnotproceduredetail.getOpOpdId();
            long curProcedureId = trnotproceduredetail.getOpProcedureId();
            MstProcedure mstProcedure = mstProcedureRepository.getById(trnotproceduredetail.getOpProcedureId());
            trnotproceduredetail.setOpProcedureName(mstProcedure.getProcedureName());
            trnOtProcedureDetailRepository.save(trnotproceduredetail);
            List<MstProcedureChecklist> mstProcedureChecklist = mstProcedure.getProcedureChecklist();
            for (MstProcedureChecklist curObj : mstProcedureChecklist) {
                TrnOtProcedureChecklist obj = new TrnOtProcedureChecklist();
                obj.setOpcChecklistId(curObj);
                obj.setOpcOpsId(curOpsId);
                obj.setOpcOpdId(curOpdId);
                obj.setOpcProcedureId(curProcedureId);
                trnOtProcedureChecklistRepository.save(obj);
            }
            List<MstConsent> mstConsent = mstProcedure.getProcedureConsent();
            for (MstConsent curObj : mstConsent) {
                TrnOtConsentsDetails obj = new TrnOtConsentsDetails();
                obj.setOcdConsentId(curObj);
                obj.setOcdOpsId(curOpsId);
                obj.setOcdOpdId(curOpdId);
                obj.setOcdProcedureId(curProcedureId);
                trnOtConsentsDetailsRepository.save(obj);
            }
            List<MstStaff> mstStaffd = mstProcedure.getProcedureDoctor();
            for (MstStaff curObj : mstStaffd) {
                TrnOtDoctorDetails obj = new TrnOtDoctorDetails();
                obj.setOddStaffId(curObj);
                obj.setOddOpsId(curOpsId);
                obj.setOddOpdId(curOpdId);
                obj.setOddProcedureId(curProcedureId);
                trnOtDoctorDetailsRepository.save(obj);
            }
            List<MstStaff> mstStaffs = mstProcedure.getProcedureStaff();
            for (MstStaff curObj : mstStaffs) {
                TrnOtStaffDetails obj = new TrnOtStaffDetails();
                obj.setOsdStaffId(curObj);
                obj.setOsdOpsId(curOpsId);
                obj.setOsdOpdId(curOpdId);
                obj.setOsdProcedureId(curProcedureId);
                trnOtStaffDetailsRepository.save(obj);
            }
            List<InvItem> invItem = mstProcedure.getProcedureItem();
            for (InvItem curObj : invItem) {
                TrnOtEquipmentDetails obj = new TrnOtEquipmentDetails();
                obj.setOedInvItemId(curObj);
                obj.setOedOpsId(curOpsId);
                obj.setOedOpdId(curOpdId);
                obj.setOedProcedureId(curProcedureId);
                trnOtEquipmentDetailsRepository.save(obj);
            }
            List<MstIntraOperativeInstructions> mstIntraOperativeInstructions = mstProcedure.getProcedureIntraOperativeInstruction();
            for (MstIntraOperativeInstructions curObj : mstIntraOperativeInstructions) {
                TrnOtIntraOperativeInstructionDetails obj = new TrnOtIntraOperativeInstructionDetails();
                obj.setOioidIoiId(curObj);
                obj.setOioidOpsId(curOpsId);
                obj.setOioidOpdId(curOpdId);
                obj.setOioidProcedureId(curProcedureId);
                trnOtIntraOperativeInstructionDetailsRepository.save(obj);
            }
            List<MstPostOperativeInstructions> mstPostOperativeInstructions = mstProcedure.getProcedurePostOperativeInstruction();
            for (MstPostOperativeInstructions curObj : mstPostOperativeInstructions) {
                TrnOtPostOperativeInstructionDetails obj = new TrnOtPostOperativeInstructionDetails();
                obj.setOpooidPooiId(curObj);
                obj.setOpooidOpsId(curOpsId);
                obj.setOpooidOpdId(curOpdId);
                obj.setOpooidProcedureId(curProcedureId);
                trnOtPostOperativeInstructionDetailsRepository.save(obj);
            }
            List<MstPreOperativeInstructions> mstPreOperativeInstructions = mstProcedure.getProcedurePreOperativeInstruction();
            for (MstPreOperativeInstructions curObj : mstPreOperativeInstructions) {
                TrnOtPreOperativeInstructionDetails obj = new TrnOtPreOperativeInstructionDetails();
                obj.setOpoidPoiId(curObj);
                obj.setOpoidOpsId(curOpsId);
                obj.setOpoidOpdId(curOpdId);
                obj.setOpoidProcedureId(curProcedureId);
                trnOtPreOperativeInstructionDetailsRepository.save(obj);
            }
            List<MbillService> mbillService = mstProcedure.getProcedureService();
            for (MbillService curObj : mbillService) {
                TrnOtServiceDetails obj = new TrnOtServiceDetails();
                obj.setOsrdServiceId(curObj);
                obj.setOsrdOpsId(curOpsId);
                obj.setOsrdOpdId(curOpdId);
                obj.setOsrdProcedureId(curProcedureId);
                trnOtServiceDetailsRepository.save(obj);
            }
            respMap.put("trnotproceduredetailOpOpsId", Long.toString(curOpsId));
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
    public Map<String, String> createbylistbyopdid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opdId") Long opdId, @RequestBody List<TrnOtProcedureDetail> trnOtProcedureDetail) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtProcedureDetail curObj : trnOtProcedureDetail) {
            if (curObj.getOpProcedureId() != 0) {
                curObj.setOpOpsId(opdId);
                trnOtProcedureDetailRepository.save(curObj);
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
    public Map<String, String> createbylistbyopsid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opsId") Long opsId, @RequestBody List<TrnOtProcedureDetail> trnOtProcedureDetail) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtProcedureDetail curObj : trnOtProcedureDetail) {
            if (curObj.getOpProcedureId() != 0) {
                curObj.setOpOpsId(opsId);
                trnOtProcedureDetailRepository.save(curObj);
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

    @RequestMapping("byid/{opId}")
    public TrnOtProcedureDetail read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opId") Long opId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtProcedureDetailRepository.getById(opId);
    }

    @RequestMapping("byopsid/{opsId}")
    public List<TrnOtProcedureDetail> byOpsId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opsId") Long opsId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtProcedureDetailRepository.findAllByOpOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(opsId);
    }

    @RequestMapping("byopdid/{opdId}")
    public List<TrnOtProcedureDetail> byOpdId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opdId") Long opdId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtProcedureDetailRepository.findAllByOpOpdIdEqualsAndIsActiveTrueAndIsDeletedFalse(opdId);
    }

    @RequestMapping("byprocedureid/{procedureId}")
    public List<TrnOtProcedureDetail> byProcedureId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("procedureId") Long procedureId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtProcedureDetailRepository.findAllByOpProcedureIdEqualsAndIsActiveTrueAndIsDeletedFalse(procedureId);
    }

    @RequestMapping("update")
    public TrnOtProcedureDetail update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOtProcedureDetail trnOtProcedureDetail) {
        TenantContext.setCurrentTenant(tenantName);
        return trnOtProcedureDetailRepository.save(trnOtProcedureDetail);
    }

    @RequestMapping("updatebylistbyopdid")
    public Map<String, String> updatebylistbyopdid(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TrnOtProcedureDetail> trnOtProcedureDetail) {
        TenantContext.setCurrentTenant(tenantName);
        for (TrnOtProcedureDetail curObj : trnOtProcedureDetail) {
            if (curObj.getOpProcedureId() != 0) {
                trnOtProcedureDetailRepository.save(curObj);
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

    @RequestMapping("delete/{opId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("opId") Long opId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnOtProcedureDetail trnOtProcedureDetail = trnOtProcedureDetailRepository.getById(opId);
        if (trnOtProcedureDetail != null) {
            trnOtProcedureDetail.setIsDeleted(true);
            trnOtProcedureDetailRepository.save(trnOtProcedureDetail);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
