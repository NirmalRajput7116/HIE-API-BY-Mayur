package com.cellbeans.hspa.advancesearch;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mstvisit.MstVisit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/advance")
public class AdvaceSearchController {

    @Autowired
    AdvanceSearchDao objMstPatientDao;

    @RequestMapping(value = "/advanceSearch", method = RequestMethod.POST, consumes = {"application/json"})
    public @ResponseBody
    List<?> patientfilter(@RequestHeader("X-tenantId") String tenantName, @RequestBody AdvanceSearch objAdvanceSearch) throws Exception {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("query parameter" + objAdvanceSearch);
        return objMstPatientDao.findDetails(objAdvanceSearch);
    }

    @RequestMapping(value = "geListByPatientid", method = RequestMethod.POST, consumes = {"application/json"})
    public List<MstVisit> patientid(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "patientId") long patientId) {
        TenantContext.setCurrentTenant(tenantName);
        return objMstPatientDao.findAllByPatientId(patientId);
    }

}
            
