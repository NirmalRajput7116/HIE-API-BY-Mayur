package com.cellbeans.hspa.mstsponsorcombination;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mstcompanytype.MstCompanyTypeRepository;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_sponsor_combination")
public class MstSponsorCombinationController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstSponsorCombinationRepository mstSponsorCombinationRepository;

    @Autowired
    MstCompanyTypeRepository mstCompanyTypeRepository;

    @Autowired
    private MstSponsorCombinationService mstSponsorCombinationService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstSponsorCombination mstSponsorCombination) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstSponsorCombination.getScName() != null) {
            mstSponsorCombinationRepository.save(mstSponsorCombination);
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
        List<MstSponsorCombination> records;
        records = mstSponsorCombinationRepository.findByScNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{scId}")
    public MstSponsorCombination read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("scId") Long scId) {
        TenantContext.setCurrentTenant(tenantName);
        MstSponsorCombination mstSponsorCombination = mstSponsorCombinationRepository.getById(scId);
        return mstSponsorCombination;
    }

    @RequestMapping("update")
    public MstSponsorCombination update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstSponsorCombination mstSponsorCombination) {
        TenantContext.setCurrentTenant(tenantName);
        return mstSponsorCombinationRepository.save(mstSponsorCombination);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstSponsorCombination> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "scId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstSponsorCombinationRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstSponsorCombinationRepository.findByScNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{scId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("scId") Long scId) {
        TenantContext.setCurrentTenant(tenantName);
        MstSponsorCombination mstSponsorCombination = mstSponsorCombinationRepository.getById(scId);
        if (mstSponsorCombination != null) {
            mstSponsorCombination.setIsDeleted(true);
            mstSponsorCombinationRepository.save(mstSponsorCombination);
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
        List<Tuple> items = mstSponsorCombinationService.getMstSponsorCombinationForDropdown(page, size, globalFilter);
        return items;
    }

}
            
