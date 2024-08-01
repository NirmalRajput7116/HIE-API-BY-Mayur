package com.cellbeans.hspa.mis.misopdreports;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
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
@RequestMapping("/mis-Outward-referral-Report")
public class OutwardReferralController {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    CreateReport createReport;

    @Autowired
    MstUnitRepository mstUnitRepository;

    @RequestMapping("getoutwardreferralReport/{unitList}/{mstgenderlist}/{fromdate}/{todate}")
    public List<OutwardReferralListDTO> searchOutwardreferral(@RequestHeader("X-tenantId") String tenantName, @RequestBody OutwardReferralSearchDTO outwardReferralSearchDTO, @PathVariable String[] unitList, @PathVariable String[] mstgenderlist, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        List<OutwardReferralListDTO> outwardreferrallistDTOList = new ArrayList<OutwardReferralListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " SELECT ifnull(un.unit_name,'')as unit_name, concat(u.user_firstname,' ',u.user_lastname)as PatientName,p.patient_mr_no,u.user_dob,u.user_age, " +
                " ge.gender_name,n.nationality_name,ifnull(u.user_uid,'')as user_uid, date(rh.created_date)as RefDate,et.ret_name as RefEntityType, " + " ifnull(e.re_name,'')as ReferEntity," +
                "s.speciality_name,ifnull(rh.rh_subject,'') as Reason,ifnull(un.unit_id,'')as unit_id, " + " date(v.visit_date) as VisitDate, " +
                " concat(ifnull(mu.user_firstname,''),' ',ifnull(mu.user_lastname,'') )as RefDocName, " + " ifnull(mcc.cc_name,'') as Visitreason " +
                " FROM temr_referral_history rh " +
                " left join mst_referring_entity_type et on rh.rh_referring_entity_type_id=et.ret_id " +
                " left join mst_referring_entity e on rh.rh_referring_entity=e.re_id " +
                " left join mst_speciality s on rh.rh_speciality_id=s.speciality_id " +
                " left join mst_visit v on rh.rh_visit_id=v.visit_id " +
                " left join mst_patient p on v.visit_patient_id=p.patient_id " +
                " left join mst_user u on p.patient_user_id=u.user_id " +
                " left join mst_nationality n on u.user_nationality_id=n.nationality_id " +
                " left join mst_unit un on v.visit_unit_id=un.unit_id " +
                " left join mst_gender ge on u.user_gender_id=ge.gender_id " +
                " left join mst_staff ms on rh.rh_staff_id=ms.staff_id " +
                " left join mst_user mu on ms.staff_user_id=mu.user_id " +
                " left join memr_chief_complaint mcc on v.visit_chief_complaint=mcc.cc_id ";
        String CountQuery = " SELECT count(rh.rh_referring_entity_type_id) " + " FROM temr_referral_history rh " + " left join mst_referring_entity_type et on rh.rh_referring_entity_type_id=et.ret_id " + " left join mst_referring_entity e on rh.rh_referring_entity=e.re_id " + " left join mst_speciality s on rh.rh_speciality_id=s.speciality_id " + " left join mst_visit v on rh.rh_visit_id=v.visit_id " + " left join mst_patient p on v.visit_patient_id=p.patient_id " + " left join mst_user u on p.patient_user_id=u.user_id " + " left join mst_nationality n on u.user_nationality_id=n.nationality_id " + " left join mst_unit un on v.visit_unit_id=un.unit_id " + " left join mst_gender ge on u.user_gender_id=ge.gender_id " + " left join mst_staff ms on rh.rh_staff_id=ms.staff_id " + " left join mst_user mu on ms.staff_user_id=mu.user_id " + " left join memr_chief_complaint mcc on v.visit_chief_complaint=mcc.cc_id ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
            // opdListOfRegistrationSearchDTO.setDORfromdate("1990-06-07");
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
            //opdListOfRegistrationSearchDTO.setDORtodate(strDate);
        }
        if (outwardReferralSearchDTO.getTodaydate()) {
            Query += " where (date(v.created_date)=curdate()) ";
            CountQuery += " where (date(v.created_date)=curdate()) ";
        } else {
            Query += " where (date(rh.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            CountQuery += " where (date(rh.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and un.unit_id in (" + values + ") ";
            CountQuery += " and un.unit_id in (" + values + ") ";
        }
        if (!mstgenderlist[0].equals("null")) {
            String values = mstgenderlist[0];
            for (int i = 1; i < mstgenderlist.length; i++) {
                values += "," + mstgenderlist[i];
            }
            Query += " and u.user_gender_id in (" + values + ") ";
            CountQuery += " and u.user_gender_id in (" + values + ") ";
        }
        if (!String.valueOf(outwardReferralSearchDTO.getRefentityId()).equals("0") && !String.valueOf(outwardReferralSearchDTO.getRefentityTypeId()).equals("null")) {
            Query += " and rh.rh_referring_entity_type_id=" + outwardReferralSearchDTO.getRefentityTypeId() + " ";
            CountQuery += " and rh.rh_referring_entity_type_id=" + outwardReferralSearchDTO.getRefentityTypeId() + " ";
        }
        if (!String.valueOf(outwardReferralSearchDTO.getRefentityId()).equals("0") && !String.valueOf(outwardReferralSearchDTO.getRefentityId()).equals("null")) {
            Query += " and rh.rh_referring_entity=" + outwardReferralSearchDTO.getRefentityId() + " ";
            CountQuery += " and rh.rh_referring_entity=" + outwardReferralSearchDTO.getRefentityId() + " ";
        }
        if (!String.valueOf(outwardReferralSearchDTO.getSpecialityId()).equals("0") && !String.valueOf(outwardReferralSearchDTO.getSpecialityId()).equals("null")) {
            Query += " and rh.rh_speciality_id=" + outwardReferralSearchDTO.getSpecialityId() + " ";
            CountQuery += " and rh.rh_speciality_id=" + outwardReferralSearchDTO.getSpecialityId() + " ";
        }
        try {
            Query += " limit " + ((outwardReferralSearchDTO.getOffset() - 1) * outwardReferralSearchDTO.getLimit()) + "," + outwardReferralSearchDTO.getLimit();
            System.out.println("Mainquery" + Query);
            List<Object[]> listOutwardReferral = entityManager.createNativeQuery(Query).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            count = cc.longValue();
            for (Object[] obj : listOutwardReferral) {
                OutwardReferralListDTO objoutwardreferrallistDTO = new OutwardReferralListDTO();
                objoutwardreferrallistDTO.setUnitName("" + obj[0]);
                objoutwardreferrallistDTO.setPatientName("" + obj[1]);
                objoutwardreferrallistDTO.setMrNO("" + obj[2]);
                objoutwardreferrallistDTO.setdOB("" + obj[3]);
                objoutwardreferrallistDTO.setAge("" + obj[4]);
                objoutwardreferrallistDTO.setGender("" + obj[5]);
                objoutwardreferrallistDTO.setIdProof("" + obj[6]);
                objoutwardreferrallistDTO.setIdProofDocNo("" + obj[7]);
                objoutwardreferrallistDTO.setReferralDate("" + obj[8]);
                objoutwardreferrallistDTO.setReferralEntityType("" + obj[9]);
                objoutwardreferrallistDTO.setReferralEntity("" + obj[10]);
                objoutwardreferrallistDTO.setSpeciality("" + obj[11]);
                objoutwardreferrallistDTO.setReason("" + obj[12]);
                objoutwardreferrallistDTO.setUnitId(Long.parseLong("" + obj[13]));
                objoutwardreferrallistDTO.setVisitDate("" + obj[14]);
                objoutwardreferrallistDTO.setReferralDoctor("" + obj[15]);
                objoutwardreferrallistDTO.setVisitReason("" + obj[16]);
                objoutwardreferrallistDTO.setCount(count);
                outwardreferrallistDTOList.add(objoutwardreferrallistDTO);
            }
        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("Error:" + e);
            //return null;
        }
        return outwardreferrallistDTOList;
    }
//    @RequestMapping(value = "report", method = RequestMethod.GET)
//    public ModelAndView searchOutwardreferral() {
//        JasperReportsPdfView view = new JasperReportsPdfView();
//        view.setUrl("classpath:/Reports/report1.jrxml");
//        view.setApplicationContext(applicationContext);
//        Map<String, Object> params = new HashMap<>();
//        MstCity abc = new MstCity();
//        abc.setCityId(1);
//        abc.setCityName("Rohan");
//
//        List<MstCity> added = new ArrayList<MstCity>();
//        added.add(abc);
//        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(added);
//        params.put("datasource", ds);
//        return new ModelAndView(view, params);
//    }

    @RequestMapping("searchOutwardreferralPrint/{unitList}/{mstgenderlist}/{fromdate}/{todate}")
    public String searchOutwardreferralPrint(@RequestHeader("X-tenantId") String tenantName, @RequestBody OutwardReferralSearchDTO outwardReferralSearchDTO, @PathVariable String[] unitList, @PathVariable String[] mstgenderlist, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        List<OutwardReferralListDTO> outwardreferrallistDTOList = new ArrayList<OutwardReferralListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String Query = " SELECT ifnull(un.unit_name,'')as unit_name, concat(u.user_firstname,' ',u.user_lastname)as PatientName,p.patient_mr_no,u.user_dob,u.user_age, " + " ge.gender_name,n.nationality_name,ifnull(u.user_uid,'')as user_uid, date(rh.created_date)as RefDate,et.ret_name as RefEntityType, " + " ifnull(e.re_name,'')as ReferEntity,s.speciality_name,ifnull(rh.rh_subject,'') as Reason,ifnull(un.unit_id,'')as unit_id, " + " date(v.visit_date) as VisitDate, " + " concat(ifnull(mu.user_firstname,''),' ',ifnull(mu.user_lastname,'') )as RefDocName, " + " ifnull(mcc.cc_name,'') as Visitreason " + " FROM temr_referral_history rh " + " left join mst_referring_entity_type et on rh.rh_referring_entity_type_id=et.ret_id " + " left join mst_referring_entity e on rh.rh_referring_entity=e.re_id " + " left join mst_speciality s on rh.rh_speciality_id=s.speciality_id " + " left join mst_visit v on rh.rh_visit_id=v.visit_id " + " left join mst_patient p on v.visit_patient_id=p.patient_id " + " left join mst_user u on p.patient_user_id=u.user_id " + " left join mst_nationality n on u.user_nationality_id=n.nationality_id " + " left join mst_unit un on v.visit_unit_id=un.unit_id " + " left join mst_gender ge on u.user_gender_id=ge.gender_id " + " left join mst_staff ms on rh.rh_staff_id=ms.staff_id " + " left join mst_user mu on ms.staff_user_id=mu.user_id " + " left join memr_chief_complaint mcc on v.visit_chief_complaint=mcc.cc_id ";
        String CountQuery = " SELECT count(rh.rh_referring_entity_type_id) " + " FROM temr_referral_history rh " + " left join mst_referring_entity_type et on rh.rh_referring_entity_type_id=et.ret_id " + " left join mst_referring_entity e on rh.rh_referring_entity=e.re_id " + " left join mst_speciality s on rh.rh_speciality_id=s.speciality_id " + " left join mst_visit v on rh.rh_visit_id=v.visit_id " + " left join mst_patient p on v.visit_patient_id=p.patient_id " + " left join mst_user u on p.patient_user_id=u.user_id " + " left join mst_nationality n on u.user_nationality_id=n.nationality_id " + " left join mst_unit un on v.visit_unit_id=un.unit_id " + " left join mst_gender ge on u.user_gender_id=ge.gender_id " + " left join mst_staff ms on rh.rh_staff_id=ms.staff_id " + " left join mst_user mu on ms.staff_user_id=mu.user_id " + " left join memr_chief_complaint mcc on v.visit_chief_complaint=mcc.cc_id ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
            // opdListOfRegistrationSearchDTO.setDORfromdate("1990-06-07");
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
            //opdListOfRegistrationSearchDTO.setDORtodate(strDate);
        }
        if (outwardReferralSearchDTO.getTodaydate()) {
            Query += " where (date(v.created_date)=curdate()) ";
            CountQuery += " where (date(v.created_date)=curdate()) ";
        } else {
            Query += " where (date(rh.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            CountQuery += " where (date(rh.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and un.unit_id in (" + values + ") ";
            CountQuery += " and un.unit_id in (" + values + ") ";
        }
        if (!mstgenderlist[0].equals("null")) {
            String values = mstgenderlist[0];
            for (int i = 1; i < mstgenderlist.length; i++) {
                values += "," + mstgenderlist[i];
            }
            Query += " and u.user_gender_id in (" + values + ") ";
            CountQuery += " and u.user_gender_id in (" + values + ") ";
        }
        if (!String.valueOf(outwardReferralSearchDTO.getRefentityTypeId()).equals("0") && !String.valueOf(outwardReferralSearchDTO.getRefentityTypeId()).equals("null")) {
            Query += " and rh.rh_referring_entity_type_id=" + outwardReferralSearchDTO.getRefentityTypeId() + " ";
            CountQuery += " and rh.rh_referring_entity_type_id=" + outwardReferralSearchDTO.getRefentityTypeId() + " ";
        }
        if (!String.valueOf(outwardReferralSearchDTO.getRefentityId()).equals("0") && !String.valueOf(outwardReferralSearchDTO.getRefentityId()).equals("null")) {
            Query += " and rh.rh_referring_entity=" + outwardReferralSearchDTO.getRefentityId() + " ";
            CountQuery += " and rh.rh_referring_entity=" + outwardReferralSearchDTO.getRefentityId() + " ";
        }
        if (!String.valueOf(outwardReferralSearchDTO.getSpecialityId()).equals("0") && !String.valueOf(outwardReferralSearchDTO.getSpecialityId()).equals("null")) {
            Query += " and rh.rh_speciality_id=" + outwardReferralSearchDTO.getSpecialityId() + " ";
            CountQuery += " and rh.rh_speciality_id=" + outwardReferralSearchDTO.getSpecialityId() + " ";
        }
        try {
            System.out.println("Mainquery" + Query);
            List<Object[]> listOutwardReferral = entityManager.createNativeQuery(Query).getResultList();
            BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            count = cc.longValue();
            for (Object[] obj : listOutwardReferral) {
                OutwardReferralListDTO objoutwardreferrallistDTO = new OutwardReferralListDTO();
                objoutwardreferrallistDTO.setUnitName("" + obj[0]);
                objoutwardreferrallistDTO.setPatientName("" + obj[1]);
                objoutwardreferrallistDTO.setMrNO("" + obj[2]);
                objoutwardreferrallistDTO.setdOB("" + obj[3]);
                objoutwardreferrallistDTO.setAge("" + obj[4]);
                objoutwardreferrallistDTO.setGender("" + obj[5]);
                objoutwardreferrallistDTO.setIdProof("" + obj[6]);
                objoutwardreferrallistDTO.setIdProofDocNo("" + obj[7]);
                objoutwardreferrallistDTO.setReferralDate("" + obj[8]);
                objoutwardreferrallistDTO.setReferralEntityType("" + obj[9]);
                objoutwardreferrallistDTO.setReferralEntity("" + obj[10]);
                objoutwardreferrallistDTO.setSpeciality("" + obj[11]);
                objoutwardreferrallistDTO.setReason("" + obj[12]);
                objoutwardreferrallistDTO.setUnitId(Long.parseLong("" + obj[13]));
                objoutwardreferrallistDTO.setVisitDate("" + obj[14]);
                objoutwardreferrallistDTO.setReferralDoctor("" + obj[15]);
                objoutwardreferrallistDTO.setVisitReason("" + obj[16]);
                objoutwardreferrallistDTO.setCount(count);
                outwardreferrallistDTOList.add(objoutwardreferrallistDTO);
            }
        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("Error:" + e);
            //return null;
        }
        MstUnit HeaderObject = mstUnitRepository.findByUnitId(outwardReferralSearchDTO.getUnitId());
        outwardreferrallistDTOList.get(0).setObjHeaderData(HeaderObject);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(outwardreferrallistDTOList);
        // return createReport.createJasperReportXLS("OutwardReferralReport", ds);
        if (outwardReferralSearchDTO.getPrint()) {
            return createReport.createJasperReportXLS("OutwardReferralReport", ds);
        } else {
            return createReport.createJasperReportPDF("OutwardReferralReport", ds);
        }

    }

}

