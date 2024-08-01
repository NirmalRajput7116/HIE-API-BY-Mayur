
package com.cellbeans.hspa.nclivisitquestionanswer;

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
import com.cellbeans.hspa.nclivisitquestionanswer.NcliVisitQuestionAnswer;
import com.cellbeans.hspa.nclivisitquestionanswer.NcliVisitQuestionAnswerRepository;

@RestController
@RequestMapping("/ncli_visit_question_answer")
public class NcliVisitQuestionAnswerController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    NcliVisitQuestionAnswerRepository ncliVisitQuestionAnswerRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody NcliVisitQuestionAnswerDto ncliVisitQuestionAnswerDto) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("I am add call");
        for (NcliVisitQuestionAnswer singleObject : ncliVisitQuestionAnswerDto.getListQuestionAnswer()) {
            ncliVisitQuestionAnswerRepository.save(singleObject);
        }
        respMap.put("success", "1");
        respMap.put("msg", "Added Successfully");
        return respMap;
//		if (ncliVisitQuestionAnswer.getTest() != null) {
//			ncliVisitQuestionAnswerRepository.save(ncliVisitQuestionAnswer);
//			respMap.put("success", "1");
//			respMap.put("msg", "Added Successfully");
//			return respMap;
//		} else {
//			respMap.put("success", "0");
//			respMap.put("msg", "Failed To Add Null Field");
//			return respMap;
//		}
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<NcliVisitQuestionAnswer> records;
        records = ncliVisitQuestionAnswerRepository.findByTestContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{vqaId}")
    public NcliVisitQuestionAnswer read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vqaId") Long vqaId) {
        TenantContext.setCurrentTenant(tenantName);
        NcliVisitQuestionAnswer ncliVisitQuestionAnswer = ncliVisitQuestionAnswerRepository.getById(vqaId);
        return ncliVisitQuestionAnswer;
    }

    @RequestMapping("filledClinicalFormByVisitId/{visitId}")
    public List<NcliVisitQuestionAnswerFilledDto> readFilledClinicalFormByVisitId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("visitId") Long visitId) {
        TenantContext.setCurrentTenant(tenantName);
        List<NcliVisitQuestionAnswerFilledDto> ncliVisitQuestionAnswerFilledList = ncliVisitQuestionAnswerRepository.getAllFilledByVisitIdWithGroup(visitId);
        return ncliVisitQuestionAnswerFilledList;
    }

    //Nayan
    @RequestMapping("filledClinicalFormByPatientId/{patientId}")
    public List<NcliVisitQuestionAnswerFilledDto> readFilledClinicalFormByPatientId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("patientId") Long patientId) {
        TenantContext.setCurrentTenant(tenantName);
        List<NcliVisitQuestionAnswerFilledDto> ncliVisitQuestionAnswerFilledList = ncliVisitQuestionAnswerRepository.getAllFilledByPatientId(patientId);
        return ncliVisitQuestionAnswerFilledList;
    }


    @RequestMapping("byVisitIdAndCfId/{vqaVisitId}/{cfId}")
    public NcliVisitQuestionAnswerDto readByVqaIdCfId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vqaVisitId") int vqaVisitId, @PathVariable("cfId") Long cfId) {
        TenantContext.setCurrentTenant(tenantName);
        NcliVisitQuestionAnswerDto ncliVisitQuestionAnswerDto = new NcliVisitQuestionAnswerDto();
        ncliVisitQuestionAnswerDto.setListQuestionAnswer(ncliVisitQuestionAnswerRepository.findAllByVqaVisitIdAndVqaQuestionIdQuestionGroupIdGroupCfIdCfIdAndIsActiveTrueAndIsDeletedFalse(vqaVisitId, cfId));
        return ncliVisitQuestionAnswerDto;
//		return ncliVisitQuestionAnswer;list
    }

    @RequestMapping("byVisitIdAndCfId/{vqaVisitId}/{cfId}/{isOutbound}")
    public NcliVisitQuestionAnswerDto readByVqaIdCfId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vqaVisitId") int vqaVisitId, @PathVariable("cfId") Long cfId, @PathVariable("isOutbound") Boolean isOutbound) {
        TenantContext.setCurrentTenant(tenantName);
        NcliVisitQuestionAnswerDto ncliVisitQuestionAnswerDto = new NcliVisitQuestionAnswerDto();
        if (isOutbound.equals(false)) {

            ncliVisitQuestionAnswerDto.setListQuestionAnswer(ncliVisitQuestionAnswerRepository.findAllByVqaVisitIdAndVqaQuestionIdQuestionGroupIdGroupCfIdCfIdAndIsActiveTrueAndIsDeletedFalseAndVqaIsOutBoundFalse(vqaVisitId, cfId));
        } else {
            ncliVisitQuestionAnswerDto.setListQuestionAnswer(ncliVisitQuestionAnswerRepository.findAllByVqaVisitIdAndVqaQuestionIdQuestionGroupIdGroupCfIdCfIdAndIsActiveTrueAndIsDeletedFalseAndVqaIsOutBoundTrue(vqaVisitId, cfId));
        }
        return ncliVisitQuestionAnswerDto;
//		return ncliVisitQuestionAnswer;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody NcliVisitQuestionAnswerDto ncliVisitQuestionAnswerDto) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("CF ID IS " + ncliVisitQuestionAnswerDto.getListQuestionAnswer().get(0).getCfId());
        ncliVisitQuestionAnswerRepository.deletePreviousAnswers(ncliVisitQuestionAnswerDto.getListQuestionAnswer().get(0).getCfId());

//		System.out.println(ncliVisitQuestionAnswer.toString());
//		return ncliVisitQuestionAnswerRepository.save(ncliVisitQuestionAnswer);
        System.out.println("I am edit call");
//		for(NcliVisitQuestionAnswer singleObject: ncliVisitQuestionAnswerDto.getListQuestionAnswer()) {
//			ncliVisitQuestionAnswerRepository.findByVqaQuestionIdQuestionIdEquals(singleObject.getVqaQuestionId().getQuestionId());
//			ncliVisitQuestionAnswerRepository.delete(singleObject);
//		}
        for (NcliVisitQuestionAnswer singleObject : ncliVisitQuestionAnswerDto.getListQuestionAnswer()) {
            ncliVisitQuestionAnswerRepository.save(singleObject);
        }
        respMap.put("success", "1");
        respMap.put("msg", "Added Successfully");
        return respMap;
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<NcliVisitQuestionAnswer> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                                  @RequestParam(value = "size", required = false, defaultValue = "20") String size,
                                                  @RequestParam(value = "qString", required = false) String qString,
                                                  @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                                  @RequestParam(value = "col", required = false, defaultValue = "vqaId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return ncliVisitQuestionAnswerRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return ncliVisitQuestionAnswerRepository.findByTestAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @PutMapping("delete/{vqaId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("vqaId") Long vqaId) {
        TenantContext.setCurrentTenant(tenantName);
        NcliVisitQuestionAnswer ncliVisitQuestionAnswer = ncliVisitQuestionAnswerRepository.getById(vqaId);
        if (ncliVisitQuestionAnswer != null) {
            ncliVisitQuestionAnswer.setIsDeleted(true);
            ncliVisitQuestionAnswerRepository.save(ncliVisitQuestionAnswer);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }
}