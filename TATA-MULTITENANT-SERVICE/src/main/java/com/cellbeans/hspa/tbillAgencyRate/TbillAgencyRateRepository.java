package com.cellbeans.hspa.tbillAgencyRate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TbillAgencyRateRepository extends JpaRepository<TbillAgencyRate, Long> {
//    Page<MotSurgeryNote> findBySnNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TbillAgencyRate> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TbillAgencyRate> findAllByIsActiveTrueAndIsDeletedFalse();

    Page<TbillAgencyRate> findByAtAgencyIdAgencyNameContainsOrAtMbillServiceIdServiceNameContainsAndIsActiveTrueAndIsDeletedFalse(String agencyname, String servicename, Pageable page);

    TbillAgencyRate findByAtAgencyIdAgencyIdAndAtMbillServiceIdServiceIdAndIsActiveTrueAndIsDeletedFalse(long agencyId, long mbillServiceId);
//    List<MotSurgeryNote> findBySnNameContains(String key);

}
            
