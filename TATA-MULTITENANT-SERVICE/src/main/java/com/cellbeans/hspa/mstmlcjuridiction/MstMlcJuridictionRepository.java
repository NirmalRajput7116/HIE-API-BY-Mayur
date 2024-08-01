package com.cellbeans.hspa.mstmlcjuridiction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstMlcJuridictionRepository extends JpaRepository<MstMlcJuridiction, Long> {

    Page<MstMlcJuridiction> findByMjNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstMlcJuridiction> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstMlcJuridiction> findByMjNameContains(String key);

}
            
