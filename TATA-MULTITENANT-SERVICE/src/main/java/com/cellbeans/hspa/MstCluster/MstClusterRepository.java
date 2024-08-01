package com.cellbeans.hspa.MstCluster;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MstClusterRepository extends JpaRepository<MstCluster, Long> {

    Page<MstCluster> findByClusterNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstCluster> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    @Query(value = "SELECT * FROM mst_cluster mct LEFT JOIN mst_unit_unit_cluster_id_list muc ON muc.unit_cluster_id_list_cluster_id = mct.cluster_id LEFT JOIN mst_unit mu ON mu.unit_id= muc.mst_unit_unit_id where mct.is_active = 1 and mct.is_deleted = 0 and mu.unit_id =:unitId", nativeQuery = true)
    List<MstCluster> findAllByIsActiveTrueAndIsDeletedFalse(@Param("unitId") String unitId);

    List<MstCluster> findByClusterNameContains(String key);

    MstCluster findByClusterNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);
}
