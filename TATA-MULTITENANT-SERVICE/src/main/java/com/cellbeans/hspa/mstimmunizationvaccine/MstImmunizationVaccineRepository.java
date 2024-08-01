
package com.cellbeans.hspa.mstimmunizationvaccine;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cellbeans.hspa.mstimmunizationvaccine.MstImmunizationVaccine;
import java.lang.String;
import java.util.List;

public interface MstImmunizationVaccineRepository extends JpaRepository<MstImmunizationVaccine, Long> {
	Page<MstImmunizationVaccine> findByIvNameAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

	Page<MstImmunizationVaccine> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

	List<MstImmunizationVaccine> findByIvNameContains(String key);

	List<MstImmunizationVaccine> findByIvIcIdIcIdEquals(Long key);
}

