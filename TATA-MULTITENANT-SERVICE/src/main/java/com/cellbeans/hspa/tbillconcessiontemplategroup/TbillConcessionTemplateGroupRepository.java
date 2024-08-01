package com.cellbeans.hspa.tbillconcessiontemplategroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TbillConcessionTemplateGroupRepository extends JpaRepository<TbillConcessionTemplateGroup, Long> {

    Page<TbillConcessionTemplateGroup> findByCtgPercentageContainsAndIsActiveTrueAndIsDeletedFalse(Long name, Pageable page);

    Page<TbillConcessionTemplateGroup> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    /*        List<TbillConcessionTemplateGroup> findByCtgGroupIdGroupNameContains(String key);*/
}
            
