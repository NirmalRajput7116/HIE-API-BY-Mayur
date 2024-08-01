package com.cellbeans.hspa.temrcomplaint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemrComplaintRepository extends JpaRepository<TemrComplaint, Long> {


    /* Page<TemrComplaint> findByComplaintComplaintIdComplaintNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);*/

    Page<TemrComplaint> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);
//    List<TemrComplaint> findByComplaintComplaintIdComplaintNameContains(String key);
}
            
