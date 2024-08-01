package com.cellbeans.hspa.temrreferralhistory;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.trnappointment.TrnAppointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/temr_referral_history")
public class TemrReferralHistoryController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TemrReferralHistoryRepository temrReferralHistoryRepository;

    @Autowired
    CreateJSONObject createJSONObject;

    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrReferralHistory temrReferralHistory) {
        TenantContext.setCurrentTenant(tenantName);
        if (temrReferralHistory != null) {
            temrReferralHistoryRepository.save(temrReferralHistory);
            respMap.put("success", "1");
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
        List<TemrReferralHistory> records;
        records = temrReferralHistoryRepository.findByRhVisitIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{rhId}")
    public TemrReferralHistory read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("rhId") Long rhId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrReferralHistory temrReferralHistory = temrReferralHistoryRepository.getById(rhId);
        return temrReferralHistory;
    }

    @RequestMapping("CheckAppointment/{rhId}")
    public boolean CheckAppointment(@RequestHeader("X-tenantId") String tenantName, @PathVariable("rhId") Long rhId) {
        TenantContext.setCurrentTenant(tenantName);
        String str = " SELECT ta.* FROM trn_appointment ta " +
                "WHERE ta.refer_history_id = " + rhId + " AND ta.is_referral_cancelled = FALSE AND ta.appointment_is_cancelled = false ";
        List<TrnAppointment> trnAppointmentList = entityManager.createNativeQuery(str, TrnAppointment.class).getResultList();
        if (trnAppointmentList.size() > 0) {
            return true;
        } else {
            return false;
        }

    }

    @RequestMapping("bypid/{pid}")
    public Iterable<TemrReferralHistory> readByPid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pid") Long pid) {
        TenantContext.setCurrentTenant(tenantName);
        return temrReferralHistoryRepository.findByRhPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(pid);
//        return temrReferralHistory;
    }

    @RequestMapping("closeReferralbyrhid/{rhid}")
    public boolean closeReferralStatus(@RequestHeader("X-tenantId") String tenantName, @PathVariable("rhid") Long rhid) {
        TenantContext.setCurrentTenant(tenantName);
        TemrReferralHistory temrReferralHistory = temrReferralHistoryRepository.getById(rhid);
        temrReferralHistory.setRhStatus(false);
        temrReferralHistoryRepository.save(temrReferralHistory);
        return true;
    }

    @RequestMapping("update")
    public TemrReferralHistory update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrReferralHistory temrReferralHistory) {
        TenantContext.setCurrentTenant(tenantName);
        return temrReferralHistoryRepository.save(temrReferralHistory);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TemrReferralHistory> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) Long visitId, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "rhId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (visitId == null || visitId.equals("")) {
            return temrReferralHistoryRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return temrReferralHistoryRepository.findByRhVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(visitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @GetMapping
    @RequestMapping("listBy")
    public Iterable<TemrReferralHistory> listByExternal(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) Long visitId, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "rhId") String col, @RequestParam(value = "external") boolean external) {
        TenantContext.setCurrentTenant(tenantName);
        if (visitId == null || visitId.equals("")) {
            return temrReferralHistoryRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            if (external == true) {
                return temrReferralHistoryRepository.findByRhVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalseAndRhIsExTrue(visitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            } else {
                return temrReferralHistoryRepository.findByRhVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalseAndRhIsExFalse(visitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

            }

        }

    }

    @PutMapping("delete/{rhId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("rhId") Long rhId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrReferralHistory temrReferralHistory = temrReferralHistoryRepository.getById(rhId);
        if (temrReferralHistory != null) {
            temrReferralHistory.setDeleted(true);
            temrReferralHistoryRepository.save(temrReferralHistory);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getlistofinboundreferrals")
    public ResponseEntity searchListofReferrals(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrReferralSearch temrReferralSearch) {
        TenantContext.setCurrentTenant(tenantName);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        if (temrReferralSearch.getFromdate().equals("") || temrReferralSearch.getFromdate().equals("null")) {
            temrReferralSearch.setFromdate(strDate);
        }
        if (temrReferralSearch.getTodate().equals("") || temrReferralSearch.getTodate().equals("null")) {
//            todate = strDate;
            temrReferralSearch.setTodate(strDate);
        }
        String Query = " SELECT trh.created_date AS rhDate, CASE WHEN trh.rh_is_ex=1 THEN 'External' ELSE 'Internal' END AS rhType, " +
                " IFNULL(CONCAT(mt.title_name, ' ',mus.user_firstname,' ',mus.user_lastname),'') AS patientName, " +
                " ifnull(mp.patient_id,'') AS patientId, mu.unit_name AS referFromUnit, ifnull(md.department_name,'') AS rhDepartment, ifnull(msd.sd_name,'') AS rhSubDept, " +
                " IFNULL(CONCAT(smus.user_firstname,' ',smus.user_lastname),'') AS drName, ifnull(trh.rh_subject,'') AS rhReason,  " +
                " CASE WHEN trh.rh_status = 1 THEN 'Open' ELSE 'close' END AS rhStatus, " +
                " ifnull(trh.rh_timeline_id,'') AS rhTimelineId , mp.patient_user_id AS patientUserId, trh.rh_id AS rhId, trh.rh_visit_id AS rhVisitId, ifnull(mp.patient_mr_no,'') AS patientMrNo, CASE WHEN ta.appointment_id  IS null THEN 'false' ELSE 'true' END AS isappointment, " +
                " CASE WHEN ta.appointment_is_cancelled THEN 'true' ELSE 'false' END AS isCancelled, CASE WHEN ta.is_referral_cancelled THEN 'true' ELSE 'false' END AS is_referral_cancelled, CASE WHEN ta.is_referral_rescheduled THEN 'true' ELSE 'false' END AS is_referral_rescheduled,ta.appointment_id " +
                "FROM temr_referral_history trh " +
                "LEFT JOIN mst_patient mp ON mp.patient_id = trh.rh_patient_id " +
                "LEFT JOIN mst_user mus ON mus.user_id = mp.patient_user_id " +
                "Left JOIN mst_title mt ON mt.title_id = mus.user_title_id " +
                "LEFT JOIN mst_department md ON md.department_id = trh.rh_department_id " +
                "LEFT JOIN mst_sub_department msd ON msd.sd_id = trh.rh_sd_id " +
                "LEFT JOIN mst_staff ms ON ms.staff_id = trh.rh_staff_id " +                         // refer by dr or referr ltr created by dr(rh_staff_id)
                "LEFT JOIN mst_user smus ON smus.user_id = ms.staff_user_id" +
                " LEFT JOIN mst_unit mu ON mu.unit_id = trh.rh_by_unit_id LEFT JOIN trn_appointment ta ON ta.refer_history_id = trh.rh_id " +
                " WHERE trh.rh_to_unit_id =" + temrReferralSearch.getUnitId() + " AND trh.is_active = 1 AND trh.is_deleted = 0  AND (ta.appointment_id IS NULL OR (ta.appointment_id IS NOT NULL)) ";

        //        in where condition bracket condition is hide by Rohan and Gayatri (AND ta.appointment_is_closed = false) while checkking referal status show (close) work flow 16.09.2021
//        " WHERE trh.rh_by_unit_id =" + temrReferralSearch.getUnitId() + " AND trh.is_active = 1 AND trh.is_deleted = 0 AND (ta.appointment_id IS NULL OR (ta.appointment_id IS NOT NULL AND ta.appointment_is_closed = false)) ";

        String CountQuery = "";
        if (temrReferralSearch.getStaffType() == 0) {
            Query += " and (trh.rh_doctor_id =" + temrReferralSearch.getStaffId();
            Query += " or trh.rh_department_id in (SELECT mssd.staff_department_id_department_id FROM mst_staff_staff_department_id mssd WHERE mssd.mst_staff_staff_id=" + temrReferralSearch.getStaffId() + ")";
            Query += " or trh.rh_sd_id in (SELECT ssd.staff_sd_id_sd_id FROM mst_staff_staff_sd_id ssd WHERE ssd.mst_staff_staff_id=" + temrReferralSearch.getStaffId() + "))";
        }
        if (temrReferralSearch.getPatientMrNo() != null && !temrReferralSearch.getPatientMrNo().equals("")) {
            Query += " and  mp.patient_mr_no like  '%" + temrReferralSearch.getPatientMrNo() + "%' ";
        }
        if (!temrReferralSearch.getUserFirstname().equals("") && temrReferralSearch.getUserFirstname() != null) {
            Query += " and (mus.user_firstname like '%" + temrReferralSearch.getUserFirstname() + "%' or mus.user_lastname like '%" + temrReferralSearch.getUserFirstname() + "%') ";
        }
        if (temrReferralSearch.getUserMobile() != null && !temrReferralSearch.getUserMobile().equals("")) {
            Query += " and mu.user_mobile like '%" + temrReferralSearch.getUserMobile() + "%'  ";
        }
        Query += " and (date(trh.created_date) between '" + temrReferralSearch.getFromdate() + "' and '" + temrReferralSearch.getTodate() + "')  ";
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        String columnName = "rhDate,rhType,patientName,patientId,referFromUnit,rhDepartment,rhSubDept,drName,rhReason,rhStatus,rhTimelineId,patientUserId,rhId,rhVisitId,patientMrNo,isappointment,isCancelled,is_referral_cancelled,is_referral_rescheduled,appointment_id";
        Query += " limit " + ((temrReferralSearch.getOffset() - 1) * temrReferralSearch.getLimit()) + "," + temrReferralSearch.getLimit();
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
    }

    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getlistofoutboundreferrals")
    public ResponseEntity searchListofOutBoundReferrals(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrReferralSearch temrReferralSearch) {
        TenantContext.setCurrentTenant(tenantName);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        if (temrReferralSearch.getFromdate().equals("") || temrReferralSearch.getFromdate().equals("null")) {
            temrReferralSearch.setFromdate(strDate);
        }
        if (temrReferralSearch.getTodate().equals("") || temrReferralSearch.getTodate().equals("null")) {
//            todate = strDate;
            temrReferralSearch.setTodate(strDate);
        }
        String Query = " SELECT trh.created_date AS rhDate, CASE WHEN trh.rh_is_ex=1 THEN 'External' ELSE 'Internal' END AS rhType, " +
                " IFNULL(CONCAT(mt.title_name, ' ',mus.user_firstname,' ',mus.user_lastname),'') AS patientName, " +
                " ifnull(mp.patient_id,'') AS patientId, mu.unit_name AS referFromUnit, ifnull(md.department_name,'') AS rhDepartment, ifnull(msd.sd_name,'') AS rhSubDept, " +
                " IFNULL(CONCAT(smus.user_firstname,' ',smus.user_lastname),'') AS drName, ifnull(trh.rh_subject,'') AS rhReason,  " +
                " CASE WHEN trh.rh_status = 1 THEN 'Open' ELSE 'close' END AS rhStatus, " +
                " ifnull(trh.rh_timeline_id,'') AS rhTimelineId , mp.patient_user_id AS patientUserId, trh.rh_id AS rhId, trh.rh_visit_id AS rhVisitId, ifnull(mp.patient_mr_no,'') AS patientMrNo,CASE WHEN ta.appointment_id  IS null THEN 'false' ELSE 'true' END AS isappointment, " +
                " CASE WHEN ta.appointment_is_cancelled THEN 'true' ELSE 'false' END AS isCancelled, CASE WHEN ta.is_referral_cancelled THEN 'true' ELSE 'false' END AS is_referral_cancelled, CASE WHEN ta.is_referral_rescheduled THEN 'true' ELSE 'false' END AS is_referral_rescheduled,ta.appointment_id  " +
                "FROM temr_referral_history trh " +
                "LEFT JOIN mst_patient mp ON mp.patient_id = trh.rh_patient_id " +
                "LEFT JOIN mst_user mus ON mus.user_id = mp.patient_user_id " +
                "Left JOIN mst_title mt ON mt.title_id = mus.user_title_id " +
                "LEFT JOIN mst_department md ON md.department_id = trh.rh_department_id " +
                "LEFT JOIN mst_sub_department msd ON msd.sd_id = trh.rh_sd_id " +
                "LEFT JOIN mst_staff ms ON ms.staff_id = trh.rh_doctor_id " +  // refer to Dr (rh_doctor_id)
                "LEFT JOIN mst_user smus ON smus.user_id = ms.staff_user_id" +
                " LEFT JOIN mst_unit mu ON mu.unit_id = trh.rh_by_unit_id LEFT JOIN trn_appointment ta ON ta.refer_history_id = trh.rh_id " +
                " WHERE trh.rh_by_unit_id =" + temrReferralSearch.getUnitId() + " AND trh.is_active = 1 AND trh.is_deleted = 0 AND (ta.appointment_id IS NULL OR (ta.appointment_id IS NOT NULL)) ";
//        in where condition bracket condition is hide by Rohan and Gayatri (AND ta.appointment_is_closed = false) while checkking referal status show (close) work flow 16.09.2021
//        " WHERE trh.rh_by_unit_id =" + temrReferralSearch.getUnitId() + " AND trh.is_active = 1 AND trh.is_deleted = 0 AND (ta.appointment_id IS NULL OR (ta.appointment_id IS NOT NULL AND ta.appointment_is_closed = false)) ";
        String CountQuery = "";
        if (temrReferralSearch.getStaffType() == 0) {
            Query += " and trh.rh_staff_id =" + temrReferralSearch.getStaffId();
        }
        if (temrReferralSearch.getPatientMrNo() != null && !temrReferralSearch.getPatientMrNo().equals("")) {
            Query += " and  mp.patient_mr_no like  '%" + temrReferralSearch.getPatientMrNo() + "%' ";
        }
        if (!temrReferralSearch.getUserFirstname().equals("") && temrReferralSearch.getUserFirstname() != null) {
            Query += " and (mus.user_firstname like '%" + temrReferralSearch.getUserFirstname() + "%' or mus.user_lastname like '%" + temrReferralSearch.getUserFirstname() + "%') ";
        }
        if (temrReferralSearch.getUserMobile() != null && !temrReferralSearch.getUserMobile().equals("")) {
            Query += " and mu.user_mobile like '%" + temrReferralSearch.getUserMobile() + "%'  ";
        }
        Query += " and (date(trh.created_date) between '" + temrReferralSearch.getFromdate() + "' and '" + temrReferralSearch.getTodate() + "')  ";
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        String columnName = "rhDate,rhType,patientName,patientId,referFromUnit,rhDepartment,rhSubDept,drName,rhReason,rhStatus,rhTimelineId,patientUserId,rhId,rhVisitId,patientMrNo,isappointment,isCancelled,is_referral_cancelled,is_referral_rescheduled,appointment_id";
        Query += " limit " + ((temrReferralSearch.getOffset() - 1) * temrReferralSearch.getLimit()) + "," + temrReferralSearch.getLimit();
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));
    }

}
            
