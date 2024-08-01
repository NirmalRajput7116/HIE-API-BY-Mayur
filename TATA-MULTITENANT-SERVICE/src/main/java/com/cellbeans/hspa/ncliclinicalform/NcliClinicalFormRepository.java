
package com.cellbeans.hspa.ncliclinicalform;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cellbeans.hspa.ncliclinicalform.NcliClinicalForm;
import java.lang.String;
import java.util.List;

public interface NcliClinicalFormRepository extends JpaRepository<NcliClinicalForm, Long> {

    
	Page<NcliClinicalForm> findByCfNameAndIsActiveTrueAndIsDeletedFalse(String name,Pageable page);
             

	Page<NcliClinicalForm> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

	List<NcliClinicalForm> findByCfNameContains(String key);

	List<NcliClinicalForm> findAllByCfCfcIdCfcIdAndIsActiveTrueAndIsDeletedFalse(Long key);
}

