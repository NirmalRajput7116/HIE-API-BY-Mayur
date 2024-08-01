package com.cellbeans.hspa.invitemtermsandcondition;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvItemTermsAndConditionRepository extends JpaRepository<InvItemTermsAndCondition, Long> {

    Page<InvItemTermsAndCondition> findByTacDescripationContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<InvItemTermsAndCondition> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<InvItemTermsAndCondition> findByTacDescripationContains(String key);

}
            
