package com.cellbeans.hspa.memrclinicalprocedure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemrClinicalProcedureRepository extends JpaRepository<MemrClinicalProcedure, Long> {

    Page<MemrClinicalProcedure> findByCpNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MemrClinicalProcedure> findAllByIsActiveTrueAndIsDeletedFalseOrderByCpName(Pageable page);

    List<MemrClinicalProcedure> findByCpNameContains(String key);

    List<MemrClinicalProcedure> findByCpSpeiclityIdSpecialityIdAndIsActiveTrueAndIsDeletedFalse(Long id, Pageable page);

    Page<MemrClinicalProcedure> findByCpNameContainsOrCpSpeiclityIdSpecialityNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByCpName(String name, String name1, Pageable page);

    @Query(value = "SELECT count(mc.cp_name) FROM memr_clinical_procedure mc WHERE mc.cp_name=:cp_name and mc.is_deleted=false", nativeQuery = true)
    int findByAllOrderByCpName(@Param("cp_name") String cp_name);
}
            
