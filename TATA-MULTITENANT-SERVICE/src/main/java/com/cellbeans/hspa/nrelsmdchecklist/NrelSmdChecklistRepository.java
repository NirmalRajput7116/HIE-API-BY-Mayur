
package com.cellbeans.hspa.nrelsmdchecklist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cellbeans.hspa.nrelsmdchecklist.NrelSmdChecklist;
import java.lang.String;
import java.util.List;

public interface NrelSmdChecklistRepository extends JpaRepository<NrelSmdChecklist, Long> {

    
	Page<NrelSmdChecklist> findByTestAndIsActiveTrueAndIsDeletedFalse(String name,Pageable page);
             

	Page<NrelSmdChecklist> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

        List<NrelSmdChecklist> findByTestContains(String key);
}

