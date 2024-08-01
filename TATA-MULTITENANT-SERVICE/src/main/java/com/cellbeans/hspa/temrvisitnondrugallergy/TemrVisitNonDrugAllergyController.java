package com.cellbeans.hspa.temrvisitnondrugallergy;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.temrtimeline.TemrTimeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/temr_visit_non_drug_allergy")
public class TemrVisitNonDrugAllergyController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TemrVisitNonDrugAllergyRepository temrVisitNonDrugAllergyRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrVisitNonDrugAllergy temrVisitNonDrugAllergy) {
        TenantContext.setCurrentTenant(tenantName);
        if (temrVisitNonDrugAllergy.getVndaNdaId() != null) {
            temrVisitNonDrugAllergyRepository.deletend(temrVisitNonDrugAllergy.getVnTimelineId().getTimelineId(), temrVisitNonDrugAllergy.getVndaNdaId().getNdaId());
            TemrVisitNonDrugAllergy obj = temrVisitNonDrugAllergyRepository.save(temrVisitNonDrugAllergy);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            respMap.put("newID", Long.toString(obj.getVndaId()));
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }
//        @RequestMapping("/autocomplete/{key}")
//	public  Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
//		Map<String, Object> automap  = new HashMap<String, Object>();
//		List<TemrVisitNonDrugAllergy> records;
//		records = temrVisitNonDrugAllergyRepository.findByVndaNameContains(key);
//		automap.put("record", records);
//		return automap;
//	}

    @RequestMapping("byid/{vndaId}")
    public TemrVisitNonDrugAllergy read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vndaId") Long vndaId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrVisitNonDrugAllergy temrVisitNonDrugAllergy = temrVisitNonDrugAllergyRepository.getById(vndaId);
        return temrVisitNonDrugAllergy;
    }

    @RequestMapping("update")
    public TemrVisitNonDrugAllergy update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrVisitNonDrugAllergy temrVisitNonDrugAllergy) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVisitNonDrugAllergyRepository.save(temrVisitNonDrugAllergy);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TemrVisitNonDrugAllergy> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "vndaId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return temrVisitNonDrugAllergyRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return temrVisitNonDrugAllergyRepository.findByVndaNdaIdNdaNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{vndaId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vndaId") Long vndaId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrVisitNonDrugAllergy temrVisitNonDrugAllergy = temrVisitNonDrugAllergyRepository.getById(vndaId);
        if (temrVisitNonDrugAllergy != null) {
            temrVisitNonDrugAllergy.setIsDeleted(true);
            temrVisitNonDrugAllergyRepository.save(temrVisitNonDrugAllergy);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("listbyvisitid/{vndaVisitId}")
    public List<TemrVisitNonDrugAllergy> listbyVisitId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vndaVisitId") Long vndaVisitId) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVisitNonDrugAllergyRepository.findAllByVndaVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(vndaVisitId);
    }

    @RequestMapping("listbytimelineid/{id}")
    public List<TemrVisitNonDrugAllergy> listbytimelineid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVisitNonDrugAllergyRepository.findAllByVnTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(id);
    }

    @RequestMapping("listbypatientid/{id}")
    public List<TemrVisitNonDrugAllergy> listbypatientid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVisitNonDrugAllergyRepository.findAllByVnTimelineIdTimelinePatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(id);
    }

    @RequestMapping("getAllRecordByTimelineList")
    public List<TemrVisitNonDrugAllergy> getAllRecordByTimelineList(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TemrTimeline> temrTimeline) {
        TenantContext.setCurrentTenant(tenantName);
        List<TemrVisitNonDrugAllergy> nonDrugAllergyList = new ArrayList<TemrVisitNonDrugAllergy>();
        List<TemrVisitNonDrugAllergy> nonDrugAllergy;
        for (int i = 0; i < temrTimeline.size(); i++) {
            nonDrugAllergy = new ArrayList<TemrVisitNonDrugAllergy>();
            nonDrugAllergy = temrVisitNonDrugAllergyRepository.findAllByVnTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(temrTimeline.get(i).getTimelineId());
            for (int j = 0; j < nonDrugAllergy.size(); j++) {
                nonDrugAllergyList.add(nonDrugAllergy.get(j));
            }
        }
        return nonDrugAllergyList;
    }

    @RequestMapping("containdicatedlistbytimelineid/{id}")
    public List<TemrVisitNonDrugAllergy> containdicatedlistbytimelineid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVisitNonDrugAllergyRepository.findAllByVnTimelineIdTimelinePatientIdPatientIdEqualsAndVndaAsIdAsIdEqualsAndIsDeletedFalse(id);
    }
}
            
