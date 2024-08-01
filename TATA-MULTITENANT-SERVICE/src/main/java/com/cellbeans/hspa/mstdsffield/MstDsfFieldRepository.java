package com.cellbeans.hspa.mstdsffield;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstDsfFieldRepository extends JpaRepository<MstDsfFeild, Long> {

    List<MstDsfFeild> findByDfDsfIdDsfIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long DsfId);

    MstDsfFeild findByDfDsfIdDsfIdEqualsAndDfFieldIdFieldIdEquals(Long dsfId, Long fieldId);
}
