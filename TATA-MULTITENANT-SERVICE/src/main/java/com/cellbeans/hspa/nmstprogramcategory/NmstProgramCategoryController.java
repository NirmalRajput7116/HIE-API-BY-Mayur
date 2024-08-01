
package com.cellbeans.hspa.nmstprogramcategory;

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
import com.cellbeans.hspa.nmstprogramcategory.NmstProgramCategory;
import com.cellbeans.hspa.nmstprogramcategory.NmstProgramCategoryRepository;

@RestController
@RequestMapping("/nmst_program_category")
public class NmstProgramCategoryController {

	Map<String, String> respMap = new HashMap<String, String>();
	@Autowired
	NmstProgramCategoryRepository nmstProgramCategoryRepository;

	@RequestMapping("create")
	public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody NmstProgramCategory nmstProgramCategory) {
	TenantContext.setCurrentTenant(tenantName);
		if (nmstProgramCategory.getPcName() != null) {
			nmstProgramCategoryRepository.save(nmstProgramCategory);
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
		List<NmstProgramCategory> records;
		records = nmstProgramCategoryRepository.findByPcNameContains(key);
		automap.put("record", records);
		return automap;
	}

	@RequestMapping("byid/{pcId}")
	public NmstProgramCategory read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pcId") Long pcId) {
	TenantContext.setCurrentTenant(tenantName);
		NmstProgramCategory nmstProgramCategory = nmstProgramCategoryRepository.getById(pcId);
		return nmstProgramCategory;
	}

	@RequestMapping("update")
	public NmstProgramCategory update(@RequestHeader("X-tenantId") String tenantName, @RequestBody NmstProgramCategory nmstProgramCategory) {
	TenantContext.setCurrentTenant(tenantName);
		return nmstProgramCategoryRepository.save(nmstProgramCategory);
	}

	@GetMapping
	@RequestMapping("list")
	public Iterable<NmstProgramCategory> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
			@RequestParam(value = "size", required = false, defaultValue = "20") String size,
			@RequestParam(value = "qString", required = false) String qString,
			@RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
			@RequestParam(value = "col", required = false, defaultValue = "pcId") String col) {
			TenantContext.setCurrentTenant(tenantName);

		if (qString == null || qString.equals("")) {
			return nmstProgramCategoryRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		} else {

			return nmstProgramCategoryRepository.findByPcNameAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		}

	}

	@PutMapping("delete/{pcId}")
	public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("pcId") Long pcId) {
	TenantContext.setCurrentTenant(tenantName);
		NmstProgramCategory nmstProgramCategory = nmstProgramCategoryRepository.getById(pcId);
		if (nmstProgramCategory != null) {
			nmstProgramCategory.setIsDeleted(true);
			nmstProgramCategoryRepository.save(nmstProgramCategory);
			respMap.put("msg", "Operation Successful");
			respMap.put("success", "1");
		} else {
			respMap.put("msg", "Operation Failed");
			respMap.put("success", "0");
		}
		return respMap;
	}

}

