package com.cellbeans.hspa.tradtestreport;

import com.cellbeans.hspa.TenantContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/trad_test_report")
public class TradTestReportController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    TradTestReportRepository tradTestReportRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TradTestReport tradTestReport) {
        TenantContext.setCurrentTenant(tenantName);
        if (tradTestReport.getTrContent() != null) {
            tradTestReportRepository.save(tradTestReport);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("byid/{ttId}")
    public TradTestReport read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ttId") Long ttId) {
        TenantContext.setCurrentTenant(tenantName);
        TradTestReport tradTestReport = tradTestReportRepository.getById(ttId);
        return tradTestReport;
    }

    @RequestMapping("byPatientId/{patientId}")
    public TradTestReport readByPatientId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId) {
        TenantContext.setCurrentTenant(tenantName);
        TradTestReport tradTestReport = tradTestReportRepository.findByTrPatientIdPatientIdEquals(patientId);
        return tradTestReport;
    }

    @RequestMapping("update")
    public TradTestReport update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TradTestReport tradTestReport) {
        TenantContext.setCurrentTenant(tenantName);
        return tradTestReportRepository.save(tradTestReport);
    }

}
