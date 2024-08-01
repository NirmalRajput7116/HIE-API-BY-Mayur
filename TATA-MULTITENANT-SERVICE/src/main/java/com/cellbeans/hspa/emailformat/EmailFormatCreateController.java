package com.cellbeans.hspa.emailformat;

import com.cellbeans.hspa.TenantContext;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RestController
@RequestMapping("/EmailFormatCreateController")
public class EmailFormatCreateController {

    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping("getEmailData")
    public String getEmailData(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        String TotalString = "";
        TotalString += patientCountDetails(tenantName) + "\n\n" + patientAgeWiseCountDetails(tenantName) + "\n\n" + getDisgnosisReport(tenantName);
        return TotalString;
    }

    @RequestMapping("getEmailDataForOthers")
    public String getEmailDataForOthers(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        String TotalString = "";
        TotalString += patientCountDetails(tenantName) + "\n\n" + patientAgeWiseCountDetails(tenantName) + "\n\n" + getDisgnosisReport(tenantName) + "\n\n" + getFastMovingMedicineReport(tenantName) + "\n\n" + getPrescribedInvestigation(tenantName);
        return TotalString;
    }

    public String patientCountDetails(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        String patientcString = "";
        String patientCountQuery = " SELECT ifnull(maleTable.MALE+femaleTable.FEMALE+otherTable.OTHER,0) as TotalPatient, ifnull(maleTable.MALE,0) as male,ifnull(femaleTable.FEMALE,0) as female,ifnull(otherTable.OTHER,0) as other FROM " + " (SELECT count(mst_visit.visit_id) as MALE FROM mst_visit " + " where is_active = 1 and is_deleted = 0 and date(created_date)=CURDATE() " + " and visit_patient_id in(select patient_id from mst_patient where patient_user_id in (select user_id from mst_user where user_gender_id =1))) as maleTable, " + " (SELECT count(mst_visit.visit_id) as FEMALE FROM mst_visit " + " where is_active = 1 and is_deleted = 0 and date(created_date)=CURDATE() " + " and visit_patient_id in(select patient_id from mst_patient where patient_user_id in (select user_id from mst_user where user_gender_id =2))) as femaleTable, " + "  (SELECT count(mst_visit.visit_id) as OTHER FROM mst_visit " + " where is_active = 1 and is_deleted = 0 and date(created_date)=CURDATE() " + " and visit_patient_id in(select patient_id from mst_patient where patient_user_id in (select user_id from mst_user where user_gender_id =3)) ) as otherTable ";
        List<Object[]> patientCount = (List<Object[]>) entityManager.createNativeQuery(patientCountQuery).getResultList();
        for (Object[] pc : patientCount) {
            patientcString += "Patient Count :" + pc[0].toString() + "\nM :" + pc[1].toString() + "\tF :" + pc[2].toString() + "\tOther :" + pc[3].toString();
        }
        return patientcString;
    }

    public String patientAgeWiseCountDetails(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        String patientcAgeString = "";
        String patientAgeCountQuery = " select ifnull(PediatricTable.gender_name,'') as Gender_name,ifnull(PediatricTable.Pediatric,0) as Pediatric,ifnull(AdultTable.Adult,0) as Adult ,ifnull(EldelyTable.Eldely,0) as  Eldely from " + " (select mg.gender_name, count(mv.visit_id) as Pediatric from mst_visit mv  " + " inner join  mst_patient mp ON mp.patient_id = mv.visit_patient_id " + "  inner join  mst_user mu ON mu.user_id = mp.patient_user_id " + "  left join mst_unit m on m.unit_id = mv.visit_unit_id LEFT JOIN mst_gender mg on mg.gender_id = mu.user_gender_id  " + " where  date(mv.created_date)=CURDATE() and  mu.user_age between 0 and  18  and  mv.is_active = 1 and mv.is_deleted = 0 group by  mu.user_gender_id) as PediatricTable " + " left join " + "(select mg.gender_name, count(mv.visit_id) as Adult from mst_visit mv  " + " inner join  mst_patient mp ON mp.patient_id = mv.visit_patient_id " + "  inner join  mst_user mu ON mu.user_id = mp.patient_user_id " + " left join mst_unit m on m.unit_id = mv.visit_unit_id LEFT JOIN mst_gender mg on mg.gender_id = mu.user_gender_id  " + " where  date(mv.created_date)=CURDATE() and  mu.user_age between 19 and  59  and  mv.is_active = 1 and mv.is_deleted = 0 group by  mu.user_gender_id) as  AdultTable " + " on  PediatricTable.gender_name=AdultTable.gender_name " + " left Join " + "(select mg.gender_name, count(mv.visit_id) as Eldely from mst_visit mv  " + " inner join  mst_patient mp ON mp.patient_id = mv.visit_patient_id " + " inner join  mst_user mu ON mu.user_id = mp.patient_user_id " + " left join mst_unit m on m.unit_id = mv.visit_unit_id LEFT JOIN mst_gender mg on mg.gender_id = mu.user_gender_id " + " where  date(mv.created_date)=CURDATE() and  mu.user_age >= 60  and  mv.is_active = 1 and mv.is_deleted = 0 group by  mu.user_gender_id) as EldelyTable " + " on  AdultTable.gender_name=EldelyTable.gender_name ";
        List<Object[]> patientAgeCount = (List<Object[]>) entityManager.createNativeQuery(patientAgeCountQuery).getResultList();
        String s1 = "";
        String s2 = "";
        String s3 = "";
        for (Object[] pac : patientAgeCount) {
            if (pac[0].toString().equalsIgnoreCase("Male")) {
                s1 = pac[1].toString() + " M\t";
                s2 = pac[2].toString() + " M\t";
                s3 = pac[3].toString() + " M\t";
            } else {
                s1 += pac[1].toString() + " F\t";
                s2 += pac[2].toString() + " F\t";
                s3 += pac[3].toString() + " F\t";
            }

        }
        patientcAgeString += "Pediatric :" + s1 + "\n Adult :" + s2 + "\n Elderly :" + s3;
        return patientcAgeString;
    }

    public String getDisgnosisReport(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        String dignosisString = "";
        String header = "Top 10 Diagnosis \n";
        int i = 1;
        String dignosisCountQuery = "SELECT ifnull(mic.ic_code,'') as ICDCode,ifnull(mic.ic_description,'') as ICDName,count(tvd.vd_ic_id) as count " + " from temr_visit_diagnosis tvd " + " INNER JOIN memr_icd_code mic ON mic.ic_id = tvd.vd_ic_id " + " INNER JOIN mst_visit mv ON mv.visit_id = tvd.vd_visit_id INNER JOIN mst_unit mu ON mu.unit_id = mv.visit_unit_id  " + " where date(tvd.created_date)=CURDATE() and tvd.vd_is_final_diagnosis in(0,1)  GROUP BY tvd.vd_ic_id order by count desc limit 10 ";
        List<Object[]> dignosisCount = (List<Object[]>) entityManager.createNativeQuery(dignosisCountQuery).getResultList();
        // Print the list objects in tabular format.
        header += "---------------------------------------------------------------------------------------------";
        header += "\nSr. No\t\tICD Code\t\tICD Name ";
        header += "\n---------------------------------------------------------------------------------------------\n";
        String oupput = "";
        for (Object[] pac : dignosisCount) {
            oupput += i + "\t\t\t" + pac[0] + "\t\t\t" + pac[1] + "\n";
            i += 1;
        }
        oupput += "---------------------------------------------------------------------------------------------";
        dignosisString = header + oupput;
        return dignosisString;
    }

    public String getFastMovingMedicineReport(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        String fMovingItemString = "";
        String header = "Top 10 Fast Moving Medicine \n";
        int i = 1;
        String fastMovingCountQuery = "select  IFNULL(fastMovingItem.item_code,'') AS item_code,IFNULL(fastMovingItem.item_name,'') as item_name,fastMovingItem.itemcount from " + "(select  it.item_code,it.item_name,count(i.psi_item_id) as itemcount from tinv_pharmacy_sale_item i,inv_item it " + " where i.psi_item_id=it.item_id and date(i.created_date)=CURDATE() group by i.psi_item_id,it.item_id) as fastMovingItem " + " order by  fastMovingItem.itemcount desc limit 10 ";
        List<Object[]> fastMovingCount = (List<Object[]>) entityManager.createNativeQuery(fastMovingCountQuery).getResultList();
        // Print the list objects in tabular format.
        header += "---------------------------------------------------------------------------------------------";
        header += "\nSr. No\t\tItem Code\t\tItem Name ";
        header += "\n---------------------------------------------------------------------------------------------\n";
        String oupput = "";
        for (Object[] fmi : fastMovingCount) {
            oupput += i + "\t\t\t" + fmi[0] + "\t\t\t" + fmi[1] + "\n";
            i += 1;
        }
        oupput += "---------------------------------------------------------------------------------------------";
        fMovingItemString = header + oupput;
        return fMovingItemString;
    }

    public String getPrescribedInvestigation(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        String prescribedInvestigationString = "";
        String header = "Top 10 Frequently Prescribed Investigation \n";
        int i = 1;
        String preInvestigationCountQuery = " select IFNULL(prescribeInvestigation.service_code,'') as service_code,IFNULL(prescribeInvestigation.service_name,'') as service_name,prescribeInvestigation.serviceCount from " + " (select ms.service_code,ms.service_name,count(vi.vi_service_id) as serviceCount from temr_visit_investigation vi,mbill_service ms " + " where vi.vi_service_id=ms.service_id and date(vi.vi_created_date)=CURDATE() group by vi.vi_service_id,ms.service_id) as prescribeInvestigation " + " order by prescribeInvestigation.serviceCount desc limit 10 ";
        List<Object[]> preInvestigationCount = (List<Object[]>) entityManager.createNativeQuery(preInvestigationCountQuery).getResultList();
        // Print the list objects in tabular format.
        header += "---------------------------------------------------------------------------------------------";
        header += "\nSr. No\t\tCount\t\tService Code\t\tService Name ";
        header += "\n---------------------------------------------------------------------------------------------\n";
        String oupput = "";
        for (Object[] pc : preInvestigationCount) {
            oupput += i + "\t\t" + pc[2] + "\t\t" + pc[0] + "\t\t\t" + pc[1] + "\n";
            i += 1;
        }
        oupput += "---------------------------------------------------------------------------------------------";
        prescribedInvestigationString = header + oupput;
        return prescribedInvestigationString;
    }

    public String getFollowUpSmsByPatientId(@RequestHeader("X-tenantId") String tenantName, Long patientId) {
        TenantContext.setCurrentTenant(tenantName);
        String followUpStr = "";
        String header = "Please visit hospital today For FollowUp %0a";
        int i = 0;
        String query = " select mp.patient_mr_no, mu.user_firstname, mu.user_lastname, mu.user_mobile,mu.user_alt_phone, mu.user_email from mst_patient mp inner join mst_user mu on mu.user_id=mp.patient_user_id where mp.patient_id=" + patientId;
        List<Object[]> details = (List<Object[]>) entityManager.createNativeQuery(query).getResultList();
        // Print the list objects in tabular format.
        for (Object[] pc : details) {
            System.out.println("Patient details :" + pc[i]);
            if (pc[0] != null) {
                header += "Patient MR No: " + pc[0] + "%0a";
            }
            if (pc[1] != null) {
                header += "Patient Name: " + pc[1] + " " + pc[2] + "%0a";
            }
            if (pc[3] != null) {
                header += "Mobile No.: " + pc[3] + "%0a";
            }
            if (pc[4] != null) {
                header += "Phone No: " + pc[4] + "%0a";
            }
            if (pc[5] != null) {
                header += "Email No: " + pc[5] + "%0a";
            }
        }
        return header;
    }

    public String getFollowUpSmsByAdmissionId(@RequestHeader("X-tenantId") String tenantName, Long admissionId) {
        TenantContext.setCurrentTenant(tenantName);
        String followUpStr = "";
        String header = "Please visit hospital today For FollowUp %0a";
        int i = 1;
        String query = " select mp.patient_mr_no, mu.user_firstname, mu.user_lastname, mu.user_mobile,mu.user_alt_phone, mu.user_email from trn_admission ta inner join mst_patient mp on mp.patient_id= ta.admission_patient_id inner join mst_user mu on mu.user_id=mp.patient_user_id where ta.admission_id=" + admissionId;
        List<Object[]> details = (List<Object[]>) entityManager.createNativeQuery(query).getResultList();
        // Print the list objects in tabular format.s
        for (Object[] pc : details) {
            System.out.println("Admission patient details :" + pc[i]);
            if (pc[0] != null) {
                header += "Patient MR No: " + pc[0] + "%0a";
            }
            if (pc[1] != null) {
                header += "Patient Name: " + pc[1] + " " + pc[2] + "%0a";
            }
            if (pc[3] != null) {
                header += "Mobile No.: " + pc[3] + "%0a";
            }
            if (pc[4] != null) {
                header += "Phone No: " + pc[4] + "%0a";
            }
            if (pc[5] != null) {
                header += "Email No: " + pc[5] + "%0a";
            }
        }
        return header;
    }

}
