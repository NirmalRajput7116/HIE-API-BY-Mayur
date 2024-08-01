package com.cellbeans.hspa.motanesthesianote;

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
@RequestMapping("/mot_anesthesia_note")
public class MotAnesthesiaNoteController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MotAnesthesiaNoteRepository motAnesthesiaNoteRepository;

    @Autowired
    private MotAnesthesiaNoteService motAnesthesiaNoteService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MotAnesthesiaNote motAnesthesiaNote) {
        TenantContext.setCurrentTenant(tenantName);
        if (motAnesthesiaNote.getAnName() != null) {
            motAnesthesiaNoteRepository.save(motAnesthesiaNote);
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
        List<MotAnesthesiaNote> records;
        records = motAnesthesiaNoteRepository.findByAnNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{anId}")
    public MotAnesthesiaNote read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("anId") Long anId) {
        TenantContext.setCurrentTenant(tenantName);
        MotAnesthesiaNote motAnesthesiaNote = motAnesthesiaNoteRepository.getById(anId);
        return motAnesthesiaNote;
    }

    @RequestMapping("update")
    public MotAnesthesiaNote update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MotAnesthesiaNote motAnesthesiaNote) {
        TenantContext.setCurrentTenant(tenantName);
        return motAnesthesiaNoteRepository.save(motAnesthesiaNote);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MotAnesthesiaNote> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "anId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return motAnesthesiaNoteRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return motAnesthesiaNoteRepository.findByAnNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{anId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("anId") Long anId) {
        TenantContext.setCurrentTenant(tenantName);
        MotAnesthesiaNote motAnesthesiaNote = motAnesthesiaNoteRepository.getById(anId);
        if (motAnesthesiaNote != null) {
            motAnesthesiaNote.setIsDeleted(true);
            motAnesthesiaNoteRepository.save(motAnesthesiaNote);
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
        List<Tuple> items = motAnesthesiaNoteService.getMotAnesthesiaNoteForDropdown(page, size, globalFilter);
        return items;
    }

}
            
