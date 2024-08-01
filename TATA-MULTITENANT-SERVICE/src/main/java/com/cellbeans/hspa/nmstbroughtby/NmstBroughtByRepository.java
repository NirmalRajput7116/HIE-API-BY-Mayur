
package com.cellbeans.hspa.nmstbroughtby;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cellbeans.hspa.nmstbroughtby.NmstBroughtBy;
import java.lang.String;
import java.util.List;

public interface NmstBroughtByRepository extends JpaRepository<NmstBroughtBy, Long> {

    
	Page<NmstBroughtBy> findByBbFullnameAndIsActiveTrueAndIsDeletedFalse(String name,Pageable page);
             

	Page<NmstBroughtBy> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

        List<NmstBroughtBy> findByBbFullnameContains(String key);
}

