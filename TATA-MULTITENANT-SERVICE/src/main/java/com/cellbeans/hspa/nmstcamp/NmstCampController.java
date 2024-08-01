
package com.cellbeans.hspa.nmstcamp;

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
import com.cellbeans.hspa.nmstcamp.NmstCamp;
import com.cellbeans.hspa.nmstcamp.NmstCampRepository;

@RestController
@RequestMapping("/nmst_camp")
public class NmstCampController {

	Map<String, String> respMap = new HashMap<String, String>();
	@Autowired
	NmstCampRepository nmstCampRepository;

	@RequestMapping("create")
	public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody NmstCamp nmstCamp) {
	TenantContext.setCurrentTenant(tenantName);
		if (nmstCamp.getCampName() != null) {
			nmstCampRepository.save(nmstCamp);
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
		List<NmstCamp> records;
		records = nmstCampRepository.findByCampNameContains(key);
		automap.put("record", records);
		return automap;
	}

	@RequestMapping("byid/{campId}")
	public NmstCamp read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("campId") Long campId) {
	TenantContext.setCurrentTenant(tenantName);
		NmstCamp nmstCamp = nmstCampRepository.getById(campId);
		return nmstCamp;
	}

	@RequestMapping("update")
	public NmstCamp update(@RequestHeader("X-tenantId") String tenantName, @RequestBody NmstCamp nmstCamp) {
	TenantContext.setCurrentTenant(tenantName);
		return nmstCampRepository.save(nmstCamp);
	}

	@GetMapping
	@RequestMapping("list")
	public Iterable<NmstCamp> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
			@RequestParam(value = "size", required = false, defaultValue = "20") String size,
			@RequestParam(value = "qString", required = false) String qString,
			@RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
			@RequestParam(value = "col", required = false, defaultValue = "campId") String col) {
			TenantContext.setCurrentTenant(tenantName);

		if (qString == null || qString.equals("")) {
			return nmstCampRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		} else {

			return nmstCampRepository.findByCampNameAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		}

	}

	@PutMapping("delete/{campId}")
	public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("campId") Long campId) {
	TenantContext.setCurrentTenant(tenantName);
		NmstCamp nmstCamp = nmstCampRepository.getById(campId);
		if (nmstCamp != null) {
			nmstCamp.setIsDeleted(true);
			nmstCampRepository.save(nmstCamp);
			respMap.put("msg", "Operation Successful");
			respMap.put("success", "1");
		} else {
			respMap.put("msg", "Operation Failed");
			respMap.put("success", "0");
		}
		return respMap;
	}

}

