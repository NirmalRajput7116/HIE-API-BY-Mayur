package com.cellbeans.hspa.mstproceduresubcategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstProcedureSubcategoryRepository extends JpaRepository<MstProcedureSubcategory, Long> {

    Page<MstProcedureSubcategory> findByPsNameContainsAndIsActiveTrueAndIsDeletedFalseOrPsCategoryIdPcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, String name1, Pageable page);

    Page<MstProcedureSubcategory> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstProcedureSubcategory> findByPsNameContains(String key);

}
            
