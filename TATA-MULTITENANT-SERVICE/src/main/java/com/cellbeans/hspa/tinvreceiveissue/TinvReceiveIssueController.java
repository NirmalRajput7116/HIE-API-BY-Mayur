package com.cellbeans.hspa.tinvreceiveissue;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.invstore.InvStore;
import com.cellbeans.hspa.invstore.InvStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/tinv_receive_issue")
public class TinvReceiveIssueController {
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TinvReceiveIssueRepository tinvReceiveIssueRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    InvStoreRepository invStoreRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvReceiveIssue tinvReceiveIssue) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvReceiveIssue.getRiIssueDate() != null) {
            long storeId = tinvReceiveIssue.getRiRecivingStoreId().getStoreId();
            InvStore invStore = invStoreRepository.getById(storeId);
            if (invStore.getStorePrefix() != null) {
                tinvReceiveIssue.setRiNo(invStore.getStorePrefix() + "/REC/" + createAutoRInumber(tenantName));
                tinvReceiveIssueRepository.save(tinvReceiveIssue);
                respMap.put("riId", String.valueOf(tinvReceiveIssue.getRiId()));
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
        List<TinvReceiveIssue> records;
        records = tinvReceiveIssueRepository.findByRiIssueDateContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{riId}")
    public TinvReceiveIssue read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("riId") Long riId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvReceiveIssue tinvReceiveIssue = tinvReceiveIssueRepository.getById(riId);
        return tinvReceiveIssue;
    }

    @RequestMapping("update")
    public TinvReceiveIssue update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvReceiveIssue tinvReceiveIssue) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvReceiveIssueRepository.save(tinvReceiveIssue);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TinvReceiveIssue> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "riId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tinvReceiveIssueRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tinvReceiveIssueRepository.findByRiIssueDateContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{riId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("riId") Long riId) {
        TenantContext.setCurrentTenant(tenantName);
        TinvReceiveIssue tinvReceiveIssue = tinvReceiveIssueRepository.getById(riId);
        if (tinvReceiveIssue != null) {
            tinvReceiveIssue.setIsDeleted(true);
            tinvReceiveIssueRepository.save(tinvReceiveIssue);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("search")
    public List<TinvReceiveIssue> getListOfItemsDependsOnSerch(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchStoreReceiveIssue searchStoreReceiveIssue) {
        TenantContext.setCurrentTenant(tenantName);
        if (searchStoreReceiveIssue == null) {
            SearchStoreReceiveIssue sb = new SearchStoreReceiveIssue();
            searchStoreReceiveIssue = sb;
        }
        String queryInya = "";
        String itemStore = "";
        String itemSupplier = "";
        if (searchStoreReceiveIssue.getSearchReciveNo() == null)
            searchStoreReceiveIssue.setSearchReciveNo("");
        if (searchStoreReceiveIssue.getSearchFromDate() == null) {
            String sDate1 = "1998/12/31";
            searchStoreReceiveIssue.setSearchFromDate(sDate1);
        }
        if (searchStoreReceiveIssue.getSearchToDate() == null) {
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchStoreReceiveIssue.setSearchToDate(date);
        }
        if (searchStoreReceiveIssue.getSearchFromStoreId().getStoreId() != 0)
            itemStore = "AND i.riFromStoreId.storeId=" + searchStoreReceiveIssue.getSearchFromStoreId().getStoreId() + "";
        if (searchStoreReceiveIssue.getSearchToStoreId().getStoreId() != 0)
            itemSupplier = "AND i.riRecivingStoreId.storeId=" + searchStoreReceiveIssue.getSearchToStoreId().getStoreId() + "";
        queryInya = "Select i from TinvReceiveIssue i Where i.isActive=1 AND i.isDeleted=0 AND i.riUnitId.unitId=" + searchStoreReceiveIssue.getUnitId() + " And i.riIssueNumber like '%" + searchStoreReceiveIssue.getSearchReciveNo() + "%'" + " and  date(i.createdDate) between   " + " ' " + searchStoreReceiveIssue.getSearchFromDate() + " ' AND  " + "'" + searchStoreReceiveIssue.getSearchToDate() + " ' " + itemStore + "  " + itemSupplier + " order by i.riId desc";
        // System.out.println(queryInya);
        long count = (Long) entityManager.createQuery("Select count(i.riId) from TinvReceiveIssue i Where i.isActive=1 AND i.isDeleted=0 AND i.riUnitId.unitId=" + searchStoreReceiveIssue.getUnitId() + " And i.riIssueNumber like '%" + searchStoreReceiveIssue.getSearchReciveNo() + "%'" + " and  date(i.createdDate) between   " + " ' " + searchStoreReceiveIssue.getSearchFromDate() + " ' AND  " + "'" + searchStoreReceiveIssue.getSearchToDate() + " ' " + itemStore + "  " + itemSupplier + " order by i.riId desc").getSingleResult();
        List<TinvReceiveIssue> ItemList = entityManager.createQuery(queryInya, TinvReceiveIssue.class).setFirstResult(((searchStoreReceiveIssue.getOffset() - 1) * searchStoreReceiveIssue.getLimit())).setMaxResults(searchStoreReceiveIssue.getLimit()).getResultList();
        if (ItemList.size() > 0)
            ItemList.get(0).setCount(count);
        return ItemList;
    }

    @RequestMapping("createAutoRInumber")
    public String createAutoRInumber(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        int value;
        try {
            String Query = "SELECT ifnull(max(r.ri_id), 0) FROM tinv_receive_issue r";
            System.out.println("max number" + Query);
            BigInteger temp = (BigInteger) entityManager.createNativeQuery(Query).getSingleResult();
            value = temp.intValue() + 1;
            return Integer.toString(value);
        } catch (Exception e) {
            return "1";
        }
    }

}
            
