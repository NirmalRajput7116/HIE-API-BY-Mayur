
package com.cellbeans.hspa.nmstcontact;

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
import com.cellbeans.hspa.nmstcontact.NmstContact;
import com.cellbeans.hspa.nmstcontact.NmstContactRepository;

@RestController
@RequestMapping("/nmst_contact")
public class NmstContactController {

	Map<String, String> respMap = new HashMap<String, String>();
	@Autowired
	NmstContactRepository nmstContactRepository;

	@RequestMapping("create")
	public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody NmstContact nmstContact) {
	TenantContext.setCurrentTenant(tenantName);
		if (nmstContact.getContactMobile() != null) {
			nmstContactRepository.save(nmstContact);
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
		List<NmstContact> records;
		records = nmstContactRepository.findByContactMobileContains(key);
		automap.put("record", records);
		return automap;
	}

	@RequestMapping("byid/{contactId}")
	public NmstContact read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("contactId") Long contactId) {
	TenantContext.setCurrentTenant(tenantName);
		NmstContact nmstContact = nmstContactRepository.getById(contactId);
		return nmstContact;
	}

	@RequestMapping("update")
	public NmstContact update(@RequestHeader("X-tenantId") String tenantName, @RequestBody NmstContact nmstContact) {
	TenantContext.setCurrentTenant(tenantName);
		return nmstContactRepository.save(nmstContact);
	}

	@GetMapping
	@RequestMapping("list")
	public Iterable<NmstContact> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
			@RequestParam(value = "size", required = false, defaultValue = "20") String size,
			@RequestParam(value = "qString", required = false) String qString,
			@RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
			@RequestParam(value = "col", required = false, defaultValue = "contactId") String col) {
			TenantContext.setCurrentTenant(tenantName);

		if (qString == null || qString.equals("")) {
			return nmstContactRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		} else {

			return nmstContactRepository.findByContactMobileAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		}

	}

	@PutMapping("delete/{contactId}")
	public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("contactId") Long contactId) {
	TenantContext.setCurrentTenant(tenantName);
		NmstContact nmstContact = nmstContactRepository.getById(contactId);
		if (nmstContact != null) {
			nmstContact.setIsDeleted(true);
			nmstContactRepository.save(nmstContact);
			respMap.put("msg", "Operation Successful");
			respMap.put("success", "1");
		} else {
			respMap.put("msg", "Operation Failed");
			respMap.put("success", "0");
		}
		return respMap;
	}

}
