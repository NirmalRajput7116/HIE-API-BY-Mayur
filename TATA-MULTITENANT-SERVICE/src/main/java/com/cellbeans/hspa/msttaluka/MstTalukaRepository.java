package com.cellbeans.hspa.msttaluka;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MstTalukaRepository extends JpaRepository<MstTaluka, Long> {

    Page<MstTaluka> findByTalukaNameContainsAndIsActiveTrueAndIsDeleteFalseOrderByTalukaNameAsc(String name, Pageable page);

    Page<MstTaluka> findAllByIsActiveTrueAndIsDeleteFalseOrderByTalukaNameAsc(Pageable page);

    List<MstTaluka> findByTalukaNameContains(String key);

    MstTaluka findByTalukaNameEqualsAndIsActiveTrueAndIsDeleteFalse(String key);

    List<MstTaluka> findByTalukaDistrictIdDistrictIdEqualsAndIsActiveTrueAndIsDeleteFalse(Long id);

    @Query(value = "SELECT * FROM mst_taluka mt INNER JOIN mst_district md ON md.district_id = mt.taluka_district_id WHERE mt.taluka_name = :talukaName AND md.district_id = :distId AND mt.is_active = TRUE AND mt.is_delete = FALSE", nativeQuery = true)
    MstTaluka findByTalukaName(@Param("talukaName") String talukaName, @Param("distId") Long distId);

}
            
