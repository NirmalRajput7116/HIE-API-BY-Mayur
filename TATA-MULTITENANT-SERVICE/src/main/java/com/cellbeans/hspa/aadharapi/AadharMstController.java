package com.cellbeans.hspa.aadharapi;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aadhar")
public class AadharMstController {

    @Autowired
    AadharMstRepository aadharMstRepository;

    @GetMapping
    @RequestMapping("findByAadharNo/{aadharNo}")
    public List<AadharMst> findByAadharNo(@RequestHeader("X-tenantId") String tenantName, @PathVariable("aadharNo") String aadharNo) {
        TenantContext.setCurrentTenant(tenantName);
        return aadharMstRepository.findAllByAadharNoContains(aadharNo);

    }

}
