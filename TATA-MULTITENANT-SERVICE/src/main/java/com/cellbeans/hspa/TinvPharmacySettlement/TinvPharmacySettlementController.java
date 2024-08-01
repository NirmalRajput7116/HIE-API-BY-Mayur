package com.cellbeans.hspa.TinvPharmacySettlement;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.mstvisit.MstVisitDTO;
import com.cellbeans.hspa.tinvitemsalesreturnitem.TinvItemSalesReturnItem;
import com.cellbeans.hspa.tinvpharmacysale.TinvPharmacySale;
import com.cellbeans.hspa.tinvpharmacysale.TinvPharmacySaleRepository;
import com.cellbeans.hspa.tinvpharmacysale.tinvPharmacySaleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_pharmacy_settlement")
public class TinvPharmacySettlementController {

    @Autowired
    TinvPharmacySaleRepository tinvPharmacySaleRepository;
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    CreateJSONObject createJSONObject;
    @Autowired
    TinvPharmacyBillSettlementRepository tinvPharmacyBillSettlementRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvPharmacyBillSettlement tinvPharmacyBillSettlement) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvPharmacyBillSettlement.getPbsPsId().size() != 0) {
            tinvPharmacyBillSettlementRepository.save(tinvPharmacyBillSettlement);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("listByUnitId")
    public Iterable<TinvPharmacyBillSettlement> listByUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "pbs_id") String col, @RequestParam(value = "unitId", required = false) long unitId, @RequestParam(value = "listTypePharmacy") Integer listTypePharmacy) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tinvPharmacyBillSettlementRepository.findAllByPbsUnitIdUnitIdAndPbsPsIdPharmacyType(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)), unitId, listTypePharmacy);

        } else {
            return tinvPharmacyBillSettlementRepository.findAllByPbsUnitIdAndPbsUnitIdUnitId(qString, unitId, listTypePharmacy, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)));
        }

    }

    @GetMapping
    @RequestMapping("getPatientSearchInPharmacy")
    public List<MstVisitDTO> getPatientSearchInPharmacy(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "unitId", defaultValue = "") long unitId, @RequestParam(value = "usePharmacy") Integer usePharmacy) {
        TenantContext.setCurrentTenant(tenantName);
        if (usePharmacy == 1) {
            return tinvPharmacySaleRepository.findByPatientDetailsbyUnitIDAndSearchWithIpdNo(unitId, 1, qString, qString, qString, qString, PageRequest.of(0, 20));
        } else {
            return tinvPharmacySaleRepository.findByPatientDetailsbyUnitIDAndSearch(unitId, 0, qString, qString, qString, qString, PageRequest.of(0, 20));
        }
    }

    @RequestMapping("getPharmacyCreditListByUnitId")
    public List<tinvPharmacySaleDTO> getPharmacyListByUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "visitId", required = false) String visitId, @RequestParam(value = "unitId", defaultValue = "") long unitId, @RequestParam(value = "type", required = false) int type) throws ParseException {
        TenantContext.setCurrentTenant(tenantName);
        List<tinvPharmacySaleDTO> tinvPharmacySaleList = new ArrayList<tinvPharmacySaleDTO>();
        try {
            if (type == 0) {
                String query = "SELECT i.ps_id,i.ps_name,ifnull(p.patient_mr_no,'') as patient_mr_no,i.ps_patient_first_name,ifnull(i.ps_patient_middle_name,'') as ps_patient_middle_name,i.ps_patient_last_name,g.gender_name,i.ps_concsssion_percentage,i.ps_concsssion_amount,ifnull(i.ps_concession_reason,'') as ps_concession_reason,ifnull(i.ps_no_of_items,'') as ps_no_of_items, " + "i.ps_total_amount,i.ps_net_amount,i.ps_out_standing_amount_for_patient,i.ps_out_standing_amount_for_company,i.ps_received_amount_for_company,i.ps_tax_amount,ifnull(i.ps_patient_address,'') as ps_patient_address,CAST(i.item_delivered AS UNSIGNED) as item_delivered,ifnull(i.ps_patient_mobile_no,'') as ps_patient_mobile_no,i.ps_co_pay_from_tarrif,i.ps_discount_from_tarrif,i.ps_patient_pay_from_from_tarrif, " + "i.ps_ref_doctor,date(i.created_date) as created_date,date(i.last_modified_date) as last_modified_date,i.pharmacy_unit_id,g.gender_id,ifnull(p.patient_id,0) as patient_id,ifnull(v.visit_tariff_id,0) as visit_tariff_id ,0,'' " + " FROM tinv_pharmacy_sale i,mst_visit v,mst_patient p,mst_gender g where v.visit_patient_id=p.patient_id and i.ps_gender_id=g.gender_id and i.ps_visit_id=v.visit_id and  i.ps_visit_id=" + visitId + " and i.ps_out_standing_amount_for_patient>0 and i.pharmacy_unit_id=" + unitId + " and i.pharmacy_type=" + type + " order by ps_id desc ";
                List<Object[]> listObj = entityManager.createNativeQuery(query).getResultList();
                for (Object temp[] : listObj) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    tinvPharmacySaleDTO obj = new tinvPharmacySaleDTO(Long.parseLong(temp[0].toString()), Long.parseLong(temp[1].toString()), temp[2].toString(), temp[3].toString(), temp[4].toString(), temp[5].toString(), temp[6].toString(), Double.valueOf(temp[7].toString()), Double.valueOf(temp[8].toString()), temp[9].toString(), temp[10].toString(), Double.valueOf(temp[11].toString()), Double.valueOf(temp[12].toString()), Double.valueOf(temp[13].toString()), Double.valueOf(temp[14].toString()), Double.valueOf(temp[15].toString()), Double.valueOf(temp[16].toString()), temp[17].toString(), Integer.valueOf(temp[18].toString()), temp[19].toString(), Double.valueOf(temp[20].toString()), Double.valueOf(temp[21].toString()), Double.valueOf(temp[22].toString()), temp[23].toString(), format.parse(temp[24].toString()), format.parse(temp[25].toString()), Long.parseLong(temp[26].toString()), Long.parseLong(temp[27].toString()), Long.parseLong(temp[28].toString()), Long.parseLong(temp[29].toString()), Long.parseLong(temp[30].toString()), temp[31].toString());
                    tinvPharmacySaleList.add(obj);
                }
            } else {
                String query = "SELECT i.ps_id,i.ps_name,ifnull(p.patient_mr_no,'') as patient_mr_no,i.ps_patient_first_name,ifnull(i.ps_patient_middle_name,'') as ps_patient_middle_name,i.ps_patient_last_name,g.gender_name,i.ps_concsssion_percentage,i.ps_concsssion_amount,ifnull(i.ps_concession_reason,'') as ps_concession_reason,ifnull(i.ps_no_of_items,'') as ps_no_of_items, " + "i.ps_total_amount,i.ps_net_amount,i.ps_out_standing_amount_for_patient,i.ps_out_standing_amount_for_company,i.ps_received_amount_for_company,i.ps_tax_amount,ifnull(i.ps_patient_address,'') as ps_patient_address,CAST(i.item_delivered AS UNSIGNED) as item_delivered,ifnull(i.ps_patient_mobile_no,'') as ps_patient_mobile_no,i.ps_co_pay_from_tarrif,i.ps_discount_from_tarrif,i.ps_patient_pay_from_from_tarrif, " + "i.ps_ref_doctor,date(i.created_date) as created_date,date(i.last_modified_date) as last_modified_date,i.pharmacy_unit_id,g.gender_id,ifnull(p.patient_id,0) as patient_id,ifnull(v.visit_tariff_id,0) as visit_tariff_id ,ifnull(a.admission_id,0) as admission_id,ifnull(a.admission_ipd_no,'') as admission_ipd_no " + " FROM tinv_pharmacy_sale i,trn_admission a,mst_visit v,mst_patient p,mst_gender g where i.ps_admission_id=a.admission_id and a.admission_patient_id=p.patient_id and i.ps_gender_id=g.gender_id and i.ps_visit_id=v.visit_id and admission_status=0 and  i.ps_admission_id=" + visitId + " and i.ps_out_standing_amount_for_patient>0 and i.pharmacy_type=" + type + " and i.pharmacy_unit_id=" + unitId + " order by i.ps_id desc ";
                List<Object[]> listObj = entityManager.createNativeQuery(query).getResultList();
                for (Object temp[] : listObj) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    tinvPharmacySaleDTO obj = new tinvPharmacySaleDTO(Long.parseLong(temp[0].toString()), Long.parseLong(temp[1].toString()), temp[2].toString(), temp[3].toString(), temp[4].toString(), temp[5].toString(), temp[6].toString(), Double.valueOf(temp[7].toString()), Double.valueOf(temp[8].toString()), temp[9].toString(), temp[10].toString(), Double.valueOf(temp[11].toString()), Double.valueOf(temp[12].toString()), Double.valueOf(temp[13].toString()), Double.valueOf(temp[14].toString()), Double.valueOf(temp[15].toString()), Double.valueOf(temp[16].toString()), temp[17].toString(), Integer.valueOf(temp[18].toString()), temp[19].toString(), Double.valueOf(temp[20].toString()), Double.valueOf(temp[21].toString()), Double.valueOf(temp[22].toString()), temp[23].toString(), format.parse(temp[24].toString()), format.parse(temp[25].toString()), Long.parseLong(temp[26].toString()), Long.parseLong(temp[27].toString()), Long.parseLong(temp[28].toString()), Long.parseLong(temp[29].toString()), Long.parseLong(temp[30].toString()), temp[31].toString());
                    tinvPharmacySaleList.add(obj);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tinvPharmacySaleList;
    }

    @GetMapping
    @RequestMapping("getReturnItemListByPsId")
    public List<TinvItemSalesReturnItem> getReturnItemListByPsId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "isrPsId", required = false) long isrPsId) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "select si from TinvItemSalesReturnItem si where si.isriIsrId.isrPsId.psId=" + isrPsId;
        return entityManager.createQuery(query, TinvItemSalesReturnItem.class).getResultList();
    }

    @GetMapping
    @RequestMapping("getPatientSearchInPharmacyEdit")
    //// @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity getPatientSearchInPharmacyEdit(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "unitId", defaultValue = "") long unitId, @RequestParam(value = "pharmacyType", defaultValue = "0") int pharmacyType) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "select ps.ps_patient_first_name,ifnull(ps.ps_patient_middle_name,'') as ps_patient_middle_name,ps.ps_patient_last_name,ifnull(ps.ps_patient_mobile_no,'') as ps_patient_mobile_no,p.patient_mr_no,ifnull(u.user_age,0) as user_age,g.gender_name,ps.ps_visit_id,ifnull(ps.ps_admission_id,0) as ps_admission_id " + " from tinv_pharmacy_sale ps,mst_visit v,mst_patient p,mst_user u,mst_gender g where ps.ps_visit_id=v.visit_id and v.visit_patient_id=p.patient_id and p.patient_user_id=u.user_id and u.user_gender_id=g.gender_id and " + "  ps.is_active=1 and ps.is_deleted=0 and (ps.ps_patient_first_name like '%" + qString + "%' or ps.ps_patient_last_name like '%" + qString + "%' or ps.ps_patient_mobile_no like '%" + qString + "%' or p.patient_mr_no like '%" + qString + "%') and ps.pharmacy_type=" + pharmacyType + " and ps.pharmacy_unit_id=" + unitId + " order by ps.created_date desc limit 10 ";
        String col = "ps_patient_first_name,ps_patient_middle_name,ps_patient_last_name,ps_patient_mobile_no,patient_mr_no,user_age,gender_name,ps_visit_id,ps_admission_id";
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(col, query, null));
    }

    @RequestMapping("getPharmacyListForEdit")
    public List<TinvPharmacySale> getPharmacyListForEdit(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "Id") String Id, @RequestParam(value = "unitId", defaultValue = "") long unitId, @RequestParam(value = "pharmacyType", defaultValue = "0") int pharmacyType) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "select ps from TinvPharmacySale ps where ps.isActive=1 and ps.isDeleted=0 and ps.pharmacyUnitId.unitId=" + unitId;
        if (pharmacyType == 1) {
            query += " and ps.psAdmissionId.admissionId=" + Id + " and ps.pharmacyType=1";
        } else {
            query += " and ps.psVisitId.visitId=" + Id + " and ps.pharmacyType=0";
        }
        return entityManager.createQuery(query, TinvPharmacySale.class).getResultList();
    }

    @RequestMapping("updatePharmacySalesData")
    public Map<String, String> updatePharmacySalesData(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvPharmacySale> tinvPharmacySaleList) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvPharmacySaleList.size() != 0) {
            tinvPharmacySaleRepository.saveAll(tinvPharmacySaleList);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Update Data");
            return respMap;
        }
    }

}
