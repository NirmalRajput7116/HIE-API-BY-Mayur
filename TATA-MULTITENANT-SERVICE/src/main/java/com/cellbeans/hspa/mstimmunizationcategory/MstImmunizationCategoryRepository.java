
package com.cellbeans.hspa.mstimmunizationcategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cellbeans.hspa.mstimmunizationcategory.MstImmunizationCategory;
import java.lang.String;
import java.util.List;

public interface MstImmunizationCategoryRepository extends JpaRepository<MstImmunizationCategory, Long> {

    
	Page<MstImmunizationCategory> findByIcNameAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);
             

	Page<MstImmunizationCategory> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

        List<MstImmunizationCategory> findByIcNameContains(String key);
}

