package com.cellbeans.hspa.mbillconcessiontemplategroupdetails;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mbill_concession_template_details")
public class MbillConcessionTemplateSubGroupDetailsController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MbillConcessionTemplateSubGroupDetailsRepository mbillConcessionTemplateSubGroupDetailsRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<MbillConcessionTemplateSubGroupDetails> mbillConcessionTemplateSubGroupDetails) {
        TenantContext.setCurrentTenant(tenantName);
        if (mbillConcessionTemplateSubGroupDetails.get(0).getCtgdCtId() != null) {
            mbillConcessionTemplateSubGroupDetailsRepository.saveAll(mbillConcessionTemplateSubGroupDetails);
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
        List<MbillConcessionTemplateSubGroupDetails> records;
        records = mbillConcessionTemplateSubGroupDetailsRepository.findByCtgdCtIdCtNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{ctgdId}")
    public MbillConcessionTemplateSubGroupDetails read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ctgdId") Long ctgdId) {
        TenantContext.setCurrentTenant(tenantName);
        MbillConcessionTemplateSubGroupDetails mbillConcessionTemplateSubGroupDetails = mbillConcessionTemplateSubGroupDetailsRepository.getById(ctgdId);
        return mbillConcessionTemplateSubGroupDetails;
    }

    @RequestMapping("update")
    public MbillConcessionTemplateSubGroupDetails update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MbillConcessionTemplateSubGroupDetails mbillConcessionTemplateSubGroupDetails) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillConcessionTemplateSubGroupDetailsRepository.save(mbillConcessionTemplateSubGroupDetails);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MbillConcessionTemplateSubGroupDetails> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ctgdId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mbillConcessionTemplateSubGroupDetailsRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mbillConcessionTemplateSubGroupDetailsRepository.findByCtgdSgIdSgNameContainsOrCtgdCtIdCtNameContains(qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @GetMapping
    @RequestMapping("listbyconcessiontemplate")
    public List<MbillConcessionTemplateSubGroupDetails> listByConcessionTemplateId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qString", required = false, defaultValue = "") String qString) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mbillConcessionTemplateSubGroupDetailsRepository.findAllByIsActiveTrueAndIsDeletedFalse();
        } else {
            return mbillConcessionTemplateSubGroupDetailsRepository.findAllByCtgdCtIdCtIdAndIsActiveTrueAndIsDeletedFalse(Long.valueOf(qString));

        }

    }

    @GetMapping
    @RequestMapping("listbysubgroupid")
    public List<MbillConcessionTemplateSubGroupDetails> listByConcessionSgId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qString", required = false) String qString) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mbillConcessionTemplateSubGroupDetailsRepository.findAllByIsActiveTrueAndIsDeletedFalse();
        } else {
            return mbillConcessionTemplateSubGroupDetailsRepository.findAllByCtgdSgIdSgIdAndIsActiveTrueAndIsDeletedFalse(Long.valueOf(qString));
        }
    }

    @PutMapping("delete/{ctgdId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ctgdId") Long ctgdId) {
        TenantContext.setCurrentTenant(tenantName);
        MbillConcessionTemplateSubGroupDetails mbillConcessionTemplateSubGroupDetails = mbillConcessionTemplateSubGroupDetailsRepository.getById(ctgdId);
        if (mbillConcessionTemplateSubGroupDetails != null) {
            mbillConcessionTemplateSubGroupDetails.setDeleted(true);
            mbillConcessionTemplateSubGroupDetailsRepository.save(mbillConcessionTemplateSubGroupDetails);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
