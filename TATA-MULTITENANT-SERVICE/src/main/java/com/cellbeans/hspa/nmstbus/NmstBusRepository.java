
package com.cellbeans.hspa.nmstbus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cellbeans.hspa.nmstbus.NmstBus;
import java.lang.String;
import java.util.List;

public interface NmstBusRepository extends JpaRepository<NmstBus, Long> {

    
	Page<NmstBus> findByBusNameAndIsActiveTrueAndIsDeletedFalse(String name,Pageable page);
             

	Page<NmstBus> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

        List<NmstBus> findByBusNameContains(String key);
}

