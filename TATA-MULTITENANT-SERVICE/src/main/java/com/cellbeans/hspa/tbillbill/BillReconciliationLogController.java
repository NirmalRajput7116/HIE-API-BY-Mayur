package com.cellbeans.hspa.tbillbill;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tbill_reconciliation_log")
public class BillReconciliationLogController {

    @Autowired
    BillReconciliationLogRepository billReconciliationLogRepository;

    Map<String, String> respMap = new HashMap<String, String>();

    @RequestMapping("create")
    @org.springframework.transaction.annotation.Transactional(rollbackFor = {Exception.class})
    public BillReconciliationLog create(@RequestHeader("X-tenantId") String tenantName, @RequestBody BillReconciliationLog billReconciliationLog) {
        TenantContext.setCurrentTenant(tenantName);
        return billReconciliationLogRepository.save(billReconciliationLog);
    }
}
