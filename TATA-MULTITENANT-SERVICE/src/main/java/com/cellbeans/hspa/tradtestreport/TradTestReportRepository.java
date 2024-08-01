package com.cellbeans.hspa.tradtestreport;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TradTestReportRepository extends JpaRepository<TradTestReport, Long> {

    TradTestReport findByTrPatientIdPatientIdEquals(Long patientId);
}
