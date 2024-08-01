package com.cellbeans.hspa.mstpriority;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstPriorityRepository extends JpaRepository<MstPriority, Long> {

    Page<MstPriority> findByPriorityNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByPriorityNameAsc(String name, Pageable page);

    List<MstPriority> findByPriorityNameAndIsActiveTrueAndIsDeletedFalse(String name);

    Page<MstPriority> findAllByIsActiveTrueAndIsDeletedFalseOrderByPriorityNameAsc(Pageable page);

    List<MstPriority> findByPriorityNameContains(String key);

    MstPriority findByPriorityNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
            
