package com.cellbeans.hspa.trnotchecklistdetails;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrnOtProcedureChecklistRepository extends JpaRepository<TrnOtProcedureChecklist, Long> {

    List<TrnOtProcedureChecklist> findAllByOpcOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

    List<TrnOtProcedureChecklist> findAllByOpcOpdIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

    List<TrnOtProcedureChecklist> findAllByOpcProcedureIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

}
