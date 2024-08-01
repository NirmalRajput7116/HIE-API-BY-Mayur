package com.cellbeans.hspa.tatabiologylabresult;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TataBiologyResultRepository extends JpaRepository<TataBiologyTestResult, Long> {

    @Query(value = "select * from  lis_patient_observation r where r.patientid=:id order by r.id asc", nativeQuery = true)
    List<TataBiologyTestResult> findAllByPatientId(@Param("id") String id);

}