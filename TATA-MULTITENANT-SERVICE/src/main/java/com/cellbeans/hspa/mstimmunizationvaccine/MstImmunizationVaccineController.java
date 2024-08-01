
package com.cellbeans.hspa.mstimmunizationvaccine;

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
import com.cellbeans.hspa.mstimmunizationvaccine.MstImmunizationVaccine;
import com.cellbeans.hspa.mstimmunizationvaccine.MstImmunizationVaccineRepository;

@RestController
@RequestMapping("/mst_immunization_vaccine")
public class MstImmunizationVaccineController {

	Map<String, String> respMap = new HashMap<String, String>();
	@Autowired
	MstImmunizationVaccineRepository mstImmunizationVaccineRepository;

	@RequestMapping("create")
	public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstImmunizationVaccine mstImmunizationVaccine) {
	TenantContext.setCurrentTenant(tenantName);
		if (mstImmunizationVaccine.getIvName() != null) {
			mstImmunizationVaccineRepository.save(mstImmunizationVaccine);
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
		List<MstImmunizationVaccine> records;
		records = mstImmunizationVaccineRepository.findByIvNameContains(key);
		automap.put("record", records);
		return automap;
	}

	@RequestMapping("byid/{ivId}")
	public MstImmunizationVaccine read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ivId") Long ivId) {
		TenantContext.setCurrentTenant(tenantName);
		MstImmunizationVaccine mstImmunizationVaccine = mstImmunizationVaccineRepository.getById(ivId);
		return mstImmunizationVaccine;
	}

	@RequestMapping("byicid/{ivIcId}")
	public List<MstImmunizationVaccine> readByIvIcId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ivIcId") Long ivIcId) {
		TenantContext.setCurrentTenant(tenantName);
		List<MstImmunizationVaccine> mstImmunizationVaccine = mstImmunizationVaccineRepository.findByIvIcIdIcIdEquals(ivIcId);
		return mstImmunizationVaccine;
	}

	@RequestMapping("update")
	public MstImmunizationVaccine update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstImmunizationVaccine mstImmunizationVaccine) {
	TenantContext.setCurrentTenant(tenantName);
		return mstImmunizationVaccineRepository.save(mstImmunizationVaccine);
	}

	@GetMapping
	@RequestMapping("list")
	public Iterable<MstImmunizationVaccine> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
			@RequestParam(value = "size", required = false, defaultValue = "20") String size,
			@RequestParam(value = "qString", required = false) String qString,
			@RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
			@RequestParam(value = "col", required = false, defaultValue = "ivId") String col) {
			TenantContext.setCurrentTenant(tenantName);

		if (qString == null || qString.equals("")) {
			return mstImmunizationVaccineRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		} else {

			return mstImmunizationVaccineRepository.findByIvNameAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

		}

	}

	@PutMapping("delete/{ivId}")
	public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ivId") Long ivId) {
	TenantContext.setCurrentTenant(tenantName);
		MstImmunizationVaccine mstImmunizationVaccine = mstImmunizationVaccineRepository.getById(ivId);
		if (mstImmunizationVaccine != null) {
			mstImmunizationVaccine.setIsDeleted(true);
			mstImmunizationVaccineRepository.save(mstImmunizationVaccine);
			respMap.put("msg", "Operation Successful");
			respMap.put("success", "1");
		} else {
			respMap.put("msg", "Operation Failed");
			respMap.put("success", "0");
		}
		return respMap;
	}

}

