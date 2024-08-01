package com.cellbeans.hspa.memrchiefcomplaint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemrChiefComplaintRepository extends JpaRepository<MemrChiefComplaint, Long> {

    Page<MemrChiefComplaint> findByCcNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Iterable<MemrChiefComplaint> findAllByCcNameAndIsActiveTrueAndIsDeletedFalse(String CcName);

    Page<MemrChiefComplaint> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MemrChiefComplaint> findByCcNameContains(String key);

    MemrChiefComplaint findByCcNameAndIsActiveTrueAndIsDeletedFalse(String CcName);

}
            
