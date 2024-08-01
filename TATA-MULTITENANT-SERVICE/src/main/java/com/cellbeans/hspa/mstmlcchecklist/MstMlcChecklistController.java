package com.cellbeans.hspa.mstmlcchecklist;

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
@RequestMapping("/mst_mlc_checklist")
public class MstMlcChecklistController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstMlcChecklistRepository mstMlcChecklistRepository;

    @Autowired
    private MstMlcChecklistService mstMlcChecklistService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstMlcChecklist mstMlcChecklist) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstMlcChecklist.getMcName() != null) {
            mstMlcChecklistRepository.save(mstMlcChecklist);
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
        List<MstMlcChecklist> records;
        records = mstMlcChecklistRepository.findByMcNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{mcId}")
    public MstMlcChecklist read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("mcId") Long mcId) {
        TenantContext.setCurrentTenant(tenantName);
        MstMlcChecklist mstMlcChecklist = mstMlcChecklistRepository.getById(mcId);
        return mstMlcChecklist;
    }

    @RequestMapping("update")
    public MstMlcChecklist update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstMlcChecklist mstMlcChecklist) {
        TenantContext.setCurrentTenant(tenantName);
        return mstMlcChecklistRepository.save(mstMlcChecklist);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstMlcChecklist> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "mcId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstMlcChecklistRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstMlcChecklistRepository.findByMcNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{mcId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("mcId") Long mcId) {
        TenantContext.setCurrentTenant(tenantName);
        MstMlcChecklist mstMlcChecklist = mstMlcChecklistRepository.getById(mcId);
        if (mstMlcChecklist != null) {
            mstMlcChecklist.setIsDeleted(true);
            mstMlcChecklistRepository.save(mstMlcChecklist);
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
        List<Tuple> items = mstMlcChecklistService.getMstMlcChecklistForDropdown(page, size, globalFilter);
        return items;
    }

}
            
