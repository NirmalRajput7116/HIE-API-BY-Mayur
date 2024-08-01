package com.cellbeans.hspa.outboundcarecoordination;

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
@RequestMapping("/outbound_carecoordination")
public class TrnOutboundCareCoordinationController {

    Map<String, String> respMap = new HashMap<String, String>();

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    TrnOutboundCareCoordinationRepository trnOutboundCareCoordinationRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnOutboundCareCoordination trnOutboundCareCoordination) {
        TenantContext.setCurrentTenant(tenantName);
        trnOutboundCareCoordinationRepository.save(trnOutboundCareCoordination);
        respMap.put("success", "1");
        respMap.put("msg", "Added Successfully");
        return respMap;
    }

    @RequestMapping("getoutboundcalllist")
    public Map<String, Object> getoutboundcalllist(@RequestBody SearchoutboundccDTO SearchoutboundccDTO) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        if (SearchoutboundccDTO.getFromdate().equals("") || SearchoutboundccDTO.getFromdate().equals("null")) {
            SearchoutboundccDTO.setFromdate(strDate);
        }
        if (SearchoutboundccDTO.getTodate().equals("") || SearchoutboundccDTO.getTodate().equals("null")) {
//            todate = strDate;
            SearchoutboundccDTO.setTodate(strDate);
        }
        String query = " SELECT toc.outboundcc_id,mp.patient_mr_no,mss.user_age,mss.user_dob,msu.user_mobile, " +
                " CONCAT(IFNULL(mt.title_name,''),' ', IFNULL(mss.user_firstname,''),' ', IFNULL(mss.user_lastname,'')) AS patient_name, " +
                " CONCAT(IFNULL(mt.title_name,''),' ', IFNULL(msu.user_firstname,''),' ', IFNULL(msu.user_lastname,'')) AS user_name," +
                " toc.outbound_query,toc.outbound_responce,toc.outbounddescription,mst.service_type_name,mct.call_category_name," +
                " msb.ccs_name,rsb.resolution_category_name,mg.gender_name,toc.created_date, " +
                " IFNULL(ncfc.cfc_id, '') as cfc_id, IFNULL(ncf.cf_id, '') as cf_id, IFNULL(ncfc.cfc_name, '') as cfc_name, IFNULL(ncf.cf_name,'')as cf_name, IFNULL(mv.visit_id, '') as visit_id, toc.created_date as date, " +
                " mu.unit_name" +
                " FROM trn_outbound_cc toc" +
                " LEFT JOIN memr_servicetype mst ON mst.service_type_id = toc.outboundservice_type_id" +
                " LEFT JOIN memr_callcategory mct ON mct.call_category_id = toc.outboundcall_category_id" +
                " LEFT JOIN memr_call_sub_category msb ON msb.csc_id = toc.outboundcallsubcategory" +
                " LEFT JOIN memr_resolutioncategory rsb ON rsb.resolution_category_id = toc.outboundresolution_category_id" +
                " LEFT JOIN mst_unit mu ON mu.unit_id = toc.outboundcc_unit_id" +
                " LEFT JOIN mst_staff ms ON ms.staff_id = toc.outboundcc_staff_id" +
                " LEFT JOIN mst_user msu ON msu.user_id = ms.staff_user_id" +
                " LEFT JOIN mst_title mt ON msu.user_title_id=msu.user_id" +
                " LEFT JOIN mst_patient mp ON mp.patient_id = toc.outboundcc_patient_id" +
                " LEFT JOIN mst_user mss ON mss.user_id=mp.patient_user_id" +
                " LEFT JOIN mst_gender mg ON mg.gender_id=mss.user_gender_id " +
                " LEFT JOIN ncli_clinical_form_category ncfc ON ncfc.cfc_id = toc.outbound_clinical_from_category_id " +
                " LEFT JOIN ncli_clinical_form ncf ON ncf.cf_id = toc.outbound_clinical_form_id " +
                " LEFT JOIN mst_visit mv ON mv.visit_id = toc.outbound_visit_id ";
        if(!SearchoutboundccDTO.getUserFirstname().equals("")){
            query += "  where (mss.user_firstname like '%" + SearchoutboundccDTO.getUserFirstname() + "%' or mss.user_lastname like '%" + SearchoutboundccDTO.getUserFirstname() + "%'  ) ";
        }


        if(!SearchoutboundccDTO.getUserFirstname().equals("")){
            query += "  and (mss.user_firstname like '%" + SearchoutboundccDTO.getUserFirstname() + "%' or mss.user_lastname like '%" + SearchoutboundccDTO.getUserFirstname() + "%'  ) ";
        }


        if (!SearchoutboundccDTO.getStaffId().equals("0") && SearchoutboundccDTO.getStaffId() != null && !SearchoutboundccDTO.getStaffId().equals("")) {
            query += " AND ms.staff_user_id =" + SearchoutboundccDTO.getStaffId() + " ";
        }

        query += " And date(toc.created_date) BETWEEN '" + SearchoutboundccDTO.getFromdate() + "' and '" + SearchoutboundccDTO.getTodate() + "' ";

        query += " order by toc.outboundcc_id desc ";

        String Cquery = "select count(*) from ( " + query + " ) as combine ";

        query += " limit " + ((SearchoutboundccDTO.getOffset() - 1) * SearchoutboundccDTO.getLimit()) + "," + SearchoutboundccDTO.getLimit();
        System.out.println("query ==>" + query);
        List<Object[]> queue = entityManager.createNativeQuery(query).getResultList();
        BigInteger cc = (BigInteger) entityManager.createNativeQuery(Cquery).getSingleResult();
        Map<String, Object> map = new HashMap<>();
        map.put("content", queue);
        map.put("count", cc);
        return map;

    }
}
