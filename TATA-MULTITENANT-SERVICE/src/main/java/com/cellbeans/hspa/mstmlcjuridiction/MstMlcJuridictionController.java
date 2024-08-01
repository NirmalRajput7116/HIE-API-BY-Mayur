package com.cellbeans.hspa.mstmlcjuridiction;

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
@RequestMapping("/mst_mlc_juridiction")
public class MstMlcJuridictionController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstMlcJuridictionRepository mstMlcJuridictionRepository;

    @Autowired
    private MstMlcJuridictionService mstMlcJuridictionService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstMlcJuridiction mstMlcJuridiction) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstMlcJuridiction.getMjName() != null) {
            mstMlcJuridictionRepository.save(mstMlcJuridiction);
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
        List<MstMlcJuridiction> records;
        records = mstMlcJuridictionRepository.findByMjNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{mjId}")
    public MstMlcJuridiction read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("mjId") Long mjId) {
        TenantContext.setCurrentTenant(tenantName);
        MstMlcJuridiction mstMlcJuridiction = mstMlcJuridictionRepository.getById(mjId);
        return mstMlcJuridiction;
    }

    @RequestMapping("update")
    public MstMlcJuridiction update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstMlcJuridiction mstMlcJuridiction) {
        TenantContext.setCurrentTenant(tenantName);
        return mstMlcJuridictionRepository.save(mstMlcJuridiction);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstMlcJuridiction> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "mjId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstMlcJuridictionRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstMlcJuridictionRepository.findByMjNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{mjId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("mjId") Long mjId) {
        TenantContext.setCurrentTenant(tenantName);
        MstMlcJuridiction mstMlcJuridiction = mstMlcJuridictionRepository.getById(mjId);
        if (mstMlcJuridiction != null) {
            mstMlcJuridiction.setIsDeleted(true);
            mstMlcJuridictionRepository.save(mstMlcJuridiction);
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
        List<Tuple> items = mstMlcJuridictionService.getMstMlcJuridictionForDropdown(page, size, globalFilter);
        return items;
    }

}
            
