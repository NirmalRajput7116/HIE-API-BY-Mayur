
package com.cellbeans.hspa.nmstbus;

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
import com.cellbeans.hspa.nmstbus.NmstBus;
import com.cellbeans.hspa.nmstbus.NmstBusRepository;

@RestController
@RequestMapping("/nmst_bus")
public class NmstBusController {

	Map<String, String> respMap = new HashMap<String, String>();
	@Autowired
	NmstBusRepository nmstBusRepository;

	@RequestMapping("create")
	public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody NmstBus nmstBus) {
	TenantContext.setCurrentTenant(tenantName);
		if (nmstBus.getBusName() != null) {
			nmstBusRepository.save(nmstBus);
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
		List<NmstBus> records;
		records = nmstBusRepository.findByBusNameContains(key);
		automap.put("record", records);
		return automap;
	}

	@RequestMapping("byid/{busId}")
	public NmstBus read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("busId") Long busId) {
	TenantContext.setCurrentTenant(tenantName);
		NmstBus nmstBus = nmstBusRepository.getById(busId);
		return nmstBus;
	}

	@RequestMapping("update")
	public NmstBus update(@RequestHeader("X-tenantId") String tenantName, @RequestBody NmstBus nmstBus) {
	TenantContext.setCurrentTenant(tenantName);
		return nmstBusRepository.save(nmstBus);
	}

	@GetMapping
	@RequestMapping("list")
	public Iterable<NmstBus> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
			@RequestParam(value = "size", required = false, defaultValue = "20") String size,
			@RequestParam(value = "qString", required = false) String qString,
			@RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
			@RequestParam(value = "col", required = false, defaultValue = "busId") String col) {
			TenantContext.setCurrentTenant(tenantName);

		if (qString == null || qString.equals("")) {
			return nmstBusRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		} else {

			return nmstBusRepository.findByBusNameAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		}

	}

	@PutMapping("delete/{busId}")
	public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("busId") Long busId) {
	TenantContext.setCurrentTenant(tenantName);
		NmstBus nmstBus = nmstBusRepository.getById(busId);
		if (nmstBus != null) {
			nmstBus.setIsDeleted(true);
			nmstBusRepository.save(nmstBus);
			respMap.put("msg", "Operation Successful");
			respMap.put("success", "1");
		} else {
			respMap.put("msg", "Operation Failed");
			respMap.put("success", "0");
		}
		return respMap;
	}

}

