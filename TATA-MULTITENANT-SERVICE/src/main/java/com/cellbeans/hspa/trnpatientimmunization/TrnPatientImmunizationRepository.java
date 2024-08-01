
package com.cellbeans.hspa.trnpatientimmunization;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cellbeans.hspa.trnpatientimmunization.TrnPatientImmunization;
import java.lang.String;
import java.util.List;

public interface TrnPatientImmunizationRepository extends JpaRepository<TrnPatientImmunization, Long> {

    
//	Page<TrnPatientImmunization> findByPiTestAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);


	Page<TrnPatientImmunization> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

	List<TrnPatientImmunization> findAllByPiPatientIdPatientIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long patientId);

//        List<TrnPatientImmunization> findByPiTestContains(String key);
}

