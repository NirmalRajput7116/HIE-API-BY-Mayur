package com.cellbeans.hspa.memrclinicalform;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MemrClinicalFormRepository extends JpaRepository<MemrClinicalForm, Long> {

    Page<MemrClinicalForm> findBymcfIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MemrClinicalForm> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);
    // Page<MemrClinicalForm> findAllByMcfNameAndIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MemrClinicalForm> findByMcfIdContains(String key);

    //All clinical form List
    List<MemrClinicalForm> findAllByMcfNameEqualsAndIsActiveTrueAndIsDeletedFalseOrderByMcfCountrolParameterAsc(String key);

    //    Orgid Wise Clinical form list 12.08.2021 by Rohan and Hemant
//    List<MemrClinicalForm> findAllByMcfOrgIdOrgIdAndIsActiveTrueAndIsDeletedFalseOrderByMcfCountrolParameterAsc(Long key);
    @Query(value = "SELECT * FROM memr_clinical_form mf " +
            "WHERE mf.mcf_is_care_coordination = false  " +
            "AND mf.is_active =1 " +
            "AND mf.is_deleted = 0 AND mf.mcf_org_id = :key " +
            "GROUP BY mf.mcf_name", nativeQuery = true)
    List<MemrClinicalForm> findAllByMcfOrgIdOrgIdAndIsActiveTrueAndIsDeletedFalseAndMcfIsCareCoordinationFalseOrderByMcfCountrolParameterAsc1(@Param("key") Long key);

    List<MemrClinicalForm> findAllByMcfOrgIdOrgIdAndIsActiveTrueAndIsDeletedFalseAndMcfIsCareCoordinationFalseOrderByMcfCountrolParameterAsc(Long key);

    List<MemrClinicalForm> findAllByMcfOrgIdOrgIdAndIsActiveTrueAndIsDeletedFalseAndMcfIsCareCoordinationTrueOrderByMcfCountrolParameterAsc(Long key);

    @Query(value = "SELECT * FROM memr_clinical_form mf " +
            "WHERE mf.mcf_is_care_coordination = true " +
            "AND mf.is_active =1 " +
            "AND mf.is_deleted = 0 AND mf.mcf_org_id = :key " +
            "GROUP BY mf.mcf_name", nativeQuery = true)
    List<MemrClinicalForm> findAllByMcfOrgIdOrgIdAndIsActiveTrueAndIsDeletedFalseAndMcfIsCareCoordinationTrueOrderByMcfCountrolParameterAsc1(@Param("key") Long key);

    @Query("SELECT DISTINCT mcfName FROM MemrClinicalForm where isActive = true  and is_deleted = false ")
    List<MemrClinicalForm> findDistinctformName();

    //	@Query(value = "SELECT DISTINCT mcf_name FROM memr_clinical_form where is_active = true and is_deleted = false   #pageable ", countQuery = "SELECT  COUNT(DISTINCT mcf_name) FROM memr_clinical_form where is_active = true and is_deleted = false",nativeQuery = true)
    @Query(value = "SELECT DISTINCT mcf_name FROM memr_clinical_form where is_active = true and is_deleted = false ", nativeQuery = true)
    List<MemrClinicalForm> findDistinctName();

    @Modifying
    @Transactional
    @Query("UPDATE MemrClinicalForm Set isDeleted = true WHERE  mcf_name = :key")
    int findOneByMcfNameEquals(@Param("key") String key);

    MemrClinicalForm findByMcfNameEquals(String name);

}


