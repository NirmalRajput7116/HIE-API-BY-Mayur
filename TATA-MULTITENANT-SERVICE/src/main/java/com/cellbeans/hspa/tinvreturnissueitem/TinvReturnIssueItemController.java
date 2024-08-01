package com.cellbeans.hspa.tinvreturnissueitem;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_return_issue_item")
public class TinvReturnIssueItemController {

    @PersistenceContext
    EntityManager entityManager;

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    TinvReturnIssueItemRepository tinvReturnIssueItemRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvReturnIssueItem> tinvReturnIssueList) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvReturnIssueList.size() > 0) {
            tinvReturnIssueItemRepository.saveAll(tinvReturnIssueList);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byreiId/{id}")
    public List<TinvReturnIssueItem> getItemsById(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        return tinvReturnIssueItemRepository.findAllByReiItReiIdReiIdAndIsActiveTrueAndIsDeletedFalse(id);
    }

}
