package com.cellbeans.hspa.trncompanycontract;

import com.cellbeans.hspa.TenantContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/trn_company_contract")
public class TrnCompanyContractController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TrnCompanyContractRepository trnCompanyContractRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnCompanyContract trnCompanyContract) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnCompanyContract.getCcCompanyId().getCompanyId() > 0) {
            trnCompanyContractRepository.save(trnCompanyContract);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }
        
    /*    @RequestMapping("/autocomplete/{key}")
    public  Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
		Map<String, Object> automap  = new HashMap<String, Object>();
		List<TrnCompanyContract> records;
		records = trnCompanyContractRepository.findByCdNameContains(key);
		automap.put("record", records);
		return automap;
	}*/

    @RequestMapping("byid/{ccId}")
    public TrnCompanyContract read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ccId") Long ccId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnCompanyContract trnCompanyContract = trnCompanyContractRepository.getById(ccId);
        return trnCompanyContract;
    }

    @RequestMapping("update")
    public TrnCompanyContract update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnCompanyContract trnCompanyContract) {
        TenantContext.setCurrentTenant(tenantName);
        return trnCompanyContractRepository.save(trnCompanyContract);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TrnCompanyContract> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ccId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return trnCompanyContractRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return trnCompanyContractRepository.findByCcCompanyIdCompanyNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{ccId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ccId") Long ccId) {
        TenantContext.setCurrentTenant(tenantName);
        TrnCompanyContract trnCompanyContract = trnCompanyContractRepository.getById(ccId);
        if (trnCompanyContract != null) {
            trnCompanyContract.setIsDeleted(true);
            trnCompanyContractRepository.save(trnCompanyContract);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
