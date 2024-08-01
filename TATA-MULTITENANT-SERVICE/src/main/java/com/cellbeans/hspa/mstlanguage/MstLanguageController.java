package com.cellbeans.hspa.mstlanguage;

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
@RequestMapping("/mst_language")
public class MstLanguageController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstLanguageRepository mstLanguageRepository;

    @Autowired
    private MstLanguageService mstLanguageService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstLanguage mstLanguage) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstLanguage.getLanguageName() != null) {
            mstLanguage.setLanguageName(mstLanguage.getLanguageName().trim());
            MstLanguage mstLanguageObject = mstLanguageRepository.findByLanguageNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstLanguage.getLanguageName());
            if (mstLanguageObject == null) {
                mstLanguageRepository.save(mstLanguage);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "2");
                respMap.put("msg", "Already Added");
                return respMap;
            }
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
        List<MstLanguage> records;
        records = mstLanguageRepository.findByLanguageNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{languageId}")
    public MstLanguage read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("languageId") Long languageId) {
        TenantContext.setCurrentTenant(tenantName);
        MstLanguage mstLanguage = mstLanguageRepository.getById(languageId);
        return mstLanguage;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstLanguage mstLanguage) {
        TenantContext.setCurrentTenant(tenantName);
        mstLanguage.setLanguageName(mstLanguage.getLanguageName().trim());
        MstLanguage mstLanguageObject = mstLanguageRepository.findByLanguageNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstLanguage.getLanguageName());
        if (mstLanguageObject == null) {
            mstLanguageRepository.save(mstLanguage);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstLanguageObject.getLanguageId() == mstLanguage.getLanguageId()) {
            mstLanguageRepository.save(mstLanguage);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else {
            respMap.put("success", "2");
            respMap.put("msg", "Already Added");
            return respMap;
        }
    }

    @RequestMapping("language")
    public List<MstLanguage> getLanguage(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        return mstLanguageRepository.findAllByIsDeletedFalse();
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstLanguage> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "languageId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstLanguageRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByLanguageName(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstLanguageRepository.findByLanguageNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByLanguageName(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{languageId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("languageId") Long languageId) {
        TenantContext.setCurrentTenant(tenantName);
        MstLanguage mstLanguage = mstLanguageRepository.getById(languageId);
        if (mstLanguage != null) {
            mstLanguage.setIsDeleted(true);
            mstLanguageRepository.save(mstLanguage);
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
        List<Tuple> items = mstLanguageService.getMstLanguageForDropdown(page, size, globalFilter);
        return items;
    }

}
            
