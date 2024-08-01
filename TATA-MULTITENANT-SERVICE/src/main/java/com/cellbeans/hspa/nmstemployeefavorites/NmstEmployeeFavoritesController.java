
package com.cellbeans.hspa.nmstemployeefavorites;

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
import com.cellbeans.hspa.nmstemployeefavorites.NmstEmployeeFavorites;
import com.cellbeans.hspa.nmstemployeefavorites.NmstEmployeeFavoritesRepository;

@RestController
@RequestMapping("/nmst_employee_favorites")
public class NmstEmployeeFavoritesController {

	Map<String, String> respMap = new HashMap<String, String>();
	@Autowired
	NmstEmployeeFavoritesRepository nmstEmployeeFavoritesRepository;

	@RequestMapping("create")
	public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody NmstEmployeeFavorites nmstEmployeeFavorites) {
	TenantContext.setCurrentTenant(tenantName);
		if (nmstEmployeeFavorites.getEfTitle() != null) {
			nmstEmployeeFavoritesRepository.save(nmstEmployeeFavorites);
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
		List<NmstEmployeeFavorites> records;
		records = nmstEmployeeFavoritesRepository.findByEfTitleContains(key);
		automap.put("record", records);
		return automap;
	}

	@RequestMapping("byid/{efId}")
	public NmstEmployeeFavorites read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("efId") Long efId) {
	TenantContext.setCurrentTenant(tenantName);
		NmstEmployeeFavorites nmstEmployeeFavorites = nmstEmployeeFavoritesRepository.getById(efId);
		return nmstEmployeeFavorites;
	}

	@RequestMapping("update")
	public NmstEmployeeFavorites update(@RequestHeader("X-tenantId") String tenantName, @RequestBody NmstEmployeeFavorites nmstEmployeeFavorites) {
	TenantContext.setCurrentTenant(tenantName);
		return nmstEmployeeFavoritesRepository.save(nmstEmployeeFavorites);
	}

	@GetMapping
	@RequestMapping("list")
	public Iterable<NmstEmployeeFavorites> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
			@RequestParam(value = "size", required = false, defaultValue = "20") String size,
			@RequestParam(value = "qString", required = false) String qString,
			@RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
			@RequestParam(value = "col", required = false, defaultValue = "efId") String col) {
			TenantContext.setCurrentTenant(tenantName);

		if (qString == null || qString.equals("")) {
			return nmstEmployeeFavoritesRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		} else {

			return nmstEmployeeFavoritesRepository.findByEfTitleAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		}

	}

	@PutMapping("delete/{efId}")
	public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("efId") Long efId) {
	TenantContext.setCurrentTenant(tenantName);
		NmstEmployeeFavorites nmstEmployeeFavorites = nmstEmployeeFavoritesRepository.getById(efId);
		if (nmstEmployeeFavorites != null) {
			nmstEmployeeFavorites.setIsDeleted(true);
			nmstEmployeeFavoritesRepository.save(nmstEmployeeFavorites);
			respMap.put("msg", "Operation Successful");
			respMap.put("success", "1");
		} else {
			respMap.put("msg", "Operation Failed");
			respMap.put("success", "0");
		}
		return respMap;
	}

}

