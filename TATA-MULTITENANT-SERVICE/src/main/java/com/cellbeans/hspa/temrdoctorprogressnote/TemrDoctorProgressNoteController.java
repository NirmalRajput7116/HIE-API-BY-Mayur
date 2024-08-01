package com.cellbeans.hspa.temrdoctorprogressnote;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/temr_doctor_progress_note")
public class TemrDoctorProgressNoteController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TemrDoctorProgressNoteRepository temrDoctorProgressNoteRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrDoctorProgressNote temrDoctorProgressNote) {
        TenantContext.setCurrentTenant(tenantName);
        if (temrDoctorProgressNote.getDpnVisitId().getVisitId() > 0) {
            temrDoctorProgressNoteRepository.save(temrDoctorProgressNote);
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
        List<TemrDoctorProgressNote> records;
        records = temrDoctorProgressNoteRepository.findByDpnIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{dpnId}")
    public TemrDoctorProgressNote read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dpnId") Long dpnId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrDoctorProgressNote temrDoctorProgressNote = temrDoctorProgressNoteRepository.getById(dpnId);
        return temrDoctorProgressNote;
    }

    @RequestMapping("update")
    public TemrDoctorProgressNote update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrDoctorProgressNote temrDoctorProgressNote) {
        TenantContext.setCurrentTenant(tenantName);
        return temrDoctorProgressNoteRepository.save(temrDoctorProgressNote);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TemrDoctorProgressNote> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "dpnId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return temrDoctorProgressNoteRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return temrDoctorProgressNoteRepository.findByDpnIdContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{dpnId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dpnId") Long dpnId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrDoctorProgressNote temrDoctorProgressNote = temrDoctorProgressNoteRepository.getById(dpnId);
        if (temrDoctorProgressNote != null) {
            temrDoctorProgressNote.setIsDeleted(true);
            temrDoctorProgressNoteRepository.save(temrDoctorProgressNote);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
