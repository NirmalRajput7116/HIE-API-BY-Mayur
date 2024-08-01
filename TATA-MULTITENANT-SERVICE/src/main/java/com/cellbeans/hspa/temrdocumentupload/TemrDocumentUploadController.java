package com.cellbeans.hspa.temrdocumentupload;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.applicationproperty.Propertyconfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/temr_document_upload")
public class TemrDocumentUploadController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TemrDocumentUploadRepository temrDocumentUploadRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrDocumentUpload temrDocumentUpload) {
        TenantContext.setCurrentTenant(tenantName);
        if (temrDocumentUpload.getDuDcId() != null) {
            temrDocumentUploadRepository.save(temrDocumentUpload);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("getfilebasepath")
    public Map<String, String> getbasepath(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("BasePath" + Propertyconfig.getPatientFileBasePath());
        respMap.put("path", Propertyconfig.getPatientFileBasePath());
        return respMap;
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<TemrDocumentUpload> records;
        records = temrDocumentUploadRepository.findByDuDcIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{duId}")
    public TemrDocumentUpload read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("duId") Long duId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrDocumentUpload temrDocumentUpload = temrDocumentUploadRepository.getById(duId);
        return temrDocumentUpload;
    }

    @RequestMapping("update")
    public TemrDocumentUpload update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TemrDocumentUpload temrDocumentUpload) {
        TenantContext.setCurrentTenant(tenantName);
        return temrDocumentUploadRepository.save(temrDocumentUpload);
    }

    @GetMapping
    @RequestMapping("listByPatientId")
    public Iterable<TemrDocumentUpload> listByVisitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) Long patientId, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "duId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (patientId == null || patientId.equals("")) {
            return temrDocumentUploadRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return temrDocumentUploadRepository.findByDuPatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(patientId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{duId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("duId") Long duId) {
        TenantContext.setCurrentTenant(tenantName);
        TemrDocumentUpload temrDocumentUpload = temrDocumentUploadRepository.getById(duId);
        if (temrDocumentUpload != null) {
            temrDocumentUpload.setDeleted(true);
            temrDocumentUploadRepository.save(temrDocumentUpload);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
