package com.cellbeans.hspa.mis.misopdreports;

import com.cellbeans.hspa.TenantContext;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/mis_NewPatient_FolloupVisit_report")
public class MisNewPatientFoloupVisitController {

    @PersistenceContext
    EntityManager entityManager;

    @RequestMapping("searchNewpatFolloupVisit/{unitList}/{fromdate}/{todate}")
    public Map<String, List<NewPatientFolloupVisitListDTO>> searchNewPatFolloupVisit(@RequestHeader("X-tenantId") String tenantName, @RequestBody NewPatientFolloupVisitSearchDTO newpatientFolloupVisitSearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        Map<String, List<NewPatientFolloupVisitListDTO>> respMap = new HashMap<String, List<NewPatientFolloupVisitListDTO>>();
        List<NewPatientFolloupVisitListDTO> newpatientfolloupvisitlistDTOList = new ArrayList<NewPatientFolloupVisitListDTO>();
        List<NewPatientFolloupVisitListDTO> newpatientfolloupvisitlistDTOList1 = new ArrayList<NewPatientFolloupVisitListDTO>();
        List<NewPatientFolloupVisitListDTO> newpatientfolloupvisitlistDTOList2 = new ArrayList<NewPatientFolloupVisitListDTO>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String FQuery1 = " select  ifnull(un.unit_name,'')as unit_name,count(v.visit_patient_id) as rmaleCount from mst_visit v " + " left join mst_patient p on v.visit_patient_id=p.patient_id " + " left join mst_user u on p.patient_user_id=u.user_id " + " left join mst_gender g on u.user_gender_id=g.gender_id " + " left join mst_unit un on v.visit_unit_id=un.unit_id " + " where (reason_visit='Follow Up') and  u.user_gender_id=1 ";
        String FQuery2 = "select  ifnull(un.unit_name,'')as unit_name,count(v.visit_patient_id) as rfeCount from mst_visit v " + " left join mst_patient p on v.visit_patient_id=p.patient_id " + " left join mst_user u on p.patient_user_id=u.user_id " + " left join mst_gender g on u.user_gender_id=g.gender_id " + " left join mst_unit un on v.visit_unit_id=un.unit_id " + " where (reason_visit='Follow Up') and u.user_gender_id=2 ";
        String FQuery3 = " select  ifnull(un.unit_name,'')as unit_name,count(v.visit_patient_id) as rotCount from mst_visit v " + " left join mst_patient p on v.visit_patient_id=p.patient_id " + " left join mst_user u on p.patient_user_id=u.user_id " + " left join mst_gender g on u.user_gender_id=g.gender_id " + " left join mst_unit un on v.visit_unit_id=un.unit_id " + " where (reason_visit='Follow Up') and  u.user_gender_id=3 ";
        String RQuery1 = " select  ifnull(un.unit_name,'')as unit_name,count(v.visit_patient_id) as rmaleCount from mst_visit v " + " left join mst_patient p on v.visit_patient_id=p.patient_id " + " left join mst_user u on p.patient_user_id=u.user_id " + " left join mst_gender g on u.user_gender_id=g.gender_id " + " left join mst_unit un on v.visit_unit_id=un.unit_id " + " where (reason_visit='First Visit') and  u.user_gender_id=1";
        String RQuery2 = " select  ifnull(un.unit_name,'')as unit_name,count(v.visit_patient_id) as rfeCount from mst_visit v " + " left join mst_patient p on v.visit_patient_id=p.patient_id " + " left join mst_user u on p.patient_user_id=u.user_id " + " left join mst_gender g on u.user_gender_id=g.gender_id " + " left join mst_unit un on v.visit_unit_id=un.unit_id " + " where (reason_visit='First Visit') and u.user_gender_id=2 ";
        String RQuery3 = " select  ifnull(un.unit_name,'')as unit_name,count(v.visit_patient_id) as rotCount from mst_visit v " + " left join mst_patient p on v.visit_patient_id=p.patient_id " + " left join mst_user u on p.patient_user_id=u.user_id " + " left join mst_gender g on u.user_gender_id=g.gender_id " + " left join mst_unit un on v.visit_unit_id=un.unit_id " + " where (reason_visit='First Visit') and  u.user_gender_id=3 ";
        String RegFolCalQuery1 = " select  ifnull(un.unit_name,'')as unit_name,count(v.visit_patient_id) as rmaleCount from mst_visit v " + " left join mst_patient p on v.visit_patient_id=p.patient_id " + " left join mst_user u on p.patient_user_id=u.user_id " + " left join mst_gender g on u.user_gender_id=g.gender_id " + " left join mst_unit un on v.visit_unit_id=un.unit_id " + " where (reason_visit='Follow Up' or reason_visit='First Visit') and  u.user_gender_id=1 ";
        String RegFolCalQuery2 = "select  ifnull(un.unit_name,'')as unit_name,count(v.visit_patient_id) as rfeCount from mst_visit v " + " left join mst_patient p on v.visit_patient_id=p.patient_id " + " left join mst_user u on p.patient_user_id=u.user_id " + " left join mst_gender g on u.user_gender_id=g.gender_id " + " left join mst_unit un on v.visit_unit_id=un.unit_id " + " where (reason_visit='Follow Up' or reason_visit='First Visit') and u.user_gender_id=2 ";
        String RegFolCalQuery3 = " select  ifnull(un.unit_name,'')as unit_name,count(v.visit_patient_id) as rotCount from mst_visit v " + " left join mst_patient p on v.visit_patient_id=p.patient_id " + " left join mst_user u on p.patient_user_id=u.user_id " + " left join mst_gender g on u.user_gender_id=g.gender_id " + " left join mst_unit un on v.visit_unit_id=un.unit_id " + " where (reason_visit='Follow Up' or reason_visit='First Visit') and  u.user_gender_id=3 ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
            // opdListOfRegistrationSearchDTO.setDORfromdate("1990-06-07");
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
            //opdListOfRegistrationSearchDTO.setDORtodate(strDate);
        }
        if (newpatientFolloupVisitSearchDTO.getTodaydate()) {
            FQuery1 += " and date(v.created_date)=curdate() ";
            FQuery2 += " and date(v.created_date)=curdate() ";
            FQuery3 += " and date(v.created_date)=curdate() ";
            RQuery1 += " and date(v.created_date)=curdate() ";
            RQuery2 += " and date(v.created_date)=curdate() ";
            RQuery3 += " and date(v.created_date)=curdate() ";
            RegFolCalQuery1 += " and date(v.created_date)=curdate() ";
            RegFolCalQuery2 += " and date(v.created_date)=curdate() ";
            RegFolCalQuery3 += " and date(v.created_date)=curdate() ";

        } else {
            FQuery1 += " and (date(v.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            FQuery2 += " and (date(v.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            FQuery3 += " and (date(v.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            RQuery1 += " and (date(v.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            RQuery2 += " and (date(v.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            RQuery3 += " and (date(v.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            RegFolCalQuery1 += " and (date(v.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            RegFolCalQuery2 += " and (date(v.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            RegFolCalQuery3 += " and (date(v.created_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            FQuery1 += " and v.visit_unit_id in (" + values + ") ";
            FQuery2 += " and v.visit_unit_id in (" + values + ") ";
            FQuery3 += " and v.visit_unit_id in (" + values + ") ";
            RQuery1 += " and v.visit_unit_id in (" + values + ") ";
            RQuery2 += " and v.visit_unit_id in (" + values + ") ";
            RQuery3 += " and v.visit_unit_id in (" + values + ") ";
            RegFolCalQuery1 += " and v.visit_unit_id in (" + values + ") ";
            RegFolCalQuery2 += " and v.visit_unit_id in (" + values + ") ";
            RegFolCalQuery3 += " and v.visit_unit_id in (" + values + ") ";
        }
        String RegMainQuery = " select newvisit.RUnitName,newvisit.RMailcount,newvisit.RFemailCount,newvisit.ROtherCount From ( select ifnull(fml.unit_name,'')as RUnitName ,ifnull(fml.rmaleCount,0)as RMailcount, " + " ifnull(ffl.rfeCount,0)as RFemailCount,ifnull(fol.rotCount,0) as ROtherCount from " + " ( " + RQuery1 + "group by un.unit_id) as fml left join " + " ( " + RQuery2 + " group by un.unit_id) as ffl " + " on fml.unit_name=ffl.unit_name " + " left join " + " ( " + RQuery3 + " group by un.unit_id) as fol " + " on ffl.unit_name=fml.unit_name ";
        String FolMainQuery = " select ifnull(fml.unit_name,'')as FUnitName ,ifnull(fml.rmaleCount,0)as FMailcount, " + " ifnull(ffl.rfeCount,0)as FFemailCount,ifnull(fol.rotCount,0) as FOtherCount from " + " ( " + FQuery1 + " group by un.unit_id) as fml left join " + " ( " + FQuery2 + "group by un.unit_id) as ffl " + " on fml.unit_name=ffl.unit_name " + " left join " + " ( " + FQuery3 + " group by un.unit_id) as fol " + " on ffl.unit_name=fml.unit_name ";
        String NewNFolMainQuery = "select newvisitFolTot.RFUnitName,newvisitFolTot.RFMailcount,newvisitFolTot.RFFemailCount,newvisitFolTot.RFOtherCount from ( select ifnull(fml.unit_name,'')as RFUnitName ,ifnull(fml.rmaleCount,0)as RFMailcount, " + " ifnull(ffl.rfeCount,0)as RFFemailCount,ifnull(fol.rotCount,0) as RFOtherCount from " + " ( " + RegFolCalQuery1 + " group by un.unit_id) as fml left join " + " ( " + RegFolCalQuery2 + " group by un.unit_id) as ffl " + " on fml.unit_name=ffl.unit_name " + " left join " + " ( " + RegFolCalQuery3 + " group by un.unit_id) as fol " + " on  ffl.unit_name=fml.unit_name ";
        try {
            System.out.println("OPD VIsit Report (New Visit & Folloup Visit)" + FolMainQuery);
            List<Object[]> listNewpatientFolloupVis = entityManager.createNativeQuery(FolMainQuery).getResultList();
            for (Object[] obj : listNewpatientFolloupVis) {
                NewPatientFolloupVisitListDTO objnewpatientfolloupvisitlistDTO = new NewPatientFolloupVisitListDTO();
                objnewpatientfolloupvisitlistDTO.setUnitName("" + obj[0]);
                objnewpatientfolloupvisitlistDTO.setFollovismale("" + obj[1]);
                objnewpatientfolloupvisitlistDTO.setFollovisfemale("" + obj[2]);
                objnewpatientfolloupvisitlistDTO.setFollovisother("" + obj[3]);
                newpatientfolloupvisitlistDTOList.add(objnewpatientfolloupvisitlistDTO);
            }
            respMap.put("Follow", newpatientfolloupvisitlistDTOList);
            System.out.println("Registration Mainquery" + RegMainQuery);
            List<Object[]> listNewpatientFolloupVis1 = entityManager.createNativeQuery(RegMainQuery + " ) as newvisit group by newvisit.RUnitName").getResultList();
            for (Object[] obj : listNewpatientFolloupVis1) {
                NewPatientFolloupVisitListDTO objnewpatientfolloupvisitlistDTO = new NewPatientFolloupVisitListDTO();
                objnewpatientfolloupvisitlistDTO.setUnitName("" + obj[0]);
                objnewpatientfolloupvisitlistDTO.setNewregmale("" + obj[1]);
                objnewpatientfolloupvisitlistDTO.setNewregfemale("" + obj[2]);
                objnewpatientfolloupvisitlistDTO.setNewregother("" + obj[3]);
                newpatientfolloupvisitlistDTOList1.add(objnewpatientfolloupvisitlistDTO);
            }
            respMap.put("newlist", newpatientfolloupvisitlistDTOList1);
            System.out.println("Mainquery" + NewNFolMainQuery);
            List<Object[]> listNewpatientFolloupVis2 = entityManager.createNativeQuery(NewNFolMainQuery + " ) as newvisitFolTot group by newvisitFolTot.RFUnitName").getResultList();
            for (Object[] obj : listNewpatientFolloupVis2) {
                NewPatientFolloupVisitListDTO objnewpatientfolloupvisitlistDTO = new NewPatientFolloupVisitListDTO();
                objnewpatientfolloupvisitlistDTO.setUnitName("" + obj[0]);
                objnewpatientfolloupvisitlistDTO.setTotalvismale("" + obj[1]);
                objnewpatientfolloupvisitlistDTO.setTotalvisfemale("" + obj[2]);
                objnewpatientfolloupvisitlistDTO.setTotalvisother("" + obj[3]);
                newpatientfolloupvisitlistDTOList2.add(objnewpatientfolloupvisitlistDTO);
            }
            respMap.put("RegfollowCal", newpatientfolloupvisitlistDTOList2);

        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("Error:" + e);
        }
        return respMap;
    }

}
