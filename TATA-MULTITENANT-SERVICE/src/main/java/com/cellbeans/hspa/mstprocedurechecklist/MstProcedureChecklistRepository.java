package com.cellbeans.hspa.mstprocedurechecklist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstProcedureChecklistRepository extends JpaRepository<MstProcedureChecklist, Long> {

    Page<MstProcedureChecklist> findByPcNameContainsAndIsActiveTrueAndIsDeletedFalseOrPcCategoryIdPcNameContainsAndIsActiveTrueAndIsDeletedFalseOrPcSubcategoryIdPsNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, String name1, String name2, Pageable page);

    Page<MstProcedureChecklist> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstProcedureChecklist> findByPcNameContains(String key);

}
            
