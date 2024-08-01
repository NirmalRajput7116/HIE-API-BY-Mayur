package com.cellbeans.hspa.mstquestionanswer;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_question_answer")
public class MstQuestionAnswerController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    MstQuestionAnswerRepository mstQuestionAnswerRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstQuestionAnswer mstQuestionAnswer) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstQuestionAnswer.getAnswerFieldId().getFieldId() != 0L) {
            mstQuestionAnswerRepository.save(mstQuestionAnswer);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("getaqlistbyfieldid/{fieldId}")
    public List<MstQuestionAnswer> getQAByFieldId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("fieldId") Long fieldId) {
        TenantContext.setCurrentTenant(tenantName);
        List<MstQuestionAnswer> mstQAList = mstQuestionAnswerRepository.findQAListByFieldId(fieldId);
        return mstQAList;
    }

    @RequestMapping("getbyanswernamefieldid/{answerName}/{fieldId}")
    public Map<String, Object> getByAnswerNameFieldId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("answerName") String answerName, @PathVariable("fieldId") Long fieldId) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> restMap = new HashMap<String, Object>();
        MstQuestionAnswer mstQuestionAnswer = mstQuestionAnswerRepository.findByAnswerNameEqualsAndAnswerFieldIdFieldIdEquals(answerName, fieldId);
        restMap.put("content", mstQuestionAnswer);
        return restMap;
    }

}
