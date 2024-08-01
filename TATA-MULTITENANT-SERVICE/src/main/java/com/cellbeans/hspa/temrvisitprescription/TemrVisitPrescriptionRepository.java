package com.cellbeans.hspa.temrvisitprescription;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TemrVisitPrescriptionRepository extends JpaRepository<TemrVisitPrescription, Long> {

    Page<TemrVisitPrescription> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    /*TemrVisitPrescription findByVpVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(Long key);*/

    List<TemrVisitPrescription> findAllByVpPatientMrNumberAndIsActiveTrueAndIsDeletedFalse(String key);

    List<TemrVisitPrescription> findAllByVpPatientMrNumberAndClosePrescriptionFalseAndIsActiveTrueAndIsDeletedFalse(String key);

    Page<TemrVisitPrescription> findByVpVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(Long visitId, Pageable page);

    List<TemrVisitPrescription> findByVpVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(Long id);

    List<TemrVisitPrescription> findByVpTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(Long id);

    List<TemrVisitPrescription> findAllByVpTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(Long id);

    List<TemrVisitPrescription> findByVpTimelineIdTimelineAdmissionIdAdmissionIdAndIsActiveTrueAndIsDeletedFalse(Long id);

    @Query(value = "select  new com.cellbeans.hspa.temrvisitprescription.TemrVistPrecriptionDTOForPharmacy(vp.vpId,vp.vpVisitId.visitId,vp.vpPrescribedStaffId,vp.createdDate) from TemrVisitPrescription vp where vp.vpPatientMrNumber =:vpPatientMrNumber and vp.closePrescription =false and vp.isActive=true and vp.isDeleted=false ")
    List<TemrVistPrecriptionDTOForPharmacy> findByVpPatientMrNumber(@Param("vpPatientMrNumber") String vpPatientMrNumber);

    //By Seetanshu
    TemrVisitPrescription findAllByVpIdAndClosePrescriptionFalseAndIsActiveTrueAndIsDeletedFalse(long key);

}
            
