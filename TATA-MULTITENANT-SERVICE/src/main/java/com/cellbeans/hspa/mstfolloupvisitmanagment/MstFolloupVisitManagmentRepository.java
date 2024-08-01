package com.cellbeans.hspa.mstfolloupvisitmanagment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MstFolloupVisitManagmentRepository extends JpaRepository<MstFolloupVisitManagment, Long> {

    Page<MstFolloupVisitManagment> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    MstFolloupVisitManagment findByFollowupServiceIdServiceIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long serviceId);

    MstFolloupVisitManagment findMstFolloupVisitManagmentByFollowupServiceIdServiceIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long serviceId);
}
