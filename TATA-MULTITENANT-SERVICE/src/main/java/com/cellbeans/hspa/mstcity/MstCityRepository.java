package com.cellbeans.hspa.mstcity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MstCityRepository extends JpaRepository<MstCity, Long> {

    Page<MstCity> findByCityNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByCityName(String name, Pageable page);

    Page<MstCity> findAllByIsActiveTrueAndIsDeletedFalseOrderByCityName(Pageable page);

    List<MstCity> findByCityNameContains(String key);

//    List<MstCity> findByCityStateIdStateIdEquals(Long key);
    List<MstCity> findByCityStateIdStateIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);

    List<MstCity> findByCityDistrictIdDistrictIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long Key);

    List<MstCity> findByCityTalukaIdTalukaIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long Key);

    MstCity findByCityNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

    @Query(value = "SELECT count(mc.city_name) FROM mst_city mc WHERE mc.city_name=:city_name and mc.is_deleted=false", nativeQuery = true)
    int findByAllOrderByCityName(@Param("city_name") String city_name);

    @Query(value = "select city.city_id,city.city_name FROM mst_city city where city.is_deleted=0  order by city.city_name asc limit 0,100", nativeQuery = true)
    List<Object[]> findAllByIsDeletedFalseOrderByCityNameAsc();

    Page<MstCity> findByCityNameContainsAndCityTalukaIdTalukaIdAndIsActiveTrueAndIsDeletedFalse(String name, Long cityId, Pageable page);
}
            
