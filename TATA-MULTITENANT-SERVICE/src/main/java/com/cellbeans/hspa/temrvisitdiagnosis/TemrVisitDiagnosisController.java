package com.cellbeans.hspa.temrvisitdiagnosis;

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
@RequestMapping("/temr_visit_diagnosis")
public class TemrVisitDiagnosisController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TemrVisitDiagnosisRepository temrVisitDiagnosisRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrVisitDiagnosis temrVisitDiagnosis) {
        TenantContext.setCurrentTenant(tenantName);
        if (temrVisitDiagnosis.getVdStatus() != null) {
            temrVisitDiagnosisRepository.save(temrVisitDiagnosis);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("createNew")
    public Map<String, String> createNew(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TemrVisitDiagnosis> temrVisitDiagnosis) {
        TenantContext.setCurrentTenant(tenantName);
        temrVisitDiagnosisRepository.saveAll(temrVisitDiagnosis);
        respMap.put("success", "1");
        respMap.put("msg", "Added Successfully");
        return respMap;

    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<TemrVisitDiagnosis> records;
        records = temrVisitDiagnosisRepository.findByVdStatusContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{vdId}")
    public TemrVisitDiagnosis read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vdId") Long vdId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrVisitDiagnosis temrVisitDiagnosis = temrVisitDiagnosisRepository.getById(vdId);
        return temrVisitDiagnosis;
    }

    @RequestMapping("changestatusbyid/{vidId}/{booleanValue}")
    public Map<String, String> changeStatus(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vidId") Long vidId, @PathVariable("booleanValue") Boolean booleanValue) {
        TenantContext.setCurrentTenant(tenantName);
        TemrVisitDiagnosis temrVisitDiagnosis = temrVisitDiagnosisRepository.getById(vidId);
        temrVisitDiagnosis.setVdIsFinalDiagnosis(booleanValue);
        temrVisitDiagnosisRepository.save(temrVisitDiagnosis);
        if (booleanValue) {
            respMap.put("success", "1");
            respMap.put("msg", "Final");
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Provisional");
        }
        return respMap;
    }

    @RequestMapping("update")
    public TemrVisitDiagnosis update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrVisitDiagnosis temrVisitDiagnosis) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVisitDiagnosisRepository.save(temrVisitDiagnosis);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TemrVisitDiagnosis> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "vdId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return temrVisitDiagnosisRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return temrVisitDiagnosisRepository.findByVdStatusContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @GetMapping
    @RequestMapping("listbyid")
    public Iterable<TemrVisitDiagnosis> listbyId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "vdId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return temrVisitDiagnosisRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return temrVisitDiagnosisRepository.findByVdVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{vdId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vdId") Long vdId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrVisitDiagnosis temrVisitDiagnosis = temrVisitDiagnosisRepository.getById(vdId);
        if (temrVisitDiagnosis != null) {
            temrVisitDiagnosis.setIsDeleted(true);
            temrVisitDiagnosisRepository.save(temrVisitDiagnosis);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("getAllRecordServicebyTimelineId")
    public Iterable<TemrVisitDiagnosis> getAllRecordServicebyTimelineId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "vdId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return temrVisitDiagnosisRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return temrVisitDiagnosisRepository.findByVdTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @GetMapping
    @RequestMapping("getAllRecordServicebyTimelinePatientId")
    public Iterable<TemrVisitDiagnosis> getAllRecordServicebyTimelinePatientId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "vdId") String col) {
        TenantContext.setCurrentTenant(tenantName);
//        if (qString == null || qString.equals("")) {
//            return temrVisitDiagnosisRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//        } else {
            return temrVisitDiagnosisRepository.findByVdTimelineIdTimelinePatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString));
//        }
    }

//    @GetMapping
//    @RequestMapping("getAllRecordServicebyPatientId")
//    public Iterable<TemrVisitDiagnosis> getAllRecordServicebyPatientId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "vdId") String col) {
//        TenantContext.setCurrentTenant(tenantName);
////        if (qString == null || qString.equals("")) {
////            return temrVisitDiagnosisRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
////
////        } else {
//            return temrVisitDiagnosisRepository.findByVdTimelineIdTimelinePatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
////        }
//    }

    @RequestMapping("getAllRecordByTimelineList")
    public List<TemrVisitDiagnosis> getAllRecordByTimelineList(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TemrTimeline> temrTimeline) {
        TenantContext.setCurrentTenant(tenantName);
        List<TemrVisitDiagnosis> temrDignosisList = new ArrayList<TemrVisitDiagnosis>();
        List<TemrVisitDiagnosis> temrDignosis;
        for (int i = 0; i < temrTimeline.size(); i++) {
            temrDignosis = new ArrayList<TemrVisitDiagnosis>();
            temrDignosis = temrVisitDiagnosisRepository.findAllByVdTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(temrTimeline.get(i).getTimelineId());
            for (int j = 0; j < temrDignosis.size(); j++) {
                temrDignosisList.add(temrDignosis.get(j));
            }
        }
        return temrDignosisList;
    }

}
            
