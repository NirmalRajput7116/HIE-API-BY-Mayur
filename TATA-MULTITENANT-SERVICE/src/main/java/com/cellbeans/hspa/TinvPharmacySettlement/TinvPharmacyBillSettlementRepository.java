package com.cellbeans.hspa.TinvPharmacySettlement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TinvPharmacyBillSettlementRepository extends JpaRepository<TinvPharmacyBillSettlement, Long> {

    @Query(value = "select * from tinv_pharmacy_bill_settlement pbs,tinv_pharmacy_bill_settlement_pbs_ps_id pbsm,tinv_pharmacy_sale ps,mst_visit v,mst_patient p where pbs.pbs_id=pbsm.tinv_pharmacy_bill_settlement_pbs_id and  pbsm.pbs_ps_id_ps_id=ps.ps_id and ps.ps_visit_id=v.visit_id and v.visit_patient_id=p.patient_id and  ps.pharmacy_type=:listTypePharmacy  and  pbs.pbs_unit_id=:unitId  GROUP BY pbs.pbs_id  \n#pageable\n", countQuery = "select count(*) from (select pbs.pbs_id from tinv_pharmacy_bill_settlement pbs,tinv_pharmacy_bill_settlement_pbs_ps_id pbsm,tinv_pharmacy_sale ps,mst_visit v,mst_patient p where pbs.pbs_id=pbsm.tinv_pharmacy_bill_settlement_pbs_id and  pbsm.pbs_ps_id_ps_id=ps.ps_id and ps.ps_visit_id=v.visit_id and v.visit_patient_id=p.patient_id and ps.pharmacy_type=:listTypePharmacy and pbs.pbs_unit_id=:unitId GROUP BY pbs.pbs_id) as dataTable ", nativeQuery = true)
    Page<TinvPharmacyBillSettlement> findAllByPbsUnitIdUnitIdAndPbsPsIdPharmacyType(Pageable page, @Param("unitId") long unitId, @Param("listTypePharmacy") Integer listTypePharmacy);

    @Query(value = "select * from tinv_pharmacy_bill_settlement pbs,tinv_pharmacy_bill_settlement_pbs_ps_id pbsm,tinv_pharmacy_sale ps,mst_visit v,mst_patient p where pbs.pbs_id=pbsm.tinv_pharmacy_bill_settlement_pbs_id and  pbsm.pbs_ps_id_ps_id=ps.ps_id and ps.ps_visit_id=v.visit_id and v.visit_patient_id=p.patient_id and  ps.pharmacy_type=:listTypePharmacy  and (ps.ps_patient_first_name like %:qString% or ps.ps_patient_last_name like %:qString% or ps.ps_patient_mobile_no like %:qString% or p.patient_mr_no like %:qString% )  and  pbs.pbs_unit_id=:unitId  GROUP BY pbs.pbs_id  \n#pageable\n", countQuery = "select count(*) from (select pbs.pbs_id from tinv_pharmacy_bill_settlement pbs,tinv_pharmacy_bill_settlement_pbs_ps_id pbsm,tinv_pharmacy_sale ps,mst_visit v,mst_patient p where pbs.pbs_id=pbsm.tinv_pharmacy_bill_settlement_pbs_id and  pbsm.pbs_ps_id_ps_id=ps.ps_id and ps.ps_visit_id=v.visit_id and v.visit_patient_id=p.patient_id and ps.pharmacy_type=:listTypePharmacy and (ps.ps_patient_first_name like %:qString% or ps.ps_patient_last_name like %:qString% or ps.ps_patient_mobile_no like %:qString% or p.patient_mr_no like %:qString% ) and  pbs.pbs_unit_id=:unitId GROUP BY pbs.pbs_id) as dataTable ", nativeQuery = true)
    Page<TinvPharmacyBillSettlement> findAllByPbsUnitIdAndPbsUnitIdUnitId(@Param("qString") String qString, @Param("unitId") long unitId, @Param("listTypePharmacy") Integer listTypePharmacy, Pageable page);

}
