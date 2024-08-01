package com.cellbeans.hspa.mstanaesthesianote;

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
@RequestMapping("/mst_anaesthesia_note")
public class MstAnaesthesiaNoteController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstAnaesthesiaNoteRepository mstAnaesthesiaNoteRepository;

    @Autowired
    private MstAnaesthesiaNoteService mstAnaesthesiaNoteService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstAnaesthesiaNote mstAnaesthesiaNote) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstAnaesthesiaNote.getAnShortDescription() != null) {
            mstAnaesthesiaNoteRepository.save(mstAnaesthesiaNote);
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
        List<MstAnaesthesiaNote> records;
        records = mstAnaesthesiaNoteRepository.findByAnShortDescriptionContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{anId}")
    public MstAnaesthesiaNote read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("anId") Long anId) {
        TenantContext.setCurrentTenant(tenantName);
        MstAnaesthesiaNote mstAnaesthesiaNote = mstAnaesthesiaNoteRepository.getById(anId);
        return mstAnaesthesiaNote;
    }

    @RequestMapping("update")
    public MstAnaesthesiaNote update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstAnaesthesiaNote mstAnaesthesiaNote) {
        TenantContext.setCurrentTenant(tenantName);
        return mstAnaesthesiaNoteRepository.save(mstAnaesthesiaNote);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstAnaesthesiaNote> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "anId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstAnaesthesiaNoteRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstAnaesthesiaNoteRepository.findByAnShortDescriptionContainsAndIsActiveTrueAndIsDeletedFalseOrAnLongDescriptionContainsAndIsActiveTrueAndIsDeletedFalse(qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{anId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("anId") Long anId) {
        TenantContext.setCurrentTenant(tenantName);
        MstAnaesthesiaNote mstAnaesthesiaNote = mstAnaesthesiaNoteRepository.getById(anId);
        if (mstAnaesthesiaNote != null) {
            mstAnaesthesiaNote.setIsDeleted(true);
            mstAnaesthesiaNoteRepository.save(mstAnaesthesiaNote);
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
        List<Tuple> items = mstAnaesthesiaNoteService.getMstAnaesthesiaNoteForDropdown(page, size, globalFilter);
        return items;
    }

}
            
