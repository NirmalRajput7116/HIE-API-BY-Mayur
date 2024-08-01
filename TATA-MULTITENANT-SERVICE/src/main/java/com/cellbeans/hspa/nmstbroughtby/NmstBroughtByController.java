
package com.cellbeans.hspa.nmstbroughtby;

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
import com.cellbeans.hspa.nmstbroughtby.NmstBroughtBy;
import com.cellbeans.hspa.nmstbroughtby.NmstBroughtByRepository;

@RestController
@RequestMapping("/nmst_brought_by")
public class NmstBroughtByController {

	Map<String, String> respMap = new HashMap<String, String>();
	@Autowired
	NmstBroughtByRepository nmstBroughtByRepository;

	@RequestMapping("create")
	public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody NmstBroughtBy nmstBroughtBy) {
	TenantContext.setCurrentTenant(tenantName);
		if (nmstBroughtBy.getBbFullname() != null) {
			nmstBroughtByRepository.save(nmstBroughtBy);
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
		List<NmstBroughtBy> records;
		records = nmstBroughtByRepository.findByBbFullnameContains(key);
		automap.put("record", records);
		return automap;
	}

	@RequestMapping("byid/{bbId}")
	public NmstBroughtBy read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bbId") Long bbId) {
	TenantContext.setCurrentTenant(tenantName);
		NmstBroughtBy nmstBroughtBy = nmstBroughtByRepository.getById(bbId);
		return nmstBroughtBy;
	}

	@RequestMapping("update")
	public NmstBroughtBy update(@RequestHeader("X-tenantId") String tenantName, @RequestBody NmstBroughtBy nmstBroughtBy) {
	TenantContext.setCurrentTenant(tenantName);
		return nmstBroughtByRepository.save(nmstBroughtBy);
	}

	@GetMapping
	@RequestMapping("list")
	public Iterable<NmstBroughtBy> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
			@RequestParam(value = "size", required = false, defaultValue = "20") String size,
			@RequestParam(value = "qString", required = false) String qString,
			@RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
			@RequestParam(value = "col", required = false, defaultValue = "bbId") String col) {
			TenantContext.setCurrentTenant(tenantName);

		if (qString == null || qString.equals("")) {
			return nmstBroughtByRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		} else {

			return nmstBroughtByRepository.findByBbFullnameAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		}

	}

	@PutMapping("delete/{bbId}")
	public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bbId") Long bbId) {
	TenantContext.setCurrentTenant(tenantName);
		NmstBroughtBy nmstBroughtBy = nmstBroughtByRepository.getById(bbId);
		if (nmstBroughtBy != null) {
			nmstBroughtBy.setIsDeleted(true);
			nmstBroughtByRepository.save(nmstBroughtBy);
			respMap.put("msg", "Operation Successful");
			respMap.put("success", "1");
		} else {
			respMap.put("msg", "Operation Failed");
			respMap.put("success", "0");
		}
		return respMap;
	}

}

