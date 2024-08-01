package com.cellbeans.hspa.mpathtest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MpathTestRepository extends JpaRepository<MpathTest, Long> {

    Page<MpathTest> findByTestNameContainsAndIsActiveTrueAndIsDeletedFalseOrTestCodeContainsAndIsActiveTrueAndIsDeletedFalseOrTestPrintNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, String name1, String name2, Pageable page);

    Page<MpathTest> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MpathTest> findByTestNameContains(String key);

    List<MpathTest> findByMbillServiceIdServiceIdAndIsActiveTrueAndIsDeletedFalse(Long key);

    List<MpathTest> findByMbillServiceIdServiceIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long key);
    //   List<MpathTest> findAllByTestServiceId
}

