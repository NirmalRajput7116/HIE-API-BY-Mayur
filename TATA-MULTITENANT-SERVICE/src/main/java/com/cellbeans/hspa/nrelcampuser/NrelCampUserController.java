
package com.cellbeans.hspa.nrelcampuser;

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
import com.cellbeans.hspa.nrelcampuser.NrelCampUser;
import com.cellbeans.hspa.nrelcampuser.NrelCampUserRepository;

@RestController
@RequestMapping("/nrel_camp_user")
public class NrelCampUserController {

	Map<String, String> respMap = new HashMap<String, String>();
	@Autowired
	NrelCampUserRepository nrelCampUserRepository;

	@RequestMapping("create")
	public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody NrelCampUser nrelCampUser) {
	TenantContext.setCurrentTenant(tenantName);
		if (nrelCampUser.getTest() != null) {
			nrelCampUserRepository.save(nrelCampUser);
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
		List<NrelCampUser> records;
		records = nrelCampUserRepository.findByTestContains(key);
		automap.put("record", records);
		return automap;
	}

	@RequestMapping("byid/{cuId}")
	public NrelCampUser read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("cuId") Long cuId) {
	TenantContext.setCurrentTenant(tenantName);
		NrelCampUser nrelCampUser = nrelCampUserRepository.getById(cuId);
		return nrelCampUser;
	}

	@RequestMapping("update")
	public NrelCampUser update(@RequestHeader("X-tenantId") String tenantName, @RequestBody NrelCampUser nrelCampUser) {
	TenantContext.setCurrentTenant(tenantName);
		return nrelCampUserRepository.save(nrelCampUser);
	}

	@GetMapping
	@RequestMapping("list")
	public Iterable<NrelCampUser> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
			@RequestParam(value = "size", required = false, defaultValue = "20") String size,
			@RequestParam(value = "qString", required = false) String qString,
			@RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
			@RequestParam(value = "col", required = false, defaultValue = "cuId") String col) {
			TenantContext.setCurrentTenant(tenantName);

		if (qString == null || qString.equals("")) {
			return nrelCampUserRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		} else {

			return nrelCampUserRepository.findByTestAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		}

	}

	@PutMapping("delete/{cuId}")
	public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("cuId") Long cuId) {
	TenantContext.setCurrentTenant(tenantName);
		NrelCampUser nrelCampUser = nrelCampUserRepository.getById(cuId);
		if (nrelCampUser != null) {
			nrelCampUser.setIsDeleted(true);
			nrelCampUserRepository.save(nrelCampUser);
			respMap.put("msg", "Operation Successful");
			respMap.put("success", "1");
		} else {
			respMap.put("msg", "Operation Failed");
			respMap.put("success", "0");
		}
		return respMap;
	}

}

