package com.cellbeans.hspa.trndischarge;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.mipdbed.MipdBed;
import com.cellbeans.hspa.mis.misipdreports.MisadmissionSearchDTO;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.cellbeans.hspa.mstvisit.MstVisitRepository;
import com.cellbeans.hspa.smsutility.Sendsms;
import com.cellbeans.hspa.tbillbill.TBillBill;
import com.cellbeans.hspa.tbillbill.TbillBillRepository;
import com.cellbeans.hspa.trnadmission.TrnAdmission;
import com.cellbeans.hspa.trnadmission.TrnAdmissionRepository;
import com.cellbeans.hspa.trnbedstatus.TrnBedStatusRepository;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/trn_discharge")
public class TrnDischargeController {

    Map<String, String> respMap = new HashMap<String, String>();
    Map<String, Object> respMapcreate = new HashMap<String, Object>();

    @Autowired
    TrnDischargeRepository trnDischargeRepository;

    @Autowired
    TrnAdmissionRepository trnAdmissionRepository;

    @Autowired
    TbillBillRepository tbillBillRepository;

    @Autowired
    TrnBedStatusRepository trnBedStatusRepository;
    @Autowired
    MstUnitRepository mstUnitRepository;
    @Autowired
    MstVisitRepository mstVisitRepository;
    @Autowired
    CreateReport createReport;

    @Autowired
    Sendsms sendsms;

    @PersistenceContext
    EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, Object> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnDischarge trnDischarge) {
        TenantContext.setCurrentTenant(tenantName);
        String currentdate = null;
        TrnDischarge newtrnDischarge = new TrnDischarge();
        if (trnDischarge.getDischargeAdmissionId().getAdmissionId() != 0L) {
            TrnAdmission trnAdmission = new TrnAdmission();
            trnAdmission = trnAdmissionRepository.getById(trnDischarge.getDischargeAdmissionId().getAdmissionId());
            newtrnDischarge = trnDischargeRepository.save(trnDischarge);
            if (trnDischarge.getDischargeDate() == null) {
                currentdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            } else {
                currentdate = trnDischarge.getDischargeDate();
            }
            trnAdmissionRepository.markasdischarge(currentdate, true, trnDischarge.getDischargeAdmissionId().getAdmissionId());
            for (MipdBed singlebed : trnAdmission.getAdmissionCurrentBedId()) {
                trnBedStatusRepository.bedstatusupdate(2, singlebed.getBedId());
            }
            //trnAdmissionRepository.dischargebedfree(trnDischarge.getDischargeAdmissionId().getAdmissionId());
            // sendsms.sendmessage(trnDischarge.getDischargeAdmissionId().getAdmissionStaffId().getPatientUserId().getUserMobile(),"You have been dischared.");
            // sendsms.sendmessage(trnDischarge.getDischargeStaffId().getStaffUserId().getUserMobile(), "The Patient "+ trnDischarge.getDischargeAdmissionId().getAdmissionPatientId().getPatientId()+ " has been dischared. ");
            respMapcreate.put("success", "1");
            respMapcreate.put("msg", "Added Successfully");
            respMapcreate.put("object", newtrnDischarge);
            return respMapcreate;
        } else {
            respMapcreate.put("success", "0");
            respMapcreate.put("msg", "Failed To Add Null Field");
            return respMapcreate;
        }
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<TrnDischarge> records;
        records = trnDischargeRepository.findByDischargeIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{dischargeId}")
    public TrnDischarge read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dischargeId") Long dischargeId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnDischarge trnDischarge = trnDischargeRepository.getById(dischargeId);
        return trnDischarge;
    }

    @RequestMapping("admissionbyid/{admissionId}")
    public TrnDischarge admissionbyid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("admissionId") Long admissionId) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("admission ==>" + admissionId);
        TrnDischarge trnDischarge = trnDischargeRepository.findAllByDischargeAdmissionIdAdmissionIdEquals(admissionId);
        return trnDischarge;
    }

    @RequestMapping("finalbillchack/{admissionId}")
    public boolean finalbillchack(@RequestHeader("X-tenantId") String tenantName, @PathVariable("admissionId") Long admissionId) {
        TenantContext.setCurrentTenant(tenantName);
        boolean finalbillcheck = false;
        try {
            List<TBillBill> tBillBillList = tbillBillRepository.findByBillAdmissionIdAdmissionIdAndIsActiveTrueAndIsDeletedFalseAndIpdBillTrueAndFinalBillTrueOrderByBillIdDesc(admissionId);
            if (tBillBillList.size() > 0) {
                finalbillcheck = true;
            }
        } catch (Exception e) {
            finalbillcheck = false;
        }
        return finalbillcheck;
    }

    @RequestMapping("update")
    public TrnDischarge update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnDischarge trnDischarge) {
        TenantContext.setCurrentTenant(tenantName);
        return trnDischargeRepository.save(trnDischarge);
    }

    @RequestMapping("dischargedate")
    public String dischargedate(@RequestHeader("X-tenantId") String tenantName, @RequestParam long admissionid) {
        TenantContext.setCurrentTenant(tenantName);
        String Disdate = "";
        String Query = "SELECT tbs FROM TrnDischarge tbs where tbs.dischargeAdmissionId.admissionId ='" + admissionid + "'";
        TrnDischarge objTrnDischarge = (TrnDischarge) entityManager.createQuery(Query).getSingleResult();
        Disdate = objTrnDischarge.getDischargeDate();
        return Disdate;
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TrnDischarge> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "dischargeId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return trnDischargeRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return trnDischargeRepository.findBydischargeIdContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @GetMapping
    @RequestMapping("listbyunit")
    public Iterable<TrnDischarge> listbyunit(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "dischargeId") String col, @RequestParam(value = "unitId", required = false, defaultValue = "0") String unitId) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            long str = Long.parseLong(unitId);
            return trnDischargeRepository.findAllByDischargeAdmissionIdAdmissionUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(str, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return trnDischargeRepository.findBydischargeIdContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @RequestMapping("listbydischargesearch")
    public List<TrnDischarge> searchConsoltant(@RequestHeader("X-tenantId") String tenantName, @RequestBody MisadmissionSearchDTO misadmissionSearchDTO) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        List<TrnDischarge> listTrnDischarge = new ArrayList<TrnDischarge>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String query2 = "select d from TrnDischarge d";
        String countQuery = "select count(d.dischargeId) from TrnDischarge d";
        if (!misadmissionSearchDTO.getPatientName().equals("")) {
            query2 += " where (d.dischargeAdmissionId.admissionPatientId.patientUserId.userFirstname like '%" + misadmissionSearchDTO.getPatientName() + "%' or d.dischargeAdmissionId.admissionPatientId.patientUserId.userLastname like '%" + misadmissionSearchDTO.getPatientName() + "%') ";
            countQuery += " where (d.dischargeAdmissionId.admissionPatientId.patientUserId.userFirstname like '%" + misadmissionSearchDTO.getPatientName() + "%' or d.dischargeAdmissionId.admissionPatientId.patientUserId.userLastname like '%" + misadmissionSearchDTO.getPatientName() + "%') ";

        } else {
            query2 += " where (d.dischargeAdmissionId.admissionPatientId.patientUserId.userFirstname like '%%' or d.dischargeAdmissionId.admissionPatientId.patientUserId.userLastname like '%%') ";
            countQuery += " where (d.dischargeAdmissionId.admissionPatientId.patientUserId.userFirstname like '%%' or d.dischargeAdmissionId.admissionPatientId.patientUserId.userLastname like '%%') ";
        }
        if (misadmissionSearchDTO.getFromdate().equals("") || misadmissionSearchDTO.getFromdate().equals("null")) {
            misadmissionSearchDTO.setFromdate("1990-06-07");
        }
        if (misadmissionSearchDTO.getTodate().equals("") || misadmissionSearchDTO.getTodate().equals("null")) {
            misadmissionSearchDTO.setTodate(strDate);
        }
        if (!String.valueOf(misadmissionSearchDTO.getUserId()).equals("0")) {
            query2 += " and d.dischargeStaffId.staffId=" + misadmissionSearchDTO.getUserId() + " ";
            countQuery += " and d.dischargeStaffId.staffId=" + misadmissionSearchDTO.getUserId() + " ";
        }
        if (!String.valueOf(misadmissionSearchDTO.getDoctorId()).equals("0")) {
            query2 += " and d.dischargeStaffId.staffId=" + misadmissionSearchDTO.getDoctorId() + " ";
            countQuery += " and d.dischargeStaffId.staffId=" + misadmissionSearchDTO.getDoctorId() + " ";
        }
        if (!String.valueOf(misadmissionSearchDTO.getDtId()).equals("0")) {
            query2 += " and d.dischargeType.dtId=" + misadmissionSearchDTO.getDtId() + " ";
            countQuery += " and d.dischargeType.dtId=" + misadmissionSearchDTO.getDtId() + " ";
        }
        if (!String.valueOf(misadmissionSearchDTO.getUnitId()).equals("0")) {
            query2 += " and d.dischargeAdmissionId.admissionUnitId.unitId=" + misadmissionSearchDTO.getUnitId() + " ";
            countQuery += " and d.dischargeAdmissionId.admissionUnitId.unitId=" + misadmissionSearchDTO.getUnitId() + " ";
        }
        if (!misadmissionSearchDTO.getMrNo().equals("")) {
            query2 += " and d.dischargeAdmissionId.admissionPatientId.patientMrNo like  '%" + misadmissionSearchDTO.getMrNo() + "%' ";
            countQuery += " and d.dischargeAdmissionId.admissionPatientId.patientMrNo like '%" + misadmissionSearchDTO.getMrNo() + "%' ";
        }
        if (misadmissionSearchDTO.getFromdate().equals("")) {
            misadmissionSearchDTO.setFromdate("1990-06-07");
        }
        if (misadmissionSearchDTO.getTodate().equals("")) {
            misadmissionSearchDTO.setTodate(strDate);
        }
        if (misadmissionSearchDTO.getTodaydate()) {
            query2 += " and date(d.dischargeDate)=curdate()='" + strDate + "' ";
            countQuery += " and date(d.dischargeDate)=curdate()='" + strDate + "' ";
        } else {
            query2 += " and (date(d.dischargeDate) between '" + misadmissionSearchDTO.getFromdate() + "' and '" + misadmissionSearchDTO.getTodate() + "') ";
            countQuery += " and (date(d.dischargeDate) between '" + misadmissionSearchDTO.getFromdate() + "' and '" + misadmissionSearchDTO.getTodate() + "')";
        }
        if (!String.valueOf(misadmissionSearchDTO.getTariffId()).equals("0")) {
            query2 += " and d.dischargeAdmissionId.admissionPatientId.patientId in (select p.visitPatientId.patientId from MstVisit p where p.visitTariffId.tariffId=" + misadmissionSearchDTO.getTariffId() + ") ";
            countQuery += " and d.dischargeAdmissionId.admissionPatientId.patientId in (select p.visitPatientId.patientId from MstVisit p where p.visitTariffId.tariffId=" + misadmissionSearchDTO.getTariffId() + ") ";
        }
        query2 += "order by d.dischargeDate DESC";
        countQuery += "order by d.dischargeDate DESC";
        try {
            //query2 += " limit " + ((misadmissionSearchDTO.getOffset() - 1) * misadmissionSearchDTO.getLimit()) + "," + misadmissionSearchDTO.getLimit();
            System.out.println("IPD Discharge Report" + query2);
            listTrnDischarge = entityManager.createQuery(query2).setFirstResult((misadmissionSearchDTO.getOffset() - 1) * misadmissionSearchDTO.getLimit()).setMaxResults(misadmissionSearchDTO.getLimit()).getResultList();
            if (!String.valueOf(misadmissionSearchDTO.getTariffId()).equals("0")) {
                for (TrnDischarge obj : listTrnDischarge) {
                    MstVisit objVisit = mstVisitRepository.findTop1ByVisitPatientIdPatientIdEqualsAndVisitTariffIdTariffIdEqualsOrderByVisitIdDesc(obj.getDischargeAdmissionId().getAdmissionPatientId().getPatientId(), misadmissionSearchDTO.getTariffId());
                    if (objVisit != null) {
                        obj.setObjMbillTariff(objVisit.getVisitTariffId());
                        obj.setObjReferYBy(objVisit.getReferBy());
                    }
                }
            } else {
                for (TrnDischarge obj : listTrnDischarge) {
                    MstVisit objVisit = mstVisitRepository.findTop1ByVisitPatientIdPatientIdEqualsOrderByVisitIdDesc(obj.getDischargeAdmissionId().getAdmissionPatientId().getPatientId());
                    if (objVisit != null) {
                        obj.setObjMbillTariff(objVisit.getVisitTariffId());
                        obj.setObjReferYBy(objVisit.getReferBy());
                    }
                }
            }
            long cc = (long) entityManager.createQuery(countQuery).getSingleResult();
            System.out.println("cc " + cc);
            count = cc;
            if (listTrnDischarge.size() != 0) {
                listTrnDischarge.get(0).setCount(count);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error:" + e);
            return null;
        }
        return listTrnDischarge;

    }

    @RequestMapping("listbydischargesearchPrint")
    public String searchConsoltantPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody MisadmissionSearchDTO misadmissionSearchDTO) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        List<TrnDischarge> listTrnDischarge = new ArrayList<TrnDischarge>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String query2 = "select d from TrnDischarge d";
        String countQuery = "select count(d.dischargeId) from TrnDischarge d";
        if (!misadmissionSearchDTO.getPatientName().equals("")) {
            query2 += " where (d.dischargeAdmissionId.admissionPatientId.patientUserId.userFirstname like '%" + misadmissionSearchDTO.getPatientName() + "%' or d.dischargeAdmissionId.admissionPatientId.patientUserId.userLastname like '%" + misadmissionSearchDTO.getPatientName() + "%') ";
            countQuery += " where (d.dischargeAdmissionId.admissionPatientId.patientUserId.userFirstname like '%" + misadmissionSearchDTO.getPatientName() + "%' or d.dischargeAdmissionId.admissionPatientId.patientUserId.userLastname like '%" + misadmissionSearchDTO.getPatientName() + "%') ";

        } else {
            query2 += " where (d.dischargeAdmissionId.admissionPatientId.patientUserId.userFirstname like '%%' or d.dischargeAdmissionId.admissionPatientId.patientUserId.userLastname like '%%') ";
            countQuery += " where (d.dischargeAdmissionId.admissionPatientId.patientUserId.userFirstname like '%%' or d.dischargeAdmissionId.admissionPatientId.patientUserId.userLastname like '%%') ";
        }
        if (misadmissionSearchDTO.getFromdate().equals("") || misadmissionSearchDTO.getFromdate().equals("null")) {
            misadmissionSearchDTO.setFromdate("1990-06-07");
        }
        if (misadmissionSearchDTO.getTodate().equals("") || misadmissionSearchDTO.getTodate().equals("null")) {
            misadmissionSearchDTO.setTodate(strDate);
        }
        if (!String.valueOf(misadmissionSearchDTO.getUserId()).equals("0")) {
            query2 += " and d.dischargeStaffId.staffId=" + misadmissionSearchDTO.getUserId() + " ";
            countQuery += " and d.dischargeStaffId.staffId=" + misadmissionSearchDTO.getUserId() + " ";
        }
        if (!String.valueOf(misadmissionSearchDTO.getDoctorId()).equals("0")) {
            query2 += " and d.dischargeStaffId.staffId=" + misadmissionSearchDTO.getDoctorId() + " ";
            countQuery += " and d.dischargeStaffId.staffId=" + misadmissionSearchDTO.getDoctorId() + " ";
        }
        if (!String.valueOf(misadmissionSearchDTO.getDtId()).equals("0")) {
            query2 += " and d.dischargeType.dtId=" + misadmissionSearchDTO.getDtId() + " ";
            countQuery += " and d.dischargeType.dtId=" + misadmissionSearchDTO.getDtId() + " ";
        }
        if (!String.valueOf(misadmissionSearchDTO.getUnitId()).equals("0")) {
            query2 += " and d.dischargeAdmissionId.admissionUnitId.unitId=" + misadmissionSearchDTO.getUnitId() + " ";
            countQuery += " and d.dischargeAdmissionId.admissionUnitId.unitId=" + misadmissionSearchDTO.getUnitId() + " ";
        }
        if (!misadmissionSearchDTO.getMrNo().equals("")) {
            query2 += " and d.dischargeAdmissionId.admissionPatientId.patientMrNo like  '%" + misadmissionSearchDTO.getMrNo() + "%' ";
            countQuery += " and d.dischargeAdmissionId.admissionPatientId.patientMrNo like '%" + misadmissionSearchDTO.getMrNo() + "%' ";
        }
        if (misadmissionSearchDTO.getFromdate().equals("")) {
            misadmissionSearchDTO.setFromdate("1990-06-07");
        }
        if (misadmissionSearchDTO.getTodate().equals("")) {
            misadmissionSearchDTO.setTodate(strDate);
        }
        if (misadmissionSearchDTO.getTodaydate()) {
            query2 += " and date(d.dischargeDate)=curdate()='" + strDate + "' ";
            countQuery += " and date(d.dischargeDate)=curdate()='" + strDate + "' ";
        } else {
            query2 += " and (date(d.dischargeDate) between '" + misadmissionSearchDTO.getFromdate() + "' and '" + misadmissionSearchDTO.getTodate() + "') ";
            countQuery += " and (date(d.dischargeDate) between '" + misadmissionSearchDTO.getFromdate() + "' and '" + misadmissionSearchDTO.getTodate() + "') ";
        }
        if (!String.valueOf(misadmissionSearchDTO.getTariffId()).equals("0")) {
            query2 += " and d.dischargeAdmissionId.admissionPatientId.patientId in (select p.visitPatientId.patientId from MstVisit p where p.visitTariffId.tariffId=" + misadmissionSearchDTO.getTariffId() + ")";
            countQuery += " and d.dischargeAdmissionId.admissionPatientId.patientId in (select p.visitPatientId.patientId from MstVisit p where p.visitTariffId.tariffId=" + misadmissionSearchDTO.getTariffId() + ")";
        }
        try {
            //query2 += " limit " + ((misadmissionSearchDTO.getOffset() - 1) * misadmissionSearchDTO.getLimit()) + "," + misadmissionSearchDTO.getLimit();
            System.out.println("Main Query" + query2);
            listTrnDischarge = entityManager.createQuery(query2).getResultList();
            if (!String.valueOf(misadmissionSearchDTO.getTariffId()).equals("0")) {
                for (TrnDischarge obj : listTrnDischarge) {
                    MstVisit objVisit = mstVisitRepository.findTop1ByVisitPatientIdPatientIdEqualsAndVisitTariffIdTariffIdEqualsOrderByVisitIdDesc(obj.getDischargeAdmissionId().getAdmissionPatientId().getPatientId(), misadmissionSearchDTO.getTariffId());
                    if (objVisit != null) {
                        obj.setObjMbillTariff(objVisit.getVisitTariffId());
                    }
                }
            } else {
                for (TrnDischarge obj : listTrnDischarge) {
                    MstVisit objVisit = mstVisitRepository.findTop1ByVisitPatientIdPatientIdEqualsOrderByVisitIdDesc(obj.getDischargeAdmissionId().getAdmissionPatientId().getPatientId());
                    if (objVisit != null) {
                        obj.setObjMbillTariff(objVisit.getVisitTariffId());
                    }
                }
            }
            long cc = (long) entityManager.createQuery(countQuery).getSingleResult();
            count = cc;
            listTrnDischarge.get(0).setCount(count);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error:" + e);
            return null;
        }
        //   return listTrnDischarge;
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(misadmissionSearchDTO.getUnitId());
        listTrnDischarge.get(0).setObjHeaderData(HeaderObject);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listTrnDischarge);
        if (misadmissionSearchDTO.getPrint()) {
            return createReport.createJasperReportXLS("DischargeReport", ds);
        } else {
            return createReport.createJasperReportPDF("DischargeReport", ds);
        }
    }

    @RequestMapping("listAllDischarge")
    public List listAllDischarge(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "dischargeId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        String Disdate = "";
        String Query = "select mst_user.user_firstname,mst_user.user_lastname ,admission_department_id,discharge_status,discharge_comment from trn_discharge  inner join trn_admission on trn_admission.admission_id=trn_discharge.discharge_admission_id inner join mst_patient on trn_admission.admission_patient_id=mst_patient.patient_id  inner join mst_user on mst_user.user_id=mst_patient.patient_user_id;";
        //List objTrnDischarge =(List<Object>)entityManager.createQuery(Query).getResultList();
        //Disdate=objTrnDischarge.getDischargeDate();
        return entityManager.createQuery(Query).getResultList();
    }

    @PutMapping("delete/{dischargeId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dischargeId") Long dischargeId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnDischarge trnDischarge = trnDischargeRepository.getById(dischargeId);
        if (trnDischarge != null) {
            trnDischarge.setIsDeleted(true);
            trnDischargeRepository.save(trnDischarge);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("listdischarge")
    public Iterable<TrnDischarge> listdischarge(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                                @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                @RequestParam(value = "col", required = false, defaultValue = "discharge_id") String col) throws ParseException {
        TenantContext.setCurrentTenant(tenantName);
        return trnDischargeRepository.findDischargePatient(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @GetMapping
    @RequestMapping("listtodaysdischarge")
    public Iterable<TrnDischarge> listtodaysdischarge(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                      @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                                      @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                      @RequestParam(value = "col", required = false, defaultValue = "discharge_id") String col) throws ParseException {
        TenantContext.setCurrentTenant(tenantName);
        return trnDischargeRepository.findTodaysDischargePatient(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @GetMapping
    @RequestMapping("listdeathdischarge")
    public Iterable<TrnDischarge> listdeathdischarge(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                     @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                                     @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                     @RequestParam(value = "col", required = false, defaultValue = "discharge_id") String col) throws ParseException {
        TenantContext.setCurrentTenant(tenantName);
        return trnDischargeRepository.findDeathDischargePatient(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @GetMapping
    @RequestMapping("listrecovereddischarge")
    public Iterable<TrnDischarge> listrecovereddischarge(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                         @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                                         @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                         @RequestParam(value = "col", required = false, defaultValue = "discharge_id") String col) throws ParseException {
        TenantContext.setCurrentTenant(tenantName);
        return trnDischargeRepository.findRecoveredDischargePatient(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }
}
            
