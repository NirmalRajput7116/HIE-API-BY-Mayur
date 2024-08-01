
package com.cellbeans.hspa.nclianswer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cellbeans.hspa.nclianswer.NcliAnswer;
import java.lang.String;
import java.util.List;

public interface NcliAnswerRepository extends JpaRepository<NcliAnswer, Long> {

    
	Page<NcliAnswer> findByAnswerNameAndIsActiveTrueAndIsDeletedFalse(String name,Pageable page);
             

	Page<NcliAnswer> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

        List<NcliAnswer> findByAnswerNameContains(String key);

        List<NcliAnswer> findAllByAnswerQuestionIdQuestionGroupIdGroupCfIdCfIdAndIsActiveTrueAndIsDeletedFalse(Long cfId);
}

