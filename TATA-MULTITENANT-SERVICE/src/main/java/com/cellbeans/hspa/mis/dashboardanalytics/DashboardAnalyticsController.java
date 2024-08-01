package com.cellbeans.hspa.mis.dashboardanalytics;

import com.cellbeans.hspa.TenantContext;
import org.json.JSONException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dashboard_analytics")
public class DashboardAnalyticsController {

    @PersistenceContext
    EntityManager entityManager;

    @RequestMapping("getOpdAllUnitCount/{fromDate}/{toDate}/{unitId}/{todayDate}")
    public Map<String, Long> getPatientVisitedCount(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate, @PathVariable("unitId") int unitId, @PathVariable("todayDate") boolean todayDate) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Long> respMap = new HashMap<String, Long>();
        BigInteger newAppoint_countquery, patientVis_countquery, follupAppoint_countquery, NewRegistration_countquery, FollowupVisit_countquery;
        String patientViscountquery = " SELECT count(v.visit_id) FROM mst_visit v where v.visit_registration_source = 0 ";
        String newAppointcountquery = " SELECT count(a.appointment_id) FROM trn_appointment a " + " left join mst_appointment_reason r on a.appointment_ar_id = r.ar_id " + " where r.ar_id=3 ";
        String follupAppointcountquery = " SELECT count(a.appointment_id) FROM trn_appointment a " + " left join mst_appointment_reason r on a.appointment_ar_id = r.ar_id " + " where r.ar_id=2 ";
        String NewRegistrationcountquery = " SELECT count(visit_id) FROM mst_visit v " + " left join mst_unit u on v.visit_unit_id=u.unit_id " + " where reason_visit='First Visit' ";
        String FollowupVisitcountquery = " SELECT count(visit_id) FROM mst_visit v " + " left join mst_unit u on v.visit_unit_id=u.unit_id " + " where reason_visit='Follow Up' ";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        if (fromDate.equals("") || fromDate.equals("null")) {
            fromDate = "1990-06-07";
        }
        if (toDate.equals("") || toDate.equals("null")) {
            toDate = strDate;
        }
        if (todayDate) {
            patientViscountquery += " and date(v.visit_date)= CURDATE()";
            newAppointcountquery += " and date(a.appointment_date)= CURDATE()";
            follupAppointcountquery += " and date(a.appointment_date)= CURDATE()";
            NewRegistrationcountquery += " and date(v.visit_date)= CURDATE()";
            FollowupVisitcountquery += " and date(v.visit_date)= CURDATE()";

        } else {
            patientViscountquery += " and (date(v.visit_date) between '" + fromDate + "' and '" + toDate + "') ";
            newAppointcountquery += " and date(a.appointment_date) between '" + fromDate + "' and '" + toDate + "') ";
            follupAppointcountquery += " and date(a.appointment_date) between '" + fromDate + "' and '" + toDate + "') ";
            NewRegistrationcountquery += " and date(v.visit_date) between '" + fromDate + "' and '" + toDate + "') ";
            FollowupVisitcountquery += " and date(v.visit_date) between '" + fromDate + "' and '" + toDate + "') ";

        }
        if (unitId != 0) {
            patientViscountquery += " and v.visit_unit_id=" + unitId + " ";
            newAppointcountquery += " and a.appointment_unit_id =" + unitId + " ";
            follupAppointcountquery += " and a.appointment_unit_id =" + unitId + " ";
            NewRegistrationcountquery += " and v.visit_unit_id =" + unitId;
            FollowupVisitcountquery += " and v.visit_unit_id =" + unitId;
        }
        newAppoint_countquery = (BigInteger) entityManager.createNativeQuery(newAppointcountquery).getSingleResult();
        patientVis_countquery = (BigInteger) entityManager.createNativeQuery(patientViscountquery).getSingleResult();
        follupAppoint_countquery = (BigInteger) entityManager.createNativeQuery(follupAppointcountquery).getSingleResult();
        NewRegistration_countquery = (BigInteger) entityManager.createNativeQuery(NewRegistrationcountquery).getSingleResult();
        FollowupVisit_countquery = (BigInteger) entityManager.createNativeQuery(FollowupVisitcountquery).getSingleResult();
        respMap.put("NewAppointmentcountquery", newAppoint_countquery.longValue());
        respMap.put("PatientVisitcountquery", patientVis_countquery.longValue());
        respMap.put("FollowupAppointmentcountquery", follupAppoint_countquery.longValue());
        respMap.put("NewRegistrationcountquery", NewRegistration_countquery.longValue());
        respMap.put("FollowupVisitcountquery", FollowupVisit_countquery.longValue());
        return respMap;
    }

    @RequestMapping("getEmrAllUnitCount/{fromDate}/{toDate}/{unitId}/{todayDate}")
    public Map<String, Long> getEmrCount(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate, @PathVariable("unitId") int unitId, @PathVariable("todayDate") boolean todayDate) throws JSONException {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Long> respMap = new HashMap<String, Long>();
        BigInteger emrTimelineGenerated_countquery, finalizedEmr_countquery, doctorsOrderService_countquery, prescriptionGenerated_countquery;
        String emrTimelineGeneratedcountquery = " SELECT count(t.timeline_id) FROM temr_timeline t " + " left join mst_visit v on t.timeline_visit_id=v.visit_id " + " left join mst_unit u on v.visit_unit_id=u.unit_id " + " where timeline_id ";
        String finalizedEmrcountquery = " SELECT count(t.timeline_id) FROM temr_timeline t " + " left join mst_visit v on t.timeline_visit_id=v.visit_id " + " left join mst_unit u on v.visit_unit_id=u.unit_id " + " where isemrfinal=1";
        String doctorsOrderServicecountquery = " SELECT count(vi_id) FROM temr_visit_investigation i " + " left join mst_visit v on i.vi_visit_id=v.visit_id " + " left join mst_unit u on v.visit_unit_id=u.unit_id ";
        String prescriptionGeneratedcountquery = " SELECT count(vp_id) FROM temr_visit_prescription p " + " left join mst_visit v on p.vp_visit_id=v.visit_id " + " left join mst_unit u on v.visit_unit_id=u.unit_id ";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        if (fromDate.equals("") || fromDate.equals("null")) {
            fromDate = "1990-06-07";
        }
        if (toDate.equals("") || toDate.equals("null")) {
            toDate = strDate;
        }
        if (todayDate) {
            emrTimelineGeneratedcountquery += " and date(t.timeline_date)= CURDATE() ";
            finalizedEmrcountquery += " and date(t.timeline_date)= CURDATE() ";
            doctorsOrderServicecountquery += " and date(i.vi_created_date)= CURDATE() ";
            prescriptionGeneratedcountquery += " and date(p.created_date)= CURDATE() ";

        } else {
            emrTimelineGeneratedcountquery += " and (date(t.timeline_date) between '" + fromDate + "' and '" + toDate + "') ";
            finalizedEmrcountquery += " and date(t.timeline_date) between '" + fromDate + "' and '" + toDate + "') ";
            doctorsOrderServicecountquery += " and date(i.vi_created_date) between '" + fromDate + "' and '" + toDate + "') ";
            prescriptionGeneratedcountquery += " and date(p.created_date) between '" + fromDate + "' and '" + toDate + "') ";

        }
        if (unitId != 0) {
            emrTimelineGeneratedcountquery += " and v.visit_unit_id =" + unitId + " ";
            finalizedEmrcountquery += " and v.visit_unit_id =" + unitId + " ";
            doctorsOrderServicecountquery += " and v.visit_unit_id =" + unitId + " ";
            prescriptionGeneratedcountquery += " and v.visit_unit_id =" + unitId + " ";
        }
        emrTimelineGenerated_countquery = (BigInteger) entityManager.createNativeQuery(emrTimelineGeneratedcountquery).getSingleResult();
        finalizedEmr_countquery = (BigInteger) entityManager.createNativeQuery(finalizedEmrcountquery).getSingleResult();
        doctorsOrderService_countquery = (BigInteger) entityManager.createNativeQuery(doctorsOrderServicecountquery).getSingleResult();
        prescriptionGenerated_countquery = (BigInteger) entityManager.createNativeQuery(prescriptionGeneratedcountquery).getSingleResult();
        respMap.put("EmrTimelineGeneratedcountquery", emrTimelineGenerated_countquery.longValue());
        respMap.put("FinalizedEmrcountquery", finalizedEmr_countquery.longValue());
        respMap.put("DoctorsOrderServicecountquery", doctorsOrderService_countquery.longValue());
        respMap.put("PrescriptionGeneratedcountquery", prescriptionGenerated_countquery.longValue());
        return respMap;

    }

}
