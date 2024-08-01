package com.cellbeans.hspa.mstvaccinationchart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstVaccinationChartRepository extends JpaRepository<MstVaccinationChart, Long> {

    MstVaccinationChart findByVcId(Long id);

    List<MstVaccinationChart> findByPatientIdAndIsDeletedFalseOrderByVcIdDesc(Long id);

}
