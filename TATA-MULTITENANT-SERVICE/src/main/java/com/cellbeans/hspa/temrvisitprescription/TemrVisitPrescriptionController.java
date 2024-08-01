package com.cellbeans.hspa.temrvisitprescription;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.temritemprescription.TemrItemPrescription;
import com.cellbeans.hspa.temritemprescription.TemrItemPrescriptionRepository;
import com.cellbeans.hspa.temrtimeline.TemrTimeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/temr_visit_prescription")
public class TemrVisitPrescriptionController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TemrVisitPrescriptionRepository temrVisitPrescriptionRepository;

    @Autowired
    TemrItemPrescriptionRepository temrItemPrescriptionRepository;

    @PersistenceContext
    EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrVisitPrescription temrVisitPrescription) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("===========prescription staff id=============================");
        System.out.println(temrVisitPrescription.getVpPrescribedStaffId().getStaffId());
        temrVisitPrescription.setOrdertoEPharmacy(false);
        temrVisitPrescriptionRepository.save(temrVisitPrescription);
        respMap.put("vpId", String.valueOf(temrVisitPrescription.getVpId()));
        respMap.put("success", "1");
        respMap.put("msg", "Added Successfully");
        return respMap;
    }

    @RequestMapping("createlist")
    public Map<String, String> createlist(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TemrVisitPrescription> temrVisitPrescriptionList) {
        TenantContext.setCurrentTenant(tenantName);
        temrVisitPrescriptionRepository.saveAll(temrVisitPrescriptionList);
        respMap.put("success", "1");
        respMap.put("msg", "Added Successfully");
        return respMap;
    }
//
//    @RequestMapping("/autocomplete/{key}")
//    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
//        Map<String, Object> automap = new HashMap<String, Object>();
//        List<TemrItemPrescription> records;
//        records = temrVisitPrescriptionRepository.findByPrescriptionNameContains(key);
//        automap.put("record", records);
//        return automap;
//    }

    @RequestMapping("byid/{id}")
    public TemrVisitPrescription read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        TemrVisitPrescription temrVisitPrescription = temrVisitPrescriptionRepository.getById(id);
        return temrVisitPrescription;
    }

    @GetMapping
    @RequestMapping("byvisitid/{visitId}")
    public List<TemrItemPrescription> byvisitid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("visitId") Long visitId) {
        TenantContext.setCurrentTenant(tenantName);
        List<TemrVisitPrescription> obj;
        obj = temrVisitPrescriptionRepository.findByVpVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(visitId);
        //return temrVisitPrescriptionRepository.findByVpVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(visitId);
        if (obj != null && obj.size() != 0) {
            Long vsid = obj.get(obj.size() - 1).getVpId();
            return temrItemPrescriptionRepository.findByIpVpIdVpIdAndIsActiveTrueAndIsDeletedFalse(vsid);
        } else {
            return new ArrayList<TemrItemPrescription>();
        }
    }

    @GetMapping
    @RequestMapping("byvisitidByDto/{vpId}")
    public List<TemrVistPrescriptionDTOForItems> byvisitidByDto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vpId") Long vpId) {
        TenantContext.setCurrentTenant(tenantName);
        List<TemrVisitPrescription> obj;
//        obj =temrVisitPrescriptionRepository.findByVpVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(visitId);
//        if(obj != null && obj.size() != 0 ){
//            Long vsid = obj.get(obj.size()-1).getVpId();
        return temrItemPrescriptionRepository.findByVpPatientByvsId(vpId);
//        }
//        else{
//            return new ArrayList<TemrVistPrescriptionDTOForItems>();
//        }
    }

    @RequestMapping("update")
    public TemrVisitPrescription update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrVisitPrescription temrVisitPrescription) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVisitPrescriptionRepository.save(temrVisitPrescription);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TemrVisitPrescription> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "vpId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVisitPrescriptionRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

    }

    @GetMapping
    @RequestMapping("listByVisitIdPage")
    public Iterable<TemrItemPrescription> listByVisitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) Long visitId, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ipId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        List<TemrVisitPrescription> obj;
        obj = temrVisitPrescriptionRepository.findByVpVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(visitId);
        //return temrVisitPrescriptionRepository.findByVpVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(visitId);
        if (obj != null) {
            Long vpId = obj.get(obj.size() - 1).getVpId();
            return temrItemPrescriptionRepository.findByIpVpIdVpIdAndIsActiveTrueAndIsDeletedFalse(vpId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return new ArrayList<TemrItemPrescription>();
        }
    }
//    @GetMapping
//    @RequestMapping("listByVisitIdPage")
//    public Iterable<TemrItemPrescription> listByVisitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
//                                                         @RequestParam(value = "size", required = false, defaultValue = "100") String size,
//                                                         @RequestParam(value = "qString", required = false) Long visitId,
//                                                         @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
//                                                         @RequestParam(value = "col", required = false, defaultValue = "ipId") String col) {
//
//        TemrVisitPrescription obj = new TemrVisitPrescription();
//        obj =temrVisitPrescriptionRepository.findByVpVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(visitId);
//        //return temrVisitPrescriptionRepository.findByVpVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(visitId);
//        if(obj != null){
//            Long vpId = obj.getVpId();
//            return temrItemPrescriptionRepository.findByIpVpIdVpIdAndIsActiveTrueAndIsDeletedFalse(vpId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//        }
//        else{
//            return new ArrayList<TemrItemPrescription>();
//        }
//    }

    @PutMapping("delete/{id}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id) {
        TenantContext.setCurrentTenant(tenantName);
        TemrVisitPrescription temrVisitPrescription = temrVisitPrescriptionRepository.getById(id);
        if (temrVisitPrescription != null) {
            temrVisitPrescription.setDeleted(true);
            temrVisitPrescriptionRepository.save(temrVisitPrescription);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @PostMapping
    @RequestMapping("bymrno")
    public Iterable<TemrVisitPrescription> bymrno(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrVisitPrescription temrVisitPrescription) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVisitPrescriptionRepository.findAllByVpPatientMrNumberAndIsActiveTrueAndIsDeletedFalse(temrVisitPrescription.getVpPatientMrNumber());
    }
//    @PostMapping
//    @RequestMapping("bymrnoforClosePre")
//    public Iterable<TemrVisitPrescription> bymrnoforClosePre(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrVisitPrescription temrVisitPrescription) {
//        return temrVisitPrescriptionRepository.findAllByVpPatientMrNumberAndClosePrescriptionFalseAndIsActiveTrueAndIsDeletedFalse(temrVisitPrescription.getVpPatientMrNumber());
//    }

    @PostMapping
    @RequestMapping("bymrnoforClosePre")
    public List<TemrVistPrecriptionDTOForPharmacy> bymrnoforClosePre(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrVisitPrescription temrVisitPrescription) {
        TenantContext.setCurrentTenant(tenantName);
        List<TemrVistPrecriptionDTOForPharmacy> DtoPreList = temrVisitPrescriptionRepository.findByVpPatientMrNumber(temrVisitPrescription.getVpPatientMrNumber());
        return DtoPreList;
    }

    @GetMapping
    @RequestMapping("bytimelineid/{timelineId}")
    public List<TemrItemPrescription> bytimelineid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("timelineId") Long timelineId) {
        TenantContext.setCurrentTenant(tenantName);
        List<TemrVisitPrescription> obj;
        obj = temrVisitPrescriptionRepository.findByVpTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(timelineId);
        //return temrVisitPrescriptionRepository.findByVpVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(visitId);
        if (obj != null && obj.size() != 0) {
            Long vsid = obj.get(obj.size() - 1).getVpId();
            return temrItemPrescriptionRepository.findByIpVpIdVpIdAndIsActiveTrueAndIsDeletedFalse(vsid);
        } else {
            return new ArrayList<TemrItemPrescription>();
        }
    }

    @GetMapping
    @RequestMapping("bypatientid/{patientId}")
    public List<TemrItemPrescription> bypatientid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId) {
        TenantContext.setCurrentTenant(tenantName);
        List<TemrVisitPrescription> obj;
//       obj =temrVisitPrescriptionRepository.findByVpTimelineIdTimelinePatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(patientId);
//        return temrVisitPrescriptionRepository.findByVpVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(visitId);
//        if (obj != null && obj.size() != 0) {
//            Long vsid = obj.get(obj.size() - 1).getVpId();
//            return temrItemPrescriptionRepository.findByIpVpIdVpIdAndIsActiveTrueAndIsDeletedFalse(vsid);
//        } else {
//            return new ArrayList<TemrItemPrescription>();
//        }
        return temrItemPrescriptionRepository.findAllByIpVpIdVpTimelineIdTimelinePatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(patientId);
    }

    @GetMapping
    @RequestMapping("byAdmisionId/{admisionId}")
    public List<TemrVisitPrescription> byAdmisionId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("admisionId") Long admisionId) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVisitPrescriptionRepository.findByVpTimelineIdTimelineAdmissionIdAdmissionIdAndIsActiveTrueAndIsDeletedFalse(admisionId);
    }

    @RequestMapping("getAllRecordByTimelineList")
    public List<TemrItemPrescription> getAllRecordByTimelineList(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TemrTimeline> temrTimeline) {
        TenantContext.setCurrentTenant(tenantName);
        List<TemrVisitPrescription> prescriptionsList = new ArrayList<TemrVisitPrescription>();
        List<TemrVisitPrescription> prescriptions;
        List<TemrItemPrescription> itemPrescriptionsList = new ArrayList<TemrItemPrescription>();
        List<TemrItemPrescription> itemPrescriptions;
        for (int i = 0; i < temrTimeline.size(); i++) {
            prescriptions = new ArrayList<TemrVisitPrescription>();
            prescriptions = temrVisitPrescriptionRepository.findAllByVpTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(temrTimeline.get(i).getTimelineId());
            for (int j = 0; j < prescriptions.size(); j++) {
                prescriptionsList.add(prescriptions.get(j));
            }
        }
        for (int i = 0; i < prescriptionsList.size(); i++) {
            itemPrescriptions = new ArrayList<TemrItemPrescription>();
            itemPrescriptions = temrItemPrescriptionRepository.findByIpVpIdVpIdAndIsActiveTrueAndIsDeletedFalse(prescriptionsList.get(i).getVpId());
            for (int j = 0; j < itemPrescriptions.size(); j++) {
                itemPrescriptionsList.add(itemPrescriptions.get(j));
            }
        }
        return itemPrescriptionsList;

    }

}
            
