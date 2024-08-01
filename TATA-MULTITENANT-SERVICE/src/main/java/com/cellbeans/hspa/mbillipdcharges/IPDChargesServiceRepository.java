package com.cellbeans.hspa.mbillipdcharges;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IPDChargesServiceRepository extends JpaRepository<IPDChargesService, Long> {

    IPDChargesService findByCsId(Long id);

    @Query(value = "SELECT * FROM ipd_charges_service where  ipd_charges_service.cs_date = :date order by cs_id desc LIMIT 1", nativeQuery = true)
    IPDChargesService findOneByCsDateAndIsActiveTrueAndIsDeletedFalse(@Param("date") Date date);

    List<IPDChargesService> findAllByCsChargeIdIpdchargeIdAndIsActiveTrueAndIsDeletedFalse(long id);
//   List<IPDChargesService> findAllByCsChargeIdIpdchargeIdAndIsActiveTrueAndIsDeletedFalseAndCsCancelFalse(long id);

    List<IPDChargesService> findAllByCsChargeIdIpdchargeIdAndCsServiceIdServiceNameContainsAndIsActiveTrueAndIsDeletedFalseAndCsCancelFalse(@Param("id") long id, @Param("qString") String qString);

    ///@Query(value = "SELECT i.csId,i FROM IPDChargesService i where  i.csChargeId.ipdchargeId = :id", nativeQuery = false)
    List<IPDChargesService> findAllByCsChargeIdIpdchargeIdAndCsBilledFalseAndIsActiveTrueAndIsDeletedFalseAndCsCancelFalseOrderByCsId(long id);

    List<IPDChargesService> findAllByCsChargeIdIpdchargeIdAndIsActiveTrueAndIsDeletedFalseOrderByCsId(long id);

    List<IPDChargesService> findAllByCsChargeIdIpdchargeIdEqualsAndCsServiceIdServiceIdAndIsActiveTrueAndIsDeletedFalseAndCsBilledTrue(long chargeid, long serviceid);

}
