
package com.cellbeans.hspa.trnpatientimmunizationchart;

import com.cellbeans.hspa.mststaff.MstStaff;
import org.exolab.castor.types.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cellbeans.hspa.trnpatientimmunizationchart.TrnPatientImmunizationChart;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.lang.String;
import java.util.Date;
import java.util.List;

public interface TrnPatientImmunizationChartRepository extends JpaRepository<TrnPatientImmunizationChart, Long> {
	Page<TrnPatientImmunizationChart> findByTestAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

	Page<TrnPatientImmunizationChart> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

	List<TrnPatientImmunizationChart> findByTestContains(String key);

	List<TrnPatientImmunizationChart> findByPicPiIdPiIdEquals(Long key);

	@Modifying
	@Query("update TrnPatientImmunizationChart trnPatientImmunizationChart set trnPatientImmunizationChart.picIsTaken = :picIsTaken, trnPatientImmunizationChart.picDateOfVaccinationTaken = :picDateOfVaccinationTaken, trnPatientImmunizationChart.picAgeOf = :picAgeOf, trnPatientImmunizationChart.picDateOfNextVisit = :picDateOfNextVisit, trnPatientImmunizationChart.picStaffId.staffId = :picStaffId where trnPatientImmunizationChart.picId = :picId")
	@Transactional
	int updateDateOfVaccinationTakenAndIsTakenStatus(@Param("picId") Long picId, @Param("picIsTaken") Boolean picIsTaken, @Param("picDateOfVaccinationTaken") Date picDateOfVaccinationTaken, @Param("picAgeOf") String picAgeOf, @Param("picDateOfNextVisit") Date picDateOfNextVisit, @Param("picStaffId") Long picStaffId);
}

