
package com.cellbeans.hspa.nmstcampvisit;

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
import com.cellbeans.hspa.nmstcampvisit.NmstCampVisit;
import com.cellbeans.hspa.nmstcampvisit.NmstCampVisitRepository;

@RestController
@RequestMapping("/nmst_camp_visit")
public class NmstCampVisitController {

	Map<String, String> respMap = new HashMap<String, String>();
	@Autowired
	NmstCampVisitRepository nmstCampVisitRepository;

	@RequestMapping("create")
	public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody NmstCampVisit nmstCampVisit) {
	TenantContext.setCurrentTenant(tenantName);
		if (nmstCampVisit.getCvName() != null) {
			nmstCampVisitRepository.save(nmstCampVisit);
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
		List<NmstCampVisit> records;
		records = nmstCampVisitRepository.findByCvNameContains(key);
		automap.put("record", records);
		return automap;
	}

	@RequestMapping("byid/{cvId}")
	public NmstCampVisit read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("cvId") Long cvId) {
	TenantContext.setCurrentTenant(tenantName);
		NmstCampVisit nmstCampVisit = nmstCampVisitRepository.getById(cvId);
		return nmstCampVisit;
	}

	@RequestMapping("bycampid/{bycampid}")
	public List<NmstCampVisit> getCampVisitByCampId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bycampid") Long bycampid) {
	TenantContext.setCurrentTenant(tenantName);
	   List<NmstCampVisit> records;
		records = nmstCampVisitRepository.findByCvCampIdCampIdEqualsAndIsActiveTrueAndIsDeletedFalse(bycampid);
		return records;
	}

	@RequestMapping("update")
	public NmstCampVisit update(@RequestHeader("X-tenantId") String tenantName, @RequestBody NmstCampVisit nmstCampVisit) {
	TenantContext.setCurrentTenant(tenantName);
		return nmstCampVisitRepository.save(nmstCampVisit);
	}

	@GetMapping
	@RequestMapping("list")
	public Iterable<NmstCampVisit> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
			@RequestParam(value = "size", required = false, defaultValue = "20") String size,
			@RequestParam(value = "qString", required = false) String qString,
			@RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
			@RequestParam(value = "col", required = false, defaultValue = "cvId") String col) {
			TenantContext.setCurrentTenant(tenantName);

		if (qString == null || qString.equals("")) {
			return nmstCampVisitRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		} else {

			return nmstCampVisitRepository.findByCvNameAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		}

	}

	@PutMapping("delete/{cvId}")
	public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("cvId") Long cvId) {
	TenantContext.setCurrentTenant(tenantName);
		NmstCampVisit nmstCampVisit = nmstCampVisitRepository.getById(cvId);
		if (nmstCampVisit != null) {
			nmstCampVisit.setIsDeleted(true);
			nmstCampVisitRepository.save(nmstCampVisit);
			respMap.put("msg", "Operation Successful");
			respMap.put("success", "1");
		} else {
			respMap.put("msg", "Operation Failed");
			respMap.put("success", "0");
		}
		return respMap;
	}

}

