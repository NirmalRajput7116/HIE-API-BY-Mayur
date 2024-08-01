package com.cellbeans.hspa.tnstbirthcertificate;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tnst_birth_certificate")
public class TnstBirthCertificateController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TnstBirthCertificateRepository tnstBirthCertificateRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TnstBirthCertificate tnstBirthCertificate) {
        TenantContext.setCurrentTenant(tenantName);
        if (tnstBirthCertificate.getBcNo() != null) {
            tnstBirthCertificateRepository.save(tnstBirthCertificate);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            // System.out.println(tnstBirthCertificate.toString());
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }
        
/*
      @RequestMapping("/autocomplete/{key}")
	public  Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
		Map<String, Object> automap  = new HashMap<String, Object>();
		List<TnstBirthCertificate> records;
		records = tnstBirthCertificateRepository.findByContains(key);
		automap.put("record", records);
		return automap;
	}
*/

    @RequestMapping("byid/{bcId}")
    public TnstBirthCertificate read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bcId") Long bcId) {
        TenantContext.setCurrentTenant(tenantName);
        TnstBirthCertificate tnstBirthCertificate = tnstBirthCertificateRepository.getById(bcId);
        return tnstBirthCertificate;
    }

    @RequestMapping("update")
    public TnstBirthCertificate update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TnstBirthCertificate tnstBirthCertificate) {
        return tnstBirthCertificateRepository.save(tnstBirthCertificate);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TnstBirthCertificate> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                               @RequestParam(value = "size", required = false, defaultValue = "20") String size,
                                               @RequestParam(value = "qString", required = false) Long qString,
                                               @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                               @RequestParam(value = "col", required = false, defaultValue = "bcId") String col,
                                               @RequestParam(value = "search", required = false) String search) {
        TenantContext.setCurrentTenant(tenantName);
        if (search == null || search.equals("")) {
            return tnstBirthCertificateRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tnstBirthCertificateRepository.findAllByBcAuthorizedStaffIdStaffUserIdUserFullnameContainsOrBcChildNameContainsAndBcUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(search, search, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{bcId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bcId") Long bcId) {
        TenantContext.setCurrentTenant(tenantName);
        TnstBirthCertificate tnstBirthCertificate = tnstBirthCertificateRepository.getById(bcId);
        if (tnstBirthCertificate != null) {
            tnstBirthCertificate.setDeleted(true);
            tnstBirthCertificateRepository.save(tnstBirthCertificate);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
            
