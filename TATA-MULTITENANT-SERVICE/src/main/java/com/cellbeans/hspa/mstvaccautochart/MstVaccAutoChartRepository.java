package com.cellbeans.hspa.mstvaccautochart;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstVaccAutoChartRepository extends JpaRepository<MstVaccAutoChart, Long> {

    Page<MstVaccAutoChart> findAllByIsActiveTrueAndIsDeletedFalseAndVacPatientCategoryVpcId(String name, Pageable page, Integer patientCategory);

    List<MstVaccAutoChart> findAllByIsActiveTrueAndIsDeletedFalseAndVacPatientCategoryVpcId(Integer patientCategory);

    Page<MstVaccAutoChart> findAllByIsActiveTrueAndIsDeletedFalseAndVacPatientCategoryVpcId(Pageable page, Integer patientCategory);

    List<MstVaccAutoChart> findByVacDoseContains(String key);

}
