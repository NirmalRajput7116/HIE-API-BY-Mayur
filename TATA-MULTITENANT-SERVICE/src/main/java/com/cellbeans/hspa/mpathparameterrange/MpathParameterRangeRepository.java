package com.cellbeans.hspa.mpathparameterrange;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MpathParameterRangeRepository extends JpaRepository<MpathParameterRange, Long> {

    List<MpathParameterRange> findAllByPrParameterIdParameterIdAndIsActiveTrueAndIsDeletedFalse(long paramId);

    Page<MpathParameterRange> findByPrAgeFromContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MpathParameterRange> findByPrParameterIdParameterIdLikeAndIsActiveTrueAndIsDeletedFalse(long parameterId, Pageable page);

    Page<MpathParameterRange> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MpathParameterRange> findByPrAgeFromContains(String key);

    @Query(value = "SELECT * FROM mpath_parameter_range where (:age between pr_age_from and pr_age_to) and pr_parameter_id=:paramId and pr_gender_id=:genderId and is_deleted=false and is_active=true", nativeQuery = true)
    List<MpathParameterRange> findByParamByAgeAndGender(@Param("age") long age, @Param("genderId") long genderId, @Param("paramId") long paramId);
//    @Query(value = "select param from MpathParameterRange param where (param.prAgeFrom >=:age and param.prAgeTo<=:age) and param.prGenderId.genderId=:genderId and param.prParameterId.parameterId=:paramId" )
//    List<MpathParameterRange> findByParamByAgeAndGender(@Param("age") int age,@Param("genderId") long genderId,@Param("paramId") long paramId);
//    public List<Country> findByPopulationBetween(int start, int end);

   /* @Query("select ar from MpathParameterRange ar where( (ar.arAgeFrom >= ?3 and ar.arAgeTo<= ?3) and (ar.arGenderId.genderId = ?1) and (ar.arParameterId.parameterId = ?2))")
    List<MpathParameterRange> findByParamByAgeAndGender(long genderId, long paramId, int age);*/
}
