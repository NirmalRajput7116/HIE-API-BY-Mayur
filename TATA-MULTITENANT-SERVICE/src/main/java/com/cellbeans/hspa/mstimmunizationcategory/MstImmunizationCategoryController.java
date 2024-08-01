
package com.cellbeans.hspa.mstimmunizationcategory;

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
import com.cellbeans.hspa.mstimmunizationcategory.MstImmunizationCategory;
import com.cellbeans.hspa.mstimmunizationcategory.MstImmunizationCategoryRepository;

@RestController
@RequestMapping("/mst_immunization_category")
public class MstImmunizationCategoryController {

	Map<String, String> respMap = new HashMap<String, String>();
	@Autowired
	MstImmunizationCategoryRepository mstImmunizationCategoryRepository;

	@RequestMapping("create")
	public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstImmunizationCategory mstImmunizationCategory) {
	TenantContext.setCurrentTenant(tenantName);
		if (mstImmunizationCategory.getIcName() != null) {
			mstImmunizationCategoryRepository.save(mstImmunizationCategory);
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
		List<MstImmunizationCategory> records;
		records = mstImmunizationCategoryRepository.findByIcNameContains(key);
		automap.put("record", records);
		return automap;
	}

	@RequestMapping("byid/{icId}")
	public MstImmunizationCategory read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("icId") Long icId) {
	TenantContext.setCurrentTenant(tenantName);
		MstImmunizationCategory mstImmunizationCategory = mstImmunizationCategoryRepository.getById(icId);
		return mstImmunizationCategory;
	}

	@RequestMapping("update")
	public MstImmunizationCategory update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstImmunizationCategory mstImmunizationCategory) {
	TenantContext.setCurrentTenant(tenantName);
		return mstImmunizationCategoryRepository.save(mstImmunizationCategory);
	}

	@GetMapping
	@RequestMapping("list")
	public Iterable<MstImmunizationCategory> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
			@RequestParam(value = "size", required = false, defaultValue = "20") String size,
			@RequestParam(value = "qString", required = false) String qString,
			@RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
			@RequestParam(value = "col", required = false, defaultValue = "icId") String col) {
			TenantContext.setCurrentTenant(tenantName);

		if (qString == null || qString.equals("")) {
			return mstImmunizationCategoryRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		} else {

			return mstImmunizationCategoryRepository.findByIcNameAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		}

	}

	@PutMapping("delete/{icId}")
	public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("icId") Long icId) {
	TenantContext.setCurrentTenant(tenantName);
		MstImmunizationCategory mstImmunizationCategory = mstImmunizationCategoryRepository.getById(icId);
		if (mstImmunizationCategory != null) {
			mstImmunizationCategory.setIsDeleted(true);
			mstImmunizationCategoryRepository.save(mstImmunizationCategory);
			respMap.put("msg", "Operation Successful");
			respMap.put("success", "1");
		} else {
			respMap.put("msg", "Operation Failed");
			respMap.put("success", "0");
		}
		return respMap;
	}

}

