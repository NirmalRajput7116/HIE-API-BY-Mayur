package com.cellbeans.hspa.trnappointment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrnAppointmentPortalUploadRepository extends JpaRepository<TrnPortalUploadDocs, Long> {

    List<TrnPortalUploadDocs> findAllByPudAppointmentIdEqualsAndIsActiveTrueAndIsDeletedFalse(String appointmentId);
}
