package com.cellbeans.hspa.mstquestionanswer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MstQuestionAnswerRepository extends JpaRepository<MstQuestionAnswer, Long> {

    @Query(value = "select mqa.* from mst_question_answer mqa inner join mst_field mf on mf.field_id=mqa.answer_field_id where mqa.answer_field_id=:fieldId and mqa.is_active = 1 and mqa.is_deleted = 0", nativeQuery = true)
    List<MstQuestionAnswer> findQAListByFieldId(@Param("fieldId") Long fieldId);

    MstQuestionAnswer findByAnswerNameEqualsAndAnswerFieldIdFieldIdEquals(String name, Long fieldId);

}
