package com.cellbeans.hspa.mstnondrugallergy;

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
@RequestMapping("/mst_non_drug_allergy")
public class MstNonDrugAllergyController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstNonDrugAllergyRepository mstNonDrugAllergyRepository;
    @Autowired
    private MstNonDrugAllergyService mstNonDrugAllergyService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstNonDrugAllergy mstNonDrugAllergy) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstNonDrugAllergy.getNdaName() != null) {
            mstNonDrugAllergy.setNdaName(mstNonDrugAllergy.getNdaName().trim());
            MstNonDrugAllergy mstNonDrugAllergyObject = mstNonDrugAllergyRepository.findByNdaNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstNonDrugAllergy.getNdaName());
            if (mstNonDrugAllergyObject == null) {
                mstNonDrugAllergyRepository.save(mstNonDrugAllergy);
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
        List<MstNonDrugAllergy> records;
        records = mstNonDrugAllergyRepository.findByNdaNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{ndaId}")
    public MstNonDrugAllergy read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ndaId") Long ndaId) {
        TenantContext.setCurrentTenant(tenantName);
        MstNonDrugAllergy mstNonDrugAllergy = mstNonDrugAllergyRepository.getById(ndaId);
        return mstNonDrugAllergy;
    }

    @RequestMapping("update")
    public MstNonDrugAllergy update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstNonDrugAllergy mstNonDrugAllergy) {
        TenantContext.setCurrentTenant(tenantName);
        return mstNonDrugAllergyRepository.save(mstNonDrugAllergy);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstNonDrugAllergy> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ndaId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstNonDrugAllergyRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstNonDrugAllergyRepository.findByNdaNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{ndaId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ndaId") Long ndaId) {
        TenantContext.setCurrentTenant(tenantName);
        MstNonDrugAllergy mstNonDrugAllergy = mstNonDrugAllergyRepository.getById(ndaId);
        if (mstNonDrugAllergy != null) {
            mstNonDrugAllergy.setIsDeleted(true);
            mstNonDrugAllergyRepository.save(mstNonDrugAllergy);
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
        List<Tuple> items = mstNonDrugAllergyService.getMstNonDrugAllergyForDropdown(page, size, globalFilter);
        return items;
    }

}
            
