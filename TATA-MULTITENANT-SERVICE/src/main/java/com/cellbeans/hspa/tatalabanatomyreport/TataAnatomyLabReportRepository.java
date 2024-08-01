package com.cellbeans.hspa.tatalabanatomyreport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TataAnatomyLabReportRepository extends JpaRepository<TataAnatomyLabReport, Long> {

    @Query(value = "SELECT p FROM TataAnatomyLabReport p where p.inspectdate between :datefrom and :dateto order by inspectdate desc")
    Page<TataAnatomyLabReport> findByMultiFilterPathology(@Param("datefrom") Date dateFrom, @Param("dateto") Date dateTo, Pageable page);

    @Query(value = "SELECT p FROM  TataAnatomyLabReport p where p.patientid=:sampleid order by p.id desc")
    List<TataAnatomyLabReport> findAllByPatientId(@Param("sampleid") String sampleid);

}