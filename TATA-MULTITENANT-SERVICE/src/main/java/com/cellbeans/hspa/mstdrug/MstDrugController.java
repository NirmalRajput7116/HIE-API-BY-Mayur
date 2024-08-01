package com.cellbeans.hspa.mstdrug;

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
@RequestMapping("/mst_drug")
public class MstDrugController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstDrugRepository mstDrugRepository;

    @Autowired
    private MstDrugService mstDrugService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDrug mstDrug) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstDrug.getDrugName() != null) {
            MstDrug md2 = mstDrugRepository.save(mstDrug);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            respMap.put("drugId", String.valueOf(md2.getDrugId()));
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
        List<MstDrug> records;
        records = mstDrugRepository.findByDrugNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{drugId}")
    public MstDrug read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("drugId") Long drugId) {
        TenantContext.setCurrentTenant(tenantName);
        MstDrug mstDrug = mstDrugRepository.getById(drugId);
        return mstDrug;
    }

    @RequestMapping("update")
    public MstDrug update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDrug mstDrug) {
        TenantContext.setCurrentTenant(tenantName);
        return mstDrugRepository.save(mstDrug);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstDrug> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "drugId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("--------qString--"+qString);
        if (qString == null || qString.equals("")) {
            return mstDrugRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstDrugRepository.findByDrugNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{drugId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("drugId") Long drugId) {
        TenantContext.setCurrentTenant(tenantName);
        MstDrug mstDrug = mstDrugRepository.getById(drugId);
        if (mstDrug != null) {
            mstDrug.setIsDeleted(true);
            mstDrugRepository.save(mstDrug);
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
        List<Tuple> items = mstDrugService.getMstDrugForDropdown(page, size, globalFilter);
        return items;
    }

}
            
