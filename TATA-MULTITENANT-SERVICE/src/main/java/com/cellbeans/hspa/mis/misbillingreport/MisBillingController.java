package com.cellbeans.hspa.mis.misbillingreport;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.mbilldoctorshare.MbillDoctorShare;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.cellbeans.hspa.mstuser.MstUser;
import com.cellbeans.hspa.mstuser.MstUserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/mis_billing_report")
public class MisBillingController {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    CreateJSONObject createJSONObject;

    @Autowired
    CreateReport createReport;

    @Autowired
    MstUnitRepository mstUnitRepository;

    @Autowired
    MstUserRepository mstUserRepository;

    @RequestMapping("searchConsoltantOpd/{unitList}")
    public List<ConsultantreferralListDTO> searchConsoltantOpd(@RequestHeader("X-tenantId") String tenantName, @RequestBody ConsultantreferallSearchDTO consultantreferallSearchDTO, @PathVariable String[] unitList) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Double doctorTotal;
        Double serviceTotal;
        Double hospitalTotal;
        List<ConsultantreferralListDTO> consultantreferrallistDTOList = new ArrayList<ConsultantreferralListDTO>();
        String query3 = "";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // String strDate = formatter.format(date);
        String marege = "select billDetails.bs_id,billDetails.class_name,billDetails.bill_number,billDetails.bill_date," + "billDetails.patient_mr_no,billDetails.patientName,billDetails.bs_service_id,billDetails.service_name," + "billDetails.serviceAmount,billDetails.bs_co_pay_qty_rate,doctorStaff.doctorName,userNameTB.user_name,doctorStaff.sdid ,billDetails.unit_name,billDetails.unit_id ";
        String query2 = " from (select s.bs_id,c.class_name,t.bill_number,t.bill_date,p.patient_mr_no,concat(u.user_firstname,' ',u.user_lastname) as patientName," + "ss.service_name,(s.bs_co_pay_qty_rate*s.bs_quantity) as serviceAmount,s.bs_co_pay_qty_rate,s.bs_service_id,un.unit_name,un.unit_id " + "from tbill_bill_service s left join mbill_service ss on s.bs_service_id=ss.service_id " + "left join tbill_bill t on s.bs_bill_id=t.bill_id " + "left join mst_unit un on un.unit_id=t.tbill_unit_id " + "left join mst_visit v on t.bill_visit_id=v.visit_id " + "left join mst_patient p on  v.visit_patient_id=p.patient_id " + "left join mst_user u on p.patient_user_id=u.user_id, mst_class c " + " where t.bill_class_id=c.class_id) as billDetails left join " + " (select s1.bs_id,st1.staff_user_id,st1.staff_id  as sdid,u1.user_fullname as doctorName from tbill_bill_service s1 left join mst_staff st1 on s1.bs_staff_id=st1.staff_id " + " left join mst_user u1 on st1.staff_user_id=u1.user_id) as doctorStaff " + " on billDetails.bs_id=doctorStaff.bs_id left join " + "(select s.bs_id,st.staff_id,u.user_name from tbill_bill_service s left join " + " tbill_bill t on s.bs_bill_id=t.bill_id left join mst_staff st on t.bill_user_id=st.staff_id " + " left join mst_user u on st.staff_user_id=u.user_id ) as userNameTB " + " on billDetails.bs_id=userNameTB.bs_id ";
        // query2+=marege;
        String countQuery = "select count(billDetails.bs_id) from " + "(select s.bs_id,c.class_name,t.bill_number,t.bill_date,p.patient_mr_no,concat(u.user_firstname,' ',u.user_lastname) as patientName," + "ss.service_name,s.bs_co_pay_qty_rate as serviceAmount,s.bs_co_pay_qty_rate,s.bs_service_id,un.unit_name,un.unit_id  " + " from tbill_bill_service s left join mbill_service ss on s.bs_service_id=ss.service_id " + "left join tbill_bill t on s.bs_bill_id=t.bill_id " + "left join mst_unit un on un.unit_id=t.tbill_unit_id " + "left join mst_visit v on t.bill_visit_id=v.visit_id " + "left join mst_patient p on  v.visit_patient_id=p.patient_id " + "left join  mst_user u on p.patient_user_id=u.user_id," + "  mst_class c " + " where t.bill_class_id=c.class_id) as billDetails left join " + " " + " (select s1.bs_id,st1.staff_user_id,st1.staff_id  as sdid,u1.user_fullname as doctorName from tbill_bill_service s1 left join mst_staff st1 on s1.bs_staff_id=st1.staff_id " + " left join mst_user u1 on st1.staff_user_id=u1.user_id) as doctorStaff " + " on billDetails.bs_id=doctorStaff.bs_id left join " + " " + "(select s.bs_id,st.staff_id,u.user_name from tbill_bill_service s left join " + " tbill_bill t on s.bs_bill_id=t.bill_id left join mst_staff st on t.bill_user_id=st.staff_id " + " left join mst_user u on st.staff_user_id=u.user_id ) as userNameTB " + " on billDetails.bs_id=userNameTB.bs_id ";
        String CalSumQuery2 = "select ifNull(sum(IF(d.doctor_share_amount>0, d.doctor_share_amount, (ifnull(s.bs_co_pay_qty_rate,0)/100)*d.doctor_share_per)),0) as doctorAmount from mbill_doctor_share d " + "inner join tbill_bill_service s on s.bs_service_id=d.ds_serviceid " + "inner join tbill_bill t on s.bs_bill_id=t.bill_id inner join mst_visit v on t.bill_visit_id=v.visit_id inner join mst_patient p on  v.visit_patient_id=p.patient_id " + "inner join  mst_user u on p.patient_user_id=u.user_id " + "where d.ds_staff_id=s.bs_staff_id and s.is_active=1 and s.is_deleted=0 and s.bs_cancel=0 ";
        if (!consultantreferallSearchDTO.getPatientName().equals("")) {
            query2 += " where  billDetails.patientName like '%" + consultantreferallSearchDTO.getPatientName() + "%' ";
            countQuery += " where  billDetails.patientName like '%" + consultantreferallSearchDTO.getPatientName() + "%' ";
            CalSumQuery2 += " and (u.user_firstname like '%" + consultantreferallSearchDTO.getPatientName() + "%' or u.user_lastname like '%" + consultantreferallSearchDTO.getPatientName() + "%' ) ";
        } else {
            query2 += " where  billDetails.patientName  like '%%' ";
            countQuery += " where billDetails.patientName  like '%%' ";
        }
        if (consultantreferallSearchDTO.getFromdate().equals("") || consultantreferallSearchDTO.getFromdate().equals("null")) {
            consultantreferallSearchDTO.setFromdate("1990-06-07");
        }
        if (consultantreferallSearchDTO.getTodate().equals("") || consultantreferallSearchDTO.getTodate().equals("null")) {
            consultantreferallSearchDTO.setTodate(strDate);
        }
        if (!String.valueOf(consultantreferallSearchDTO.getUserId()).equals("0")) {
            query2 += "  and userNameTB.staff_id=" + consultantreferallSearchDTO.getUserId() + " ";
            countQuery += "  and userNameTB.staff_id=" + consultantreferallSearchDTO.getUserId() + " ";
            CalSumQuery2 += " and s.bs_staff_id=" + consultantreferallSearchDTO.getUserId() + " ";
        }
        if (!String.valueOf(consultantreferallSearchDTO.getDoctorId()).equals("0")) {
            query2 += " and doctorStaff.sdid=" + consultantreferallSearchDTO.getDoctorId() + " ";
            countQuery += " and doctorStaff.sdid=" + consultantreferallSearchDTO.getDoctorId() + " ";
            CalSumQuery2 += " and s.bs_staff_id=" + consultantreferallSearchDTO.getDoctorId() + " ";

        }
        if (!consultantreferallSearchDTO.getMrNo().equals("")) {
            query2 += " and  billDetails.patient_mr_no like  '%" + consultantreferallSearchDTO.getMrNo() + "%' ";
            countQuery += " and  billDetails.patient_mr_no like '%" + consultantreferallSearchDTO.getMrNo() + "%' ";
            CalSumQuery2 += " and p.patient_mr_no like '%" + consultantreferallSearchDTO.getMrNo() + "%' ";
        }
        if (!String.valueOf(consultantreferallSearchDTO.getServiceId()).equals("0")) {
            query2 += " and billDetails.bs_service_id=" + consultantreferallSearchDTO.getServiceId() + " ";
            countQuery += " and billDetails.bs_service_id=" + consultantreferallSearchDTO.getServiceId() + " ";
            CalSumQuery2 += " and s.bs_service_id=" + consultantreferallSearchDTO.getServiceId() + " ";
        }
        // user Name
        if (!String.valueOf(consultantreferallSearchDTO.getUserId1()).equals("0")) {
            query2 += " and billDetails.patient_user_id=" + consultantreferallSearchDTO.getUserId1() + " ";
            countQuery += " and billDetails.patient_user_id=" + consultantreferallSearchDTO.getUserId1() + " ";
            CalSumQuery2 += " and s.staff_user_id=" + consultantreferallSearchDTO.getUserId1() + " ";
        }
//        if (!String.valueOf(consultantreferallSearchDTO.getUnitId()).equals("0")) {
//            query2 += " and billDetails.unit_id=" + consultantreferallSearchDTO.getUnitId() + " ";
//            countQuery += " and billDetails.unit_id=" + consultantreferallSearchDTO.getUnitId() + " ";
//            CalSumQuery1 += " and t.tbill_unit_id=" + consultantreferallSearchDTO.getUnitId() + " ";
//            CalSumQuery2 += " and d.ds_unit_id=" + consultantreferallSearchDTO.getUnitId() + " ";
//        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            query2 += " and billDetails.unit_id in (" + values + ")";
            countQuery += " and billDetails.unit_id in (" + values + ")";
            CalSumQuery2 += " and d.ds_unit_id in (" + values + ")";
        }
        if (consultantreferallSearchDTO.getFromdate().equals("")) {
            consultantreferallSearchDTO.setFromdate("1990-06-07");
        }
        if (consultantreferallSearchDTO.getTodate().equals("")) {
            consultantreferallSearchDTO.setTodate(strDate);
        }
        if (consultantreferallSearchDTO.getTodaydate()) {
            query2 += "  and date(billDetails.bill_date)=curdate() ";
            countQuery += "  and date(billDetails.bill_date)=curdate()";
            CalSumQuery2 += "  and date(t.bill_date)='" + strDate + "' ";
        } else {
            query2 += " and  (date(billDetails.bill_date) between '" + consultantreferallSearchDTO.getFromdate() + "' and '" + consultantreferallSearchDTO.getTodate() + "') ";
            countQuery += " and  (date(billDetails.bill_date) between '" + consultantreferallSearchDTO.getFromdate() + "' and '" + consultantreferallSearchDTO.getTodate() + "') ";
            CalSumQuery2 += " and (date(t.bill_date) between '" + consultantreferallSearchDTO.getFromdate() + "' and '" + consultantreferallSearchDTO.getTodate() + "') ";
        }
//        if (fromdate.equals("") || fromdate.equals("null")) {
//            fromdate = "1990-06-07";
//        }
//        if (todate.equals("") || todate.equals("null")) {
//            todate = strDate;
//        }
//
//        if (consultantreferallSearchDTO.getTodaydate()) {
//            query2 += " and (date(billDetails.bill_date)=curdate()) ";
//            countQuery += " and (date(billDetails.bill_date)=curdate()) ";
//            CalSumQuery2 += "and (date(t.bill_date)=curdate()) ";
//            //genderCount += " and (date(v.visit_date)=curdate()) ";
//        } else {
//            query2 += " and (date(billDetails.bill_date) between '" + fromdate + "' and '" + todate + "')  ";
//            countQuery += " and (date(billDetails.bill_date) between '" + fromdate + "' and '" + todate + "')  ";
//            CalSumQuery2 += " and (date(t.bill_date) between '" + fromdate + "' and '" + todate + "')  ";
//           // genderCount += " and (date(v.visit_date) between '" + fromdate + "' and '" + todate + "')  ";
//        }
        try {
            query3 = query2;
            query2 += "ORDER BY billDetails.bill_date DESC limit " + ((consultantreferallSearchDTO.getOffset() - 1) * consultantreferallSearchDTO.getLimit()) + "," + consultantreferallSearchDTO.getLimit();
            System.out.println("Service Wise Details Report "+ CalSumQuery2);
//            System.out.println("Main Query" + query2);
            List<Object[]> listTBillBillService = entityManager.createNativeQuery(marege + query2).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(countQuery).getSingleResult();
            count = cc.longValue();
            for (Object[] obj : listTBillBillService) {
                ConsultantreferralListDTO objconsultantreferrallistDTO = new ConsultantreferralListDTO();
                objconsultantreferrallistDTO.setOpClassName("" + obj[1]);
                objconsultantreferrallistDTO.setBillNo("" + obj[2]);
                objconsultantreferrallistDTO.setBillDate("" + obj[3]);
                objconsultantreferrallistDTO.setMrNo("" + obj[4]);
                objconsultantreferrallistDTO.setPatientName("" + obj[5]);
                objconsultantreferrallistDTO.setBsServiceId(Long.parseLong("" + obj[6]));
                objconsultantreferrallistDTO.setServiceName("" + obj[7]);
                objconsultantreferrallistDTO.setDoctorName("" + obj[10]);
                objconsultantreferrallistDTO.setUserName("" + obj[11]);
                objconsultantreferrallistDTO.setServiceAmount("" + obj[8]);
                objconsultantreferrallistDTO.setBsStaffId(Long.parseLong("" + obj[12]));
                objconsultantreferrallistDTO.setUnitName("" + obj[13]);
                objconsultantreferrallistDTO.setUnitId(Long.parseLong("" + obj[14]));
                objconsultantreferrallistDTO.setCount(count);
                objconsultantreferrallistDTO.setDoctorAmount(getCalculateDctoreShare(tenantName, objconsultantreferrallistDTO.getBsStaffId(), objconsultantreferrallistDTO.getBsServiceId(), objconsultantreferrallistDTO.getUnitId(), "" + obj[9]));
                objconsultantreferrallistDTO.setHospitalAmount(String.valueOf(Math.round(Double.parseDouble(obj[9].toString()) - (objconsultantreferrallistDTO.getDoctorAmount() != null ? objconsultantreferrallistDTO.getDoctorAmount() : 0))));
                consultantreferrallistDTOList.add(objconsultantreferrallistDTO);
            }
            doctorTotal = (Double) entityManager.createNativeQuery(CalSumQuery2).getSingleResult();
            System.out.println("doctor Query" + CalSumQuery2);
            String CalSumQuery1 = " select sum(billDetails.serviceAmount),sum(billDetails.bs_co_pay_qty_rate) " + query3;
            List<Object[]> lisCout = entityManager.createNativeQuery(CalSumQuery1).getResultList();
            System.out.println("other Query" + CalSumQuery1);
            if (consultantreferrallistDTOList.size() > 0) {
                for (Object[] obj : lisCout) {
                    consultantreferrallistDTOList.get(0).setServiceTotal(Double.parseDouble(obj[0].toString()));
                    consultantreferrallistDTOList.get(0).setHospitalTotal(Double.parseDouble(obj[1].toString()) - doctorTotal);
                }
                consultantreferrallistDTOList.get(0).setDoctorTotal(doctorTotal);

            }

        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("Error:" + e);
            return null;
        }
        return consultantreferrallistDTOList;
    }

    @RequestMapping("searchConsoltantOpdPrint1/{unitList}")
    public String searchConsoltantOpdPrint1(@RequestHeader("X-tenantId") String tenantName, @RequestBody ConsultantreferallSearchDTO consultantreferallSearchDTO, @PathVariable String[] unitList) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Double doctorTotal;
        Double serviceTotal;
        Double hospitalTotal;
        List<ConsultantreferralListDTO> consultantreferrallistDTOList = new ArrayList<ConsultantreferralListDTO>();
        Date date = new Date();
        String query3 = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String marege = "select billDetails.bs_id,billDetails.class_name,billDetails.bill_number,billDetails.bill_date," + "billDetails.patient_mr_no,billDetails.patientName,billDetails.bs_service_id,billDetails.service_name," + "billDetails.serviceAmount,billDetails.bs_co_pay_qty_rate,doctorStaff.doctorName,userNameTB.user_name,doctorStaff.sdid ,billDetails.unit_name,billDetails.unit_id ";
        String query2 = " from (select s.bs_id,c.class_name,t.bill_number,t.bill_date,p.patient_mr_no,concat(u.user_firstname,' ',u.user_lastname) as patientName," + "ss.service_name,(s.bs_co_pay_qty_rate*s.bs_quantity) as serviceAmount,s.bs_co_pay_qty_rate,s.bs_service_id,un.unit_name,un.unit_id " + "from tbill_bill_service s left join mbill_service ss on s.bs_service_id=ss.service_id " + "left join tbill_bill t on s.bs_bill_id=t.bill_id " + "left join mst_unit un on un.unit_id=t.tbill_unit_id " + "left join mst_visit v on t.bill_visit_id=v.visit_id " + "left join mst_patient p on  v.visit_patient_id=p.patient_id " + "left join mst_user u on p.patient_user_id=u.user_id, mst_class c " + " where t.bill_class_id=c.class_id) as billDetails left join " + " (select s1.bs_id,st1.staff_user_id,st1.staff_id  as sdid,u1.user_fullname as doctorName from tbill_bill_service s1 left join mst_staff st1 on s1.bs_staff_id=st1.staff_id " + " left join mst_user u1 on st1.staff_user_id=u1.user_id) as doctorStaff " + " on billDetails.bs_id=doctorStaff.bs_id left join " + "(select s.bs_id,st.staff_id,u.user_name from tbill_bill_service s left join " + " tbill_bill t on s.bs_bill_id=t.bill_id left join mst_staff st on t.bill_user_id=st.staff_id " + " left join mst_user u on st.staff_user_id=u.user_id ) as userNameTB " + " on billDetails.bs_id=userNameTB.bs_id ";
        // query2+=marege;
        String countQuery = "select count(billDetails.bs_id) from " + "(select s.bs_id,c.class_name,t.bill_number,t.bill_date,p.patient_mr_no,concat(u.user_firstname,' ',u.user_lastname) as patientName," + "ss.service_name,s.bs_co_pay_qty_rate as serviceAmount,s.bs_co_pay_qty_rate,s.bs_service_id,un.unit_name,un.unit_id  " + " from tbill_bill_service s left join mbill_service ss on s.bs_service_id=ss.service_id " + "left join tbill_bill t on s.bs_bill_id=t.bill_id " + "left join mst_unit un on un.unit_id=t.tbill_unit_id " + "left join mst_visit v on t.bill_visit_id=v.visit_id " + "left join mst_patient p on  v.visit_patient_id=p.patient_id " + "left join  mst_user u on p.patient_user_id=u.user_id," + "  mst_class c " + " where t.bill_class_id=c.class_id) as billDetails left join " + " " + " (select s1.bs_id,st1.staff_user_id,st1.staff_id  as sdid,u1.user_fullname as doctorName from tbill_bill_service s1 left join mst_staff st1 on s1.bs_staff_id=st1.staff_id " + " left join mst_user u1 on st1.staff_user_id=u1.user_id) as doctorStaff " + " on billDetails.bs_id=doctorStaff.bs_id left join " + " " + "(select s.bs_id,st.staff_id,u.user_name from tbill_bill_service s left join " + " tbill_bill t on s.bs_bill_id=t.bill_id left join mst_staff st on t.bill_user_id=st.staff_id " + " left join mst_user u on st.staff_user_id=u.user_id ) as userNameTB " + " on billDetails.bs_id=userNameTB.bs_id ";
        String CalSumQuery2 = "select ifNull(sum(IF(d.doctor_share_amount>0, d.doctor_share_amount, (ifnull(s.bs_co_pay_qty_rate,0)/100)*d.doctor_share_per)),0) as doctorAmount from mbill_doctor_share d " + "inner join tbill_bill_service s on s.bs_service_id=d.ds_serviceid " + "inner join tbill_bill t on s.bs_bill_id=t.bill_id inner join mst_visit v on t.bill_visit_id=v.visit_id inner join mst_patient p on  v.visit_patient_id=p.patient_id " + "inner join  mst_user u on p.patient_user_id=u.user_id " + "where d.ds_staff_id=s.bs_staff_id and s.is_active=1 and s.is_deleted=0 and s.bs_cancel=0 ";
        if (!consultantreferallSearchDTO.getPatientName().equals("")) {
            query2 += " where  billDetails.patientName like '%" + consultantreferallSearchDTO.getPatientName() + "%' ";
            countQuery += " where  billDetails.patientName like '%" + consultantreferallSearchDTO.getPatientName() + "%' ";
            CalSumQuery2 += " and (u.user_firstname like '%" + consultantreferallSearchDTO.getPatientName() + "%' or u.user_lastname like '%" + consultantreferallSearchDTO.getPatientName() + "%' ) ";
        } else {
            query2 += " where  billDetails.patientName  like '%%' ";
            countQuery += " where billDetails.patientName  like '%%' ";
        }
        if (consultantreferallSearchDTO.getFromdate().equals("") || consultantreferallSearchDTO.getFromdate().equals("null")) {
            consultantreferallSearchDTO.setFromdate("1990-06-07");
        }
        if (consultantreferallSearchDTO.getTodate().equals("") || consultantreferallSearchDTO.getTodate().equals("null")) {
            consultantreferallSearchDTO.setTodate(strDate);
        }
        if (!String.valueOf(consultantreferallSearchDTO.getUserId()).equals("0")) {
            query2 += "  and userNameTB.staff_id=" + consultantreferallSearchDTO.getUserId() + " ";
            countQuery += "  and userNameTB.staff_id=" + consultantreferallSearchDTO.getUserId() + " ";
            CalSumQuery2 += " and s.bs_staff_id=" + consultantreferallSearchDTO.getUserId() + " ";
        }
        if (!String.valueOf(consultantreferallSearchDTO.getDoctorId()).equals("0")) {
            query2 += " and doctorStaff.sdid=" + consultantreferallSearchDTO.getDoctorId() + " ";
            countQuery += " and doctorStaff.sdid=" + consultantreferallSearchDTO.getDoctorId() + " ";
            CalSumQuery2 += " and s.bs_staff_id=" + consultantreferallSearchDTO.getDoctorId() + " ";

        }
        if (!consultantreferallSearchDTO.getMrNo().equals("")) {
            query2 += " and  billDetails.patient_mr_no like  '%" + consultantreferallSearchDTO.getMrNo() + "%' ";
            countQuery += " and  billDetails.patient_mr_no like '%" + consultantreferallSearchDTO.getMrNo() + "%' ";
            CalSumQuery2 += " and p.patient_mr_no like '%" + consultantreferallSearchDTO.getMrNo() + "%' ";
        }
        if (!String.valueOf(consultantreferallSearchDTO.getServiceId()).equals("0")) {
            query2 += " and billDetails.bs_service_id=" + consultantreferallSearchDTO.getServiceId() + " ";
            countQuery += " and billDetails.bs_service_id=" + consultantreferallSearchDTO.getServiceId() + " ";
            CalSumQuery2 += " and s.bs_service_id=" + consultantreferallSearchDTO.getServiceId() + " ";
        }
//        if (!String.valueOf(consultantreferallSearchDTO.getUnitId()).equals("0")) {
//            query2 += " and billDetails.unit_id=" + consultantreferallSearchDTO.getUnitId() + " ";
//            countQuery += " and billDetails.unit_id=" + consultantreferallSearchDTO.getUnitId() + " ";
//            CalSumQuery1 += " and t.tbill_unit_id=" + consultantreferallSearchDTO.getUnitId() + " ";
//            CalSumQuery2 += " and d.ds_unit_id=" + consultantreferallSearchDTO.getUnitId() + " ";
//        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            query2 += " and billDetails.unit_id in (" + values + ")";
            countQuery += " and billDetails.unit_id in (" + values + ")";
            CalSumQuery2 += " and d.ds_unit_id in (" + values + ")";
        }
        if (consultantreferallSearchDTO.getFromdate().equals("")) {
            consultantreferallSearchDTO.setFromdate("1990-06-07");
        }
        if (consultantreferallSearchDTO.getTodate().equals("")) {
            consultantreferallSearchDTO.setTodate(strDate);
        }
        if (consultantreferallSearchDTO.getTodaydate()) {
            query2 += "  and date(billDetails.bill_date)=curdate() ";
            countQuery += "  and date(billDetails.bill_date)=curdate()";
            CalSumQuery2 += "  and date(t.bill_date)='" + strDate + "' ";
        } else {
            query2 += " and  (date(billDetails.bill_date) between '" + consultantreferallSearchDTO.getFromdate() + "' and '" + consultantreferallSearchDTO.getTodate() + "') ";
            countQuery += " and  (date(billDetails.bill_date) between '" + consultantreferallSearchDTO.getFromdate() + "' and '" + consultantreferallSearchDTO.getTodate() + "') ";
            CalSumQuery2 += " and (date(t.bill_date) between '" + consultantreferallSearchDTO.getFromdate() + "' and '" + consultantreferallSearchDTO.getTodate() + "') ";
        }
        try {
            query3 = query2;
            //    query2 += " limit " + ((consultantreferallSearchDTO.getOffset() - 1) * consultantreferallSearchDTO.getLimit()) + "," + consultantreferallSearchDTO.getLimit();
            System.out.println("CalSumQuery2" + CalSumQuery2);
//            System.out.println("Main Query" + query2);
            List<Object[]> listTBillBillService = entityManager.createNativeQuery(marege + query2).getResultList();
//            BigInteger cc = (BigInteger) entityManager.createNativeQuery(countQuery).getSingleResult();
//            count = cc.longValue();
            for (Object[] obj : listTBillBillService) {
                ConsultantreferralListDTO objconsultantreferrallistDTO = new ConsultantreferralListDTO();
                objconsultantreferrallistDTO.setOpClassName("" + obj[1]);
                objconsultantreferrallistDTO.setBillNo("" + obj[2]);
                objconsultantreferrallistDTO.setBillDate(obj[3].toString());
                objconsultantreferrallistDTO.setMrNo("" + obj[4]);
                objconsultantreferrallistDTO.setPatientName("" + obj[5]);
                objconsultantreferrallistDTO.setBsServiceId(Long.parseLong("" + obj[6]));
                objconsultantreferrallistDTO.setServiceName("" + obj[7]);
                objconsultantreferrallistDTO.setDoctorName("" + obj[10]);
                objconsultantreferrallistDTO.setUserName("" + obj[11]);
                objconsultantreferrallistDTO.setServiceAmount("" + obj[8]);
                objconsultantreferrallistDTO.setBsStaffId(Long.parseLong("" + obj[12]));
                objconsultantreferrallistDTO.setUnitName("" + obj[13]);
                objconsultantreferrallistDTO.setUnitId(Long.parseLong("" + obj[14]));
                objconsultantreferrallistDTO.setCount(count);
                objconsultantreferrallistDTO.setDoctorAmount(getCalculateDctoreShare(tenantName, objconsultantreferrallistDTO.getBsStaffId(), objconsultantreferrallistDTO.getBsServiceId(), objconsultantreferrallistDTO.getUnitId(), "" + obj[9]));
                objconsultantreferrallistDTO.setHospitalAmount(String.valueOf(Math.round(Double.parseDouble(obj[9].toString()) - (objconsultantreferrallistDTO.getDoctorAmount() != null ? objconsultantreferrallistDTO.getDoctorAmount() : 0))));
                objconsultantreferrallistDTO.setShowType(consultantreferallSearchDTO.getShowtype());
                consultantreferrallistDTOList.add(objconsultantreferrallistDTO);
            }
            doctorTotal = (Double) entityManager.createNativeQuery(CalSumQuery2).getSingleResult();
            System.out.println("doctor Query" + CalSumQuery2);
            String CalSumQuery1 = " select sum(billDetails.serviceAmount),sum(billDetails.bs_co_pay_qty_rate) " + query3;
            List<Object[]> lisCout = entityManager.createNativeQuery(CalSumQuery1).getResultList();
            System.out.println("other Query" + CalSumQuery1);
            if (consultantreferrallistDTOList.size() > 0) {
                for (Object[] obj : lisCout) {
                    consultantreferrallistDTOList.get(0).setServiceTotal(Double.parseDouble(obj[0].toString()));
                    consultantreferrallistDTOList.get(0).setHospitalTotal(Double.parseDouble(obj[1].toString()) - doctorTotal);
                }
                consultantreferrallistDTOList.get(0).setDoctorTotal(doctorTotal);

            }

        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("Error:" + e);
            return null;
        }
        // return consultantreferrallistDTOList;
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(consultantreferallSearchDTO.getUnitId());
        consultantreferrallistDTOList.get(0).setObjHeaderData(HeaderObject);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(consultantreferrallistDTOList);
        // return createReport.createJasperReportXLS("OutwardReferralReport", ds);
        if (consultantreferallSearchDTO.getPrint()) {
            return createReport.createJasperReportXLS("ConsultantReferalReport", ds);
        } else {
            return createReport.createJasperReportPDF(
                    "ConsultantReferalReport", ds);
        }
    }

    @RequestMapping("searchConsoltantOpdPrint/{unitList}")
    public String searchConsoltantOpdPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody ConsultantreferallSearchDTO consultantreferallSearchDTO, @PathVariable String[] unitList) throws Exception {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Double doctorTotal;
        Double serviceTotal;
        Double hospitalTotal;
        JSONArray jsonArray = null;
        Date date = new Date();
        String query3 = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String marege = "select billDetails.bs_id as bsId,billDetails.class_name as opClassName,billDetails.bill_number as billNo,billDetails.bill_date as billDate," + "billDetails.patient_mr_no as mrNo,billDetails.patientName,billDetails.bs_service_id as bsServiceId,billDetails.service_name as serviceName," + "billDetails.serviceAmount,billDetails.bs_gross_rate,doctorStaff.doctorName,userNameTB.user_name as userName,doctorStaff.sdid ,billDetails.unit_name as unitName,billDetails.unit_id as unitId";
        String query2 = " from (select s.bs_id,c.class_name,t.bill_number,t.bill_date,p.patient_mr_no,concat(u.user_firstname,' ',u.user_lastname) as patientName," + "ss.service_name,(s.bs_base_rate*s.bs_quantity) as serviceAmount,s.bs_gross_rate,s.bs_service_id,un.unit_name,un.unit_id " + "from tbill_bill_service s left join mbill_service ss on s.bs_service_id=ss.service_id " + "left join tbill_bill t on s.bs_bill_id=t.bill_id " + "left join mst_unit un on un.unit_id=t.tbill_unit_id " + "left join mst_visit v on t.bill_visit_id=v.visit_id " + "left join mst_patient p on  v.visit_patient_id=p.patient_id " + "left join mst_user u on p.patient_user_id=u.user_id, mst_class c " + " where t.bill_class_id=c.class_id) as billDetails left join " + " (select s1.bs_id,st1.staff_user_id,st1.staff_id  as sdid,u1.user_fullname as doctorName from tbill_bill_service s1 left join mst_staff st1 on s1.bs_staff_id=st1.staff_id " + " left join mst_user u1 on st1.staff_user_id=u1.user_id) as doctorStaff " + " on billDetails.bs_id=doctorStaff.bs_id left join " + "(select s.bs_id,st.staff_id,u.user_name from tbill_bill_service s left join " + " tbill_bill t on s.bs_bill_id=t.bill_id left join mst_staff st on t.bill_user_id=st.staff_id " + " left join mst_user u on st.staff_user_id=u.user_id ) as userNameTB " + " on billDetails.bs_id=userNameTB.bs_id ";
        String countQuery = "select count(billDetails.bs_id) from " + "(select s.bs_id,c.class_name,t.bill_number,t.bill_date,p.patient_mr_no,concat(u.user_firstname,' ',u.user_lastname) as patientName," + "ss.service_name,s.bs_base_rate as serviceAmount,s.bs_gross_rate,s.bs_service_id,un.unit_name,un.unit_id  " + " from tbill_bill_service s left join mbill_service ss on s.bs_service_id=ss.service_id " + "left join tbill_bill t on s.bs_bill_id=t.bill_id " + "left join mst_unit un on un.unit_id=t.tbill_unit_id " + "left join mst_visit v on t.bill_visit_id=v.visit_id " + "left join mst_patient p on  v.visit_patient_id=p.patient_id " + "left join  mst_user u on p.patient_user_id=u.user_id," + "  mst_class c " + " where t.bill_class_id=c.class_id) as billDetails left join " + " " + " (select s1.bs_id,st1.staff_user_id,st1.staff_id  as sdid,u1.user_fullname as doctorName from tbill_bill_service s1 left join mst_staff st1 on s1.bs_staff_id=st1.staff_id " + " left join mst_user u1 on st1.staff_user_id=u1.user_id) as doctorStaff " + " on billDetails.bs_id=doctorStaff.bs_id left join " + " " + "(select s.bs_id,st.staff_id,u.user_name from tbill_bill_service s left join " + " tbill_bill t on s.bs_bill_id=t.bill_id left join mst_staff st on t.bill_user_id=st.staff_id " + " left join mst_user u on st.staff_user_id=u.user_id ) as userNameTB " + " on billDetails.bs_id=userNameTB.bs_id ";
        String CalSumQuery2 = "select ifNull(sum(IF(d.doctor_share_amount>0, d.doctor_share_amount, (ifnull(s.bs_base_rate,0)/100)*d.doctor_share_per)),0) as doctorAmount from mbill_doctor_share d " + "inner join tbill_bill_service s on s.bs_service_id=d.ds_serviceid " + "inner join tbill_bill t on s.bs_bill_id=t.bill_id inner join mst_visit v on t.bill_visit_id=v.visit_id inner join mst_patient p on  v.visit_patient_id=p.patient_id " + "inner join  mst_user u on p.patient_user_id=u.user_id " + "where d.ds_staff_id=s.bs_staff_id and s.is_active=1 and s.is_deleted=0 and s.bs_cancel=0 ";
        if (!consultantreferallSearchDTO.getPatientName().equals("")) {
            query2 += " where  billDetails.patientName like '%" + consultantreferallSearchDTO.getPatientName() + "%' ";
            countQuery += " where  billDetails.patientName like '%" + consultantreferallSearchDTO.getPatientName() + "%' ";
            CalSumQuery2 += " and (u.user_firstname like '%" + consultantreferallSearchDTO.getPatientName() + "%' or u.user_lastname like '%" + consultantreferallSearchDTO.getPatientName() + "%' ) ";
        } else {
            query2 += " where  billDetails.patientName  like '%%' ";
            countQuery += " where billDetails.patientName  like '%%' ";
        }
        if (consultantreferallSearchDTO.getFromdate().equals("") || consultantreferallSearchDTO.getFromdate().equals("null")) {
            consultantreferallSearchDTO.setFromdate("1990-06-07");
        }
        if (consultantreferallSearchDTO.getTodate().equals("") || consultantreferallSearchDTO.getTodate().equals("null")) {
            consultantreferallSearchDTO.setTodate(strDate);
        }
        if (!String.valueOf(consultantreferallSearchDTO.getUserId()).equals("0")) {
            query2 += "  and userNameTB.staff_id=" + consultantreferallSearchDTO.getUserId() + " ";
            countQuery += "  and userNameTB.staff_id=" + consultantreferallSearchDTO.getUserId() + " ";
            CalSumQuery2 += " and s.bs_staff_id=" + consultantreferallSearchDTO.getUserId() + " ";
        }
        if (!String.valueOf(consultantreferallSearchDTO.getDoctorId()).equals("0")) {
            query2 += " and doctorStaff.sdid=" + consultantreferallSearchDTO.getDoctorId() + " ";
            countQuery += " and doctorStaff.sdid=" + consultantreferallSearchDTO.getDoctorId() + " ";
            CalSumQuery2 += " and s.bs_staff_id=" + consultantreferallSearchDTO.getDoctorId() + " ";

        }
        if (!consultantreferallSearchDTO.getMrNo().equals("")) {
            query2 += " and  billDetails.patient_mr_no like  '%" + consultantreferallSearchDTO.getMrNo() + "%' ";
            countQuery += " and  billDetails.patient_mr_no like '%" + consultantreferallSearchDTO.getMrNo() + "%' ";
            CalSumQuery2 += " and p.patient_mr_no like '%" + consultantreferallSearchDTO.getMrNo() + "%' ";
        }
        if (!String.valueOf(consultantreferallSearchDTO.getServiceId()).equals("0")) {
            query2 += " and billDetails.bs_service_id=" + consultantreferallSearchDTO.getServiceId() + " ";
            countQuery += " and billDetails.bs_service_id=" + consultantreferallSearchDTO.getServiceId() + " ";
            CalSumQuery2 += " and s.bs_service_id=" + consultantreferallSearchDTO.getServiceId() + " ";
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            query2 += " and billDetails.unit_id in (" + values + ")";
            countQuery += " and billDetails.unit_id in (" + values + ")";
            CalSumQuery2 += " and d.ds_unit_id in (" + values + ")";
        }
        if (consultantreferallSearchDTO.getFromdate().equals("")) {
            consultantreferallSearchDTO.setFromdate("1990-06-07");
        }
        if (consultantreferallSearchDTO.getTodate().equals("")) {
            consultantreferallSearchDTO.setTodate(strDate);
        }
        if (consultantreferallSearchDTO.getTodaydate()) {
            query2 += "  and date(billDetails.bill_date)=curdate() ";
            countQuery += "  and date(billDetails.bill_date)=curdate()";
            CalSumQuery2 += "  and date(t.bill_date)='" + strDate + "' ";
        } else {
            query2 += " and  (date(billDetails.bill_date) between '" + consultantreferallSearchDTO.getFromdate() + "' and '" + consultantreferallSearchDTO.getTodate() + "') ";
            countQuery += " and  (date(billDetails.bill_date) between '" + consultantreferallSearchDTO.getFromdate() + "' and '" + consultantreferallSearchDTO.getTodate() + "') ";
            CalSumQuery2 += " and (date(t.bill_date) between '" + consultantreferallSearchDTO.getFromdate() + "' and '" + consultantreferallSearchDTO.getTodate() + "') ";
        }
        try {
            query3 = query2;
            query2 += " order by  billDetails.bs_id desc ";
            System.out.println("CalSumQuery2" + CalSumQuery2);
            doctorTotal = (Double) entityManager.createNativeQuery(CalSumQuery2).getSingleResult();
            System.out.println("doctor Query" + CalSumQuery2);
            String CalSumQuery1 = " select sum(billDetails.serviceAmount),sum(billDetails.bs_gross_rate) " + query3;
            List<Object[]> lisCout = entityManager.createNativeQuery(CalSumQuery1).getResultList();
            String columnName = "bsId,opClassName,billNo,billDate,mrNo,patientName,bsServiceId,serviceName,serviceAmount,bs_gross_rate,doctorName,userName,sdid,unitName,unitId";
            jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, marege + query2, countQuery));
            MstUnit HeaderObject = mstUnitRepository.findByUnitId(consultantreferallSearchDTO.getUnitId());
            MstUser HeaderObjectUser = mstUserRepository.findbyUserID(consultantreferallSearchDTO.getUserId());
            JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
            JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
            if (jsonArray != null) {
                for (Object[] obj : lisCout) {
                    jsonArray.getJSONObject(0).put("serviceTotal", Double.parseDouble(obj[0].toString()));
                    jsonArray.getJSONObject(0).put("hospitalTotal", Double.parseDouble(obj[1].toString()) - doctorTotal);
                }
                jsonArray.getJSONObject(0).put("doctorTotal", doctorTotal);
                jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
                jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonArray.getJSONObject(i).put("doctorAmount", getCalculateDctoreShare(tenantName, Long.parseLong(jsonArray.getJSONObject(i).get("sdid").toString()), Long.parseLong(jsonArray.getJSONObject(i).get("bsServiceId").toString()), Long.parseLong(jsonArray.getJSONObject(i).get("unitId").toString()), jsonArray.getJSONObject(i).get("bs_gross_rate").toString()));
                jsonArray.getJSONObject(i).put("hospitalAmount", String.valueOf(Math.round(Double.parseDouble(jsonArray.getJSONObject(i).get("bs_gross_rate").toString()) - (jsonArray.getJSONObject(i).get("doctorAmount").toString() != null ? Double.parseDouble(jsonArray.getJSONObject(i).get("doctorAmount").toString()) : 0))));
                jsonArray.getJSONObject(i).put("showType", consultantreferallSearchDTO.getShowtype());
            }
            if (consultantreferallSearchDTO.getPrint()) {
                columnName = "bsId,opClassName,billNo,billDate,mrNo,patientName,bsServiceId,serviceName,serviceAmount,bs_gross_rate,doctorName,userName,sdid,unitName,unitId,doctorAmount,hospitalAmount";
                return createReport.generateExcel(columnName, "ConsultantReferalReport", jsonArray);
            } else {
                return createReport.createJasperReportPDFByJson("ConsultantReferalReport", jsonArray.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @RequestMapping("searchConsoltantIpd/{unitList}")
    public List<ConsultantreferralListDTO> searchConsoltantIpd(@RequestHeader("X-tenantId") String tenantName, @RequestBody ConsultantreferallSearchDTO consultantreferallSearchDTO, @PathVariable String[] unitList) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Double doctorTotal;
        Double serviceTotal;
        Double hospitalTotal;
        List<ConsultantreferralListDTO> consultantreferrallistDTOList = new ArrayList<ConsultantreferralListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String query3 = "";
        String merge = "select billDetails.bs_id,billDetails.class_name,billDetails.bill_number,billDetails.bill_date," + "billDetails.patient_mr_no,billDetails.patientName,billDetails.bs_service_id,billDetails.service_name," + " ifnull(billDetails.serviceAmount,0), ifnull(billDetails.bs_co_pay_qty_rate,0),ifnull(doctorStaff.doctorName,'') as doctorName,userNameTB.user_name,doctorStaff.sdid,billDetails.unit_name,billDetails.unit_id  ";
        String queryForIpd = " from (select s.bs_id,c.class_name,t.bill_number,t.bill_date,p.patient_mr_no,concat(u.user_firstname,' ',u.user_lastname) as patientName," + "ss.service_name,(s.bs_co_pay_qty_rate*s.bs_quantity) as serviceAmount,s.bs_co_pay_qty_rate,s.bs_service_id,un.unit_name,un.unit_id " + "from tbill_bill_service s left join mbill_service ss on s.bs_service_id=ss.service_id " + "left join tbill_bill t on s.bs_bill_id=t.bill_id " + "left join mst_unit un on un.unit_id=t.tbill_unit_id, " + " trn_admission ta  " + " left join mst_patient p on  ta.admission_patient_id=p.patient_id " + "left join mst_user u on p.patient_user_id=u.user_id, mst_class c " + " where  ta.admission_id=t.bill_admission_id and t.bill_class_id=c.class_id) as billDetails left join " + " (select s1.bs_id,st1.staff_user_id,st1.staff_id  as sdid,u1.user_fullname as doctorName from tbill_bill_service s1 left join mst_staff st1 on s1.bs_staff_id=st1.staff_id " + " left join mst_user u1 on st1.staff_user_id=u1.user_id) as doctorStaff " + " on billDetails.bs_id=doctorStaff.bs_id left join " + "(select s.bs_id,st.staff_id,u.user_name from tbill_bill_service s left join " + " tbill_bill t on s.bs_bill_id=t.bill_id left join mst_staff st on t.bill_user_id=st.staff_id " + " left join mst_user u on st.staff_user_id=u.user_id ) as userNameTB " + " on billDetails.bs_id=userNameTB.bs_id ";
        String countIpdQuery = "select count(billDetails.bs_id) from " + "(select s.bs_id,c.class_name,t.bill_number,t.bill_date,p.patient_mr_no,concat(u.user_firstname,' ',u.user_lastname) as patientName," + "ss.service_name,s.bs_co_pay_qty_rate as serviceAmount,s.bs_co_pay_qty_rate,s.bs_service_id ,un.unit_name,un.unit_id " + " from tbill_bill_service s left join mbill_service ss on s.bs_service_id=ss.service_id " + "left join tbill_bill t on s.bs_bill_id=t.bill_id " + "left join mst_unit un on un.unit_id=t.tbill_unit_id ," + "  trn_admission ta " + " left join mst_patient p on  ta.admission_patient_id=p.patient_id " + "left join  mst_user u on p.patient_user_id=u.user_id," + "  mst_class c " + " where  ta.admission_id=t.bill_admission_id  and t.bill_class_id=c.class_id) as billDetails left join " + " " + " (select s1.bs_id,st1.staff_user_id,st1.staff_id  as sdid,u1.user_fullname as doctorName from tbill_bill_service s1 left join mst_staff st1 on s1.bs_staff_id=st1.staff_id " + " left join mst_user u1 on st1.staff_user_id=u1.user_id) as doctorStaff " + " on billDetails.bs_id=doctorStaff.bs_id left join " + " " + "(select s.bs_id,st.staff_id,u.user_name from tbill_bill_service s left join " + " tbill_bill t on s.bs_bill_id=t.bill_id left join mst_staff st on t.bill_user_id=st.staff_id " + " left join mst_user u on st.staff_user_id=u.user_id ) as userNameTB " + " on billDetails.bs_id=userNameTB.bs_id ";
        // String CalSumQuery1 = " select ifNull(sum(ifnull(s.bs_base_rate,0)),0) as  serviceRate , ifNull(sum(ifnull(s.bs_qty_rate,0)),0) as hospitalRate " + " from tbill_bill_service s left join tbill_bill t on s.bs_bill_id=t.bill_id left join mst_visit v on t.bill_visit_id=v.visit_id left join mst_patient p on  v.visit_patient_id=p.patient_id " + " left join  mst_user u on p.patient_user_id=u.user_id " + " where   s.is_active=1 and s.is_deleted=0 and s.bs_cancel=0";
        String CalSumQuery2 = "select ifNull(sum(IF(d.doctor_share_amount>0, d.doctor_share_amount, (ifnull(s.bs_co_pay_qty_rate,0)/100)*d.doctor_share_per)),0) as doctorAmount from mbill_doctor_share d " + "inner join tbill_bill_service s on s.bs_service_id=d.ds_serviceid " + "inner join tbill_bill t on s.bs_bill_id=t.bill_id inner join mst_visit v on t.bill_visit_id=v.visit_id inner join mst_patient p on  v.visit_patient_id=p.patient_id " + "inner join  mst_user u on p.patient_user_id=u.user_id " + "where d.ds_staff_id=s.bs_staff_id and s.is_active=1 and s.is_deleted=0 and s.bs_cancel=0";
        if (!consultantreferallSearchDTO.getPatientName().equals("")) {
            queryForIpd += " where  billDetails.patientName like '%" + consultantreferallSearchDTO.getPatientName() + "%' ";
            countIpdQuery += " where  billDetails.patientName like '%" + consultantreferallSearchDTO.getPatientName() + "%' ";
            // CalSumQuery1 += " and (u.user_firstname like '%" + consultantreferallSearchDTO.getPatientName() + "%' or u.user_lastname like '%" + consultantreferallSearchDTO.getPatientName() + "%' ) ";
            CalSumQuery2 += " and (u.user_firstname like '%" + consultantreferallSearchDTO.getPatientName() + "%' or u.user_lastname like '%" + consultantreferallSearchDTO.getPatientName() + "%' ) ";
        } else {
            queryForIpd += " where  billDetails.patientName  like '%%' ";
            countIpdQuery += " where billDetails.patientName  like '%%' ";
        }
        if (consultantreferallSearchDTO.getFromdate().equals("") || consultantreferallSearchDTO.getFromdate().equals("null")) {
            consultantreferallSearchDTO.setFromdate("1990-06-07");
        }
        if (consultantreferallSearchDTO.getTodate().equals("") || consultantreferallSearchDTO.getTodate().equals("null")) {
            consultantreferallSearchDTO.setTodate(strDate);
        }
        if (!String.valueOf(consultantreferallSearchDTO.getUserId()).equals("0")) {
            queryForIpd += "  and userNameTB.staff_id=" + consultantreferallSearchDTO.getUserId() + " ";
            countIpdQuery += "  and userNameTB.staff_id=" + consultantreferallSearchDTO.getUserId() + " ";
            //  CalSumQuery1 += " and s.bs_staff_id=" + consultantreferallSearchDTO.getUserId() + " ";
            CalSumQuery2 += " and s.bs_staff_id=" + consultantreferallSearchDTO.getUserId() + " ";
        }
        if (!String.valueOf(consultantreferallSearchDTO.getDoctorId()).equals("0")) {
            queryForIpd += " and doctorStaff.sdid=" + consultantreferallSearchDTO.getDoctorId() + " ";
            countIpdQuery += " and doctorStaff.sdid=" + consultantreferallSearchDTO.getDoctorId() + " ";
            // CalSumQuery1 += " and s.bs_staff_id=" + consultantreferallSearchDTO.getDoctorId() + " ";
            CalSumQuery2 += " and s.bs_staff_id=" + consultantreferallSearchDTO.getDoctorId() + " ";
        }
        if (!consultantreferallSearchDTO.getMrNo().equals("")) {
            queryForIpd += " and  billDetails.patient_mr_no like  '%" + consultantreferallSearchDTO.getMrNo() + "%' ";
            countIpdQuery += " and  billDetails.patient_mr_no like '%" + consultantreferallSearchDTO.getMrNo() + "%' ";
            // CalSumQuery1 += " and p.patient_mr_no like '%" + consultantreferallSearchDTO.getMrNo() + "%' ";
            CalSumQuery2 += " and p.patient_mr_no like '%" + consultantreferallSearchDTO.getMrNo() + "%' ";
        }
        if (!String.valueOf(consultantreferallSearchDTO.getServiceId()).equals("0")) {
            queryForIpd += " and billDetails.bs_service_id=" + consultantreferallSearchDTO.getServiceId() + " ";
            countIpdQuery += " and billDetails.bs_service_id=" + consultantreferallSearchDTO.getServiceId() + " ";
            //  CalSumQuery1 += " and s.bs_service_id=" + consultantreferallSearchDTO.getServiceId() + " ";
            CalSumQuery2 += " and s.bs_service_id=" + consultantreferallSearchDTO.getServiceId() + " ";
        }
//        if (!String.valueOf(consultantreferallSearchDTO.getUnitId()).equals("0")) {
//            queryForIpd += " and billDetails.unit_id=" + consultantreferallSearchDTO.getUnitId() + " ";
//            countIpdQuery += " and billDetails.unit_id=" + consultantreferallSearchDTO.getUnitId() + " ";
////            CalSumQuery1 += " and s.bs_service_id=" + consultantreferallSearchDTO.getUnitId() + " ";
////            CalSumQuery2 += " and s.bs_service_id=" + consultantreferallSearchDTO.getUnitId() + " ";
//            CalSumQuery1 += " and t.tbill_unit_id=" + consultantreferallSearchDTO.getUnitId() + " ";
//            CalSumQuery2 += " and d.ds_unit_id=" + consultantreferallSearchDTO.getUnitId() + " ";
//        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            queryForIpd += " and billDetails.unit_id in (" + values + ")";
            countIpdQuery += " and billDetails.unit_id in (" + values + ")";
            // CalSumQuery1 += " and t.tbill_unit_id in (" + values + ")";
            CalSumQuery2 += " and d.ds_unit_id in (" + values + ")";
        }
        if (consultantreferallSearchDTO.getFromdate().equals("")) {
            consultantreferallSearchDTO.setFromdate("1990-06-07");
        }
        if (consultantreferallSearchDTO.getTodate().equals("")) {
            consultantreferallSearchDTO.setTodate(strDate);
        }
        if (consultantreferallSearchDTO.getTodaydate()) {
            queryForIpd += "  and date(billDetails.bill_date)=curdate() ";
            countIpdQuery += "  and date(billDetails.bill_date)=curdate() ";
            //  CalSumQuery1 += "  and date(t.bill_date)='" + strDate + "' ";
            CalSumQuery2 += "  and date(t.bill_date)='" + strDate + "' ";
        } else {
            queryForIpd += " and  (date(billDetails.bill_date) between '" + consultantreferallSearchDTO.getFromdate() + "' and '" + consultantreferallSearchDTO.getTodate() + "') ";
            countIpdQuery += " and  (date(billDetails.bill_date) between '" + consultantreferallSearchDTO.getFromdate() + "' and '" + consultantreferallSearchDTO.getTodate() + "') ";
            // CalSumQuery1 += " and (date(t.bill_date) between '" + consultantreferallSearchDTO.getFromdate() + "' and '" + consultantreferallSearchDTO.getTodate() + "') ";
            CalSumQuery2 += " and (date(t.bill_date) between '" + consultantreferallSearchDTO.getFromdate() + "' and '" + consultantreferallSearchDTO.getTodate() + "') ";
        }
        try {
            query3 = queryForIpd;
            queryForIpd += " limit " + ((consultantreferallSearchDTO.getOffset() - 1) * consultantreferallSearchDTO.getLimit()) + "," + consultantreferallSearchDTO.getLimit();
            // System.out.println("CalSumQuery1" + CalSumQuery1);
            System.out.println("CalSumQuery2" + CalSumQuery2);
            System.out.println("Main1 Query" + queryForIpd);
            List<Object[]> listTBillBillIpdService = entityManager.createNativeQuery(merge + queryForIpd).getResultList();
            //  BigInteger cc = (BigInteger) entityManager.createNativeQuery(countQuery).getSingleResult();
            BigInteger dd = (BigInteger) entityManager.createNativeQuery(countIpdQuery).getSingleResult();
            count = dd.longValue();  //cc.longValue() +
            for (Object[] obj : listTBillBillIpdService) {
                ConsultantreferralListDTO objconsultantreferrallistDTO = new ConsultantreferralListDTO();
                objconsultantreferrallistDTO.setOpClassName("" + obj[1]);
                objconsultantreferrallistDTO.setBillNo("" + obj[2]);
                objconsultantreferrallistDTO.setBillDate("" + obj[3]);
                objconsultantreferrallistDTO.setMrNo("" + obj[4]);
                objconsultantreferrallistDTO.setPatientName("" + obj[5]);
                objconsultantreferrallistDTO.setBsServiceId(Long.parseLong("" + obj[6]));
                objconsultantreferrallistDTO.setServiceName("" + obj[7]);
                objconsultantreferrallistDTO.setDoctorName("" + obj[10]);
                objconsultantreferrallistDTO.setUserName("" + obj[11]);
                objconsultantreferrallistDTO.setServiceAmount("" + obj[8]);
                objconsultantreferrallistDTO.setBsStaffId(Long.parseLong("" + obj[12]));
                objconsultantreferrallistDTO.setUnitName("" + obj[13]);
                objconsultantreferrallistDTO.setUnitId(Long.parseLong("" + obj[14]));
                objconsultantreferrallistDTO.setCount(count);
                objconsultantreferrallistDTO.setDoctorAmount(getCalculateDctoreShare(tenantName, objconsultantreferrallistDTO.getBsStaffId(), objconsultantreferrallistDTO.getBsServiceId(), objconsultantreferrallistDTO.getUnitId(), "" + obj[9]));
                objconsultantreferrallistDTO.setHospitalAmount(String.valueOf(Math.round(Double.parseDouble(obj[9].toString()) - (objconsultantreferrallistDTO.getDoctorAmount() != null ? objconsultantreferrallistDTO.getDoctorAmount() : 0))));
                consultantreferrallistDTOList.add(objconsultantreferrallistDTO);

            }
            doctorTotal = (Double) entityManager.createNativeQuery(CalSumQuery2).getSingleResult();
            System.out.println("doctor Query" + CalSumQuery2);
            String CalSumQuery1 = " select sum(billDetails.serviceAmount),sum(billDetails.bs_co_pay_qty_rate) " + query3;
            List<Object[]> lisCout = entityManager.createNativeQuery(CalSumQuery1).getResultList();
            System.out.println("other Query" + CalSumQuery1);
            if (consultantreferrallistDTOList.size() > 0) {
                for (Object[] obj : lisCout) {
                    consultantreferrallistDTOList.get(0).setServiceTotal(Double.parseDouble(obj[0].toString()));
                    consultantreferrallistDTOList.get(0).setHospitalTotal(Double.parseDouble(obj[1].toString()) - doctorTotal);
                }
                consultantreferrallistDTOList.get(0).setDoctorTotal(doctorTotal);

            }

        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("Error:" + e);
            return null;
        }
        return consultantreferrallistDTOList;

    }

    @RequestMapping("searchConsoltantIpdPrint/{unitList}")
    public String searchConsoltantIpdPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody ConsultantreferallSearchDTO consultantreferallSearchDTO, @PathVariable String[] unitList) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Double doctorTotal;
        Double serviceTotal;
        Double hospitalTotal;
        List<ConsultantreferralListDTO> consultantreferrallistDTOList = new ArrayList<ConsultantreferralListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String query3 = "";
        String merge = "select billDetails.bs_id,billDetails.class_name,billDetails.bill_number,billDetails.bill_date," + "billDetails.patient_mr_no,billDetails.patientName,billDetails.bs_service_id,billDetails.service_name," + " ifnull(billDetails.serviceAmount,0), ifnull(billDetails.bs_co_pay_qty_rate,0),ifnull(doctorStaff.doctorName,'') as doctorName,userNameTB.user_name,doctorStaff.sdid,billDetails.unit_name,billDetails.unit_id  ";
        String queryForIpd = " from (select s.bs_id,c.class_name,t.bill_number,t.bill_date,p.patient_mr_no,concat(u.user_firstname,' ',u.user_lastname) as patientName," + "ss.service_name,(s.bs_co_pay_qty_rate*s.bs_quantity) as serviceAmount,s.bs_co_pay_qty_rate,s.bs_service_id,un.unit_name,un.unit_id " + "from tbill_bill_service s left join mbill_service ss on s.bs_service_id=ss.service_id " + "left join tbill_bill t on s.bs_bill_id=t.bill_id " + "left join mst_unit un on un.unit_id=t.tbill_unit_id, " + " trn_admission ta  " + " left join mst_patient p on  ta.admission_patient_id=p.patient_id " + "left join mst_user u on p.patient_user_id=u.user_id, mst_class c " + " where  ta.admission_id=t.bill_admission_id and t.bill_class_id=c.class_id) as billDetails left join " + " (select s1.bs_id,st1.staff_user_id,st1.staff_id  as sdid,u1.user_fullname as doctorName from tbill_bill_service s1 left join mst_staff st1 on s1.bs_staff_id=st1.staff_id " + " left join mst_user u1 on st1.staff_user_id=u1.user_id) as doctorStaff " + " on billDetails.bs_id=doctorStaff.bs_id left join " + "(select s.bs_id,st.staff_id,u.user_name from tbill_bill_service s left join " + " tbill_bill t on s.bs_bill_id=t.bill_id left join mst_staff st on t.bill_user_id=st.staff_id " + " left join mst_user u on st.staff_user_id=u.user_id ) as userNameTB " + " on billDetails.bs_id=userNameTB.bs_id ";
        String countIpdQuery = "select count(billDetails.bs_id) from " + "(select s.bs_id,c.class_name,t.bill_number,t.bill_date,p.patient_mr_no,concat(u.user_firstname,' ',u.user_lastname) as patientName," + "ss.service_name,s.bs_co_pay_qty_rate as serviceAmount,s.bs_co_pay_qty_rate,s.bs_service_id ,un.unit_name,un.unit_id " + " from tbill_bill_service s left join mbill_service ss on s.bs_service_id=ss.service_id " + "left join tbill_bill t on s.bs_bill_id=t.bill_id " + "left join mst_unit un on un.unit_id=t.tbill_unit_id ," + "  trn_admission ta " + " left join mst_patient p on  ta.admission_patient_id=p.patient_id " + "left join  mst_user u on p.patient_user_id=u.user_id," + "  mst_class c " + " where  ta.admission_id=t.bill_admission_id  and t.bill_class_id=c.class_id) as billDetails left join " + " " + " (select s1.bs_id,st1.staff_user_id,st1.staff_id  as sdid,u1.user_fullname as doctorName from tbill_bill_service s1 left join mst_staff st1 on s1.bs_staff_id=st1.staff_id " + " left join mst_user u1 on st1.staff_user_id=u1.user_id) as doctorStaff " + " on billDetails.bs_id=doctorStaff.bs_id left join " + " " + "(select s.bs_id,st.staff_id,u.user_name from tbill_bill_service s left join " + " tbill_bill t on s.bs_bill_id=t.bill_id left join mst_staff st on t.bill_user_id=st.staff_id " + " left join mst_user u on st.staff_user_id=u.user_id ) as userNameTB " + " on billDetails.bs_id=userNameTB.bs_id ";
        // String CalSumQuery1 = " select ifNull(sum(ifnull(s.bs_base_rate,0)),0) as  serviceRate , ifNull(sum(ifnull(s.bs_qty_rate,0)),0) as hospitalRate " + " from tbill_bill_service s left join tbill_bill t on s.bs_bill_id=t.bill_id left join mst_visit v on t.bill_visit_id=v.visit_id left join mst_patient p on  v.visit_patient_id=p.patient_id " + " left join  mst_user u on p.patient_user_id=u.user_id " + " where   s.is_active=1 and s.is_deleted=0 and s.bs_cancel=0";
        String CalSumQuery2 = "select ifNull(sum(IF(d.doctor_share_amount>0, d.doctor_share_amount, (ifnull(s.bs_co_pay_qty_rate,0)/100)*d.doctor_share_per)),0) as doctorAmount from mbill_doctor_share d " + "inner join tbill_bill_service s on s.bs_service_id=d.ds_serviceid " + "inner join tbill_bill t on s.bs_bill_id=t.bill_id inner join mst_visit v on t.bill_visit_id=v.visit_id inner join mst_patient p on  v.visit_patient_id=p.patient_id " + "inner join  mst_user u on p.patient_user_id=u.user_id " + "where d.ds_staff_id=s.bs_staff_id and s.is_active=1 and s.is_deleted=0 and s.bs_cancel=0";
        if (!consultantreferallSearchDTO.getPatientName().equals("")) {
            queryForIpd += " where  billDetails.patientName like '%" + consultantreferallSearchDTO.getPatientName() + "%' ";
            countIpdQuery += " where  billDetails.patientName like '%" + consultantreferallSearchDTO.getPatientName() + "%' ";
            // CalSumQuery1 += " and (u.user_firstname like '%" + consultantreferallSearchDTO.getPatientName() + "%' or u.user_lastname like '%" + consultantreferallSearchDTO.getPatientName() + "%' ) ";
            CalSumQuery2 += " and (u.user_firstname like '%" + consultantreferallSearchDTO.getPatientName() + "%' or u.user_lastname like '%" + consultantreferallSearchDTO.getPatientName() + "%' ) ";
        } else {
            queryForIpd += " where  billDetails.patientName  like '%%' ";
            countIpdQuery += " where billDetails.patientName  like '%%' ";
        }
        if (consultantreferallSearchDTO.getFromdate().equals("") || consultantreferallSearchDTO.getFromdate().equals("null")) {
            consultantreferallSearchDTO.setFromdate("1990-06-07");
        }
        if (consultantreferallSearchDTO.getTodate().equals("") || consultantreferallSearchDTO.getTodate().equals("null")) {
            consultantreferallSearchDTO.setTodate(strDate);
        }
        if (!String.valueOf(consultantreferallSearchDTO.getUserId()).equals("0")) {
            queryForIpd += "  and userNameTB.staff_id=" + consultantreferallSearchDTO.getUserId() + " ";
            countIpdQuery += "  and userNameTB.staff_id=" + consultantreferallSearchDTO.getUserId() + " ";
            //  CalSumQuery1 += " and s.bs_staff_id=" + consultantreferallSearchDTO.getUserId() + " ";
            CalSumQuery2 += " and s.bs_staff_id=" + consultantreferallSearchDTO.getUserId() + " ";
        }
        if (!String.valueOf(consultantreferallSearchDTO.getDoctorId()).equals("0")) {
            queryForIpd += " and doctorStaff.sdid=" + consultantreferallSearchDTO.getDoctorId() + " ";
            countIpdQuery += " and doctorStaff.sdid=" + consultantreferallSearchDTO.getDoctorId() + " ";
            // CalSumQuery1 += " and s.bs_staff_id=" + consultantreferallSearchDTO.getDoctorId() + " ";
            CalSumQuery2 += " and s.bs_staff_id=" + consultantreferallSearchDTO.getDoctorId() + " ";
        }
        if (!consultantreferallSearchDTO.getMrNo().equals("")) {
            queryForIpd += " and  billDetails.patient_mr_no like  '%" + consultantreferallSearchDTO.getMrNo() + "%' ";
            countIpdQuery += " and  billDetails.patient_mr_no like '%" + consultantreferallSearchDTO.getMrNo() + "%' ";
            // CalSumQuery1 += " and p.patient_mr_no like '%" + consultantreferallSearchDTO.getMrNo() + "%' ";
            CalSumQuery2 += " and p.patient_mr_no like '%" + consultantreferallSearchDTO.getMrNo() + "%' ";
        }
        if (!String.valueOf(consultantreferallSearchDTO.getServiceId()).equals("0")) {
            queryForIpd += " and billDetails.bs_service_id=" + consultantreferallSearchDTO.getServiceId() + " ";
            countIpdQuery += " and billDetails.bs_service_id=" + consultantreferallSearchDTO.getServiceId() + " ";
            //  CalSumQuery1 += " and s.bs_service_id=" + consultantreferallSearchDTO.getServiceId() + " ";
            CalSumQuery2 += " and s.bs_service_id=" + consultantreferallSearchDTO.getServiceId() + " ";
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            queryForIpd += " and billDetails.unit_id in (" + values + ")";
            countIpdQuery += " and billDetails.unit_id in (" + values + ")";
            // CalSumQuery1 += " and t.tbill_unit_id in (" + values + ")";
            CalSumQuery2 += " and d.ds_unit_id in (" + values + ")";
        }
        if (consultantreferallSearchDTO.getFromdate().equals("")) {
            consultantreferallSearchDTO.setFromdate("1990-06-07");
        }
        if (consultantreferallSearchDTO.getTodate().equals("")) {
            consultantreferallSearchDTO.setTodate(strDate);
        }
        if (consultantreferallSearchDTO.getTodaydate()) {
            queryForIpd += "  and date(billDetails.bill_date)=curdate() ";
            countIpdQuery += "  and date(billDetails.bill_date)=curdate() ";
            //  CalSumQuery1 += "  and date(t.bill_date)='" + strDate + "' ";
            CalSumQuery2 += "  and date(t.bill_date)='" + strDate + "' ";
        } else {
            queryForIpd += " and  (date(billDetails.bill_date) between '" + consultantreferallSearchDTO.getFromdate() + "' and '" + consultantreferallSearchDTO.getTodate() + "') ";
            countIpdQuery += " and  (date(billDetails.bill_date) between '" + consultantreferallSearchDTO.getFromdate() + "' and '" + consultantreferallSearchDTO.getTodate() + "') ";
            // CalSumQuery1 += " and (date(t.bill_date) between '" + consultantreferallSearchDTO.getFromdate() + "' and '" + consultantreferallSearchDTO.getTodate() + "') ";
            CalSumQuery2 += " and (date(t.bill_date) between '" + consultantreferallSearchDTO.getFromdate() + "' and '" + consultantreferallSearchDTO.getTodate() + "') ";
        }
        try {
            query3 = queryForIpd;
            //  queryForIpd += " limit " + ((consultantreferallSearchDTO.getOffset() - 1) * consultantreferallSearchDTO.getLimit()) + "," + consultantreferallSearchDTO.getLimit();
            // System.out.println("CalSumQuery1" + CalSumQuery1);
            System.out.println("CalSumQuery2" + CalSumQuery2);
            System.out.println("Main1 Query" + queryForIpd);
            List<Object[]> listTBillBillIpdService = entityManager.createNativeQuery(merge + queryForIpd).getResultList();
            //  BigInteger cc = (BigInteger) entityManager.createNativeQuery(countQuery).getSingleResult();
            //  BigInteger dd = (BigInteger) entityManager.createNativeQuery(countIpdQuery).getSingleResult();
            //  count = dd.longValue();  //cc.longValue() +
            for (Object[] obj : listTBillBillIpdService) {
                ConsultantreferralListDTO objconsultantreferrallistDTO = new ConsultantreferralListDTO();
                objconsultantreferrallistDTO.setOpClassName("" + obj[1]);
                objconsultantreferrallistDTO.setBillNo("" + obj[2]);
                objconsultantreferrallistDTO.setBillDate(obj[3].toString());
                objconsultantreferrallistDTO.setMrNo("" + obj[4]);
                objconsultantreferrallistDTO.setPatientName("" + obj[5]);
                objconsultantreferrallistDTO.setBsServiceId(Long.parseLong("" + obj[6]));
                objconsultantreferrallistDTO.setServiceName("" + obj[7]);
                objconsultantreferrallistDTO.setDoctorName("" + obj[10]);
                objconsultantreferrallistDTO.setUserName("" + obj[11]);
                objconsultantreferrallistDTO.setServiceAmount("" + obj[8]);
                objconsultantreferrallistDTO.setBsStaffId(Long.parseLong("" + obj[12]));
                objconsultantreferrallistDTO.setUnitName("" + obj[13]);
                objconsultantreferrallistDTO.setUnitId(Long.parseLong("" + obj[14]));
                objconsultantreferrallistDTO.setCount(count);
                objconsultantreferrallistDTO.setDoctorAmount(getCalculateDctoreShare(tenantName, objconsultantreferrallistDTO.getBsStaffId(), objconsultantreferrallistDTO.getBsServiceId(), objconsultantreferrallistDTO.getUnitId(), "" + obj[9]));
                objconsultantreferrallistDTO.setHospitalAmount(String.valueOf(Math.round(Double.parseDouble(obj[9].toString()) - (objconsultantreferrallistDTO.getDoctorAmount() != null ? objconsultantreferrallistDTO.getDoctorAmount() : 0))));
                objconsultantreferrallistDTO.setShowType(consultantreferallSearchDTO.getShowtype());
                consultantreferrallistDTOList.add(objconsultantreferrallistDTO);

            }
            doctorTotal = (Double) entityManager.createNativeQuery(CalSumQuery2).getSingleResult();
            System.out.println("doctor Query" + CalSumQuery2);
            String CalSumQuery1 = " select sum(billDetails.serviceAmount),sum(billDetails.bs_co_pay_qty_rate) " + query3;
            List<Object[]> lisCout = entityManager.createNativeQuery(CalSumQuery1).getResultList();
            System.out.println("other Query" + CalSumQuery1);
            if (consultantreferrallistDTOList.size() > 0) {
                for (Object[] obj : lisCout) {
                    consultantreferrallistDTOList.get(0).setServiceTotal(Double.parseDouble(obj[0].toString()));
                    consultantreferrallistDTOList.get(0).setHospitalTotal(Double.parseDouble(obj[1].toString()) - doctorTotal);
                }
                consultantreferrallistDTOList.get(0).setDoctorTotal(doctorTotal);

            }

        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("Error:" + e);
            return null;
        }
        // return consultantreferrallistDTOList;
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(consultantreferallSearchDTO.getUnitId());
        consultantreferrallistDTOList.get(0).setObjHeaderData(HeaderObject);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(consultantreferrallistDTOList);
        // return createReport.createJasperReportXLS("OutwardReferralReport", ds);
        if (consultantreferallSearchDTO.getPrint()) {
            return createReport.createJasperReportXLS("ConsultantReferalReport", ds);
        } else {
            return createReport.createJasperReportPDF("ConsultantReferalReport", ds);
        }

    }

    @RequestMapping("mis_billing_report/searchConsoltantOpdgetDoctorShareById/{staffId}/{serviceId}/{unitId}")
    public MbillDoctorShare getActivityPatientCount(@RequestHeader("X-tenantId") String tenantName, @PathVariable("staffId") long staffId, @PathVariable("serviceId") long serviceId, @PathVariable("unitId") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        MbillDoctorShare obj = new MbillDoctorShare();
        try {
            obj = entityManager.createQuery("select p from MbillDoctorShare p where p.dsStaffId.staffId=" + staffId + " and p.dsServiceid.serviceId=" + serviceId + " and p.dsUnitId.unitId=" + unitId + "", MbillDoctorShare.class).getSingleResult();

        } catch (NoResultException e) {
            System.out.println(e);

        }
        if (obj != null) {
            return obj;
        } else {
            return null;
        }

    }

    public Double getCalculateDctoreShare(@RequestHeader("X-tenantId") String tenantName, long staffId, long serviceId, long unitId, String hamt) {
        TenantContext.setCurrentTenant(tenantName);
        MbillDoctorShare obj = new MbillDoctorShare();
        Double doctorShare = 0d;
        try {
            if (hamt == null) {
                hamt = "0";
            }
            obj = entityManager.createQuery("select p from MbillDoctorShare p where p.dsStaffId.staffId=" + staffId + " and p.dsServiceid.serviceId=" + serviceId + " and p.dsUnitId.unitId=" + unitId + "", MbillDoctorShare.class).getSingleResult();
            if (obj != null) {
                if (obj.getDoctorShareAmount() == 0) {
                    doctorShare = ((Double.parseDouble(hamt) / 100) * obj.getDoctorSharePer());
                } else {
                    doctorShare = obj.getDoctorShareAmount();
                }
            } else {
                doctorShare = 0d;
            }
            return doctorShare;
        } catch (NoResultException e) {
            System.out.println(e);
            return doctorShare;
        }
    }

    @RequestMapping("getTarifServiceRateListReport/{unitList}/{serviceId}/{tariffId}/{classId}/{fromdate}/{todate}")
    public ResponseEntity searchTarifServiceRateListReport(@RequestHeader("X-tenantId") String tenantName,
                                                           @RequestBody TarifServiceWiseRateListSearchDTO tarifServicewiseRateListSearchDTO, @PathVariable String[] unitList,
                                                           @PathVariable String serviceId, @PathVariable String tariffId, @PathVariable String classId,
                                                           @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = " ";
        String CountQuery = "";
        String columnName = "";
        MainQuery += " SELECT mu.unit_name AS UnitName,mt.tariff_name AS TariffName,mg.group_name AS GroupName,msg.sg_name AS SubGroupName, " +
                "ms.service_name AS ServiceName,mc.class_name AS Class,mtsc.company_pay AS ComPay,mtsc.patient_pay AS PatientPay  " +
                "FROM ts_tariff_service_class tsc   " +
                "inner join mbill_tariff_service ts on tsc.mbill_tariff_service_ts_id = ts.ts_id " +
                "inner join mbill_tariff_service_class mtsc on tsc.mbill_tariff_service_ts_id = mtsc.tsc_id " +
                "inner join mbill_tariff mt on ts.ts_tariff_id = mt.tariff_id " +
                "inner join mst_unit mu on mt.tariffunit_id = mu.unit_id " +
                "inner join mbill_service ms on ts.ts_service_id = ms.service_id " +
                "inner join mst_class mc on mtsc.tsc_class_id = mc.class_id " +
                "inner join mbill_group mg on ms.service_group_id = mg.group_id " +
                "inner join mbill_sub_group msg on ms.service_sg_id = msg.sg_id " +
                "where ts.is_active=1 and ts.is_deleted=0 ";
        if (fromdate.equals("") || fromdate.equals("null") || fromdate.equals("undefined")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null") || todate.equals("undefined")) {
            todate = strDate;
        }
        if (tarifServicewiseRateListSearchDTO.getTodaydate()) {
            MainQuery += " and (date(ts.created_date)=curdate()) ";
        } else {
            MainQuery += " and (date(ts.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        // Unit Name
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 0; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            MainQuery += " and mt.tariffunit_id in (" + values + ") ";
        }
        // Service Name or Code
        if (!serviceId.equals("null") && !serviceId.equals("0")) {
            MainQuery += " AND ts.ts_service_id=  " + serviceId + " ";
        }
        // Tarrif Name
        if (!tariffId.equals("null") && !tariffId.equals("0")) {
            MainQuery += " AND ts.ts_tariff_id=  " + tariffId + " ";
        }
        // Class Name
        if (!classId.equals("null") && !classId.equals("0")) {
            MainQuery += " AND mtsc.tsc_class_id=  " + classId + " ";
        }
        // Group Name
        if (tarifServicewiseRateListSearchDTO.getGroupId() != 0) {
            MainQuery += " and ms.service_group_id = " + tarifServicewiseRateListSearchDTO.getGroupId() + " ";
        }
        // Sub Group Name
        if (tarifServicewiseRateListSearchDTO.getSubGroupId() != 0) {
            MainQuery += " and ms.service_sg_id = " + tarifServicewiseRateListSearchDTO.getSubGroupId() + " ";
        }
        MainQuery += " GROUP BY ms.service_name ";
        System.out.println("Tarif Service Rate List"+ MainQuery);
        CountQuery = "select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "UnitName,TariffName,GroupName,SubGroupName,ServiceName,Class,ComPay,PatientPay";
        MainQuery += "limit "
                + ((tarifServicewiseRateListSearchDTO.getOffset() - 1) * tarifServicewiseRateListSearchDTO.getLimit()) + ","
                + tarifServicewiseRateListSearchDTO.getLimit();
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));

    }

    @RequestMapping("getTarifServiceRateListReportPrint/{unitList}/{serviceId}/{tariffId}/{classId}/{fromdate}/{todate}")
    public String searchTarifServiceRateListReportPrint(@RequestHeader("X-tenantId") String tenantName,
                                                        @RequestBody TarifServiceWiseRateListSearchDTO tarifServicewiseRateListSearchDTO, @PathVariable String[] unitList,
                                                        @PathVariable String serviceId, @PathVariable String tariffId, @PathVariable String classId,
                                                        @PathVariable String fromdate, @PathVariable String todate) throws JsonProcessingException {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String MainQuery = " ";
        String CountQuery = "";
        String columnName = "";
        MainQuery += " SELECT mu.unit_name AS UnitName,mt.tariff_name AS TariffName,mg.group_name AS GroupName,msg.sg_name AS SubGroupName, " +
                "ms.service_name AS ServiceName,mc.class_name AS Class,mtsc.company_pay AS ComPay,mtsc.patient_pay AS PatientPay  " +
                "FROM ts_tariff_service_class tsc   " +
                "inner join mbill_tariff_service ts on tsc.mbill_tariff_service_ts_id = ts.ts_id " +
                "inner join mbill_tariff_service_class mtsc on tsc.mbill_tariff_service_ts_id = mtsc.tsc_id " +
                "inner join mbill_tariff mt on ts.ts_tariff_id = mt.tariff_id " +
                "inner join mst_unit mu on mt.tariffunit_id = mu.unit_id " +
                "inner join mbill_service ms on ts.ts_service_id = ms.service_id " +
                "inner join mst_class mc on mtsc.tsc_class_id = mc.class_id " +
                "inner join mbill_group mg on ms.service_group_id = mg.group_id " +
                "inner join mbill_sub_group msg on ms.service_sg_id = msg.sg_id " +
                "where ts.is_active=1 and ts.is_deleted=0 ";
        if (fromdate.equals("") || fromdate.equals("null") || fromdate.equals("undefined")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null") || todate.equals("undefined")) {
            todate = strDate;
        }
        if (tarifServicewiseRateListSearchDTO.getTodaydate()) {
            MainQuery += " and (date(ts.created_date)=curdate()) ";
        } else {
            MainQuery += " and (date(ts.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        // Unit Name
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 0; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            MainQuery += " and mt.tariffunit_id in (" + values + ") ";
        }
        // Service Name or Code
        if (!serviceId.equals("null") && !serviceId.equals("0")) {
            MainQuery += " AND ts.ts_service_id=  " + serviceId + " ";
        }
        // Tarrif Name
        if (!tariffId.equals("null") && !tariffId.equals("0")) {
            MainQuery += " AND ts.ts_tariff_id=  " + tariffId + " ";
        }
        // Class Name
        if (!classId.equals("null") && !classId.equals("0")) {
            MainQuery += " AND mtsc.tsc_class_id=  " + classId + " ";
        }
        // Group Name
        if (tarifServicewiseRateListSearchDTO.getGroupId() != 0) {
            MainQuery += " and ms.service_group_id = " + tarifServicewiseRateListSearchDTO.getGroupId() + " ";
        }
        // Sub Group Name
        if (tarifServicewiseRateListSearchDTO.getSubGroupId() != 0) {
            MainQuery += " and ms.service_sg_id = " + tarifServicewiseRateListSearchDTO.getSubGroupId() + " ";
        }
        MainQuery += " GROUP BY ms.service_name ";
        CountQuery = "select count(*) from ( " + MainQuery + " ) as combine ";
        columnName = "UnitName,TariffName,GroupName,SubGroupName,ServiceName,Class,ComPay,PatientPay";
//        MainQuery += "limit "
//                + ((tarifServicewiseRateListSearchDTO.getOffset() - 1) * tarifServicewiseRateListSearchDTO.getLimit()) + ","
//                + tarifServicewiseRateListSearchDTO.getLimit();
        //  return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        JSONArray jsonArray = new JSONArray(createJSONObject.createJsonObjectWithCount(columnName, MainQuery, CountQuery));
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(tarifServicewiseRateListSearchDTO.getUnitId());
        MstUser HeaderObjectUser = mstUserRepository.findbyUserID(tarifServicewiseRateListSearchDTO.getUserId());
        JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObject));
        JSONObject jsonObjectUser = new JSONObject(new ObjectMapper().writeValueAsString(HeaderObjectUser));
        jsonArray.getJSONObject(0).put("objHeaderData", jsonObject);
        jsonArray.getJSONObject(0).put("objHeaderDataUser", jsonObjectUser);
        if (tarifServicewiseRateListSearchDTO.getPrint()) {
            columnName = "UnitName,TariffName,GroupName,SubGroupName,ServiceName,Class,ComPay,PatientPay";
            return createReport.generateExcel(columnName, "TarifServicewiseRateListReport", jsonArray);
        } else {
            return createReport.createJasperReportPDFByJson("TarifServicewiseRateListReport", jsonArray.toString());
        }
    }

}
