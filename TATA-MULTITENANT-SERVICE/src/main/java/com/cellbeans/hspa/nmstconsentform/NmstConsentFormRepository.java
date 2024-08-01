
package com.cellbeans.hspa.nmstconsentform;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cellbeans.hspa.nmstconsentform.NmstConsentForm;
import java.lang.String;
import java.util.List;

public interface NmstConsentFormRepository extends JpaRepository<NmstConsentForm, Long> {

    
	Page<NmstConsentForm> findByCfHouseholdNoAndIsActiveTrueAndIsDeletedFalse(String name,Pageable page);
             

	Page<NmstConsentForm> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

        List<NmstConsentForm> findByCfHouseholdNoContains(String key);
}

