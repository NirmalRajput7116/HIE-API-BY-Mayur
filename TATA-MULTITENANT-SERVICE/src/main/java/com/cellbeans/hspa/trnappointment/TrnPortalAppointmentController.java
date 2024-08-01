package com.cellbeans.hspa.trnappointment;

import com.cellbeans.hspa.TenantContext;

import com.cellbeans.hspa.mstpatient.MstPatientRepository;
import com.cellbeans.hspa.smsutility.Sendsms;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RestController
@RequestMapping("/trn_portal_appointment")
public class TrnPortalAppointmentController {

    @Autowired
    TrnAppointmentPortalRepository trnAppointmentPortalRepository;

    @Autowired
    TrnAppointmentPortalUploadRepository trnAppointmentPortalUploadRepository;

    @Autowired
    MstPatientRepository mstPatientRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    Sendsms sendsms;

    @Autowired
    private com.cellbeans.hspa.applicationproperty.Propertyconfig Propertyconfig;

    @GetMapping
    @RequestMapping("portallist")
    public List<TrnPortalAppointment> portallist(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                 @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                                 @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                 @RequestParam(value = "dateFrom") String dateFrom,
                                                 @RequestParam(value = "dateTo") String dateTo,
                                                 @RequestParam(value = "marno") String marno,
                                                 @RequestParam(value = "mobileno") String mobileno,
                                                 @RequestParam(value = "adharno") String adharno,
                                                 @RequestParam(value = "aptstatus") String aptstatus,
                                                 @RequestParam(value = "col", required = false, defaultValue = "appointmentId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        List<TrnPortalAppointment> portalAppointmentList = null;
        try {
            String query = "SELECT a From TrnPortalAppointment a where a.isActive=1 and a.isDeleted=0 ";
            if (dateFrom != null && dateTo != null) {
                query += " and date(a.appointmentAppointmentDate) between " + "'" + dateFrom + "' AND  " + "'" + dateTo + "'";
            } else {
                query += " and date(a.appointmentAppointmentDate) between " + "'" + dateFrom + "' AND  " + "'" + dateTo + "'";
            }
            if (aptstatus.equals("1")) {
                query += " and a.appointmentIsConfirmed=1 ";
            } else if (aptstatus.equals("2")) {
                query += " and a.appointmentIsCancelled=1 ";
            } else {
                query += " and a.appointmentIsConfirmed=0 ";
            }
            if (marno != null && !marno.equals("")) {
                query += " and a.appointmentUserMARNo like '%" + marno + "%'";
            }
            if (mobileno != null && !mobileno.equals("")) {
                query += " and a.appointmentMobile like '%" + mobileno + "%'";
            }
            if (adharno != null && !adharno.equals("")) {
                query += " and a.appointmentUserUid like '%" + adharno + "%'";
            }
            query += " order by a.appointmentPortalId desc ";
            // query +=" limit " + (Integer.parseInt(page) - 1) * Integer.parseInt(size) + "," + Integer.parseInt(size);
            System.out.println("query: " + query);
            portalAppointmentList = (List<TrnPortalAppointment>) entityManager.createQuery(query).getResultList();
            // return trnAppointmentPortalRepository.findAllIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } catch (Exception e) {
        }
        return portalAppointmentList;
    }

    @RequestMapping("create")
    public String createAppointment(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnPortalAppointment trnPortalAppointment) {
        TenantContext.setCurrentTenant(tenantName);
        JSONObject object = new JSONObject();
        if (trnPortalAppointment != null) {
            if (trnPortalAppointment.getAppointmentPortalId() != 0L) {
                trnPortalAppointment.setAppointmentIsConfirmed(true);
                TrnPortalAppointment newTrnAppointmentPortal = trnAppointmentPortalRepository.save(trnPortalAppointment);
                if (Propertyconfig.getSmsApi()) {
                    if (newTrnAppointmentPortal.getAppointmentMobile() != null) {
                        sendsms.sendMessage(newTrnAppointmentPortal.getAppointmentMobile(), "Your%20appointment%20has%20been%20confirmed.");
                        // emailsend.sendSimpleMessage("abhijeetborse90@gmail.com","WELCOME to HSPA","You are Register Successfully MRNO is "+ newMrNo);
                    }
                }
                object.put("appointmentPortalId", newTrnAppointmentPortal.getAppointmentPortalId());
                return object.toString();
            }
            //trnAppointmentPortalRepository.save(trnPortalAppointment);
        }
        object.put("appointment failed to created", 0);
        return object.toString();
    }

    @RequestMapping("byid/{portalappointmentId}")
    public TrnPortalAppointment read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("portalappointmentId") Long appointmentId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnPortalAppointment trnPortalAppointment = trnAppointmentPortalRepository.getById(appointmentId);
        return trnPortalAppointment;
    }

    @RequestMapping("update")
    public String updateAppointment(@RequestHeader("X-tenantId") String tenantName, @RequestParam(name = "portalappointmentId") Long portalappointmentId, @RequestParam(name = "portalpatientId") Long portalpatientId) {
        TenantContext.setCurrentTenant(tenantName);
        List<TrnPortalUploadDocs> trnPortalAppointmentDoc = trnAppointmentPortalUploadRepository.findAllByPudAppointmentIdEqualsAndIsActiveTrueAndIsDeletedFalse(String.valueOf(portalappointmentId));
        if (trnPortalAppointmentDoc != null) {
            for (TrnPortalUploadDocs obj : trnPortalAppointmentDoc) {
                obj.setAppointmentPatientId(mstPatientRepository.getById(portalpatientId));
                trnAppointmentPortalUploadRepository.save(obj);
            }
            return "appointment doc updated";
        }
        return "appointment failed updated";
    }

    @RequestMapping("cancel")
    public String cancelAppointment(@RequestHeader("X-tenantId") String tenantName, @RequestParam("appointmentPortalId") Long appointmentPortalId, @RequestParam("appointmentCancelReason") String appointmentCancelReason) {
        TenantContext.setCurrentTenant(tenantName);
        JSONObject object = new JSONObject();
        if (appointmentPortalId != null) {
            if (appointmentPortalId != 0L) {
                TrnPortalAppointment newTrnAppointmentPortal = trnAppointmentPortalRepository.getById(appointmentPortalId);
                newTrnAppointmentPortal.setAppointmentCancelReason(appointmentCancelReason);
                newTrnAppointmentPortal.setAppointmentIsCancelled(true);
                TrnPortalAppointment updatedobj = trnAppointmentPortalRepository.save(newTrnAppointmentPortal);
                if (Propertyconfig.getSmsApi()) {
                    if (updatedobj.getAppointmentMobile() != null) {
                        sendsms.sendMessage(newTrnAppointmentPortal.getAppointmentMobile(), "Your%20appointment%20has%20been%20rejected%20because%20" + updatedobj.getAppointmentCancelReason());
                        // emailsend.sendSimpleMessage("abhijeetborse90@gmail.com","WELCOME to HSPA","You are Register Successfully MRNO is "+ newMrNo);
                    }
                }
                object.put("success", "1");
                return object.toString();
            }
        }
        object.put("portal appointment failed to created", 0);
        return object.toString();
    }

}
