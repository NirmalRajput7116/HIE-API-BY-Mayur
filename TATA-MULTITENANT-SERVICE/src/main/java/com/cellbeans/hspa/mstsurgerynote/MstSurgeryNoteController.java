package com.cellbeans.hspa.mstsurgerynote;

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
@RequestMapping("/mst_surgery_note")
public class MstSurgeryNoteController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstSurgeryNoteRepository mstSurgeryNoteRepository;

    @Autowired
    private MstSurgeryNoteService mstSurgeryNoteService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstSurgeryNote mstSurgeryNote) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstSurgeryNote.getSnShortDescription() != null) {
            mstSurgeryNoteRepository.save(mstSurgeryNote);
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
        List<MstSurgeryNote> records;
        records = mstSurgeryNoteRepository.findBySnShortDescriptionContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{snId}")
    public MstSurgeryNote read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("snId") Long snId) {
        TenantContext.setCurrentTenant(tenantName);
        MstSurgeryNote mstSurgeryNote = mstSurgeryNoteRepository.getById(snId);
        return mstSurgeryNote;
    }

    @RequestMapping("update")
    public MstSurgeryNote update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstSurgeryNote mstSurgeryNote) {
        TenantContext.setCurrentTenant(tenantName);
        return mstSurgeryNoteRepository.save(mstSurgeryNote);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstSurgeryNote> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "snId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstSurgeryNoteRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstSurgeryNoteRepository.findBysnShortDescriptionContainsAndIsActiveTrueAndIsDeletedFalseOrSnLongDescriptionAndIsActiveTrueAndIsDeletedFalse(qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{snId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("snId") Long snId) {
        TenantContext.setCurrentTenant(tenantName);
        MstSurgeryNote mstSurgeryNote = mstSurgeryNoteRepository.getById(snId);
        if (mstSurgeryNote != null) {
            mstSurgeryNote.setIsDeleted(true);
            mstSurgeryNoteRepository.save(mstSurgeryNote);
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
        List<Tuple> items = mstSurgeryNoteService.getMstSurgeryNoteForDropdown(page, size, globalFilter);
        return items;
    }

}
            
