package com.cellbeans.hspa.trnotproceduredetail;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrnOtProcedureDetailRepository extends JpaRepository<TrnOtProcedureDetail, Long> {

    List<TrnOtProcedureDetail> findAllByOpOpdIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

    List<TrnOtProcedureDetail> findAllByOpOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

    List<TrnOtProcedureDetail> findAllByOpProcedureIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

}
