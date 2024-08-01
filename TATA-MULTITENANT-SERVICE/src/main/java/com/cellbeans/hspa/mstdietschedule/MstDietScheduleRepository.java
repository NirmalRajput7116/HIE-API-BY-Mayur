package com.cellbeans.hspa.mstdietschedule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstDietScheduleRepository extends JpaRepository<MstDietSchedule, Long> {

    MstDietSchedule findByDsId(Long id);

    Page<MstDietSchedule> findByDsDietNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstDietSchedule> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstDietSchedule> findByDsIdContains(Long key);

    MstDietSchedule findByDsDietNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);

}
