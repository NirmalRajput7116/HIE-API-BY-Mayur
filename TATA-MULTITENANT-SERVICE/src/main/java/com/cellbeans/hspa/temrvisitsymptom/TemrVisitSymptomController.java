package com.cellbeans.hspa.temrvisitsymptom;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.temrtimeline.TemrTimeline;
import com.cellbeans.hspa.temrtimeline.TemrTimelineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/temr_visit_symptom")
public class TemrVisitSymptomController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TemrVisitSymptomRepository temrVisitSymptomRepository;

    @Autowired
    TemrTimelineRepository temrTimelineRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrVisitSymptom temrVisitSymptom) {
        TenantContext.setCurrentTenant(tenantName);
        if (temrVisitSymptom != null) {
            if (temrVisitSymptom.getVsSymptomId() != null) {
                temrVisitSymptomRepository.deletesymtorm(temrVisitSymptom.getVsTimelineId().getTimelineId(), temrVisitSymptom.getVsSymptomId().getSymptomId());
            } else {
                temrVisitSymptomRepository.deletesymtormByName(temrVisitSymptom.getVsTimelineId().getTimelineId(), temrVisitSymptom.getVsSignSymptoms());
            }
            TemrVisitSymptom newCreated = temrVisitSymptomRepository.save(temrVisitSymptom);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            respMap.put("newID", Long.toString(newCreated.getVsId()));
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
//		List<TemrVisitSymptom> records;
//		records = temrVisitSymptomRepository.findBySymptomNameContains(key);
//		automap.put("record", records);
//		return automap;
//	}

    @RequestMapping("byid/{vsId}")
    public TemrVisitSymptom read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vsId") Long vsId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrVisitSymptom temrVisitSymptom = temrVisitSymptomRepository.getById(vsId);
        return temrVisitSymptom;
    }

    @RequestMapping("update")
    public TemrVisitSymptom update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrVisitSymptom temrVisitSymptom) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVisitSymptomRepository.save(temrVisitSymptom);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TemrVisitSymptom> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "vsId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return temrVisitSymptomRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return temrVisitSymptomRepository.findByVsSymptomIdSymptomNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @RequestMapping("listbyvisitid/{dcVisitId}")
    public List<TemrVisitSymptom> listbyVisitId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dcVisitId") Long dcVisitId) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVisitSymptomRepository.findAllByVsVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(dcVisitId);
    }

    @PutMapping("delete/{vsId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vsId") Long vsId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrVisitSymptom temrVisitSymptom = temrVisitSymptomRepository.getById(vsId);
        if (temrVisitSymptom != null) {
            temrVisitSymptom.setIsDeleted(true);
            temrVisitSymptomRepository.save(temrVisitSymptom);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("listbytimelineid/{id}")
    public List<TemrVisitSymptom> listbytimelineid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVisitSymptomRepository.findAllByVsTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(id);
    }

    @RequestMapping("listbypatientid/{id}")
    public List<TemrVisitSymptom> listbypatientid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVisitSymptomRepository.findAllByVsTimelineIdTimelinePatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(id);
    }

    @RequestMapping("getAllRecordByTimelineList")
    public List<TemrVisitSymptom> getAllRecordByTimelineList(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TemrTimeline> temrTimeline) {
        TenantContext.setCurrentTenant(tenantName);
        List<TemrVisitSymptom> sysmtomsList = new ArrayList<TemrVisitSymptom>();
        List<TemrVisitSymptom> sysmtoms;
        for (int i = 0; i < temrTimeline.size(); i++) {
            sysmtoms = new ArrayList<TemrVisitSymptom>();
            sysmtoms = temrVisitSymptomRepository.findAllByVsTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(temrTimeline.get(i).getTimelineId());
            for (int j = 0; j < sysmtoms.size(); j++) {
                sysmtomsList.add(sysmtoms.get(j));
            }
        }
        return sysmtomsList;
    }

    @RequestMapping("createList")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TemrVisitSymptom> temrVisitSymptom) {
       /* for (int i = 0; i < temrVisitSymptom.size(); i++) {
            if( temrVisitSymptom.get(i).getVsSymptomId() != null) {
                temrVisitSymptomRepository.deletesymtorm(temrVisitSymptom.get(i).getVsTimelineId().getTimelineId(),
                        temrVisitSymptom.get(i).getVsSymptomId().getSymptomId());
            }
            temrVisitSymptom.get(i).getVsTimelineId().setTimelineIsSymptoms(true);
        }*/
        TenantContext.setCurrentTenant(tenantName);
        temrVisitSymptomRepository.saveAll(temrVisitSymptom);
        if (temrVisitSymptom != null && temrVisitSymptom.size() != 0) {
            temrTimelineRepository.updateTimelineIsSymptoms(temrVisitSymptom.get(0).getVsTimelineId().getTimelineId(),
                    true);
        }
        respMap.put("success", "1");
        respMap.put("msg", "Added Successfully");
        respMap.put("newID", "");
        return respMap;
    }

}
            
