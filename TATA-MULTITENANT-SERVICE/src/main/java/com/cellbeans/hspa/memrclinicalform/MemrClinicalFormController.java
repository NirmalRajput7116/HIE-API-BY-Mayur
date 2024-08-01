package com.cellbeans.hspa.memrclinicalform;

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
@RequestMapping("/memr_clinical_form")
public class MemrClinicalFormController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MemrClinicalFormRepository memrClinicalFormRepository;

    @Autowired
    private MemrClinicalFormService memrClinicalFormService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrClinicalForm memrClinicalForm) {
        TenantContext.setCurrentTenant(tenantName);
        if (memrClinicalForm.getMcfName() != null) {
            memrClinicalFormRepository.save(memrClinicalForm);
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
        List<MemrClinicalForm> records;
        records = memrClinicalFormRepository.findByMcfIdContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{mcfId}")
    public MemrClinicalForm read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("mcfId") Long mcfId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrClinicalForm memrClinicalForm = memrClinicalFormRepository.getById(mcfId);
        return memrClinicalForm;
    }

    @RequestMapping("byname/{formname}")
    public List<MemrClinicalForm> byname(@RequestHeader("X-tenantId") String tenantName, @PathVariable("formname") String formname) {
        TenantContext.setCurrentTenant(tenantName);
        return memrClinicalFormRepository.findAllByMcfNameEqualsAndIsActiveTrueAndIsDeletedFalseOrderByMcfCountrolParameterAsc(formname);
    }

    @RequestMapping("listbyname")
    public List<MemrClinicalForm> listbyname(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        return memrClinicalFormRepository.findDistinctformName();
    }

    @RequestMapping("update")
    public MemrClinicalForm update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrClinicalForm memrClinicalForm) {
        TenantContext.setCurrentTenant(tenantName);
        return memrClinicalFormRepository.save(memrClinicalForm);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MemrClinicalForm> list(@RequestHeader("X-tenantId") String tenantName,
                                           @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                           @RequestParam(value = "size", required = false, defaultValue = "20") String size,
                                           @RequestParam(value = "qString", required = false) String qString,
                                           @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                           @RequestParam(value = "col", required = false, defaultValue = "mcfId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            //col = "mcf_id";
            // return memrClinicalFormRepository.findDistinctformName();
            return memrClinicalFormRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return memrClinicalFormRepository.findBymcfIdContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }
    }

    @PutMapping("delete/{mcfId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("mcfId") Long mcfId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrClinicalForm memrClinicalForm = memrClinicalFormRepository.getById(mcfId);
        if (memrClinicalForm != null) {
            memrClinicalForm.setIsDeleted(true);
            memrClinicalFormRepository.save(memrClinicalForm);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("deletebyname/{name}")
    public Map<String, String> read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("name") String name) {
        TenantContext.setCurrentTenant(tenantName);
        memrClinicalFormRepository.findByMcfNameEquals(name);
        //memrClinicalFormRepository.save(memrClinicalForm);
        respMap.put("msg", "Operation Successful");
        respMap.put("success", "1");
        return respMap;
    }

    //    All clinical form list 12.08.2021 by rohan and hemant
    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                            @RequestParam(value = "size", defaultValue = "5", required = false) Integer size,
                            @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = memrClinicalFormService.getMemrClinicalFormForDropdown(page, size, globalFilter);
        return items;
    }

    //    Orgid wise clinical form list 12.08.2021 by rohan and hemant
    @RequestMapping("byOrgid/{mcfOrgId}")
    public List<MemrClinicalForm> byOrgIdData(@RequestHeader("X-tenantId") String tenantName, @PathVariable("mcfOrgId") Long mcfOrgId) {
        TenantContext.setCurrentTenant(tenantName);
        List<MemrClinicalForm> memrClinicalForm = memrClinicalFormRepository.findAllByMcfOrgIdOrgIdAndIsActiveTrueAndIsDeletedFalseAndMcfIsCareCoordinationFalseOrderByMcfCountrolParameterAsc1(mcfOrgId);
        return memrClinicalForm;
    }

    @RequestMapping("byOrgidIscarecoordination/{mcfOrgId}")
    public List<MemrClinicalForm> byOrgIdDataIscarecoordination(@RequestHeader("X-tenantId") String tenantName, @PathVariable("mcfOrgId") Long mcfOrgId) {
        TenantContext.setCurrentTenant(tenantName);
        List<MemrClinicalForm> memrClinicalForm = memrClinicalFormRepository.findAllByMcfOrgIdOrgIdAndIsActiveTrueAndIsDeletedFalseAndMcfIsCareCoordinationTrueOrderByMcfCountrolParameterAsc1(mcfOrgId);
        return memrClinicalForm;
    }
}

