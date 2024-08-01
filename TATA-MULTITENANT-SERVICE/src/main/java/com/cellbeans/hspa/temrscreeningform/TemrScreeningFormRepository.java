package com.cellbeans.hspa.temrscreeningform;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemrScreeningFormRepository extends JpaRepository<TemrScreeningForm, Long> {

    List<TemrScreeningForm> findAllBySfVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(long visitId);

    Page<TemrScreeningForm> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);
}
