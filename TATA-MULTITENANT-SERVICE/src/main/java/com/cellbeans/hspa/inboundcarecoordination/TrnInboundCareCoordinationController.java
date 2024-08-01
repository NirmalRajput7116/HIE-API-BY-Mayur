package com.cellbeans.hspa.inboundcarecoordination;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inbound_carecoordination")
public class TrnInboundCareCoordinationController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    TrnInboundCareCoordinationRepository trnInboundCareCoordinationRepository;

    @PersistenceContext
    EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnInboundCareCoordination trnInboundCareCoordination) {
        TenantContext.setCurrentTenant(tenantName);
        trnInboundCareCoordinationRepository.save(trnInboundCareCoordination);
        respMap.put("success", "1");
        respMap.put("msg", "Added Successfully");
        return respMap;
    }

//    @RequestMapping("update")
//    public Map<String, String> update(@RequestBody TrnInboundCareCoordination trnInboundCareCoordination) {
//        trnInboundCareCoordinationRepository.save(trnInboundCareCoordination);
//        respMap.put("success", "1");
//        respMap.put("msg", "Added Successfully");
//        return respMap;
//    }

    @RequestMapping("getinboundcalllist")
    public Map<String, Object> getinboundcalllist(@RequestBody SearchinboundccDTO searchinboundccDTO) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        if (searchinboundccDTO.getFromdate().equals("") || searchinboundccDTO.getFromdate().equals("null")) {
            searchinboundccDTO.setFromdate(strDate);
        }
        if (searchinboundccDTO.getTodate().equals("") || searchinboundccDTO.getTodate().equals("null")) {
//            todate = strDate;
            searchinboundccDTO.setTodate(strDate);
        }
        String query = " SELECT mu.unit_id,tic.inboundcc_id,mu.unit_name,mp.patient_mr_no,mss.user_age,mss.user_dob,mss.user_mobile," +
                " CONCAT(ifnull(mt.title_name,''),' ',ifnull(mss.user_firstname,'') ,' ',ifnull(mss.user_lastname,''))as patient_name ," +
                " CONCAT(ifnull(mt.title_name,''),' ',ifnull(msu.user_firstname,'') ,' ',ifnull(msu.user_lastname,''))as user_name ," +
                " tic.created_date, ifnull(tic.inbound_responce,'') AS responce,ifnull(tic.inbound_query,'') AS inquery," +
                " ifnull(tic.inbounddescription,'') AS description,ifnull(co.call_outcome_name,'') AS calloutcome," +
                " ifnull(cr.care_activity_name,'') AS careactivity,ifnull(cs.channel_source_name,'') AS chanelsource," +
                " ifnull(ity.issue_type_name,'') AS issuetype,mg.gender_name, " +
                " IFNULL(ncfc.cfc_id, '') AS cfc_id, IFNULL(ncf.cf_id, '') AS cf_id, " +
                " IFNULL(ncfc.cfc_name, '')AS cfc_name, IFNULL(ncf.cf_name,'') AS cf_name, IFNULL(mv.visit_id, '') AS visit_id " +
                " FROM trn_inbound_cc tic" +
                " LEFT JOIN memr_calloutcome co ON co.call_outcome_id = tic.inboundcall_outcome_id" +
                " LEFT JOIN memr_careactivity cr ON cr.care_activity_id = tic.inboundcare_activity_id" +
                " left JOIN memr_channelsource cs ON cs.channel_source_id = tic.inboundchannel_source_id" +
                " left JOIN memr_issuetype ity ON ity.issue_type_id = tic.inboundissue_type_id " +
                " LEFT JOIN mst_unit mu ON mu.unit_id = tic.inboundcc_unit_id" +
                " LEFT JOIN mst_staff ms ON ms.staff_id = tic.inboundcc_staff_id" +
                " LEFT JOIN mst_user msu ON msu.user_id = ms.staff_user_id" +
                " LEFT JOIN mst_title mt ON msu.user_title_id=msu.user_id" +
                " LEFT JOIN mst_patient mp ON mp.patient_id = tic.inboundcc_patient_id" +
                " LEFT JOIN mst_user mss ON mss.user_id=mp.patient_user_id" +
                " LEFT JOIN mst_gender mg ON mg.gender_id=mss.user_gender_id   " +
                " LEFT JOIN ncli_clinical_form_category ncfc ON ncfc.cfc_id = tic.inbound_clinical_from_category_id " +
                " LEFT JOIN ncli_clinical_form ncf ON ncf.cf_id = tic.inbound_clinical_form_id " +
                " left JOIN mst_visit mv ON mv.visit_id = tic.inbound_visit_id ";

        if(!searchinboundccDTO.getUserFirstname().equals("")){
            query += "  where (mss.user_firstname like '%" + searchinboundccDTO.getUserFirstname() + "%' or mss.user_lastname like '%" + searchinboundccDTO.getUserFirstname() + "%'  ) ";
        }

        if (!searchinboundccDTO.getStaffId().equals("0") && searchinboundccDTO.getStaffId() != null && !searchinboundccDTO.getStaffId().equals("")) {
            query += " AND ms.staff_user_id =" + searchinboundccDTO.getStaffId() + " ";
        }

        query += " And date(tic.created_date) BETWEEN '" + searchinboundccDTO.getFromdate() + "' and '" + searchinboundccDTO.getTodate() + "' ";


        query += " order by tic.inboundcc_id desc ";

        String Cquery = "select count(*) from ( " + query + " ) as combine ";

        query += " limit " + ((searchinboundccDTO.getOffset() - 1) * searchinboundccDTO.getLimit()) + "," + searchinboundccDTO.getLimit();
        System.out.println("query ==>" + query);
        List<Object[]> queue = entityManager.createNativeQuery(query).getResultList();
        BigInteger cc = (BigInteger) entityManager.createNativeQuery(Cquery).getSingleResult();
        Map<String, Object> map = new HashMap<>();
        map.put("content", queue);
        map.put("count", cc);
        return map;

    }


}
