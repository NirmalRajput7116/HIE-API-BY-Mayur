package com.cellbeans.hspa.mstmaritalstatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstMaritalStatusRepository extends JpaRepository<MstMaritalStatus, Long> {

    Page<MstMaritalStatus> findByMsNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByMsNameAsc(String name, Pageable page);

    Page<MstMaritalStatus> findAllByIsActiveTrueAndIsDeletedFalseOrderByMsNameAsc(Pageable page);

    List<MstMaritalStatus> findByMsNameContains(String key);

    MstMaritalStatus findByMsNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
            
