package com.cellbeans.hspa.tbillconcessiontemplategroup;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tbill_concession_template_group")
public class TbillConcessionTemplateGroupController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TbillConcessionTemplateGroupRepository tbillConcessionTemplateGroupRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillConcessionTemplateGroup tbillConcessionTemplateGroup) {
        TenantContext.setCurrentTenant(tenantName);
        if (tbillConcessionTemplateGroup.getCtgCtId().getCtName() != null) {
            tbillConcessionTemplateGroupRepository.save(tbillConcessionTemplateGroup);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }
        
    /*    @RequestMapping("/autocomplete/{key}")
    public  Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
		Map<String, Object> automap  = new HashMap<String, Object>();
		List<TbillConcessionTemplateGroup> records;
		records = tbillConcessionTemplateGroupRepository.findByCtgGroupIdGroupNameContains(key);
		automap.put("record", records);
		return automap;
	}*/

    @RequestMapping("byid/{ctgId}")
    public TbillConcessionTemplateGroup read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ctgId") Long ctgId) {
        TenantContext.setCurrentTenant(tenantName);
        TbillConcessionTemplateGroup tbillConcessionTemplateGroup = tbillConcessionTemplateGroupRepository.getById(ctgId);
        return tbillConcessionTemplateGroup;
    }

    @RequestMapping("update")
    public TbillConcessionTemplateGroup update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillConcessionTemplateGroup tbillConcessionTemplateGroup) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillConcessionTemplateGroupRepository.save(tbillConcessionTemplateGroup);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TbillConcessionTemplateGroup> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ctgId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tbillConcessionTemplateGroupRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tbillConcessionTemplateGroupRepository.findByCtgPercentageContainsAndIsActiveTrueAndIsDeletedFalse(Long.valueOf(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{ctgId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ctgId") Long ctgId) {
        TenantContext.setCurrentTenant(tenantName);
        TbillConcessionTemplateGroup tbillConcessionTemplateGroup = tbillConcessionTemplateGroupRepository.getById(ctgId);
        if (tbillConcessionTemplateGroup != null) {
            tbillConcessionTemplateGroup.setIsDeleted(true);
            tbillConcessionTemplateGroupRepository.save(tbillConcessionTemplateGroup);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
