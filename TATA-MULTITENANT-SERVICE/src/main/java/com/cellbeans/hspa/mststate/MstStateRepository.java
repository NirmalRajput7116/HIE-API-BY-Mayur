package com.cellbeans.hspa.mststate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MstStateRepository extends JpaRepository<MstState, Long> {

    Page<MstState> findByStateNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByStateNameAsc(String name, Pageable page);

    Page<MstState> findAllByIsActiveTrueAndIsDeletedFalseOrderByStateNameAsc(Pageable page);

    List<MstState> findByStateNameContains(String key);

    List<MstState> findByStateCountryIdCountryIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

    @Query(value = "SELECT ms.* FROM mst_state ms INNER JOIN mst_country mc ON mc.country_id = ms.state_country_id WHERE ms.state_name = :stateName AND ms.state_country_id = :countryId AND ms.is_active = TRUE AND ms.is_deleted = FALSE", nativeQuery = true)
    MstState findByAllOrderByStateName(@Param("stateName") String stateName, @Param("countryId") Long countryId);

}
            
