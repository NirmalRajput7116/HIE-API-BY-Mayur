
package com.cellbeans.hspa.ncliquestion;

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
import com.cellbeans.hspa.ncliquestion.NcliQuestion;
import com.cellbeans.hspa.ncliquestion.NcliQuestionRepository;

@RestController
@RequestMapping("/ncli_question")
public class NcliQuestionController {

	Map<String, String> respMap = new HashMap<String, String>();
	@Autowired
	NcliQuestionRepository ncliQuestionRepository;

	@RequestMapping("create")
	public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody NcliQuestion ncliQuestion) {
	TenantContext.setCurrentTenant(tenantName);
		if (ncliQuestion.getQuestionName() != null) {
			ncliQuestionRepository.save(ncliQuestion);
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
		List<NcliQuestion> records;
		records = ncliQuestionRepository.findByQuestionNameContains(key);
		automap.put("record", records);
		return automap;
	}

	@RequestMapping("byid/{questionId}")
	public NcliQuestion read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("questionId") Long questionId) {
	TenantContext.setCurrentTenant(tenantName);
		NcliQuestion ncliQuestion = ncliQuestionRepository.getById(questionId);
		return ncliQuestion;
	}

	@RequestMapping("update")
	public NcliQuestion update(@RequestHeader("X-tenantId") String tenantName, @RequestBody NcliQuestion ncliQuestion) {
	TenantContext.setCurrentTenant(tenantName);
		return ncliQuestionRepository.save(ncliQuestion);
	}

	@GetMapping
	@RequestMapping("list")
	public Iterable<NcliQuestion> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
									   @RequestParam(value = "size", required = false, defaultValue = "500") String size,
									   @RequestParam(value = "qString", required = false) String qString,
									   @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
									   @RequestParam(value = "col", required = false, defaultValue = "questionId") String col) {
		TenantContext.setCurrentTenant(tenantName);
		if (qString == null || qString.equals("")) {
			return ncliQuestionRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
		} else {
			return ncliQuestionRepository.findByQuestionNameAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
		}
	}

	@GetMapping
	@RequestMapping("byCfId/{cfId}")
	public Iterable<NcliQuestion> listByCfId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
									   @RequestParam(value = "size", required = false, defaultValue = "20") String size,
									   @RequestParam(value = "qString", required = false) String qString,
									   @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
									   @RequestParam(value = "col", required = false, defaultValue = "questionId") String col,
											 @PathVariable("cfId") Long cfId) {
		TenantContext.setCurrentTenant(tenantName);
		return ncliQuestionRepository.findAllByQuestionGroupIdGroupCfIdCfIdAndIsActiveTrueAndIsDeletedFalse(cfId);
	}

	@PutMapping("delete/{questionId}")
	public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("questionId") Long questionId) {
	TenantContext.setCurrentTenant(tenantName);
		NcliQuestion ncliQuestion = ncliQuestionRepository.getById(questionId);
		if (ncliQuestion != null) {
			ncliQuestion.setIsDeleted(true);
			ncliQuestionRepository.save(ncliQuestion);
			respMap.put("msg", "Operation Successful");
			respMap.put("success", "1");
		} else {
			respMap.put("msg", "Operation Failed");
			respMap.put("success", "0");
		}
		return respMap;
	}

}

