package com.cellbeans.hspa.msttemplateinvestigation;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_template_investigation")
public class MstTemplateInvestigationController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstTemplateInvestigationRepository mstTemplateInvestigationRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<MstTemplateInvestigation> mstTemplateInvestigation) {
        TenantContext.setCurrentTenant(tenantName);
        mstTemplateInvestigationRepository.saveAll(mstTemplateInvestigation);
        respMap.put("success", "1");
        respMap.put("msg", "Added Successfully");
        return respMap;
    }

    @RequestMapping("byid/{etId}")
    public MstTemplateInvestigation read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tiId") Long tiId) {
        TenantContext.setCurrentTenant(tenantName);
        MstTemplateInvestigation mstTemplateInvestigation = mstTemplateInvestigationRepository.getById(tiId);
        return mstTemplateInvestigation;
    }

    @RequestMapping("update")
    public MstTemplateInvestigation update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstTemplateInvestigation mstTemplateInvestigation) {
        TenantContext.setCurrentTenant(tenantName);
        return mstTemplateInvestigationRepository.save(mstTemplateInvestigation);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstTemplateInvestigation> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "tiId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstTemplateInvestigationRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstTemplateInvestigationRepository.findAllByTiEtIdEtIdAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{tiId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tiId") Long tiId) {
        TenantContext.setCurrentTenant(tenantName);
        MstTemplateInvestigation mstTemplateInvestigation = mstTemplateInvestigationRepository.getById(tiId);
        if (mstTemplateInvestigation != null) {
            mstTemplateInvestigation.setDeleted(true);
            mstTemplateInvestigationRepository.save(mstTemplateInvestigation);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @PutMapping("delete_by_etid/{id}/{tiId}")
    public Map<String, String> delete_by_etid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("id") Long id, @PathVariable("tiId") Long tiId) {
        TenantContext.setCurrentTenant(tenantName);
        if (tiId == 0) {
            List<MstTemplateInvestigation> mstTemplateInvestigation = mstTemplateInvestigationRepository.findAllByTiEtIdEtIdAndIsActiveTrueAndIsDeletedFalse(id);
            for (MstTemplateInvestigation inves : mstTemplateInvestigation) {
                inves.setDeleted(true);
                mstTemplateInvestigationRepository.saveAll(mstTemplateInvestigation);
            }
        } else {
            MstTemplateInvestigation mstTemplateInvestigation = mstTemplateInvestigationRepository.getById(tiId);
            mstTemplateInvestigation.setDeleted(true);
            mstTemplateInvestigationRepository.save(mstTemplateInvestigation);
        }
        respMap.put("msg", "Operation Successful");
        respMap.put("success", "1");
        return respMap;
    }

}
            
