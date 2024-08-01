
package com.cellbeans.hspa.nmstcontact;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cellbeans.hspa.nmstcontact.NmstContact;
import java.lang.String;
import java.util.List;

public interface NmstContactRepository extends JpaRepository<NmstContact, Long> {

    
	Page<NmstContact> findByContactMobileAndIsActiveTrueAndIsDeletedFalse(String name,Pageable page);
             

	Page<NmstContact> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

        List<NmstContact> findByContactMobileContains(String key);
}

