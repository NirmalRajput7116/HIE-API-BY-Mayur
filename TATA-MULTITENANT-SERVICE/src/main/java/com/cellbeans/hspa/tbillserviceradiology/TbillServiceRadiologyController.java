package com.cellbeans.hspa.tbillserviceradiology;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tbill_service_radiology")
public class TbillServiceRadiologyController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TbillServiceRadiologyRepository tbillServiceRadiologyRepository;

    @PersistenceContext
    EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillServiceRadiology tbillServiceRadiology) {
        TenantContext.setCurrentTenant(tenantName);
        if (tbillServiceRadiology.getBsrBsId() != null) {
            tbillServiceRadiologyRepository.save(tbillServiceRadiology);
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
        List<TbillServiceRadiology> records;
        records = tbillServiceRadiologyRepository.findByBsrBsIdBsBillIdBillNumberContainsAndIsDeletedFalseAndIsActiveTrue(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{bsrId}")
    public TbillServiceRadiology read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bsrId") Long bsrId) {
        TenantContext.setCurrentTenant(tenantName);
        TbillServiceRadiology tbillServiceRadiology = tbillServiceRadiologyRepository.getById(bsrId);
        return tbillServiceRadiology;
    }
//	@RequestMapping("update")
//	public TbillServiceRadiology update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillServiceRadiology tbillServiceRadiology) {
//		return tbillServiceRadiologyRepository.save(tbillServiceRadiology);
//	}

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillServiceRadiology tbillServiceRadiology) {
        TenantContext.setCurrentTenant(tenantName);
        tbillServiceRadiologyRepository.save(tbillServiceRadiology);
        respMap.put("msg", "Operation Successful");
        respMap.put("success", "1");
        return respMap;
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TbillServiceRadiology> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsrId") String col) throws Exception {
        TenantContext.setCurrentTenant(tenantName);
        Date todaysdate;
        LocalDate today = LocalDate.now();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        todaysdate = df.parse(df.format(today));
        if (qString == null || qString.equals("")) {
            return tbillServiceRadiologyRepository.findAllByBsrIsCancelFalseAndBsrIsScanCompletedFalseAndIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)), todaysdate);
        } else {
            return tbillServiceRadiologyRepository.findByBsrBsIdBsBillIdBillNumberContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{bsrId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bsrId") Long bsrId) {
        TenantContext.setCurrentTenant(tenantName);
        TbillServiceRadiology tbillServiceRadiology = tbillServiceRadiologyRepository.getById(bsrId);
        if (tbillServiceRadiology != null) {
            tbillServiceRadiology.setIsDeleted(true);
            tbillServiceRadiologyRepository.save(tbillServiceRadiology);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("byttbillid/{id}")
    public Iterable<TbillServiceRadiology> getservicebyttbillid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillServiceRadiologyRepository.findByBsrBsIdBsBillIdBillIdEqualsAndIsActiveTrueAndIsDeletedFalse(id);
    }

    @RequestMapping("bypatientid")
    public Iterable<TbillServiceRadiology> getservicebyPatientid(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsrId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        String Query = "  (   " +
                "SELECT bsr.*   " +
                "FROM tbill_service_radiology bsr   " +
                "LEFT JOIN tbill_bill_service tbbs ON tbbs.bs_id = bsr.bsr_bs_id   " +
                "LEFT JOIN tbill_bill tb ON tb.bill_id = tbbs.bs_bill_id   " +
                "LEFT JOIN mst_unit mu ON mu.unit_id = tb.tbill_unit_id   " +
                "LEFT JOIN ipd_charges_service cs ON cs.cs_id = bsr.bsr_cs_id   " +
                "LEFT JOIN trn_admission ta ON ta.admission_id = tb.bill_admission_id   " +
                "LEFT JOIN mst_patient mp ON mp.patient_id = ta.admission_patient_id   " +
                "LEFT JOIN mst_user muser ON muser.user_id = mp.patient_user_id   " +
                "WHERE bsr.ipd_bill = TRUE AND bsr.bsr_cs_id IS NULL and mp.patient_id = " + qString + ")   " +
                " UNION ALL (   " +
                "SELECT bsr.*   " +
                "FROM tbill_service_radiology bsr   " +
                "LEFT JOIN tbill_bill_service tbbs ON tbbs.bs_id = bsr.bsr_bs_id   " +
                "LEFT JOIN tbill_bill tb ON tb.bill_id = tbbs.bs_bill_id   " +
                "LEFT JOIN mst_unit mu ON mu.unit_id = tb.tbill_unit_id   " +
                "LEFT JOIN ipd_charges_service cs ON cs.cs_id = bsr.bsr_cs_id   " +
                "LEFT JOIN mbill_ipd_charge ic ON ic.ipdcharge_id = cs.cs_charge_id   " +
                "LEFT JOIN trn_admission ta ON ta.admission_id = ic.charge_admission_id   " +
                "LEFT JOIN mst_patient mp ON mp.patient_id = ta.admission_patient_id   " +
                "LEFT JOIN mst_user muser ON muser.user_id = mp.patient_user_id   " +
                "WHERE bsr.ipd_bill = TRUE AND bsr.bsr_cs_id IS NOT NULL and mp.patient_id = " + qString + ")    ";
        String CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        Query += "  limit " + ((Integer.parseInt(page) - 1) * Integer.parseInt(size)) + "," + Integer.parseInt(size);
        List<TbillServiceRadiology> allList = entityManager.createNativeQuery(Query, TbillServiceRadiology.class).getResultList();
//            return tbillServiceRadiologyRepository.findAllByBsrCsIdCsDateBetweenAndBsrCsIdCsChargeIdTbillUnitIdUnitIdAndBsrIpdBillTrue(StartDate, EndDate, unitid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        BigInteger count = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
        if (allList.size() > 0) {
            allList.get(0).setCount(count.longValue());
        }
        return allList;
//        return tbillServiceRadiologyRepository.findByBsrBsIdBsBillIdBillAdmissionIdAdmissionPatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(id);
    }

    @GetMapping
    @RequestMapping("updateservicestatus/{reportId}/{accessionId}")
    public Map<String, String> updateservicestatus(@RequestHeader("X-tenantId") String tenantName, @PathVariable("reportId") int reportId, @PathVariable("accessionId") String accessionId) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("===report finalized==");
        System.out.println(reportId);
        // System.out.println(patientId);
//        String[] test = patientId.split("_");
//        String billServiceId = test[1];
        TbillServiceRadiology tbillServiceRadiology = tbillServiceRadiologyRepository.findByBsrIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(accessionId));
        if (tbillServiceRadiology != null) {
            tbillServiceRadiology.setBsrIsReported(true);
            tbillServiceRadiology.setBsrReportId(reportId);
            tbillServiceRadiologyRepository.save(tbillServiceRadiology);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("getallradiologyservices")
    public Iterable<TbillServiceRadiology> getallradiologyservices(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsrId") String col, @RequestParam(value = "startdate", required = false) String startdate, @RequestParam(value = "enddate", required = false) String enddate) throws ParseException {
        TenantContext.setCurrentTenant(tenantName);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date StartDate = df.parse(startdate);
        Date EndDate = df.parse(enddate);
        if (qString == null || qString.equals("")) {
            return tbillServiceRadiologyRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tbillServiceRadiologyRepository.findAllByBsrBsIdBsBillIdTbillUnitIdUnitIdAndCreatedDateBetweenAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString), StartDate, EndDate, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @GetMapping
    @RequestMapping("findPreviousPatient")
    public TbillServiceRadiology findPreviousPatient(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillServiceRadiologyRepository.findByBsrIsCallTrueAndBsrIsScanCompletedFalseAndIsActiveTrueAndIsDeletedFalse();

    }

    @GetMapping
    @RequestMapping("orderlistbyreporttype")
    public Iterable<TbillServiceRadiology> orderlistbyreporttype(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsrId") String col, @RequestParam(value = "reporttype", required = false) String reporttype) {
        TenantContext.setCurrentTenant(tenantName);
        if (reporttype == null || reporttype.equals("")) {
            return tbillServiceRadiologyRepository.findAllByBsrBsIdBsBillIdTbillUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            Boolean reportstatus;
            // System.out.println("===report status==");
            // System.out.println(reporttype);
            if (!reporttype.equals("true")) {
                reportstatus = false;
                // System.out.println(reportstatus);
            } else {
                reportstatus = true;
                // System.out.println(reportstatus);
            }
            return tbillServiceRadiologyRepository.findByBsrBsIdBsBillIdTbillUnitIdUnitIdAndBsrIsReportedEqualsAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString), reportstatus, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }
    }

    @GetMapping
    @RequestMapping("getcancelledservicelist")
    public Iterable<TbillServiceRadiology> getcancelledservicelist(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsrId") String col, @RequestParam(value = "cancelstatus", required = false) String cancelstatus) {
        TenantContext.setCurrentTenant(tenantName);
        if (cancelstatus == null || cancelstatus.equals("")) {
            return tbillServiceRadiologyRepository.findAllByBsrBsIdBsBillIdTbillUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            Boolean cancelStatus;
            // System.out.println("===report status==");
            // System.out.println(cancelstatus);
            if (!cancelstatus.equals("true")) {
                cancelStatus = false;
                // System.out.println(cancelStatus);
            } else {
                cancelStatus = true;
                // System.out.println(cancelStatus);
            }
            return tbillServiceRadiologyRepository.findByBsrBsIdBsBillIdTbillUnitIdUnitIdAndBsrIsCancelEqualsAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString), cancelStatus, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }
    }

    @GetMapping
    @RequestMapping("getservicelistbysearch")
    public Iterable<TbillServiceRadiology> getservicelistbysearch(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsrId") String col, @RequestParam(value = "allsearch", required = false) String allsearch) {
        TenantContext.setCurrentTenant(tenantName);
        if (allsearch == null || allsearch.equals("")) {
            return tbillServiceRadiologyRepository.findAllByBsrBsIdBsBillIdTbillUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            // System.out.println("===report status==");
            return tbillServiceRadiologyRepository.findByBsrBsIdBsBillIdTbillUnitIdUnitIdAndBsrBsIdBsBillIdBillVisitIdVisitPatientIdPatientMrNoContainsOrBsrBsIdBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserFirstnameContainsOrBsrBsIdBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserMiddlenameContainsOrBsrBsIdBsBillIdBillVisitIdVisitPatientIdPatientUserIdUserLastnameContainsOrBsrBsIdBsServiceIdServiceCodeContainsOrBsrBsIdBsServiceIdServiceNameContainsOrBsrBsIdBsStaffIdStaffSdIdSdNameContainsAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString), allsearch, allsearch, allsearch, allsearch, allsearch, allsearch, allsearch, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }
    }

    @GetMapping("getallservicesbypatient")
    public Map<String, Object> getServicesByPatient(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsrId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillServiceRadiologyRepository.findByPatientId(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    //By GS
    @GetMapping("getallservicesbypatientvisit")
    public Iterable<TbillServiceRadiology> getServicesByPatientVisit(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsrId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillServiceRadiologyRepository.findAllByBsrBsIdBsBillIdBillVisitIdVisitId(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @GetMapping("getallservicesbypatientadmission")
    public Iterable<TbillServiceRadiology> getServicesByPatientAdmission(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsrId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillServiceRadiologyRepository.findAllByBsrBsIdBsBillIdBillAdmissionIdAdmissionId(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @GetMapping
    @RequestMapping("ListRecordByStartEndDate")
    public List<TbillServiceRadiology> listrecordbystartenddate(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "unitid", required = false, defaultValue = "0") Long unitid, @RequestParam(value = "startdate", required = false) String startdate, @RequestParam(value = "enddate", required = false) String enddate, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "bsrId") String col, @RequestBody TbillServiceRadiologyDTO json) throws ParseException {
        TenantContext.setCurrentTenant(tenantName);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date StartDate1 = df.parse(startdate);
        Date EndDate1 = df.parse(enddate);
        String StartDate = df.format(StartDate1);
        String EndDate = df.format(EndDate1);
        System.out.println("start======>" + StartDate);
        System.out.println("end======>" + EndDate);
        if (json.getIpd()) {
            String Query = " (SELECT bsr.*  " +
                    "FROM tbill_service_radiology bsr " +
                    "left JOIN tbill_bill_service tbbs ON tbbs.bs_id = bsr.bsr_bs_id " +
                    "left JOIN tbill_bill tb ON tb.bill_id = tbbs.bs_bill_id " +
                    "left JOIN mst_unit mu ON mu.unit_id = tb.tbill_unit_id " +
                    "left JOIN ipd_charges_service cs ON cs.cs_id = bsr.bsr_cs_id " +
                    "LEFT JOIN trn_admission ta ON ta.admission_id = tb.bill_admission_id " +
                    "LEFT JOIN mst_patient mp ON mp.patient_id = ta.admission_patient_id " +
                    "LEFT JOIN mst_user muser ON muser.user_id = mp.patient_user_id " +
                    "WHERE  bsr.ipd_bill = TRUE AND  (bsr.bsr_cs_id IS NULL AND (DATE(tbbs.bs_date) BETWEEN " +
                    " '" + StartDate + "' AND '" + EndDate + "') AND tb.tbill_unit_id = " + unitid + ")  ";
            if (json.getPatientName() != null && !json.getPatientName().equals("")) {
                Query += " And (CONCAT(muser.user_firstname,' ',muser.user_lastname) LIKE '%" + json.getPatientName() + "%' or  mp.patient_mr_no LIKE '%" + json.getPatientName() + "%') ";
            }
            if (json.getReported() != null) {
                Query += " And bsr.bsr_is_reported = " + json.getReported();
            }
            if (json.getCancelService() != null) {
                Query += " And bsr.bsr_is_cancel = " + json.getCancelService();
            }
            Query += " order by bsr.bsr_id asc  ) union all " +
                    " ( SELECT bsr.*  " +
                    "FROM tbill_service_radiology bsr " +
                    "left JOIN tbill_bill_service tbbs ON tbbs.bs_id = bsr.bsr_bs_id " +
                    "left JOIN tbill_bill tb ON tb.bill_id = tbbs.bs_bill_id " +
                    "left JOIN mst_unit mu ON mu.unit_id = tb.tbill_unit_id " +
                    "left JOIN ipd_charges_service cs ON cs.cs_id = bsr.bsr_cs_id " +
                    "left JOIN mbill_ipd_charge ic ON ic.ipdcharge_id = cs.cs_charge_id " +
                    "LEFT JOIN trn_admission ta ON ta.admission_id = ic.charge_admission_id " +
                    "LEFT JOIN mst_patient mp ON mp.patient_id = ta.admission_patient_id " +
                    "LEFT JOIN mst_user muser ON muser.user_id = mp.patient_user_id " +
                    "WHERE  bsr.ipd_bill = TRUE AND (bsr.bsr_cs_id IS NOT NULL AND DATE(cs.cs_date) BETWEEN '" + StartDate + "' AND '" + EndDate + "' AND ic.tbill_unit_id = " + unitid + ") ";
            if (json.getPatientName() != null && !json.getPatientName().equals("")) {
                Query += " And (CONCAT(muser.user_firstname,' ',muser.user_lastname) LIKE '%" + json.getPatientName() + "%' or  mp.patient_mr_no LIKE '%" + json.getPatientName() + "%') ";
            }
            if (json.getReported() != null) {
                Query += " And bsr.bsr_is_reported = " + json.getReported();
            }
            if (json.getCancelService() != null) {
                Query += " And bsr.bsr_is_cancel = " + json.getCancelService();
            }
            Query += " order by bsr.bsr_id asc )";
            String CountQuery = " select count(*) from ( " + Query + " ) as combine ";
            Query += "  limit " + ((Integer.parseInt(page) - 1) * Integer.parseInt(size)) + "," + Integer.parseInt(size);
            List<TbillServiceRadiology> Ipdlist = entityManager.createNativeQuery(Query, TbillServiceRadiology.class).getResultList();
//            return tbillServiceRadiologyRepository.findAllByBsrCsIdCsDateBetweenAndBsrCsIdCsChargeIdTbillUnitIdUnitIdAndBsrIpdBillTrue(StartDate, EndDate, unitid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            BigInteger count = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            if (Ipdlist.size() > 0) {
                Ipdlist.get(0).setCount(count.longValue());
            }
            return Ipdlist;
        } else {
            String Query = " SELECT bsr.*  " +
                    "FROM tbill_service_radiology bsr " +
                    "left JOIN tbill_bill_service tbbs ON tbbs.bs_id = bsr.bsr_bs_id " +
                    "left JOIN tbill_bill tb ON tb.bill_id = tbbs.bs_bill_id " +
                    "left JOIN mst_unit mu ON mu.unit_id = tb.tbill_unit_id " +
                    " LEFT JOIN mst_visit mv ON mv.visit_id = tb.bill_visit_id " +
                    "LEFT JOIN mst_patient mp ON mp.patient_id = mv.visit_patient_id " +
                    "LEFT JOIN mst_user muser ON muser.user_id = mp.patient_user_id " +
                    "WHERE  bsr.ipd_bill = false AND  (bsr.bsr_cs_id IS NULL AND (DATE(tbbs.bs_date) BETWEEN" +
                    " '" + StartDate + "' AND '" + EndDate + "') AND tb.tbill_unit_id = " + unitid + ")  ";
            if (json.getPatientName() != null && !json.getPatientName().equals("")) {
                Query += " And (CONCAT(muser.user_firstname,' ',muser.user_lastname) LIKE '%" + json.getPatientName() + "%' or mp.patient_mr_no LIKE '%" + json.getPatientName() + "%') ";
            }
            if (json.getReported() != null) {
                Query += " And bsr.bsr_is_reported = " + json.getReported();
            }
            if (json.getCancelService() != null) {
                Query += " And bsr.bsr_is_cancel = " + json.getCancelService();
            }
            String CountQuery = " select count(*) from ( " + Query + " ) as combine ";
            Query += " order by bsr.bsr_id asc limit " + ((Integer.parseInt(page) - 1) * Integer.parseInt(size)) + "," + Integer.parseInt(size);
            List<TbillServiceRadiology> Opdlist = entityManager.createNativeQuery(Query, TbillServiceRadiology.class).getResultList();
            BigInteger count = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            if (Opdlist.size() > 0) {
                Opdlist.get(0).setCount(count.longValue());
            }
//            return tbillServiceRadiologyRepository.findAllByBsrBsIdBsDateBetweenAndBsrBsIdBsBillIdTbillUnitIdUnitIdAndBsrIpdBillFalse(StartDate, EndDate, unitid, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            return Opdlist;
        }

    }

    @GetMapping
    @RequestMapping("getRadiologyList")
    public List<TbillServiceRadiology> getRadiologyList(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qString", required = false, defaultValue = "0") String qString) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "select p from TbillServiceRadiology p where p.bsrId=" + qString + " or p.bsrBsId.bsBillId.billVisitId.visitPatientId.patientId=" + qString;
        return entityManager.createQuery(query, TbillServiceRadiology.class).getResultList();
        //  return tbillServiceRadiologyRepository.findAllByBsrIdOrBsrBsIdBsBillIdBillAdmissionIdAdmissionPatientIdPatientIdOrBsrBsIdBsBillIdBillVisitIdVisitPatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString));
    }
}
            
