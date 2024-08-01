
package com.cellbeans.hspa.nmstvisitnature;

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
import com.cellbeans.hspa.nmstvisitnature.NmstVisitNature;
import com.cellbeans.hspa.nmstvisitnature.NmstVisitNatureRepository;

@RestController
@RequestMapping("/nmst_visit_nature")
public class NmstVisitNatureController {

	Map<String, String> respMap = new HashMap<String, String>();
	@Autowired
	NmstVisitNatureRepository nmstVisitNatureRepository;

	@RequestMapping("create")
	public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody NmstVisitNature nmstVisitNature) {
	TenantContext.setCurrentTenant(tenantName);
		if (nmstVisitNature.getVnName() != null) {
			nmstVisitNatureRepository.save(nmstVisitNature);
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
		List<NmstVisitNature> records;
		records = nmstVisitNatureRepository.findByVnNameContains(key);
		automap.put("record", records);
		return automap;
	}

	@RequestMapping("byid/{vnId}")
	public NmstVisitNature read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vnId") Long vnId) {
	TenantContext.setCurrentTenant(tenantName);
		NmstVisitNature nmstVisitNature = nmstVisitNatureRepository.getById(vnId);
		return nmstVisitNature;
	}

	@RequestMapping("update")
	public NmstVisitNature update(@RequestHeader("X-tenantId") String tenantName, @RequestBody NmstVisitNature nmstVisitNature) {
	TenantContext.setCurrentTenant(tenantName);
		return nmstVisitNatureRepository.save(nmstVisitNature);
	}

	@GetMapping
	@RequestMapping("list")
	public Iterable<NmstVisitNature> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
			@RequestParam(value = "size", required = false, defaultValue = "20") String size,
			@RequestParam(value = "qString", required = false) String qString,
			@RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
			@RequestParam(value = "col", required = false, defaultValue = "vnId") String col) {
			TenantContext.setCurrentTenant(tenantName);

		if (qString == null || qString.equals("")) {
			return nmstVisitNatureRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		} else {

			return nmstVisitNatureRepository.findByVnNameAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		}

	}

	@PutMapping("delete/{vnId}")
	public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vnId") Long vnId) {
	TenantContext.setCurrentTenant(tenantName);
		NmstVisitNature nmstVisitNature = nmstVisitNatureRepository.getById(vnId);
		if (nmstVisitNature != null) {
			nmstVisitNature.setIsDeleted(true);
			nmstVisitNatureRepository.save(nmstVisitNature);
			respMap.put("msg", "Operation Successful");
			respMap.put("success", "1");
		} else {
			respMap.put("msg", "Operation Failed");
			respMap.put("success", "0");
		}
		return respMap;
	}

}

