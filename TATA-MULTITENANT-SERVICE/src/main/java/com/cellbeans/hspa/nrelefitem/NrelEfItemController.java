
package com.cellbeans.hspa.nrelefitem;

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
import com.cellbeans.hspa.nrelefitem.NrelEfItem;
import com.cellbeans.hspa.nrelefitem.NrelEfItemRepository;

@RestController
@RequestMapping("/nrel_ef_item")
public class NrelEfItemController {

	Map<String, String> respMap = new HashMap<String, String>();
	@Autowired
	NrelEfItemRepository nrelEfItemRepository;

	@RequestMapping("create")
	public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody NrelEfItem nrelEfItem) {
	TenantContext.setCurrentTenant(tenantName);
		if (nrelEfItem.getTest() != null) {
			nrelEfItemRepository.save(nrelEfItem);
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
		List<NrelEfItem> records;
		records = nrelEfItemRepository.findByTestContains(key);
		automap.put("record", records);
		return automap;
	}

	@RequestMapping("byid/{eiId}")
	public NrelEfItem read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("eiId") Long eiId) {
	TenantContext.setCurrentTenant(tenantName);
		NrelEfItem nrelEfItem = nrelEfItemRepository.getById(eiId);
		return nrelEfItem;
	}

	@RequestMapping("update")
	public NrelEfItem update(@RequestHeader("X-tenantId") String tenantName, @RequestBody NrelEfItem nrelEfItem) {
	TenantContext.setCurrentTenant(tenantName);
		return nrelEfItemRepository.save(nrelEfItem);
	}

	@GetMapping
	@RequestMapping("list")
	public Iterable<NrelEfItem> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
			@RequestParam(value = "size", required = false, defaultValue = "20") String size,
			@RequestParam(value = "qString", required = false) String qString,
			@RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
			@RequestParam(value = "col", required = false, defaultValue = "eiId") String col) {
			TenantContext.setCurrentTenant(tenantName);

		if (qString == null || qString.equals("")) {
			return nrelEfItemRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		} else {

			return nrelEfItemRepository.findByTestAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		}

	}

	@PutMapping("delete/{eiId}")
	public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("eiId") Long eiId) {
	TenantContext.setCurrentTenant(tenantName);
		NrelEfItem nrelEfItem = nrelEfItemRepository.getById(eiId);
		if (nrelEfItem != null) {
			nrelEfItem.setIsDeleted(true);
			nrelEfItemRepository.save(nrelEfItem);
			respMap.put("msg", "Operation Successful");
			respMap.put("success", "1");
		} else {
			respMap.put("msg", "Operation Failed");
			respMap.put("success", "0");
		}
		return respMap;
	}

}

