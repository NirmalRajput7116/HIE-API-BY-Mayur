package com.cellbeans.hspa.tinvpharmacysale;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.temrvisitprescription.TemrVisitPrescription;
import com.cellbeans.hspa.temrvisitprescription.TemrVisitPrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/tinv_pharmacy_sale")
public class TinvPharmacySaleController {
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TinvPharmacySaleRepository tinvPharmacySaleRepository;
    @Autowired
    TemrVisitPrescriptionRepository temrVisitPrescriptionRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvPharmacySale tinvPharmacySale) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvPharmacySale != null) {
            System.out.println("created by : " + tinvPharmacySale.getCreatedBy());
            tinvPharmacySaleRepository.save(tinvPharmacySale);
            respMap.put("psId", Long.toString(tinvPharmacySale.getPsId()));
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<TinvPharmacySale> records;
        records = tinvPharmacySaleRepository.findByPsVisitIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{psId}")
    public TinvPharmacySale read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("psId") Long psId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPharmacySale tinvPharmacySale = tinvPharmacySaleRepository.getById(psId);
        return tinvPharmacySale;
    }

    @RequestMapping("update")
    public TinvPharmacySale update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvPharmacySale tinvPharmacySale) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvPharmacySaleRepository.save(tinvPharmacySale);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TinvPharmacySale> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "psId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tinvPharmacySaleRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tinvPharmacySaleRepository.findByPsNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @GetMapping
    @RequestMapping("listByUnitId")
    public Iterable<TinvPharmacySale> listByUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "psId") String col, @RequestParam(value = "unitId", required = false) long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tinvPharmacySaleRepository.findAllByPharmacyUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)), unitId);

        } else {
            return tinvPharmacySaleRepository.findByPsNameContainsAndPharmacyUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(qString, unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{psId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("psId") Long psId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPharmacySale tinvPharmacySale = tinvPharmacySaleRepository.getById(psId);
        if (tinvPharmacySale != null) {
            tinvPharmacySale.setIsDeleted(true);
            tinvPharmacySaleRepository.save(tinvPharmacySale);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("updateoutstanding")
    public Map<String, String> updatingOutStandingAmounr(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvPharmacySale tinvPharmacySale) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPharmacySale tinv = tinvPharmacySaleRepository.getById(tinvPharmacySale.getPsId());
        if (tinv != null) {
            tinv.setPsOutStandingAmountForPatient(tinvPharmacySale.getPsOutStandingAmountForPatient());
            tinvPharmacySaleRepository.save(tinv);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @PostMapping
    @RequestMapping("closePrescription")
    public Map<String, String> bytimelineid(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TemrVisitPrescription> temrVisitPrescriptionList) {
        TenantContext.setCurrentTenant(tenantName);
        List<TemrVisitPrescription> listUpadte = new ArrayList<TemrVisitPrescription>();
        try {
            for (TemrVisitPrescription obj : temrVisitPrescriptionList) {
                TemrVisitPrescription prescriptionsObj = temrVisitPrescriptionRepository.findAllByVpIdAndClosePrescriptionFalseAndIsActiveTrueAndIsDeletedFalse(obj.getVpId());
                prescriptionsObj.setClosePrescription(true);
                listUpadte.add(prescriptionsObj);
            }
            temrVisitPrescriptionRepository.saveAll(listUpadte);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");

        } catch (Exception e) {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;

    }

    @RequestMapping("isdelivery/{psId}")
    public Map<String, String> updateDrugeListbyhisStatus(@RequestHeader("X-tenantId") String tenantName, @PathVariable("psId") Long psId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPharmacySale tinvPharmacySaleItem = tinvPharmacySaleRepository.getById(psId);
        if (tinvPharmacySaleItem != null) {
            if (tinvPharmacySaleItem.getItemDelivered()) {
                tinvPharmacySaleItem.setItemDelivered(false);
            } else {
                tinvPharmacySaleItem.setItemDelivered(true);
            }
            tinvPharmacySaleRepository.save(tinvPharmacySaleItem);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;

    }

    @RequestMapping("search")
    public List<TinvPharmacySale> getListOfItemsDependsOnSerch(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchPharmacyBillList searchPharmacyBillList) throws ParseException {
        TenantContext.setCurrentTenant(tenantName);
        if (searchPharmacyBillList == null) {
            SearchPharmacyBillList sb = new SearchPharmacyBillList();
            searchPharmacyBillList = sb;
        }
        String queryInya = "";
        String forTeriff = "";
        if (searchPharmacyBillList.getSearchMrNo() == null)
            searchPharmacyBillList.setSearchMrNo("");
        if (searchPharmacyBillList.getSearchPatientName() == null)
            searchPharmacyBillList.setSearchPatientName("");
        if (searchPharmacyBillList.getSearchMobileNo() == null)
            searchPharmacyBillList.setSearchMobileNo("");
        if (searchPharmacyBillList.getSearchFromDate() == null) {
            String sDate1 = "1998/12/31";
            searchPharmacyBillList.setSearchFromDate(sDate1);
        }
        if (searchPharmacyBillList.getSearchToDate() == null) {
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchPharmacyBillList.setSearchToDate(date);
        }
        if (searchPharmacyBillList.getSearchmbillTariffId().getTariffId() != 0)
            forTeriff = " AND i.psTariffId.tariffId=" + searchPharmacyBillList.getSearchmbillTariffId().getTariffId() + " ";
        queryInya = "Select i from TinvPharmacySale i Where i.psVisitId.visitPatientId.patientMrNo like '%" + searchPharmacyBillList.getSearchMrNo() + "%' And ( i.psPatientFirstName like '%" + searchPharmacyBillList.getSearchPatientName() + "%' Or  i.psPatientMiddleName like '%" + searchPharmacyBillList.getSearchPatientName() + "%' Or  i.psPatientLastName like '%" + searchPharmacyBillList.getSearchPatientName() + "%' ) AND  i.psPatientMobileNo like '%" + searchPharmacyBillList.getSearchMobileNo() + "%' AND date(i.createdDate) between   " + " ' " + searchPharmacyBillList.getSearchFromDate() + " ' AND  " + "'" + searchPharmacyBillList.getSearchToDate() + " ' " + forTeriff + "  order by i.psId desc  ";
        // System.out.println(queryInya);
        long count = (Long) entityManager.createQuery("Select count(i.psId) from TinvPharmacySale i Where i.psVisitId.visitPatientId.patientMrNo like '%" + searchPharmacyBillList.getSearchMrNo() + "%' And ( i.psPatientFirstName like '%" + searchPharmacyBillList.getSearchPatientName() + "%' Or  i.psPatientMiddleName like '%" + searchPharmacyBillList.getSearchPatientName() + "%' Or  i.psPatientLastName like '%" + searchPharmacyBillList.getSearchPatientName() + "%') AND  i.psPatientMobileNo like '%" + searchPharmacyBillList.getSearchMobileNo() + "%' AND  date(i.createdDate) between  " + " ' " + searchPharmacyBillList.getSearchFromDate() + " '  AND " + " ' " + searchPharmacyBillList.getSearchToDate() + "  '  " + forTeriff + "  order by i.psId desc  ").getSingleResult();
        List<TinvPharmacySale> tinvPharmacySaleList = entityManager.createQuery(queryInya, TinvPharmacySale.class).setFirstResult(searchPharmacyBillList.getOffset()).setMaxResults(searchPharmacyBillList.getLimit()).getResultList();
        if (tinvPharmacySaleList.size() > 0)
            tinvPharmacySaleList.get(0).setCount(count);
        return tinvPharmacySaleList;
    }

    /*  @RequestMapping("getPharmacyListByUnitId")
      public List<tinvPharmacySaleDTO> getPharmacyListByUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchPharmacyBillList searchPharmacyBillList)
              throws ParseException {
          List<tinvPharmacySaleDTO> tinvPharmacySaleList = new ArrayList<tinvPharmacySaleDTO>();
          try {
              long count = 0;
              String queryInya = "";
              System.out.println("pharmacy type" + searchPharmacyBillList.getPharmacyType());
              if (searchPharmacyBillList.getPharmacyType() == 0) {
                  queryInya = "SELECT i.ps_id,i.ps_name,ifnull(p.patient_mr_no,'') as patient_mr_no,i.ps_patient_first_name,ifnull(i.ps_patient_middle_name,'') as ps_patient_middle_name,i.ps_patient_last_name,g.gender_name,i.ps_concsssion_percentage,i.ps_concsssion_amount,ifnull(i.ps_concession_reason,'') as ps_concession_reason,ifnull(i.ps_no_of_items,'') as ps_no_of_items, "
                          + " ifnull(i.ps_total_amount,0) as ps_total_amount,ifnull(i.ps_net_amount,0) as ps_net_amount,ifnull(i.ps_out_standing_amount_for_patient,0) as ps_out_standing_amount_for_patient,ifnull(i.ps_out_standing_amount_for_company,0) as ps_out_standing_amount_for_company,ifnull(i.ps_received_amount_for_company,0) as ps_received_amount_for_company,ifnull(i.ps_tax_amount,0) as ps_tax_amount,ifnull(i.ps_patient_address,'') as ps_patient_address,CAST(i.item_delivered AS UNSIGNED) as item_delivered,ifnull(i.ps_patient_mobile_no,'') as ps_patient_mobile_no,i.ps_co_pay_from_tarrif,i.ps_discount_from_tarrif,i.ps_patient_pay_from_from_tarrif, "
                          + " ifnull(i.ps_ref_doctor,'') as ps_ref_doctor,date(i.created_date) as created_date,date(i.last_modified_date) as last_modified_date,i.pharmacy_unit_id,g.gender_id,ifnull(p.patient_id,0) as patient_id,ifnull(v.visit_tariff_id,0) as visit_tariff_id,0,'' "
                          + " FROM tinv_pharmacy_sale i left join mst_visit v on i.ps_visit_id=v.visit_id left join  mst_patient p on v.visit_patient_id=p.patient_id left join mst_gender g on i.ps_gender_id=g.gender_id where i.pharmacy_unit_id="
                          + searchPharmacyBillList.getUnitId() + " and i.pharmacy_type="
                          + searchPharmacyBillList.getPharmacyType()
                          + " and i.ps_admission_id is null order by ps_id desc limit "
                          + ((searchPharmacyBillList.getOffset() - 1) * searchPharmacyBillList.getLimit()) + ","
                          + searchPharmacyBillList.getLimit();
                  BigInteger temp1 = (BigInteger) entityManager
                          .createNativeQuery(
                                  "select count(i.ps_id) FROM tinv_pharmacy_sale i left join mst_visit v on i.ps_visit_id=v.visit_id left join  mst_patient p on v.visit_patient_id=p.patient_id left join mst_gender g on i.ps_gender_id=g.gender_id where i.pharmacy_unit_id="
                                          + searchPharmacyBillList.getUnitId() + " and i.pharmacy_type="
                                          + searchPharmacyBillList.getPharmacyType() + " and i.ps_admission_id is null")
                          .getSingleResult();
                  count = temp1.longValue();
              } else {
                  queryInya = "SELECT i.ps_id,i.ps_name,ifnull(p.patient_mr_no,'') as patient_mr_no,i.ps_patient_first_name,ifnull(i.ps_patient_middle_name,'') as ps_patient_middle_name,i.ps_patient_last_name,g.gender_name,i.ps_concsssion_percentage,i.ps_concsssion_amount,ifnull(i.ps_concession_reason,'') as ps_concession_reason,ifnull(i.ps_no_of_items,'') as ps_no_of_items, "
                          + " ifnull(i.ps_total_amount,0) as ps_total_amount,ifnull(i.ps_net_amount,0) as ps_net_amount,ifnull(i.ps_out_standing_amount_for_patient,0) as ps_out_standing_amount_for_patient,ifnull(i.ps_out_standing_amount_for_company,0) as ps_out_standing_amount_for_company,ifnull(i.ps_received_amount_for_company,0) as ps_received_amount_for_company,ifnull(i.ps_tax_amount,0) as ps_tax_amount,ifnull(i.ps_patient_address,'') as ps_patient_address,CAST(i.item_delivered AS UNSIGNED) as item_delivered,ifnull(i.ps_patient_mobile_no,'') as ps_patient_mobile_no,i.ps_co_pay_from_tarrif,i.ps_discount_from_tarrif,i.ps_patient_pay_from_from_tarrif, "
                          + " ifnull(i.ps_ref_doctor,'') as ps_ref_doctor,date(i.created_date) as created_date,date(i.last_modified_date) as last_modified_date,i.pharmacy_unit_id,g.gender_id,ifnull(p.patient_id,0) as patient_id,ifnull(v.visit_tariff_id,0) as visit_tariff_id,ifnull(a.admission_id,0) as admission_id,ifnull(a.admission_ipd_no,'') as admission_ipd_no "
                          + " FROM tinv_pharmacy_sale i,trn_admission a,mst_visit v,mst_patient p,mst_gender g where i.ps_admission_id=a.admission_id and a.admission_patient_id=p.patient_id and i.ps_gender_id=g.gender_id and i.ps_visit_id=v.visit_id  and i.pharmacy_unit_id="
                          + searchPharmacyBillList.getUnitId() + " and i.pharmacy_type="
                          + searchPharmacyBillList.getPharmacyType() + " order by ps_id desc limit "
                          + ((searchPharmacyBillList.getOffset() - 1) * searchPharmacyBillList.getLimit()) + ","
                          + searchPharmacyBillList.getLimit();
                  BigInteger temp1 = (BigInteger) entityManager.createNativeQuery(
                          "select count(i.ps_id) FROM tinv_pharmacy_sale i,trn_admission a,mst_visit v,mst_patient p,mst_gender g where i.ps_admission_id=a.admission_id and a.admission_patient_id=p.patient_id and i.ps_gender_id=g.gender_id and i.ps_visit_id=v.visit_id  and i.pharmacy_unit_id="
                                  + searchPharmacyBillList.getUnitId() + " and i.pharmacy_type="
                                  + searchPharmacyBillList.getPharmacyType())
                          .getSingleResult();
                  count = temp1.longValue();
              }

              System.out.println("queryInya : " + queryInya);

              List<Object[]> listObj = entityManager.createNativeQuery(queryInya).getResultList();
              for (Object temp[] : listObj) {
                  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                  System.out.println("26 : " + temp[26] + "27 : " + temp[27] + " 28 : " + temp[28] + " 29 : " + temp[29]);

                  String gender_id = null;
                  if (temp[27] == null) {
                      gender_id = "4";
                  } else {
                      gender_id = "" + temp[27];
                  }

                  System.out.println("gender_id : " + gender_id);

                  tinvPharmacySaleDTO obj = new tinvPharmacySaleDTO(Long.parseLong(temp[0].toString()),
                          Long.parseLong(temp[1].toString()), temp[2].toString(), "" + temp[3], "" + temp[4],
                          "" + temp[5], "" + temp[6], Double.valueOf(temp[7].toString()),
                          Double.valueOf(temp[8].toString()), temp[9].toString(), temp[10].toString(),
                          Double.valueOf(temp[11].toString()), Double.valueOf(temp[12].toString()),
                          Double.valueOf(temp[13].toString()), Double.valueOf(temp[14].toString()),
                          Double.valueOf(temp[15].toString()), Double.valueOf(temp[16].toString()), temp[17].toString(),
                          Integer.valueOf(temp[18].toString()), temp[19].toString(), Double.valueOf(temp[20].toString()),
                          Double.valueOf(temp[21].toString()), Double.valueOf(temp[22].toString()), temp[23].toString(),
                          format.parse(temp[24].toString()), format.parse(temp[25].toString()),
                          Long.parseLong("" + temp[26]), Long.parseLong(gender_id), Long.parseLong("" + temp[28]),
                          Long.parseLong(temp[29].toString()), Long.parseLong(temp[30].toString()), temp[31].toString());
                  tinvPharmacySaleList.add(obj);
              }
              if (tinvPharmacySaleList.size() > 0)
                  tinvPharmacySaleList.get(0).setCount(count);

          } catch (Exception e) {
              e.printStackTrace();
          }

          return tinvPharmacySaleList;
      }*/
    @RequestMapping("getPharmacyListByUnitId")
    public List<tinvPharmacySaleDTO> getPharmacyListByUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchPharmacyBillList searchPharmacyBillList) throws ParseException {
        TenantContext.setCurrentTenant(tenantName);
        List<tinvPharmacySaleDTO> tinvPharmacySaleList = new ArrayList<tinvPharmacySaleDTO>();
        try {
            long count = 0;
            String queryInya = "";
            if (searchPharmacyBillList.getPharmacyType() == 0) {
                queryInya = "SELECT i.ps_id,i.ps_name,ifnull(p.patient_mr_no,'') as patient_mr_no,i.ps_patient_first_name,ifnull(i.ps_patient_middle_name,'') as ps_patient_middle_name,i.ps_patient_last_name,g.gender_name,i.ps_concsssion_percentage,i.ps_concsssion_amount,ifnull(i.ps_concession_reason,'') as ps_concession_reason,ifnull(i.ps_no_of_items,'') as ps_no_of_items, " + " ifnull(i.ps_total_amount,0) as ps_total_amount,ifnull(i.ps_net_amount,0) as ps_net_amount,ifnull(i.ps_out_standing_amount_for_patient,0) as ps_out_standing_amount_for_patient,ifnull(i.ps_out_standing_amount_for_company,0) as ps_out_standing_amount_for_company,ifnull(i.ps_received_amount_for_company,0) as ps_received_amount_for_company,ifnull(i.ps_tax_amount,0) as ps_tax_amount,ifnull(i.ps_patient_address,'') as ps_patient_address,CAST(i.item_delivered AS UNSIGNED) as item_delivered,ifnull(i.ps_patient_mobile_no,'') as ps_patient_mobile_no,i.ps_co_pay_from_tarrif,i.ps_discount_from_tarrif,i.ps_patient_pay_from_from_tarrif, " + " ifnull(i.ps_ref_doctor,'') as ps_ref_doctor,date(i.created_date) as created_date,date(i.last_modified_date) as last_modified_date,i.pharmacy_unit_id,g.gender_id,ifnull(p.patient_id,0) as patient_id,ifnull(v.visit_tariff_id,0) as visit_tariff_id,IFNULL(0,0) AS admission_id, IFNULL(0,'') AS admission_ipd_no,u.user_age as age, case when  tt.isemrfinal then  tt.timeline_id  ELSE 'null' END AS timelineId " + " FROM tinv_pharmacy_sale i left join mst_visit v on i.ps_visit_id=v.visit_id LEFT join temr_timeline tt on tt.timeline_visit_id = v.visit_id left join  mst_patient p on v.visit_patient_id=p.patient_id left join mst_gender g on i.ps_gender_id=g.gender_id left join mst_user u on p.patient_user_id=u.user_id where i.pharmacy_unit_id=" + searchPharmacyBillList.getUnitId() + " and i.pharmacy_type=" + searchPharmacyBillList.getPharmacyType() + " and i.ps_admission_id is null GROUP BY v.visit_id order by ps_id desc limit " + ((searchPharmacyBillList.getOffset() - 1) * searchPharmacyBillList.getLimit()) + "," + searchPharmacyBillList.getLimit();
                BigInteger temp1 = (BigInteger) entityManager.createNativeQuery("select count(i.ps_id) FROM tinv_pharmacy_sale i left join mst_visit v on i.ps_visit_id=v.visit_id left join  mst_patient p on v.visit_patient_id=p.patient_id left join mst_gender g on i.ps_gender_id=g.gender_id where i.pharmacy_unit_id=" + searchPharmacyBillList.getUnitId() + " and i.pharmacy_type=" + searchPharmacyBillList.getPharmacyType() + " and i.ps_admission_id is null").getSingleResult();
                count = temp1.longValue();
            } else if (searchPharmacyBillList.getPharmacyType() == 1) {
                queryInya = "SELECT i.ps_id,i.ps_name,ifnull(p.patient_mr_no,'') as patient_mr_no,i.ps_patient_first_name,ifnull(i.ps_patient_middle_name,'') as ps_patient_middle_name,i.ps_patient_last_name,g.gender_name,i.ps_concsssion_percentage,i.ps_concsssion_amount,ifnull(i.ps_concession_reason,'') as ps_concession_reason,ifnull(i.ps_no_of_items,'') as ps_no_of_items, " + " ifnull(i.ps_total_amount,0) as ps_total_amount,ifnull(i.ps_net_amount,0) as ps_net_amount,ifnull(i.ps_out_standing_amount_for_patient,0) as ps_out_standing_amount_for_patient,ifnull(i.ps_out_standing_amount_for_company,0) as ps_out_standing_amount_for_company,ifnull(i.ps_received_amount_for_company,0) as ps_received_amount_for_company,ifnull(i.ps_tax_amount,0) as ps_tax_amount,ifnull(i.ps_patient_address,'') as ps_patient_address,CAST(i.item_delivered AS UNSIGNED) as item_delivered,ifnull(i.ps_patient_mobile_no,'') as ps_patient_mobile_no,i.ps_co_pay_from_tarrif,i.ps_discount_from_tarrif,i.ps_patient_pay_from_from_tarrif, " + " ifnull(i.ps_ref_doctor,'') as ps_ref_doctor,date(i.created_date) as created_date,date(i.last_modified_date) as last_modified_date,i.pharmacy_unit_id,g.gender_id,ifnull(p.patient_id,0) as patient_id,ifnull(v.visit_tariff_id,0) as visit_tariff_id,ifnull(a.admission_id,0) as admission_id,ifnull(a.admission_ipd_no,'') as admission_ipd_no,u.user_age as age " + " FROM tinv_pharmacy_sale i,trn_admission a,mst_visit v,mst_patient p,mst_gender g,mst_user u where i.ps_admission_id=a.admission_id and a.admission_patient_id=p.patient_id and i.ps_gender_id=g.gender_id and i.ps_visit_id=v.visit_id and p.patient_user_id=u.user_id and i.pharmacy_unit_id=" + searchPharmacyBillList.getUnitId() + " and i.pharmacy_type=" + searchPharmacyBillList.getPharmacyType() + " order by ps_id desc limit " + ((searchPharmacyBillList.getOffset() - 1) * searchPharmacyBillList.getLimit()) + "," + searchPharmacyBillList.getLimit();
                BigInteger temp1 = (BigInteger) entityManager.createNativeQuery("select count(i.ps_id) FROM tinv_pharmacy_sale i,trn_admission a,mst_visit v,mst_patient p,mst_gender g where i.ps_admission_id=a.admission_id and a.admission_patient_id=p.patient_id and i.ps_gender_id=g.gender_id and i.ps_visit_id=v.visit_id  and i.pharmacy_unit_id=" + searchPharmacyBillList.getUnitId() + " and i.pharmacy_type=" + searchPharmacyBillList.getPharmacyType()).getSingleResult();
                count = temp1.longValue();
            } else {
                queryInya = "SELECT i.ps_id,i.ps_name,ifnull(p.patient_mr_no,'') as patient_mr_no,i.ps_patient_first_name,ifnull(i.ps_patient_middle_name,'') as ps_patient_middle_name,i.ps_patient_last_name,g.gender_name,i.ps_concsssion_percentage,i.ps_concsssion_amount,ifnull(i.ps_concession_reason,'') as ps_concession_reason,ifnull(i.ps_no_of_items,'') as ps_no_of_items, " + " ifnull(i.ps_total_amount,0) as ps_total_amount,ifnull(i.ps_net_amount,0) as ps_net_amount,ifnull(i.ps_out_standing_amount_for_patient,0) as ps_out_standing_amount_for_patient,ifnull(i.ps_out_standing_amount_for_company,0) as ps_out_standing_amount_for_company,ifnull(i.ps_received_amount_for_company,0) as ps_received_amount_for_company,ifnull(i.ps_tax_amount,0) as ps_tax_amount,ifnull(i.ps_patient_address,'') as ps_patient_address,CAST(i.item_delivered AS UNSIGNED) as item_delivered,ifnull(i.ps_patient_mobile_no,'') as ps_patient_mobile_no,i.ps_co_pay_from_tarrif,i.ps_discount_from_tarrif,i.ps_patient_pay_from_from_tarrif, " + " ifnull(i.ps_ref_doctor,'') as ps_ref_doctor,date(i.created_date) as created_date,date(i.last_modified_date) as last_modified_date,i.pharmacy_unit_id,g.gender_id,ifnull(p.patient_id,0) as patient_id,ifnull(v.visit_tariff_id,0) as visit_tariff_id,IFNULL(0,0) AS admission_id, IFNULL(0,'') AS admission_ipd_no " + " FROM tinv_pharmacy_sale i left join mst_visit v on i.ps_visit_id=v.visit_id left join  mst_patient p on v.visit_patient_id=p.patient_id left join mst_gender g on i.ps_gender_id=g.gender_id where i.pharmacy_unit_id=" + searchPharmacyBillList.getUnitId() + " and i.pharmacy_type=" + searchPharmacyBillList.getPharmacyType() + " and i.ps_admission_id is null  order by ps_id desc limit " + ((searchPharmacyBillList.getOffset() - 1) * searchPharmacyBillList.getLimit()) + "," + searchPharmacyBillList.getLimit();
                BigInteger temp1 = (BigInteger) entityManager.createNativeQuery("select count(i.ps_id) FROM tinv_pharmacy_sale i left join mst_visit v on i.ps_visit_id=v.visit_id left join  mst_patient p on v.visit_patient_id=p.patient_id left join mst_gender g on i.ps_gender_id=g.gender_id where i.pharmacy_unit_id=" + searchPharmacyBillList.getUnitId() + " and i.pharmacy_type=" + searchPharmacyBillList.getPharmacyType() + " and i.ps_admission_id is null").getSingleResult();
                count = temp1.longValue();
            }
            List<Object[]> listObj = entityManager.createNativeQuery(queryInya).getResultList();
            for (Object temp[] : listObj) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                System.out.println("1" + temp[0] + " 2 " + temp[1] + " 3" + temp[2] + "4" + temp[3] + " 5" + temp[4] + " " + temp[5] + " " + temp[6] + " " + temp[7] + " " + temp[8] + " " + temp[9] + " " + temp[10] + " " + temp[11] + " " + temp[12] + " " + temp[13] + " " + temp[14] + " " + temp[15] + " " + temp[16] + " " + temp[17] + " " + temp[18] + " " + temp[19] + " " + temp[20] + " " + temp[21] + " " + temp[22] + " " + temp[23] + " " + temp[24] + " " + temp[25] + " " + temp[26] + " 27" + temp[27] + " 28" + temp[28] + " 29" + temp[29] + " " + temp[30] + " " + temp[31] + " " + temp[33]);
                String genderId = "4";
                if (temp[27] != null) {
                    genderId = "" + temp[27];
                }
                if (searchPharmacyBillList.getPharmacyType() == 0 || searchPharmacyBillList.getPharmacyType() == 1) {
                    String timelineId = "0";
                    if (temp[33] != null) {
                        timelineId = "" + temp[33];
                    }
                    tinvPharmacySaleDTO obj = new tinvPharmacySaleDTO(Long.parseLong(temp[0] + ""), Long.parseLong(temp[1] + ""), temp[2] + "", temp[3] + "", temp[4] + "", temp[5] + "", temp[6] + "", Double.valueOf(temp[7] + ""), Double.valueOf(temp[8] + ""), temp[9] + "", temp[10] + "", Double.valueOf(temp[11] + ""), Double.valueOf(temp[12] + ""), Double.valueOf(temp[13] + ""), Double.valueOf(temp[14] + ""), Double.valueOf(temp[15] + ""), Double.valueOf(temp[16] + ""), temp[17] + "", Integer.valueOf(temp[18] + ""), temp[19] + "", Double.valueOf(temp[20] + ""), Double.valueOf(temp[21] + ""), Double.valueOf(temp[22] + ""), temp[23] + "", format.parse(temp[24] + ""), format.parse(temp[25] + ""), Long.parseLong(temp[26] + ""), Long.parseLong(genderId + ""), Long.parseLong(temp[28] + ""), Long.parseLong(temp[29] + ""), Long.parseLong(temp[30] + ""), temp[31] + "", Long.parseLong(temp[32] + ""), timelineId + "");
                    tinvPharmacySaleList.add(obj);
                } else {
                    tinvPharmacySaleDTO obj = new tinvPharmacySaleDTO(Long.parseLong(temp[0] + ""), Long.parseLong(temp[1] + ""), temp[2] + "", temp[3] + "", temp[4] + "", temp[5] + "", temp[6] + "", Double.valueOf(temp[7] + ""), Double.valueOf(temp[8] + ""), temp[9] + "", temp[10] + "", Double.valueOf(temp[11] + ""), Double.valueOf(temp[12] + ""), Double.valueOf(temp[13] + ""), Double.valueOf(temp[14] + ""), Double.valueOf(temp[15] + ""), Double.valueOf(temp[16] + ""), temp[17] + "", Integer.valueOf(temp[18] + ""), temp[19] + "", Double.valueOf(temp[20] + ""), Double.valueOf(temp[21] + ""), Double.valueOf(temp[22] + ""), temp[23] + "", format.parse(temp[24] + ""), format.parse(temp[25] + ""), Long.parseLong(temp[26] + ""), Long.parseLong(genderId + ""), Long.parseLong(temp[28] + ""), Long.parseLong(temp[29] + ""), Long.parseLong(temp[30] + ""), temp[31] + "");
                    tinvPharmacySaleList.add(obj);
                }

            }
            if (tinvPharmacySaleList.size() > 0)
                tinvPharmacySaleList.get(0).setCount(count);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tinvPharmacySaleList;
    }

    @RequestMapping("searchunitwise")
    public List<tinvPharmacySaleDTO> getListOfItemsDependsOnSearch(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchPharmacyBillList searchPharmacyBillList) throws ParseException {
        TenantContext.setCurrentTenant(tenantName);
        List<tinvPharmacySaleDTO> tinvPharmacySaleList = new ArrayList<tinvPharmacySaleDTO>();
        String countQuery = "";
        long count = 0;
        if (searchPharmacyBillList == null) {
            SearchPharmacyBillList sb = new SearchPharmacyBillList();
            searchPharmacyBillList = sb;
        }
        String queryInya = "";
        String forTeriff = "";
        BigInteger countTemp;
        if (searchPharmacyBillList.getSearchMrNo() == null)
            searchPharmacyBillList.setSearchMrNo("");
        if (searchPharmacyBillList.getSearchPatientName() == null)
            searchPharmacyBillList.setSearchPatientName("");
        if (searchPharmacyBillList.getSearchMobileNo() == null)
            searchPharmacyBillList.setSearchMobileNo("");
        if (searchPharmacyBillList.getSearchFromDate() == null) {
            String sDate1 = "1998-12-31";
            searchPharmacyBillList.setSearchFromDate(sDate1);
        }
        if (!searchPharmacyBillList.getSearchMobileNo().equals("")) {
        }
        if (searchPharmacyBillList.getSearchToDate() == null) {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            searchPharmacyBillList.setSearchToDate(date);
        }
        if (searchPharmacyBillList.getSearchmbillTariffId().getTariffId() != 0)
            forTeriff = " AND i.ps_tariff_id=" + searchPharmacyBillList.getSearchmbillTariffId().getTariffId() + " ";
        if (searchPharmacyBillList.getPharmacyType() == 0) {
            if (searchPharmacyBillList.getSearchMrNo().equals("")) {
                queryInya = "SELECT i.ps_id,i.ps_name,ifnull(p.patient_mr_no,'') as patient_mr_no,i.ps_patient_first_name,ifnull(i.ps_patient_middle_name,'') as ps_patient_middle_name,i.ps_patient_last_name,g.gender_name,i.ps_concsssion_percentage,i.ps_concsssion_amount,ifnull(i.ps_concession_reason,'') as ps_concession_reason,ifnull(i.ps_no_of_items,'') as ps_no_of_items, " + " ifnull(i.ps_total_amount,0) as ps_total_amount,ifnull(i.ps_net_amount,0) as ps_net_amount,ifnull(i.ps_out_standing_amount_for_patient,0) as ps_out_standing_amount_for_patient,ifnull(i.ps_out_standing_amount_for_company,0) as ps_out_standing_amount_for_company,ifnull(i.ps_received_amount_for_company,0) as ps_received_amount_for_company,ifnull(i.ps_tax_amount,0) as ps_tax_amount,ifnull(i.ps_patient_address,'') as ps_patient_address,CAST(i.item_delivered AS UNSIGNED) as item_delivered,ifnull(i.ps_patient_mobile_no,'') as ps_patient_mobile_no,i.ps_co_pay_from_tarrif,i.ps_discount_from_tarrif,i.ps_patient_pay_from_from_tarrif, " + " ifnull(i.ps_ref_doctor,'') as ps_ref_doctor,date(i.created_date) as created_date,date(i.last_modified_date) as last_modified_date,i.pharmacy_unit_id,g.gender_id,ifnull(p.patient_id,0) as patient_id,ifnull(v.visit_tariff_id,0) as visit_tariff_id,0,'' " + " FROM tinv_pharmacy_sale i left join mst_visit v on  i.ps_visit_id=v.visit_id left join mst_patient p on v.visit_patient_id=patient_id, mst_gender g where i.ps_gender_id=g.gender_id and i.ps_admission_id is null and i.pharmacy_type=" + searchPharmacyBillList.getPharmacyType() + " and " + " ( i.ps_patient_first_name like '%" + searchPharmacyBillList.getSearchPatientName() + "%' Or  i.ps_patient_middle_name like '%" + searchPharmacyBillList.getSearchPatientName() + "%' Or  i.ps_patient_last_name like '%" + searchPharmacyBillList.getSearchPatientName() + "%' )" + " AND i.pharmacy_unit_id = " + searchPharmacyBillList.getUnitId() + " AND date(i.created_date) between   " + " '" + searchPharmacyBillList.getSearchFromDate() + "' AND  " + "'" + searchPharmacyBillList.getSearchToDate() + "' " + forTeriff + "  ";
                countQuery += "Select count(i.ps_id) FROM tinv_pharmacy_sale i left join mst_visit v on  i.ps_visit_id=v.visit_id left join mst_patient p on v.visit_patient_id=patient_id, mst_gender g where i.ps_gender_id=g.gender_id and i.ps_admission_id is null and i.pharmacy_type=" + searchPharmacyBillList.getPharmacyType() + " and  ( i.ps_patient_first_name like '%" + searchPharmacyBillList.getSearchPatientName() + "%' Or  i.ps_patient_middle_name like '%" + searchPharmacyBillList.getSearchPatientName() + "%' Or  i.ps_patient_last_name like '%" + searchPharmacyBillList.getSearchPatientName() + "%' )  AND i.pharmacy_unit_id = " + searchPharmacyBillList.getUnitId() + " AND date(i.created_date) between   " + " '" + searchPharmacyBillList.getSearchFromDate() + "' AND  " + "'" + searchPharmacyBillList.getSearchToDate() + "' " + forTeriff + "  ";
                if (!searchPharmacyBillList.getSearchMobileNo().equals("")) {
                    queryInya += " AND  i.ps_patient_mobile_no like '%" + searchPharmacyBillList.getSearchMobileNo() + "%' ";
                    countQuery += " AND  i.ps_patient_mobile_no like '%" + searchPharmacyBillList.getSearchMobileNo() + "%' ";
                }
            } else {
                queryInya = "SELECT i.ps_id,i.ps_name,ifnull(p.patient_mr_no,'') as patient_mr_no,i.ps_patient_first_name,ifnull(i.ps_patient_middle_name,'') as ps_patient_middle_name,i.ps_patient_last_name,g.gender_name,i.ps_concsssion_percentage,i.ps_concsssion_amount,ifnull(i.ps_concession_reason,'') as ps_concession_reason,ifnull(i.ps_no_of_items,'') as ps_no_of_items, " + " ifnull(i.ps_total_amount,0) as ps_total_amount,ifnull(i.ps_net_amount,0) as ps_net_amount,ifnull(i.ps_out_standing_amount_for_patient,0) as ps_out_standing_amount_for_patient,ifnull(i.ps_out_standing_amount_for_company,0) as ps_out_standing_amount_for_company,ifnull(i.ps_received_amount_for_company,0) as ps_received_amount_for_company,ifnull(i.ps_tax_amount,0) as ps_tax_amount,ifnull(i.ps_patient_address,'') as ps_patient_address,CAST(i.item_delivered AS UNSIGNED) as item_delivered,ifnull(i.ps_patient_mobile_no,'') as ps_patient_mobile_no,i.ps_co_pay_from_tarrif,i.ps_discount_from_tarrif,i.ps_patient_pay_from_from_tarrif, " + " ifnull(i.ps_ref_doctor,'') as ps_ref_doctor,date(i.created_date) as created_date,date(i.last_modified_date) as last_modified_date,i.pharmacy_unit_id,g.gender_id,ifnull(p.patient_id,0) as patient_id,ifnull(v.visit_tariff_id,0) as visit_tariff_id,0,'' " + " FROM tinv_pharmacy_sale i left join mst_visit v on  i.ps_visit_id=v.visit_id left join mst_patient p on v.visit_patient_id=patient_id, mst_gender g where i.ps_gender_id=g.gender_id and i.ps_admission_id is null and i.pharmacy_type=" + searchPharmacyBillList.getPharmacyType() + " and " + " p.patient_mr_no like '%" + searchPharmacyBillList.getSearchMrNo() + "%' And ( i.ps_patient_first_name like '%" + searchPharmacyBillList.getSearchPatientName() + "%' Or  i.ps_patient_middle_name like '%" + searchPharmacyBillList.getSearchPatientName() + "%' Or  i.ps_patient_last_name like '%" + searchPharmacyBillList.getSearchPatientName() + "%' ) " + " AND i.pharmacy_unit_id = " + searchPharmacyBillList.getUnitId() + " AND date(i.created_date) between   " + " '" + searchPharmacyBillList.getSearchFromDate() + "' AND  " + "'" + searchPharmacyBillList.getSearchToDate() + "' " + forTeriff + "   ";
                countQuery = "Select count(i.ps_id) FROM tinv_pharmacy_sale i left join mst_visit v on  i.ps_visit_id=v.visit_id left join mst_patient p on v.visit_patient_id=patient_id, mst_gender g where i.ps_gender_id=g.gender_id and i.ps_admission_id is null and i.pharmacy_type=" + searchPharmacyBillList.getPharmacyType() + " and p.patient_mr_no like '%" + searchPharmacyBillList.getSearchMrNo() + "%' And  ( i.ps_patient_first_name like '%" + searchPharmacyBillList.getSearchPatientName() + "%' Or  i.ps_patient_middle_name like '%" + searchPharmacyBillList.getSearchPatientName() + "%' Or  i.ps_patient_last_name like '%" + searchPharmacyBillList.getSearchPatientName() + "%' )" + " AND i.pharmacy_unit_id = " + searchPharmacyBillList.getUnitId() + " AND date(i.created_date) between   " + " '" + searchPharmacyBillList.getSearchFromDate() + "' AND  " + "'" + searchPharmacyBillList.getSearchToDate() + "' " + forTeriff + "  ";
                if (!searchPharmacyBillList.getSearchMobileNo().equals("")) {
                    queryInya += " AND  i.ps_patient_mobile_no like '%" + searchPharmacyBillList.getSearchMobileNo() + "%' ";
                    countQuery += " AND  i.ps_patient_mobile_no like '%" + searchPharmacyBillList.getSearchMobileNo() + "%' ";
                }
            }
        } else {
            if (searchPharmacyBillList.getSearchMrNo().equals("")) {
                queryInya = "SELECT i.ps_id,i.ps_name,ifnull(p.patient_mr_no,'') as patient_mr_no,i.ps_patient_first_name,ifnull(i.ps_patient_middle_name,'') as ps_patient_middle_name,i.ps_patient_last_name,g.gender_name,i.ps_concsssion_percentage,i.ps_concsssion_amount,ifnull(i.ps_concession_reason,'') as ps_concession_reason,ifnull(i.ps_no_of_items,'') as ps_no_of_items, " + " ifnull(i.ps_total_amount,0) as ps_total_amount,ifnull(i.ps_net_amount,0) as ps_net_amount,ifnull(i.ps_out_standing_amount_for_patient,0) as ps_out_standing_amount_for_patient,ifnull(i.ps_out_standing_amount_for_company,0) as ps_out_standing_amount_for_company,ifnull(i.ps_received_amount_for_company,0) as ps_received_amount_for_company,ifnull(i.ps_tax_amount,0) as ps_tax_amount,ifnull(i.ps_patient_address,'') as ps_patient_address,CAST(i.item_delivered AS UNSIGNED) as item_delivered,ifnull(i.ps_patient_mobile_no,'') as ps_patient_mobile_no,i.ps_co_pay_from_tarrif,i.ps_discount_from_tarrif,i.ps_patient_pay_from_from_tarrif, " + " ifnull(i.ps_ref_doctor,'') as ps_ref_doctor,date(i.created_date) as created_date,date(i.last_modified_date) as last_modified_date,i.pharmacy_unit_id,g.gender_id,ifnull(p.patient_id,0) as patient_id,ifnull(v.visit_tariff_id,0) as visit_tariff_id,ifnull(a.admission_id,0) as admission_id,ifnull(a.admission_ipd_no,'') as admission_ipd_no " + " FROM tinv_pharmacy_sale i,trn_admission a,mst_visit v,mst_patient p,mst_gender g where i.ps_admission_id=a.admission_id and a.admission_patient_id=p.patient_id and i.ps_gender_id=g.gender_id and i.ps_visit_id=v.visit_id  and i.pharmacy_type=" + searchPharmacyBillList.getPharmacyType() + " and " + " ( i.ps_patient_first_name like '%" + searchPharmacyBillList.getSearchPatientName() + "%' Or  i.ps_patient_middle_name like '%" + searchPharmacyBillList.getSearchPatientName() + "%' Or  i.ps_patient_last_name like '%" + searchPharmacyBillList.getSearchPatientName() + "%' )" + " AND i.pharmacy_unit_id = " + searchPharmacyBillList.getUnitId() + " AND date(i.created_date) between   " + " '" + searchPharmacyBillList.getSearchFromDate() + "' AND  " + "'" + searchPharmacyBillList.getSearchToDate() + "' " + forTeriff + "  ";
                countQuery += "Select count(i.ps_id) FROM tinv_pharmacy_sale i,trn_admission a,mst_visit v,mst_patient p,mst_gender g where i.ps_admission_id=a.admission_id and a.admission_patient_id=p.patient_id and i.ps_gender_id=g.gender_id and i.ps_visit_id=v.visit_id  and i.pharmacy_type=" + searchPharmacyBillList.getPharmacyType() + " and  ( i.ps_patient_first_name like '%" + searchPharmacyBillList.getSearchPatientName() + "%' Or  i.ps_patient_middle_name like '%" + searchPharmacyBillList.getSearchPatientName() + "%' Or  i.ps_patient_last_name like '%" + searchPharmacyBillList.getSearchPatientName() + "%' )  AND i.pharmacy_unit_id = " + searchPharmacyBillList.getUnitId() + " AND date(i.created_date) between   " + " '" + searchPharmacyBillList.getSearchFromDate() + "' AND  " + "'" + searchPharmacyBillList.getSearchToDate() + "' " + forTeriff + "  ";
                if (!searchPharmacyBillList.getSearchMobileNo().equals("")) {
                    queryInya += " AND  i.ps_patient_mobile_no like '%" + searchPharmacyBillList.getSearchMobileNo() + "%' ";
                    countQuery += " AND  i.ps_patient_mobile_no like '%" + searchPharmacyBillList.getSearchMobileNo() + "%' ";
                }

            } else {
                queryInya = "SELECT i.ps_id,i.ps_name,ifnull(p.patient_mr_no,'') as patient_mr_no,i.ps_patient_first_name,ifnull(i.ps_patient_middle_name,'') as ps_patient_middle_name,i.ps_patient_last_name,g.gender_name,i.ps_concsssion_percentage,i.ps_concsssion_amount,ifnull(i.ps_concession_reason,'') as ps_concession_reason,ifnull(i.ps_no_of_items,'') as ps_no_of_items, " + " ifnull(i.ps_total_amount,0) as ps_total_amount,ifnull(i.ps_net_amount,0) as ps_net_amount,ifnull(i.ps_out_standing_amount_for_patient,0) as ps_out_standing_amount_for_patient,ifnull(i.ps_out_standing_amount_for_company,0) as ps_out_standing_amount_for_company,ifnull(i.ps_received_amount_for_company,0) as ps_received_amount_for_company,ifnull(i.ps_tax_amount,0) as ps_tax_amount,ifnull(i.ps_patient_address,'') as ps_patient_address,CAST(i.item_delivered AS UNSIGNED) as item_delivered,ifnull(i.ps_patient_mobile_no,'') as ps_patient_mobile_no,i.ps_co_pay_from_tarrif,i.ps_discount_from_tarrif,i.ps_patient_pay_from_from_tarrif, " + " ifnull(i.ps_ref_doctor,'') as ps_ref_doctor,date(i.created_date) as created_date,date(i.last_modified_date) as last_modified_date,i.pharmacy_unit_id,g.gender_id,ifnull(p.patient_id,0) as patient_id,ifnull(v.visit_tariff_id,0) as visit_tariff_id,ifnull(a.admission_id,0) as admission_id,ifnull(a.admission_ipd_no,'') as admission_ipd_no " + " FROM tinv_pharmacy_sale i,trn_admission a,mst_visit v,mst_patient p,mst_gender g where i.ps_admission_id=a.admission_id and a.admission_patient_id=p.patient_id and i.ps_gender_id=g.gender_id and i.ps_visit_id=v.visit_id  and i.pharmacy_type=" + searchPharmacyBillList.getPharmacyType() + " and " + " p.patient_mr_no like '%" + searchPharmacyBillList.getSearchMrNo() + "%' And ( i.ps_patient_first_name like '%" + searchPharmacyBillList.getSearchPatientName() + "%' Or  i.ps_patient_middle_name like '%" + searchPharmacyBillList.getSearchPatientName() + "%' Or  i.ps_patient_last_name like '%" + searchPharmacyBillList.getSearchPatientName() + "%' ) " + " AND i.pharmacy_unit_id = " + searchPharmacyBillList.getUnitId() + " AND date(i.created_date) between   " + " '" + searchPharmacyBillList.getSearchFromDate() + "' AND  " + "'" + searchPharmacyBillList.getSearchToDate() + "' " + forTeriff + "   ";
                countQuery = "Select count(i.ps_id) FROM tinv_pharmacy_sale i,trn_admission a,mst_visit v,mst_patient p,mst_gender g where i.ps_admission_id=a.admission_id and a.admission_patient_id=p.patient_id and i.ps_gender_id=g.gender_id and i.ps_visit_id=v.visit_id  and i.pharmacy_type=" + searchPharmacyBillList.getPharmacyType() + " and p.patient_mr_no like '%" + searchPharmacyBillList.getSearchMrNo() + "%' And  ( i.ps_patient_first_name like '%" + searchPharmacyBillList.getSearchPatientName() + "%' Or  i.ps_patient_middle_name like '%" + searchPharmacyBillList.getSearchPatientName() + "%' Or  i.ps_patient_last_name like '%" + searchPharmacyBillList.getSearchPatientName() + "%' )" + " AND i.pharmacy_unit_id = " + searchPharmacyBillList.getUnitId() + " AND date(i.created_date) between   " + " '" + searchPharmacyBillList.getSearchFromDate() + "' AND  " + "'" + searchPharmacyBillList.getSearchToDate() + "' " + forTeriff + "  ";
                if (!searchPharmacyBillList.getSearchMobileNo().equals("")) {
                    queryInya += " AND  i.ps_patient_mobile_no like '%" + searchPharmacyBillList.getSearchMobileNo() + "%' ";
                    countQuery += " AND  i.ps_patient_mobile_no like '%" + searchPharmacyBillList.getSearchMobileNo() + "%' ";
                }
            }
          /*  if (searchPharmacyBillList.getPharmacyType() == 1) {
                queryInya += " and admission_status=0 ";
                countQuery += " and admission_status=0 ";
            }*/
        }
        queryInya += " order by i.ps_id desc  limit " + ((searchPharmacyBillList.getOffset() - 1) * searchPharmacyBillList.getLimit()) + "," + searchPharmacyBillList.getLimit()+";";
        countTemp = (BigInteger) entityManager.createNativeQuery(countQuery).getSingleResult();
        count = countTemp.longValue();
        System.out.println("queryInya " + queryInya);
        List<Object[]> listObj = entityManager.createNativeQuery(queryInya).getResultList();
        for (Object temp[] : listObj) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            tinvPharmacySaleDTO obj = new tinvPharmacySaleDTO(Long.parseLong(temp[0] + ""), Long.parseLong(temp[1].toString()), temp[2].toString(), temp[3].toString(), temp[4].toString(), temp[5].toString(), temp[6].toString(), Double.valueOf(temp[7].toString()), Double.valueOf(temp[8].toString()), temp[9].toString(), temp[10].toString(), Double.valueOf(temp[11].toString()), Double.valueOf(temp[12].toString()), Double.valueOf(temp[13].toString()), Double.valueOf(temp[14].toString()), Double.valueOf(temp[15].toString()), Double.valueOf(temp[16].toString()), temp[17].toString(), Integer.valueOf(temp[18].toString()), temp[19].toString(), Double.valueOf(temp[20].toString()), Double.valueOf(temp[21].toString()), Double.valueOf(temp[22].toString()), temp[23].toString(), format.parse(temp[24].toString()), format.parse(temp[25].toString()), Long.parseLong(temp[26].toString()), Long.parseLong(temp[27].toString()), Long.parseLong(temp[28].toString()), Long.parseLong(temp[29].toString()), Long.parseLong(temp[30].toString()), temp[31].toString());
            tinvPharmacySaleList.add(obj);
        }
        if (tinvPharmacySaleList.size() > 0)
            tinvPharmacySaleList.get(0).setCount(count);
        return tinvPharmacySaleList;
    }

    @GetMapping
    @RequestMapping("getPatientOutstanding")
    public boolean list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "admissionId", required = false) long admissionId) {
        TenantContext.setCurrentTenant(tenantName);
        //return true if patient oustanding payment in pharmacy else false
        String query = "select p from TinvPharmacySale p where p.psAdmissionId.admissionId=" + admissionId + " and p.psOutStandingAmountForPatient>0 and p.isActive=1 and p.isDeleted=0 and p.pharmacyType=1";
        List<TinvPharmacySale> list = entityManager.createQuery(query, TinvPharmacySale.class).getResultList();
        if (list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @GetMapping
    @RequestMapping("getPatientPharmacySale")
    public List<TinvPharmacySale> pharmacylist(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "adminsionId", required = false) long adminsionId) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "select p from TinvPharmacySale p where p.psAdmissionId.admissionId=" + adminsionId + " and p.isActive=1 and p.isDeleted=0 and p.pharmacyType=1";
        List<TinvPharmacySale> list = entityManager.createQuery(query, TinvPharmacySale.class).getResultList();
        return list;
    }

    @GetMapping
    @RequestMapping("getPharmacyBillCalaulation")
    public Map<String, Double> pharmacyCallist(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "adminsionId", required = false) long adminsionId) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Double> respCalMap = new HashMap<String, Double>();
        String query = " select ifnull(psCal.totalAmount,0) as psTotalAmount, " + " ifnull(psCal.totalTaxAmount-psrCal.totalReturnTaxAmount,0) as psTotalTaxAmount, " + " ifnull(psCal.totalDisCountAmount-psrCal.totalConsesionAmount,0) as psTotalDiscountAmount,  " + " ifnull(psCal.totalOutstandingAmunt,0) as totalOutstandingAmunt " + " from " + " (select ifnull(sum(ps.ps_total_amount),0) as totalAmount,ifnull(sum(ps.ps_tax_amount),0) as totalTaxAmount, " + " ifnull(sum(ps.ps_concsssion_amount),0) as totalDisCountAmount,ifnull(sum(ps.ps_out_standing_amount_for_patient),0) as totalOutstandingAmunt from tinv_pharmacy_sale ps " + " where ps.ps_admission_id=" + adminsionId + " and ps.pharmacy_type=1) as psCal, " + " (select ifnull(sum(isri.isri_return_total_amount),0) as totalReturnAmount,ifnull(sum(isri.isri_tax_amount),0) as totalReturnTaxAmount, " + " ifnull(sum(isri.isri_concession_amount),0) as totalConsesionAmount " + " from tinv_item_sales_return_item isri,tinv_item_sales_return isr where isri.isri_isr_id=isr_id " + " and isr.isr_ps_id in (select ps.ps_id from tinv_pharmacy_sale ps where  ps.ps_admission_id=" + adminsionId + " and ps.pharmacy_type=1)) as psrCal ";
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(query).getResultList();
        for (Object[] obj : list) {
            respCalMap.put("psTotalAmount", Double.valueOf(obj[0].toString()));
            respCalMap.put("psTotalTaxAmount", Double.valueOf(obj[1].toString()));
            respCalMap.put("psTotalDiscountAmount", Double.valueOf(obj[2].toString()));
            respCalMap.put("totalOutstandingAmount", Double.valueOf(obj[3].toString()));
        }
        return respCalMap;
    }

    @GetMapping
    @RequestMapping("getPatientOutstandingByAdmisionId")
    public boolean getPatientOutstandingByAdmisionId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "admisionId") long admisionId) {
        TenantContext.setCurrentTenant(tenantName);
        //return true if patient oustanding payment is 0  and if oustanding remains then return false
        String query = "select p from TinvPharmacySale p where p.psAdmissionId.admissionId=" + admisionId + " and p.psOutStandingAmountForPatient>0 and p.isActive=1 and p.isDeleted=0 and p.pharmacyType=1 ";
        List<TinvPharmacySale> list = entityManager.createQuery(query, TinvPharmacySale.class).getResultList();
        if (list.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    @GetMapping
    @RequestMapping("getPatientOutstandingListByAdmisionId")
    public List<TinvPharmacySale> getPatientOutstandingListByAdmisionId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "admisionId") long admisionId) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "select p from TinvPharmacySale p where p.psAdmissionId.admissionId=" + admisionId + " and p.psOutStandingAmountForPatient>0 and p.isActive=1 and p.isDeleted=0 and p.pharmacyType=1 ";
        List<TinvPharmacySale> list = entityManager.createQuery(query, TinvPharmacySale.class).getResultList();
        return list;
    }

    @GetMapping
    @RequestMapping("getsaleshistory")
    public List<TinvSaleHistoryDTO> getSalesHistory(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "patientId") long patientId, @RequestParam(value = "page", required = false, defaultValue = "1") long page, @RequestParam(value = "size", required = false, defaultValue = "10") long size) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "SELECT tpsi.created_date AS createDate,if(tps.pharmacy_type=0,'OPD','') AS pharmacyType,tpsi.psi_batch_code AS batchCode,tpsi.psi_item_name AS itemName, tpsi.psi_item_quantity AS itemQuantity," +
                " tpsi.psi_mrp AS mrp,tpsi.psi_tax_amount AS taxAmount,tpsi.psi_total_amount AS totalAmount" +
                " FROM tinv_pharmacy_sale_item tpsi" +
                " INNER JOIN  tinv_pharmacy_sale tps ON tps.ps_id = tpsi.psi_ps_id" +
                " INNER JOIN mst_visit mv ON mv.visit_id = tps.ps_visit_id" +
                " INNER JOIN mst_patient mp ON mp.patient_id = mv.visit_patient_id" +
                " WHERE tps.is_active=1 AND tps.is_deleted=0 AND  tps.pharmacy_type=0 and mv.visit_patient_id =" + patientId + "" +
                " UNION all" +
                " SELECT tpsi.created_date AS createDate,if(tps.pharmacy_type=1,'IPD','') AS pharmacyType,tpsi.psi_batch_code AS batchCode,tpsi.psi_item_name AS itemName, tpsi.psi_item_quantity AS itemQuantity," +
                " tpsi.psi_mrp AS mrp,tpsi.psi_tax_amount AS taxAmount,tpsi.psi_total_amount AS totalAmount" +
                " FROM tinv_pharmacy_sale_item tpsi" +
                " INNER JOIN tinv_pharmacy_sale tps ON tps.ps_id = tpsi.psi_ps_id" +
                " INNER JOIN trn_admission ta ON ta.admission_id = tps.ps_admission_id" +
                " INNER JOIN mst_patient mp ON mp.patient_id = ta.admission_patient_id" +
                " WHERE tps.is_active=1 AND tps.is_deleted=0 AND tps.pharmacy_type=1 AND  ta.admission_patient_id =" + patientId + "";
        long count = 0;
        List<TinvSaleHistoryDTO> listOfTinvSaleHistoryDTOList = new ArrayList<TinvSaleHistoryDTO>();
        String CountQuery = " select count(*) from ( " + query + " ) as combine ";
        //List<TinvSaleHistoryDTO> tempObj = entityManager.createQuery(query, TinvSaleHistoryDTO.class).getResultList();
        query += " ORDER BY  createDate desc limit " + ((page - 1) * size) + "," + size;
        System.out.println("query::" + query);
        BigInteger cc = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
        count = cc.longValue();
        List<Object[]> response = (List<Object[]>) entityManager.createNativeQuery(query).getResultList();
        for (Object[] ob : response) {
            TinvSaleHistoryDTO obj = new TinvSaleHistoryDTO();
            obj.setCreateDate("" + ob[0]);
            obj.setPharmacyType("" + ob[1]);
            obj.setBatchCode("" + ob[2]);
            obj.setItemName("" + ob[3]);
            obj.setItemQuantity("" + ob[4]);
            obj.setMrp("" + ob[5]);
            obj.setTaxAmount("" + ob[6]);
            obj.setTotalAmount("" + ob[7]);
            obj.setTotalElements(count);
            listOfTinvSaleHistoryDTOList.add(obj);
        }
        return listOfTinvSaleHistoryDTOList;
    }

}


            
