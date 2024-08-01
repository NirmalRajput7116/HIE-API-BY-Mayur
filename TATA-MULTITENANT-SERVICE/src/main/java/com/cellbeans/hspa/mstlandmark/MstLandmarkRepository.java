package com.cellbeans.hspa.mstlandmark;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MstLandmarkRepository extends JpaRepository<MstLandmark, Long> {

    Page<MstLandmark> findByLandmarkNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByLandmarkNameAsc(String name, Pageable page);

    Page<MstLandmark> findByLandmarkNameContainsAndLandmarkCityIdCityIdAndIsActiveTrueAndIsDeletedFalse(String name, Long cityId, Pageable page);

    Page<MstLandmark> findAllByIsActiveTrueAndIsDeletedFalseOrderByLandmarkNameAsc(Pageable page);

    List<MstLandmark> findByLandmarkNameContains(String key);

    List<MstLandmark> findByLandmarkCityIdCityIdEquals(Long key);

    @Query(value = "SELECT * FROM mst_landmark ml INNER JOIN mst_city mct ON mct.city_id = ml.landmark_city_id WHERE ml.landmark_name = :landmarkName AND mct.city_id = :cityId AND ml.is_active = TRUE AND ml.is_deleted = FALSE", nativeQuery = true)
    MstLandmark findByLandMarkName(@Param("landmarkName") String landmarkName, @Param("cityId") Long cityId);
}

