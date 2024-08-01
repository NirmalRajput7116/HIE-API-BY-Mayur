package com.cellbeans.hspa.tnstnurseprogressnotes;

import com.cellbeans.hspa.TenantContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tnst_nurse_progress_notes")
public class TnstNurseProgressNotesController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TnstNurseProgressNotesRepository tnstNurseProgressNotesRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TnstNurseProgressNotes tnstNurseProgressNotes) {
        TenantContext.setCurrentTenant(tenantName);
        if (tnstNurseProgressNotes.getNpnContent() != null) {
            tnstNurseProgressNotesRepository.save(tnstNurseProgressNotes);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

   /* @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        Map<String, Object> automap = new HashMap<String, Object>();
        List<TnstNurseProgressNotes> records;
        records = tnstNurseProgressNotesRepository.findByContains(key);
        automap.put("record", records);
        return automap;
    }*/

    @RequestMapping("byid/{npnId}")
    public TnstNurseProgressNotes read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("npnId") Long npnId) {
        TenantContext.setCurrentTenant(tenantName);
        TnstNurseProgressNotes tnstNurseProgressNotes = tnstNurseProgressNotesRepository.getById(npnId);
        return tnstNurseProgressNotes;
    }

    @RequestMapping("update")
    public TnstNurseProgressNotes update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TnstNurseProgressNotes tnstNurseProgressNotes) {
        TenantContext.setCurrentTenant(tenantName);
        return tnstNurseProgressNotesRepository.save(tnstNurseProgressNotes);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TnstNurseProgressNotes> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "npnId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tnstNurseProgressNotesRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tnstNurseProgressNotesRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @GetMapping
    @RequestMapping("listByAdmissionId")
    public Iterable<TnstNurseProgressNotes> listByAdmissionId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) Long qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "npnId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tnstNurseProgressNotesRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tnstNurseProgressNotesRepository.findByNpnAdmissionIdAdmissionIdAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{npnId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("npnId") Long npnId) {
        TenantContext.setCurrentTenant(tenantName);
        TnstNurseProgressNotes tnstNurseProgressNotes = tnstNurseProgressNotesRepository.getById(npnId);
        if (tnstNurseProgressNotes != null) {
            tnstNurseProgressNotes.setDeleted(true);
            tnstNurseProgressNotesRepository.save(tnstNurseProgressNotes);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
