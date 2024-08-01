package com.cellbeans.hspa.rpathtestparameter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RpathTestParameterRepository extends JpaRepository<RpathTestParameter, Long> {

    Page<RpathTestParameter> findByTpTestIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<RpathTestParameter> findByTpTestIdTestIdLikeAndIsActiveTrueAndIsDeletedFalseOrderByTpParameterSequenceNumberAsc(long testId, Pageable page);

    List<RpathTestParameter> findByTpTestIdTestIdLikeAndIsActiveTrueAndIsDeletedFalseOrderByTpParameterSequenceNumberAsc(long testId);

    Page<RpathTestParameter> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<RpathTestParameter> findByTpTestIdContains(String key);

    RpathTestParameter findByTpTestIdTestIdLikeAndTpParameterIdParameterIdLike(long testId, long parameterId);

}
            
