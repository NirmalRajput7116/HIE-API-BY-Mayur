
package com.cellbeans.hspa.nmstconsentform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;
import com.cellbeans.hspa.nmstconsentform.NmstConsentForm;
import com.cellbeans.hspa.nmstconsentform.NmstConsentFormRepository;

@RestController
@RequestMapping("/nmst_consent_form")
public class NmstConsentFormController {

	Map<String, String> respMap = new HashMap<String, String>();
	@Autowired
	NmstConsentFormRepository nmstConsentFormRepository;

	@RequestMapping("create")
	public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody NmstConsentForm nmstConsentForm) {
	TenantContext.setCurrentTenant(tenantName);
		if (nmstConsentForm.getCfHouseholdNo() != null) {
			nmstConsentFormRepository.save(nmstConsentForm);
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
	public  Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
	TenantContext.setCurrentTenant(tenantName);
		Map<String, Object> automap  = new HashMap<String, Object>();
		List<NmstConsentForm> records;
		records = nmstConsentFormRepository.findByCfHouseholdNoContains(key);
		automap.put("record", records);
		return automap;
	}

	@RequestMapping("byid/{cfId}")
	public NmstConsentForm read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("cfId") Long cfId) {
	TenantContext.setCurrentTenant(tenantName);
		NmstConsentForm nmstConsentForm = nmstConsentFormRepository.getById(cfId);
		return nmstConsentForm;
	}

	@RequestMapping("update")
	public NmstConsentForm update(@RequestHeader("X-tenantId") String tenantName, @RequestBody NmstConsentForm nmstConsentForm) {
	TenantContext.setCurrentTenant(tenantName);
		return nmstConsentFormRepository.save(nmstConsentForm);
	}

	@GetMapping
	@RequestMapping("list")
	public Iterable<NmstConsentForm> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
			@RequestParam(value = "size", required = false, defaultValue = "20") String size,
			@RequestParam(value = "qString", required = false) String qString,
			@RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
			@RequestParam(value = "col", required = false, defaultValue = "cfId") String col) {
			TenantContext.setCurrentTenant(tenantName);

		if (qString == null || qString.equals("")) {
			return nmstConsentFormRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		} else {

			return nmstConsentFormRepository.findByCfHouseholdNoAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		}

	}

	@PutMapping("delete/{cfId}")
	public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("cfId") Long cfId) {
	TenantContext.setCurrentTenant(tenantName);
		NmstConsentForm nmstConsentForm = nmstConsentFormRepository.getById(cfId);
		if (nmstConsentForm != null) {
			nmstConsentForm.setIsDeleted(true);
			nmstConsentFormRepository.save(nmstConsentForm);
			respMap.put("msg", "Operation Successful");
			respMap.put("success", "1");
		} else {
			respMap.put("msg", "Operation Failed");
			respMap.put("success", "0");
		}
		return respMap;
	}

}

