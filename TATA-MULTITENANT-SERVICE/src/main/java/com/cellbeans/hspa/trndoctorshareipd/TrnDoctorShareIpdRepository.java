package com.cellbeans.hspa.trndoctorshareipd;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrnDoctorShareIpdRepository extends JpaRepository<TrnDoctorShareIpd, Long> {

    Page<TrnDoctorShareIpd> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    TrnDoctorShareIpd findFirstByDsiBillIdBillIdEqualsAndIsActiveTrueAndIsDeletedFalse(long dsiBillId);

}
