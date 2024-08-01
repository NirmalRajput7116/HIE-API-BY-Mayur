package com.cellbeans.hspa.memricdcode;

import com.cellbeans.hspa.TenantContext;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/memr_icd_code")
public class MemrIcdCodeController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MemrIcdCodeRepository memrIcdCodeRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private MemrIcdCodeService memrIcdCodeService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrIcdCode memrIcdCode) {
        TenantContext.setCurrentTenant(tenantName);
        if (memrIcdCode.getIcCode() != null) {
            if (memrIcdCodeRepository.findByAllOrderByClusterName(memrIcdCode.getIcCode()) == 0) {
                memrIcdCodeRepository.save(memrIcdCode);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Already Added");
            }
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("/autocompleteICCode/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        List<MemrIcdCode> records;
        Map<String, Object> automap = new HashMap<String, Object>();
        records = memrIcdCodeRepository.findByIcCodeContains(key);
//        String query = "SELECT * FROM memr_icd_code where ic_code like '%"+key+"%'";
//        System.out.println(query);
//        records = entityManager.createNativeQuery(query).getResultList();
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("/autocompleteICDesc/{key}")
    public Map<String, Object> listauto1(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        List<MemrIcdCode> records;
        Map<String, Object> automap = new HashMap<String, Object>();
        records = memrIcdCodeRepository.findByIcDescriptionContains(key);
//        String query = "SELECT * FROM memr_icd_code where ic_code like '%"+key+"%'";
//        System.out.println(query);
//        records = entityManager.createNativeQuery(query).getResultList();
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{icId}")
    public MemrIcdCode read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("icId") Long icId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrIcdCode memrIcdCode = memrIcdCodeRepository.getById(icId);
        return memrIcdCode;
    }

    @RequestMapping("update")
    public MemrIcdCode update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MemrIcdCode memrIcdCode) {
        TenantContext.setCurrentTenant(tenantName);
        return memrIcdCodeRepository.save(memrIcdCode);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MemrIcdCode> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "icId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return memrIcdCodeRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return memrIcdCodeRepository.findByIcCodeContainsAndIsActiveTrueAndIsDeletedFalseOrIcDescriptionContainsAndIsActiveTrueAndIsDeletedFalseOrIcDtIdDtNameContainsAndIsActiveTrueAndIsDeletedFalseOrIcDscIdDcsNameContainsAndIsActiveTrueAndIsDeletedFalseOrIcDcIdDcNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, qString, qString, qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @GetMapping
    @RequestMapping("autocomplete")
    public List<MemrIcdCode> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "col", required = false, defaultValue = "icId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "SELECT mi FROM MemrIcdCode mi where mi.icCode like '%" + qString + "%'";
        System.out.println(query);
        return entityManager.createQuery(query).getResultList();
    }

    @GetMapping
    @RequestMapping("autocompleteicddescription")
    public List<MemrIcdCode> listdesc(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "col", required = false, defaultValue = "icId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "SELECT mi FROM MemrIcdCode mi where mi.icDescription like '%" + qString + "%'";
        System.out.println(query);
        return entityManager.createQuery(query).getResultList();
    }

    @PutMapping("delete/{icId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("icId") Long icId) {
        TenantContext.setCurrentTenant(tenantName);
        MemrIcdCode memrIcdCode = memrIcdCodeRepository.getById(icId);
        if (memrIcdCode != null) {
            memrIcdCode.setIsDeleted(true);
            memrIcdCodeRepository.save(memrIcdCode);
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
        List<Tuple> items = memrIcdCodeService.getMemrIcdCodeForDropdown(page, size, globalFilter);
        return items;
    }

}
            
