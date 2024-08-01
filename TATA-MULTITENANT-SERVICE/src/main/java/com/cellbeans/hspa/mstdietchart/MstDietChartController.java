package com.cellbeans.hspa.mstdietchart;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_diet_chart")
public class MstDietChartController {

    @Autowired
    MstDietChartRepository mstDietChartRepository;

    Map<String, String> respMap = new HashMap<String, String>();

    @GetMapping
    @RequestMapping("/list/{id}")
    public List<MstDietChart> list(@RequestHeader("X-tenantId") String tenantName, @PathVariable Long id) {
        TenantContext.setCurrentTenant(tenantName);
        return mstDietChartRepository.findByPatientIdAndIsDeletedFalseOrderByIdDesc(id);
    }

    @RequestMapping("delete/{deleteId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable Long deleteId) {
        TenantContext.setCurrentTenant(tenantName);
        MstDietChart mstDietChart = mstDietChartRepository.getById(deleteId);
        if (mstDietChart != null) {
            mstDietChart.setIsDeleted(true);
            mstDietChartRepository.save(mstDietChart);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
