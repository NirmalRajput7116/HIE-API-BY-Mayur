package com.cellbeans.hspa.mstdietchart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstDietChartRepository extends JpaRepository<MstDietChart, Long> {

    MstDietChart getById(Long id);

    List<MstDietChart> findByPatientIdAndIsDeletedFalseOrderByIdDesc(Long id);

}
