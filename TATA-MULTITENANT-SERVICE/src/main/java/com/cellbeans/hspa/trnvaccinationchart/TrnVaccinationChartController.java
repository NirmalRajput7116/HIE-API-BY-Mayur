package com.cellbeans.hspa.trnvaccinationchart;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.invitem.InvItem;
import com.cellbeans.hspa.invitem.InvItemRepository;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstvaccinationchart.MstVaccinationChart;
import com.cellbeans.hspa.mstvaccinationchart.MstVaccinationChartRepository;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trn_vaccination")
public class TrnVaccinationChartController {

    @Autowired
    InvItemRepository invItemRepository;

    @Autowired
    TrnVaccinationChartRepository trnVaccinationChartRepository;

    @Autowired
    MstVaccinationChartRepository mstVaccinationChartRepository;

    @PersistenceContext
    EntityManager entityManager;

    Map<String, String> respMap = new HashMap<String, String>();

    @RequestMapping("addvaccinationchart")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody String trnVaccinationChart) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("mylist :"+trnVaccinationChart);
        JSONArray jsonArray = new JSONArray(trnVaccinationChart);
        for (int i = 0; i < jsonArray.length(); i++) {
            org.json.JSONObject myObject = jsonArray.getJSONObject(i);
            Gson gson = new Gson();
            TrnVaccinationChart trnVaccChart = gson.fromJson(myObject.toString(), TrnVaccinationChart.class);
            // System.out.println("trnVaccChart.getChartId():"+trnVaccChart.getChartId());
            trnVaccChart.setCreatedDate(new Date());
            trnVaccChart.setChartId(trnVaccChart.getChartId());
            // System.out.println("trnVaccChart new:"+trnVaccChart);
            trnVaccinationChartRepository.save(trnVaccChart);
        }
        respMap.put("msg", "Added Successfully");
        return respMap;
    }

    @RequestMapping("byall/{data}")
    public List<InvItem> getAllItemsByAll(@RequestHeader("X-tenantId") String tenantName, @PathVariable("data") String data) {
        TenantContext.setCurrentTenant(tenantName);
        return invItemRepository.findByItemNameContainsOrItemCodeContainsOrItemBrandNameContainsAndIsActiveTrueAndIsDeletedFalse(data, data, data);

    }

    @RequestMapping(value = "/savevaccinationschedule", method = RequestMethod.POST)
    public Map<String, String> saveDietSchedule(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnVaccinationChart trnVaccinationChart) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, String> respMap = new HashMap<String, String>();
        if (trnVaccinationChart.getChartId().getVcId() == null) {
            trnVaccinationChart.setIsDeleted(false);
            trnVaccinationChart.setCreatedDate(new Date());
            MstVaccinationChart mstVaccinationChart = new MstVaccinationChart();
            //	mstVaccinationChart.setCreatedBy(trnVaccinationChart.getGivenBy());
            mstVaccinationChart.setPatientId(trnVaccinationChart.getChartId().getPatientId());
            trnVaccinationChart.setChartId(mstVaccinationChart);
            trnVaccinationChart = trnVaccinationChartRepository.save(trnVaccinationChart);
        } else {
            trnVaccinationChart.setIsDeleted(false);
            MstVaccinationChart mstVaccinationChart = mstVaccinationChartRepository.findByVcId(trnVaccinationChart.getChartId().getVcId());
            trnVaccinationChart.setChartId(mstVaccinationChart);
            trnVaccinationChart = trnVaccinationChartRepository.save(trnVaccinationChart);
        }
        respMap.put("status", "200");
        respMap.put("message", "success");
        respMap.put("vaccinationId", "" + trnVaccinationChart.getChartId().getVcId());
        return respMap;
    }

    @RequestMapping("/getVaccinationScheduleList/{id}")
    public List<TrnVaccinationChart> diet_item_list(@RequestHeader("X-tenantId") String tenantName, @PathVariable Long id) {
        TenantContext.setCurrentTenant(tenantName);
        String Query = "Select trn_vc from TrnVaccinationChart trn_vc where trn_vc.chartid.vcId=" + id + " order by trn_vc.itemId.itemGenericName desc";
        return entityManager.createQuery(Query).getResultList();

    }

    @RequestMapping(value = "/updatevaccine", method = RequestMethod.POST)
    public Map<String, String> updateList(@RequestHeader("X-tenantId") String tenantName, @RequestBody JSONObject object) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, String> respMap = new HashMap<String, String>();
        TrnVaccinationChart trnVaccinationChart = trnVaccinationChartRepository.findByVcId(Long.parseLong(object.get("id").toString()));
        trnVaccinationChart.setGivenDate(String.valueOf(new Date()));
        MstStaff mstStaff = new MstStaff();
        mstStaff.setStaffId(Integer.parseInt("" + object.get("givenBy")));
        trnVaccinationChart.setGivenby(mstStaff);
        trnVaccinationChartRepository.save(trnVaccinationChart);
        respMap.put("status", "200");
        respMap.put("message", "success");
        return respMap;

    }

    @RequestMapping(value = "/updatevaccinechart", method = RequestMethod.POST)
    public Map<String, String> updateListChart(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnVaccinationChart object) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, String> respMap = new HashMap<String, String>();
        // System.out.println("updatgeVacChart :"+object);
        TrnVaccinationChart newObj = trnVaccinationChartRepository.findByVacIdVacIdAndChartIdVcId(object.getVacId().getVacId(), object.getChartId().getVcId());
        if (object.getDueDate() != null)
            newObj.setDueDate(object.getDueDate());
        if (object.getComments() != null)
            newObj.setComments(object.getComments());
        if (object.getAdminDate() != null)
            newObj.setAdminDate(object.getAdminDate());
        trnVaccinationChartRepository.save(newObj);
        respMap.put("status", "200");
        respMap.put("message", "success");
        return respMap;

    }

    @GetMapping
    @RequestMapping("/chartlist/{patientId}")
    public List<TrnVaccinationChart> list(@RequestHeader("X-tenantId") String tenantName, @PathVariable long patientId) {
        TenantContext.setCurrentTenant(tenantName);
        return trnVaccinationChartRepository.findByChart(patientId);
    }

    @GetMapping
    @RequestMapping("delete/{vacId}/{chartId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vacId") Long vacId, @PathVariable("chartId") Long chartId) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("vacId "+vacId+"  chartId"+chartId);
        List<TrnVaccinationChart> trnVaccinationChart = trnVaccinationChartRepository.findByChartIdVcId(chartId);
        for (int i = 0; i < trnVaccinationChart.size(); i++) {
            if (trnVaccinationChart.get(i) != null) {
                trnVaccinationChart.get(i).setIsDeleted(true);
                trnVaccinationChartRepository.save(trnVaccinationChart.get(i));
                respMap.put("msg", "Operation Successful");
                respMap.put("success", "1");

            } else {
                respMap.put("msg", "Operation Failed");

            }
        }
        return respMap;
    }

}
