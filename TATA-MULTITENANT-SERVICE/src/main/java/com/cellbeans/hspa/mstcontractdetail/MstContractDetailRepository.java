package com.cellbeans.hspa.mstcontractdetail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstContractDetailRepository extends JpaRepository<MstContractDetail, Long> {

    Page<MstContractDetail> findByCdNameAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstContractDetail> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstContractDetail> findByCdNameContains(String key);

}
            
