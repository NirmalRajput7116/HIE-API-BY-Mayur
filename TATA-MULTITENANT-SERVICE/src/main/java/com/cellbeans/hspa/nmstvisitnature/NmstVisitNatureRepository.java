
package com.cellbeans.hspa.nmstvisitnature;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cellbeans.hspa.nmstvisitnature.NmstVisitNature;
import java.lang.String;
import java.util.List;

public interface NmstVisitNatureRepository extends JpaRepository<NmstVisitNature, Long> {

    
	Page<NmstVisitNature> findByVnNameAndIsActiveTrueAndIsDeletedFalse(String name,Pageable page);
             

	Page<NmstVisitNature> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

        List<NmstVisitNature> findByVnNameContains(String key);
}

