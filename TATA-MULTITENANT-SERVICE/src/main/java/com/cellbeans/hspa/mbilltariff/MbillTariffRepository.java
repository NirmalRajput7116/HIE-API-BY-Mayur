package com.cellbeans.hspa.mbilltariff;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MbillTariffRepository extends JpaRepository<MbillTariff, Long> {

    Page<MbillTariff> findByTariffNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MbillTariff> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MbillTariff> findByTariffNameContains(String key);

    @Query(value = "SELECT new com.cellbeans.hspa.mbilltariff.MBillTariffDTO(t.tariffId, t.tariffName, t.tariffCode, t.tariffCoPay, t.tariffDiscount, t.isActive, t.isDeleted) from MbillTariff t where t.isActive=true AND t.isDeleted = false")
    List<MBillTariffDTO> getAllTariff();

    @Query(value = "SELECT new com.cellbeans.hspa.mbilltariff.MBillTariffDTO(t.tariffId, t.tariffName, t.tariffCode, t.tariffCoPay, t.tariffDiscount, t.isActive, t.isDeleted) from MbillTariff t where t.tariffunitId.unitId=?1 and t.isActive=true AND t.isDeleted = false")
    List<MBillTariffDTO> getAllTariffByUnitId(long unitId);
//   @Query(value = "SELECT * FROM mbill_tariff left join mbill_tariff_tarrif_genders on mbill_tariff_tarrif_genders.mbill_tariff_tariff_id=mbill_tariff.tariff_id where mbill_tariff.is_active=1 and mbill_tariff_tarrif_genders.tarrif_genders_gender_id=?1", nativeQuery = true)
//    List<MbillTariff> getAllTariffbygender(Long genderid);

    @Query(value = "SELECT * FROM mbill_tariff left join mbill_tariff_tarrif_genders on mbill_tariff_tarrif_genders.mbill_tariff_tariff_id=mbill_tariff.tariff_id where mbill_tariff.is_active=1 and mbill_tariff_tarrif_genders.tarrif_genders_gender_id=?1 and mbill_tariff.tariffunit_id =?2", nativeQuery = true)
    List<MbillTariff> getAllTariffbygender(Long genderid, Long unitid);

    @Query(value = "SELECT * FROM mbill_tariff left join mbill_tariff_tarrif_genders on mbill_tariff_tarrif_genders.mbill_tariff_tariff_id=mbill_tariff.tariff_id  where mbill_tariff.is_active=1 and mbill_tariff_tarrif_genders.tarrif_genders_gender_id=?1 and mbill_tariff.tarrifagefrom >= ?2 and mbill_tariff.tariffunit_id =?3", nativeQuery = true)
    List<MbillTariff> getAllTariffbygenderandage(Long genderid, String age, Long unitid);

    MbillTariff findByTariffIdAndIsActiveTrueAndIsDeletedFalse(Long tariffId);

    Page<MbillTariff> findAllByTariffunitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(long unitId, Pageable page);

    Page<MbillTariff> findByTariffNameContainsAndTariffunitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(String name, long unitId, Pageable page);

}