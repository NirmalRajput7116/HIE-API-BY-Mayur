
package com.cellbeans.hspa.nrelcvlocation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cellbeans.hspa.nrelcvlocation.NrelCvLocation;
import java.lang.String;
import java.util.List;

public interface NrelCvLocationRepository extends JpaRepository<NrelCvLocation, Long> {

    
	Page<NrelCvLocation> findByTestAndIsActiveTrueAndIsDeletedFalse(String name,Pageable page);
             

	Page<NrelCvLocation> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

        List<NrelCvLocation> findByTestContains(String key);
}

