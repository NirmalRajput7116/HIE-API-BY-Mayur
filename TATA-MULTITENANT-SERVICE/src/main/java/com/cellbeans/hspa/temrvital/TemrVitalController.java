package com.cellbeans.hspa.temrvital;

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
@RequestMapping("/temr_vital")
public class TemrVitalController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TemrVitalRepository temrVitalRepository;

    @Autowired
    TemrTimelineRepository temrTimelineRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrVital temrVital) {
        TenantContext.setCurrentTenant(tenantName);
        TemrVital temrVitalNew = null;
        if (temrVital != null) {
            try {
                temrVitalNew = temrVitalRepository.findTimelineIdAndIsActiveTrueAndIsDeletedFalse(temrVital.getVitalTimelineId().getTimelineId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (temrVitalNew != null) {
            temrVital.setVitalId(temrVitalNew.getVitalId());
            temrVitalRepository.save(temrVital);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else if (temrVital != null) {
            temrVitalRepository.save(temrVital);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Please Enter Remark");
            return respMap;
        }
//        if (temrVital != null) {
//            temrVitalRepository.save(temrVital);
//            respMap.put("success", "1");
//            respMap.put("msg", "Added Successfully");
//            return respMap;
//        }
//        else {
//            respMap.put("success", "0");
//            respMap.put("msg", "Please Enter Remark");
//            return respMap;
//        }
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<TemrVital> records;
        records = temrVitalRepository.findByVitalRemarkContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{vitalId}")
    public TemrVital read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vitalId") Long vitalId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrVital temrVital = temrVitalRepository.getById(vitalId);
        return temrVital;
    }

    @RequestMapping("byvisitid/{visitId}")
    public TemrVital readByVisitid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("visitId") Long visitId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrVital temrVital = temrVitalRepository.findByVitalVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(visitId);
        return temrVital;
    }

  /*  @GetMapping
    @RequestMapping("getrecordbytimelineid")
    public TemrVital getrecordbytimelineid(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qString", required = false) String qString) {
        return temrVitalRepository.findTimelineIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString));
    }*/

    @RequestMapping("update")
    public TemrVital update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrVital temrVital) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVitalRepository.save(temrVital);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TemrVital> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "vitalId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return temrVitalRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return temrVitalRepository.findByVitalVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{vitalId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vitalId") Long vitalId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrVital temrVital = temrVitalRepository.getById(vitalId);
        if (temrVital != null) {
            temrVital.setIsDeleted(true);
            temrVitalRepository.save(temrVital);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("listbytimelineid")
    public Iterable<TemrVital> listbytimelineid(@RequestHeader("X-tenantId") String tenantName,
                                                @RequestParam(value = "page") String page,
                                                @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                                @RequestParam(value = "qString", required = false) String qString,
                                                @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                @RequestParam(value = "col", required = false, defaultValue = "vitalId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return temrVitalRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return temrVitalRepository.findByVitalTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @GetMapping
    @RequestMapping("listbypatientid")
    public Iterable<TemrVital> listbypatientid(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "vitalId") String col) {
        TenantContext.setCurrentTenant(tenantName);
//        if (qString == null || qString.equals("")) {
//            return temrVitalRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//
//        } else {
            return temrVitalRepository.findByVitalTimelineIdTimelinePatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

//        }
    }

    @GetMapping
    @RequestMapping("getVitaldetailsByPatientId/{patientId}/{startdate}/{enddate}/{page}/{size}")
    public Iterable<TemrVital> getVitaldetailsByPatientId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") String patientId, @PathVariable("startdate") String startdate, @PathVariable("enddate") String enddate, @PathVariable("page") String page, @PathVariable("size") String size) {
        TenantContext.setCurrentTenant(tenantName);
        String sort = "DESC";
        String col = "vital_id";
//        Date StartDate = new Date();
//        Date Enddate= new Date();
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//
//        try {
//             StartDate = df.parse(startdate);
//             Enddate = df.parse(enddate);
//       } catch (ParseException e) {
//            e.printStackTrace();
//        }
        if (enddate.equals(startdate) || startdate.equals(enddate)) {
            return temrVitalRepository.findAllByVitalVisitIdVisitPatientIdPatientIdAndCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(patientId), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return temrVitalRepository.findAllByVitalVisitIdVisitPatientIdPatientIdAndCreatedDateBetweenAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(patientId), startdate, enddate, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

    @RequestMapping("addvitalusingmachine")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "vitalBodyTemp") String vitalBodyTemp, @RequestParam(value = "vitalDiaBp") String vitalDiaBp, @RequestParam(value = "vitalSysBp") String vitalSysBp, @RequestParam(value = "vitalPulse") String vitalPulse, @RequestParam(value = "vitalSpo2") String vitalSpo2, @RequestParam(value = "vitalMap") String vitalMap, @RequestParam(value = "vitalTimelineId") Long vitalTimelineId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrTimeline temrTimeline = new TemrTimeline();
        temrTimeline = temrTimelineRepository.findByTimelineIdEqualsAndIsActiveTrueAndIsDeletedFalse(vitalTimelineId);
        if (vitalTimelineId != null || vitalTimelineId != 0) {
            TemrVital temrVital = new TemrVital();
            if (vitalBodyTemp != "") {
                temrVital.setVitalBodyTemp(vitalBodyTemp);
            }
            if (vitalDiaBp != "") {
                temrVital.setVitalDiaBp(vitalDiaBp);
            }
            if (vitalSysBp != "") {
                temrVital.setVitalSysBp(vitalSysBp);
            }
            if (vitalPulse != "") {
                temrVital.setVitalPulse(vitalPulse);
            }
            if (vitalSpo2 != "") {
                temrVital.setVitalSpo2(vitalSpo2);
            }
            if (vitalMap != "") {
                temrVital.setVitalMap(vitalMap);
            }
            temrVital.setVitalTimelineId(temrTimeline);
            temrVital.setVitalVisitId(temrTimeline.getTimelineVisitId());
            temrVitalRepository.save(temrVital);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Please Enter Remark");
            return respMap;
        }
    }

    @RequestMapping("getAllRecordByTimelineList")
    public List<TemrVital> getAllRecordByTimelineList(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TemrTimeline> temrTimeline) {
        TenantContext.setCurrentTenant(tenantName);
        List<TemrVital> temrVitalList = new ArrayList<TemrVital>();
        List<TemrVital> temrVital;
        for (int i = 0; i < temrTimeline.size(); i++) {
            temrVital = new ArrayList<TemrVital>();
            temrVital = temrVitalRepository.findAllByVitalTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(temrTimeline.get(i).getTimelineId());
            for (int j = 0; j < temrVital.size(); j++) {
                temrVitalList.add(temrVital.get(j));
            }
        }
        return temrVitalList;
    }

    @GetMapping
    @RequestMapping("getrecordbytimelineid/{timelineId}")
    public TemrVital getrecordbytimelineid(@RequestHeader("X-tenantId") String tenantName,
                                           @PathVariable("timelineId") Long timelineId) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVitalRepository.findByVitalTimelineIdTimelineIdEquals(timelineId);
    }

}

