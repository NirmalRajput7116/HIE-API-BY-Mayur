
package com.cellbeans.hspa.nmstcamp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cellbeans.hspa.nmstcamp.NmstCamp;
import java.lang.String;
import java.util.List;

public interface NmstCampRepository extends JpaRepository<NmstCamp, Long> {

    
	Page<NmstCamp> findByCampNameAndIsActiveTrueAndIsDeletedFalse(String name,Pageable page);
             

	Page<NmstCamp> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

        List<NmstCamp> findByCampNameContains(String key);
}

