package com.cellbeans.hspa.motsurgerynote;

import com.cellbeans.hspa.TenantContext;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mot_surgery_note")
public class MotSurgeryNoteController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MotSurgeryNoteRepository motSurgeryNoteRepository;

    @Autowired
    private MotSurgeryNoteService motSurgeryNoteService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MotSurgeryNote motSurgeryNote) {
        TenantContext.setCurrentTenant(tenantName);
        if (motSurgeryNote.getSnName() != null) {
            motSurgeryNoteRepository.save(motSurgeryNote);
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
        List<MotSurgeryNote> records;
        records = motSurgeryNoteRepository.findBySnNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{snId}")
    public MotSurgeryNote read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("snId") Long snId) {
        TenantContext.setCurrentTenant(tenantName);
        MotSurgeryNote motSurgeryNote = motSurgeryNoteRepository.getById(snId);
        return motSurgeryNote;
    }

    @RequestMapping("update")
    public MotSurgeryNote update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MotSurgeryNote motSurgeryNote) {
        TenantContext.setCurrentTenant(tenantName);
        return motSurgeryNoteRepository.save(motSurgeryNote);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MotSurgeryNote> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "snId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return motSurgeryNoteRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return motSurgeryNoteRepository.findBySnNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{snId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("snId") Long snId) {
        TenantContext.setCurrentTenant(tenantName);
        MotSurgeryNote motSurgeryNote = motSurgeryNoteRepository.getById(snId);
        if (motSurgeryNote != null) {
            motSurgeryNote.setIsDeleted(true);
            motSurgeryNoteRepository.save(motSurgeryNote);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = motSurgeryNoteService.getMotSurgeryNoteForDropdown(page, size, globalFilter);
        return items;
    }

}
            
