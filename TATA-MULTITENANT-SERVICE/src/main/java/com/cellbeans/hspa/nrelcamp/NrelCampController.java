
package com.cellbeans.hspa.nrelcamp;

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
import com.cellbeans.hspa.nrelcamp.NrelCamp;
import com.cellbeans.hspa.nrelcamp.NrelCampRepository;

@RestController
@RequestMapping("/nrel_camp")
public class NrelCampController {

	Map<String, String> respMap = new HashMap<String, String>();
	@Autowired
	NrelCampRepository nrelCampRepository;

	@RequestMapping("create")
	public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody NrelCamp nrelCamp) {
	TenantContext.setCurrentTenant(tenantName);
		if (nrelCamp.getTest() != null) {
			nrelCampRepository.save(nrelCamp);
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
		List<NrelCamp> records;
		records = nrelCampRepository.findByTestContains(key);
		automap.put("record", records);
		return automap;
	}

	@RequestMapping("byid/{campId}")
	public NrelCamp read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("campId") Long campId) {
	TenantContext.setCurrentTenant(tenantName);
		NrelCamp nrelCamp = nrelCampRepository.getById(campId);
		return nrelCamp;
	}

	@RequestMapping("update")
	public NrelCamp update(@RequestHeader("X-tenantId") String tenantName, @RequestBody NrelCamp nrelCamp) {
	TenantContext.setCurrentTenant(tenantName);
		return nrelCampRepository.save(nrelCamp);
	}

	@GetMapping
	@RequestMapping("list")
	public Iterable<NrelCamp> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
			@RequestParam(value = "size", required = false, defaultValue = "20") String size,
			@RequestParam(value = "qString", required = false) String qString,
			@RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
			@RequestParam(value = "col", required = false, defaultValue = "campId") String col) {
			TenantContext.setCurrentTenant(tenantName);

		if (qString == null || qString.equals("")) {
			return nrelCampRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		} else {

			return nrelCampRepository.findByTestAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		}

	}

	@PutMapping("delete/{campId}")
	public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("campId") Long campId) {
	TenantContext.setCurrentTenant(tenantName);
		NrelCamp nrelCamp = nrelCampRepository.getById(campId);
		if (nrelCamp != null) {
			nrelCamp.setIsDeleted(true);
			nrelCampRepository.save(nrelCamp);
			respMap.put("msg", "Operation Successful");
			respMap.put("success", "1");
		} else {
			respMap.put("msg", "Operation Failed");
			respMap.put("success", "0");
		}
		return respMap;
	}

}

