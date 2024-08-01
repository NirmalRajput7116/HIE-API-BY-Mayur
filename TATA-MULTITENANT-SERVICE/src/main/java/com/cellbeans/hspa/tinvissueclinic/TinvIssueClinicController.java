package com.cellbeans.hspa.tinvissueclinic;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.invstore.InvStore;
import com.cellbeans.hspa.invstore.InvStoreRepository;
import com.cellbeans.hspa.tinvissueclinicitem.TinvIssueClinicItem;
import com.cellbeans.hspa.tinvissueclinicitem.TinvIssueClinicItemRepository;
import com.cellbeans.hspa.tinvopeningbalanceitem.TinvOpeningBalanceItem;
import com.cellbeans.hspa.tinvopeningbalanceitem.TinvOpeningBalanceItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_issue_clinic")
public class TinvIssueClinicController {
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TinvIssueClinicRepository tinvIssueClinicRepository;
    @Autowired
    TinvIssueClinicItemRepository tinvIssueClinicItemRepository;
    @Autowired
    TinvOpeningBalanceItemRepository tinvOpeningBalanceItemRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    InvStoreRepository invStoreRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvIssueClinic tinvIssueClinic) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvIssueClinic.getIcDate() != null) {
            long storeId = tinvIssueClinic.getIcFromStoreId().getStoreId();
            InvStore invStore = invStoreRepository.getById(storeId);
            if (invStore.getStorePrefix() != null) {
                tinvIssueClinic.setIcNo(invStore.getStorePrefix() + "/TRX/" + codeForGengeration(tenantName));
                tinvIssueClinicRepository.save(tinvIssueClinic);
                respMap.put("icId", String.valueOf(tinvIssueClinic.getIcId()));
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "2");
                respMap.put("msg", "Please fill store prefix in store configuration");
                return respMap;
            }
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
        List<TinvIssueClinic> records;
        records = tinvIssueClinicRepository.findByIcDateContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{icId}")
    public TinvIssueClinic read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("icId") Long icId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvIssueClinic tinvIssueClinic = tinvIssueClinicRepository.getById(icId);
        return tinvIssueClinic;
    }

    @RequestMapping("update")
    public TinvIssueClinic update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvIssueClinic tinvIssueClinic) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvIssueClinicRepository.save(tinvIssueClinic);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TinvIssueClinic> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "icId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tinvIssueClinicRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tinvIssueClinicRepository.findByIcDateContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{icId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("icId") Long icId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvIssueClinic tinvIssueClinic = tinvIssueClinicRepository.getById(icId);
        if (tinvIssueClinic != null) {
            tinvIssueClinic.setIsDeleted(true);
            tinvIssueClinicRepository.save(tinvIssueClinic);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @PostMapping("listofissue")
    public List<TinvIssueClinic> getListOfIssueItems(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvIssueClinic tinvIssueClinic) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvIssueClinicRepository.findAllByIcFromStoreIdStoreIdAndIcToStoreIdStoreIdAndIsActiveTrueAndIsDeletedFalse(tinvIssueClinic.getIcFromStoreId().getStoreId(), tinvIssueClinic.getIcToStoreId().getStoreId());
    }

    @PostMapping("listofissueCheck")
    public List<TinvIssueClinic> getListOfIssueItemsCheck(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvIssueClinic tinvIssueClinic) {
        TenantContext.setCurrentTenant(tenantName);
        String Query = "SELECT * FROM tinv_issue_clinic c,tinv_issue_clinic_item ci " + " where c.ic_id=ci.ici_ic_id and c.ic_from_store_id=" + tinvIssueClinic.getIcFromStoreId().getStoreId() + " " + "and c.ic_to_store_id=" + tinvIssueClinic.getIcToStoreId().getStoreId() + " and c.is_active=1 and c.is_deleted=0  and ci.ici_ri_id is NULL";
        //"and ci.ici_ri_id not in (SELECT i.ri_id FROM tata_unit_wise.tinv_receive_issue i where i.ri_id IS NOT NULL)";
        System.out.println("Query" + Query);
        return entityManager.createNativeQuery(Query, TinvIssueClinic.class).getResultList();
    }

    @PostMapping("listofissueByUnitId")
    public List<TinvIssueClinic> listofissueByUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvIssueClinic tinvIssueClinic) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvIssueClinicRepository.findAllByIcFromStoreIdStoreIdAndIcToStoreIdStoreIdAndIssueClinicUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(tinvIssueClinic.getIcFromStoreId().getStoreId(), tinvIssueClinic.getIcToStoreId().getStoreId(), tinvIssueClinic.getIssueClinicUnitId().getUnitId());
    }

    @RequestMapping("search")
    public List<TinvIssueClinic> getListOfItemsDependsOnSerch(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchStoreIssue searchStoreIssue) {
        TenantContext.setCurrentTenant(tenantName);
        if (searchStoreIssue == null) {
            SearchStoreIssue sb = new SearchStoreIssue();
            searchStoreIssue = sb;
        }
        String queryInya = "";
        String itemStore = "";
        String itemSupplier = "";
        if (searchStoreIssue.getSearchIndentNo() == null)
            searchStoreIssue.setSearchIndentNo("");
        if (searchStoreIssue.getSearchIssueNo() == null)
            searchStoreIssue.setSearchIssueNo("");
        if (searchStoreIssue.getSearchFromDate() == null) {
            String sDate1 = "1998/12/31";
            searchStoreIssue.setSearchFromDate(sDate1);
        }
        if (searchStoreIssue.getSearchToDate() == null) {
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchStoreIssue.setSearchToDate(date);
        }
        if (searchStoreIssue.getSearchFromStoreId().getStoreId() != 0)
            itemStore = "AND i.icFromStoreId.storeId=" + searchStoreIssue.getSearchFromStoreId().getStoreId() + "";
        if (searchStoreIssue.getSearchToStoreId().getStoreId() != 0)
            itemSupplier = "AND i.icToStoreId.storeId=" + searchStoreIssue.getSearchToStoreId().getStoreId() + "";
        queryInya = "Select i from TinvIssueClinic i Where i.isActive=1 AND i.isDeleted=0 AND i.icIndentNo like '%" + searchStoreIssue.getSearchIndentNo() + "%'" + " and  i.icNo like '%" + searchStoreIssue.getSearchIssueNo() + "%'" + "  AND date(i.createdDate) between   " + " ' " + searchStoreIssue.getSearchFromDate() + " ' AND  " + "'" + searchStoreIssue.getSearchToDate() + " ' " + itemStore + "  " + itemSupplier + " order by i.icId desc";
        // System.out.println(queryInya);
        long count = (Long) entityManager.createQuery("Select count(i.icId) from TinvIssueClinic i Where i.isActive=1 AND i.isDeleted=0 AND i.icIndentNo like '%" + searchStoreIssue.getSearchIndentNo() + "%'" + " and  i.icNo like '%" + searchStoreIssue.getSearchIssueNo() + "%'" + "  AND date(i.createdDate) between   " + " ' " + searchStoreIssue.getSearchFromDate() + " ' AND  " + "'" + searchStoreIssue.getSearchToDate() + " ' " + itemStore + "  " + itemSupplier + " order by i.icId desc").getSingleResult();
        List<TinvIssueClinic> ItemList = entityManager.createQuery(queryInya, TinvIssueClinic.class).setFirstResult(((searchStoreIssue.getOffset() - 1) * searchStoreIssue.getLimit())).setMaxResults(searchStoreIssue.getLimit()).getResultList();
        if (ItemList.size() > 0)
            ItemList.get(0).setCount(count);
        return ItemList;
    }

    @RequestMapping("issuecodeForGengeration")
    public String codeForGengeration(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        String codeGeneration = "";
        try {
            String Query = "SELECT IFNULL ((select max(ic_id) from tinv_issue_clinic), 0)";
            System.out.println(Query);
            codeGeneration = entityManager.createNativeQuery(Query).getSingleResult().toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return codeGeneration;

    }

    @RequestMapping("cancelbyid/{icId}")
    public Map<String, String> cancelbyid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("icId") Long icId, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvIssueClinic tinvIssueClinic = tinvIssueClinicRepository.getById(icId);
        // return tinvIssueClinic;
        if (tinvIssueClinic != null) {
            tinvIssueClinic.setIsCancel(true);
            tinvIssueClinicRepository.save(tinvIssueClinic);
            List<TinvIssueClinicItem> listissueItem = tinvIssueClinicItemRepository.findByIciIcIdIcIdAndIsActiveTrueAndIsDeletedFalse(icId);
            for (TinvIssueClinicItem obj : listissueItem) {
//                String Query="update tinv_opening_balance_item set is_deleted=1 where obi_store_id="+tinvIssueClinic.getIcToStoreId()+" and opening_balance_unit_id="+obj.getIssueClinicUnitId()+" and i.obi_item_batch_code='"+obj.getIciItemBatchCode()+"' " +
//                        " and obi_item_id=78 and obi_item_quantity=100";
//                obj.getIciIssueQuantity();
                TinvOpeningBalanceItem calObj = tinvOpeningBalanceItemRepository.getById(obj.getIciItemId().getObiId());
                if (calObj != null) {
                    double calculate = calObj.getObiItemQuantity() + obj.getIciIssueQuantity();
                    calObj.setObiItemQuantity(calculate);
                    tinvOpeningBalanceItemRepository.save(calObj);
                }
            }
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("updateIndendStatusByIcId/{icId}")
    public Map<String, String> updateIndendStatusByIcId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("icId") Long icId) {
        TenantContext.setCurrentTenant(tenantName);
        if (icId != 0) {
            TinvIssueClinic obj = tinvIssueClinicRepository.getById(icId);
            obj.setIssueIndentStatus(true);
            tinvIssueClinicRepository.save(obj);
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
    @RequestMapping("listofInTransit")
    public Iterable<TinvIssueClinic> listofInTransit(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                     @RequestParam(value = "size", required = false, defaultValue = "20") String size,
                                                     @RequestParam(value = "qString", required = false) String qString,
                                                     @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                     @RequestParam(value = "col", required = false, defaultValue = "icId") String col,
                                                     @RequestParam(value = "unitId", required = false) long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvIssueClinicRepository.findAllByIssueClinicUnitIdUnitIdAndIssueIndentStatusFalseAndIsActiveTrueAndIsDeletedFalse(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
    }

    @RequestMapping("searchForReceve")
    public List<TinvIssueClinic> searchForReceve(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchStoreIssue searchStoreIssue) {
        TenantContext.setCurrentTenant(tenantName);
        if (searchStoreIssue == null) {
            SearchStoreIssue sb = new SearchStoreIssue();
            searchStoreIssue = sb;
        }
        String queryInya = "";
        String itemStore = "";
        String itemSupplier = "";
        String dateFiter = "";
        if (searchStoreIssue.getSearchFromDate() == null) {
            String sDate1 = "1998/12/31";
            searchStoreIssue.setSearchFromDate(sDate1);
        }
        if (searchStoreIssue.getSearchToDate() == null) {
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchStoreIssue.setSearchToDate(date);
        }
        if (searchStoreIssue.getToday()) {
            dateFiter += "  AND date(i.createdDate)=CURDATE() ";
        } else {
            dateFiter += " AND date(i.createdDate) between  ' " + searchStoreIssue.getSearchFromDate() + " ' AND  " + "'" + searchStoreIssue.getSearchToDate() + " ' ";
        }
        if (searchStoreIssue.getSearchFromStoreId().getStoreId() != 0)
            itemStore = " AND i.icFromStoreId.storeId=" + searchStoreIssue.getSearchFromStoreId().getStoreId() + " ";
        if (searchStoreIssue.getSearchToStoreId().getStoreId() != 0)
            itemSupplier = " AND i.icToStoreId.storeId=" + searchStoreIssue.getSearchToStoreId().getStoreId() + " ";
        queryInya = "Select i from TinvIssueClinic i Where i.issueIndentStatus=false " + dateFiter + itemStore + "  " + itemSupplier + " order by i.icId desc";
        // System.out.println(queryInya);
        long count = (Long) entityManager.createQuery("Select count(i.icId) from TinvIssueClinic i Where i.issueIndentStatus=false " + dateFiter + itemStore + "  " + itemSupplier + " order by i.icId desc").getSingleResult();
        List<TinvIssueClinic> ItemList = entityManager.createQuery(queryInya, TinvIssueClinic.class).setFirstResult(((searchStoreIssue.getOffset() - 1) * searchStoreIssue.getLimit())).setMaxResults(searchStoreIssue.getLimit()).getResultList();
        if (ItemList.size() > 0)
            ItemList.get(0).setCount(count);
        return ItemList;
    }

}
            
