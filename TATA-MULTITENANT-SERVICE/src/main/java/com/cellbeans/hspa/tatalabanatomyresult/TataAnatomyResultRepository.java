package com.cellbeans.hspa.tatalabanatomyresult;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TataAnatomyResultRepository extends JpaRepository<TataAnatomyTestResult, Long> {

    @Query(value = "select * from  lis_testresult r where r.SpecimenID=:id and r.InspectTime like :inspectdate% order by r.ID asc", nativeQuery = true)
    List<TataAnatomyTestResult> findAllBySpeciId(@Param("id") String id, @Param("inspectdate") String inspectDate);
//    @Query(value = "SELECT p FROM TataAnatomyTestResult p where p.specimenid=:id and  p.inspecttime between :inspectdate and :inspectdate")
//    List<TataAnatomyTestResult> findAllBySpeciId(@Param("id") String id,@Param("inspectdate") String inspectDate);
}