package com.cellbeans.hspa.tatabiologylabreport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TataBiologyLabReportRepository extends JpaRepository<TataBiologyLabReport, Long> {

    @Query(value = "SELECT p FROM TataBiologyLabReport p")
    Page<TataBiologyLabReport> findByMultiFilterPathology(Pageable page);

}