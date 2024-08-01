package com.cellbeans.hspa.MstVisitService;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Transactional
@RestController
@RequestMapping("/trn_visit_service")
public class MstVisitServiceController {

    Map<String, String> respMap = new HashMap<String, String>();
    Map<String, Iterable<?>> respMap1 = new HashMap<String, Iterable<?>>();

    @Autowired
    MstVisitServiceRepository mstVisitServiceRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstVisitService mstVisitService) {
        TenantContext.setCurrentTenant(tenantName);
        if (true) {
            mstVisitServiceRepository.save(mstVisitService);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            respMap.put("vsId", mstVisitService.getVsId().toString());
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }
}
