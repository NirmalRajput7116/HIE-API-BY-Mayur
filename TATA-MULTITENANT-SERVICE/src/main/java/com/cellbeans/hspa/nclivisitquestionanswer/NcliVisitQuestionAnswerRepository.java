
package com.cellbeans.hspa.nclivisitquestionanswer;

import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cellbeans.hspa.nclivisitquestionanswer.NcliVisitQuestionAnswer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.lang.String;
import java.util.List;

public interface NcliVisitQuestionAnswerRepository extends JpaRepository<NcliVisitQuestionAnswer, Long> {

	Page<NcliVisitQuestionAnswer> findByTestAndIsActiveTrueAndIsDeletedFalse(String name,Pageable page);

	Page<NcliVisitQuestionAnswer> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

	List<NcliVisitQuestionAnswer> findByTestContains(String key);

//	List<NcliVisitQuestionAnswer> findAllByVqaVisitIdAndVqaQuestionIdQuestionGroupIdGroupCfIdCfIdAndIsActiveTrueAndIsDeletedFalse(int visitId, Long cfId);

	List<NcliVisitQuestionAnswer> findAllByVqaVisitIdAndVqaQuestionIdQuestionGroupIdGroupCfIdCfIdAndIsActiveTrueAndIsDeletedFalse(int visitId, Long cfId);
	List<NcliVisitQuestionAnswer> findAllByVqaVisitIdAndVqaQuestionIdQuestionGroupIdGroupCfIdCfIdAndIsActiveTrueAndIsDeletedFalseAndVqaIsOutBoundFalse(int visitId, Long cfId);
	List<NcliVisitQuestionAnswer> findAllByVqaVisitIdAndVqaQuestionIdQuestionGroupIdGroupCfIdCfIdAndIsActiveTrueAndIsDeletedFalseAndVqaIsOutBoundTrue(int visitId, Long cfId);

	NcliVisitQuestionAnswer findByVqaQuestionIdQuestionIdEquals(Long key);

	@Modifying
	@org.springframework.transaction.annotation.Transactional
	@Query(value = "DELETE FROM ncli_visit_question_answer WHERE ncli_visit_question_answer.cf_id=:cfId", nativeQuery = true)
	int deletePreviousAnswers(@Param("cfId") String cfId);


	@org.springframework.transaction.annotation.Transactional
	@Query(value = "SELECT nvqa.created_date AS createdDate, mu.user_fullname AS filledBy, cf.cf_name AS cfName, nvqa.vqa_visit_id AS visitId,nvqa.cf_id AS cfId , cfc.cfc_name AS cfcName FROM ncli_visit_question_answer nvqa" +
			" INNER JOIN mst_visit mv ON mv.visit_id = nvqa.vqa_visit_id" +
			" INNER JOIN ncli_clinical_form cf ON cf.cf_id = nvqa.cf_id" +
			" INNER JOIN mst_staff ms ON ms.staff_id = mv.visit_staff_id" +
			" INNER JOIN mst_user mu ON mu.user_id=ms.staff_user_id" +
			" INNER JOIN ncli_clinical_form_category cfc ON cfc.cfc_id=cf.cf_cfc_id" +
			" WHERE vqa_visit_id =:visitId" +
			" GROUP BY nvqa.cf_id", nativeQuery = true)
	List<NcliVisitQuestionAnswerFilledDto> getAllFilledByVisitIdWithGroup(@Param("visitId") Long visitId);

	//Nayan
	@org.springframework.transaction.annotation.Transactional
	@Query(value = " SELECT nvqa.created_date AS createdDate, mu.user_fullname AS filledBy, cf.cf_name AS cfName, nvqa.vqa_visit_id AS visitId,nvqa.cf_id AS cfId, cfc.cfc_name AS cfcName , mv.visit_patient_id , tt.isemrfinal as IsEmrFinal" +
			" FROM ncli_visit_question_answer nvqa " +
			" INNER JOIN mst_visit mv ON mv.visit_id = nvqa.vqa_visit_id " +
			" INNER JOIN ncli_clinical_form cf ON cf.cf_id = nvqa.cf_id " +
			" INNER JOIN mst_staff ms ON ms.staff_id = mv.visit_staff_id " +
			" INNER JOIN mst_user mu ON mu.user_id=ms.staff_user_id " +
			" INNER JOIN ncli_clinical_form_category cfc ON cfc.cfc_id=cf.cf_cfc_id " +
			" LEFT JOIN temr_timeline tt ON tt.timeline_visit_id = mv.visit_id " +
			" WHERE mv.visit_patient_id =:patientId " +
			" GROUP BY nvqa.vqa_visit_id DESC ",nativeQuery = true)
	List<NcliVisitQuestionAnswerFilledDto> getAllFilledByPatientId(@Param("patientId") Long patientId);
}

