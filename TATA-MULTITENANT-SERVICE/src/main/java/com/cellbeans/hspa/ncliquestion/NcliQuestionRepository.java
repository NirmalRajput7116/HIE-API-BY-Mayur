
package com.cellbeans.hspa.ncliquestion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cellbeans.hspa.ncliquestion.NcliQuestion;
import java.lang.String;
import java.util.List;

public interface NcliQuestionRepository extends JpaRepository<NcliQuestion, Long> {

    
	Page<NcliQuestion> findByQuestionNameAndIsActiveTrueAndIsDeletedFalse(String name,Pageable page);
             

	Page<NcliQuestion> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

	List<NcliQuestion> findByQuestionNameContains(String key);

	List<NcliQuestion> findAllByQuestionGroupIdGroupCfIdCfIdAndIsActiveTrueAndIsDeletedFalse(Long cfId);
}

