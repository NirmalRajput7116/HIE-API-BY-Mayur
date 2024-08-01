package com.cellbeans.hspa.trnappointment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TrnAppointmentPortalRepository extends JpaRepository<TrnPortalAppointment, Long> {

   /* @Query(value = "SELECT * FROM trn_appointment_portal  where is_active = 1 and is_deleted = 0 ", nativeQuery = true)
    Page<TrnPortalAppointment> findAllIsActiveTrueAndIsDeletedFalse(Pageable page);*/
}
