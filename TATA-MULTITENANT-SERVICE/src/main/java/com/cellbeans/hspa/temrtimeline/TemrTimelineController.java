package com.cellbeans.hspa.temrtimeline;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.emailutility.Emailsend;
import com.cellbeans.hspa.mbillservice.MbillService;
import com.cellbeans.hspa.mbillservice.MbillServiceRepository;
import com.cellbeans.hspa.mstpatient.MstPatient;
import com.cellbeans.hspa.mstpatient.MstPatientRepository;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mststaff.MstStaffRepository;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.cellbeans.hspa.mstvisit.MstVisitRepository;
import com.cellbeans.hspa.smsutility.Sendsms;
import com.cellbeans.hspa.trnadmission.TrnAdmission;
import com.cellbeans.hspa.trnadmission.TrnAdmissionRepository;
import com.cellbeans.hspa.trnappointment.TrnAppointment;
import com.cellbeans.hspa.trnappointment.TrnAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@RestController
@RequestMapping("/temr_timeline")
public class TemrTimelineController {
    Map<String, String> respMap = new HashMap<String, String>();
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    TemrTimelineRepository temrTimelineRepository;
    @Autowired
    MbillServiceRepository mbillServiceRepository;
    @Autowired
    TrnAdmissionRepository trnAdmissionRepository;
    @Autowired
    MstPatientRepository mstPatientRepository;
    @Autowired
    MstStaffRepository mstStaffRepository;
    @Autowired
    TrnAppointmentRepository trnAppointmentRepository;
    @Autowired
    MstVisitRepository mstVisitRepository;
    @Autowired
    Sendsms sendsms;
    @Autowired
    private com.cellbeans.hspa.applicationproperty.Propertyconfig Propertyconfig;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrTimeline temrTimeline) {
        TenantContext.setCurrentTenant(tenantName);
        if (temrTimeline.getTimelineVisitId() != null) {
            temrTimeline = temrTimelineRepository.save(temrTimeline);
            respMap.put("success", "1");
            respMap.put("temrTimelineId", String.valueOf(temrTimeline.getTimelineId()));
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<TemrTimeline> records;
        records = temrTimelineRepository.findByTimelineVisitIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{timelineId}")
    public TemrTimeline read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("timelineId") Long timelineId) {
        TenantContext.setCurrentTenant(tenantName);
//        TemrTimeline temrTimeline = temrTimelineRepository.getById(timelineId);
        TemrTimeline temrTimeline = temrTimelineRepository.findByTimelineIdAndIsActiveTrueAndIsDeletedFalse(timelineId);
        return temrTimeline;
    }

    @RequestMapping("byvisitid/{visitId}")
    public TemrTimeline timelineByVisitId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("visitId") Long visitId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrTimeline temrTimeline = temrTimelineRepository.findByTimelineVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(visitId);
        return temrTimeline;
    }

    @RequestMapping("lasttimelinebyvisitid/{visitId}")
    public TemrTimeline lasttimelinebyvisitid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("visitId") Long visitId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrTimeline temrTimeline = temrTimelineRepository.findByLastTimelineByVisitId(visitId);
        return temrTimeline;
    }

    @RequestMapping("byBsId/{bsId}")
    public TemrTimeline byBsId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bsId") Long bsId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrTimeline temrTimeline = temrTimelineRepository.findBybsId(bsId);
        return temrTimeline;
    }

    @RequestMapping("saveemr/{timelineId}/{staffId}")
    public Map<String, String> finalizedEMR(@RequestHeader("X-tenantId") String tenantName,
                                            @PathVariable("timelineId") Long timelineId,
                                            @PathVariable("staffId") MstStaff staffId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrTimeline temrTimeline = temrTimelineRepository.findByTimelineIdEqualsAndIsActiveTrueAndIsDeletedFalse(timelineId);
        TrnAppointment trnAppointment = trnAppointmentRepository.findByAppointmentTimelineIdTimelineIdEquals(timelineId);
        if (temrTimeline != null) {
            temrTimeline.setEMRFinal(true);
            temrTimeline.setTimelineFinalizedDate(new Date());
            temrTimeline.setTimelineEMRFinalStaffId(staffId);
            temrTimelineRepository.save(temrTimeline);
            if (trnAppointment != null) {
                trnAppointment.setAppointmentIsClosed(true);
                trnAppointmentRepository.save(trnAppointment);
            }

            //Commented due to change in Duration calculation time line by Rohan and Gayatri
//            MstVisit mstVisit = mstVisitRepository.findByVisitIdEqualsAndIsActiveTrueAndIsDeletedFalse(temrTimeline.getTimelineVisitId().getVisitId());
//            long diff = (temrTimeline.getTimelineFinalizedDate().getTime() - mstVisit.getCreatedDate().getTime()) / 1000;
//            diff /= 60;
//            diff = Math.abs(Math.round(diff));
//            mstVisit.setVisitWaitingDuration(diff);
//            mstVisitRepository.save(mstVisit);


            if (Propertyconfig.getSmsApi()) {
                String msg = "";
                try {
                    if (Propertyconfig.getClientName().equalsIgnoreCase("healthspring")) {
                        msg = "Your Case paper " + temrTimeline.getTimelineVisitId().getVisitId() + " has been finalized by Dr. " + temrTimeline.getTimelineStaffId().getStaffUserId().getUserFullname();
                        sendsms.sendmessage1(temrTimeline.getTimelineVisitId().getVisitPatientId().getPatientUserId().getUserMobileCode() + temrTimeline.getTimelineVisitId().getVisitPatientId().getPatientUserId().getUserMobile(), msg);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            if (Propertyconfig.getEmailApi()) {
                String msg = "";
                String subject = "";
                try {
                    if (Propertyconfig.getClientName().equalsIgnoreCase("healthspring")) {
                        subject = "EHMS Portal - Case Paper";
                        msg = "Dear " + temrTimeline.getTimelineVisitId().getVisitPatientId().getPatientUserId().getUserTitleId().getTitleName() + ". " + temrTimeline.getTimelineVisitId().getVisitPatientId().getPatientUserId().getUserFirstname() + " " + temrTimeline.getTimelineVisitId().getVisitPatientId().getPatientUserId().getUserLastname() + " <br><br>" +
                                "Your Case paper " + temrTimeline.getTimelineVisitId().getVisitId() + " has been finalized by Dr. " + temrTimeline.getTimelineStaffId().getStaffUserId().getUserFullname() + ". Request you to collect the same." +
                                " <br><br>" +
                                " Please share your feedback on clicking below URL <br><br>" +
                                " <a href=\"https://sprw.io/stt-4074cc\">https://sprw.io/stt-4074cc</a><br><br>" +
                                " <br><br> Thanks And Regards, " +
                                " <br> eHMS Admin";
                        Emailsend emailsend1 = new Emailsend();
                        emailsend1.sendMAil1(temrTimeline.getTimelineVisitId().getVisitPatientId().getPatientUserId().getUserEmail(), msg, subject);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }

            }
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Failed to save EMR");
            respMap.put("success", "1");
        }
        return respMap;
    }
//    @RequestMapping("updateboolean/{visitId}/{patientId}/{key}/{boolean}")
//    public TemrTimeline updateBoolean(@RequestHeader("X-tenantId") String tenantName, @PathVariable("visitId") Long visitId,@PathVariable("patientId") Long patientId, @PathVariable("key") String key, @PathVariable("boolean") Boolean booleanValue) {
//
//        TemrTimeline temrTimeline = temrTimelineRepository.findByTimelineVisitIdVisitIdEqualsAndIsActiveTrueAndIsDeletedFalse(visitId);
//
//        if (temrTimeline == null) {
//
//            temrTimeline = new TemrTimeline();
//            MstVisit visit = new MstVisit();
//            visit.setVisitId(visitId);
//            temrTimeline.setTimelineVisitId(visit);
//
//            MstPatient patient = new MstPatient();
//            patient.setPatientId(patientId);
//            temrTimeline.setTimelinePatientId(patient);
//        }
//
//        switch (key) {
//            case "vital":
//                temrTimeline.setTimelineIsVitals(booleanValue);
//                break;
//            case "diagnosis":
//                temrTimeline.setTimelineIsDiagnosis(booleanValue);
//                break;
//            case "investigation":
//                temrTimeline.setTimelineIsInvestigation(booleanValue);
//                break;
//            case "prescription":
//                temrTimeline.setTimelineIsPrescription(booleanValue);
//                break;
//            case "doctorsadvice":
//                temrTimeline.setTimelineIsDoctorsAdvice(booleanValue);
//                break;
//            case "upload":
//                temrTimeline.setTimelineIsUpload(booleanValue);
//                break;
//            case "allergies":
//                temrTimeline.setTimelineIsAllergies(booleanValue);
//                break;
//            case "symptoms":
//                temrTimeline.setTimelineIsSymptoms(booleanValue);
//                break;
//            case "history":
//                temrTimeline.setTimelineIsHistory(booleanValue);
//                break;
//            case "chiefcomplaint":
//                temrTimeline.setTimelineIsChiefComplaint(booleanValue);
//                break;
//        }
//
//
//        return temrTimelineRepository.save(temrTimeline);
//    }

    @RequestMapping("saveemrForMobile/{timelineId}/{staffId}/{isemrfinal}")
    public Map<String, String> finalizedEMR(@RequestHeader("X-tenantId") String tenantName,
                                            @PathVariable("timelineId") Long timelineId,
                                            @PathVariable("staffId") MstStaff staffId,
                                            @PathVariable("isemrfinal") Boolean isemrfinal) {
        TenantContext.setCurrentTenant(tenantName);
        TemrTimeline temrTimeline = temrTimelineRepository.findByTimelineIdEqualsAndIsActiveTrueAndIsDeletedFalse(timelineId);
        TrnAppointment trnAppointment = trnAppointmentRepository.findByAppointmentTimelineIdTimelineIdEquals(timelineId);
        if (temrTimeline != null) {
            if (isemrfinal) {
                temrTimeline.setEMRFinal(isemrfinal);
                temrTimeline.setTimelineFinalizedDate(new Date());
                temrTimeline.setTimelineEMRFinalStaffId(staffId);
            }
            temrTimelineRepository.save(temrTimeline);
            if (trnAppointment != null) {
                trnAppointment.setAppointmentIsClosed(true);
                trnAppointmentRepository.save(trnAppointment);
            }
            MstVisit mstVisit = mstVisitRepository.findByVisitIdEqualsAndIsActiveTrueAndIsDeletedFalse(temrTimeline.getTimelineVisitId().getVisitId());
            if (isemrfinal) {
                long diff = (temrTimeline.getTimelineFinalizedDate().getTime() - mstVisit.getCreatedDate().getTime()) / 1000;
                diff /= 60;
                diff = Math.abs(Math.round(diff));
                mstVisit.setVisitWaitingDuration(diff);
            }
            mstVisitRepository.save(mstVisit);
            if (Propertyconfig.getSmsApi()) {
                String msg = "";
                try {
                    if (Propertyconfig.getClientName().equalsIgnoreCase("healthspring")) {
                        msg = "Your Case paper " + temrTimeline.getTimelineVisitId().getVisitId() + " has been finalized by Dr. " + temrTimeline.getTimelineStaffId().getStaffUserId().getUserFullname();
                        sendsms.sendmessage1(temrTimeline.getTimelineVisitId().getVisitPatientId().getPatientUserId().getUserMobileCode() + temrTimeline.getTimelineVisitId().getVisitPatientId().getPatientUserId().getUserMobile(), msg);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            if (Propertyconfig.getEmailApi()) {
                String msg = "";
                String subject = "";
                try {
                    if (Propertyconfig.getClientName().equalsIgnoreCase("healthspring")) {
                        subject = "EHMS Portal - Case Paper";
                        msg = "Dear " + temrTimeline.getTimelineVisitId().getVisitPatientId().getPatientUserId().getUserTitleId().getTitleName() + ". " + temrTimeline.getTimelineVisitId().getVisitPatientId().getPatientUserId().getUserFirstname() + " " + temrTimeline.getTimelineVisitId().getVisitPatientId().getPatientUserId().getUserLastname() + " <br><br>" +
                                "Your Case paper " + temrTimeline.getTimelineVisitId().getVisitId() + " has been finalized by Dr. " + temrTimeline.getTimelineStaffId().getStaffUserId().getUserFullname() + ". Request you to collect the same." +
                                " <br><br>" +
                                " Please share your feedback on clicking below URL <br><br>" +
                                " <a href=\"https://sprw.io/stt-4074cc\">https://sprw.io/stt-4074cc</a><br><br>" +
                                " <br><br> Thanks And Regards, " +
                                " <br> eHMS Admin";
                        Emailsend emailsend1 = new Emailsend();
                        emailsend1.sendMAil1(temrTimeline.getTimelineVisitId().getVisitPatientId().getPatientUserId().getUserEmail(), msg, subject);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }

            }
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Failed to save EMR");
            respMap.put("success", "1");
        }
        return respMap;
    }

    @RequestMapping("update")
    public TemrTimeline update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrTimeline temrTimeline) {
        TenantContext.setCurrentTenant(tenantName);
        return temrTimelineRepository.save(temrTimeline);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TemrTimeline> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String patientId,
                                       @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "timelineId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (patientId == null || patientId.equals("")) {
            return temrTimelineRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return temrTimelineRepository.findByTimelinePatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(patientId), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @PutMapping("delete/{timelineId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("timelineId") Long timelineId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrTimeline temrTimeline = temrTimelineRepository.getById(timelineId);
        if (temrTimeline != null) {
            temrTimeline.setDeleted(true);
            temrTimelineRepository.save(temrTimeline);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("updateboolean/{timelineId}/{key}/{boolean}")
    public Boolean updateBoolean(@RequestHeader("X-tenantId") String tenantName, @PathVariable("timelineId") Long timelineId, @PathVariable("key") String key, @PathVariable("boolean") Boolean booleanValue) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "update temr_timeline set ";
        String field = "";
        switch (key) {
            case "vital":
//                temrTimeline.setTimelineIsVitals(booleanValue);
                field = "timeline_is_vitals=";
                break;
            case "diagnosis":
//                temrTimeline.setTimelineIsDiagnosis(booleanValue);
                field = "timeline_is_diagnosis=";
                break;
            case "investigation":
//                temrTimeline.setTimelineIsInvestigation(booleanValue);
                field = "timeline_is_investigation=";
                break;
            case "prescription":
//                temrTimeline.setTimelineIsPrescription(booleanValue);
                field = "timeline_is_prescription=";
                break;
            case "doctorsadvice":
//                temrTimeline.setTimelineIsDoctorsAdvice(booleanValue);
                field = "timeline_is_doctors_advice=";
                break;
            case "upload":
//                temrTimeline.setTimelineIsUpload(booleanValue);
                field = "timeline_is_upload=";
                break;
            case "allergies":
//                temrTimeline.setTimelineIsAllergies(booleanValue);
                field = "timeline_is_allergies=";
                break;
            case "symptoms":
//                temrTimeline.setTimelineIsSymptoms(booleanValue);
                field = "timeline_is_symptoms=";
                break;
            case "history":
//                temrTimeline.setTimelineIsHistory(booleanValue);
                field = "timeline_is_history=";
                break;
            case "chiefcomplaint":
//                temrTimeline.setTimelineIsChiefComplaint(booleanValue);
                field = "timeline_is_chief_complaint=";
                break;
        }
        query = query + field + booleanValue + " where timeline_id=" + timelineId;
        updateTimeline(tenantName, query);
        TemrTimeline temrTimeline = temrTimelineRepository.findByTimelineIdEqualsAndIsActiveTrueAndIsDeletedFalse(timelineId);
        return true;
    }

    @Modifying
    private void updateTimeline(@RequestHeader("X-tenantId") String tenantName, String query) {
        TenantContext.setCurrentTenant(tenantName);
//        System.out.println(query);
        int update = entityManager.createNativeQuery(query).executeUpdate();
    }

    @RequestMapping("createeposide/{patientId}/{visitRegistraionSource}/{selectedStaff}/{serviceid}/{admissionid}")
    public TemrTimeline createEposide(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId, @PathVariable("visitRegistraionSource") Integer visitRegistraionSource, @PathVariable("selectedStaff") Long selectedStaff, @PathVariable("serviceid") Long serviceid, @PathVariable("admissionid") Long admissionid) {
        TenantContext.setCurrentTenant(tenantName);
        //  visitRegistraionSource = 1L
        MbillService mbillservice = new MbillService();
        if (serviceid != 0) {
            mbillservice = mbillServiceRepository.getById(serviceid);
        }
        TrnAdmission trnAdmission = new TrnAdmission();
        trnAdmission = trnAdmissionRepository.getById(admissionid);
        MstPatient mstPatient = new MstPatient();
        mstPatient = mstPatientRepository.getById(patientId);
        MstStaff mstStaff = new MstStaff();
        mstStaff = mstStaffRepository.getById(selectedStaff);
        TemrTimeline temrTimeline = new TemrTimeline();
        if (serviceid != 0) {
            temrTimeline.setTimelineSId(mbillservice);
        }
        temrTimeline.setTimelineAdmissionId(trnAdmission);
        temrTimeline.setTimelinePatientId(mstPatient);
        temrTimeline.setTimelineTime(new Date());
        temrTimeline.setTimelineDate(new Date());
        temrTimeline.setTimelineRegistrationSource(visitRegistraionSource);
        temrTimeline.setEMRFinal(false);
        temrTimeline.setTimelineStaffId(mstStaff);
        temrTimelineRepository.save(temrTimeline);
        return temrTimeline;
    }

    @RequestMapping("createemgeposide/{patientId}/{visitRegistraionSource}/{selectedStaff}/{serviceid}/{visitid}")
    public TemrTimeline createEmergencyEposide(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId, @PathVariable("visitRegistraionSource") Integer visitRegistraionSource, @PathVariable("selectedStaff") Long selectedStaff, @PathVariable("serviceid") Long serviceid, @PathVariable("visitid") Long visitId) {
        TenantContext.setCurrentTenant(tenantName);
        //  visitRegistraionSource = 1L
//    	System.out.println("oyeeeeeeeeee :"+serviceid);
        MbillService mbillservice = new MbillService();
        if (serviceid != 0) {
            mbillservice = mbillServiceRepository.getById(serviceid);
        }
        MstVisit mstVisit = new MstVisit();
        mstVisit = mstVisitRepository.getById(visitId);
        MstPatient mstPatient = new MstPatient();
        mstPatient = mstPatientRepository.getById(patientId);
        MstStaff mstStaff = new MstStaff();
        mstStaff = mstStaffRepository.getById(selectedStaff);
        TemrTimeline temrTimeline = new TemrTimeline();
        if (mbillservice != null) {
            temrTimeline.setTimelineSId(mbillservice);
        }
        temrTimeline.setTimelineVisitId(mstVisit);
        temrTimeline.setTimelinePatientId(mstPatient);
        temrTimeline.setTimelineTime(new Date());
        temrTimeline.setTimelineDate(new Date());
        temrTimeline.setTimelineRegistrationSource(visitRegistraionSource);
        temrTimeline.setEMRFinal(false);
        temrTimeline.setTimelineStaffId(mstStaff);
        temrTimelineRepository.save(temrTimeline);
        return temrTimeline;
    }

    @GetMapping
    @RequestMapping("updatedoctoradvice")
    public Map<String, String> updatedoctoradvice(@RequestHeader("X-tenantId") String tenantName,
                                                  @RequestParam(value = "timelineId") long timelineId,
                                                  @RequestParam(value = "visitFollowDate") Date visitFollowDate,
                                                  @RequestParam(value = "visitDoctorAdvice") String visitDoctorAdvice,
                                                  @RequestParam(value = "appoinmentId") String appoinmentId) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("------------------in updateDoctorAdvice--------------------------------");
        TemrTimeline temrTimeline = temrTimelineRepository.getById(timelineId);
        if (temrTimeline != null) {
            if (appoinmentId != null || (!appoinmentId.equals(null))) {
                temrTimeline.setTimelineAppointmentId(appoinmentId);
            }
            temrTimeline.setTimelineDoctorAdvice(visitDoctorAdvice);
            try {
                if (visitFollowDate.equals("null")) {
                    System.out.println("------------------in updateDoctorAdvice date null--------------------------------");
                } else {
                    System.out.println("------------------in updateDoctorAdvice date not null--------------------------------");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//                    System.out.println(dateFormat.format(dateFormat.parse(visitFollowDate)));
//                    temrTimeline.setTimelineFollowDate(dateFormat.parse(visitFollowDate));
                    temrTimeline.setTimelineFollowDate(visitFollowDate);
                    if (Propertyconfig.getSmsApi()) {
                        if (temrTimeline.getTimelineVisitId().getVisitPatientId().getPatientUserId().getUserMobile() != null) {
                            try {
                                sendsms.sendmessage(temrTimeline.getTimelineVisitId().getVisitPatientId().getPatientUserId().getUserMobile(), "Follow UP Date " + visitFollowDate);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                    }

                }
                //   visit.setVisitFollowDate(visitFollowDate);
                temrTimelineRepository.save(temrTimeline);
                respMap.put("msg", "Operation Successful");
                respMap.put("success", "1");
            } catch (Exception e) {
            }
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("getTimelineByAdmissionIdAndDate")
    public List<TemrTimeline> getTimelineByAdmissionIdAndDate(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnAdmission trnAdmission) {
        TenantContext.setCurrentTenant(tenantName);
        return temrTimelineRepository.findAllByTimelineAdmissionIdAdmissionIdEqualsAndCreatedDateBetweenAndIsActiveTrueAndIsDeletedFalse(trnAdmission.getAdmissionId(), trnAdmission.getCreatedDate(), new Date());
    }

}

