package com.cellbeans.hspa.trnopdipd;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrnOpdIpdRepository extends JpaRepository<TrnOpdIpd, Long> {

    Page<TrnOpdIpd> findByOiStatusAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TrnOpdIpd> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TrnOpdIpd> findByOiStatusContains(String key);

}
            