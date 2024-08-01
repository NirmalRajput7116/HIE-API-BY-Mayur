package com.cellbeans.hspa.tinvreceivereturn;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TinvReceiveReturnRepository extends JpaRepository<TinvReceiveReturn, Long> {

    Page<TinvReceiveReturn> findByRrNumberContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<TinvReceiveReturn> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<TinvReceiveReturn> findByRrNumberContains(String key);

}
            
