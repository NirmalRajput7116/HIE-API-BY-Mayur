package com.cellbeans.hspa.rpathparametertextlink;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RpathParameterTextLinkRepository extends JpaRepository<RpathParameterTextLink, Long> {
//    Page<RpathParameterTextLink> findByTpTestIdContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<RpathParameterTextLink> findByPtlParameterIdParameterIdEqualsAndIsActiveTrueAndIsDeletedFalse(long parameterId, Pageable page);

    Page<RpathParameterTextLink> findByPtlPtIdPtIdLikeAndIsActiveTrueAndIsDeletedFalse(long ptId, Pageable page);

    Page<RpathParameterTextLink> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<RpathParameterTextLink> findByPtlPtIdContains(String key);

    List<RpathParameterTextLink> findByPtlParameterIdParameterIdEqualsAndIsActiveTrueAndIsDeletedFalse(long parameterId);
    // RpathParameterTextLink findByTpTestIdTestIdLikeAndTpParameterIdParameterIdLike(long testId, long parameterId);
}