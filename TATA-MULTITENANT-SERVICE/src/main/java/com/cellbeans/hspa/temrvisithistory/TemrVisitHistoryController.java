package com.cellbeans.hspa.temrvisithistory;

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
@RequestMapping("/temr_visit_history")
public class TemrVisitHistoryController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TemrVisitHistoryRepository temrVisitHistoryRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrVisitHistory temrVisitHistory) {
        TenantContext.setCurrentTenant(tenantName);
        if (temrVisitHistory.getVhVisitId() != null && temrVisitHistory.getVhContent() != null) {
            temrVisitHistoryRepository.save(temrVisitHistory);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("createForEMRHistory")
    public Map<String, String> createForEMRHistory(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrVisitHistory temrVisitHistory) {
        TenantContext.setCurrentTenant(tenantName);
        if (temrVisitHistory.getVhVisitId() != null) {
            temrVisitHistoryRepository.save(temrVisitHistory);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<TemrVisitHistory> records;
        records = temrVisitHistoryRepository.findByVhHtIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{vhId}")
    public TemrVisitHistory read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vhId") Long vhId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrVisitHistory temrVisitHistory = temrVisitHistoryRepository.getById(vhId);
        return temrVisitHistory;
    }

    // get history based on VisitId
    @RequestMapping("byidVisitId/{vhVisitId}")
    public TemrVisitHistory readbyVisitId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vhVisitId") Long vhVisitId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrVisitHistory temrVisitHistory = temrVisitHistoryRepository.findByVhVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(vhVisitId);
        return temrVisitHistory;
    }

    @RequestMapping("update")
    public TemrVisitHistory update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrVisitHistory temrVisitHistory) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVisitHistoryRepository.save(temrVisitHistory);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TemrVisitHistory> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "vhId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return temrVisitHistoryRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return temrVisitHistoryRepository.findByVhHtIdContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{vhId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vhId") Long vhId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrVisitHistory temrVisitHistory = temrVisitHistoryRepository.getById(vhId);
        if (temrVisitHistory != null) {
            temrVisitHistory.setIsDeleted(true);
            temrVisitHistoryRepository.save(temrVisitHistory);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    // get history based on VisitId
    @RequestMapping("byidtimelineId/{id}")
    public TemrVisitHistory byidtimelineId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        TemrVisitHistory temrVisitHistory = temrVisitHistoryRepository.findFirstByVhTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalseOrderByVhIdDesc(id);
        return temrVisitHistory;
    }

    @GetMapping
    @RequestMapping("getHistorydetailsByPatientId/{patientId}/{startdate}/{enddate}/{page}/{size}")
    public Iterable<TemrVisitHistory> getHistorydetailsByPatientId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") String patientId, @PathVariable("startdate") String startdate, @PathVariable("enddate") String enddate, @PathVariable("page") String page, @PathVariable("size") String size) {
        TenantContext.setCurrentTenant(tenantName);
        String sort = "DESC";
        String col = "vh_id";
        if (enddate.equals(startdate) || startdate.equals(enddate)) {
            return temrVisitHistoryRepository.findAllByvhTimelineIdTimelinePatientIdPatientIdAndCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(patientId), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return temrVisitHistoryRepository.findAllByvhTimelineIdTimelinePatientIdPatientIdAndCreatedDateBetweenAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(patientId), startdate, enddate, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @RequestMapping("getAllRecordByTimelineList")
    public List<TemrVisitHistory> getAllRecordByTimelineList(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TemrTimeline> temrTimeline) {
        TenantContext.setCurrentTenant(tenantName);
        List<TemrVisitHistory> historyList = new ArrayList<TemrVisitHistory>();
        List<TemrVisitHistory> history;
        for (int i = 0; i < temrTimeline.size(); i++) {
            history = new ArrayList<TemrVisitHistory>();
            history = temrVisitHistoryRepository.findAllByVhTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(temrTimeline.get(i).getTimelineId());
            for (int j = 0; j < history.size(); j++) {
                historyList.add(history.get(j));
            }
        }
        return historyList;
    }

    // get history based on VisitId
    @RequestMapping("by_patient_id/{id}")
    public Map<String, Object> by_patient_id(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> recordMap = new HashMap<String, Object>();
        TemrVisitHistory record = null;
        record = temrVisitHistoryRepository.findByPatientId(id);
        recordMap.put("record", record);
        return recordMap;
    }

    @RequestMapping("allByPatientId/{id}")
    public List<TemrVisitHistory> allByPatientId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> recordMap = new HashMap<String, Object>();
        List<TemrVisitHistory> record = null;
        return temrVisitHistoryRepository.findAllByPatientId(id);
//        recordMap.put("record", record);
//        return recordMap;
    }

//    @RequestMapping("ehr_by_patient_id/{id}")
//    public Map<String, Object> ehr_by_patient_id(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
//        TenantContext.setCurrentTenant(tenantName);
//        Map<String, Object> recordMap = new HashMap<String, Object>();
//        TemrVisitHistory record = null;
//        record = temrVisitHistoryRepository.findByPatientId(id);
//        recordMap.put("record", record);
//        return recordMap;
//    }


}

