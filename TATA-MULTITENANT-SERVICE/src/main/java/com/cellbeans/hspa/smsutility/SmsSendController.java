package com.cellbeans.hspa.smsutility;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.emailutility.Emailsend;
import com.cellbeans.hspa.mstpatient.MstPatient;
import com.cellbeans.hspa.mstpatient.MstPatientRepository;
import com.cellbeans.hspa.trnadmission.TrnAdmission;
import com.cellbeans.hspa.trnappointment.TrnAppointment;
import com.cellbeans.hspa.trnappointment.TrnAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@RestController
@RequestMapping("/smssend")
public class SmsSendController {

    @Autowired
    Emailsend emailsend;
    @Autowired
    Sendsms sendsms;
    @Autowired
    TrnAppointmentRepository trnAppointmentRepository;
    @Autowired
    private com.cellbeans.hspa.applicationproperty.Propertyconfig Propertyconfig;

    @Autowired
    MstPatientRepository mstPatientRepository;

    @RequestMapping("sendmessage/{mobileno}/{emailid}/{message}")
    public String SnedSMSEMAIL(@RequestHeader("X-tenantId") String tenantName, @PathVariable("mobileno") String mobileno, @PathVariable("emailid") String emailid, @PathVariable("message") String message) {
        TenantContext.setCurrentTenant(tenantName);
        if (Propertyconfig.getSmsApi()) {
            if (mobileno != null) {
                System.out.println(mobileno);
                try {
                    sendsms.sendmessage(mobileno, message);

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        if (Propertyconfig.getEmailApi()) {
            if (emailid != null) {
                System.out.println(emailid);
                try {
                    //  emailsend.sendSimpleMessage(emailid, "WELCOME to HSPA", message);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        return "successfully";
    }

    @RequestMapping("AppoinmentConform/{appoinmentID}")
    public String AppoinmentConform(@RequestHeader("X-tenantId") String tenantName, @PathVariable("appoinmentID") Long appoinmentID) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAppointment trnAppointment = trnAppointmentRepository.getById(appoinmentID);
        if (Propertyconfig.getSmsApi()) {
            if (trnAppointment.getAppointmentUserId().getUserMobile() != null) {
                try {
                    sendsms.sendmessage(trnAppointment.getAppointmentUserId().getUserMobile(), "Your Appointment is confirmed with " + trnAppointment.getAppointmentStaffId().getStaffUserId().getUserTitleId().getTitleName() + " " + trnAppointment.getAppointmentStaffId().getStaffUserId().getUserFirstname() + " " + trnAppointment.getAppointmentStaffId().getStaffUserId().getUserLastname() + " on " + trnAppointment.getAppointmentDate() + " " + trnAppointment.getAppointmentSlot() + " successfully");

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        return "successfully";
    }

    @RequestMapping("AppoinmentCancel/{appoinmentID}")
    public String AppoinmentCancel(@RequestHeader("X-tenantId") String tenantName, @PathVariable("appoinmentID") Long appoinmentID) {
        TenantContext.setCurrentTenant(tenantName);
        TrnAppointment trnAppointment = trnAppointmentRepository.getById(appoinmentID);
        Date appDate = trnAppointment.getAppointmentDate();
        SimpleDateFormat format = new SimpleDateFormat("EEE MM/dd/yyyy HH:mm zzz", Locale.ENGLISH);
        String customAppDate = format.format(appDate);
        if (Propertyconfig.getSmsApi()) {
            if (trnAppointment.getAppointmentUserId().getUserMobile() != null) {
                try {
                    trnAppointment.getAppointmentStaffId().getStaffUserId().getUserMobile();
                    sendsms.sendmessage(trnAppointment.getAppointmentUserId().getUserMobile(), " Appointment with " + trnAppointment.getAppointmentStaffId().getStaffUserId().getUserTitleId().getTitleName() + " " + trnAppointment.getAppointmentStaffId().getStaffUserId().getUserFirstname() + " " + trnAppointment.getAppointmentStaffId().getStaffUserId().getUserLastname() + " on " + customAppDate + " " + trnAppointment.getAppointmentSlot() + " Booked Successfully Cancelled successfully");

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        return "successfully";
    }

    @RequestMapping("registrationsuccesful/{patientId}")
    public String RegistrationConform(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId) {
        TenantContext.setCurrentTenant(tenantName);
        MstPatient mstPatient = mstPatientRepository.getById(patientId);
        if (Propertyconfig.getSmsApi()) {
            if (mstPatient.getPatientUserId().getUserMobile() != null) {
                try {
                    sendsms.sendmessage(mstPatient.getPatientUserId().getUserMobile(), "Your Registration Of " + mstPatient.getPatientUserId().getUserTitleId().getTitleName() + " " + mstPatient.getPatientUserId().getUserFirstname() + " " + mstPatient.getPatientUserId().getUserLastname() + " on " + mstPatient.getPatientUserId().getCreatedDate() + " is Registred successfully");

                } catch (Exception e) {
                    System.out.println(e);
                }
                if (Propertyconfig.getDirectorMobile1() != null) {
                    sendsms.sendmessage(Propertyconfig.getDirectorMobile1(), "Your Registration Of " + mstPatient.getPatientUserId().getUserTitleId().getTitleName() + " " + mstPatient.getPatientUserId().getUserFirstname() + " " + mstPatient.getPatientUserId().getUserLastname() + " on " + mstPatient.getPatientUserId().getCreatedDate() + " is Registred successfully");

                }
                if (Propertyconfig.getDirectorMobile2() != null) {
                    sendsms.sendmessage(Propertyconfig.getDirectorMobile2(), "Your Registration Of " + mstPatient.getPatientUserId().getUserTitleId().getTitleName() + " " + mstPatient.getPatientUserId().getUserFirstname() + " " + mstPatient.getPatientUserId().getUserLastname() + " on " + mstPatient.getPatientUserId().getCreatedDate() + " is Registred successfully");

                }
                if (Propertyconfig.getDirectorMobile3() != null) {
                    sendsms.sendmessage(Propertyconfig.getDirectorMobile3(), "Your Registration Of " + mstPatient.getPatientUserId().getUserTitleId().getTitleName() + " " + mstPatient.getPatientUserId().getUserFirstname() + " " + mstPatient.getPatientUserId().getUserLastname() + " on " + mstPatient.getPatientUserId().getCreatedDate() + " is Registred successfully");

                }
                if (Propertyconfig.getDirectorMobile4() != null) {
                    sendsms.sendmessage(Propertyconfig.getDirectorMobile4(), "Your Registration Of " + mstPatient.getPatientUserId().getUserTitleId().getTitleName() + " " + mstPatient.getPatientUserId().getUserFirstname() + " " + mstPatient.getPatientUserId().getUserLastname() + " on " + mstPatient.getPatientUserId().getCreatedDate() + " is Registred successfully");

                }
                if (Propertyconfig.getDirectorMobile5() != null) {
                    sendsms.sendmessage(Propertyconfig.getDirectorMobile5(), "Your Registration Of " + mstPatient.getPatientUserId().getUserTitleId().getTitleName() + " " + mstPatient.getPatientUserId().getUserFirstname() + " " + mstPatient.getPatientUserId().getUserLastname() + " on " + mstPatient.getPatientUserId().getCreatedDate() + " is Registred successfully");

                }
            }
        }
        return "successfully";
    }

    @RequestMapping("admissionsuccesful")
    public String admissionSuccesful(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnAdmission trnAdmission) {
        TenantContext.setCurrentTenant(tenantName);
        if (Propertyconfig.getSmsApi()) {
            if (trnAdmission.getAdmissionPatientId().getPatientUserId().getUserMobile() != null) {
                try {
                    sendsms.sendmessage(trnAdmission.getAdmissionPatientId().getPatientUserId().getUserMobile(), "Your admission is successful");

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            if (trnAdmission.getAdmissionStaffId().getStaffUserId().getUserMobile() != null) {
                try {
                    sendsms.sendmessage(trnAdmission.getAdmissionStaffId().getStaffUserId().getUserMobile(), "Admission Of " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserTitleId().getTitleName() + " " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserFirstname() + " " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserLastname() + " on " + trnAdmission.getAdmissionPatientId().getPatientUserId().getCreatedDate() + " is admitted successfully");

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            if (Propertyconfig.getDirectorMobile1() != null) {
                sendsms.sendmessage(Propertyconfig.getDirectorMobile1(), "Admission Of " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserTitleId().getTitleName() + " " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserFirstname() + " " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserLastname() + " on " + trnAdmission.getAdmissionPatientId().getPatientUserId().getCreatedDate() + " is admitted successfully");

            }
            if (Propertyconfig.getDirectorMobile2() != null) {
                sendsms.sendmessage(Propertyconfig.getDirectorMobile2(), "Admission Of " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserTitleId().getTitleName() + " " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserFirstname() + " " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserLastname() + " on " + trnAdmission.getAdmissionPatientId().getPatientUserId().getCreatedDate() + " is admitted successfully");

            }
            if (Propertyconfig.getDirectorMobile3() != null) {
                sendsms.sendmessage(Propertyconfig.getDirectorMobile3(), "Admission Of " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserTitleId().getTitleName() + " " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserFirstname() + " " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserLastname() + " on " + trnAdmission.getAdmissionPatientId().getPatientUserId().getCreatedDate() + " is admitted successfully");

            }
            if (Propertyconfig.getDirectorMobile4() != null) {
                sendsms.sendmessage(Propertyconfig.getDirectorMobile4(), "Admission Of " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserTitleId().getTitleName() + " " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserFirstname() + " " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserLastname() + " on " + trnAdmission.getAdmissionPatientId().getPatientUserId().getCreatedDate() + " is admitted successfully");

            }
            if (Propertyconfig.getDirectorMobile5() != null) {
                sendsms.sendmessage(Propertyconfig.getDirectorMobile5(), "Admission Of " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserTitleId().getTitleName() + " " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserFirstname() + " " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserLastname() + " on " + trnAdmission.getAdmissionPatientId().getPatientUserId().getCreatedDate() + " is admitted successfully");

            }
        }
        return "successfully";

    }

    @PostMapping("dischargesuccesful")
    public String dischargeSuccesful(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnAdmission trnAdmission) {
        TenantContext.setCurrentTenant(tenantName);
        if (Propertyconfig.getSmsApi()) {
            if (trnAdmission.getAdmissionPatientId().getPatientUserId().getUserMobile() != null) {
                try {
                    sendsms.sendmessage(trnAdmission.getAdmissionPatientId().getPatientUserId().getUserMobile(), "Discharge Of " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserTitleId().getTitleName() + " " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserFirstname() + " " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserLastname() + " on " + trnAdmission.getAdmissionPatientId().getPatientUserId().getCreatedDate() + " is Registred successfully");

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            if (trnAdmission.getAdmissionStaffId().getStaffUserId().getUserMobile() != null) {
                try {
                    sendsms.sendmessage(trnAdmission.getAdmissionStaffId().getStaffUserId().getUserMobile(), "Discharge Of " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserTitleId().getTitleName() + " " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserFirstname() + " " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserLastname() + " on " + trnAdmission.getAdmissionPatientId().getPatientUserId().getCreatedDate() + " is Registred successfully");

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            if (Propertyconfig.getDirectorMobile1() != null) {
                sendsms.sendmessage(Propertyconfig.getDirectorMobile1(), "Discharge Of " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserTitleId().getTitleName() + " " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserFirstname() + " " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserLastname() + " on " + trnAdmission.getAdmissionPatientId().getPatientUserId().getCreatedDate() + " is Registred successfully");

            }
            if (Propertyconfig.getDirectorMobile2() != null) {
                sendsms.sendmessage(Propertyconfig.getDirectorMobile2(), "Discharge Of " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserTitleId().getTitleName() + " " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserFirstname() + " " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserLastname() + " on " + trnAdmission.getAdmissionPatientId().getPatientUserId().getCreatedDate() + " is Registred successfully");

            }
            if (Propertyconfig.getDirectorMobile3() != null) {
                sendsms.sendmessage(Propertyconfig.getDirectorMobile3(), "Discharge Of " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserTitleId().getTitleName() + " " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserFirstname() + " " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserLastname() + " on " + trnAdmission.getAdmissionPatientId().getPatientUserId().getCreatedDate() + " is Registred successfully");

            }
            if (Propertyconfig.getDirectorMobile4() != null) {
                sendsms.sendmessage(Propertyconfig.getDirectorMobile4(), "Discharge Of " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserTitleId().getTitleName() + " " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserFirstname() + " " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserLastname() + " on " + trnAdmission.getAdmissionPatientId().getPatientUserId().getCreatedDate() + " is Registred successfully");

            }
            if (Propertyconfig.getDirectorMobile5() != null) {
                sendsms.sendmessage(Propertyconfig.getDirectorMobile5(), "Discharge Of " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserTitleId().getTitleName() + " " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserFirstname() + " " + trnAdmission.getAdmissionPatientId().getPatientUserId().getUserLastname() + " on " + trnAdmission.getAdmissionPatientId().getPatientUserId().getCreatedDate() + " is Registred successfully");

            }

        }
        return "successfully";
    }

}



