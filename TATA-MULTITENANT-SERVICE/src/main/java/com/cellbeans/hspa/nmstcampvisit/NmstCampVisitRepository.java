
package com.cellbeans.hspa.nmstcampvisit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cellbeans.hspa.nmstcampvisit.NmstCampVisit;
import java.lang.String;
import java.util.List;

public interface NmstCampVisitRepository extends JpaRepository<NmstCampVisit, Long> {

    
	Page<NmstCampVisit> findByCvNameAndIsActiveTrueAndIsDeletedFalse(String name,Pageable page);
             

	Page<NmstCampVisit> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

        List<NmstCampVisit> findByCvNameContains(String key);
        List<NmstCampVisit> findByCvCampIdCampIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);
}

