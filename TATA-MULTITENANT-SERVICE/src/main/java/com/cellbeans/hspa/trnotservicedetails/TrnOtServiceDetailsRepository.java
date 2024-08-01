package com.cellbeans.hspa.trnotservicedetails;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrnOtServiceDetailsRepository extends JpaRepository<TrnOtServiceDetails, Long> {

    List<TrnOtServiceDetails> findAllByOsrdOpdIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

    List<TrnOtServiceDetails> findAllByOsrdOpsIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

    List<TrnOtServiceDetails> findAllByOsrdProcedureIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

}
