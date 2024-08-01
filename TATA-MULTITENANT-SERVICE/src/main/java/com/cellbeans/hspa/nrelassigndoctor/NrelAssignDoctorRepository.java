
package com.cellbeans.hspa.nrelassigndoctor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cellbeans.hspa.nrelassigndoctor.NrelAssignDoctor;
import java.lang.String;
import java.util.List;

public interface NrelAssignDoctorRepository extends JpaRepository<NrelAssignDoctor, Long> {

    
	Page<NrelAssignDoctor> findByTestAndIsActiveTrueAndIsDeletedFalse(String name,Pageable page);
             

	Page<NrelAssignDoctor> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

        List<NrelAssignDoctor> findByTestContains(String key);
}

