package com.cellbeans.hspa.mstdsffield;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mstfield.MstField;
import com.cellbeans.hspa.mstfield.MstFieldRepository;
import com.cellbeans.hspa.mstsection.MstSection;
import com.cellbeans.hspa.mstsection.MstSectionRepository;
import com.cellbeans.hspa.mstset.MstSet;
import com.cellbeans.hspa.mstset.MstSetRepository;
import com.cellbeans.hspa.msttab.MstTab;
import com.cellbeans.hspa.msttab.MstTabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_dsf_field")
public class MstDsfFieldController {
    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    MstDsfFieldRepository mstDsfFieldRepository;

    @Autowired
    MstSectionRepository mstSectionRepository;

    @Autowired
    MstTabRepository mstTabRepository;

    @Autowired
    MstSetRepository mstSetRepository;
    @Autowired
    MstFieldRepository mstFieldRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDsfFeild mstDsfFeild) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstDsfFeild.getDfDsfId() != null) {
            mstDsfFieldRepository.save(mstDsfFeild);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("getfieldlistbydsfid/{dfId}")
    public List<MstDsfFeild> getFieldListByDsfId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dfId") Long dfId) {
        TenantContext.setCurrentTenant(tenantName);
        List<MstDsfFeild> mstDsfFieldList = mstDsfFieldRepository.findByDfDsfIdDsfIdEqualsAndIsActiveTrueAndIsDeletedFalse(dfId);
        return mstDsfFieldList;
    }

    @RequestMapping("getbydsfidfieldid/{dsfId}/{fieldId}")
    public Map<String, Object> getByDsfIdFieldId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dsfId") Long dsfId, @PathVariable("fieldId") Long fieldId) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> restMap = new HashMap<String, Object>();
        MstDsfFeild mstDsfField = mstDsfFieldRepository.findByDfDsfIdDsfIdEqualsAndDfFieldIdFieldIdEquals(dsfId, fieldId);
        restMap.put("content", mstDsfField);
        return restMap;
    }

    @RequestMapping("update")
    public MstDsfFeild update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDsfFeild mstDsfFeild) {
        TenantContext.setCurrentTenant(tenantName);
        return mstDsfFieldRepository.save(mstDsfFeild);
    }

    @RequestMapping("getsectionlistbydsfid/{dsfId}")
    public List<MstSection> getSectionListByDsfId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dsfId") Long dsfId) {
        TenantContext.setCurrentTenant(tenantName);
        List<MstSection> mstSectionList = mstSectionRepository.findSectionListByFormId(dsfId);
        return mstSectionList;
    }

    @RequestMapping("gettablistbydsfidsectionid/{dsfId}/{sectionId}")
    public List<MstTab> getTabListByDsfIdSectionId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dsfId") Long dsfId, @PathVariable("sectionId") Long sectionId) {
        TenantContext.setCurrentTenant(tenantName);
        List<MstTab> mstTabList = mstTabRepository.findTabListByFormIdSectionId(dsfId, sectionId);
        return mstTabList;
    }

    @RequestMapping("getsetlistbydsfidsectionidtabid/{dsfId}/{sectionId}/{tabId}")
    public List<MstSet> getSetListByDsfIdSectionIdTabId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dsfId") Long dsfId, @PathVariable("sectionId") Long sectionId, @PathVariable("tabId") Long tabId) {
        TenantContext.setCurrentTenant(tenantName);
        List<MstSet> mstSetList = mstSetRepository.findSetListByFormIdTabIdSetId(dsfId, sectionId, tabId);
        return mstSetList;
    }

    @RequestMapping("getfieldlistbydsfidsectionidtabidsetid/{dsfId}/{sectionId}/{tabId}/{setId}")
    public List<MstField> getFieldListByDsfIdSectionIdTabIdSetId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dsfId") Long dsfId, @PathVariable("sectionId") Long sectionId, @PathVariable("tabId") Long tabId, @PathVariable("setId") Long setId) {
        TenantContext.setCurrentTenant(tenantName);
        List<MstField> mstFieldList = mstFieldRepository.findFieldListByDfDsfIdDsfIdSectionIdTabIdSetId(dsfId, sectionId, tabId, setId);
        return mstFieldList;
    }

}
