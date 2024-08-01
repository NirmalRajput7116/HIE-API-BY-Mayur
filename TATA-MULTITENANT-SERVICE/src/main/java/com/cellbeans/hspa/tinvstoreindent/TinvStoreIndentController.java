package com.cellbeans.hspa.tinvstoreindent;

import com.cellbeans.hspa.TenantContext;

import com.cellbeans.hspa.invstore.InvStore;
import com.cellbeans.hspa.invstore.InvStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_store_indent")
public class TinvStoreIndentController {
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TinvStoreIndentRepository tinvStoreIndentRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    InvStoreRepository invStoreRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvStoreIndent tinvStoreIndent) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvStoreIndent.getSiToStoreId().getStoreId() != 0) {
            long storeId = tinvStoreIndent.getSiFromStoreId().getStoreId();
            InvStore invStore = invStoreRepository.getById(storeId);
            if (invStore.getStorePrefix() != null) {
                tinvStoreIndent.setSiIndentNo(invStore.getStorePrefix() + "/REQ/" + codeForGengeration(tenantName));
                tinvStoreIndentRepository.save(tinvStoreIndent);
                respMap.put("siId", String.valueOf(tinvStoreIndent.getSiId()));
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
        List<TinvStoreIndent> records;
        records = tinvStoreIndentRepository.findBySiTotalAmountContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{siId}")
    public TinvStoreIndent read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("siId") Long siId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvStoreIndent tinvStoreIndent = tinvStoreIndentRepository.getById(siId);
        return tinvStoreIndent;
    }

    @RequestMapping("update")
    public TinvStoreIndent update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvStoreIndent tinvStoreIndent) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvStoreIndentRepository.save(tinvStoreIndent);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TinvStoreIndent> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "siId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tinvStoreIndentRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tinvStoreIndentRepository.findBySiTotalAmountContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{siId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("siId") Long siId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvStoreIndent tinvStoreIndent = tinvStoreIndentRepository.getById(siId);
        if (tinvStoreIndent != null) {
            tinvStoreIndent.setIsDeleted(true);
            tinvStoreIndentRepository.save(tinvStoreIndent);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @PutMapping("approve/{siId}")
    public Map<String, String> approve(@RequestHeader("X-tenantId") String tenantName, @PathVariable("siId") Long siId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvStoreIndent tinvStoreIndent = tinvStoreIndentRepository.getById(siId);
        if (tinvStoreIndent != null) {
            tinvStoreIndent.setSiIndentApprove(true);
            tinvStoreIndentRepository.save(tinvStoreIndent);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @PutMapping("setUregencyFlag/{siId}/{value}")
    public Map<String, String> setUregencyFlag(@RequestHeader("X-tenantId") String tenantName, @PathVariable("siId") Long siId, @PathVariable("value") boolean value) {
        TenantContext.setCurrentTenant(tenantName);
        TinvStoreIndent tinvStoreIndent = tinvStoreIndentRepository.getById(siId);
        if (tinvStoreIndent != null) {
            tinvStoreIndent.setSiUrgencyFlag(value);
            tinvStoreIndentRepository.save(tinvStoreIndent);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @PutMapping("freeze/{siId}")
    public Map<String, String> freeze(@RequestHeader("X-tenantId") String tenantName, @PathVariable("siId") Long siId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvStoreIndent tinvStoreIndent = tinvStoreIndentRepository.getById(siId);
        if (tinvStoreIndent != null) {
            tinvStoreIndent.setSiFreezeIndent(true);
            tinvStoreIndentRepository.save(tinvStoreIndent);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;

    }

    @RequestMapping("/fromStore/")
    public List<TinvStoreIndent> getListByStoreId(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvStoreIndent tinvStoreIndent) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvStoreIndentRepository.findBySiFromStoreIdStoreIdAndSiToStoreIdStoreIdAndStoreIndentUnitIdUnitIdAndSiFreezeIndentTrueAndSiIndentApproveTrueAndIsActiveTrueAndIsDeletedFalse(tinvStoreIndent.getSiToStoreId().getStoreId(), tinvStoreIndent.getSiFromStoreId().getStoreId(), tinvStoreIndent.getStoreIndentUnitId().getUnitId());
    }

    @RequestMapping("/fromStoreforCentral/")
    public List<TinvStoreIndent> getListByStoreIdforCentral(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvStoreIndent tinvStoreIndent) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvStoreIndentRepository.findBySiFromStoreIdStoreIdAndSiToStoreIdStoreIdAndSiFreezeIndentTrueAndSiIndentApproveTrueAndIsActiveTrueAndIsDeletedFalse(tinvStoreIndent.getSiToStoreId().getStoreId(), tinvStoreIndent.getSiFromStoreId().getStoreId());
    }

    @RequestMapping("/fromStoreforCentralByCheck/")
    public List<TinvStoreIndent> getListByStoreIdforCentralCheck(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvStoreIndent tinvStoreIndent) {
        TenantContext.setCurrentTenant(tenantName);
        String Query = "select * from tinv_store_indent si where si.si_from_store_id=" + tinvStoreIndent.getSiToStoreId().getStoreId() + " and si.si_to_store_id=" + tinvStoreIndent.getSiFromStoreId().getStoreId() + " " + "and si.si_indent_approve=1 and si.is_active=1 and si.is_deleted=0 and si.si_is_cancle=false " + "and si.si_id not in (SELECT i.ici_si_id FROM tinv_issue_clinic_item i where i.ici_si_id IS NOT NULL ) order by si.si_id desc";
        System.out.println("Query" + Query);
        return entityManager.createNativeQuery(Query, TinvStoreIndent.class).getResultList();
    }

    @RequestMapping("/indendforissueserach/")
    public List<TinvStoreIndent> getIndendSearch(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchStoreIndent searchStoreIndent) {
        TenantContext.setCurrentTenant(tenantName);
        String Query = "select si from TinvStoreIndent si where si.siIndentApprove=1 and si.isActive=1 and si.isDeleted=0 and si.siId not in (SELECT i.iciSiId FROM TinvIssueClinicItem i where i.iciSiId IS NOT NULL ) ";
        if (searchStoreIndent.getSearchFromDate() == null) {
            String sDate1 = "1998-12-31";
            searchStoreIndent.setSearchFromDate(sDate1);
        }
        if (searchStoreIndent.getSearchToDate() == null) {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            searchStoreIndent.setSearchToDate(date);
        }
        if (searchStoreIndent.getToday()) {
            if (searchStoreIndent.getDateSearchType() == 0) {
                Query += " and date(si.siIndentDate)=curdate() ";
            } else {
                Query += " and date(si.siDueDate)=curdate() ";
            }
        } else {
            if (searchStoreIndent.getDateSearchType() == 0) {
                Query += " and (date(si.siIndentDate) between '" + searchStoreIndent.getSearchFromDate() + "' and '" + searchStoreIndent.getSearchToDate() + "') ";
            } else {
                Query += " and (date(si.siDueDate) between '" + searchStoreIndent.getSearchFromDate() + "' and '" + searchStoreIndent.getSearchToDate() + "') ";
            }
        }
        if (searchStoreIndent.getSearchFromStoreId().getStoreId() != 0) {
            Query += "AND si.siFromStoreId.storeId=" + searchStoreIndent.getSearchFromStoreId().getStoreId() + " ";
        }
        if (searchStoreIndent.getSearchToStoreId().getStoreId() != 0) {
            Query += "AND si.siToStoreId.storeId=" + searchStoreIndent.getSearchToStoreId().getStoreId() + " ";
        }
        String countQuery = StringUtils.replace(Query, "si fr", "count(si.siId) fr");
        long count = (Long) entityManager.createQuery(countQuery).getSingleResult();
        Query += " order by si.siId desc";
        System.out.println("Query" + Query);
        List<TinvStoreIndent> list = entityManager.createQuery(Query, TinvStoreIndent.class).setFirstResult((searchStoreIndent.getOffset() - 1) * searchStoreIndent.getLimit()).setMaxResults(searchStoreIndent.getLimit()).getResultList();
        if (list.size() > 0) {
            list.get(0).setCount(count);
        }
        return list;
    }

    @RequestMapping("getIndentListNotDone")
    public List<TinvStoreIndent> getIndentListNotDone(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "limit", required = false, defaultValue = "10") String limit, @RequestParam(value = "Offset", required = false, defaultValue = "0") int Offset) {
        TenantContext.setCurrentTenant(tenantName);
        String Query = "SELECT * FROM tinv_store_indent i where i.si_indent_approve=1 AND i.si_is_cancle=false and i.si_id not in (SELECT i.ici_si_id FROM tinv_issue_clinic_item i where i.ici_si_id IS NOT NULL ) order by i.si_id Desc limit " + ((Offset - 1) * Integer.parseInt(limit)) + "," + limit;
        List<TinvStoreIndent> list = entityManager.createNativeQuery(Query, TinvStoreIndent.class).getResultList();
        String CountQuery = "SELECT count(i.si_id) FROM tinv_store_indent i where i.si_indent_approve=1 AND i.si_is_cancle=false and i.si_id not in (SELECT i.ici_si_id FROM tinv_issue_clinic_item i where i.ici_si_id IS NOT NULL ) ";
        // System.out.println(CountQuery);
        BigInteger temp = new BigInteger(entityManager.createNativeQuery(CountQuery).getSingleResult().toString());
        long count = temp.longValue();
        if (list.size() > 0)
            list.get(0).setCount(count);
        return list;
    }

    @RequestMapping("search")
    public List<TinvStoreIndent> getListOfItemsDependsOnSerch(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchStoreIndent searchStoreIndent) {
        TenantContext.setCurrentTenant(tenantName);
        if (searchStoreIndent == null) {
            SearchStoreIndent sb = new SearchStoreIndent();
            searchStoreIndent = sb;
        }
        String queryInya = "";
        String itemStore = "";
        String itemSupplier = "";
        if (searchStoreIndent.getSearchIndentNo() == null)
            searchStoreIndent.setSearchIndentNo("");
        if (searchStoreIndent.getSearchFromDate() == null) {
            String sDate1 = "1998/12/31";
            searchStoreIndent.setSearchFromDate(sDate1);
        }
        if (searchStoreIndent.getSearchToDate() == null) {
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchStoreIndent.setSearchToDate(date);
        }
        if (searchStoreIndent.getSearchFromStoreId().getStoreId() != 0)
            itemStore = "AND i.siFromStoreId.storeId=" + searchStoreIndent.getSearchFromStoreId().getStoreId() + "";
        if (searchStoreIndent.getSearchToStoreId().getStoreId() != 0)
            itemSupplier = "AND i.siToStoreId.storeId=" + searchStoreIndent.getSearchToStoreId().getStoreId() + "";
        queryInya = "Select i from TinvStoreIndent i Where i.isActive=1 AND i.isDeleted=0 AND  i.storeIndentUnitId.unitId=" + searchStoreIndent.getUnitId() + " And i.siIndentNo like '%" + searchStoreIndent.getSearchIndentNo() + "%'" + "  AND date(i.createdDate) between   " + " ' " + searchStoreIndent.getSearchFromDate() + " ' AND  " + "'" + searchStoreIndent.getSearchToDate() + " ' " + itemStore + "  " + itemSupplier + " AND i.siIsCancle=false order by i.siId desc";
        // System.out.println(queryInya);
        long count = (Long) entityManager.createQuery("Select count(i.siId) from TinvStoreIndent i Where i.isActive=1 AND i.isDeleted=0 AND i.storeIndentUnitId.unitId=" + searchStoreIndent.getUnitId() + " And i.siIndentNo like '%" + searchStoreIndent.getSearchIndentNo() + "%'" + "  AND date(i.createdDate) between   " + " ' " + searchStoreIndent.getSearchFromDate() + " ' AND  " + "'" + searchStoreIndent.getSearchToDate() + " ' " + itemStore + "  " + itemSupplier + " AND i.siIsCancle=false order by i.siId desc").getSingleResult();
        List<TinvStoreIndent> ItemList = entityManager.createQuery(queryInya, TinvStoreIndent.class).setFirstResult(searchStoreIndent.getOffset()).setMaxResults(searchStoreIndent.getLimit()).getResultList();
        if (ItemList.size() > 0)
            ItemList.get(0).setCount(count);
        return ItemList;
    }

    @RequestMapping("codeForGengeration")
    public String codeForGengeration(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        String codeGeneration = "";
        try {
            String Query = "select ifnull(max(si_id), 0) from tinv_store_indent ";
            System.out.println(Query);
            codeGeneration = "" + entityManager.createNativeQuery(Query).getSingleResult();
            if (codeGeneration.equals("null") || codeGeneration == "") {
                codeGeneration = "0";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return codeGeneration;

    }

}
            
