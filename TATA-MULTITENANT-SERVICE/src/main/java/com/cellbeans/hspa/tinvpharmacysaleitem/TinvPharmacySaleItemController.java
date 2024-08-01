package com.cellbeans.hspa.tinvpharmacysaleitem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.cellbeans.hspa.TenantContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_pharmacy_sale_item")
public class TinvPharmacySaleItemController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    TinvPharmacySaleItemRepository tinvPharmacySaleItemRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName,
                                      @RequestBody List<TinvPharmacySaleItem> tinvPharmacySaleItem) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvPharmacySaleItem != null) {
            tinvPharmacySaleItemRepository.saveAll(tinvPharmacySaleItem);
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
        List<TinvPharmacySaleItem> records;
        records = tinvPharmacySaleItemRepository.findByPsiItemIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{psiId}")
    public TinvPharmacySaleItem read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("psiId") Long psiId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPharmacySaleItem tinvPharmacySaleItem = tinvPharmacySaleItemRepository.getById(psiId);
        return tinvPharmacySaleItem;
    }

    @RequestMapping("update")
    public TinvPharmacySaleItem update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvPharmacySaleItem tinvPharmacySaleItem) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvPharmacySaleItemRepository.save(tinvPharmacySaleItem);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TinvPharmacySaleItem> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "psiId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tinvPharmacySaleItemRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tinvPharmacySaleItemRepository.findByPsiNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{psiId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("psiId") Long psiId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPharmacySaleItem tinvPharmacySaleItem = tinvPharmacySaleItemRepository.getById(psiId);
        if (tinvPharmacySaleItem != null) {
            tinvPharmacySaleItem.setIsDeleted(true);
            tinvPharmacySaleItemRepository.save(tinvPharmacySaleItem);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("drugelist/{psipsId}")
    public Iterable<TinvPharmacySaleItem> drugeList(@RequestHeader("X-tenantId") String tenantName, @PathVariable("psipsId") Long psipsId) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvPharmacySaleItemRepository.findByPsiPsIdPsId(psipsId);

    }

    @RequestMapping("deliverdruge/{psiId}")
    public Map<String, String> updateDrugeListbyhisStatus(@RequestHeader("X-tenantId") String tenantName, @PathVariable("psiId") Long psiId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvPharmacySaleItem tinvPharmacySaleItem = tinvPharmacySaleItemRepository.getById(psiId);
        if (tinvPharmacySaleItem != null) {
            if (tinvPharmacySaleItem.getItemGiven()) {
                tinvPharmacySaleItem.setItemGiven(false);
            } else {
                tinvPharmacySaleItem.setItemGiven(true);
            }
            tinvPharmacySaleItemRepository.save(tinvPharmacySaleItem);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;

    }

    @GetMapping
    @RequestMapping("getPharmacyItemsByAdmisionId")
    public List<TinvPharmacySaleItem> getPharmacyItemsByAdmisionId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "admisionId") long admisionId) {
        TenantContext.setCurrentTenant(tenantName);
        List<TinvPharmacySaleItem> correctList = new ArrayList<TinvPharmacySaleItem>();
        String query = "select psi from TinvPharmacySaleItem  psi where  psi.psiPsId.pharmacyType=1 and psi.psiPsId.psAdmissionId.admissionId=" + admisionId + " order by psi.createdDate ASC ";
        List<TinvPharmacySaleItem> list = entityManager.createQuery(query, TinvPharmacySaleItem.class).getResultList();
       /* for (TinvPharmacySaleItem obj : list) {
            String returnQuery = "select * from tinv_item_sales_return_item pr,tinv_item_sales_return p where pr.isri_isr_id=p.isr_id and p.isr_ps_id=" + obj.getPsiPsId().getPsId() + " and pr.isri_item_id=" + obj.getPsiObiId().getObiItemId().getItemId();
            List<TinvItemSalesReturnItem> returnList = entityManager.createNativeQuery(returnQuery, TinvItemSalesReturnItem.class).getResultList();
            for (TinvItemSalesReturnItem returnObj : returnList) {
                obj.setPsiItemQuantity(obj.getPsiItemQuantity() - returnObj.getIsriItemQuantity());
                obj.setPsiNetAmount(obj.getPsiNetAmount() - returnObj.getIsriNetAmount());
                obj.setPsiTotalAmount(obj.getPsiTotalAmount() - returnObj.getIsriTotalAmount());
                obj.setPsiTaxAmount(obj.getPsiTaxAmount() - returnObj.getIsriTaxAmount());
            }
            if (obj.getPsiItemQuantity() != 0) {
                correctList.add(obj);
            }
        }*/
        // return correctList;
        return list;
    }

}
            
