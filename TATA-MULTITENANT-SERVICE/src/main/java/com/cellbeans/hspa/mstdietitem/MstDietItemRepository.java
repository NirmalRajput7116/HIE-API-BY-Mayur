package com.cellbeans.hspa.mstdietitem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstDietItemRepository extends JpaRepository<MstDietItem, Long> {

    List<MstDietItem> findByDiDietPatientTypeDpcIdInAndDiItemNameContains(List<Integer> id, String query);

    List<MstDietItem> findByDiDietPatientTypeDpcId(Integer id);

    Page<MstDietItem> findByDiItemNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstDietItem> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstDietItem> findByDiItemNameContains(String key);

    MstDietItem findByDiItemNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);
}
