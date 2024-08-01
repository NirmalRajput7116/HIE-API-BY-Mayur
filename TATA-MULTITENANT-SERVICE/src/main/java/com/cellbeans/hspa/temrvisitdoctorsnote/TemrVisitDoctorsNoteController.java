package com.cellbeans.hspa.temrvisitdoctorsnote;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/temr_visit_doctor_note")
public class TemrVisitDoctorsNoteController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TemrVisitDoctorsNoteRepository temrVisitDoctorsNoteRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrVisitDoctorsNote temrVisitDoctorsNote) {
        TenantContext.setCurrentTenant(tenantName);
        if (temrVisitDoctorsNote.getVadnId() != null) {
            temrVisitDoctorsNoteRepository.save(temrVisitDoctorsNote);
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
    public Map<String, String> createNew(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TemrVisitDoctorsNote> temrVisitDoctorsNote) {
        TenantContext.setCurrentTenant(tenantName);
        temrVisitDoctorsNoteRepository.saveAll(temrVisitDoctorsNote);
        respMap.put("success", "1");
        respMap.put("msg", "Added Successfully");
        return respMap;
    }
//        @RequestMapping("/autocomplete/{key}")
//	public  Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
//		Map<String, Object> automap  = new HashMap<String, Object>();
//		List<TemrVisitAllergy> records;
//		records = temrVisitAllergyRepository.findByAllergyNameContains(key);
//		automap.put("record", records);
//		return automap;
//	}

    @RequestMapping("byid/{vdnId}")
    public TemrVisitDoctorsNote read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vdnId") Long vdnId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrVisitDoctorsNote temrVisitDoctorsNote = temrVisitDoctorsNoteRepository.getById(vdnId);
        return temrVisitDoctorsNote;
    }

    @RequestMapping("update")
    public TemrVisitDoctorsNote update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrVisitDoctorsNote temrVisitDoctorsNote) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVisitDoctorsNoteRepository.save(temrVisitDoctorsNote);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TemrVisitDoctorsNote> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "vaVisitId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return temrVisitDoctorsNoteRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return temrVisitDoctorsNoteRepository.findByVdnIdContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @GetMapping
    @RequestMapping("listbyid")
    public Iterable<TemrVisitDoctorsNote> listById(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "patientId", required = false) Long patientId, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "vdnId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVisitDoctorsNoteRepository.findByVaPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(patientId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

    }

    @GetMapping
    @RequestMapping("listbytimelineid")
    public Iterable<TemrVisitDoctorsNote> listByTimelineId(@RequestHeader("X-tenantId") String tenantName,
                                                           @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                           @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                                           @RequestParam(value = "timelineId", required = false) Long timelineId,
                                                           @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                           @RequestParam(value = "col", required = false, defaultValue = "vdnId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        return temrVisitDoctorsNoteRepository.findByVaTimelineIdTimelineIdEqualsAndIsActiveTrueAndIsDeletedFalse(timelineId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

    }

    @PutMapping("delete/{vdnId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vdnId") Long vdnId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrVisitDoctorsNote temrVisitDoctorsNote = temrVisitDoctorsNoteRepository.getById(vdnId);
        if (temrVisitDoctorsNote != null) {
            temrVisitDoctorsNote.setDeleted(true);
            temrVisitDoctorsNoteRepository.save(temrVisitDoctorsNote);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

   /* @RequestMapping("getAllRecordByPatientId/{patientId}")
    public List<TemrVisitAllergy> getAllRecordByPatientId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId) {
        return temrVisitDoctorsNoteRepository.findByVaPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(patientId);

    }*/

}
