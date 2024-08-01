package com.cellbeans.hspa.tbillbill;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface BillReconciliationLogRepository extends JpaRepository<BillReconciliationLog, Long> {

}
