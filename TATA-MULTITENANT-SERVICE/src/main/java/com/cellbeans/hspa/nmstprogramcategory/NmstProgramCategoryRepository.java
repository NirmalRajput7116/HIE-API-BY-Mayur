
package com.cellbeans.hspa.nmstprogramcategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cellbeans.hspa.nmstprogramcategory.NmstProgramCategory;
import java.lang.String;
import java.util.List;

public interface NmstProgramCategoryRepository extends JpaRepository<NmstProgramCategory, Long> {

    
	Page<NmstProgramCategory> findByPcNameAndIsActiveTrueAndIsDeletedFalse(String name,Pageable page);
             

	Page<NmstProgramCategory> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

        List<NmstProgramCategory> findByPcNameContains(String key);
}

