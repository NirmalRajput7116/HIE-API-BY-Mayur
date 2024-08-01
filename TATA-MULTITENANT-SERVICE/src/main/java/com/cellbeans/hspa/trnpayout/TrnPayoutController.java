package com.cellbeans.hspa.trnpayout;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/trn_payout")
public class TrnPayoutController {

    Map<String, String> respMap = new HashMap<>();
    @Autowired
    TrnPayoutRepository trnPayoutRepository;

    @PersistenceContext
    EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnPayout trnPayout) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnPayout.getPayoutServiceType() != null) {
            TrnPayout obj = trnPayoutRepository.save(trnPayout);
            respMap.put("payoutId", String.valueOf(obj.getPayoutId()));
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Record");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TrnPayout> list(
            @RequestHeader("X-tenantId") String tenantName,
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "size", required = false, defaultValue = "100") String size,
            @RequestParam(value = "qString", required = false) String qString,
            @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
            @RequestParam(value = "col", required = false, defaultValue = "payoutId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return trnPayoutRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return trnPayoutRepository.findByPayoutServiceTypeContainsAndIsActiveTrueAndIsDeletedFalse(qString,
                    PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }
}