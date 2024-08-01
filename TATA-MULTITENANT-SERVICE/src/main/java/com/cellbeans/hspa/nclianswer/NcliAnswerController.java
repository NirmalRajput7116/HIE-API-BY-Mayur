
package com.cellbeans.hspa.nclianswer;

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
import com.cellbeans.hspa.nclianswer.NcliAnswer;
import com.cellbeans.hspa.nclianswer.NcliAnswerRepository;

@RestController
@RequestMapping("/ncli_answer")
public class NcliAnswerController {

	Map<String, String> respMap = new HashMap<String, String>();
	@Autowired
	NcliAnswerRepository ncliAnswerRepository;

	@RequestMapping("create")
	public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody NcliAnswer ncliAnswer) {
	TenantContext.setCurrentTenant(tenantName);
		if (ncliAnswer.getAnswerName() != null) {
			ncliAnswerRepository.save(ncliAnswer);
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
		List<NcliAnswer> records;
		records = ncliAnswerRepository.findByAnswerNameContains(key);
		automap.put("record", records);
		return automap;
	}

	@RequestMapping("byid/{answerId}")
	public NcliAnswer read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("answerId") Long answerId) {
	TenantContext.setCurrentTenant(tenantName);
		NcliAnswer ncliAnswer = ncliAnswerRepository.getById(answerId);
		return ncliAnswer;
	}

	@RequestMapping("update")
	public NcliAnswer update(@RequestHeader("X-tenantId") String tenantName, @RequestBody NcliAnswer ncliAnswer) {
	TenantContext.setCurrentTenant(tenantName);
		return ncliAnswerRepository.save(ncliAnswer);
	}

	@GetMapping
	@RequestMapping("list")
	public Iterable<NcliAnswer> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
									 @RequestParam(value = "size", required = false, defaultValue = "1000") String size,
									 @RequestParam(value = "qString", required = false) String qString,
									 @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
									 @RequestParam(value = "col", required = false, defaultValue = "answerId") String col) {
		TenantContext.setCurrentTenant(tenantName);
		if (qString == null || qString.equals("")) {
			return ncliAnswerRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
		} else {
			return ncliAnswerRepository.findByAnswerNameAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
		}
	}

	@GetMapping
	@RequestMapping("byCfId/{cfId}")
	public Iterable<NcliAnswer> listByCfId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
									 @RequestParam(value = "size", required = false, defaultValue = "20") String size,
									 @RequestParam(value = "qString", required = false) String qString,
									 @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
									 @RequestParam(value = "col", required = false, defaultValue = "answerId") String col,
										   @PathVariable("cfId") Long cfId) {
		TenantContext.setCurrentTenant(tenantName);
		return ncliAnswerRepository.findAllByAnswerQuestionIdQuestionGroupIdGroupCfIdCfIdAndIsActiveTrueAndIsDeletedFalse(cfId);
	}

	@PutMapping("delete/{answerId}")
	public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("answerId") Long answerId) {
	TenantContext.setCurrentTenant(tenantName);
		NcliAnswer ncliAnswer = ncliAnswerRepository.getById(answerId);
		if (ncliAnswer != null) {
			ncliAnswer.setIsDeleted(true);
			ncliAnswerRepository.save(ncliAnswer);
			respMap.put("msg", "Operation Successful");
			respMap.put("success", "1");
		} else {
			respMap.put("msg", "Operation Failed");
			respMap.put("success", "0");
		}
		return respMap;
	}

}

