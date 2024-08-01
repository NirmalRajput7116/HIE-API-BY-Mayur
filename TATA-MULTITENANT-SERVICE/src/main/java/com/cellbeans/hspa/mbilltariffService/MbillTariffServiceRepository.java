package com.cellbeans.hspa.mbilltariffService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MbillTariffServiceRepository extends JpaRepository<MbillTariffService, Long> {

    List<MbillTariffService> findAllByTsTariffIdEqualsAndIsActiveTrueAndIsDeletedFalse(String tstariffId);

    List<MbillTariffService> findAllByTsTariffIdEqualsAndTsServiceIdServiceIdEqualsAndIsActiveTrueAndIsDeletedFalse(String tstariffId, long serviceid);

    List<MbillTariffService> findAllByTsTariffIdEqualsAndTsServiceIdServiceNameContainsAndIsActiveTrueAndIsDeletedFalse(String tstariffId, String service);

    //code for search service random old code
    List<MbillTariffService> findAllByTsTariffIdEqualsAndTsServiceIdServiceNameContainsAndIsActiveTrue(
            String tstariffId, String service, Pageable page);

    MbillTariffService findAllByTsTariffIdEqualsAndTsServiceIdServiceIdAndIsActiveTrue(
            String tstariffId, Long serviceId);

    List<MbillTariffService> findAllByTsTariffIdEqualsAndTsServiceIdServiceNameStartingWithAndIsActiveTrue(
            String tstariffId, String service, Pageable page);

    Page<MbillTariffService> findAllByTsTariffIdEqualsAndIsActiveTrueAndIsDeletedFalse(String tstariffId, Pageable page);

    List<MbillTariffService> findAllByTsTariffIdEqualsAndTsServiceIdServiceNameContainsAndIsActiveTrueAndIsDeletedFalseOrTsTariffIdEqualsAndTsServiceIdServiceCodeContainsAndIsActiveTrueAndIsDeletedFalseOrTsTariffIdEqualsAndTsServiceIdServiceAbbreviationsContainsAndIsActiveTrueAndIsDeletedFalse(String tstariffId, String service, String tstariffId1, String service1, String tstariffId2, String service2);

    List<MbillTariffService> findAllByTsTariffIdEqualsAndTsServiceIdServiceSgIdSgIdEqualsAndTsServiceIdServiceNameContainsAndIsActiveTrueAndIsDeletedFalse(String tstariffId, Long tssgId2, String service);

    List<MbillTariffService> findAllByTsTariffIdEqualsAndTsServiceIdServiceIsBestPackageTrueAndTsServiceIdServiceNameContainsAndIsActiveTrueAndIsDeletedFalse(String tstariffId, String service);

    List<MbillTariffService> findAllByTsTariffIdEqualsAndTsServiceIdServiceSgIdSgIdEqualsAndTsServiceIdServiceNameContainsAndIsActiveTrueAndIsDeletedFalseOrTsTariffIdEqualsAndTsServiceIdServiceSgIdSgIdEqualsAndTsServiceIdServiceCodeContainsAndIsActiveTrueAndIsDeletedFalseOrTsTariffIdEqualsAndTsServiceIdServiceSgIdSgIdEqualsAndTsServiceIdServiceAbbreviationsContainsAndIsActiveTrueAndIsDeletedFalse(String tstariffId, Long tssgId, String service, String tstariffId1, Long tssgId1, String service1, String tstariffId2, Long tssgId2, String service2);

}
