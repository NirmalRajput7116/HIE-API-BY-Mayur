
package com.cellbeans.hspa.nrelcampuser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cellbeans.hspa.nrelcampuser.NrelCampUser;
import java.lang.String;
import java.util.List;

public interface NrelCampUserRepository extends JpaRepository<NrelCampUser, Long> {

    
	Page<NrelCampUser> findByTestAndIsActiveTrueAndIsDeletedFalse(String name,Pageable page);
             

	Page<NrelCampUser> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

        List<NrelCampUser> findByTestContains(String key);
}

