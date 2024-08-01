package com.cellbeans.hspa.temrdoctorsadvice;

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
@RequestMapping("/temr_doctors_advice")
public class TemrDoctorsAdviceController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TemrDoctorsAdviceRepository temrDoctorsAdviceRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TemrDoctorsAdvice> temrDoctorsAdvice) {
        TenantContext.setCurrentTenant(tenantName);
        if (temrDoctorsAdvice.size() != 0) {
            temrDoctorsAdviceRepository.saveAll(temrDoctorsAdvice);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    /*       @RequestMapping("/autocomplete/{key}")
       public  Map<S tring, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
           Map<String, Object> automap  = new HashMap<String, Object>();
           List<TemrDoctorsAdvice> records;
           records = temrDoctorsAdviceRepository.findByDcContentContains(key);
           automap.put("record", records);
           return automap;
       }
   */
    @RequestMapping("byid/{dcId}")
    public TemrDoctorsAdvice read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dcId") Long dcId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrDoctorsAdvice temrDoctorsAdvice = temrDoctorsAdviceRepository.getById(dcId);
        return temrDoctorsAdvice;
    }

    @RequestMapping("update")
    public TemrDoctorsAdvice update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrDoctorsAdvice temrDoctorsAdvice) {
        TenantContext.setCurrentTenant(tenantName);
        return temrDoctorsAdviceRepository.save(temrDoctorsAdvice);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TemrDoctorsAdvice> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "dcId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return temrDoctorsAdviceRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return temrDoctorsAdviceRepository.findByDcContentContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @GetMapping
    @RequestMapping("listByVisitId")
    public Iterable<TemrDoctorsAdvice> listByVisitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "dcVisitId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return temrDoctorsAdviceRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return temrDoctorsAdviceRepository.findByDcVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{dcId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dcId") Long dcId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrDoctorsAdvice temrDoctorsAdvice = temrDoctorsAdviceRepository.getById(dcId);
        if (temrDoctorsAdvice != null) {
            temrDoctorsAdvice.setDeleted(true);
            temrDoctorsAdviceRepository.save(temrDoctorsAdvice);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("listByTimelineId")
    public Iterable<TemrDoctorsAdvice> listByTimelineId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "dcVisitId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return temrDoctorsAdviceRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return temrDoctorsAdviceRepository.findByDcTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @GetMapping
    @RequestMapping("listByPatientId")
    public Iterable<TemrDoctorsAdvice> listByPatientId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "dcVisitId") String col) {
        TenantContext.setCurrentTenant(tenantName);
//        if (qString == null || qString.equals("")) {
//            return temrDoctorsAdviceRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//
//        } else {
            return temrDoctorsAdviceRepository.findByDcTimelineIdTimelinePatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(String.valueOf(qString)));

//        }

    }

    @RequestMapping("getAllRecordByTimelineList")
    public List<TemrDoctorsAdvice> getAllRecordByTimelineList(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TemrTimeline> temrTimeline) {
        TenantContext.setCurrentTenant(tenantName);
        List<TemrDoctorsAdvice> clinicalProcedureList = new ArrayList<TemrDoctorsAdvice>();
        List<TemrDoctorsAdvice> clinicalProcedure;
        for (int i = 0; i < temrTimeline.size(); i++) {
            clinicalProcedure = new ArrayList<TemrDoctorsAdvice>();
            clinicalProcedure = temrDoctorsAdviceRepository.findAllByDcTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(temrTimeline.get(i).getTimelineId());
            for (int j = 0; j < clinicalProcedure.size(); j++) {
                clinicalProcedureList.add(clinicalProcedure.get(j));
            }
        }
        return clinicalProcedureList;
    }

    //   abhijeet for OT Doctor Order
    @RequestMapping("bypatientid/{patientId}")
    public List<TemrDoctorsAdvice> bypatientid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId) {
        TenantContext.setCurrentTenant(tenantName);
        return temrDoctorsAdviceRepository.findAllByDcVisitIdVisitPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(patientId);
    }

}
            
