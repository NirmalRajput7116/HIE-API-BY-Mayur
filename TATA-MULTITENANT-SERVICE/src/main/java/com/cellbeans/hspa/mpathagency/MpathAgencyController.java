package com.cellbeans.hspa.mpathagency;

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
@RequestMapping("/mpath_agency")
public class MpathAgencyController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MpathAgencyRepository mpathAgencyRepository;

    @Autowired
    private MpathAgencyService mpathAgencyService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathAgency mpathAgency) {
        TenantContext.setCurrentTenant(tenantName);
        if (mpathAgency.getAgencyName() != null) {
            mpathAgencyRepository.save(mpathAgency);
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
        List<MpathAgency> records;
        records = mpathAgencyRepository.findByAgencyNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{agencyId}")
    public MpathAgency read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("agencyId") Long agencyId) {
        TenantContext.setCurrentTenant(tenantName);
        MpathAgency mpathAgency = mpathAgencyRepository.getById(agencyId);
        return mpathAgency;
    }

    @RequestMapping("update")
    public MpathAgency update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathAgency mpathAgency) {
        TenantContext.setCurrentTenant(tenantName);
        return mpathAgencyRepository.save(mpathAgency);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MpathAgency> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "agencyId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mpathAgencyRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return mpathAgencyRepository.findByAgencyNameContainsOrAgencyCodeContainsOrAgencyLegalNameContainsOrAgencyAddressContainsOrAgencyEmailContainsOrAgencyAdminFirstNameContainsOrAgencyAdminLastNameContainsOrAgencyAdminEmailContainsAndIsActiveTrueAndIsDeletedFalse(qString, qString, qString, qString, qString, qString, qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

    @PutMapping("delete/{agencyId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("agencyId") Long agencyId) {
        TenantContext.setCurrentTenant(tenantName);
        MpathAgency mpathAgency = mpathAgencyRepository.getById(agencyId);
        if (mpathAgency != null) {
            mpathAgency.setIsDeleted(true);
            mpathAgencyRepository.save(mpathAgency);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("agencylistbyname")
    public List<MpathAgency> listByGroupName(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "agencyname", required = false) String agencyName) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("groupname :"+groupName);
        return mpathAgencyRepository.findByAgencyNameContainsAndIsActiveTrueAndIsDeletedFalse(agencyName);
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = mpathAgencyService.getMpathAgencyForDropdown(page, size, globalFilter);
        return items;
    }

}
            
