package com.cellbeans.hspa.temritemprescription;

import com.cellbeans.hspa.temrvisitprescription.TemrVistPrescriptionDTOForItems;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TemrItemPrescriptionRepository extends JpaRepository<TemrItemPrescription, Long> {
    @Query(value = "select temr_item_prescription.*, mst_route.route_name, mst_dose_frequency.df_name, memr_drug_instruction.di_name, inv_generic_name.generic_name, inv_item.item_strength from temr_visit_prescription left join temr_item_prescription on temr_item_prescription.ip_vp_id = temr_visit_prescription.vp_id left join mst_route on mst_route.route_id = temr_item_prescription.ip_route_id left join mst_dose_frequency on mst_dose_frequency.df_id = temr_item_prescription.ip_df_id left join memr_drug_instruction on memr_drug_instruction.di_id = temr_item_prescription.ip_di_id left join inv_item on inv_item.item_id = temr_item_prescription.ip_inv_item_id left join inv_generic_name on inv_generic_name.generic_id = inv_item.item_generic_id where temr_visit_prescription.vp_visit_id =:visitId", nativeQuery = true)
    List<TemrItemPrescription> findByVisitId(@Param("visitId") Long visitId);

    Page<TemrItemPrescription> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);
    /* List<TemrItemPrescription>findAllByVpVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(Long key);*/

    List<TemrItemPrescription> findByIpVpIdVpIdAndIsActiveTrueAndIsDeletedFalse(Long id);

    Page<TemrItemPrescription> findByIpVpIdVpIdAndIsActiveTrueAndIsDeletedFalse(Long id, Pageable page);

    List<TemrItemPrescription> findAllByIpVpIdVpTimelineIdTimelineIdAndIsActiveTrueAndIsDeletedFalse(Long id);

    List<TemrItemPrescription> findAllByIpVpIdVpTimelineIdTimelinePatientIdPatientIdAndIsActiveTrueAndIsDeletedFalse(Long id);

    @Query(value = "select  new com.cellbeans.hspa.temrvisitprescription.TemrVistPrescriptionDTOForItems(i.ipId,i.ipQuantity,i.ipInvItemId,i.ipVpId.vpId,i.ipDdId) from TemrItemPrescription i where i.ipVpId.vpId =:vsid and i.isActive=true and i.isDeleted=false ")
    List<TemrVistPrescriptionDTOForItems> findByVpPatientByvsId(@Param("vsid") long vsid);

}
            
