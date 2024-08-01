
package com.cellbeans.hspa.nrelsmdchecklist;

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
import com.cellbeans.hspa.nrelsmdchecklist.NrelSmdChecklist;
import com.cellbeans.hspa.nrelsmdchecklist.NrelSmdChecklistRepository;

@RestController
@RequestMapping("/nrel_smd_checklist")
public class NrelSmdChecklistController {

	Map<String, String> respMap = new HashMap<String, String>();
	@Autowired
	NrelSmdChecklistRepository nrelSmdChecklistRepository;

	@RequestMapping("create")
	public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody NrelSmdChecklist nrelSmdChecklist) {
	TenantContext.setCurrentTenant(tenantName);
		if (nrelSmdChecklist.getTest() != null) {
			nrelSmdChecklistRepository.save(nrelSmdChecklist);
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
		List<NrelSmdChecklist> records;
		records = nrelSmdChecklistRepository.findByTestContains(key);
		automap.put("record", records);
		return automap;
	}

	@RequestMapping("byid/{scId}")
	public NrelSmdChecklist read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("scId") Long scId) {
	TenantContext.setCurrentTenant(tenantName);
		NrelSmdChecklist nrelSmdChecklist = nrelSmdChecklistRepository.getById(scId);
		return nrelSmdChecklist;
	}

	@RequestMapping("update")
	public NrelSmdChecklist update(@RequestHeader("X-tenantId") String tenantName, @RequestBody NrelSmdChecklist nrelSmdChecklist) {
	TenantContext.setCurrentTenant(tenantName);
		return nrelSmdChecklistRepository.save(nrelSmdChecklist);
	}

	@GetMapping
	@RequestMapping("list")
	public Iterable<NrelSmdChecklist> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
			@RequestParam(value = "size", required = false, defaultValue = "20") String size,
			@RequestParam(value = "qString", required = false) String qString,
			@RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
			@RequestParam(value = "col", required = false, defaultValue = "scId") String col) {
			TenantContext.setCurrentTenant(tenantName);

		if (qString == null || qString.equals("")) {
			return nrelSmdChecklistRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		} else {

			return nrelSmdChecklistRepository.findByTestAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		}

	}

	@PutMapping("delete/{scId}")
	public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("scId") Long scId) {
	TenantContext.setCurrentTenant(tenantName);
		NrelSmdChecklist nrelSmdChecklist = nrelSmdChecklistRepository.getById(scId);
		if (nrelSmdChecklist != null) {
			nrelSmdChecklist.setIsDeleted(true);
			nrelSmdChecklistRepository.save(nrelSmdChecklist);
			respMap.put("msg", "Operation Successful");
			respMap.put("success", "1");
		} else {
			respMap.put("msg", "Operation Failed");
			respMap.put("success", "0");
		}
		return respMap;
	}

}

