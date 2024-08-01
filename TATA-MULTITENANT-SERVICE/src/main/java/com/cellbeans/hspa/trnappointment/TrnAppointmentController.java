package com.cellbeans.hspa.trnappointment;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.MstCluster.MstCluster;
import com.cellbeans.hspa.MstCluster.MstClusterRepository;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.emailutility.Emailsend;
import com.cellbeans.hspa.mbillservice.MbillServiceRepository;
import com.cellbeans.hspa.mbilltariff.MbillTariff;
import com.cellbeans.hspa.mstcashcounter.MstCashCounter;
import com.cellbeans.hspa.mstdepartment.MstDepartment;
import com.cellbeans.hspa.mstpatient.MstPatient;
import com.cellbeans.hspa.mstpatient.MstPatientRepository;
import com.cellbeans.hspa.mstpatientsource.MstPatientSource;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mststaff.MstStaffRepository;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.cellbeans.hspa.mstuser.MstUser;
import com.cellbeans.hspa.mstuser.MstUserRepository;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.cellbeans.hspa.mstvisit.MstVisitRepository;
import com.cellbeans.hspa.smsutility.Sendsms;
import com.cellbeans.hspa.temrreferralhistory.TemrReferralHistory;
import com.cellbeans.hspa.temrreferralhistory.TemrReferralHistoryRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/trn_appointment")
public class TrnAppointmentController {

    @Autowired
    MstVisitRepository mstVisitRepository;

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    MstPatientRepository mstPatientRepository;

    @Autowired
    MbillServiceRepository mbillServiceRepository;

    @Autowired
    TrnAppointmentRepository trnAppointmentRepository;

    @Autowired
    TrnAppointmentPortalRepository trnAppointmentPortalRepository;

    @Autowired
    MstUserRepository mstUserRepository;

    @Autowired
    Emailsend emailsend;

    @Autowired
    Sendsms sendsms;

    @Autowired
    MstStaffRepository mstStaffRepository;

    @Autowired
    private com.cellbeans.hspa.applicationproperty.Propertyconfig Propertyconfig;

    @Autowired
    CreateJSONObject createJSONObject;

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    TemrReferralHistoryRepository temrReferralHistoryRepository;

    @Autowired
    MstUnitRepository mstUnitRepository;

    @Autowired
    MstClusterRepository mstClusterRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnAppointment trnAppointment) {
        TenantContext.setCurrentTenant(tenantName);
        Boolean flag = false;
        try {
            if (trnAppointment.getAppointmentUserId().getUserId() > 0) {
                flag = true;
            }
        } catch (NullPointerException e) {
            flag = false;
        }
        if (!flag) {
            MstUser AddedMstUser = mstUserRepository.save(trnAppointment.getAppointmentUserId());
            trnAppointment.setAppointmentUserId(AddedMstUser);
        }
        if (trnAppointment.getAppointmentDepartmentId().getDepartmentId() != 0L && trnAppointment.getAppointmentStaffId().getStaffId() != 0L) {
            String Firstname = trnAppointment.getAppointmentUserId().getUserFirstname();
            String Middlename = trnAppointment.getAppointmentUserId().getUserMiddlename();
            if (Middlename != null) {
                String Middlenamecap = Middlename.substring(0, 1).toUpperCase() + Middlename.substring(1);
                trnAppointment.getAppointmentUserId().setUserMiddlename(Middlenamecap);
            }
            String Lastname = trnAppointment.getAppointmentUserId().getUserLastname();
            String Firstnamecap = Firstname.substring(0, 1).toUpperCase() + Firstname.substring(1);
            String Lastnamecap = Lastname.substring(0, 1).toUpperCase() + Lastname.substring(1);
            trnAppointment.getAppointmentUserId().setUserFirstname(Firstnamecap);
            trnAppointment.getAppointmentUserId().setUserLastname(Lastnamecap);
            TrnAppointment trnNewAppointment = trnAppointmentRepository.save(trnAppointment);
            if (Propertyconfig.getSmsApi()) {
                if (trnAppointment.getAppointmentUserId().getUserMobile() != null) {
                    MstStaff mstStaff = new MstStaff();
                    mstStaff = mstStaffRepository.getById(trnAppointment.getAppointmentStaffId().getStaffId());
                    sendsms.sendmessage(trnAppointment.getAppointmentUserId().getUserMobile(), "Appointment with Dr " + mstStaff.getStaffUserId().getUserFirstname() + " " + mstStaff.getStaffUserId().getUserLastname() + " is booked successfully. " + trnAppointment.getAppointmentDate() + " " + trnAppointment.getAppointmentSlot());
                    // emailsend.sendSimpleMessage("abhijeetborse90@gmail.com","WELCOME to HSPA","You are Register Successfully MRNO is "+ newMrNo);
                }
            }
            if (Propertyconfig.getEmailApi()) {
                if (trnAppointment.getAppointmentUserId().getUserEmail() != null) {
                    System.out.println(trnAppointment.getAppointmentUserId().getUserEmail());
                    //     emailsend.sendSimpleMessage(trnAppointment.getAppointmentUserId().getUserEmail(), "WELCOME to HSPA", "Appointment with Dr " + trnAppointment.getAppointmentStaffId().getStaffUserId().getUserFirstname() + " " +
                    //            trnAppointment.getAppointmentStaffId().getStaffUserId().getUserLastname() + " is scheduled, successfully. " + trnAppointment.getAppointmentDate() + " " + trnAppointment.getAppointmentSlot());
                }
            }
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            respMap.put("id", Long.toString(trnNewAppointment.getAppointmentId()));
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            respMap.put("id", "null");
            return respMap;
        }

    }

    @RequestMapping("byid/{appointmentId}")
    public TrnAppointment read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("appointmentId") Long appointmentId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAppointment trnAppointment = trnAppointmentRepository.findByAppointmentIdEquals(appointmentId);
        return trnAppointment;
    }

    @RequestMapping("updateAppointmentConfirmation/{appointmentId}")
    public Map<String, String> updateAppointmentConfirmation(@RequestHeader("X-tenantId") String tenantName, @PathVariable("appointmentId") Long appointmentId) {
        TenantContext.setCurrentTenant(tenantName);
        trnAppointmentRepository.updateAppointmentIsConfirmed(appointmentId);
        respMap.put("status", "success");
        return respMap;
    }

    @RequestMapping("update")
    public TrnAppointment update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnAppointment trnAppointment) {
        TenantContext.setCurrentTenant(tenantName);
        return trnAppointmentRepository.save(trnAppointment);
    }

    @RequestMapping("slots")
    public List<String> listSlots(@RequestHeader("X-tenantId") String tenantName, @RequestBody Map<String, String> model) {
        TenantContext.setCurrentTenant(tenantName);
        String appDate = model.get("currAppDate");
        Long staffId = Long.parseLong(model.get("staffId"));
        Calendar cal = Calendar.getInstance();
        //yyyy/mm/dd
        int year = Integer.parseInt(appDate.substring(0, 4));
        int month = Integer.parseInt(appDate.substring(5, 7));
        int day = Integer.parseInt(appDate.substring(8, 10));
        ;
        cal.set(year, month, day);
        String currAppDate = year + "-" + month + "-" + day;
        return trnAppointmentRepository.findAllByAppointmentDateAndIsDeletedIsFalse(currAppDate, staffId);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TrnAppointment> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1")
            String page, @RequestParam(value = "size", required = false, defaultValue = "100")
                                                 String size, @RequestParam(value = "qString", required = false)
                                                 String qString, @RequestParam(value = "unitId", required = false)
                                                 long unitId, @RequestParam(value = "filter", required = false)
                                                 String filter, @RequestParam(value = "sort", required = false, defaultValue = "DESC")
                                                 String sort, @RequestParam(value = "col", required = false, defaultValue = "appointmentDate") String col) {
        TenantContext.setCurrentTenant(tenantName);
        qString = qString.trim();
        filter = filter.trim();
        if ((qString == null || qString.equals("")) && (filter == null || filter.equals(""))) {
            return trnAppointmentRepository.findAllByAppointmentUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            if (filter.equals("mrno")) {
                System.out.println("appoinment search mr no ");
                return trnAppointmentRepository.findAllByAppointmentPatientIdPatientMrNoContainsAndAppointmentUnitIdUnitIdEqualsAndIsActiveTrueAndAppointmentIsCancelledTrue(qString, unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

            } else if (filter.equals("name")) {
                return trnAppointmentRepository.findByAppointmentUserIdUserFirstnameContainsAndAppointmentUnitIdUnitIdOrAppointmentUserIdUserLastnameContainsAndAppointmentUnitIdUnitIdAndIsActiveTrueAndAppointmentIsCancelledFalse(qString, unitId, qString, unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            } else if (filter.equals("uid")) {
                return trnAppointmentRepository.findByAppointmentUnitIdUnitIdAndAppointmentUserIdUserUidContainsAndIsActiveTrueAndAppointmentIsCancelledFalse(unitId, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

            } else if (filter.equals("mobile")) {
                return trnAppointmentRepository.findByAppointmentUnitIdUnitIdAndAppointmentUserIdUserMobileContainsAndIsActiveTrueAndAppointmentIsCancelledFalse(unitId, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

            } else if (filter.equals("reason")) {
                return trnAppointmentRepository.findByAppointmentUnitIdUnitIdAndAppointmentArIdArIdAndIsActiveTrueAndAppointmentIsCancelledFalse(unitId, Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

            }
            if (filter.equals("staff")) {
                return trnAppointmentRepository.findByAppointmentUnitIdUnitIdEqualsAndAppointmentStaffIdStaffIdAndIsActiveTrueAndAppointmentIsCancelledFalse(unitId, Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

            }
            if (filter.equals("dept")) {
                return trnAppointmentRepository.findByAppointmentUnitIdUnitIdAndAppointmentDepartmentIdDepartmentIdAndIsActiveTrueAndAppointmentIsCancelledFalse(unitId, Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

            }
            if (filter.equals("date")) {
                String[] str = qString.split("/");
                try {
                    /*Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(str[0]);
                    Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(str[1]);*/
                    Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(str[0]);
                    Date date2 = new SimpleDateFormat("dd-MM-yyyy").parse(str[1]);
                    return trnAppointmentRepository.findByAppointmentDateBetweenAndIsActiveTrueAndIsDeletedFalseAndAppointmentIsCancelledFalse(date1, date2, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
                } catch (Exception e) {
                    List<TrnAppointment> objlist = new ArrayList<>();
                    return objlist;
                }
            }
            if (filter.equals("bookeddate")) {
                String[] str = qString.split("/");
                try {
                    Date date11 = new SimpleDateFormat("dd-MM-yyyy").parse(str[0]);
                    Date date12 = new SimpleDateFormat("dd-MM-yyyy").parse(str[1]);
                    return trnAppointmentRepository.findByCreatedDateBetweenAndIsActiveTrueAndIsDeletedFalseAndAppointmentIsCancelledFalse(date11, date12, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
                } catch (Exception e) {
                    List<TrnAppointment> objlist = new ArrayList<>();
                    return objlist;
                }
            }
            if (filter.equals("aptstatus")) {
                if (qString.equals("1")) {
                    return trnAppointmentRepository.findAllByAppointmentUnitIdUnitIdAndAppointmentIsConfirmFalseAndAppointmentIsBlockFalseAndAppointmentIsCancelledFalse(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
                }
                if (qString.equals("2")) {
                    return trnAppointmentRepository.findAllByAppointmentUnitIdUnitIdAndAppointmentIsConfirmTrueAndAppointmentIsCancelledreasoneIsNullAndAppointmentIsCancelledFalse(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
                }
                if (qString.equals("3")) {
                    return trnAppointmentRepository.findAllByAppointmentUnitIdUnitIdAndAppointmentIsConfirmFalseAndAppointmentIsCancelledreasoneIsNotNullAndAppointmentIsCancelledTrue(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
                } else {
                    return trnAppointmentRepository.findAllByAppointmentUnitIdUnitIdAndAppointmentIsConfirmFalseAndAppointmentIsCancelledreasoneIsNotNullAndAppointmentIsBlockTrueAndAppointmentIsCancelledFalse(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
                }
            } else {
                return trnAppointmentRepository.findAllByAppointmentUnitIdUnitIdAndIsActiveTrueAndAndAppointmentIsCancelledFalse(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            }

        }

    }

    @PutMapping("delete/{appointmentId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("appointmentId") Long appointmentId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAppointment trnAppointment = trnAppointmentRepository.getById(appointmentId);
        if (trnAppointment != null) {
            trnAppointment.setIsDeleted(true);
            trnAppointmentRepository.save(trnAppointment);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("cancel")
    public Map<String, String> cancel(@RequestHeader("X-tenantId") String tenantName, @RequestParam("appointmentId") Long appointmentId, @RequestParam("appointmentreasone") String appointmentreasone) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAppointment trnAppointment = trnAppointmentRepository.findByAppointmentIdEquals(appointmentId);
//        TrnAppointment trnAppointment = trnAppointmentRepository.getById(appointmentId);
        if (trnAppointment != null) {
            trnAppointment.setAppointmentIsCancelled(true);
            trnAppointment.setAppointmentIsCancelledreasone(appointmentreasone);
            trnAppointmentRepository.save(trnAppointment);
            if (Propertyconfig.getSmsApi()) {
                if (trnAppointment.getAppointmentUserId().getUserMobile() != null) {
                    MstStaff mstStaff = new MstStaff();
                    mstStaff = mstStaffRepository.getById(trnAppointment.getAppointmentStaffId().getStaffId());
                    sendsms.sendmessage(trnAppointment.getAppointmentUserId().getUserMobile(), "Appointment with Dr " + mstStaff.getStaffUserId().getUserFirstname() + " " + mstStaff.getStaffUserId().getUserLastname() + " Cancelled successfully. " + trnAppointment.getAppointmentDate() + " " + trnAppointment.getAppointmentSlot());
                    // emailsend.sendSimpleMessage("abhijeetborse90@gmail.com","WELCOME to HSPA","You are Register Successfully MRNO is "+ newMrNo);
                }
            }
            respMap.put("msg", "Appointment Cancelled");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("block")
    public Map<String, String> block(@RequestHeader("X-tenantId") String tenantName, @RequestParam("appointmentId") Long appointmentId, @RequestParam("appointmentreasone") String appointmentreasone) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAppointment trnAppointment = trnAppointmentRepository.getById(appointmentId);
        if (trnAppointment != null) {
            trnAppointment.setAppointmentIsBlock(true);
            // trnAppointment.setAppointmentIsCancelled(true);
            trnAppointment.setAppointmentIsCancelledreasone(appointmentreasone);
            trnAppointmentRepository.save(trnAppointment);
            if (Propertyconfig.getSmsApi()) {
                if (trnAppointment.getAppointmentUserId().getUserMobile() != null) {
                    MstStaff mstStaff = new MstStaff();
                    mstStaff = mstStaffRepository.getById(trnAppointment.getAppointmentStaffId().getStaffId());
                    sendsms.sendmessage(trnAppointment.getAppointmentUserId().getUserMobile(), "Appointment with Dr " + mstStaff.getStaffUserId().getUserFirstname() + " " + mstStaff.getStaffUserId().getUserLastname() + " Cancelled successfully. " + trnAppointment.getAppointmentDate() + " " + trnAppointment.getAppointmentSlot());
                    // emailsend.sendSimpleMessage("abhijeetborse90@gmail.com","WELCOME to HSPA","You are Register Successfully MRNO is "+ newMrNo);
                }
            }
            respMap.put("msg", "Appointment Cancelled");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("unblock")
    public Map<String, String> unblock(@RequestHeader("X-tenantId") String tenantName, @RequestParam("appointmentId") Long appointmentId, @RequestParam("appointmentreasone") String appointmentreasone) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAppointment trnAppointment = trnAppointmentRepository.getById(appointmentId);
        if (trnAppointment != null) {
            trnAppointment.setAppointmentIsBlock(false);
            // trnAppointment.setAppointmentIsCancelled(true);
            trnAppointment.setAppointmentIsCancelledreasone(appointmentreasone);
            trnAppointmentRepository.save(trnAppointment);

            /*if (Propertyconfig.getSmsApi()) {
                if (trnAppointment.getAppointmentUserId().getUserMobile() != null) {
                    MstStaff mstStaff = new MstStaff();

                    mstStaff = mstStaffRepository.getById(trnAppointment.getAppointmentStaffId().getStaffId());
                    sendsms.sendmessage(trnAppointment.getAppointmentUserId().getUserMobile(), "Appointment with Dr " + mstStaff.getStaffUserId().getUserFirstname() + " " + mstStaff.getStaffUserId().getUserLastname() + " Cancelled successfully. " + trnAppointment.getAppointmentDate() + " " + trnAppointment.getAppointmentSlot());
                    // emailsend.sendSimpleMessage("abhijeetborse90@gmail.com","WELCOME to HSPA","You are Register Successfully MRNO is "+ newMrNo);
                }
            }
*/
            respMap.put("msg", "Appointment Unblocked");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("confirmAppointment/{flag}/{serviceId}")
    public MstVisit confirmAppointment(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnAppointment trnAppointment, @PathVariable("flag") Boolean flag, @PathVariable("serviceId") Integer serviceId) {
        TenantContext.setCurrentTenant(tenantName);
        if (serviceId == 2) {
            Long appointmentId = trnAppointment.getAppointmentId();
            trnAppointment = trnAppointmentRepository.findByAppointmentIdEquals(appointmentId);

        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        MstVisit mstVisit = new MstVisit();
        MstCashCounter mstCashCounter = new MstCashCounter();
        mstCashCounter.setCcId(1);
        mstVisit.setVisitCcId(mstCashCounter);
        mstVisit.setIsVirtual(flag);
        mstVisit.setIsVisitUrgent(false);
        mstVisit.setOpdVisitType(serviceId);
        mstVisit.setVisitStaffId(trnAppointment.getAppointmentStaffId());
        mstVisit.setEappointmentId(trnAppointment.getEappointmentId());
        if (trnAppointment.getAppointmentTariffId() != null) {
            mstVisit.setVisitTariffId(trnAppointment.getAppointmentTariffId());

        } else {
            MbillTariff mbillTariff = new MbillTariff();
            mbillTariff.setTariffId(1);
            mstVisit.setVisitTariffId(mbillTariff);
        }
        MstPatientSource mstPatientSource = new MstPatientSource();
        mstPatientSource.setPsId(1);
        mstVisit.setVisitPsId(mstPatientSource);
        MstUnit mstUnit = new MstUnit();
//        mstUnit.setUnitId(1);
        mstUnit.setUnitId(trnAppointment.getAppointmentUnitId().getUnitId());
        mstVisit.setVisitUnitId(mstUnit);
        MstDepartment mstDepartment = new MstDepartment();
        mstDepartment.setDepartmentId(1);
        mstVisit.setVisitDepartmentId(mstDepartment);
        mstVisit.setSponsorCombination("1");
        mstVisit.setVisitNewBornStatus(false);
        mstVisit.setVisitRemark(trnAppointment.getAppointmentreason());
        mstVisit.setCreatedBy(trnAppointment.getCreatedBy());
        mstVisit.setCreatedbyId(trnAppointment.getCreatedbyId());
        mstVisit.setIsJoinCall(false);

        MstVisit mv = mstVisitRepository.save(mstVisit);
        trnAppointmentRepository.save(trnAppointment);
        if (trnAppointment.getAppointmentStaffId() != null) {
            trnAppointmentRepository.updateTrnAppointMentSlot(trnAppointment.getAppointmentId(), trnAppointment.getAppointmentSlot(), trnAppointment.getAppointmentStaffId().getStaffId(), trnAppointment.getHubId());
        } else {
            trnAppointmentRepository.updateTrnAppointMentSlot(trnAppointment.getAppointmentId(), trnAppointment.getAppointmentSlot(), trnAppointment.getHubId());
        }
        mstVisitRepository.updatePatientId(trnAppointment.getAppointmentPatientId().getPatientId(), mv.getVisitId());
        trnAppointment.setAppointmentStaffId(mstStaffRepository.getById(trnAppointment.getAppointmentStaffId().getStaffId()));
        return mv;

    }

    @PutMapping("confirm/{appointmentId}")
    public Map<String, String> confirm(@RequestHeader("X-tenantId") String tenantName, @PathVariable("appointmentId") Long appointmentId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAppointment trnAppointment = trnAppointmentRepository.getById(appointmentId);
        if (trnAppointment != null) {
            trnAppointment.setAppointmentIsConfirm(true);
            trnAppointmentRepository.save(trnAppointment);
            respMap.put("msg", "Appointment Confirmed");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @PutMapping("disconfirm/{appointmentId}")
    public Map<String, String> disconfirm(@RequestHeader("X-tenantId") String tenantName, @PathVariable("appointmentId") Long appointmentId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAppointment trnAppointment = trnAppointmentRepository.getById(appointmentId);
        if (trnAppointment != null) {
            trnAppointment.setAppointmentIsConfirm(false);
            trnAppointmentRepository.save(trnAppointment);
            respMap.put("msg", "Appointment Confirmed");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("AppoinmentBulkList")
    public Iterable<TrnAppointment> AppoinmentBulkList(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                       @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                                       @RequestParam(value = "qString", required = false) String qString,
                                                       @RequestParam(value = "filter", required = false) String filter,
                                                       @RequestParam(value = "unitId", required = false) long unitId,
                                                       @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                       @RequestParam(value = "col", required = false, defaultValue = "appointmentId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (filter.equals("stafff")) {
            return trnAppointmentRepository.findByAppointmentUnitIdUnitIdEqualsAndAppointmentStaffIdStaffIdAndAppointmentIsConfirmFalseAndAppointmentIsBlockFalseAndIsActiveTrueAndAppointmentIsCancelledFalse(unitId, Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }
        /*    else if (filter.equals("ddate")){
                return trnAppointmentRepository.findByAppointmentDateBetweenAndIsActiveTrueAndIsDeletedFalseAndAppointmentIsCancelledFalse(date, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }*/
        else {
            col = "appointment_id";
            return trnAppointmentRepository.findAllAppointmentIsConfirmFalseAndAppointmentIsCancelledreasoneIsNullAndAppointmentUnitIdUnitIdAndAppointmentIsBlockFalseAndIsActiveTrueAndIsDeletedFalse(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }
    }

    @GetMapping
    @RequestMapping("AppoinmentTodaysList")
    public List<TrnAppointment> AppoinmenttodaysList(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "unitId", required = false) String unitId, @RequestParam(value = "dateFrom", required = false) String dateFrom, @RequestParam(value = "dateTo", required = false) String dateTo) {
        TenantContext.setCurrentTenant(tenantName);
        try {
            System.out.println("unitId :" + unitId);
            System.out.println("dateFrom :" + dateFrom);
            System.out.println("dateTo :" + dateTo);
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            c.setTime(sdf.parse(dateTo));
            c.add(Calendar.DAY_OF_MONTH, 1);
            String newDateTo = sdf.format(c.getTime());
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
            String dateToStr = sd.format(sd.parse(newDateTo));
            String dateFromStr = sd.format(sd.parse(dateFrom));
            String query = "SELECT a From TrnAppointment a where"
                    + " a.isActive=1"
                    + " and a.isDeleted=0"
                    + " and a.appointmentIsConfirm=0"
                    //	+ " and a.appointmentIsCancelled=0"
                    + " and a.appointmentIsBlock=0";
            if (unitId != null && unitId != "") {
                query += " and a.appointmentUnitId.unitId=" + Long.parseLong(unitId);
            }
            query += " and (a.createdDate BETWEEN '" + dateFromStr + "' and '" + dateToStr + "')";
            System.out.println("Query :" + query);
            List<TrnAppointment> jsonArray = (List<TrnAppointment>) entityManager.createQuery(query).getResultList();
            return jsonArray;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
        //return trnAppointmentRepository.findAllAppointmentIsConfirmFalseAndAppointmentreasoneIsNullAndAppointmentUnitIdUnitIdAndAppointmentIsBlockFalseAndIsActiveTrueAndIsDeletedFalse(unitId);
    }

    @GetMapping
    @RequestMapping("AppoinmentTodaysstaffList")
    public List<TrnAppointment> AppoinmentstaffwisetodaysList(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "unitId", required = false) long unitId, @RequestParam(value = "staffId", required = false) long staffId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnAppointmentRepository.findAllAppointmentIsConfirmFalseAndIsNullAndAppointmentUnitIdUnitIdAndAppointmentIsBlockFalseAndIsActiveTrueAndIsDeletedFalse(unitId, staffId);
    }

    @GetMapping("getRecordByPatientIdAndDate")
    public TrnAppointment getRecordByPatientIdAndDate(@RequestHeader("X-tenantId") String tenantName, @RequestParam("patientId") Long patientId, @RequestParam("followUpDate") String followUpDate) {
        TenantContext.setCurrentTenant(tenantName);
        Date sdate = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            sdate = sdf1.parse(followUpDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        TrnAppointment trnAppointment = trnAppointmentRepository.findByAppointmentPatientIdPatientIdAndAppointmentDateAndAppointmentIsCancelledFalseAndIsActiveTrue(patientId, sdate);
        return trnAppointment;
    }

    @RequestMapping(value = "/billSearchByobj", method = RequestMethod.POST, consumes = {"application/json"})
    public @ResponseBody
    List<TrnAppointment> billfilter(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnAppointmentSearch objTrnAppointmentSearch) throws Exception {
        TenantContext.setCurrentTenant(tenantName);
        List<TrnAppointment> objTrnAppointment = new ArrayList<>();
        String Query = "";
        Query = "select t from TrnAppointment t where t.appointmentUnitId.unitId = " + objTrnAppointmentSearch.getUnitid() + " and t.isActive = true and t.isDeleted = false";
        if (objTrnAppointmentSearch.getMrno() != null) {
            Query += " and t.appointmentPatientId.patientMrNo like '%" + objTrnAppointmentSearch.getMrno() + "%'";
        }
        if (objTrnAppointmentSearch.getPtname() != null) {
            Query += " and (t.appointmentUserId.userFirstname like '%" + objTrnAppointmentSearch.getPtname() + "%' or t.appointmentUserId.userLastname like '%" + objTrnAppointmentSearch.getPtname() + "%')";
        }
        if (objTrnAppointmentSearch.getUid() != null) {
            Query += " and t.appointmentUserId.userUid like '%" + objTrnAppointmentSearch.getUid() + "%'";
        }
        if (objTrnAppointmentSearch.getMobile() != null) {
            Query += " and t.appointmentUserId.userMobile like '%" + objTrnAppointmentSearch.getMobile() + "%'";
        }
        if (objTrnAppointmentSearch.getRofapt() > 0) {
            Query += " and t.appointmentArId.arId = " + objTrnAppointmentSearch.getRofapt();
        }
        if (objTrnAppointmentSearch.getStaff() > 0) {
            Query += " and t.appointmentStaffId.staffId = " + objTrnAppointmentSearch.getStaff();
        }
        if (objTrnAppointmentSearch.getDept() > 0) {
            Query += " and t.appointmentDepartmentId.departmentId = " + objTrnAppointmentSearch.getDept();
        }
        if (objTrnAppointmentSearch.getAptstatus() > 0) {
            if (objTrnAppointmentSearch.getAptstatus() == 1) {
                Query += " and t.appointmentIsBlock = false and t.appointmentIsConfirm = false and t.appointmentIsCancelled = false";
            }
            if (objTrnAppointmentSearch.getAptstatus() == 2) {
                Query += " and t.appointmentIsConfirm = true and t.appointmentIsCancelled = false";
            }
            if (objTrnAppointmentSearch.getAptstatus() == 3) {
                Query += " and t.appointmentIsConfirm = false and t.appointmentIsCancelled = true";
            }
            if (objTrnAppointmentSearch.getAptstatus() == 4) {
                Query += " and t.appointmentIsBlock = true and t.appointmentIsConfirm = false and t.appointmentIsCancelled = false";
            }
        }
        if (objTrnAppointmentSearch.getFromdate() != null) {
            String date1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(objTrnAppointmentSearch.getFromdate()));
            String date2 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(objTrnAppointmentSearch.getTodate()));
            Query += " and t.createdDate between '" + date1 + "' And '" + date2 + "'";
        }
        if (objTrnAppointmentSearch.getBookedfromdate() != null) {
            String date1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(objTrnAppointmentSearch.getBookedfromdate()));
            String date2 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(objTrnAppointmentSearch.getBookedtodate()));
            Query += " and t.appointmentDate between '" + date1 + "' And '" + date2 + "'";
        }
        Query += " order by t.createdDate desc";
        objTrnAppointment = entityManager.createQuery(Query, TrnAppointment.class).setFirstResult(((objTrnAppointmentSearch.getOffset() - 1) * objTrnAppointmentSearch.getLimit())).setMaxResults(objTrnAppointmentSearch.getLimit()).getResultList();
        long count = 0;
        String CountQuery = StringUtils.replace(Query, "t fr", "count(t.appointmentId) fr");
        count = (long) entityManager.createQuery(CountQuery).getSingleResult();
        if (count != 0) {
            objTrnAppointment.get(0).setCount((int) count);
        }
        return objTrnAppointment;

    }

    //vijay p.
    @RequestMapping("allbulkappbyfilter/{page}/{size}/{sort}/{col}")
    public Iterable<TrnAppointment> allBulkAppListbyfilter(@RequestHeader("X-tenantId") String tenantName, @RequestBody String jsonStr, @PathVariable("page") String page, @PathVariable("size") String size, @PathVariable("sort") String sort, @PathVariable("col") String col) {
        TenantContext.setCurrentTenant(tenantName);
        JSONObject json = new JSONObject(jsonStr);
        System.out.println("json:" + json);
        try {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            c.setTime(sdf.parse(String.valueOf(json.getString("dateTo"))));
            c.add(Calendar.DAY_OF_MONTH, 1);
            String newDateTo = sdf.format(c.getTime());
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
            String dateTo = sd.format(sd.parse(newDateTo));
            String dateFrom = sd.format(sd.parse(json.getString("dateFrom")));
            // filters - >unit id /staffid /block /unblock /department/subdepartment /between date/between time
            String query = "SELECT a From TrnAppointment a where"
                    + " a.isActive=1"
                    + " and a.isDeleted=0"
                    + " and a.appointmentIsConfirm=0"
                    + " and a.appointmentIsCancelled=0"
                    + " and a.appointmentIsBlock=" + json.getBoolean("isBlock");
            if (!json.getString("staffId").isEmpty()) {
                query += " and a.appointmentStaffId.staffId=" + json.getString("staffId");
            }
            if (!json.getString("unitId").isEmpty()) {
                query += " and a.appointmentUnitId.unitId=" + json.getString("unitId");
            }
            if (!json.getString("departmentId").isEmpty()) {
                query += " and a.appointmentDepartmentId.departmentId=" + json.getString("departmentId");
            }
            if (!json.getString("subDepartmentId").isEmpty()) {
                query += " and a.appointmentSdId.sdId=" + json.getString("subDepartmentId");
            }
            query += " and (a.createdDate BETWEEN '" + dateFrom + "' and '" + dateTo + "')";
            query += " Order by a.appointmentId " + sort;
            //   System.out.println("query 2:" + query);
            String countQuery = StringUtils.replace(query, " a From", " count(a) from");
            Long count = (Long) entityManager.createQuery(countQuery).getSingleResult();
            // list for pagination below
            //  List<TrnAppointment> jsonArray = (List<TrnAppointment>) entityManager.createQuery(query).setFirstResult(((Integer.parseInt(page) - 1) * Integer.parseInt(size))).setMaxResults(Integer.parseInt(size)).getResultList();
            List<TrnAppointment> jsonArray = (List<TrnAppointment>) entityManager.createQuery(query).getResultList();
            if (jsonArray.size() > 0) {
                jsonArray.get(0).setCount(Integer.parseInt(count.toString()));
            }
            return jsonArray;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;

    }

    @RequestMapping("createAppointment")
    public Map<String, Object> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody AppointmentDto appointmentDto) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> respMap1 = new HashMap<String, Object>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date today = null;
        Date appointmentData = null;
        try {
            today = appointmentDto.getAppointmentDate();
            appointmentData = today;
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            TrnAppointment trnAppointment = new TrnAppointment();
            trnAppointment.setAppointmentIsConfirm(false);
            trnAppointment.setAppointmentUserId(appointmentDto.getAppointmentUserId());
            trnAppointment.setInsuranceCoveredAppointment(appointmentDto.getInsuranceCoveredAppointment());
            trnAppointment.setAppointmentDate(appointmentDto.getAppointmentDate());
            MstStaff staff = new MstStaff();
            if (appointmentDto.getStaffId() != null) {
                staff.setStaffId(appointmentDto.getStaffId());
            } else {
                staff.setStaffId(1L);
            }
            trnAppointment.setAppointmentStaffId(staff);
            MstUnit unit = new MstUnit();
            if (appointmentDto.getUnitId() != null) {
                unit.setUnitId(appointmentDto.getUnitId());
            } else {
                unit.setUnitId(1L);
            }
            trnAppointment.setAppointmentUnitId(unit);
            MstPatient mstPatient = mstPatientRepository.findByPatientUserIdUserId(appointmentDto.getAppointmentUserId().getUserId());
            trnAppointment.setAppointmentPatientId(mstPatient);
            trnAppointment.setAppointmentMrNo(mstPatient.getPatientMrNo());
            trnAppointment.setAppointmentSlot(appointmentDto.getAppointmentTime());
            trnAppointment.setAppointmentreason(appointmentDto.getAppointmentReason());
            trnAppointment.setAppointmentServiceId(mbillServiceRepository.getById(appointmentDto.getAppointmentService()));
            trnAppointment.setAppointmentPaymentId(appointmentDto.getAppointmentPaymentId());
            trnAppointment.setAppointmentPaymentRemark(appointmentDto.getAppointmentPaymentDetail());
            trnAppointment.setAppointmentServiceAmount(Double.parseDouble(appointmentDto.getAppointmentAmount()));
            trnAppointment.setIsAppointmentByApi(false);
            trnAppointment.setIsAppointmentPatientPortal(true);
            trnAppointment.setIsCheckConsent1(appointmentDto.getIsCheckConsent1());
            trnAppointment.setIsCheckConsent2(appointmentDto.getIsCheckConsent2());
            trnAppointment.setCreatedBy(appointmentDto.getCreatedBy());
            trnAppointment.setCreatedbyId(appointmentDto.getCreatedbyId());
            trnAppointment.setUnitOf(appointmentDto.getUnitOf());
            //trnAppointment.setEappointmentId(appointmentDto.getEappointmentId());    //  for EHS appointment id
            trnAppointment.setEappointmentId((appointmentDto.getEappointmentId() != null) ? appointmentDto.getEappointmentId() : 0L); // Default to 0 if null

            if (appointmentDto.getHubId() != null) {
                trnAppointment.setHubId(appointmentDto.getHubId());
            }
            if (appointmentDto.getParentUnitId() != null) {
                trnAppointment.setParentUnitId(appointmentDto.getParentUnitId());
            }
            trnAppointment.setIsVirtual(appointmentDto.getIsVirtual());
            trnAppointment.setIsInPerson(appointmentDto.getIsInPerson());
            trnAppointment.setIsRescheduled(appointmentDto.getIsRescheduled());
            trnAppointment.setIsAppointmentReferral(appointmentDto.getIsAppointmentReferral());
            if (appointmentDto.getReferHistoryId() != null) {
                trnAppointment.setReferHistoryId(appointmentDto.getReferHistoryId());
            }
            if (appointmentDto.getReferAppointId() != null && !appointmentDto.getReferAppointId().equals("null")) {
                TrnAppointment referralappointment = trnAppointmentRepository.getById(Long.parseLong(appointmentDto.getReferAppointId()));
                if (referralappointment != null) {
                    referralappointment.setReferralRescheduled(true);
                    trnAppointmentRepository.save(referralappointment);
                }
            }
            if (appointmentDto.getAppointmentFollowupTimelineId() != null) {
                trnAppointment.setAppointmentFollowupTimelineId(appointmentDto.getAppointmentFollowupTimelineId());
            }
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
            String format = formatter1.format(appointmentDto.getAppointmentDate());
//            System.out.println(format + " AMOL 20122021 " + appointmentDto.getAppointmentTime());
            if(trnAppointmentRepository.getIsAppointmentDateAndSlotBooked(format, appointmentDto.getAppointmentTime(), staff.getStaffId()) != 0) {
                respMap1.put("status", "0");
            } else {
                TrnAppointment newAppointment = trnAppointmentRepository.save(trnAppointment);
                respMap1.put("status", "1");
                respMap1.put("appointment", newAppointment);
            }
        } catch (Exception e) {
            e.printStackTrace();
            respMap1.put("status", "0");
            System.out.print(e);
        }
        return respMap1;

    }

    @RequestMapping("byVisitId/{visitId}")
    public TrnAppointment byVisitId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("visitId") Long visitId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAppointment trnAppointment = trnAppointmentRepository.findByAppointmentTimelineIdTimelineVisitIdVisitIdEquals(visitId);
        return trnAppointment;
    }

    @RequestMapping("bytimelineId/{timelineId}")
    public TrnAppointment bytimelineId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("timelineId") Long timelineId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAppointment trnAppointment = trnAppointmentRepository.findByAppointmentTimelineIdTimelineIdEquals(timelineId);
        return trnAppointment;
    }

    @RequestMapping("getSummary/{staffId}")
    public ResponseEntity getSummary(@RequestHeader("X-tenantId") String tenantName, @PathVariable("staffId") Long staffId) {
        TenantContext.setCurrentTenant(tenantName);
        String Query = " SELECT mu.unit_name AS unitName, mut.unittype_name AS unitType,  " +
                " count(case when (ta.appointment_is_confirm = TRUE AND ta.appointment_is_cancelled = FALSE AND tt.isemrfinal = FALSE) then 1 ELSE NULL END) AS waitingCount,  " +
                " count(case when tt.isemrfinal = true then 1 ELSE NULL END) AS completedCount,  " +
                " COUNT(CASE WHEN ta.appointment_is_confirm = TRUE THEN 1 ELSE NULL END) AS totalCount,mu.unit_id as unitId, IFNULL( GROUP_CONCAT(tt.timeline_visit_id),'') AS visitIdList  " +
                " FROM trn_appointment ta  " +
                " LEFT JOIN mst_unit mu ON mu.unit_id = ta.appointment_unit_id  " +
                " LEFT JOIN mst_unit_type mut ON mut.unittype_id = mu.unit_unittype_id  " +
                " LEFT JOIN temr_timeline tt ON tt.timeline_id = ta.appointment_timeline_id  " +
                " WHERE date(ta.created_date) = CURDATE() AND ta.appointment_staff_id = " + staffId +
                " GROUP BY ta.appointment_unit_id ";
        String columnName = "unitName,unitType,waitingCount,completedCount,totalCount,unitId,visitIdList";
        String CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
    }
//    Appointment list Backup 05.10.2021 by rohan ,Gayatri,Amol and Sagar sir
//    @PostMapping
//    @RequestMapping("appRequestList")
//    public List<TrnAppointment> appRequestList(@RequestHeader("X-tenantId") String tenantName, @RequestBody String search_query,
//                                               @RequestParam(value = "page", required = false, defaultValue = "1")
//                                                       String page, @RequestParam(value = "size", required = false, defaultValue = "100")
//                                                       String size, @RequestParam(value = "qString", required = false)
//                                                       String qString, @RequestParam(value = "unitId", required = false)
//                                                       long unitId, @RequestParam(value = "filter", required = false)
//                                                       String filter, @RequestParam(value = "sort", required = false, defaultValue = "DESC")
//                                                       String sort, @RequestParam(value = "col", required = false, defaultValue = "appointmentDate") String col) throws JSONException {
//        TenantContext.setCurrentTenant(tenantName);
//        JSONObject json = new JSONObject(search_query);
//        String search = search_query.contains("search") ? json.getString("search") : "";
//        String startdate = search_query.contains("strtdate") ? json.getString("strtdate") : "";
//        String enddate = search_query.contains("enddate") ? json.getString("enddate") : "";
//        String bookstrtdate = search_query.contains("bookstrtdate") ? json.getString("bookstrtdate") : "";
//        String bookendtdate = search_query.contains("bookendtdate") ? json.getString("bookendtdate") : "";
//        int appStatus = search_query.contains("appStatus") ? json.getInt("appStatus") : 0;
//        int appReason = search_query.contains("appReason") ? json.getInt("appReason") : 0;
//        String patientName = search_query.contains("patientName") ? json.getString("patientName") : "";
//        String mrNo = search_query.contains("mrNo") ? json.getString("mrNo") : "";
//        String mobNo = search_query.contains("mobNo") ? json.getString("mobNo") : "";
//        qString = qString.trim();
//        filter = filter.trim();
//        String query = "";
//        Date date = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        String strDate = formatter.format(date);
//        if (startdate.equals("") || startdate.equals("null")) {
//            startdate = strDate;
//        }
//        if (enddate.equals("") || enddate.equals("null")) {
//            enddate = strDate;
//        }
//        query += " SELECT ta.*,trh.created_date AS rhDate, muu.user_fullname As staffName, trh.rh_subject As rfSubject,CONCAT(muu.user_fullname,' on ',date(trh.created_date), ' Reason ', trh.rh_subject) as Reason, IFNULL(mu.unit_name,'') AS ParentUnit, IFNULL(mcc.cluster_name,'') AS clusterName FROM trn_appointment ta " +
//                " INNER JOIN mst_patient mp ON mp.patient_id = ta.appointment_patient_id " +
//                " INNER JOIN mst_user muser ON muser.user_id = mp.patient_user_id " +
//                " LEFT JOIN temr_timeline tt ON tt.timeline_id = ta.appointment_timeline_id " +
//                " LEFT JOIN mst_visit mv ON mv.visit_id = tt.timeline_visit_id" +
//                " LEFT JOIN temr_referral_history trh ON trh.rh_id = ta.refer_history_id" +
//                " LEFT JOIN mst_staff msst ON msst.staff_id = trh.rh_staff_id " +
//                " LEFT JOIN mst_user muu ON muu.user_id = msst.staff_user_id" +
//                " LEFT JOIN mst_unit mu ON mu.unit_id = ta.parent_unit_id " +
//                " LEFT JOIN mst_cluster mcc ON mcc.cluster_id = ta.hub_id " +
//                " WHERE ta.is_active = 1 AND ta.is_deleted = 0 ";
//        if (appStatus == 1) {
//            query += " and ta.appointment_is_confirm = false  and appointment_is_block = false and ta.appointment_is_cancelled =false ";
////            return trnAppointmentRepository.findAllByAppointmentUnitIdUnitIdAndAppointmentIsConfirmFalseAndAppointmentIsBlockFalseAndAppointmentIsCancelledFalse(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//        }
//        if (appStatus == 2) {
//            query += " and ta.appointment_is_confirm = true and ta.appointment_is_cancelled =false and tt.isemrfinal = false";
////            return trnAppointmentRepository.findAllByAppointmentUnitIdUnitIdAndAppointmentIsConfirmTrueAndAppointmentIsCancelledreasoneIsNullAndAppointmentIsCancelledFalse(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//        } else {
////            query += " and ta.appointment_is_confirm = false  ";
//        }
//        if (appStatus == 3) {
//            query += " and ta.appointment_is_cancelled =true ";
////            return trnAppointmentRepository.findAllByAppointmentUnitIdUnitIdAndAppointmentIsConfirmFalseAndAppointmentIsCancelledreasoneIsNotNullAndAppointmentIsCancelledTrue(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//        }
//        if (appStatus == 4) {
//            query += "and ta.appointment_is_cancelled = false and ta.appointment_is_block = true ";
////            return trnAppointmentRepository.findAllByAppointmentUnitIdUnitIdAndAppointmentIsConfirmFalseAndAppointmentIsCancelledreasoneIsNotNullAndAppointmentIsBlockTrueAndAppointmentIsCancelledFalse(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//        }
//        if (appStatus == 5) {
//            query += " and tt.isemrfinal = true ";
////            return trnAppointmentRepository.findAllByAppointmentUnitIdUnitIdAndAppointmentIsConfirmFalseAndAppointmentIsCancelledreasoneIsNotNullAndAppointmentIsBlockTrueAndAppointmentIsCancelledFalse(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//        }
//        query += "  and ta.appointment_unit_id=" + unitId;
///*
//        if(bookstrtdate.equals("")|| bookstrtdate.equals("null")){
//            startdate = strDate;
//        }
//        if (bookendtdate.equals("")|| bookendtdate.equals("null")) {
//            enddate = strDate;
//        }*/
//        if (!patientName.equals("")) {
//            query += " and concat(muser.user_firstname,' ', muser.user_lastname) LIKE '%" + patientName + "%' ";
//
//        }
//        if (!mrNo.equals("")) {
//            query += " and mp.patient_mr_no LIKE '%" + mrNo + "%' ";
//
//        }
//        if (!mobNo.equals("")) {
//            query += " and muser.user_mobile LIKE '%" + mobNo + "%' ";
//        }
//        if (filter.equals("bookeddate")) {
//            query += " AND (DATE(ta.created_date) BETWEEN '" + bookstrtdate + "' and '" + bookendtdate + "') ";
//
//        } else {
//            query += " and (date(ta.appointment_date) between '" + startdate + "' and '" + enddate + "') ";
//        }
//        if (appReason != 0) {
//            query += " and ta.appointment_ar_id = " + appReason;
//        }
//        query += "AND ta.appointment_slot IS NOT NULL order by ta.appointment_date desc, STR_TO_DATE(ta.appointment_slot, '%l:%i %p') ";
//        String CountQuery = " select count(*) from ( " + query + " ) as combine ";
//        query += " limit " + ((Integer.parseInt(page) - 1) * Integer.parseInt(size)) + "," + Integer.parseInt(size);
//        System.out.println("query " + query);
//        BigInteger count = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
//        List<TrnAppointment> list = entityManager.createNativeQuery(query, TrnAppointment.class).getResultList();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        for (TrnAppointment trnAppointment : list) {
//            if (trnAppointment.getReferHistoryId() != null) {
//                TemrReferralHistory temrReferralHistory = temrReferralHistoryRepository.getById(Long.parseLong(trnAppointment.getReferHistoryId()));
//                trnAppointment.setReason(temrReferralHistory.getRhStaffId().getStaffUserId().getUserFullname() + " on " + simpleDateFormat.format(temrReferralHistory.getCreatedDate()) + " Reason " + temrReferralHistory.getRhSubject());
//            }
//            if (trnAppointment.getParentUnitId() != null) {
//                MstUnit mstUnit = mstUnitRepository.getById(Long.parseLong(trnAppointment.getParentUnitId()));
//                trnAppointment.setParentUnitName(mstUnit.getUnitName());
//            }
//            if (trnAppointment.getHubId() != null) {
//                MstCluster mstCluster = mstClusterRepository.getById(Long.parseLong(trnAppointment.getHubId()));
//                trnAppointment.setClusterName(mstCluster.getClusterName());
//            }
//        }
//        if (list.size() > 0) {
//            list.get(0).setCount(count.intValue());
//        }
//        return list;
//    }

    @PostMapping
    @RequestMapping("appRequestList")
    public List<AppointmentListDto> appRequestList(@RequestHeader("X-tenantId") String tenantName,
                                                   @RequestBody String search_query,
                                                   @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                   @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                                   @RequestParam(value = "qString", required = false) String qString,
                                                   @RequestParam(value = "unitId", required = false) long unitId,
                                                   @RequestParam(value = "filter", required = false) String filter,
                                                   @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                   @RequestParam(value = "col", required = false, defaultValue = "appointmentDate") String col) throws JSONException {
        System.out.println("Called appRequestList");
        TenantContext.setCurrentTenant(tenantName);
        System.out.print(search_query);
        JSONObject json = new JSONObject(search_query);
        String search = search_query.contains("search") ? json.getString("search") : "";
        String startdate = search_query.contains("strtdate") ? json.getString("strtdate") : "";
        String enddate = search_query.contains("enddate") ? json.getString("enddate") : "";
        String bookstrtdate = search_query.contains("bookstrtdate") ? json.getString("bookstrtdate") : "";
        String bookendtdate = search_query.contains("bookendtdate") ? json.getString("bookendtdate") : "";
        int appStatus = search_query.contains("appStatus") ? json.getInt("appStatus") : 0;
        int appReason = search_query.contains("appReason") ? json.getInt("appReason") : 0;
        String patientName = search_query.contains("patientName") ? json.getString("patientName") : "";
        String mrNo = search_query.contains("mrNo") ? json.getString("mrNo") : "";
        String mobNo = search_query.contains("mobNo") ? json.getString("mobNo") : "";
        qString = qString.trim();
        filter = filter.trim();
        String query = "";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        if (startdate.equals("") || startdate.equals("null")) {
            startdate = strDate;
        }
        if (enddate.equals("") || enddate.equals("null")) {
            enddate = strDate;
        }
        query += " SELECT ta.appointment_id AS appointmentId, CASE WHEN ta.appointment_is_confirm THEN 'true' ELSE 'false' END AS appointmentIsConfirm, CASE WHEN ta.is_rescheduled THEN 'true' ELSE 'false' END AS isRescheduled, CASE WHEN ta.appointment_is_cancelled THEN 'true' ELSE 'false' END appointmentIsCancelled, ta.appointment_timeline_id AS appointmentTimelineId, CASE WHEN tt.isemrfinal THEN 'true' ELSE 'false' END AS isemrfinal, CASE WHEN ta.is_appointment_referral THEN 'true' ELSE 'false' END AS isAppointmentReferral, CONCAT(IFNULL(mti.title_name,''),' ', IFNULL(muser.user_firstname,''),' ', IFNULL(muser.user_lastname,'')) AS patientName, " +
                " muser.user_age AS patientAge, " +
                " gen.gender_name AS patientGender, " +
                " ta.appointment_mr_no AS appointmentMrNo, IFNULL(muser.user_uid,'') AS userUid, " +
                " ta.appointment_staff_id AS appointmentStaffId, CONCAT(IFNULL(mt.title_name,''),' ', IFNULL(muu.user_firstname,''),' ', IFNULL(muu.user_lastname,'')) AS staffName, " +
                " spl.speciality_name, IFNULL(mu.unit_name,'') AS parentUnitName, IFNULL(mcc.cluster_name,'') AS clusterName, " +
                " mser.service_name AS serviceName, " +
                " ta.appointment_date AS appointmentDate,  " +
                " ta.appointment_slot AS appointmentSlot, CASE WHEN ta.is_appointment_patient_portal THEN 'true' ELSE 'false' END AS isAppointmentPatientPortal,  " +
                " ta.appointmentreason AS appointmentReason, " +
                " ar.ar_id AS arId,  " +
                " ar.ar_name AS arName,  " +
                " muser.created_by AS createdBy, " +
                " ta.appointment_unit_id AS appointmentUnitId, " +
                " u.unit_name AS appointmentUnitName,u.unit_address,ifnull(cc.city_name,'')as city_name, ifnull(dss.district_name,'')as district_name,ifnull(st.state_name,'')as state_name,ifnull(cn.country_name,'')as country_name,ifnull(u.unit_mobile,'')as unit_mobile, " +
                " IFNULL(u.unit_email,''), ifnull(ta.created_date,'') AS createdDate, ta.appointmentbookby ,ta.appointment_is_cancelledreasone,muser.user_dob, IFNULL(muser.user_email,''), muser.user_address, muser.user_mobile, mp.patient_id AS patientId, IFNULL(mbg.bloodgroup_name,''), gen.gender_id as patientGenderId," +
                " mser.service_id, mser.service_base_rate,  CASE WHEN ta.is_in_person  THEN 'true' ELSE 'false' END, muser.user_id, ta.refer_history_id," +
                " case when ta.refer_history_id != 'NULL' then CONCAT(ifnull(murh.user_fullname,'') ,' on ', ifnull(Date(trh.created_date),''), ' Subject ', IFNULL(trh.rh_subject,'')) ELSE '' end AS Reason " +
                " FROM trn_appointment ta " +
                " INNER JOIN mst_patient mp ON mp.patient_id = ta.appointment_patient_id " +
                " INNER JOIN mst_user muser ON muser.user_id = mp.patient_user_id " +
                " LEFT JOIN mst_bloodgroup mbg ON mbg.bloodgroup_id = muser.user_bloodgroup_id  " +
                " LEFT JOIN mst_title mti ON mti.title_id = muser.user_title_id " +
                " LEFT JOIN mst_gender gen ON gen.gender_id = muser.user_gender_id " +
                " LEFT JOIN mst_unit u ON u.unit_id = ta.appointment_unit_id " +
                " LEFT JOIN mst_city cc ON cc.city_id=u.unit_city_id " +
                " LEFT JOIN mst_district dss ON dss.district_id =cc.city_district_id " +
                " LEFT JOIN mst_state st ON st.state_id=cc.city_state_id " +
                " LEFT JOIN mst_country cn ON cn.country_id=st.state_country_id " +
                " LEFT JOIN mst_staff map ON map.staff_id=ta.appointment_staff_id " +
                " LEFT JOIN mst_user muu ON muu.user_id = map.staff_user_id " +
                " LEFT JOIN mst_speciality spl ON spl.speciality_id=map.staff_speciality_id " +
                " LEFT JOIN mst_department d ON d.department_id = ta.appointment_department_id " +
                " LEFT JOIN mst_appointment_reason ar ON ar.ar_id = ta.appointment_ar_id " +
                " LEFT JOIN mst_appointment_type AT ON AT.at_id = ta.appointment_at_id " +
                " LEFT JOIN mst_patient_type pt ON pt.pt_id = ta.appointment_pt_id " +
                " LEFT JOIN mst_booking_type bt ON bt.bt_id = ta.appointment_bt_id " +
                " LEFT JOIN mst_referring_entity_type ret ON ret.ret_id = ta.appointment_ret_id " +
                " LEFT JOIN mst_referring_entity re ON re.re_id = ta.appointment_re_id " +
                " LEFT JOIN temr_timeline tt ON tt.timeline_id = ta.appointment_timeline_id " +
                " LEFT JOIN mst_visit mv ON mv.visit_id = tt.timeline_visit_id " +
                " LEFT JOIN temr_referral_history trh ON trh.rh_id = ta.refer_history_id " +
                " LEFT JOIN mst_staff msst ON msst.staff_id = trh.rh_staff_id " +
                " LEFT JOIN mst_title mt ON mt.title_id = muu.user_title_id " +
                " LEFT JOIN mst_gender mg ON mg.gender_id = muu.user_gender_id " +
                " LEFT JOIN mst_unit mu ON mu.unit_id = ta.parent_unit_id " +
                " LEFT JOIN mst_cluster mcc ON mcc.cluster_id = ta.hub_id " +
                " LEFT JOIN mbill_service mser ON mser.service_id = ta.appointment_service_id " +
                " LEFT JOIN mst_staff msrh ON msrh.staff_id = trh.rh_staff_id " +
                " LEFT JOIN mst_user murh ON murh.user_id = msrh.staff_user_id  " +
                " WHERE ta.is_active = 1 AND ta.is_deleted = 0 ";
        if (appStatus == 1) {
            query += " and ta.appointment_is_confirm = false  and appointment_is_block = false and ta.appointment_is_cancelled =false ";
//            return trnAppointmentRepository.findAllByAppointmentUnitIdUnitIdAndAppointmentIsConfirmFalseAndAppointmentIsBlockFalseAndAppointmentIsCancelledFalse(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
        if (appStatus == 2) {
            query += " and ta.appointment_is_confirm = true and ta.appointment_is_cancelled =false and tt.isemrfinal = false";
//            return trnAppointmentRepository.findAllByAppointmentUnitIdUnitIdAndAppointmentIsConfirmTrueAndAppointmentIsCancelledreasoneIsNullAndAppointmentIsCancelledFalse(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
//            query += " and ta.appointment_is_confirm = false  ";
        }
        if (appStatus == 3) {
            query += " and ta.appointment_is_cancelled =true ";
//            return trnAppointmentRepository.findAllByAppointmentUnitIdUnitIdAndAppointmentIsConfirmFalseAndAppointmentIsCancelledreasoneIsNotNullAndAppointmentIsCancelledTrue(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
        if (appStatus == 4) {
            query += "and ta.appointment_is_cancelled = false and ta.appointment_is_block = true ";
//            return trnAppointmentRepository.findAllByAppointmentUnitIdUnitIdAndAppointmentIsConfirmFalseAndAppointmentIsCancelledreasoneIsNotNullAndAppointmentIsBlockTrueAndAppointmentIsCancelledFalse(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
        if (appStatus == 5) {
            query += " and tt.isemrfinal = true ";
//            return trnAppointmentRepository.findAllByAppointmentUnitIdUnitIdAndAppointmentIsConfirmFalseAndAppointmentIsCancelledreasoneIsNotNullAndAppointmentIsBlockTrueAndAppointmentIsCancelledFalse(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
        query += "  and ta.appointment_unit_id=" + unitId;
/*
        if(bookstrtdate.equals("")|| bookstrtdate.equals("null")){
            startdate = strDate;
        }
        if (bookendtdate.equals("")|| bookendtdate.equals("null")) {
            enddate = strDate;
        }*/
        if (!patientName.equals("")) {
            query += " and concat(muser.user_firstname,' ', muser.user_lastname) LIKE '%" + patientName + "%' ";

        }
        if (!mrNo.equals("")) {
            query += " and mp.patient_mr_no LIKE '%" + mrNo + "%' ";

        }
        if (!mobNo.equals("")) {
            query += " and muser.user_mobile LIKE '%" + mobNo + "%' ";
        }
        if (filter.equals("bookeddate")) {
            query += " AND (DATE(ta.created_date) BETWEEN '" + bookstrtdate + "' and '" + bookendtdate + "') ";

        } else {
            query += " and (date(ta.appointment_date) between '" + startdate + "' and '" + enddate + "') ";
        }
        if (appReason != 0) {
            query += " and ta.appointment_ar_id = " + appReason;
        }
        query += "AND ta.appointment_slot IS NOT NULL order by ta.appointment_date desc, STR_TO_DATE(ta.appointment_slot, '%l:%i %p') ";
        String CountQuery = " select count(*) from ( " + query + " ) as combine ";
        if (Integer.parseInt(size) != 0) {
            query += " limit " + ((Integer.parseInt(page) - 1) * Integer.parseInt(size)) + "," + Integer.parseInt(size);
        }
        System.out.println("query " + query);
        BigInteger count = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
        List<Object[]> list = entityManager.createNativeQuery(query).getResultList();//, TrnAppointment.class
        if (list.size() > 0) {
//            list.get(0).setCount(count.intValue());
        }
//        List<Object[]> mstVisit = mstVisitRepository.getByVisitPatientIdPatientIdEquals(patientId);
//
        ArrayList<AppointmentListDto> list1 = new ArrayList<>();
//
        for (int i = 0; i < list.size(); i++) {
            AppointmentListDto appointmentListDto = new AppointmentListDto();
            appointmentListDto.setAppointmentId(list.get(i)[0]);
            appointmentListDto.setIsAppointmentConfirm(Boolean.parseBoolean("" + list.get(i)[1]));
            appointmentListDto.setIsRescheduled(Boolean.parseBoolean("" + list.get(i)[2]));
            appointmentListDto.setAppointmentIsCancelled(Boolean.parseBoolean("" + list.get(i)[3]));
            appointmentListDto.setAppointmentTimelineId(list.get(i)[4]);
            appointmentListDto.setIsemrfinal(Boolean.parseBoolean("" + list.get(i)[5]));
            appointmentListDto.setIsAppointmentReferral(Boolean.parseBoolean("" + list.get(i)[6]));
            appointmentListDto.setPatientName(list.get(i)[7]);
            appointmentListDto.setPatientAge("" + list.get(i)[8]);
            appointmentListDto.setPatientGender("" + list.get(i)[9]);
            appointmentListDto.setAppointmentMrNo(list.get(i)[10]);
            appointmentListDto.setUserUid("" + list.get(i)[11]);
            appointmentListDto.setAppointmentStaffId(list.get(i)[12]);
            appointmentListDto.setStaffName("" + list.get(i)[13]);
            appointmentListDto.setSpecialityName("" + list.get(i)[14]);
            appointmentListDto.setParentUnitName("" + list.get(i)[15]);
            appointmentListDto.setClusterName(list.get(i)[16]);
            appointmentListDto.setServiceName("" + list.get(i)[17]);
            appointmentListDto.setAppointmentDate(list.get(i)[18]);
            appointmentListDto.setAppointmentSlot(list.get(i)[19]);
            appointmentListDto.setIsAppointmentPatientPortal(Boolean.parseBoolean("" + list.get(i)[20]));
            appointmentListDto.setAppointmentReason(list.get(i)[21]);
            appointmentListDto.setAppointmentArId(list.get(i)[22]);
            appointmentListDto.setArName("" + list.get(i)[23]);
            appointmentListDto.setCreatedBy("" + list.get(i)[24]);
            appointmentListDto.setAppointmentUnitId(list.get(i)[25]);
            appointmentListDto.setAppointmentUnitName("" + list.get(i)[26]);
            appointmentListDto.setUnit_address("" + list.get(i)[27]);
            appointmentListDto.setUnitCity("" + list.get(i)[28]);
            appointmentListDto.setUnitdistrict("" + list.get(i)[29]);
            appointmentListDto.setUnitstate("" + list.get(i)[30]);
            appointmentListDto.setUnitcountry("" + list.get(i)[31]);
            appointmentListDto.setUnitmobile("" + list.get(i)[32]);
            appointmentListDto.setUnitEmail("" + list.get(i)[33]);
            appointmentListDto.setCreatedDate("" + new Date());
//            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//             String strDate2 = formatter2.format(list.get(i)[34]);
//             appointmentListDto.setCreatedDate(strDate2);
            appointmentListDto.setCreatedDate("" + list.get(i)[34]);
            /*SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
             String strDate1 = formatter1.format(list.get(i)[34]);ssss
             appointmentListDto.setCreatedDate(new Date(strDate1));*/
            appointmentListDto.setAppointmentbookby("" + list.get(i)[35]);
            appointmentListDto.setAppointmentIsCancelledreasone(list.get(i)[36]);
            appointmentListDto.setPatientDOB("" + list.get(i)[37]);
            appointmentListDto.setPatientEmail("" + list.get(i)[38]);
            appointmentListDto.setPatientAddress("" + list.get(i)[39]);
            appointmentListDto.setPatientMobile("" + list.get(i)[40]);
            appointmentListDto.setPatientId("" + list.get(i)[41]);
            appointmentListDto.setPatientBloodgroupName("" + list.get(i)[42]);
            appointmentListDto.setPatientGenderId("" + list.get(i)[43]);
//            if (list.get(i)[44] != null) {
            if (!list.get(i)[44].equals("null")) {
                appointmentListDto.setAppointmentServiceId("" + list.get(i)[44]);
            }
            appointmentListDto.setAppointmentServiceAmount("" + list.get(i)[45]);
            appointmentListDto.setIsInPerson(Boolean.parseBoolean("" + list.get(i)[46]));
            appointmentListDto.setAppointmentUserId("" + list.get(i)[47]);
//            if(!list.get(i)[48].equals("null")) {
            appointmentListDto.setReferHistoryId("" + list.get(i)[48]);
            appointmentListDto.setReason("" + list.get(i)[49]);
            appointmentListDto.setCount(count);
//            }
            list1.add(appointmentListDto);
        }
        return list1;
    }

    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getlistofAppointment")
    public ResponseEntity searchListCancelOfAppointment(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnAppointmentSearch trnAppointmentSearch) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        if (trnAppointmentSearch.getFromdate().equals("") || trnAppointmentSearch.getFromdate().equals("null")) {
            trnAppointmentSearch.setFromdate(strDate);
        }
        if (trnAppointmentSearch.getTodate().equals("") || trnAppointmentSearch.getTodate().equals("null")) {
//            todate = strDate;
            trnAppointmentSearch.setTodate(strDate);
        }
        //test commit
        String Query = " SELECT ta.appointment_date AS appoimtmentDate, ta.created_date AS bookingDate, ifnull(ta.appointment_slot,'') AS slot, mu.unit_name AS bookingUnit, " +
                " case when ta.appointment_is_cancelled=1 then 'true' ELSE 'false' end AS isCancel, case when ta.appointment_is_confirm=1 then 'true' ELSE 'false' end AS isConfirm, ta.appointment_mr_no AS mrNo,  " +
                "ta.appointment_staff_id AS appStaff, " +
                "mt.title_name,mus.user_firstname AS fname,ifnull(mus.user_middlename,'') AS mname, ifnull(mus.user_lastname,'') AS lname, mus.user_age AS age, mg.gender_name AS gender, " +
                "mus.user_mobile AS mobile, ifnull(mus.user_residence_phone,'') AS phone, ifnull(mus.user_email,'') AS email, ta.appointmentreason as appointreason, mus.user_driving_no as userDrivingNo, ms.service_name as serviceName ," +
                "ifnull(concat(mssu.user_firstname,' ',mssu.user_lastname),'') AS DoctorName, " +
                " case when ta.is_virtual=1 then 'Virtual' ELSE 'Physical' END AS isVirtual, mstf.unit_name AS DrUnit,  ta.created_by AS createdBy  " +
                "FROM trn_appointment ta " +
                "LEFT JOIN mst_unit mu ON mu.unit_id = ta.appointment_unit_id " +
                "LEFT JOIN mbill_service ms ON ms.service_id = ta.appointment_service_id " +
                "LEFT JOIN mst_staff mst ON mst.staff_id = ta.appointment_staff_id " +
                "LEFT JOIN mst_user mssu ON mst.staff_user_id=mssu.user_id " +
                "LEFT JOIN mst_patient mp ON mp.patient_id = ta.appointment_patient_id " +
                "LEFT JOIN mst_user mus ON mus.user_id = mp.patient_user_id " +
                "LEFT JOIN mst_gender mg ON mus.user_gender_id = mg.gender_id " +
                "LEFT JOIN mst_title mt ON mt.title_id = mus.user_title_id " +
                "LEFT JOIN temr_timeline ttl ON ttl.timeline_id = ta.appointment_timeline_id " +
                "LEFT JOIN mst_visit mv ON mv.visit_id = ttl.timeline_visit_id " +
                "LEFT JOIN staff_configuration sc ON sc.sc_staff_id = mst.staff_id\n" +
                "LEFT JOIN mst_unit mstf ON mstf.unit_id=sc.sc_unit_id " +
                "WHERE ta.is_active = 1 AND ta.is_deleted = 0 ";
        String CountQuery = "";
        String columnName = "";
        // unit Id
/*
        if (!unitList[0].equals("null")) {
            String values = String.valueOf(unitList[0]);
            for (int i = 1; i < unitList.length; i++) {ca
                values += "," + unitList[i];
`            }
            Query += " and t.appointment_unit_id in (" + values + ") ";
        }
*/
        if (trnAppointmentSearch.getUnitid() != 0) {
            Query += " and ta.appointment_unit_id = '" + trnAppointmentSearch.getUnitid() + "'";
        }
        // for doctor
        if (trnAppointmentSearch.getStaffId1() != null && !trnAppointmentSearch.getStaffId1().equals("0")) {
            Query += " and ta.appointment_staff_id = " + trnAppointmentSearch.getStaffId1() + " ";
        }
        if (trnAppointmentSearch.getConsultType() == 1) {
            Query += " and mv.is_virtual = 0 ";
        }
        if (trnAppointmentSearch.getConsultType() == 2) {
            Query += " and mv.is_virtual = 1 ";
        }
        if (trnAppointmentSearch.getPtname() != null && !trnAppointmentSearch.getPtname().equals("0")) {
            Query += " and concat(mus.user_firstname, ' ', mus.user_lastname) LIKE '%" + trnAppointmentSearch.getPtname() + "%' ";
        }
        if (trnAppointmentSearch.getMrno() != null && !trnAppointmentSearch.getMrno().equals("0")) {
            Query += " and mp.patient_mr_no LIKE '%" + trnAppointmentSearch.getMrno() + "%' ";
        }
        if (trnAppointmentSearch.getMobile() != null && !trnAppointmentSearch.getMobile().equals("0")) {
            Query += " and mus.user_mobile LIKE '%" + trnAppointmentSearch.getMobile() + "%' ";
        }
        Query += " and (date(ta.appointment_date) between '" + trnAppointmentSearch.getFromdate() + "' and '" + trnAppointmentSearch.getTodate() + "')  ";
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "appointmentDate,bookingDate,slot,bookingUnit,isCancel,isConfirm,mrNo,appStaff,title_name,fname,mname,lname,age,gender,mobile,phone,email,appointreason,userDrivingNo,serviceName,DoctorName,isVirtual,DrUnit,createdBy";
        Query += " limit " + ((trnAppointmentSearch.getOffset() - 1) * trnAppointmentSearch.getLimit()) + "," + trnAppointmentSearch.getLimit();
        System.out.println("Query=" + Query);
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
    }

    @RequestMapping("cancelAppointment")
    public Map<String, String> confirmAppointment(@RequestHeader("X-tenantId") String tenantName, @RequestBody String data) {
        TenantContext.setCurrentTenant(tenantName);
        JSONObject object = new JSONObject(data);
        TrnAppointment trnAppointment1 = trnAppointmentRepository.getById(object.getLong("id"));
        int count;
        Map<String, String> respMap = new HashMap<String, String>();
        if (trnAppointment1.getIsAppointmentReferral()) {
            count = trnAppointmentRepository.cancelAppointment(object.getLong("id"), object.getString("reason"), object.getLong("cancelrasonId"), object.getBoolean("isRescheduled"), true);
        } else {
            count = trnAppointmentRepository.cancelAppointment(object.getLong("id"), object.getString("reason"), object.getLong("cancelrasonId"), object.getBoolean("isRescheduled"), false);
        }
        if (count == 0) {
            respMap.put("status", "0");
            respMap.put("message", "fail");
        } else {
            respMap.put("status", "1");
            respMap.put("message", "success");
        }
        return respMap;

    }

    @PostMapping
    @RequestMapping("futureappRequestList")
    public List<TrnAppointment> futureappRequestList(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                     @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                                     @RequestParam(value = "qString", required = false) String qString,
                                                     @RequestParam(value = "unitId", required = false) long unitId,
                                                     @RequestParam(value = "patientId", required = false) long patientId,
                                                     @RequestParam(value = "filter", required = false) String filter,
                                                     @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                     @RequestParam(value = "col", required = false, defaultValue = "appointmentDate") String col) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        String query = "";
        query += " SELECT ta.*,trh.created_date AS rhDate," +
                " muu.user_fullname AS staffName, trh.rh_subject AS rfSubject, " +
                " CONCAT(muu.user_fullname,' on ', DATE(trh.created_date), ' Reason ', trh.rh_subject) AS Reason, " +
                " IFNULL(mu.unit_name,'') AS ParentUnit, IFNULL(mcc.cluster_name,'') AS clusterName " +
                " FROM trn_appointment ta " +
                " INNER JOIN mst_patient mp ON mp.patient_id = ta.appointment_patient_id " +
                " INNER JOIN mst_user muser ON muser.user_id = mp.patient_user_id " +
                " LEFT JOIN temr_timeline tt ON tt.timeline_id = ta.appointment_timeline_id " +
                " LEFT JOIN mst_visit mv ON mv.visit_id = tt.timeline_visit_id " +
                " LEFT JOIN temr_referral_history trh ON trh.rh_id = ta.refer_history_id " +
                " LEFT JOIN mst_staff msst ON msst.staff_id = trh.rh_staff_id " +
                " LEFT JOIN mst_user muu ON muu.user_id = msst.staff_user_id " +
                " LEFT JOIN mst_unit mu ON mu.unit_id = ta.parent_unit_id " +
                " LEFT JOIN mst_cluster mcc ON mcc.cluster_id = ta.hub_id " +
                " WHERE ta.is_active = 1 AND ta.is_deleted = 0 And ta.appointment_is_cancelled = 0 and ta.appointment_unit_id =" + unitId +
                " AND mp.patient_id =" + patientId +
                " AND DATE(ta.appointment_date) >= DATE(NOW()) " +
                " ORDER BY ta.appointment_date ASC, STR_TO_DATE(ta.appointment_slot, '%l:%i %p') ";
        String CountQuery = " select count(*) from ( " + query + " ) as combine ";
        query += " limit " + ((Integer.parseInt(page) - 1) * Integer.parseInt(size)) + "," + Integer.parseInt(size);
        System.out.println("query " + query);
        BigInteger count = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
        List<TrnAppointment> list = entityManager.createNativeQuery(query, TrnAppointment.class).getResultList();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        for (TrnAppointment trnAppointment : list) {
            if (trnAppointment.getReferHistoryId() != null) {
                TemrReferralHistory temrReferralHistory = temrReferralHistoryRepository.getById(Long.parseLong(trnAppointment.getReferHistoryId()));
                trnAppointment.setReason(temrReferralHistory.getRhStaffId().getStaffUserId().getUserFullname() + " on " + simpleDateFormat.format(temrReferralHistory.getCreatedDate()) + " Reason " + temrReferralHistory.getRhSubject());
            }
            if (trnAppointment.getParentUnitId() != null) {
                MstUnit mstUnit = mstUnitRepository.getById(Long.parseLong(trnAppointment.getParentUnitId()));
                trnAppointment.setParentUnitName(mstUnit.getUnitName());
            }
            if (trnAppointment.getHubId() != null) {
                MstCluster mstCluster = mstClusterRepository.getById(Long.parseLong(trnAppointment.getHubId()));
                trnAppointment.setClusterName(mstCluster.getClusterName());
            }
        }
        if (list.size() > 0) {
            list.get(0).setCount(count.intValue());
        }
        return list;
    }

    @GetMapping
    @RequestMapping("AppoinmentByUserIdList")
    public Iterable<TrnAppointment> AppoinmentByUserIdList(@RequestParam(value = "userId", required = false) Long userId, @RequestParam(value = "page", required = false, defaultValue = "1")
            String page, @RequestParam(value = "size", required = false, defaultValue = "10") String size, @RequestParam(value = "fromDate", required = false) String fromDate, @RequestParam(value = "toDate", required = false) String toDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date fDate = null;
        Date tDate = null;
        Date appointmentData = null;
        try {
            if (fromDate != null) {
                fDate = formatter.parse(fromDate);
            }
            if (toDate != null) {
                tDate = formatter.parse(toDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int offset = Integer.parseInt(size) * (Integer.parseInt(page) - 1);
        int limit = Integer.parseInt(size);
        String query = " ";
        if (fDate == tDate || fDate == null || tDate == null) {
            query = " select * from trn_appointment p where p.appointment_user_id= " + userId + " and p.is_active=1 and p.is_deleted=0 and appointment_is_closed=0 and CAST(p.appointment_date as DATE)>= CAST(now() as DATE)  order by p.appointment_id desc ";
        } else {
            System.out.println("fDate : " + fDate);
            System.out.println("tDate : " + tDate);
            System.out.println("tDate : " + fDate.toString().equalsIgnoreCase(tDate.toString()));
            if (fDate.toString().equalsIgnoreCase(tDate.toString())) {
                query = " select * from trn_appointment p where p.appointment_user_id= " + userId + " and p.is_active=1 and p.is_deleted=0 and appointment_is_closed=0 and  CAST(p.appointment_date as DATE)>= CAST('" + toDate + "' as DATE) order by p.appointment_id desc ";
            } else {
                query = " select * from trn_appointment p where p.appointment_user_id= " + userId + " and p.is_active=1 and p.is_deleted=0 and appointment_is_closed=0 and CAST(p.appointment_date as DATE) >=CAST('" + fromDate + "' as DATE) and CAST(p.appointment_date as DATE) <=CAST('" + toDate + "' as DATE) order by p.appointment_id desc ";
            }
        }
        String CountQuery = " select count(*) from (" + query + ") as combine ";
        BigInteger count = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
        query += " limit " + offset + ", " + limit;
        System.out.println("Query : " + query);
        List<TrnAppointment> trnAppointmentlist = entityManager.createNativeQuery(query, TrnAppointment.class).getResultList();
        if (trnAppointmentlist.size() > 0) {
            trnAppointmentlist.get(0).setCount1(count);
        }
        return trnAppointmentlist;
    }

    @GetMapping
    @RequestMapping("ClosedAppoinmentByUserId")
    public Iterable<TrnAppointment> ClosedAppoinmentByUserId(@RequestParam(value = "userId", required = false) Long userId, @RequestParam(value = "page", required = false, defaultValue = "1")
            String page, @RequestParam(value = "size", required = false, defaultValue = "10") String size) {
        int offset = Integer.parseInt(size) * (Integer.parseInt(page) - 1);
        int limit = Integer.parseInt(size);
        String query = " select * from trn_appointment p where p.appointment_user_id= " + userId + " and p.is_active=1 and p.is_deleted=0 and p.appointment_is_closed =true order by p.appointment_id desc ";
        String CountQuery = " select count(*) from (" + query + ") as combine ";
        BigInteger count = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
        query += " limit " + offset + ", " + limit;
        List<TrnAppointment> trnAppointmentlist = entityManager.createNativeQuery(query, TrnAppointment.class).getResultList();
        if (trnAppointmentlist.size() > 0) {
            trnAppointmentlist.get(0).setCount1(count);
        }
        return trnAppointmentlist;
    }

}




