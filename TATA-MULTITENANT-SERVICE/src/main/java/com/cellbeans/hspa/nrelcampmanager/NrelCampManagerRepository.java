
package com.cellbeans.hspa.nrelcampmanager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cellbeans.hspa.nrelcampmanager.NrelCampManager;
import java.lang.String;
import java.util.List;

public interface NrelCampManagerRepository extends JpaRepository<NrelCampManager, Long> {

    
	Page<NrelCampManager> findByTestAndIsActiveTrueAndIsDeletedFalse(String name,Pageable page);
             

	Page<NrelCampManager> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

        List<NrelCampManager> findByTestContains(String key);
}

