package com.cellbeans.hspa.mstvaccautochart;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.trnvaccinationchart.TrnVaccinationChart;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@RestController
@RequestMapping("/mst_vacc_auto_chart")
public class MstVaccAutoChartController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstVaccAutoChartRepository mstVaccAutoChartRepository;
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private MstVaccAutoChartService mstVaccAutoChartService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstVaccAutoChart mstVaccAutoChart) {
        TenantContext.setCurrentTenant(tenantName);
        mstVaccAutoChart.setCreatedDate(new Date());
        mstVaccAutoChart.setIsActive(true);
        mstVaccAutoChart.setIsDeleted(false);
        mstVaccAutoChartRepository.save(mstVaccAutoChart);
        respMap.put("success", "1");
        respMap.put("msg", "Added Successfully");
        return respMap;
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MstVaccAutoChart> records;
        records = mstVaccAutoChartRepository.findByVacDoseContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{vacId}")
    public MstVaccAutoChart read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vacId") Long vacId) {
        TenantContext.setCurrentTenant(tenantName);
        MstVaccAutoChart mstVaccAutoChart = mstVaccAutoChartRepository.getById(vacId);
        return mstVaccAutoChart;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstVaccAutoChart mstVaccAutoChart) {
        TenantContext.setCurrentTenant(tenantName);
        mstVaccAutoChartRepository.save(mstVaccAutoChart);
        respMap.put("success", "1");
        respMap.put("msg", "updated Successfully");
        return respMap;
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstVaccAutoChart> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "vacId") String col, @RequestParam Integer patientCategoryId) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstVaccAutoChartRepository.findAllByIsActiveTrueAndIsDeletedFalseAndVacPatientCategoryVpcId(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)), patientCategoryId);

        } else {
            return mstVaccAutoChartRepository.findAllByIsActiveTrueAndIsDeletedFalseAndVacPatientCategoryVpcId(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)), patientCategoryId);

        }

    }

    @RequestMapping("categoryChartList/{patientCategoryId}/{patientId}")
    public String list(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientCategoryId") Integer patientCategoryId, @PathVariable("patientId") String patientId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnVaccinationChart tempObj = null;
        List<MstVaccAutoChart> list = new ArrayList<MstVaccAutoChart>();
        MstVaccAutoChart ob = null;
        ObjectMapper mapperObj = new ObjectMapper();
        List<MstVaccAutoChart> listMstVaccAutoChart = entityManager.createNativeQuery("SELECT * FROM mst_vacc_auto_chart vac where vac_patient_category= " + patientCategoryId + " and vac.is_deleted=0 and vac.is_active=1", MstVaccAutoChart.class).getResultList();
        for (MstVaccAutoChart obj : listMstVaccAutoChart) {
            ob = new MstVaccAutoChart();
            ob = obj;
            if (obj != null) {
                try {
                    tempObj = new TrnVaccinationChart();
                    if (obj.getVacId() >= 0)
                        if (obj.getVacId() >= 0)
                            tempObj = (TrnVaccinationChart) entityManager.createNativeQuery("SELECT * FROM trn_vaccination_chart vac inner Join mst_vaccination_chart vc on vac.chart_id=vc.vc_id where vac.is_deleted=0 and vac.is_active=1 and vc.patient_id=" + patientId + " and vac.vac_id=" + ob.getVacId(), TrnVaccinationChart.class).getSingleResult();
                    String jsonStr = mapperObj.writeValueAsString(tempObj);
                    obj.setTrnVaccinationChart(mapperObj.writeValueAsString(jsonStr));

                } catch (Exception ex) {
                }

            }
            list.add(ob);
        }
        // System.out.println("end of code :" + list);
        String jsonString = new Gson().toJson(list);
        // System.out.println("jsonString :" + jsonString);
        return jsonString;
        //     return mstVaccAutoChartRepository.findAllByIsActiveTrueAndIsDeletedFalseAndVacPatientCategoryVpcId(patientCategoryId);
    }

/*
@RequestMapping("categoryChartList/{patientCategoryId}")
public List<MstVaccAutoChart> list(@RequestHeader("X-tenantId") String tenantName, @PathVariable Integer patientCategoryId) {

      return mstVaccAutoChartRepository.findAllByIsActiveTrueAndIsDeletedFalseAndVacPatientCategoryVpcId(patientCategoryId);



}*/

    @PutMapping("delete/{vacId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vacId") Long vacId) {
        TenantContext.setCurrentTenant(tenantName);
        MstVaccAutoChart mstVaccAutoChart = mstVaccAutoChartRepository.getById(vacId);
        if (mstVaccAutoChart != null) {
            mstVaccAutoChart.setIsDeleted(true);
            mstVaccAutoChartRepository.save(mstVaccAutoChart);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = mstVaccAutoChartService.getMstVaccAutoChartForDropdown(page, size, globalFilter);
        return items;
    }

}
