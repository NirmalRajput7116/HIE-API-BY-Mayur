package com.cellbeans.hspa.temrvisitinvestigation;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mbillservice.MbillService;
import com.cellbeans.hspa.mstvisit.MstVisitRepository;
import com.cellbeans.hspa.temrtimeline.TemrTimeline;
import com.cellbeans.hspa.trnadmission.TrnAdmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/temr_visit_investigation")
public class TemrVisitInvestigationController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TemrVisitInvestigationRespository temrVisitInvestigationRespository;

    @Autowired
    MstVisitRepository mstVisitRepository;

    @Autowired
    TrnAdmissionRepository mstAdmissionRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TemrVisitInvestigation> temrVisitInvestigation) {
        TenantContext.setCurrentTenant(tenantName);
        if (temrVisitInvestigation.get(0).getViVisitId() != null) {
//            if(temrVisitInvestigation.getViRegistrationSource() == 1 && temrVisitInvestigation.getViServiceId().getServiceIsLaboratotry() == true)
//            {
//                temrVisitInvestigation.setIsServiceBilled(1);
//            }
            temrVisitInvestigationRespository.saveAll(temrVisitInvestigation);
            respMap.put("success", "1");
            respMap.put("msg", "Service Added in list");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed to Add Service to list");
            return respMap;
        }
    }

    @RequestMapping("byid/{viVisitId}")
    public TemrVisitInvestigation read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("viVisitId") Long viVisitId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrVisitInvestigation temrVisitInvestigation = temrVisitInvestigationRespository.getById(viVisitId);
        return temrVisitInvestigation;
    }

    @RequestMapping("update")
    public TemrVisitInvestigation update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrVisitInvestigation temrVisitInvestigation) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVisitInvestigationRespository.save(temrVisitInvestigation);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TemrVisitInvestigation> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "viId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return temrVisitInvestigationRespository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return temrVisitInvestigationRespository.findByViVisitIdVisitIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @GetMapping
    @RequestMapping("docorderlist")
    public Iterable<TemrVisitInvestigation> docorderlist(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "viVisitId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return temrVisitInvestigationRespository.findAllByIsServiceBilledAndIsActiveTrueAndIsDeletedFalse(0, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return temrVisitInvestigationRespository.findByViVisitIdVisitIdEqualsAndIsServiceBilledAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString), 0, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    // Author: Mohit Pagination for IPD/ OPD
    @GetMapping
    @RequestMapping("listForOpd")
    public Iterable<TemrVisitInvestigation> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "patientRegSrc", required = false) Integer patientRegSrc, @RequestParam(value = "viVisitId", required = false) Long viVisitId, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "viVisitId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (patientRegSrc == null || patientRegSrc.equals("")) {
            return temrVisitInvestigationRespository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return temrVisitInvestigationRespository.findAllByViVisitIdVisitIdAndViRegistrationSourceEqualsAndIsActiveTrueAndIsDeletedFalse(viVisitId, patientRegSrc, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @GetMapping("listbyadmission")
    public List<MbillService> listByAdmission(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "patientId") Long patientId, @RequestParam(value = "admissionDate") String admissionDate, @RequestParam(value = "patientRegSrc") Integer patientRegSrc) {
        TenantContext.setCurrentTenant(tenantName);
        //1. find visits from admission date
        //2. find all visitinvestingation by visits of admission
        /*Date date = null;
        try
        {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(admissionDate);
        }
        catch (Exception e)
        {
            System.out.print(e.getMessage());
            date = new Date();
        }*/
        return temrVisitInvestigationRespository.findDoctorOrderForIPD(patientId, patientRegSrc, admissionDate, mstVisitRepository);
    }

    @GetMapping("listbyadmissionIPDunbilled")
    public List<TemrVisitInvestigation> listByAdmissionIPDunBilled(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "patientId") Long patientId, @RequestParam(value = "admissionDate") String admissionDate, @RequestParam(value = "patientRegSrc") Integer patientRegSrc) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVisitInvestigationRespository.findunbilledDoctorOrderForIPD(patientId, patientRegSrc, admissionDate, mstAdmissionRepository);
    }

    @PostMapping("updatedoctororder")
    public Map<String, String> updateVisitInvestigationForBill(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TemrVisitInvestigation> temrVisitInvestigations) {
        TenantContext.setCurrentTenant(tenantName);
        Boolean flg = temrVisitInvestigationRespository.updateDoctorOrderForBill(temrVisitInvestigations);
        if (flg) {
            respMap.put("success", "1");
            respMap.put("msg", "Doctor Order Updated in list");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed to Update Doctor Order to list");
            return respMap;
        }
    }

    @PutMapping("delete/{viVisitId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("viVisitId") Long viVisitId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrVisitInvestigation temrVisitInvestigation = temrVisitInvestigationRespository.getById(viVisitId);
        if (temrVisitInvestigation != null) {
            temrVisitInvestigation.setDeleted(true);
            temrVisitInvestigation.setActive(false);
            temrVisitInvestigationRespository.save(temrVisitInvestigation);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("listbytimelienid")
    public Iterable<TemrVisitInvestigation> listbytimelienid(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "viVisitId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return temrVisitInvestigationRespository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return temrVisitInvestigationRespository.findByViTimelineIdTimelineIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @GetMapping
    @RequestMapping("listbypatientid")
    public Iterable<TemrVisitInvestigation> listbypatientid(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "viVisitId") String col) {
        TenantContext.setCurrentTenant(tenantName);
//        if (qString == null || qString.equals("")) {
//            return temrVisitInvestigationRespository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//        } else {
            return temrVisitInvestigationRespository.findByViTimelineIdTimelinePatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString));
//        }
    }

    @RequestMapping("getAllRecordByTimelineList")
    public List<TemrVisitInvestigation> getAllRecordByTimelineList(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TemrTimeline> temrTimeline) {
        TenantContext.setCurrentTenant(tenantName);
        List<TemrVisitInvestigation> servicesList = new ArrayList<TemrVisitInvestigation>();
        List<TemrVisitInvestigation> services;
        for (int i = 0; i < temrTimeline.size(); i++) {
            services = new ArrayList<TemrVisitInvestigation>();
            services = temrVisitInvestigationRespository.findAllByViTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(temrTimeline.get(i).getTimelineId());
            for (int j = 0; j < services.size(); j++) {
                servicesList.add(services.get(j));
            }
        }
        return servicesList;
    }

    @RequestMapping("addservice")
    public Map<String, String> addservice(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrVisitInvestigation temrVisitInvestigation) {
        TenantContext.setCurrentTenant(tenantName);
        if (temrVisitInvestigation.getViRegistrationSource() != 0) {
            temrVisitInvestigation.setIsServiceBilled(0);
            temrVisitInvestigationRespository.save(temrVisitInvestigation);
            respMap.put("success", "1");
            respMap.put("msg", "Service Added in list");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed to Add Service to list");
            return respMap;
        }
    }

    @RequestMapping("create_single_service")
    public Map<String, String> create_single_service(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrVisitInvestigation temrVisitInvestigation) {
        TenantContext.setCurrentTenant(tenantName);
        if (temrVisitInvestigation.getViVisitId() != null) {
            if (temrVisitInvestigation.getViRegistrationSource() == 1 && temrVisitInvestigation.getViServiceId().getServiceIsLaboratory() == true) {
                temrVisitInvestigation.setIsServiceBilled(1);
            }
            temrVisitInvestigationRespository.save(temrVisitInvestigation);
            respMap.put("success", "1");
            respMap.put("msg", "Service Added in list");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed to Add Service to list");
            return respMap;
        }
    }

    @RequestMapping("getRecordIPDForLabOrRadiologyListBytmeLineId/{timelineId}")
    public List<TemrVisitInvestigation> getRecordIPDForLabOrRadiologyListBytmeLineId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("timelineId") Long timelineId) {
        TenantContext.setCurrentTenant(tenantName);
        List<TemrVisitInvestigation> temrVisitInvestigation = temrVisitInvestigationRespository.findAllByViServiceIdServiceIsLaboratoryAndViTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(true, timelineId);
        return temrVisitInvestigation;
    }

    @GetMapping
    @RequestMapping("alllistbytimelienid")
    public List<TemrVisitInvestigation> alllistbytimelienid(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qString", required = false) String qString) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return temrVisitInvestigationRespository.findAllByIsActiveTrueAndIsDeletedFalse();

        } else {
            return temrVisitInvestigationRespository.findByViTimelineIdTimelineIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString));
        }
    }

    @GetMapping
    @RequestMapping("ListRecordByStartEndDate")
    public Iterable<TemrVisitInvestigation> listrecordbystartenddate(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                                     @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                                                     @RequestParam(value = "startdate", required = false) String startdate,
                                                                     @RequestParam(value = "enddate", required = false) String enddate,
                                                                     @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                                     @RequestParam(value = "col", required = false, defaultValue = "viId") String col,
                                                                     @RequestParam(value = "unitid", required = false, defaultValue = "0") Long unitid,
                                                                     @RequestParam(value = "qString", required = false, defaultValue = "null") String qString) throws ParseException {
        TenantContext.setCurrentTenant(tenantName);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date StartDate = df.parse(startdate);
        Date EndDate = df.parse(enddate);
        if (qString == null || qString.equals("")) {
            return temrVisitInvestigationRespository.findAllByViCreatedDateBetweenAndViTimelineIdTimelineServiceIdBsBillIdTbillUnitIdUnitIdOrViTimelineIdTimelineAdmissionIdAdmissionUnitIdUnitIdAndViServiceIdServiceIsRadiology(StartDate, EndDate, unitid, unitid, true, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return temrVisitInvestigationRespository.findAllByViCreatedDateBetweenAndViTimelineIdTimelineServiceIdBsBillIdTbillUnitIdUnitIdOrViTimelineIdTimelineAdmissionIdAdmissionUnitIdUnitIdAndViServiceIdServiceIsRadiologyAndViServiceIdServiceNameContains(StartDate, EndDate, unitid, unitid, true, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @GetMapping
    @RequestMapping("listbyEMRtimelienid/{timelineId}")
    public List<TemrVisitInvestigation> listbyEMRtimelienid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("timelineId") Long timelineId) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVisitInvestigationRespository.findByViTimelineIdTimelineIdEqualsAndIsServiceBilledEqualsAndIsActiveTrueAndIsDeletedFalse(timelineId, 0);
    }
}




