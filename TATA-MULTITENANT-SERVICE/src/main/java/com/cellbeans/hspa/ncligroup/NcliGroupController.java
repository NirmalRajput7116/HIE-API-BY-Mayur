
package com.cellbeans.hspa.ncligroup;

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
import com.cellbeans.hspa.ncligroup.NcliGroup;
import com.cellbeans.hspa.ncligroup.NcliGroupRepository;

@RestController
@RequestMapping("/ncli_group")
public class NcliGroupController {

	Map<String, String> respMap = new HashMap<String, String>();
	@Autowired
	NcliGroupRepository ncliGroupRepository;

	@RequestMapping("create")
	public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody NcliGroup ncliGroup) {
	TenantContext.setCurrentTenant(tenantName);
		if (ncliGroup.getGroupName() != null) {
			ncliGroupRepository.save(ncliGroup);
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
		List<NcliGroup> records;
		records = ncliGroupRepository.findByGroupNameContains(key);
		automap.put("record", records);
		return automap;
	}

	@RequestMapping("byid/{groupId}")
	public NcliGroup read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("groupId") Long groupId) {
	TenantContext.setCurrentTenant(tenantName);
		NcliGroup ncliGroup = ncliGroupRepository.getById(groupId);
		return ncliGroup;
	}

	@RequestMapping("update")
	public NcliGroup update(@RequestHeader("X-tenantId") String tenantName, @RequestBody NcliGroup ncliGroup) {
	TenantContext.setCurrentTenant(tenantName);
		return ncliGroupRepository.save(ncliGroup);
	}

	@GetMapping
	@RequestMapping("list")
	public Iterable<NcliGroup> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
									@RequestParam(value = "size", required = false, defaultValue = "500") String size,
									@RequestParam(value = "qString", required = false) String qString,
									@RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
									@RequestParam(value = "col", required = false, defaultValue = "groupId") String col) {
		TenantContext.setCurrentTenant(tenantName);

		if (qString == null || qString.equals("")) {
			return ncliGroupRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		} else {

			return ncliGroupRepository.findByGroupNameAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		}
	}

	@GetMapping
	@RequestMapping("byCfId/{groupCfId}")
	public Iterable<NcliGroup> listByCfId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
									@RequestParam(value = "size", required = false, defaultValue = "20") String size,
									@RequestParam(value = "qString", required = false) String qString,
									@RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
									@RequestParam(value = "col", required = false, defaultValue = "groupId") String col,
										  @PathVariable("groupCfId") Long groupCfId) {
		TenantContext.setCurrentTenant(tenantName);
		System.out.println(groupCfId);
		return ncliGroupRepository.findAllByGroupCfIdCfIdAndIsActiveTrueAndIsDeletedFalse(groupCfId);
	}

	@PutMapping("delete/{groupId}")
	public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("groupId") Long groupId) {
	TenantContext.setCurrentTenant(tenantName);
		NcliGroup ncliGroup = ncliGroupRepository.getById(groupId);
		if (ncliGroup != null) {
			ncliGroup.setIsDeleted(true);
			ncliGroupRepository.save(ncliGroup);
			respMap.put("msg", "Operation Successful");
			respMap.put("success", "1");
		} else {
			respMap.put("msg", "Operation Failed");
			respMap.put("success", "0");
		}
		return respMap;
	}

}

