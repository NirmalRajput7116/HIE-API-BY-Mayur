
package com.cellbeans.hspa.ncliclinicalformcategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cellbeans.hspa.ncliclinicalformcategory.NcliClinicalFormCategory;
import java.lang.String;
import java.util.List;

public interface NcliClinicalFormCategoryRepository extends JpaRepository<NcliClinicalFormCategory, Long> {

    
	Page<NcliClinicalFormCategory> findByCfcNameAndIsActiveTrueAndIsDeletedFalse(String name,Pageable page);
             

	Page<NcliClinicalFormCategory> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

        List<NcliClinicalFormCategory> findByCfcNameContains(String key);
}

