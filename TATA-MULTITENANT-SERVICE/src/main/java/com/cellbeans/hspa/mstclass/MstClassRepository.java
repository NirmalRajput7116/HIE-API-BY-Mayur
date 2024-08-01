package com.cellbeans.hspa.mstclass;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MstClassRepository extends JpaRepository<MstClass, Long> {

    Page<MstClass> findByClassNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstClass> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstClass> findByClassNameContains(String key);

    MstClass findByClassNameContainsAndIsActiveTrueAndIsDeletedFalse(String qString);

    MstClass findByIsOpdTrueAndIsActiveTrueAndIsDeletedFalse();

    MstClass findByIsEmgTrueAndIsActiveTrueAndIsDeletedFalse();

    @Query(value = "SELECT count(mc.class_name) FROM mst_class mc WHERE mc.class_name=:class_name and mc.is_deleted=false", nativeQuery = true)
    int findByAllOrderByClassName(@Param("class_name") String class_name);

}
            
