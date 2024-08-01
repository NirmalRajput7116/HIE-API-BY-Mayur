package com.cellbeans.hspa.trnvaccinationchart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrnVaccinationChartRepository extends JpaRepository<TrnVaccinationChart, Long> {

    TrnVaccinationChart findByVcId(Long id);

    TrnVaccinationChart findByVacIdVacIdAndChartIdVcId(long id, long chartId);

    List<TrnVaccinationChart> findByChartIdVcId(long chartId);

    @Query("select vc from TrnVaccinationChart vc where vc.chartId.patientId =:patientid and vc.isDeleted=false group by vc.chartId")
    List<TrnVaccinationChart> findByChart(@Param("patientid") long patientid);

}
