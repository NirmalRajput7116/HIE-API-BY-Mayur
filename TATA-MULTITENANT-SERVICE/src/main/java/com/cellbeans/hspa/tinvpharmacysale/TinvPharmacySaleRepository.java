package com.cellbeans.hspa.tinvpharmacysale;

import com.cellbeans.hspa.mstvisit.MstVisitDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TinvPharmacySaleRepository extends JpaRepository<TinvPharmacySale, Long> {

    Page<TinvPharmacySale> findByPsNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TinvPharmacySale> findByPsNameContainsAndPharmacyUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(String name, long unitId, Pageable page);

    Page<TinvPharmacySale> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<TinvPharmacySale> findAllByPharmacyUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(Pageable page, Long unitId);

    List<TinvPharmacySale> findByPsVisitIdContains(String key);

    // Author: Mohit and Vinayak
    List<TinvPharmacySale> findAllByPsVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(Long visitId);

    default Map<String, String> getCoPayagainstVisit(Long visitId) {
        // System.out.println("VisitId :"+visitId);
        List<TinvPharmacySale> pharmacySales = findAllByPsVisitIdVisitIdAndIsActiveTrueAndIsDeletedFalse(visitId);
        Map<String, String> doubleMap = new HashMap<>();
        doubleMap.put("psOutStandingAmountForPatient", "0");
        doubleMap.put("psOutStandingAmountForCompany", "0");
        for (TinvPharmacySale pharmacySale : pharmacySales) {
            if (pharmacySale.getPsOutStandingAmountForCompany() > 0) {
                doubleMap.put("psId", doubleMap.get("psId") + "," + String.valueOf(pharmacySale.getPsId()));
            }
            doubleMap.put("psOutStandingAmountForPatient", String.valueOf(Double.valueOf(doubleMap.get("psOutStandingAmountForPatient")) + pharmacySale.getPsOutStandingAmountForPatient()));
            doubleMap.put("psOutStandingAmountForCompany", String.valueOf(Double.valueOf(doubleMap.get("psOutStandingAmountForCompany")) + pharmacySale.getPsOutStandingAmountForCompany()));
        }
        return doubleMap;
    }

    //MIS start
    @Query(value = "SELECT * FROM tinv_pharmacy_sale  where is_active = 1 and is_deleted = 0 and pharmacy_unit_id = :unitid and  DATE_FORMAT(created_date,'%y-%m-%d')= CURDATE()  \n#pageable\n", countQuery = "Select count(*) from tinv_pharmacy_sale  where is_active = 1 and is_deleted = 0 and pharmacy_unit_id = :unitid and  DATE_FORMAT(created_date,'%y-%m-%d')=CURDATE() ", nativeQuery = true)
    Page<TinvPharmacySale> findAllByCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(@Param("unitid") Long unitid, Pageable page);

    Page<TinvPharmacySale> findAllByPsVisitIdVisitPatientIdPatientMrNoContainsOrPsPatientFirstNameContainsOrPsPatientMiddleNameContainsOrPsPatientLastNameContainsAndIsActiveTrueAndIsDeletedFalse(String mrno, String fn, String mn, String lnm, Pageable page);

    @Query(value = "SELECT * FROM tinv_pharmacy_sale  where is_active = 1 and is_deleted = 0 and pharmacy_unit_id = :unitid  and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)   \n#pageable\n", countQuery = "Select count(*) from tinv_pharmacy_sale  where is_active = 1 and is_deleted = 0 and pharmacy_unit_id = :unitid  and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE)  ", nativeQuery = true)
    Page<TinvPharmacySale> findAllByFromToCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(@Param("sd") String sd, @Param("ed") String ed, @Param("unitid") Long unitid, Pageable page);

    //print MIS
    List<TinvPharmacySale> findAllByPsVisitIdVisitPatientIdPatientMrNoContainsOrPsPatientFirstNameContainsOrPsPatientMiddleNameContainsOrPsPatientLastNameContainsAndIsActiveTrueAndIsDeletedFalse(String mrno, String fn, String mn, String lnm);

    @Query(value = "SELECT * FROM tinv_pharmacy_sale  where is_active = 1 and is_deleted = 0 and pharmacy_unit_id = :unitid and   DATE_FORMAT(created_date,'%y-%m-%d') = CURDATE()  ", nativeQuery = true)
    List<TinvPharmacySale> findAllByCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(@Param("unitid") Long unitid);

    List<TinvPharmacySale> findAllByPharmacyUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long unitid);

    @Query(value = "SELECT * FROM tinv_pharmacy_sale  where is_active = 1 and is_deleted = 0 and pharmacy_unit_id = :unitid and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE) ", nativeQuery = true)
    List<TinvPharmacySale> findAllByFromToCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(@Param("sd") String sd, @Param("ed") String ed, @Param("unitid") Long unitid);

    Page<TinvPharmacySale> findAllByPharmacyUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long unitid, Pageable page);
    //MIS end

    @Query(value = "select new com.cellbeans.hspa.mstvisit.MstVisitDTO(p.psVisitId.visitId,p.psVisitId.visitPatientId,p.psVisitId.visitTariffId) from TinvPharmacySale p where p.pharmacyUnitId.unitId=:unitid and p.pharmacyType=:pharmacyType and p.isActive=true and p.isDeleted=false and p.psOutStandingAmountForPatient>0 and (p.psVisitId.visitPatientId.patientMrNo like %:mrNumber% OR p.psVisitId.visitPatientId.patientUserId.userFirstname like %:firstName% OR p.psVisitId.visitPatientId.patientUserId.userLastname like %:lastName% OR p.psVisitId.visitPatientId.patientUserId.userMobile like %:mobileNumber%)")
    List<MstVisitDTO> findByPatientDetailsbyUnitIDAndSearch(@Param("unitid") long unitId, @Param("pharmacyType") int pharmacyType, @Param("mrNumber") String mrNumber, @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("mobileNumber") String mobileNumber, Pageable pageable);

    //rohan MIS Code Start Pharmacy Return List
    @Query(value = "SELECT * FROM tinv_pharmacy_sale  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d')= CURDATE()  \n#pageable\n", countQuery = "Select count(*) from tinv_pharmacy_sale  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d')=CURDATE() ", nativeQuery = true)
    Page<TinvPharmacySale> findAllByCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(Pageable page);

    @Query(value = "SELECT * FROM tinv_pharmacy_sale  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:fromdate as DATE) and CAST(:todate as DATE)   \n#pageable\n", countQuery = "Select count(*) from tinv_pharmacy_sale  where is_active = 1 and is_deleted = 0 and pharmacy_unit_id = :unitid  and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:fromdate as DATE) and CAST(:todate as DATE)  ", nativeQuery = true)
    Page<TinvPharmacySale> findAllByFromToCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(@Param("fromdate") String fromdate, @Param("todate") String todate, Pageable page);

    @Query(value = "SELECT * FROM tinv_pharmacy_sale  where is_active = 1 and is_deleted = 0 and   DATE_FORMAT(created_date,'%y-%m-%d') = CURDATE()  ", nativeQuery = true)
    List<TinvPharmacySale> findAllByCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse();

    @Query(value = "SELECT * FROM tinv_pharmacy_sale  where is_active = 1 and is_deleted = 0 and  DATE_FORMAT(created_date,'%y-%m-%d') BETWEEN CAST(:sd as DATE) and CAST(:ed as DATE) ", nativeQuery = true)
    List<TinvPharmacySale> findAllByFromToCreatedDateEqualsAndIsActiveTrueAndIsDeletedFalse(@Param("sd") String sd, @Param("ed") String ed);

    List<TinvPharmacySale> findAllByPharmacyUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalse(Long[] unitId);

    List<TinvPharmacySale> findAllByIsActiveTrueAndIsDeletedFalse();

    Page<TinvPharmacySale> findAllByPharmacyUnitIdUnitIdInAndIsActiveTrueAndIsDeletedFalse(Pageable page, Long[] unitId);
    //rohan MIS Code End

    //by seetanshu
    @Query(value = "select new com.cellbeans.hspa.mstvisit.MstVisitDTO(p.psAdmissionId.admissionId,p.psAdmissionId.admissionPatientId,p.psVisitId.visitTariffId,p.psAdmissionId.admissionIpdNo) from TinvPharmacySale p where p.pharmacyUnitId.unitId=:unitid and p.pharmacyType=:pharmacyType and p.isActive=true and p.isDeleted=false and p.psAdmissionId.admissionStatus=false and p.psOutStandingAmountForPatient>0 and (p.psAdmissionId.admissionPatientId.patientMrNo like %:mrNumber% OR p.psAdmissionId.admissionPatientId.patientUserId.userFirstname like %:firstName% OR p.psAdmissionId.admissionPatientId.patientUserId.userLastname like %:lastName% OR p.psAdmissionId.admissionPatientId.patientUserId.userMobile like %:mobileNumber%)")
    List<MstVisitDTO> findByPatientDetailsbyUnitIDAndSearchWithIpdNo(@Param("unitid") long unitId, @Param("pharmacyType") int pharmacyType, @Param("mrNumber") String mrNumber, @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("mobileNumber") String mobileNumber, Pageable pageable);

}