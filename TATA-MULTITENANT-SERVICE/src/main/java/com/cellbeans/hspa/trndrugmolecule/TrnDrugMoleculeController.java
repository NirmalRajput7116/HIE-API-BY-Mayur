package com.cellbeans.hspa.trndrugmolecule;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/trn_drug_molecule")
public class TrnDrugMoleculeController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnDrugMoleculeRepository trnDrugMoleculeRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnDrugMolecule trnDrugMolecule) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnDrugMolecule.getDmDrugId() != null) {
            trnDrugMoleculeRepository.save(trnDrugMolecule);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }
//        @RequestMapping("/autocomplete/{key}")
//	public  Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
//		Map<String, Object> automap  = new HashMap<String, Object>();
//		List<TrnDrugMolecule> records;
//		records = trnDrugMoleculeRepository.findByDrugNameContains(key);
//		automap.put("record", records);
//		return automap;
//	}

    @RequestMapping("byid/{dmId}")
    public TrnDrugMolecule read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dmId") Long dmId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnDrugMolecule trnDrugMolecule = trnDrugMoleculeRepository.getById(dmId);
        return trnDrugMolecule;
    }

    @RequestMapping("update")
    public TrnDrugMolecule update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnDrugMolecule trnDrugMolecule) {
        TenantContext.setCurrentTenant(tenantName);
        return trnDrugMoleculeRepository.save(trnDrugMolecule);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TrnDrugMolecule> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "dmId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return trnDrugMoleculeRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return trnDrugMoleculeRepository.findByDmDrugIdDrugNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{dmId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("dmId") Long dmId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnDrugMolecule trnDrugMolecule = trnDrugMoleculeRepository.getById(dmId);
        if (trnDrugMolecule != null) {
            trnDrugMolecule.setIsDeleted(true);
            trnDrugMoleculeRepository.save(trnDrugMolecule);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
