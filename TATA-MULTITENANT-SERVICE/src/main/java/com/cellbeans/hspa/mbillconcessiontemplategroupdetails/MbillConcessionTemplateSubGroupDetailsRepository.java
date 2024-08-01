package com.cellbeans.hspa.mbillconcessiontemplategroupdetails;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MbillConcessionTemplateSubGroupDetailsRepository extends JpaRepository<MbillConcessionTemplateSubGroupDetails, Long> {
    Page<MbillConcessionTemplateSubGroupDetails> findByCtgdSgIdSgNameContainsOrCtgdCtIdCtNameContains(String subGroupName, String concessionTemplateName, Pageable page);

    Page<MbillConcessionTemplateSubGroupDetails> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MbillConcessionTemplateSubGroupDetails> findAllByIsActiveTrueAndIsDeletedFalse();

    List<MbillConcessionTemplateSubGroupDetails> findByCtgdCtIdCtNameContains(String key);

    List<MbillConcessionTemplateSubGroupDetails> findAllByCtgdCtIdCtIdAndIsActiveTrueAndIsDeletedFalse(Long ctId);

    List<MbillConcessionTemplateSubGroupDetails> findAllByCtgdSgIdSgIdAndIsActiveTrueAndIsDeletedFalse(Long sgId);

}
