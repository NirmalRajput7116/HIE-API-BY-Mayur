package com.cellbeans.hspa.patientdietschedule;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mstdietchart.MstDietChart;
import com.cellbeans.hspa.mstdietchart.MstDietChartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/patientdietschedule")
public class PatientDietScheduleController {

    @Autowired
    PatientDietScheduleRepository patientDietScheduleRepository;

    @Autowired
    MstDietChartRepository mstDietChartRepository;

    @PersistenceContext
    EntityManager entityManager;

    Map<String, String> respMap = new HashMap<String, String>();

    @RequestMapping(value = "/savedietschedule", method = RequestMethod.POST)
    public Map<String, Object> saveDietSchedule(@RequestHeader("X-tenantId") String tenantName, @RequestBody PatientDietSchedule patientDietSchedule) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> respMap = new HashMap<String, Object>();
        if (patientDietSchedule.getDietChartId().getId() == null) {
            patientDietSchedule.setIsDeleted(false);
            patientDietSchedule.getDietChartId().setCreatedBy(patientDietSchedule.getScheduledby());
            patientDietSchedule.getDietChartId().setCreatedDate(new Date());
            patientDietSchedule.getDietChartId().setUpdtedDate(new Date());
            patientDietSchedule.getDietChartId().setIsDeleted(false);
            patientDietSchedule = patientDietScheduleRepository.save(patientDietSchedule);
        } else {
            patientDietSchedule.setIsDeleted(false);
            MstDietChart mstDietChart = mstDietChartRepository.getById(patientDietSchedule.getDietChartId().getId());
            patientDietSchedule.setDietChartId(mstDietChart);
            patientDietSchedule = patientDietScheduleRepository.save(patientDietSchedule);
        }
        respMap.put("status", "200");
        respMap.put("message", "success");
        respMap.put("dietChartId", patientDietSchedule.getDietChartId().getId());
        return respMap;
    }

    @RequestMapping("/getDietScheduleList/{id}")
    public List<PatientDietSchedule> diet_item_list(@RequestHeader("X-tenantId") String tenantName, @PathVariable Long id) {
        TenantContext.setCurrentTenant(tenantName);
//		List<PatientDietSchedule> list = patientDietScheduleRepository.findByIsDeletedFalseAndDietChartIdId(id);
        String Query = "Select pds from PatientDietSchedule pds where pds.dietChartId.id=" + id + " and isDeleted=false order by pds.dietSchedule.dsFromTime asc";
        return entityManager.createQuery(Query).getResultList();
//		HashMap<String,List<PatientDietSchedule>> listOfCategory = new HashMap();
//		List<PatientDietSchedule> list = patientDietScheduleRepository.findByIsDeletedFalseAndDietChartIdId(id);
//		for (int i = 0; i < list.size(); i++) {
//			if (itemList == null) {
//				itemList = new ArrayList<>();
//				itemList.add(list.get(i));
//				listOfCategory.put(list.get(i).getDietItemId().getDiDietPatientType().getDpcPatientType(), itemList);
//			} else {
//				itemList.add(list.get(i));
//				listOfCategory.put(list.get(i).getDietItemId().getDiDietPatientType().getDpcPatientType(), itemList);
//			}
//		}
//		return list;
    }

    @RequestMapping(value = "/updatedietschedule", method = RequestMethod.POST)
    public Map<String, String> updateDietSchedule(@RequestHeader("X-tenantId") String tenantName, @RequestBody PatientDietSchedule patientDietSchedule) {
        TenantContext.setCurrentTenant(tenantName);
        patientDietScheduleRepository.updatePatientDietSchedule(patientDietSchedule.getDietItemId(), patientDietSchedule.getDietSchedule().getDsId(), patientDietSchedule.getItemQuantity(), patientDietSchedule.getItemCalorie(), patientDietSchedule.getItemPrice(), patientDietSchedule.getId());
        respMap.put("status", "200");
        respMap.put("message", "success");
        return respMap;
    }

    @RequestMapping("delete/{deleteId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable Long deleteId) {
        TenantContext.setCurrentTenant(tenantName);
        PatientDietSchedule patientDietSchedule = patientDietScheduleRepository.getById(deleteId);
        if (patientDietSchedule != null) {
            patientDietSchedule.setIsDeleted(true);
            patientDietScheduleRepository.save(patientDietSchedule);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}