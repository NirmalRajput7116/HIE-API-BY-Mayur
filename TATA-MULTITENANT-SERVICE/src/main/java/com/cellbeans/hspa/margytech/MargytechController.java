package com.cellbeans.hspa.margytech;

import com.cellbeans.hspa.TenantContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cellbeans_margytech")
public class MargytechController {

    @PersistenceContext
    EntityManager entityManager;

    @GetMapping("/getlabdata")
    public List<MargytechDTO> getAllLabData(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        List<MargytechDTO> TestDTOList = new ArrayList<MargytechDTO>();
        String query = " SELECT " +
                " IFNULL(mp.created_date,'') AS RegDate," +
                " IFNULL(mp.patient_mr_no,'') AS PatientUniqueID," +
                " IFNULL(mp.patient_mr_no,'') AS PRN," +
                " IFNULL(mt.title_name,'') AS PatientTitle," +
                " IFNULL(mu.user_firstname,'') AS PatientFirstName," +
                " IFNULL(mu.user_middlename,'') AS PatientMiddleName," +
                " IFNULL(mu.user_lastname,'') AS PatientLastName," +
                " IFNULL(mg.gender_name,'') AS Gender, " +
                " IFNULL(mu.user_age,'') AS AgeInYear,  IFNULL(mu.user_month,'') AS AgeInMonth,  IFNULL(mu.user_day,'') AS AgeInDay," +
                " IFNULL(mu.user_mobile,'') AS Mobile, " +
                " IFNULL(mu.user_email,'') AS Email, " +
                " IFNULL(mu.user_address,'') AS Address, " +
                " IFNULL(md.district_name,'') AS District, " +
                " IFNULL(ms.state_name,'') AS State, " +
                " IFNULL(mcip.cip_name,'') AS PatientIDType, " +
                " IFNULL(mu.user_uid,'') AS PatientIDNumber, " +
                " IFNULL(tb.bill_opd_number,'') AS VisitNo, " +
                " IFNULL(mv.visit_date,'') AS VisitDate," +
                " IFNULL(mun.unit_code,'') AS CenterCode," +
                " IFNULL(CONCAT(mt1.title_name,' ',mu1.user_firstname,' ',mu1.user_lastname),'') AS RefDr," +
                " IFNULL(mtest.test_code,'') AS TestCode,  " +
                " IFNULL(t.ps_sample_collection_date,'') AS SampleDate," +
                " IF(mv.visit_id IS NOT NULL,'OPD','IPD') AS PatientCategory" +
                " FROM tpath_bs t" +
                " INNER JOIN tbill_bill tb ON t.ps_bill_id = tb.bill_id" +
                " LEFT JOIN mst_visit mv ON tb.bill_visit_id = mv.visit_id" +
                " LEFT JOIN mst_referring_entity mre ON mv.refer_by = mre.re_id" +
                " INNER JOIN mst_patient mp ON mv.visit_patient_id = mp.patient_id" +
                " INNER JOIN mst_user mu ON mp.patient_user_id = mu.user_id" +
                " LEFT JOIN mst_city mc ON mu.user_city_id = mc.city_id" +
                " LEFT JOIN mst_district md ON mc.city_district_id = md.district_id" +
                " LEFT JOIN mst_state ms ON mc.city_state_id = ms.state_id" +
                " LEFT JOIN mst_title mt ON mu.user_title_id = mt.title_id" +
                " LEFT JOIN mst_gender mg on mu.user_gender_id = mg.gender_id" +
                " LEFT JOIN mst_citizen_id_proof mcip on mu.user_citizen_id = mcip.cip_id" +
                " LEFT JOIN mpath_test mtest ON t.ps_test_id = mtest.test_id" +
                " LEFT JOIN mst_unit mun ON t.is_performed_by_unit_id= mun.unit_id" +
                " LEFT JOIN mst_staff mstaff ON mv.refer_by= mstaff.staff_id" +
                " LEFT JOIN mst_user mu1 ON mstaff.staff_user_id = mu1.user_id" +
                " LEFT JOIN mst_title mt1 ON mu1.user_title_id = mt1.title_id" +
                " WHERE t.isipd=0 and t.is_active = 1 AND t.is_deleted =  0 ";
        try {
            //MainQuery += " limit " + ((billCumTestReportSearchDTO.getOffset() - 1) * billCumTestReportSearchDTO.getLimit()) + "," + billCumTestReportSearchDTO.getLimit();
            // List<Object[]> dataList = entityManager.createNativeQuery(query).getResultList();
            System.out.println("MainQuery:" + query);
            List<Object[]> list = entityManager.createNativeQuery(query).getResultList();
            // TestDTOList.addAll(list);
            for (Object[] obj : list) {
                MargytechDTO objmargytechDTO = new MargytechDTO();
                objmargytechDTO.setRegDate("" + obj[0]);
                objmargytechDTO.setPatientUniqueId("" + obj[1]);
                objmargytechDTO.setPRN("" + obj[2]);
                objmargytechDTO.setPatientTitle("" + obj[3]);
                objmargytechDTO.setPatientFirstName("" + obj[4]);
                objmargytechDTO.setPatientMiddleName("" + obj[5]);
                objmargytechDTO.setPatientLastName("" + obj[6]);
                objmargytechDTO.setGender("" + obj[7]);
                objmargytechDTO.setAgeInYear("" + obj[8]);
                objmargytechDTO.setAgeInMonth("" + obj[9]);
                objmargytechDTO.setAgeInDay("" + obj[10]);
                objmargytechDTO.setMobile("" + obj[11]);
                objmargytechDTO.setEmail("" + obj[12]);
                objmargytechDTO.setAddress("" + obj[13]);
                objmargytechDTO.setDistrict("" + obj[14]);
                objmargytechDTO.setState("" + obj[15]);
                objmargytechDTO.setPatientIdType("" + obj[16]);
                objmargytechDTO.setPatientIdNumber("" + obj[17]);
                objmargytechDTO.setVisitNo("" + obj[18]);
                objmargytechDTO.setVisitDate("" + obj[19]);
                objmargytechDTO.setCenterCode("" + obj[20]);
                objmargytechDTO.setRefDr("" + obj[21]);
                objmargytechDTO.setTestCode("" + obj[22]);
                objmargytechDTO.setSampleDate("" + obj[23]);
                objmargytechDTO.setPatientCategory("" + obj[24]);
                TestDTOList.add(objmargytechDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return TestDTOList;

    }

}