package com.cellbeans.hspa.trncompanytariff;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrnCompanyTariffRepository extends JpaRepository<TrnCompanyTariff, Long> {
    // Page<TrnCompanyTariff> findByCtTariffIdTariffNameContainsOrCtCompanyIdCompanyIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TrnCompanyTariff> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);
//    @Query(value = "select m.* from trn_company_tariff t join mbill_tariff m where m.tariff_id=t.ct_tariff_id and t.ct_company_id=:key", nativeQuery = true)

    List<TrnCompanyTariff> findByCtCompanyIdCompanyIdEquals(Long key);

    TrnCompanyTariff findByCtCompanyIdCompanyIdAndCtTariffIdTariffId(Long companyId, Long tariffId);

    List<TrnCompanyTariff> findByCtTariffIdTariffIdAndCtCompanyIdCompanyCtIdCtId(Long tariffId, Long companyId);

    List<TrnCompanyTariff> findByCtTariffIdTariffId(Long tariffId);

}
            
