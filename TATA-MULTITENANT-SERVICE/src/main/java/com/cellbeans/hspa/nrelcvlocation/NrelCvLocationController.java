
package com.cellbeans.hspa.nrelcvlocation;

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
import com.cellbeans.hspa.nrelcvlocation.NrelCvLocation;
import com.cellbeans.hspa.nrelcvlocation.NrelCvLocationRepository;

@RestController
@RequestMapping("/nrel_cv_location")
public class NrelCvLocationController {

	Map<String, String> respMap = new HashMap<String, String>();
	@Autowired
	NrelCvLocationRepository nrelCvLocationRepository;

	@RequestMapping("create")
	public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody NrelCvLocation nrelCvLocation) {
	TenantContext.setCurrentTenant(tenantName);
		if (nrelCvLocation.getTest() != null) {
			nrelCvLocationRepository.save(nrelCvLocation);
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
		List<NrelCvLocation> records;
		records = nrelCvLocationRepository.findByTestContains(key);
		automap.put("record", records);
		return automap;
	}

	@RequestMapping("byid/{clId}")
	public NrelCvLocation read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("clId") Long clId) {
	TenantContext.setCurrentTenant(tenantName);
		NrelCvLocation nrelCvLocation = nrelCvLocationRepository.getById(clId);
		return nrelCvLocation;
	}

	@RequestMapping("update")
	public NrelCvLocation update(@RequestHeader("X-tenantId") String tenantName, @RequestBody NrelCvLocation nrelCvLocation) {
	TenantContext.setCurrentTenant(tenantName);
		return nrelCvLocationRepository.save(nrelCvLocation);
	}

	@GetMapping
	@RequestMapping("list")
	public Iterable<NrelCvLocation> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
			@RequestParam(value = "size", required = false, defaultValue = "20") String size,
			@RequestParam(value = "qString", required = false) String qString,
			@RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
			@RequestParam(value = "col", required = false, defaultValue = "clId") String col) {
			TenantContext.setCurrentTenant(tenantName);

		if (qString == null || qString.equals("")) {
			return nrelCvLocationRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		} else {

			return nrelCvLocationRepository.findByTestAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		}

	}

	@PutMapping("delete/{clId}")
	public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("clId") Long clId) {
	TenantContext.setCurrentTenant(tenantName);
		NrelCvLocation nrelCvLocation = nrelCvLocationRepository.getById(clId);
		if (nrelCvLocation != null) {
			nrelCvLocation.setIsDeleted(true);
			nrelCvLocationRepository.save(nrelCvLocation);
			respMap.put("msg", "Operation Successful");
			respMap.put("success", "1");
		} else {
			respMap.put("msg", "Operation Failed");
			respMap.put("success", "0");
		}
		return respMap;
	}

}

