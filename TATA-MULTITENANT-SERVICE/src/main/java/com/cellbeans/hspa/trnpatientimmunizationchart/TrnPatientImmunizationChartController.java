
package com.cellbeans.hspa.trnpatientimmunizationchart;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mststaff.MstStaff;
import org.exolab.castor.types.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;
import com.cellbeans.hspa.trnpatientimmunizationchart.TrnPatientImmunizationChart;
import com.cellbeans.hspa.trnpatientimmunizationchart.TrnPatientImmunizationChartRepository;

@RestController
@RequestMapping("/trn_patient_immunization_chart")
public class TrnPatientImmunizationChartController {

	Map<String, String> respMap = new HashMap<String, String>();
	@Autowired
	TrnPatientImmunizationChartRepository trnPatientImmunizationChartRepository;

	@RequestMapping("create")
	public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnPatientImmunizationChart trnPatientImmunizationChart) {
	TenantContext.setCurrentTenant(tenantName);
		if (trnPatientImmunizationChart.getPicIvId() != null) {
			trnPatientImmunizationChartRepository.save(trnPatientImmunizationChart);
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
		List<TrnPatientImmunizationChart> records;
		records = trnPatientImmunizationChartRepository.findByTestContains(key);
		automap.put("record", records);
		return automap;
	}

	@RequestMapping("byid/{picId}")
	public TrnPatientImmunizationChart read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("picId") Long picId) {
		TenantContext.setCurrentTenant(tenantName);
		TrnPatientImmunizationChart trnPatientImmunizationChart = trnPatientImmunizationChartRepository.getById(picId);
		return trnPatientImmunizationChart;
	}

	@RequestMapping("bypiid/{piId}")
	public List<TrnPatientImmunizationChart> readByPiId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("piId") Long piId) {
		TenantContext.setCurrentTenant(tenantName);
		List<TrnPatientImmunizationChart> trnPatientImmunizationChart = trnPatientImmunizationChartRepository.findByPicPiIdPiIdEquals(piId);
		return trnPatientImmunizationChart;
	}

	@RequestMapping("update")
	public TrnPatientImmunizationChart update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnPatientImmunizationChart trnPatientImmunizationChart) {
		TenantContext.setCurrentTenant(tenantName);
		return trnPatientImmunizationChartRepository.save(trnPatientImmunizationChart);
	}

//	@RequestMapping("updateDateOfVaccinationTakenAndIsTakenStatus/{picId}/{picIsTaken}/{picDateOfVaccinationTaken}")
//	public TrnPatientImmunizationChart updateDateOfVaccinationTakenAndIsTakenStatus(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnPatientImmunizationChart trnPatientImmunizationChart) {
//		TenantContext.setCurrentTenant(tenantName);
//		return trnPatientImmunizationChartRepository.save(trnPatientImmunizationChart);
//	}

	@GetMapping
	@RequestMapping("updateDateOfVaccinationTakenAndIsTakenStatus/{picId}/{picIsTaken}/{picDateOfVaccinationTaken}/{picAgeOf}/{picDateOfNextVisit}/{picStaffId}")
	public int updateDateOfVaccinationTakenAndIsTakenStatus(@RequestHeader("X-tenantId") String tenantName, @PathVariable("picId") Long picId, @PathVariable("picIsTaken") Boolean picIsTaken, @PathVariable("picDateOfVaccinationTaken") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date picDateOfVaccinationTaken, @PathVariable("picAgeOf") String picAgeOf, @PathVariable("picDateOfNextVisit") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date picDateOfNextVisit, @PathVariable("picStaffId") Long picStaffId) {
		TenantContext.setCurrentTenant(tenantName);
//		Date date = new Date(); // java.util.Date; - This date has both the date and time in it already.
//		DateTime dateTime = new DateTime(picDateOfVaccinationTaken);
		return trnPatientImmunizationChartRepository.updateDateOfVaccinationTakenAndIsTakenStatus(picId, picIsTaken, picDateOfVaccinationTaken, picAgeOf, picDateOfNextVisit, picStaffId);
	}

	@GetMapping
	@RequestMapping("list")
	public Iterable<TrnPatientImmunizationChart> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
			@RequestParam(value = "size", required = false, defaultValue = "20") String size,
			@RequestParam(value = "qString", required = false) String qString,
			@RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
			@RequestParam(value = "col", required = false, defaultValue = "picId") String col) {
		TenantContext.setCurrentTenant(tenantName);
		if (qString == null || qString.equals("")) {
			return trnPatientImmunizationChartRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
		} else {
			return trnPatientImmunizationChartRepository.findByTestAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
		}
	}

	@PutMapping("delete/{picId}")
	public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("picId") Long picId) {
	TenantContext.setCurrentTenant(tenantName);
		TrnPatientImmunizationChart trnPatientImmunizationChart = trnPatientImmunizationChartRepository.getById(picId);
		if (trnPatientImmunizationChart != null) {
			trnPatientImmunizationChart.setIsDeleted(true);
			trnPatientImmunizationChartRepository.save(trnPatientImmunizationChart);
			respMap.put("msg", "Operation Successful");
			respMap.put("success", "1");
		} else {
			respMap.put("msg", "Operation Failed");
			respMap.put("success", "0");
		}
		return respMap;
	}

}

