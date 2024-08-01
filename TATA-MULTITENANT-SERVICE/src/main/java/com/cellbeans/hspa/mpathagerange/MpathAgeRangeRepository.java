package com.cellbeans.hspa.mpathagerange;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MpathAgeRangeRepository extends JpaRepository<MpathAgeRange, Long> {

    Page<MpathAgeRange> findByArAgeFromContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MpathAgeRange> findByArParameterIdParameterIdLikeAndIsActiveTrueAndIsDeletedFalse(long parameterId, Pageable page);

    Page<MpathAgeRange> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MpathAgeRange> findByArAgeFromContains(String key);
//    public List<Country> findByPopulationBetween(int start, int end);
//    @Query("select ar from MpathAgeRange ar where( (ar.arAgeFrom >= ?1 and ar.arAgeTo<= ?1) and (ar.arGenderId.genderId = ?3) and (ar.arParameterId.parameterId = ?4))")
//    public MpathAgeRange findMpathAgeRangeQuery(int age, int genderId, int parameterId);

}
