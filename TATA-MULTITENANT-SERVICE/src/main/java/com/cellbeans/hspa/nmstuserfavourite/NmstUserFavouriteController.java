
package com.cellbeans.hspa.nmstuserfavourite;

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
import com.cellbeans.hspa.nmstuserfavourite.NmstUserFavourite;
import com.cellbeans.hspa.nmstuserfavourite.NmstUserFavouriteRepository;

@RestController
@RequestMapping("/nmst_user_favourite")
public class NmstUserFavouriteController {

	Map<String, String> respMap = new HashMap<String, String>();
	@Autowired
	NmstUserFavouriteRepository nmstUserFavouriteRepository;

	@RequestMapping("create")
	public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody NmstUserFavourite nmstUserFavourite) {
	TenantContext.setCurrentTenant(tenantName);
		if (nmstUserFavourite.getUfTitle() != null) {
			nmstUserFavouriteRepository.save(nmstUserFavourite);
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
		List<NmstUserFavourite> records;
		records = nmstUserFavouriteRepository.findByUfTitleContains(key);
		automap.put("record", records);
		return automap;
	}

	@RequestMapping("byid/{ufId}")
	public NmstUserFavourite read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ufId") Long ufId) {
	TenantContext.setCurrentTenant(tenantName);
		NmstUserFavourite nmstUserFavourite = nmstUserFavouriteRepository.getById(ufId);
		return nmstUserFavourite;
	}

	@RequestMapping("update")
	public NmstUserFavourite update(@RequestHeader("X-tenantId") String tenantName, @RequestBody NmstUserFavourite nmstUserFavourite) {
	TenantContext.setCurrentTenant(tenantName);
		return nmstUserFavouriteRepository.save(nmstUserFavourite);
	}

	@GetMapping
	@RequestMapping("list")
	public Iterable<NmstUserFavourite> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
			@RequestParam(value = "size", required = false, defaultValue = "20") String size,
			@RequestParam(value = "qString", required = false) String qString,
			@RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
			@RequestParam(value = "col", required = false, defaultValue = "ufId") String col) {
			TenantContext.setCurrentTenant(tenantName);

		if (qString == null || qString.equals("")) {
			return nmstUserFavouriteRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		} else {

			return nmstUserFavouriteRepository.findByUfTitleAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		}

	}

	@PutMapping("delete/{ufId}")
	public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ufId") Long ufId) {
	TenantContext.setCurrentTenant(tenantName);
		NmstUserFavourite nmstUserFavourite = nmstUserFavouriteRepository.getById(ufId);
		if (nmstUserFavourite != null) {
			nmstUserFavourite.setIsDeleted(true);
			nmstUserFavouriteRepository.save(nmstUserFavourite);
			respMap.put("msg", "Operation Successful");
			respMap.put("success", "1");
		} else {
			respMap.put("msg", "Operation Failed");
			respMap.put("success", "0");
		}
		return respMap;
	}

}

