package com.cellbeans.hspa.advancedhistory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvancedHistoryRepository extends JpaRepository<AdvancedHistory, Long> {

    List<AdvancedHistory> findByAdvancedAmountIdIpdadvancedId(Long id);

}
