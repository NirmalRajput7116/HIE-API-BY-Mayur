package com.cellbeans.hspa.tinvreturnissue;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.tinvissueclinicitem.TinvIssueClinicItem;
import com.cellbeans.hspa.tinvreceiveissue.TinvReceiveIssue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_return_issue")
public class TinvReturnIssueController {

    @PersistenceContext
    EntityManager entityManager;

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    TinvReturnIssueRepository tinvReturnIssueRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TinvReturnIssue tinvReturnIssue) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvReturnIssue != null) {
            TinvReturnIssue obj = tinvReturnIssueRepository.save(tinvReturnIssue);
            respMap.put("reiId", String.valueOf(obj.getReiId()));
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("allreceivedByUnitId/{data}/{unitId}")
    public List<TinvReceiveIssue> allreceivedByUnitId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("data") String data, @PathVariable("unitId") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "select icir from TinvReceiveIssue icir where icir.riId=" + data + " and icir.riUnitId=" + unitId;
        return entityManager.createQuery(query, TinvReceiveIssue.class).getResultList();
    }

    @RequestMapping("getAllItemOfReceivedByriId/{riId}")
    public List<TinvIssueClinicItem> getAllItemOfReceivedByriId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("riId") long riId) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "select ici from TinvIssueClinicItem ici where ici.iciRiId.riId=" + riId;
        return entityManager.createQuery(query, TinvIssueClinicItem.class).getResultList();
    }

    @RequestMapping("codeForGengeration")
    public String codeForGengeration(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        String codeGeneration = "";
        try {
            String Query = "select ifnull(max(rei_id),0) as count from tinv_return_issue ";
            System.out.println(Query);
            codeGeneration = entityManager.createNativeQuery(Query).getSingleResult().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codeGeneration;
    }

    @RequestMapping("search")
    public List<TinvReturnIssue> getListOfItemsDependsOnSerch(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchReturnIssue searchReturnIssue) {
        TenantContext.setCurrentTenant(tenantName);
        if (searchReturnIssue == null) {
            SearchReturnIssue sb = new SearchReturnIssue();
            searchReturnIssue = sb;
        }
        String queryInya = "";
        String itemStore = "";
        String itemSupplier = "";
        if (searchReturnIssue.getSearchReturnNo() == null)
            searchReturnIssue.setSearchReturnNo("");
        if (searchReturnIssue.getSearchFromDate() == null) {
            String sDate1 = "1998/12/31";
            searchReturnIssue.setSearchFromDate(sDate1);
        }
        if (searchReturnIssue.getSearchToDate() == null) {
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            searchReturnIssue.setSearchToDate(date);
        }
        if (searchReturnIssue.getSearchFromStoreId().getStoreId() != 0)
            itemStore = "AND i.reiFromStoreId.storeId=" + searchReturnIssue.getSearchFromStoreId().getStoreId() + "";
        if (searchReturnIssue.getSearchToStoreId().getStoreId() != 0)
            itemSupplier = "AND i.reiToStoreId.storeId=" + searchReturnIssue.getSearchToStoreId().getStoreId() + "";
        queryInya = "Select i from TinvReturnIssue i Where i.reiUnitId.unitId=" + searchReturnIssue.getUnitId() + " And i.reiReturnNo like '%" + searchReturnIssue.getSearchReturnNo() + "%'" + "  AND date(i.createdDate) between   " + " ' " + searchReturnIssue.getSearchFromDate() + " ' AND  " + "'" + searchReturnIssue.getSearchToDate() + " ' " + itemStore + "  " + itemSupplier + " order by i.reiId desc";
        // System.out.println(queryInya);
        long count = (Long) entityManager.createQuery("Select count(i.reiId) from TinvReturnIssue i Where i.reiUnitId.unitId=" + searchReturnIssue.getUnitId() + " And i.reiReturnNo like '%" + searchReturnIssue.getSearchReturnNo() + "%'" + "  AND date(i.createdDate) between   " + " ' " + searchReturnIssue.getSearchFromDate() + " ' AND  " + "'" + searchReturnIssue.getSearchToDate() + " ' " + itemStore + "  " + itemSupplier + " ").getSingleResult();
        List<TinvReturnIssue> ItemList = entityManager.createQuery(queryInya, TinvReturnIssue.class).setFirstResult(((searchReturnIssue.getOffset() - 1) * searchReturnIssue.getLimit())).setMaxResults(searchReturnIssue.getLimit()).getResultList();
        if (ItemList.size() > 0)
            ItemList.get(0).setCount(count);
        return ItemList;
    }

}
