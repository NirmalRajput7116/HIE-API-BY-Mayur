package com.cellbeans.hspa.mstfield;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mstdsffield.MstDsfFieldRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_field")
public class MstFieldController {
    Map<String, String> respMap = new HashMap<String, String>();
    Map<String, Object> restMap = new HashMap<String, Object>();

    @Autowired
    MstFieldRepository mstFieldRepository;

    @Autowired
    MstDsfFieldRepository mstDsfFieldRepository;

    @RequestMapping("create")
    public Map<String, Object> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstField mstField) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstField.getFieldName() != null) {
            MstField mstFieldNew = mstFieldRepository.save(mstField);
            restMap.put("success", "1");
            restMap.put("fieldId", mstFieldNew.getFieldId());
            restMap.put("msg", "Added Successfully");
            return restMap;
        } else {
            restMap.put("success", "0");
            restMap.put("msg", "Failed To Add Null Field");
            return restMap;
        }
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MstField> records;
        records = mstFieldRepository.findByFieldNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("/fieldlist")
    public Map<String, Object> fieldList(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MstField> records;
        records = mstFieldRepository.findDistinctFieldName();
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byidToConcat")
    public Map<String, String> read(@RequestHeader("X-tenantId") String tenantName, @RequestBody String json) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, String> hm = new HashMap<>();
        String result = "";
        JSONArray array = new JSONArray(json);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            MstField mstField = mstFieldRepository.getById(object.getLong("key"));
            result = result + mstField.getFieldName() + ": " + object.getString("desc") + "<br/>";
        }
        hm.put("result", result);
        return hm;
    }

    @RequestMapping("byid/{fieldId}")
    public MstField read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fieldId") Long fieldId) {
        TenantContext.setCurrentTenant(tenantName);
        MstField mstField = mstFieldRepository.getById(fieldId);
        return mstField;
    }

    @RequestMapping("byname/{fieldName}")
    public List<MstField> readname(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fieldName") String fieldName) {
        TenantContext.setCurrentTenant(tenantName);
        return mstFieldRepository.findByFieldNameEqualsAndIsActiveTrueAndIsDeletedFalse(fieldName);
    }

    @RequestMapping("update")
    public MstField update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstField mstField) {
        TenantContext.setCurrentTenant(tenantName);
        return mstFieldRepository.save(mstField);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstField> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "fieldId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstFieldRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstFieldRepository.findByFieldNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }
    }

    @PutMapping("delete/{fieldId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fieldId") Long fieldId) {
        TenantContext.setCurrentTenant(tenantName);
        MstField mstField = mstFieldRepository.getById(fieldId);
        if (mstField != null) {
            mstField.setIsDeleted(true);
            mstFieldRepository.save(mstField);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("getfieldlistbysetid/{setId}")
    public List<MstField> getFieldListByTabId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("setId") Long setId) {
        TenantContext.setCurrentTenant(tenantName);
        List<MstField> mstFieldList = mstFieldRepository.findByFieldSetIdSetIdEqualsAndIsActiveTrueAndIsDeletedFalse(setId);
        return mstFieldList;
    }

    @RequestMapping("getfieldlistbydsfid/{dsfId}")
    public List<MstField> getFieldListByDsfId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dsfId") Long dsfId) {
        TenantContext.setCurrentTenant(tenantName);
        List<MstField> mstFieldList = mstFieldRepository.findByDfDsfIdDsfIdEqualsAndIsActiveTrueAndIsDeletedFalse(dsfId);
        return mstFieldList;
    }
}
