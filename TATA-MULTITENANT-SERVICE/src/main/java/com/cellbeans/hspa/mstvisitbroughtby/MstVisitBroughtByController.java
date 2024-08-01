package com.cellbeans.hspa.mstvisitbroughtby;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@RestController
@RequestMapping("/mst_visit_brought_by")
public class MstVisitBroughtByController {
    Map<String, String> respMap = new HashMap<String, String>();
    Map<String, Iterable<?>> respMap1 = new HashMap<String, Iterable<?>>();

    @Autowired
    MstVisitBroughtByRepository mstVisitBroughtByRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<MstVisitBroughtBy> mstVisitBroughtByList) {
        TenantContext.setCurrentTenant(tenantName);
        mstVisitBroughtByRepository.saveAll(mstVisitBroughtByList);
        respMap.put("success", "1");
        respMap.put("msg", "Added Successfully");
        return respMap;
    }

    @RequestMapping("byvisitid/{visitId}")
    public List<MstVisitBroughtBy> byvisitid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("visitId") Long visitId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstVisitBroughtByRepository.findAllByVbbVisitIdVisitIdEquals(visitId);
    }
}
