package com.cellbeans.hspa.mstsocialstatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstSocialStatusRepository extends JpaRepository<MstSocialStatus, Long> {

    Page<MstSocialStatus> findBySocialstatusNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstSocialStatus> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstSocialStatus> findBySocialstatusNameContains(String key);

}
