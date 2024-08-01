package com.cellbeans.hspa.mradtest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MradTestRepository extends JpaRepository<MradTest, Long> {

    Page<MradTest> findByTestNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MradTest> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MradTest> findByTestNameContains(String key);

}
            
