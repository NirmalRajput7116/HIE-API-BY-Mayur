
package com.cellbeans.hspa.nrelcamp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cellbeans.hspa.nrelcamp.NrelCamp;
import java.lang.String;
import java.util.List;

public interface NrelCampRepository extends JpaRepository<NrelCamp, Long> {

    
	Page<NrelCamp> findByTestAndIsActiveTrueAndIsDeletedFalse(String name,Pageable page);
             

	Page<NrelCamp> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

        List<NrelCamp> findByTestContains(String key);
}

