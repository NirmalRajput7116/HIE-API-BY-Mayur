package com.cellbeans.hspa.mstgpset;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstGpSetRepository extends JpaRepository<MstGpSet, Long> {

    List<MstGpSet> findByGpSetNameContains(String key);

    Page<MstGpSet> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<MstGpSet> findByGpSetNameContainsAndIsActiveTrueAndIsDeletedFalse(String key, Pageable page);

}
