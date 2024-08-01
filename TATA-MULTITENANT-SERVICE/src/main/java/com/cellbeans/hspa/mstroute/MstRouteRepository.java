package com.cellbeans.hspa.mstroute;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstRouteRepository extends JpaRepository<MstRoute, Long> {

    Page<MstRoute> findByRouteNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstRoute> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstRoute> findByRouteNameContains(String key);

    MstRoute findByRouteNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
            
