package com.cellbeans.hspa.temrvisitchiefcomplaint;

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
@RequestMapping("/temr_visit_chief_complaint")
public class TemrVisitChiefComplaintController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TemrVisitChiefComplaintRepository temrVisitChiefComplaintRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrVisitChiefComplaint temrVisitChiefComplaint) {
        TenantContext.setCurrentTenant(tenantName);
        if (temrVisitChiefComplaint.getVccVisitId() != null) {
            temrVisitChiefComplaintRepository.deletechep(temrVisitChiefComplaint.getVccTimelineId().getTimelineId(), temrVisitChiefComplaint.getVccCcId().getCcId());
            TemrVisitChiefComplaint obj = temrVisitChiefComplaintRepository.save(temrVisitChiefComplaint);
            long newId = obj.getVccId();
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            respMap.put("newID", Long.toString(newId));
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("createNew")
    public Map<String, String> createNew(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TemrVisitChiefComplaint> temrVisitChiefComplaint) {
        TenantContext.setCurrentTenant(tenantName);
        temrVisitChiefComplaintRepository.saveAll(temrVisitChiefComplaint);
        respMap.put("success", "1");
        respMap.put("msg", "Added Successfully");
        return respMap;

    }
//        @RequestMapping("/autocomplete/{key}")
//	public  Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
//		Map<String, Object> automap  = new HashMap<String, Object>();
//		List<TemrVisitChiefComplaint> records;
//		records = temrVisitChiefComplaintRepository.findByVccNameContains(key);
//		automap.put("record", records);
//		return automap;
//	}

    @RequestMapping("byid/{vccId}")
    public TemrVisitChiefComplaint read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vccId") Long vccId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrVisitChiefComplaint temrVisitChiefComplaint = temrVisitChiefComplaintRepository.getById(vccId);
        return temrVisitChiefComplaint;
    }

    @RequestMapping("update")
    public TemrVisitChiefComplaint update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrVisitChiefComplaint temrVisitChiefComplaint) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVisitChiefComplaintRepository.save(temrVisitChiefComplaint);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TemrVisitChiefComplaint> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "vccId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return temrVisitChiefComplaintRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return temrVisitChiefComplaintRepository.findByVccCcIdCcNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @RequestMapping("listbyvisitid/{vcVisitId}")
    public List<TemrVisitChiefComplaint> listbyVisitId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vcVisitId") Long vcVisitId) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVisitChiefComplaintRepository.findAllByVccVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(vcVisitId);
    }

    @PutMapping("delete/{vccId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vccId") Long vccId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrVisitChiefComplaint temrVisitChiefComplaint = temrVisitChiefComplaintRepository.getById(vccId);
        if (temrVisitChiefComplaint != null) {
            temrVisitChiefComplaint.setIsDeleted(true);
            temrVisitChiefComplaint.setIsActive(false);
            temrVisitChiefComplaintRepository.save(temrVisitChiefComplaint);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("listbytimelineid/{id}")
    public List<TemrVisitChiefComplaint> listbytimelineid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVisitChiefComplaintRepository.findAllByVccTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(id);
    }

    @RequestMapping("listbytimelinepatientid/{id}")
    public List<TemrVisitChiefComplaint> listbytimelinepatientid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVisitChiefComplaintRepository.findAllByVccTimelineIdTimelinePatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(id);
    }

    @GetMapping
    @RequestMapping("getcomplaintdetailsByPatientId/{patientId}/{startdate}/{enddate}/{page}/{size}")
    public Iterable<TemrVisitChiefComplaint> getComplaintdetailsByPatientId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") String patientId, @PathVariable("startdate") String startdate, @PathVariable("enddate") String enddate, @PathVariable("page") String page, @PathVariable("size") String size) {
        TenantContext.setCurrentTenant(tenantName);
        String sort = "DESC";
        String col = "vcc_id";
        if (enddate.equals(startdate) || startdate.equals(enddate)) {
            return temrVisitChiefComplaintRepository.findAllByVccTimelineIdTimelinePatientIdPatientIdAndCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(patientId), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return temrVisitChiefComplaintRepository.findAllByVccTimelineIdTimelinePatientIdPatientIdAndCreatedDateBetweenAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(patientId), startdate, enddate, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @RequestMapping("getAllRecordByTimelineList")
    public List<TemrVisitChiefComplaint> getAllRecordByTimelineList(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TemrTimeline> temrTimeline) {
        TenantContext.setCurrentTenant(tenantName);
        List<TemrVisitChiefComplaint> chiefComplaintsList = new ArrayList<TemrVisitChiefComplaint>();
        List<TemrVisitChiefComplaint> chiefComplaints;
        for (int i = 0; i < temrTimeline.size(); i++) {
            chiefComplaints = new ArrayList<TemrVisitChiefComplaint>();
            chiefComplaints = temrVisitChiefComplaintRepository.findAllByVccTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(temrTimeline.get(i).getTimelineId());
            for (int j = 0; j < chiefComplaints.size(); j++) {
                chiefComplaintsList.add(chiefComplaints.get(j));
            }
        }
        return chiefComplaintsList;
    }

}

