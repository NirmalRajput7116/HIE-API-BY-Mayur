
package com.cellbeans.hspa.nmstvillage;

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
import com.cellbeans.hspa.nmstvillage.NmstVillage;
import com.cellbeans.hspa.nmstvillage.NmstVillageRepository;

@RestController
@RequestMapping("/nmst_village")
public class NmstVillageController {

	Map<String, String> respMap = new HashMap<String, String>();
	@Autowired
	NmstVillageRepository nmstVillageRepository;

	@RequestMapping("create")
	public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody NmstVillage nmstVillage) {
	TenantContext.setCurrentTenant(tenantName);
		if (nmstVillage.getVillageName() != null) {
			nmstVillageRepository.save(nmstVillage);
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
		List<NmstVillage> records;
		records = nmstVillageRepository.findByVillageNameContains(key);
		automap.put("record", records);
		return automap;
	}

	@RequestMapping("byid/{villageId}")
	public NmstVillage read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("villageId") Long villageId) {
	TenantContext.setCurrentTenant(tenantName);
		NmstVillage nmstVillage = nmstVillageRepository.getById(villageId);
		return nmstVillage;
	}

	@RequestMapping("update")
	public NmstVillage update(@RequestHeader("X-tenantId") String tenantName, @RequestBody NmstVillage nmstVillage) {
	TenantContext.setCurrentTenant(tenantName);
		return nmstVillageRepository.save(nmstVillage);
	}

	@GetMapping
	@RequestMapping("list")
	public Iterable<NmstVillage> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
			@RequestParam(value = "size", required = false, defaultValue = "20") String size,
			@RequestParam(value = "qString", required = false) String qString,
			@RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
			@RequestParam(value = "col", required = false, defaultValue = "villageId") String col) {
			TenantContext.setCurrentTenant(tenantName);

		if (qString == null || qString.equals("")) {
			return nmstVillageRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		} else {

			return nmstVillageRepository.findByVillageNameAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		}

	}

	@PutMapping("delete/{villageId}")
	public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("villageId") Long villageId) {
	TenantContext.setCurrentTenant(tenantName);
		NmstVillage nmstVillage = nmstVillageRepository.getById(villageId);
		if (nmstVillage != null) {
			nmstVillage.setIsDeleted(true);
			nmstVillageRepository.save(nmstVillage);
			respMap.put("msg", "Operation Successful");
			respMap.put("success", "1");
		} else {
			respMap.put("msg", "Operation Failed");
			respMap.put("success", "0");
		}
		return respMap;
	}

}

