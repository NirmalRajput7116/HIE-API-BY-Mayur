package com.cellbeans.hspa.mstdistrict;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MstDistrictRepository extends JpaRepository<MstDistrict, Long> {

    Page<MstDistrict> findByDistrictNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstDistrict> findAllByIsActiveTrueAndIsDeletedFalseOrderByDistrictNameAsc(Pageable page);

    List<MstDistrict> findByDistrictNameContains(String key);

    List<MstDistrict> findByDistrictStateIdStateIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long id);

    @Query(value = "SELECT * FROM mst_district md INNER JOIN mst_state ms ON ms.state_id = md.district_state_id  WHERE md.district_name = :districtName AND ms.state_id = :stateId  AND md.is_active = TRUE AND md.is_deleted = FALSE", nativeQuery = true)
    MstDistrict findByDistrictName(@Param("districtName") String districtName, @Param("stateId") Long stateId);

}
            
