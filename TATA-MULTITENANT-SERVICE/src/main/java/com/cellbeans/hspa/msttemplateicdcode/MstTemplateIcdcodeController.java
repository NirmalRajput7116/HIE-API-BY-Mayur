package com.cellbeans.hspa.msttemplateicdcode;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mst_template_icd_code")
public class MstTemplateIcdcodeController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstTemplateIcdcodeRepository mstTemplateIcdcodeRepository;

    @PersistenceContext
    EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstTemplateIcdcode mstTemplateIcdcode) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstTemplateIcdcode.getIcEtId() != null) {
            mstTemplateIcdcodeRepository.save(mstTemplateIcdcode);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("update")
    public MstTemplateIcdcode update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstTemplateIcdcode mstTemplateIcdcode) {
        TenantContext.setCurrentTenant(tenantName);
        return mstTemplateIcdcodeRepository.save(mstTemplateIcdcode);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstTemplateIcdcode> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                             @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                             @RequestParam(value = "qString", required = false) String qString,
                                             @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                             @RequestParam(value = "col", required = false, defaultValue = "icdId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString.equals(null) || qString == null) {
            return mstTemplateIcdcodeRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return mstTemplateIcdcodeRepository.findAllByIcEtIdEtIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

    @PutMapping("delete/{icdId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("icdId") Long icdId) {
        TenantContext.setCurrentTenant(tenantName);
        MstTemplateIcdcode mstTemplateIcdcode = mstTemplateIcdcodeRepository.getById(icdId);
        if (mstTemplateIcdcode != null) {
            mstTemplateIcdcode.setDeleted(true);
            mstTemplateIcdcodeRepository.save(mstTemplateIcdcode);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
