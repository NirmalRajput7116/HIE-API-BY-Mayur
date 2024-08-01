
package com.cellbeans.hspa.nrelassigndoctor;

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
import com.cellbeans.hspa.nrelassigndoctor.NrelAssignDoctor;
import com.cellbeans.hspa.nrelassigndoctor.NrelAssignDoctorRepository;

@RestController
@RequestMapping("/nrel_assign_doctor")
public class NrelAssignDoctorController {

	Map<String, String> respMap = new HashMap<String, String>();
	@Autowired
	NrelAssignDoctorRepository nrelAssignDoctorRepository;

	@RequestMapping("create")
	public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody NrelAssignDoctor nrelAssignDoctor) {
	TenantContext.setCurrentTenant(tenantName);
		if (nrelAssignDoctor.getTest() != null) {
			nrelAssignDoctorRepository.save(nrelAssignDoctor);
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
		List<NrelAssignDoctor> records;
		records = nrelAssignDoctorRepository.findByTestContains(key);
		automap.put("record", records);
		return automap;
	}

	@RequestMapping("byid/{adId}")
	public NrelAssignDoctor read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("adId") Long adId) {
	TenantContext.setCurrentTenant(tenantName);
		NrelAssignDoctor nrelAssignDoctor = nrelAssignDoctorRepository.getById(adId);
		return nrelAssignDoctor;
	}

	@RequestMapping("update")
	public NrelAssignDoctor update(@RequestHeader("X-tenantId") String tenantName, @RequestBody NrelAssignDoctor nrelAssignDoctor) {
	TenantContext.setCurrentTenant(tenantName);
		return nrelAssignDoctorRepository.save(nrelAssignDoctor);
	}

	@GetMapping
	@RequestMapping("list")
	public Iterable<NrelAssignDoctor> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
			@RequestParam(value = "size", required = false, defaultValue = "20") String size,
			@RequestParam(value = "qString", required = false) String qString,
			@RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
			@RequestParam(value = "col", required = false, defaultValue = "adId") String col) {
			TenantContext.setCurrentTenant(tenantName);

		if (qString == null || qString.equals("")) {
			return nrelAssignDoctorRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		} else {

			return nrelAssignDoctorRepository.findByTestAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		}

	}

	@PutMapping("delete/{adId}")
	public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("adId") Long adId) {
	TenantContext.setCurrentTenant(tenantName);
		NrelAssignDoctor nrelAssignDoctor = nrelAssignDoctorRepository.getById(adId);
		if (nrelAssignDoctor != null) {
			nrelAssignDoctor.setIsDeleted(true);
			nrelAssignDoctorRepository.save(nrelAssignDoctor);
			respMap.put("msg", "Operation Successful");
			respMap.put("success", "1");
		} else {
			respMap.put("msg", "Operation Failed");
			respMap.put("success", "0");
		}
		return respMap;
	}

}

