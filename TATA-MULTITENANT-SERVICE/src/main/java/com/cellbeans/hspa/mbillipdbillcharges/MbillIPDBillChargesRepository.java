package com.cellbeans.hspa.mbillipdbillcharges;

import com.cellbeans.hspa.mbillgroup.MbillGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MbillIPDBillChargesRepository extends JpaRepository<MbillIPDBillCharges, Long> {

    Page<MbillGroup> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    MbillIPDBillCharges findByIpdbcId(Long ipdbcId);

}


