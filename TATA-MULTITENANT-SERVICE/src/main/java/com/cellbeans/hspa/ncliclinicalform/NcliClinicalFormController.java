
package com.cellbeans.hspa.ncliclinicalform;

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
import com.cellbeans.hspa.ncliclinicalform.NcliClinicalForm;
import com.cellbeans.hspa.ncliclinicalform.NcliClinicalFormRepository;

@RestController
@RequestMapping("/ncli_clinical_form")
public class NcliClinicalFormController {

	Map<String, String> respMap = new HashMap<String, String>();
	@Autowired
	NcliClinicalFormRepository ncliClinicalFormRepository;

	@RequestMapping("create")
	public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody NcliClinicalForm ncliClinicalForm) {
	TenantContext.setCurrentTenant(tenantName);
		if (ncliClinicalForm.getCfName() != null) {
			ncliClinicalFormRepository.save(ncliClinicalForm);
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
		List<NcliClinicalForm> records;
		records = ncliClinicalFormRepository.findByCfNameContains(key);
		automap.put("record", records);
		return automap;
	}

	@RequestMapping("byid/{cfId}")
	public NcliClinicalForm read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("cfId") Long cfId) {
	TenantContext.setCurrentTenant(tenantName);
		NcliClinicalForm ncliClinicalForm = ncliClinicalFormRepository.getById(cfId);
		return ncliClinicalForm;
	}

	@RequestMapping("update")
	public NcliClinicalForm update(@RequestHeader("X-tenantId") String tenantName, @RequestBody NcliClinicalForm ncliClinicalForm) {
	TenantContext.setCurrentTenant(tenantName);
		return ncliClinicalFormRepository.save(ncliClinicalForm);
	}

	@GetMapping
	@RequestMapping("list")
	public Iterable<NcliClinicalForm> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
										   @RequestParam(value = "size", required = false, defaultValue = "20") String size,
										   @RequestParam(value = "qString", required = false) String qString,
										   @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
										   @RequestParam(value = "col", required = false, defaultValue = "cfId") String col) {
		TenantContext.setCurrentTenant(tenantName);
		if (qString == null || qString.equals("")) {
			return ncliClinicalFormRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
		} else {
			return ncliClinicalFormRepository.findByCfNameAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
		}
	}


	@GetMapping
	@RequestMapping("listByCfcId")
	public Iterable<NcliClinicalForm> listByCfcId(@RequestHeader("X-tenantId") String tenantName,
												  @RequestParam(value = "cfcId", required = false) Long cfcId) {
		TenantContext.setCurrentTenant(tenantName);
//		if (qString == null || qString.equals("")) {
//			return ncliClinicalFormRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
//		} else {
			return ncliClinicalFormRepository.findAllByCfCfcIdCfcIdAndIsActiveTrueAndIsDeletedFalse(cfcId);
//		}
	}

	@PutMapping("delete/{cfId}")
	public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("cfId") Long cfId) {
	TenantContext.setCurrentTenant(tenantName);
		NcliClinicalForm ncliClinicalForm = ncliClinicalFormRepository.getById(cfId);
		if (ncliClinicalForm != null) {
			ncliClinicalForm.setIsDeleted(true);
			ncliClinicalFormRepository.save(ncliClinicalForm);
			respMap.put("msg", "Operation Successful");
			respMap.put("success", "1");
		} else {
			respMap.put("msg", "Operation Failed");
			respMap.put("success", "0");
		}
		return respMap;
	}

}

