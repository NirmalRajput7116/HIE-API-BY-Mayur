package com.cellbeans.hspa.tinvitemsalesreturn;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.tinvpharmacysaleitem.TinvPharmacySaleItem;
import com.cellbeans.hspa.tnstinpatient.TnstItemIssue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_item_sales_return")
public class TinvItemSalesReturnController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TinvItemSalesReturnRepository tinvItemSalesReturnRepository;

    @PersistenceContext
    EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvItemSalesReturn tinvItemSalesReturn) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvItemSalesReturn.getIsrNetAmount() >= 0) {
            tinvItemSalesReturnRepository.save(tinvItemSalesReturn);
            respMap.put("isrId", String.valueOf(tinvItemSalesReturn.getIsrId()));
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
        List<TinvItemSalesReturn> records;
        records = tinvItemSalesReturnRepository.findByIsrIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{isrId}")
    public TinvItemSalesReturn read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("isrId") Long isrId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvItemSalesReturn tinvItemSalesReturn = tinvItemSalesReturnRepository.getById(isrId);
        return tinvItemSalesReturn;
    }

    @RequestMapping("update")
    public TinvItemSalesReturn update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvItemSalesReturn tinvItemSalesReturn) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvItemSalesReturnRepository.save(tinvItemSalesReturn);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TinvItemSalesReturn> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "isrId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tinvItemSalesReturnRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tinvItemSalesReturnRepository.findByIsrNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @GetMapping
    @RequestMapping("listByUnitId")
    public Iterable<TinvItemSalesReturn> listByUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "isrId") String col, @RequestParam(value = "unitId", required = false, defaultValue = " ") long unitId, @RequestParam(value = "pharmacyType", required = false, defaultValue = "0") int pharmacyType) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tinvItemSalesReturnRepository.findAllByIsrUnitIdUnitIdAndIsrPsIdPharmacyTypeAndIsActiveTrueAndIsDeletedFalse(unitId, pharmacyType, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return tinvItemSalesReturnRepository.findByIsrNameContainsAndIsrUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(qString, unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{isrId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("isrId") Long isrId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvItemSalesReturn tinvItemSalesReturn = tinvItemSalesReturnRepository.getById(isrId);
        if (tinvItemSalesReturn != null) {
            tinvItemSalesReturn.setIsDeleted(true);
            tinvItemSalesReturnRepository.save(tinvItemSalesReturn);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }
//    public Long getPsIdForNursingReturn(long patientId,long[] itemlist) {
//        String query = "select  max(DISTINCT(p.ps_id)) as psId  from tinv_pharmacy_sale_item pi,tinv_pharmacy_sale p,mst_visit v where " +
//                "p.ps_id=pi.psi_ps_id and v.visit_id=p.ps_visit_id  and v.visit_patient_id=" + patientId + " and pi.psi_item_id in (" + itemlist[0];
//        for (int i = 1; i < itemlist.length; i++) {
//            query += "," + itemlist[i];
//        }
//        query += ")";
//        BigInteger temp = (BigInteger) entityManager.createNativeQuery(query).getSingleResult();
//        return temp.longValue();
//    }

    @RequestMapping("getGivenItem")
    public List<TinvPharmacySaleItem> getGivenItem(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "admissionId", required = false) long admissionId, @RequestParam(value = "unitId", required = false) long unitId, @RequestBody List<TnstItemIssue> tnstItemIssue) {
        TenantContext.setCurrentTenant(tenantName);
        String iterlist = "";
        if (tnstItemIssue.size() > 0) {
            iterlist += tnstItemIssue.get(0).getIiItemId().getObiItemId().getItemId();
            for (int i = 1; i < tnstItemIssue.size(); i++) {
                iterlist += "," + tnstItemIssue.get(i).getIiItemId().getObiItemId().getItemId();
            }
        }
        String query = "select * from tinv_pharmacy_sale_item tps,tinv_pharmacy_sale p,tinv_opening_balance_item opi where tps.psi_obi_id=obi_id " +
                " and  tps.psi_ps_id=p.ps_id and p.ps_admission_id=" + admissionId + " and opi.obi_item_id in (" + iterlist + ") and p.pharmacy_type=1 and tps.pharmacy_unit_id=" + unitId;
        return entityManager.createNativeQuery(query, TinvPharmacySaleItem.class).getResultList();
    }

    @RequestMapping("saveReturns")
    public Map<String, List<TinvItemSalesReturn>> saveReturns(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvItemSalesReturn> tinvItemSalesReturn) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, List<TinvItemSalesReturn>> respMap1 = new HashMap<String, List<TinvItemSalesReturn>>();
        if (tinvItemSalesReturn.size() > 0) {
            List<TinvItemSalesReturn> list = tinvItemSalesReturnRepository.saveAll(tinvItemSalesReturn);
            respMap1.put("isrId", list);
        }
        return respMap1;
    }

}
            
