package com.cellbeans.hspa.mis.misopdreports;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/misopdcancelvisitreport")
public class LabBillTestReportController {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    CreateJSONObject createJSONObject;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    CreateReport createReport;

    @Autowired
    MstUnitRepository mstUnitRepository;

    // Lab bill test Report start 23.10.2019
    @RequestMapping("cancelvisitreport/{unitList}")
    public List<LabBillTestReportListDTO> searchListofgetLabBillTestTestReport(@RequestHeader("X-tenantId") String tenantName, @RequestBody LabBillTestReportSearchDTO labBillTestReportSearchDTO, @PathVariable String[] unitList) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        String fromdate = labBillTestReportSearchDTO.getFromdate(),
                todate = labBillTestReportSearchDTO.getTodate();
        List<LabBillTestReportListDTO> labBillTestReportListDTO = new ArrayList<LabBillTestReportListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query1 = " SELECT u.unit_name AS Unit,IFNULL(mv.created_date,'') AS VisitDate,IFNULL(mp.patient_mr_no,'') AS MRNumber, " +
                " IFNULL(concat(mt.title_name,' ',mu.user_firstname,' ', mu.user_lastname),'') as PatientName, IFNULL(mu.user_mobile,'') AS MobileNo, " +
                " CONCAT(mt1.title_name,' ',us.user_firstname,' ',us.user_lastname) AS DoctorName, " +
                " md.department_name AS Department, msd.sd_name AS SubDepartmentName,mbs.service_name AS ServiceName, " +
//                " IFNULL(mv.reason_visit,'') AS VisitType, " +
                " case when mv.is_virtual=1 then 'Virtual' ELSE 'Physical' end AS VisitType, " +
                " tbbs.bill_cancel_reason AS VisitCancelReason,tbbs.last_modified_date AS CancelledDate " +
                " FROM tbill_bill_service  tbbs " +
                " LEFT JOIN  tbill_bill  tb ON tbbs.bs_bill_id = tb.bill_id " +
                " LEFT JOIN  mst_visit  mv ON tb.bill_visit_id = mv.visit_id " +
                " LEFT JOIN  mst_patient  mp ON mv.visit_patient_id = mp.patient_id " +
                " LEFT JOIN  mst_user  mu ON mp.patient_user_id = mu.user_id " +
                " LEFT JOIN mst_title mt ON mt.title_id = mu.user_title_id " +
                " LEFT JOIN  mst_gender mg ON mu.user_gender_id = mg.gender_id " +
                " LEFT JOIN  mbill_service  mbs ON tbbs.bs_service_id = mbs.service_id " +
                " LEFT JOIN  mbill_group  mbgroup ON mbs.service_group_id = mbgroup.group_id " +
                " LEFT JOIN  mst_department  md ON mbgroup.group_department_id = md.department_id " +
                " LEFT JOIN  mst_sub_department  msd ON mbgroup.group_sd_id = msd.sd_id " +
                " LEFT JOIN  mst_unit  u ON tb.tbill_unit_id  =  u.unit_id " +
                " LEFT JOIN mst_staff ms ON tbbs.bs_staff_id = ms.staff_id " +
                " LEFT JOIN mst_user us on ms.staff_user_id=us.user_id " +
                " LEFT JOIN mst_title mt1 ON mt1.title_id = us.user_title_id " +
                " where tbbs.is_active = 1 and tbbs.is_deleted = 0 " +
                " AND tbbs.bs_status = 5 ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (labBillTestReportSearchDTO.getTodaydate()) {
            Query1 += " and (date(tbbs.last_modified_date)=curdate()) ";

        } else {
            Query1 += " and (date(tbbs.last_modified_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (labBillTestReportSearchDTO.getStaffId1() != null && !labBillTestReportSearchDTO.getStaffId1().equals("0")) {
            Query1 += " and tbbs.bs_staff_id = " + labBillTestReportSearchDTO.getStaffId1() + "";
        }
        if (labBillTestReportSearchDTO.getDepartmentId() != null && !labBillTestReportSearchDTO.getDepartmentId().equals("0")) {
            Query1 += " and mbgroup.group_department_id = " + labBillTestReportSearchDTO.getDepartmentId() + "";
        }
        if (labBillTestReportSearchDTO.getReasonVisit() != null && !labBillTestReportSearchDTO.getReasonVisit().equals("0")) {
//            Query1 += " and tb.bill_visit_id = " + labBillTestReportSearchDTO.getReasonVisit() + "";
            Query1 += " and mv.reason_visit = " + "\"" + labBillTestReportSearchDTO.getReasonVisit() + "\"" + "";
        }
        if (labBillTestReportSearchDTO.getConsultType() != null && labBillTestReportSearchDTO.getConsultType().equals("1")) {
            Query1 += " and mv.is_virtual = 0 ";
        }
        if (labBillTestReportSearchDTO.getConsultType() != null && labBillTestReportSearchDTO.getConsultType().equals("2")) {
            Query1 += " and mv.is_virtual = 1 ";
        }
        if (labBillTestReportSearchDTO.getServiceId() != null && !labBillTestReportSearchDTO.getServiceId().equals("0")) {
            Query1 += " and tbbs.bs_service_id = " + labBillTestReportSearchDTO.getServiceId() + "";
        }
        if (!unitList[0].equals("null") || !unitList[0].equals("0")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query1 += " and tb.tbill_unit_id in (" + values + ") ";
        }
        String CountQuery = " select count(*) from ( " + Query1 + " ) as combine ";
        System.out.println("MainQuery:" + Query1);
        try {
            Query1 += " limit " + ((labBillTestReportSearchDTO.getOffset() - 1) * labBillTestReportSearchDTO.getLimit()) + "," + labBillTestReportSearchDTO.getLimit();
            List<Object[]> billCumTestReport = entityManager.createNativeQuery(Query1).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            count = cc.longValue();
            for (Object[] obj : billCumTestReport) {
                LabBillTestReportListDTO objLabBillTestListDTO = new LabBillTestReportListDTO();
                objLabBillTestListDTO.setUnit("" + obj[0]);
                objLabBillTestListDTO.setVisitDate("" + obj[1]);
                objLabBillTestListDTO.setmRNo("" + obj[2]);
                objLabBillTestListDTO.setPatientName("" + obj[3]);
                objLabBillTestListDTO.setMobileNo("" + obj[4]);
                objLabBillTestListDTO.setDoctorName("" + obj[5]);
                objLabBillTestListDTO.setDepartment("" + obj[6]);
                objLabBillTestListDTO.setSubDepartment("" + obj[7]);
                objLabBillTestListDTO.setServiceName("" + obj[8]);
                objLabBillTestListDTO.setVisitType("" + obj[9]);
                objLabBillTestListDTO.setVisitCancelReason("" + obj[10]);
                objLabBillTestListDTO.setCancelDate("" + obj[11]);
                objLabBillTestListDTO.setCount(count);        // total count
                labBillTestReportListDTO.add(objLabBillTestListDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return labBillTestReportListDTO;
    } // END Service
    // Lab bill test Report End

    // Lab bill test Report Print Strat
    @RequestMapping("cancelvisitreportPrint/{unitList}")
    public String searchListofgetLabBillTestTestReportPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody LabBillTestReportSearchDTO labBillTestReportSearchDTO, @PathVariable String[] unitList) throws Exception {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        String fromdate = labBillTestReportSearchDTO.getFromdate(),
                todate = labBillTestReportSearchDTO.getTodate();
        List<LabBillTestReportListDTO> labBillTestReportListDTO = new ArrayList<LabBillTestReportListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query1 = " SELECT u.unit_name AS Unit,IFNULL(mv.created_date,'') AS VisitDate,IFNULL(mp.patient_mr_no,'') AS MRNumber, " +
                " IFNULL(concat(mt.title_name,' ',mu.user_firstname,' ', mu.user_lastname),'') as PatientName, IFNULL(mu.user_mobile,'') AS MobileNo, " +
                " CONCAT(mt1.title_name,' ',us.user_firstname,' ',us.user_lastname) AS DoctorName, " +
                " md.department_name AS Department, msd.sd_name AS SubDepartmentName,mbs.service_name AS ServiceName, " +
                " IFNULL(mv.reason_visit,'') AS VisitType, " +
                " tbbs.bill_cancel_reason AS VisitCancelReason,tbbs.last_modified_date AS CancelledDate " +
                " FROM tbill_bill_service  tbbs " +
                " LEFT JOIN  tbill_bill  tb ON tbbs.bs_bill_id = tb.bill_id " +
                " LEFT JOIN  mst_visit  mv ON tb.bill_visit_id = mv.visit_id " +
                " LEFT JOIN  mst_patient  mp ON mv.visit_patient_id = mp.patient_id " +
                " LEFT JOIN  mst_user  mu ON mp.patient_user_id = mu.user_id " +
                " LEFT JOIN mst_title mt ON mt.title_id = mu.user_title_id " +
                " LEFT JOIN  mst_gender mg ON mu.user_gender_id = mg.gender_id " +
                " LEFT JOIN  mbill_service  mbs ON tbbs.bs_service_id = mbs.service_id " +
                " LEFT JOIN  mbill_group  mbgroup ON mbs.service_group_id = mbgroup.group_id " +
                " LEFT JOIN  mst_department  md ON mbgroup.group_department_id = md.department_id " +
                " LEFT JOIN  mst_sub_department  msd ON mbgroup.group_sd_id = msd.sd_id " +
                " LEFT JOIN  mst_unit  u ON tb.tbill_unit_id  =  u.unit_id " +
                " LEFT JOIN mst_staff ms ON tbbs.bs_staff_id = ms.staff_id " +
                " LEFT JOIN mst_user us on ms.staff_user_id=us.user_id " +
                " LEFT JOIN mst_title mt1 ON mt1.title_id = us.user_title_id " +
                " where tbbs.is_active = 1 and tbbs.is_deleted = 0 " +
                " AND tbbs.bs_status = 5 ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (labBillTestReportSearchDTO.getTodaydate()) {
            Query1 += " and (date(tbbs.last_modified_date)=curdate()) ";

        } else {
            Query1 += " and (date(tbbs.last_modified_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (labBillTestReportSearchDTO.getStaffId1() != null && !labBillTestReportSearchDTO.getStaffId1().equals("0")) {
            Query1 += " and ms.staff_user_id = " + labBillTestReportSearchDTO.getStaffId1() + "";
        }
        if (labBillTestReportSearchDTO.getDepartmentId() != null && !labBillTestReportSearchDTO.getDepartmentId().equals("0")) {
            Query1 += " and mbgroup.group_department_id = " + labBillTestReportSearchDTO.getDepartmentId() + "";
        }
        if (labBillTestReportSearchDTO.getReasonVisit() != null && !labBillTestReportSearchDTO.getReasonVisit().equals("0")) {
//            Query1 += " and tb.bill_visit_id = " + labBillTestReportSearchDTO.getReasonVisit() + "";
            Query1 += " and mv.reason_visit = " + "\"" + labBillTestReportSearchDTO.getReasonVisit() + "\"" + "";
        }
        if (labBillTestReportSearchDTO.getServiceId() != null && !labBillTestReportSearchDTO.getServiceId().equals("0")) {
            Query1 += " and tbbs.bs_service_id = " + labBillTestReportSearchDTO.getServiceId() + "";
        }
        if (!unitList[0].equals("null") || !unitList[0].equals("0")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query1 += " and tb.tbill_unit_id in (" + values + ") ";
        }
        String CountQuery = " select count(*) from ( " + Query1 + " ) as combine ";
        System.out.println("MainQuery:" + Query1);
        JSONArray array = new JSONArray();
        try {
            // Query1 += " limit " + ((labBillTestReportSearchDTO.getOffset() - 1) * labBillTestReportSearchDTO.getLimit()) + "," + labBillTestReportSearchDTO.getLimit();
            List<Object[]> cancelVisitReport = entityManager.createNativeQuery(Query1).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            count = cc.longValue();

           /* for (Object[] obj : cancelVisitReport) {
                LabBillTestReportListDTO objLabBillTestListDTO = new LabBillTestReportListDTO();
                objLabBillTestListDTO.setUnit("" + obj[0]);
                objLabBillTestListDTO.setVisitDate("" + obj[1]);
                objLabBillTestListDTO.setmRNo("" + obj[2]);
                objLabBillTestListDTO.setPatientName("" + obj[3]);
                objLabBillTestListDTO.setMobileNo("" + obj[4]);
                objLabBillTestListDTO.setDoctorName("" + obj[5]);
                objLabBillTestListDTO.setDepartment("" + obj[6]);
                objLabBillTestListDTO.setSubDepartment("" + obj[7]);
                objLabBillTestListDTO.setServiceName("" + obj[8]);
                objLabBillTestListDTO.setVisitType("" + obj[9]);
                objLabBillTestListDTO.setVisitCancelReason("" + obj[10]);
                objLabBillTestListDTO.setCancelDate("" + obj[11]);
                objLabBillTestListDTO.setCount(count);        // total count

                labBillTestReportListDTO.add(objLabBillTestListDTO);
            }*/
            for (Object[] obj : cancelVisitReport) {
                //SampleCollectionListDTO objSampleCollectionListDTO = new SampleCollectionListDTO();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("unit", "" + obj[0]);
                jsonObject.put("visitDate", "" + obj[1]);
                jsonObject.put("mRNo", "" + obj[2]);
                jsonObject.put("patientName", "" + obj[3]);
                jsonObject.put("mobileNo", "" + obj[4]);
                jsonObject.put("doctorName", "" + obj[5]);
                jsonObject.put("department", "" + obj[6]);
                jsonObject.put("subDepartment", "" + obj[7]);
                jsonObject.put("serviceName", "" + obj[8]);
                jsonObject.put("visitType", "" + obj[9]);
                jsonObject.put("visitCancelReason", "" + obj[10]);
                jsonObject.put("cancelDate", "" + obj[11]);
                array.put(jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        //   return labBillTestReportListDTO;
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(labBillTestReportSearchDTO.getUnitId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        // labBillTestReportListDTO.get(0).setObjHeaderData(HeaderObject);
        array.getJSONObject(0).put("objHeaderData", jsonObject);
        //JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(labBillTestReportListDTO);
        if (labBillTestReportSearchDTO.getPrint()) {
            String columnName = "unit,visitDate,mRNo,patientName,mobileNo,doctorName,department,subDepartment,serviceName,visitType,visitCancelReason,cancelDate";
            return createReport.generateExcel(columnName, "CancelVisitReport", array);
        } else {
            return createReport.createJasperReportPDFByJson("CancelVisitReport", array.toString());
        }
       /* if(labBillTestReportSearchDTO.getPrint()){
            return createReport.createJasperReportXLS("CancelVisitReport", ds);
        }else {
            return createReport.createJasperReportPDF("CancelVisitReport", ds);
        }*/
    } // END Service
    // Lab bill test Report Print End

}