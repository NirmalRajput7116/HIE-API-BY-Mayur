package com.cellbeans.hspa.mstvaccinationchart;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_vaccination_chart")
public class MstVaccinationChartController {

    @Autowired
    MstVaccinationChartRepository mstVaccinationChartRepository;

    Map<String, String> respMap = new HashMap<String, String>();

    @RequestMapping("addvaccchart")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstVaccinationChart mstVaccinationChart) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("mstVaccinationChart :"+ mstVaccinationChart);
        mstVaccinationChart.setCreatedDate(new Date());
        mstVaccinationChart.setIsActive(true);
        mstVaccinationChart.setIsDeleted(false);
        mstVaccinationChartRepository.save(mstVaccinationChart);
        respMap.put("vcId", String.valueOf(mstVaccinationChart.getVcId()));
        respMap.put("msg", "Added Successfully");
        return respMap;
    }

    @GetMapping
    @RequestMapping("/list/{patientId}")
    public List<MstVaccinationChart> list(@RequestHeader("X-tenantId") String tenantName, @PathVariable Long patientId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstVaccinationChartRepository.findByPatientIdAndIsDeletedFalseOrderByVcIdDesc(patientId);
    }

    @RequestMapping("delete/{deleteId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable Long deleteId) {
        TenantContext.setCurrentTenant(tenantName);
        MstVaccinationChart mstVaccinationChart = mstVaccinationChartRepository.getById(deleteId);
        if (mstVaccinationChart != null) {
            mstVaccinationChart.setIsDeleted(true);
            mstVaccinationChartRepository.save(mstVaccinationChart);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
