package com.cellbeans.hspa.mbillservice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MbillServiceRepository extends JpaRepository<MbillService, Long> {

    /*Page<MbillService> findByServiceNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);*/

    Page<MbillService> findByServiceNameContainsOrServiceSgIdSgIdEqualsAndIsActiveTrueAndIsDeletedFalse(String name, Long serviceId, Pageable page);

    Page<MbillService> findByServiceNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MbillService> findByServiceNameContainsAndServiceIsConsultionTrueAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MbillService> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<MbillService> findAllByServiceIsConsultionTrueAndIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MbillService> findByServiceNameContains(String key);

    List<MbillService> findAllByIsDeletedFalseAndServiceIsConsultionTrueOrderByServiceNameAsc();

    List<MbillService> findByServiceNameContainsAndServiceIsLaboratoryTrueAndIsActiveTrueAndIsDeletedFalse(String serviceName);

    List<MbillService> findByServiceIsRadiologyTrueAndIsActiveTrueAndIsDeletedFalse();

    @Query(value = "SELECT mbills.* FROM mpath_test mt INNER JOIN mbill_service mbills ON mbills.service_id = mt.m_bill_service_id WHERE mt.is_active = TRUE AND mt.is_deleted = FALSE AND mt.m_bill_service_id IN " +
            " ( SELECT ms.service_id FROM mbill_service ms WHERE ms.service_is_laboratory = TRUE AND ms.is_active = TRUE AND ms.is_deleted = FALSE ) AND " +
            " mt.test_id IN (SELECT tp.tp_test_id FROM rpath_test_parameter tp) ", nativeQuery = true)
    List<MbillService> findByServiceIsLaboratoryTrueAndIsActiveTrueAndIsDeletedFalse();

    //testkp
    List<MbillService> findByServiceSgIdSgIdEqualsAndServiceCodeContainsOrServiceSgIdSgIdEqualsAndServiceNameContainsAndIsActiveTrueAndIsDeletedFalse(Long sgId, String serviceCode, Long sgId2, String serviceName);

    // find by sgid and service name or code
    List<MbillService> findByServiceSgIdSgIdEqualsAndServiceNameContainsOrServiceSgIdSgIdEqualsAndServiceCodeContainsAndServiceIsBestPackageAndIsActiveTrueAndIsDeletedFalse(Long sgId, String serviceName, Long sgId2, String serviceCode, Boolean serviceIsBestPackage);

    // find by groupid and service name or code
    List<MbillService> findByServiceGroupIdGroupIdEqualsAndServiceNameContainsOrServiceGroupIdGroupIdEqualsAndServiceCodeContainsAndServiceIsBestPackageAndIsDeletedFalseAndIsActiveTrue(Long groupId, String serviceName, Long groupId2, String serviceCode, Boolean serviceIsBestPackage);

    // find by service name or code
    //Author : Mohit
    List<MbillService> findByServiceNameContainsAndIsActiveTrueAndIsDeletedFalseAndServiceIsBestPackageOrServiceCodeContainsAndIsActiveTrueAndIsDeletedFalseAndServiceIsBestPackage(String serviceName, Boolean serviceIsBestPackage, String serviceCode, Boolean serviceIsBestPckg);

    List<MbillService> findAllByServiceSgIdSgIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long sgId);

    List<MbillService> findAllByserviceSgIdSgGroupIdGroupIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long groupId);

    List<MbillService> findByIsActiveTrueAndIsDeletedFalse();

    List<MbillService> findByServiceIsConsumableTrueAndIsActiveTrueAndIsDeletedFalse();

    List<MbillService> findByServiceNameContainsOrServiceCodeContainsAndIsDeletedFalseAndIsActiveTrue(String serviceName, String serviceCode);

    List<MbillService> findByServiceIsConsumableTrueAndServiceNameContainsOrServiceCodeContainsAndIsDeletedFalseAndIsActiveTrue(String serviceName, String serviceCode);

    List<MbillService> findByServiceNameContainsAndIsActiveTrueAndIsDeletedFalseAndServiceIsBestPackageOrServiceCodeContainsAndServiceIsBestPackageAndIsActiveTrueAndIsDeletedFalse(String serviceName, Boolean serviceIsBestPackage, String serviceCode, Boolean serviceIsBestPckg);

    List<MbillService> findByServiceNameContainsAndIsActiveTrueAndIsDeletedFalseOrServiceCodeContainsAndServiceIsBestPackageAndIsActiveEqualsAndIsDeletedEquals(String serviceName, String serviceCode, Boolean serviceIsBestPackage, Boolean isactive, Boolean isdeleted, Pageable page);

    List<MbillService> findAllByServiceIsOtProcedureTrueAndIsActiveTrueAndIsDeletedFalse();

    // find by groupid and service name or code
    List<MbillService> findByServiceGroupIdGroupIdEqualsAndServiceNameContainsAndIsActiveTrueAndIsDeletedFalseOrServiceCodeContainsAndServiceIsBestPackageAndIsDeletedFalseAndIsActiveTrue(Long groupId, String serviceName, String serviceCode, Boolean serviceIsBestPackage, Pageable page);

    // find by sgid and service name or code
    List<MbillService> findByServiceSgIdSgIdEqualsAndServiceNameContainsAndIsActiveTrueAndIsDeletedFalseOrServiceCodeContainsAndServiceIsBestPackageAndIsActiveTrueAndIsDeletedFalse(Long sgId, String serviceName, String serviceCode, Boolean serviceIsBestPackage, Pageable page);

    @Query(value = "select * from mbill_service where service_id=:serviceID", nativeQuery = true)
    MbillService findbyID(@Param("serviceID") Long serviceID);

    MbillService findByServiceNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);
}

